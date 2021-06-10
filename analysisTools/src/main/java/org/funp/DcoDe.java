package org.funp;
import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

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

public class DcoDe
{

    static Event     event;

    static DvcsEvent ev ;
    static DvcsHisto hNC ;//No cuts
    static DvcsHisto hNCFT ;//No cuts FT
    static DvcsHisto hNCFD ;//No cuts FD
    
    static DvcsHisto hDC ;//DVCS cuts
    static DvcsHisto hAC   ;//All cuts

    static DvcsHisto hDCFT ;//DVCS cuts conf 1
    static DvcsHisto hDCFD ;//All cuts conf 2

    static  DvcsHisto hACFT;//DVCS cuts conf 1
    static  DvcsHisto hACFD ;//All cuts conf 2

    //DvcsHisto hft     = new DvcsHisto();//Forward Tagger
    //DvcsHisto hfd     = new DvcsHisto();//Forward Detector
  
    //int times=0;

    static int ndvcs;
    static int ndegamma;
    static int counter;
    static int FDCounter;
    static  int FTCounter;
    //static List<List<String>> records ;
    static HashMap<Integer, List<Double>> runMap;

  public static void main( String[] args ) throws FileNotFoundException, IOException 
  {
    
    processInput inputParam=new processInput(args);
    //runUtil runInfo=new runUtil();
    

    //double beamenergy;

      event = new Event();
      ev    = new DvcsEvent();

      //NO CUTS 
      hNC     = new DvcsHisto();//No cuts
      hNC.setOutputDir(inputParam.getOutputDir());
      hNCFT     = new DvcsHisto();//No cuts
      hNCFT.setOutputDir(inputParam.getOutputDir());
      hNCFD     = new DvcsHisto();//No cuts
      hNCFD.setOutputDir(inputParam.getOutputDir());

      //DVCS CUTS
      hDC     = new DvcsHisto();//DVCS cuts
      hDC.setOutputDir(inputParam.getOutputDir());
      hDCFT     = new DvcsHisto();//DVCS cuts conf 1
      hDCFT.setOutputDir(inputParam.getOutputDir());
      hDCFD     = new DvcsHisto();//All cuts conf 2
      hDCFD.setOutputDir(inputParam.getOutputDir());

      //ALL CUTS
      hAC     = new DvcsHisto();//All cuts
      hAC.setOutputDir(inputParam.getOutputDir());
      hACFT     = new DvcsHisto();//DVCS cuts conf 1
      hACFT.setOutputDir(inputParam.getOutputDir());
      hACFD     = new DvcsHisto();//All cuts conf 2
      hACFD.setOutputDir(inputParam.getOutputDir());

    //DvcsHisto hft     = new DvcsHisto();//Forward Tagger
    //DvcsHisto hfd     = new DvcsHisto();//Forward Detector
  
    //int times=0;

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
      HipoReader reader = new HipoReader();
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
      
     
      if (runMap.get(runNumber)!=null){

        ev.BeamEnergy = runMap.get(runNumber).get(2);
        System.out.println("Beam energy found is "+ev.BeamEnergy);
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);
  
        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
        //this loops over the events
        while(reader.hasNext()==true){
          Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
          Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
          Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
          Bank  scintExtras     = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));
          Bank  hel       = new Bank(reader.getSchemaFactory().getSchema("HEL::online"));
          //runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));


          reader.nextEvent(event);
          event.read(particles);
          event.read(scint);
          event.read(scintExtras);
          event.read(hel);
          event.read(runconfig);
          //System.out.println(" Current event number " + runconfig.getInt("event",0));
         
