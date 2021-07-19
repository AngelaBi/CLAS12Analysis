package org.funp;
// import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
//import org.jlab.groot.data.*;
//import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
//import org.jlab.jnp.physics.*;
//import org.jlab.jnp.reader.*;

import org.funp.dvcs.*;
import org.funp.utilities.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
//import java.io.*;

public class tagevents
{
    static Event     event;
    static DvcsEvent ev;
    static HipoWriterSorted writer;
static int ndegamma;

    static HashMap<Integer, List<Double>> runMap; 

  public static void main( String[] args ) throws FileNotFoundException, IOException
  {
    runMap = runUtil.createMapGagikStyle();
    processInput inputParam=new processInput(args);
    //runUtil runInfo=new runUtil();

    //int ndeut=0;
    //int nphot=0;
    //int nelec=0;
    int totalcounter=0;
   ndegamma=0;
    //int dvcscounter=0;

    event = new Event();
    ev    = new DvcsEvent();


    //HashMap<Integer, Double> hmap=createrunmap();
    //HashMap<Integer, Double> hmap=runUtil.createrunmap();

    for (int i=0; i<inputParam.getNfiles(); i++) {
      String filename = new String(inputParam.getFileName(i));

      HipoReader reader = new HipoReader(); // Create a reader object
      System.out.println(filename);
      reader.open(filename); // open a file
      reader.getEvent(event,0); //Reads the first event and resets to the begining of the file

      Bank  runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
      event.read(runconfig);
      while (runconfig.getInt("run",0)==0) {
        reader.nextEvent(event);
        event.read(runconfig);
      }
      int runNumber=runconfig.getInt("run",0);
      //String filenumber = new String(filename.substring(inputParam.getFileName(i).length()-10,inputParam.getFileName(i).length()-5));


      //map beam energies
      if(runMap.get(runNumber)!=null){
        writer = new HipoWriterSorted();
      String outfilename = new String(inputParam.OutputLocation+ "/dst_edeut_" + Integer.toString(runNumber) + "_trimmed.hipo");
      writer.getSchemaFactory().copy(reader.getSchemaFactory());
      writer.open(outfilename);
	    //writer.open("/home/justind/DATA/dst_edeut_006467_trimmed.hipo");
      System.out.println(outfilename);
        ev.BeamEnergy = runMap.get(runNumber).get(2);
        System.out.println("Beam energy found is "+ev.BeamEnergy);
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);

        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());

    while(reader.hasNext()==true){
        Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
        Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
        Bank  scintExtras     = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
        Bank  calos = new Bank(reader.getSchemaFactory().getSchema("REC::Calorimeter"));
        Bank  runEvent       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));

        reader.nextEvent(event);
        event.read(particles);
	      event.read(scint);
        event.read(scintExtras);
        event.read(runEvent);

        totalcounter++;
        if (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) == 0.0){//all events are good
                
                  goodEventFilterParticles(particles,scint,runEvent,scintExtras,calos,runNumber);
      
              } else if (runMap.get(runNumber).get(0) ==0.0 && runMap.get(runNumber).get(1) != 0.0){//start from beginning and go until event number
                  if (runconfig.getInt("event",0)< runMap.get(runNumber).get(1)){
                      goodEventFilterParticles(particles,scint,runEvent,scintExtras,calos,runNumber);
      
                  }
              } else if (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) == 0.0){//start from event and go to end
                if (runconfig.getInt("event",0) > runMap.get(runNumber).get(0)){
                    goodEventFilterParticles(particles,scint,runEvent,scintExtras,calos,runNumber);
      
                }
              } else{
                if (runconfig.getInt("event",0) < runMap.get(runNumber).get(1) && runconfig.getInt("event",0) > runMap.get(runNumber).get(0)){//event min and event max
                    goodEventFilterParticles(particles,scint,runEvent ,scintExtras,calos,runNumber);
            
                }
              }
        
      //writer.addEvent(event,event.getEventTag());
      //System.out.println("my tag is " + event.getEventTag());
    }

        writer.close();
        System.out.println("total counter: " + totalcounter);
        System.out.println("total e d gamma events: " + ndegamma);
        reader.close();




      }
      else {
        System.out.println("Uknown beam energy for this run setting to default of so I am skipping this\n\n\n\n\n\n\n\n" );
      }


      
      
    }



}

public static void goodEventFilterParticles(Bank particles, Bank scint, Bank runEvent, Bank scintExtras, Bank calos,int runNumber){
    if(ev.FilterParticles(particles,scint,runEvent,scintExtras,calos,runNumber)){
          //if(((ev.beta()-ev.BetaCalc()) > (0.05*ev.chi2pid()-0.25))){
            
            if (ev.TagEventsDVCScut() && ev.TagEventsExclusivityCut()){
                event.setEventTag(11);
            }else if (ev.TagEventsDVCScut() && !ev.TagEventsExclusivityCut()){
                event.setEventTag(10);
            }else{
                event.setEventTag(9);
            }
            writer.addEvent(event,event.getEventTag());
            ndegamma++;
         
      }
}
  

}
