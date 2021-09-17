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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MC
{

    static Event     event;

    static MCEvent ev ;
    static MCHisto hNC ;//No cuts

  
    static int ndvcs;
    static int ndegamma;
    static int counter;
    static int FDCounter;
    static  int FTCounter;
    
   
    //static List<List<String>> records ;
    static HashMap<Integer, List<Double>> runMap;
    static TDirectory dir;
    static int nelec_MC;
    static int nphot_MC;
    static int ndeut_MC;
    static int nother_MC;
    static int ne_MC;
    static int ng_MC;
    static int nd_MC;
   
    static int PIDNUC;
    static double MNUC;


  public static void main( String[] args ) throws FileNotFoundException, IOException 
  {
    PIDNUC=45;
    MNUC=1.875612;
    processInput inputParam=new processInput(args);
    //runUtil runInfo=new runUtil();
    dir = new TDirectory();
    dir.mkdir("/test");

   
      event = new Event();
      ev    = new MCEvent();
      double BeamEnergy = 10.3;
      //NO CUTS 
      hNC     = new MCHisto();//No cuts
      hNC.setOutputDir(inputParam.getOutputDir());
   
      ndvcs=0;
      ndegamma=0;
      counter=0;
    


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
      
     
    

        BeamEnergy = 10.3;
        System.out.println("Beam energy found is "+BeamEnergy);
        
  
        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
        //this loops over the events
        while(reader.hasNext()==true){
          Bank  lund = new Bank(reader.getSchemaFactory().getSchema("MC::Lund"));
          reader.nextEvent(event);
          event.read(lund);
        
         //System.out.println("here");
         if(ev.MCFilterParticles(lund)){
          hNC.fillBasicHisto(ev);
         }       
          
     
      
        }
        reader.close();

    
    
  }//end of the loop over files
  
    //if(counter==0)break;
    //counter--;
    TCanvas ec114 = new TCanvas("Excl after No cuts",1500,1500);
    hNC.DrawMissingQuants(ec114);

    TCanvas ec115 = new TCanvas("Momentum Distributions",1500,1500);
    hNC.DrawMomentums(ec115);

     TCanvas ec116 = new TCanvas("Momentum Distributions",1500,1500);
    hNC.DrawKinematics(ec116);

    System.out.println("total dvcs events: " + ndvcs);
    System.out.println("total deuteron gamma electron events : " + ndegamma);
   
    // System.out.println("total helicity+: " + ev.helicityplus);
    // System.out.println("total helicity-: " + ev.helicityminus);
    System.out.println("total events after excl cuts: " + counter);
  
  

   


}//end main method




  

 
  





}