        //goodEventFilterParticles(particles,scint,hel,scintExtras);
                
          
          if (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) == 0.0){//all events are good
            
              goodEventFilterParticles(particles,scint,hel,scintExtras);
  
          } else if (runMap.get(runNumber).get(0) ==0.0 && runMap.get(runNumber).get(1) != 0.0){//start from beginning and go until event number
              if (runconfig.getInt("event",0)< runMap.get(runNumber).get(1)){
                  goodEventFilterParticles(particles,scint,hel,scintExtras);
  
              }
          } else if (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) == 0.0){//start from event and go to end
            if (runconfig.getInt("event",0) > runMap.get(runNumber).get(0)){
                goodEventFilterParticles(particles,scint,hel,scintExtras);
  
            }
          } else{
            if (runconfig.getInt("event",0) < runMap.get(runNumber).get(1) && runconfig.getInt("event",0) > runMap.get(runNumber).get(0)){//event min and event max
                goodEventFilterParticles(particles,scint,hel,scintExtras);
        
            }
          }
      
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
    //TCanvas ec = new TCanvas("Before cuts",1200,1000);
    //hNC.DrawBasic( ec);
    //TCanvas ec2 = new TCanvas("After DVCS cuts",1200,1000);
    //hDC.DrawBasic( ec2);

    boolean showNOCUT_kinematics_ALL = true;
    boolean showNOCUT_kinematics_FT = false;
    boolean showNOCUT_kinematics_FD = false;

    boolean showNOCUT_missing_quants_ALL=false;
    boolean showNOCUT_missing_quants_FT=false;
    boolean showNOCUT_missing_quants_FD=false;

    boolean showDVCS_kinematics_All = false;
    boolean showDVCS_kinematics_FT = false;
    boolean showDVCS_kinematics_FD = false;

    boolean showDVCS_missing_quants_ALL = false;
    boolean showDVCS_missing_quants_FT = false;
    boolean showDVCS_missing_quants_FD = false;

    boolean showExcl_kinematicss_ALL = false;
    boolean showExcl_kinematics_FT = false;
    boolean showExcl_kinematics_FD = false;

    boolean showExcl_missing_quants_ALL = false;
    boolean showExcl_missing_quants_FT = false;
    boolean showExcl_missing_quants_FD = false;

    boolean showParticleComparison_NO_CUTS = true;
    boolean showParticleComparison_DVCS_CUTS = true;
    boolean showParticleComparison_Excl_CUTS = true;


    boolean showConeAngle_NO_CUTS = false;
    boolean showConeAngle_DVCS_CUTS = false;
    boolean showConeAngle_Excl_CUTS = false;


    boolean showAsymm_All = true;
    boolean showAsymm_FT = true;
    boolean showAsymm_FD = true;

    if (showParticleComparison_NO_CUTS){
      TCanvas ecNC = new TCanvas("Particle Comparsion No Cuts",1500,1500);
      hNC.DrawParticleComparison(ecNC);
    }
    if (showParticleComparison_DVCS_CUTS){
      TCanvas ecDC = new TCanvas("Particle Comparsion DVCS Cuts",1500,1500);
      hDC.DrawParticleComparison(ecDC);
    }
    if (showParticleComparison_NO_CUTS){
      TCanvas ecAC = new TCanvas("Particle Comparsion Excl Cuts",1500,1500);
      hAC.DrawParticleComparison(ecAC);
    }


    if (showDVCS_missing_quants_ALL){
        TCanvas ec4 = new TCanvas("Excl after DVCS cuts",1500,1500);
        hDC.DrawMissingQuants(ec4);
    }
    
    if (showDVCS_missing_quants_FT){
        TCanvas ec40 = new TCanvas("Excl after DVCS cuts FT",1500,1500);
        hDCFT.DrawMissingQuants(ec40);
    }
    if (showDVCS_missing_quants_FD){
        TCanvas ec401 = new TCanvas("Excl after DVCS cuts FT",1500,1500);
        hDCFD.DrawMissingQuants(ec401);
    }

    if (showExcl_missing_quants_ALL){
        TCanvas ec5 = new TCanvas("Excl after DVCS and exc cuts",1500,1500);
        hAC.DrawMissingQuants(ec5);
    }
    
    if (showNOCUT_kinematics_ALL){
      TCanvas ec6 = new TCanvas("No Cuts All Kinematics",1200,1000);
      hNC.DrawKinematics(ec6);
    }

    if (showNOCUT_kinematics_FT){
      TCanvas ec66 = new TCanvas("No Cuts FT Kinematics",1200,1000);
      hNCFT.DrawKinematics(ec66);
    }
    
    if (showNOCUT_kinematics_FD){
        TCanvas ec666 = new TCanvas("No Cuts FD Kinematics",1200,1000);
        hNCFD.DrawKinematics(ec666);
    }
    
    if (showDVCS_kinematics_All){
      TCanvas ec7 = new TCanvas("DVCS Cuts All Kinematics",1200,1000);
      hDC.DrawKinematics(ec7);
    }

    if (showDVCS_kinematics_FT){
      TCanvas ec77 = new TCanvas("DVCS Cuts FT Kinematics",1200,1000);
      hDCFT.DrawKinematics(ec77);
    }

    if (showDVCS_kinematics_FD){
      TCanvas ec777 = new TCanvas("DVCS Cuts FD Kinematics",1200,1000);
      hDCFD.DrawKinematics(ec777);
    }

   if (showExcl_kinematicss_ALL){
      TCanvas ec8 = new TCanvas("DVCS and Excl Cuts All Kinematics",1200,1000);
      hAC.DrawKinematics(ec8);
   }

   if (showExcl_kinematics_FT){
    TCanvas ec88 = new TCanvas("DVCS and Excl Cuts FT Kinematics",1200,1000);
    hACFT.DrawKinematics(ec88);//changed this line
   }

   if (showExcl_kinematics_FD){
    TCanvas ec89 = new TCanvas("DVCS and Excl Cuts FD Kinematics",1200,1000);
    hACFD.DrawKinematics(ec89);//changed this line
   }
    
    if (showConeAngle_NO_CUTS){
      TCanvas ec9 = new TCanvas("AllNoCuts ConeAngle",1200,1000);
      hNC.DrawConeAngle(ec9);
    }

    if (showConeAngle_DVCS_CUTS){
      TCanvas ec10 = new TCanvas("AllDVCSCuts ConeAngle",1200,1000);
      hDC.DrawConeAngle(ec10);
    }

    if (showConeAngle_Excl_CUTS){
      TCanvas ec11 = new TCanvas("AllDVCSexcCuts ConeAngle",1200,1000);
      hAC.DrawConeAngle(ec11);
    }
    
    
    if (showAsymm_All){
      TCanvas ecA = new TCanvas("Asymmetry",1200,1200);
      hAC.drawAsym(ecA);
    }

    if (showAsymm_FT){
      TCanvas ecAsymFT = new TCanvas("Asymmetry FT",1200,1200);
      hACFT.drawAsym(ecAsymFT);
    }

    if (showAsymm_FD){
      TCanvas ecAsymFD = new TCanvas("Asymmetry FD",1200,1200);
      hACFD.drawAsym(ecAsymFD);
    }
    


    //How to plot the FT and FD Asymmetries
    

    

    /*TCanvas ecP = new TCanvas("Plotdvcscuts",1800,1200);
    ecP.divide(2,1);
    ecP.cd(0);
    hDC.drawPlot1(ecP);
    ecP.cd(1);
    hAC.drawPlot1(ecP);
    ecP.cd(2);
    hDC.drawPlot3(ecP);
    ecP.cd(3);
    hDC.drawPlot4(ecP);
    ecP.cd(4);
    hDC.drawPlot5(ecP);
    //ecP.cd(5);
    //hDC.drawPlot6(ecP);




    TCanvas ecP2 = new TCanvas("Plotallcuts",900,9000);
    ecP2.divide(1,2);
    ecP2.cd(0);
    hAC.drawPlot1(ecP2);
    ecP2.cd(1);
    hAC.drawPlot2(ecP2);



    TCanvas ecP3 = new TCanvas("Plotexclcuts",1200,1200);
    //hNC.drawPlot1(ecP);
    hAC.drawPlot(ecP3);*/




    //TCanvas ec7 = new TCanvas("call2",1200,1000);
}

public static void goodEventFilterParticles(Bank particles, Bank scint, Bank hel, Bank scintExtras){
  //System.out.println("we have entered the method");
  
  if(ev.FilterParticles(particles,scint,hel,scintExtras)){
    hNC.fillBasicHisto(ev);
    if (ev.GetConf()==1){
      hNCFT.fillBasicHisto(ev);
    }else if (ev.GetConf()==2){
      hNCFD.fillBasicHisto(ev);
    }
    ndegamma++;
    if(ev.DVCScut()){
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
      if( ev.Exclusivitycut()) {
        //&& (ev.X("ehg").e()<2) && (ev.X("ehg").pz()<0.8)
        hAC.fillBasicHisto(ev);
        if (ev.GetConf()==1){
          hACFT.fillBasicHisto(ev);
          FTCounter++;
        }
        else if (ev.GetConf()==2){
          hACFD.fillBasicHisto(ev);
          FDCounter++;
        }
        counter++;
      }
    }
  }
}//end of goodEventFilterParticle

}
