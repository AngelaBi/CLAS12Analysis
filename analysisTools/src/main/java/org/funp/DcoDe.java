package org.funp;
import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
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
import org.jlab.groot.data.TDirectory;
import java.io.File;
import java.io.PrintWriter;

public class DcoDe
{

    static Event     event;

    static DvcsEvent ev ;

    static DvcsHisto hNC ;//No cuts
    static DvcsHisto hNCFT ;//No cuts FT
    static DvcsHisto hNCFD ;//No cuts FD
    
    static DvcsHisto hDC ;//DVCS cuts
    static DvcsHisto hDCFT ;//DVCS cuts conf 1
    static DvcsHisto hDCFD ;//DVCS cuts conf 2

    static DvcsHisto hCC ;//coneangle MM 2d cut
    static DvcsHisto hCCFT;//coneangle MM 2d cut conf 1
    static DvcsHisto hCCFD ;//coneangle MM 2d cut conf 2
    
    static DvcsHisto hAC   ;//All cuts
    static DvcsHisto hACFT;//All cuts conf 1
    static DvcsHisto hACFD ;//All cuts conf 2

  

    static TDirectory dir;
    static TDirectory rootdir;
    
    static int ndvcs;
    static int ndegamma;
    static int counter;
    static int FDCounter;
    static  int FTCounter;



    // static int beforefid;
    // static int afterfid;
    //static List<List<String>> records ;
    static HashMap<Integer, List<Double>> runMap;
    
    static StringBuilder builder;
   

