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

public class DcoDe {

  static Event event;

  static DvcsEvent ev;

  //static DvcsHisto hNC;// No cuts
  //static DvcsHisto hNCFT;// No cuts FT
  //static DvcsHisto hNCFD;// No cuts FD

  //static DvcsHisto hDC;// DVCS cuts
  static DvcsHisto hDCFT;// DVCS cuts conf 1
  static DvcsHisto hDCFD;// DVCS cuts conf 2

  // static DvcsHisto hPCFT;// pion cuts conf 1
  // static DvcsHisto hPCFD;// pion cuts conf 2

  static DvcsHisto hCCFT;// coneangle MM 2d cut conf 1
  static DvcsHisto hCCFD;// coneangle MM 2d cut conf 2

  static DvcsHisto hACFT;// All cuts conf 1
  static DvcsHisto hACFD;// All cuts conf 2

  static MCHistos hMC;

  static TDirectory dir;
  static TDirectory rootdir;

  static int ndvcs;
  static int ndegamma;
  static int counter;
  static int FDCounter;
  static int FTCounter;

  
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
    //hNC = new DvcsHisto();// No cuts
    //hNCFT = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// No cuts
    //hNCFD = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// No cuts
    
    dir = new TDirectory();
    rootdir = new TDirectory();
    // DVCS CUTS
    hDCFT = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// DVCS cuts conf 1 
    hDCFD = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// All cuts conf 2
    
    hCCFT = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// DVCS cuts conf 1
    hCCFD = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// All cuts conf 2
    
    // ALL CUTS
    hACFT = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// DVCS cuts conf 1  
    hACFD = new DvcsHisto(processInput.getPi0mode(),inputParam.getOutputDir());// All cuts conf 2    

    hMC = new MCHistos(processInput.getPi0mode(),inputParam.getOutputDir());// All cuts conf 2    

    ndvcs = 0;
    ndegamma = 0;
    counter = 0;
    FDCounter = 0;
    FTCounter = 0;  

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
          Bank lund=null;
          if(!processInput.getRGAmode()){
            scintExtras     = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
            }
            else scintExtras=scint;
          //Bank scintExtras = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
          Bank calos = new Bank(reader.getSchemaFactory().getSchema("REC::Calorimeter"));
          // runconfig = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
          if(processInput.getMCmode()){
            lund = new Bank(reader.getSchemaFactory().getSchema("MC::Lund"));
          }
          goodEvent = 0;

