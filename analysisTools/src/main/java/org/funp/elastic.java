package org.funp;

import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.data.TDirectory;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.funp.dvcs.*;
import org.funp.utilities.*;

import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.PrintWriter;

public class elastic {

  static Event event;

  static DvcsEvent ev;

  static DvcsHisto hNC;

  static TDirectory dir;
  static TDirectory rootdir;

  static int nelastic;
  
  
  static HashMap<Integer, List<Double>> runMap;


  public static void main(String[] args) throws FileNotFoundException, IOException {



    int goodEvent;
    

    System.out.println("\n Processing arguments \n");
    processInput inputParam = new processInput(args);
    
    event = new Event();
    ev = new DvcsEvent();

    //MAKING CSV FILE
    if (processInput.getMLmode())
      ev.makecsv();
    
    // NO CUTS
    dir = new TDirectory();
    rootdir = new TDirectory();
    
    hNC = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// No Cuts
    

    nelastic = 0;
    
    //READING MAP FOR GOOD RUNS
    runMap = runUtil.createMapGagikStyle();

    //LOOP OVER THE HIPO FILES
    for (int i = 0; i < inputParam.getNfiles(); i++) {
      goodEvent = 0;
      HipoReader reader = new HipoReader();
      if (!processInput.getMCmode() && !processInput.getnTmode())//skipping if MC or untagged
        reader.setTags(9, 10, 11);
      reader.open(inputParam.getFileName(i));
      System.out.println(inputParam.getFileName(i));
      reader.getEvent(event, 0); // Reads the first event and resets to the begining of the file
      Bank runconfig = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
      
      event.read(runconfig);
      while (runconfig.getInt("run", 0) == 0) {
        reader.nextEvent(event);
        event.read(runconfig);
      }
      System.out.println("Reading run :" + runconfig.getInt("run", 0));

      int runNumber = runconfig.getInt("run", 0);
      if (runMap.get(runNumber) != null || processInput.getMCmode()) {// This if will keep runs in the map or MC
        // Beam energy from the file or set by hand for the MC
        // right now the beam energy for the MC is hardcoded
        if (runMap.get(runNumber) != null){
          ev.BeamEnergy = runMap.get(runNumber).get(2);
          System.out.println("Beam energy found is " + ev.BeamEnergy);
        }
        else if (processInput.getMCmode()){
          ev.BeamEnergy = 10.6;
          System.out.println("Beam energy set manually to " + ev.BeamEnergy);
        }
        
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy * ev.BeamEnergy - 0.0005 * 0.0005), ev.BeamEnergy);

        System.out.println("Beam energy found for run" + runconfig.getInt("run", 0) + " " + ev.vBeam.e());
        // this loops over the events
        while (reader.hasNext() == true) {
          Bank particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
          Bank runEvent = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
          Bank scint = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
          Bank scintExtras=null;
          if(!processInput.getRGAmode()){
            scintExtras     = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
            }
            else scintExtras=scint;
          //Bank scintExtras = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
          Bank calos = new Bank(reader.getSchemaFactory().getSchema("REC::Calorimeter"));
          // runconfig = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
          goodEvent = 0;

          reader.nextEvent(event);
          event.read(particles);
          event.read(scint);
          event.read(scintExtras);
          // event.read(hel);
          event.read(runconfig);
          event.read(calos);
          event.read(runEvent);
          // System.out.println(" Current event number " + runconfig.getInt("event",0));

          // goodEventFilterParticles(particles,scint,hel,scintExtras);
          boolean allEventsGood = false;
          boolean beginningEventsGood = false;
          boolean endEventsGood = false;
          boolean neitherEventsGood = false;
          if (!processInput.getMCmode()) {
            allEventsGood = (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) == 0.0);
            beginningEventsGood = (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) != 0.0);
            endEventsGood = (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) == 0.0);
            neitherEventsGood = (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) != 0.0);
          }

          if (
            (
              processInput.getMCmode() ||
              (
                (event.getEventTag() == 11 || event.getEventTag() == 10 || event.getEventTag() == 9)||
                processInput.getnTmode()
              ) &&
                  (
                    allEventsGood || /* //all events are good */
                    (beginningEventsGood && (runconfig.getInt("event", 0) < runMap.get(runNumber).get(1))) ||
                    (endEventsGood && (runconfig.getInt("event", 0) > runMap.get(runNumber).get(0))) ||
                    (neitherEventsGood && runconfig.getInt("event", 0) < runMap.get(runNumber).get(1)
                          && runconfig.getInt("event", 0) > runMap.get(runNumber).get(0))
                  )
              )
            ) {
            goodEvent = 1;
          }
          if (goodEvent == 1)
            elasticEventFilter(particles, scint, runEvent, scintExtras, calos, runNumber);

        }
        reader.close();

      }

      else {
        System.out.println("THIS RUN IS BEING SKIPPED \n\n\n\n\n");
      }

    } // end of the loop over files

    System.out.println("total dvcs events: " + nelastic);
    System.out.println("total deuteron event : " + ev.tmpdeut);
    System.out.println("total deuteron event with CTOF info: " + ev.tmpdeutctof);
    System.out.println("total deuteron event with no CTOF info: " + ev.tmpdeutnoctof);
    System.out.println("total deuteron event with CND info: " + ev.tmpdeutcnd);
    System.out.println("total helicity+: " + ev.helicityplus);
    System.out.println("total helicity-: " + ev.helicityminus);
   
    hNC.writeHipooutput(rootdir, "NC");
   
    rootdir.writeFile(inputParam.OutputLocation + "/" + inputParam.gethipoFile());

    if (processInput.getMLmode()) {
      ev.pw.write(ev.builder.toString());
      ev.pw.close();
    }

  }

  public static void elasticEventFilter(Bank particles, Bank scint, Bank runEvent, Bank scintExtras, Bank calos,
      int runNumber) {
        
        
    if (ev.FilterParticlesElastic(particles, scint, runEvent, scintExtras, calos, runNumber)) {
      hNC.fillBasicHisto(ev);
      nelastic++;   
    }
  }// end of goodEventFilterParticle

}




    