  public static void main( String[] args ) throws FileNotFoundException, IOException 
  {
    

    // PrintWriter pw = null;
    // try{
    //   pw = new PrintWriter(new File("Data.csv"));
    // }catch (FileNotFoundException e){
    //   e.printStackTrace();
    // }

    // builder = new StringBuilder();
    // String columnNames = "dedx,momentum,particle" ;
    // builder.append(columnNames+"\n");

    int goodEvent;
    int counter11 = 0;
    System.out.println("\n Processing arguments \n");
    processInput inputParam=new processInput(args);
    //runUtil runInfo=new runUtil();
    



      event = new Event();
      ev    = new DvcsEvent();
      //ev.setArgs(args);
      ev.isML(processInput.getMLmode());
      if(ev.isML) ev.makecsv();
      //NO CUTS 
      hNC     = new DvcsHisto();//No cuts
      hNC.setOutputDir(inputParam.getOutputDir());
      hNCFT     = new DvcsHisto();//No cuts
      hNCFT.setOutputDir(inputParam.getOutputDir());
      hNCFD     = new DvcsHisto();//No cuts
      hNCFD.setOutputDir(inputParam.getOutputDir());
      dir = new TDirectory();
      rootdir = new TDirectory();
      //DVCS CUTS
      hDC     = new DvcsHisto();//DVCS cuts
      hDC.setOutputDir(inputParam.getOutputDir());
      hDCFT     = new DvcsHisto();//DVCS cuts conf 1
      hDCFT.setOutputDir(inputParam.getOutputDir());
      hDCFD     = new DvcsHisto();//All cuts conf 2
      hDCFD.setOutputDir(inputParam.getOutputDir());
      //Coneangle 2D cuts
      hCC     = new DvcsHisto();//DVCS cuts
      hCC.setOutputDir(inputParam.getOutputDir());
      hCCFT     = new DvcsHisto();//DVCS cuts conf 1
      hCCFT.setOutputDir(inputParam.getOutputDir());
      hCCFD     = new DvcsHisto();//All cuts conf 2
      hCCFD.setOutputDir(inputParam.getOutputDir());
      //ALL CUTS
      hAC     = new DvcsHisto();//All cuts
      hAC.setOutputDir(inputParam.getOutputDir());
      hACFT     = new DvcsHisto();//DVCS cuts conf 1
      hACFT.setOutputDir(inputParam.getOutputDir());
      hACFD     = new DvcsHisto();//All cuts conf 2
      hACFD.setOutputDir(inputParam.getOutputDir());

    

      ndvcs=0;
      ndegamma=0;
      counter=0;
      FDCounter = 0;
      FTCounter = 0;

    //HashMap<Integer, Double> hmap=createrunmap();
    //Why calling createrunmap using the class name and not object
    // HashMap<Integer, Double> hmap=runUtil.createrunmap();

    //this is me making a hashmap of the runs from the txtfile
    // structure of hashmap
    // runnumber:firstevent, lastevent, beamenergy
    
    runMap = runUtil.createMapGagikStyle();

    for (int i=0; i<inputParam.getNfiles(); i++) {
      goodEvent=0;
      HipoReader reader = new HipoReader();
      if(!inputParam.getMCmode() && !inputParam.getnTmode()) reader.setTags(9,10,11);
      reader.open(inputParam.getFileName(i));
      System.out.println(inputParam.getFileName(i));
      reader.getEvent(event,0); //Reads the first event and resets to the begining of the file
      Bank  runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
          //runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
      event.read(runconfig);
      while (runconfig.getInt("run",0)==0) {
        reader.nextEvent(event);
        event.read(runconfig);
      }
      System.out.println("Reading run :"+runconfig.getInt("run",0));
      int runNumberIndex = -1;
      boolean runFound = false;
      int runNumber=runconfig.getInt("run",0);
      if (runMap.get(runNumber)!=null || inputParam.getMCmode()){//This if will keep runs in the map or MC
        //BEam energy from the file or set by hand for the MC        
        //right now the beam energy for the MC is hardcoded
        if(runMap.get(runNumber)!=null )
          ev.BeamEnergy = runMap.get(runNumber).get(2);
        else if (inputParam.getMCmode())
          ev.BeamEnergy =10.6; 

        System.out.println("Beam energy found is "+ev.BeamEnergy);
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);
  
        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
        //this loops over the events
        while(reader.hasNext()==true){
          Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
          Bank  runEvent       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
          Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
          Bank  scintExtras     = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
          Bank  calos = new Bank(reader.getSchemaFactory().getSchema("REC::Calorimeter"));
          //runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
          goodEvent=0;

          reader.nextEvent(event);
          event.read(particles);
          event.read(scint);
          event.read(scintExtras);
          //event.read(hel);
          event.read(runconfig);
          event.read(calos);
          event.read(runEvent);
          //System.out.println(" Current event number " + runconfig.getInt("event",0));
         
          //goodEventFilterParticles(particles,scint,hel,scintExtras);
          boolean allEventsGood = false;
          boolean beginningEventsGood = false;
          boolean endEventsGood = false;
          boolean neitherEventsGood = false; 
          if (!inputParam.getMCmode()){
          allEventsGood= (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) == 0.0); 
          beginningEventsGood = (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) != 0.0);
          endEventsGood = (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) == 0.0);
          neitherEventsGood = (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) != 0.0);
          }
            
          

          
          if( 
            (inputParam.getMCmode() || 
             ((event.getEventTag()==11 || event.getEventTag()==10 || event.getEventTag()==9) 
             || inputParam.getnTmode() )&& 
            //((event.getEventTag()==11 ) && 
            (allEventsGood ||  /*//all events are good */ 
            (beginningEventsGood && (runconfig.getInt("event",0)< runMap.get(runNumber).get(1))) || 
            (endEventsGood && (runconfig.getInt("event",0) > runMap.get(runNumber).get(0))) ||
            (neitherEventsGood && runconfig.getInt("event",0) < runMap.get(runNumber).get(1) && runconfig.getInt("event",0) > runMap.get(runNumber).get(0))
            )          
            )
            )
            {
              goodEvent=1;
            }
          if(goodEvent==1)
            goodEventFilterParticles(particles,scint,runEvent,scintExtras,calos,runNumber); 
               
        
          
      
        }
        reader.close();


      }

      else{
            System.out.println("THIS RUN IS BEING SKIPPED \n\n\n\n\n");
      }      
    
  }//end of the loop over files
  
    //if(counter==0)break;
    //counter--;

    System.out.println("total dvcs events: " + ndvcs);
    System.out.println("total deuteron gamma electron events : " + ndegamma);
    System.out.println("total deuteron event : " + ev.tmpdeut);
    System.out.println("total deuteron event with CTOF info: " + ev.tmpdeutctof);
    System.out.println("total deuteron event with no CTOF info: " + ev.tmpdeutnoctof);
    System.out.println("total deuteron event with CND info: " + ev.tmpdeutcnd);
    System.out.println("total helicity+: " + ev.helicityplus);
    System.out.println("total helicity-: " + ev.helicityminus);
    System.out.println("total events after excl cuts: " + counter);
    System.out.println("total events after excl cuts in FT: " +FTCounter);
    System.out.println("total events after excl cuts in FD: " + FDCounter);
    //System.out.println("number before fid"+ev.beforeFidCut);
    //System.out.println("number after fid" + afterfid);
    System.out.println("number of 11 = " + counter11);

    
    hNC.writeHipooutput(rootdir,"NC");
    hNCFD.writeHipooutput(rootdir,"NCFD");
    hNCFT.writeHipooutput(rootdir,"NCFT");
    hCC.writeHipooutput(rootdir,"CC");
    hCCFD.writeHipooutput(rootdir,"CCFD");
    hCCFT.writeHipooutput(rootdir,"CCFT");
    hDC.writeHipooutput(rootdir,"DC");
    hDCFD.writeHipooutput(rootdir,"DCFD");
    hDCFT.writeHipooutput(rootdir,"DCFT");
    hAC.writeHipooutput(rootdir,"AC");
    hACFD.writeHipooutput(rootdir,"ACFD");
    hACFT.writeHipooutput(rootdir,"ACFT");  
    rootdir.writeFile(inputParam.OutputLocation + "/"+inputParam.gethipoFile()); 
   

    if (ev.isML){
      ev.pw.write(ev.builder.toString());
      ev.pw.close();
    }
    
}