          reader.nextEvent(event);
          event.read(particles);
          event.read(scint);
          event.read(scintExtras);
          // event.read(hel);
          event.read(runconfig);
          event.read(calos);
          event.read(runEvent);
          if(processInput.getMCmode()){
            event.read(lund);
          }
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
            goodEventFilterParticles(particles, scint, runEvent, scintExtras, calos, runNumber,lund);

        }
        reader.close();

      }

      else {
        System.out.println("THIS RUN IS BEING SKIPPED \n\n\n\n\n");
      }

    } // end of the loop over files



    System.out.println("total dvcs events: " + ndvcs);
    System.out.println("total deuteron gamma electron events : " + ndegamma);
    System.out.println("total deuteron event : " + ev.tmpdeut);
    System.out.println("total deuteron event with CTOF info: " + ev.tmpdeutctof);
    System.out.println("total deuteron event with no CTOF info: " + ev.tmpdeutnoctof);
    System.out.println("total deuteron event with CND info: " + ev.tmpdeutcnd);
    System.out.println("total helicity+: " + ev.helicityplus);
    System.out.println("total helicity-: " + ev.helicityminus);
    System.out.println("total events after excl cuts: " + counter);
    System.out.println("total events after excl cuts in FT: " + FTCounter);
    System.out.println("total events after excl cuts in FD: " + FDCounter);
   
    //hNC.writeHipooutput(rootdir, "NC");
    //hNCFD.writeHipooutput(rootdir, "NCFD");
    //hNCFT.writeHipooutput(rootdir, "NCFT");
    //hDC.writeHipooutput(rootdir, "DC");
    hDCFD.writeHipooutput(rootdir, "DCFD");
    hDCFT.writeHipooutput(rootdir, "DCFT");

    // hPCFD.writeHipooutput(rootdir, "PCFD");
    // hPCFT.writeHipooutput(rootdir, "PCFT");

    hCCFD.writeHipooutput(rootdir, "CCFD");
    hCCFT.writeHipooutput(rootdir, "CCFT");
    
    hACFD.writeHipooutput(rootdir, "ACFD");
    hACFT.writeHipooutput(rootdir, "ACFT");

    hMC.writeHipooutput(rootdir, "MC");
    rootdir.writeFile(inputParam.OutputLocation + "/" + inputParam.gethipoFile());

    if (processInput.getMLmode()) {
      ev.pw.write(ev.builder.toString());
      ev.pw.close();
    }

  }

  public static void goodEventFilterParticles(Bank particles, Bank scint, Bank runEvent, Bank scintExtras, Bank calos,
      int runNumber,Bank lund) {
        boolean pionCut= false;
        
    if (ev.FilterParticles(particles, scint, runEvent, scintExtras, calos, runNumber)) {
      //hNC.fillBasicHisto(ev);
      if(processInput.getPi0mode())pionCut=ev.SelectPion();
      else pionCut=!ev.SelectPion();
      if(ev.MLSelection() ||true){
      // if (ev.GetConf() == 1) {
      //   hNCFT.fillBasicHisto(ev);
      // } else if (ev.GetConf() == 2) {
      //   hNCFD.fillBasicHisto(ev);
      // }
      ndegamma++;
      if (ev.DVCScut() && ev.FiducialCuts() && ev.VertexCut(runNumber)) {
        ndvcs++;
        //hDC.fillBasicHisto(ev);
        if (ev.GetConf() == 1) {
          hDCFT.fillBasicHisto(ev);
        } else if (ev.GetConf() == 2) {
          hDCFD.fillBasicHisto(ev);
        }
        // Math.abs(ev.X("eh").mass2())<3 && ev.X("ehg").e()<1
        // (Math.toDegrees(ev.vphoton.theta())<5) && Math.abs(ev.X("ehg").e())<2 &&
        // (Math.toDegrees(ev.vphoton.theta())<5) Math.abs(ev.deltaPhiPlane2())<20
        // (ev.beta()-ev.BetaCalc())>-0.3 && Math.abs(ev.deltaPhiPlane())<1 && &&
        // (ev.beta()-ev.BetaCalc())>-0.3
        //if(pionCut  && ev.X_mis("eh").mass2()<-0.4){
        if(pionCut ){//XX add true if you don't want to exclude pions 
        //XX added a cut to remove protons
        // if (ev.GetConf() == 1) {
        //   hPCFT.fillBasicHisto(ev);
        // } else if (ev.GetConf() == 2) {
        //   hPCFD.fillBasicHisto(ev);
        // }
        if (ev.PrelimExclusivitycut()) {
          if (ev.GetConf() == 1) {
            hCCFT.fillBasicHisto(ev);
          } else if (ev.GetConf() == 2) {
            hCCFD.fillBasicHisto(ev);
          }
          if (ev.Exclusivitycut() ) {
            // && (ev.X("ehg").e()<2) && (ev.X("ehg").pz()<0.8)
            if (ev.GetConf() == 1) {
              hACFT.fillBasicHisto(ev);
              FTCounter++;
            } else if (ev.GetConf() == 2) {
              hACFD.fillBasicHisto(ev);
              FDCounter++;
            }
            counter++;
          }
        }
      }
      }
      }
    }
    if(processInput.getMCmode() && ev.MCParticles(lund)){
      //fill MC histos
      hMC.fillBasicHisto(ev);
    }
  }// end of goodEventFilterParticle

}



// ev.setArgs(args);
    //ev.isML(processInput.getMLmode());
// runUtil runInfo=new runUtil();
// static int beforefid;
  // static int afterfid;
  // static List<List<String>> records ;
   // System.out.println("number before fid"+ev.beforeFidCut);
    // System.out.println("number after fid" + afterfid);
   // System.out.println("number of 11 = " + counter11);
   //int counter11 = 0;


   // HashMap<Integer, Double> hmap=createrunmap();
    // Why calling createrunmap using the class name and not object
    // HashMap<Integer, Double> hmap=runUtil.createrunmap();

    // this is me making a hashmap of the runs from the txtfile
    // structure of hashmap
    // runnumber:firstevent, lastevent, beamenergy

    //   runconfig = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
      //int runNumberIndex = -1;
      //boolean runFound = false;


      // if (-ev.Q().mass2()>1.5){
              // BinnedHACFD.fillBasicHisto(ev);//?????????
              // }


                    // if (ev.X("eh").mass2() > (-20.0/6.0* ev.coneangle()+10)){
      // System.out.println(event.getEventTag());
      // }
          // if(counter==0)break;
    // counter--;



  //static StringBuilder builder;

        // PrintWriter pw = null;
    // try{
    // pw = new PrintWriter(new File("Data.csv"));
    // }catch (FileNotFoundException e){
    // e.printStackTrace();
    // }

    // builder = new StringBuilder();
    // String columnNames = "dedx,momentum,particle" ;
    // builder.append(columnNames+"\n");

    