public static void goodEventFilterParticles(Bank particles, Bank scint, Bank runEvent, Bank scintExtras, Bank calos,int runNumber){
  
  if(ev.FilterParticles(particles,scint,runEvent,scintExtras,calos,runNumber)){
    hNC.fillBasicHisto(ev);
    //beforefid++;
    // if (ev.X("eh").mass2() > (-20.0/6.0* ev.coneangle()+10)){
    //   System.out.println(event.getEventTag());
    // }
    
    if (ev.GetConf()==1){
      hNCFT.fillBasicHisto(ev);
    }else if (ev.GetConf()==2){
      hNCFD.fillBasicHisto(ev);
    }
    ndegamma++;
    if(ev.DVCScut()){
      
      //afterfid++;
      ndvcs++;
      //if(vMMass.mass2()>-1 && vMMass.mass2()<1 && (vphoton.theta()*180./Math.PI)<5){
      //    MMom.fill(vMMom.p());
      hDC.fillBasicHisto(ev);
      if (ev.GetConf()==1){
        hDCFT.fillBasicHisto(ev);
      }
      else if (ev.GetConf()==2){
        hDCFD.fillBasicHisto(ev);
      }
      //Math.abs(ev.X("eh").mass2())<3  && ev.X("ehg").e()<1 (Math.toDegrees(ev.vphoton.theta())<5) &&  Math.abs(ev.X("ehg").e())<2 && (Math.toDegrees(ev.vphoton.theta())<5)   Math.abs(ev.deltaPhiPlane2())<20 (ev.beta()-ev.BetaCalc())>-0.3  &&  Math.abs(ev.deltaPhiPlane())<1 &&  && (ev.beta()-ev.BetaCalc())>-0.3
      if(ev.PrelimExclusivitycut()){
        if (ev.GetConf()==1){
          hCCFT.fillBasicHisto(ev);
        }else if (ev.GetConf()==2){
          hCCFD.fillBasicHisto(ev);
        }
      if( ev.Exclusivitycut(runNumber)) {
        //&& (ev.X("ehg").e()<2) && (ev.X("ehg").pz()<0.8)
        hAC.fillBasicHisto(ev);
        
        if (ev.GetConf()==1){
          hACFT.fillBasicHisto(ev);
          FTCounter++;






        }
        else if (ev.GetConf()==2){
          hACFD.fillBasicHisto(ev);
          FDCounter++;
          // if (-ev.Q().mass2()>1.5){
          //   BinnedHACFD.fillBasicHisto(ev);//?????????
          // }
        }
        counter++;
      }
    }
    }
  
  }
}//end of goodEventFilterParticle

}


  