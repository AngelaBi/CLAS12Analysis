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


public class DcoDe
{

  public static void main( String[] args )
  {
    processInput inputParam=new processInput(args);

    double beamenergy;

    Event     event = new Event();

    DvcsEvent ev    = new DvcsEvent();
    DvcsHisto hNC     = new DvcsHisto();//No cuts
    DvcsHisto hDC     = new DvcsHisto();//DVCS cuts
    DvcsHisto hAC     = new DvcsHisto();//All cuts
    DvcsHisto hft     = new DvcsHisto();//Forward Tagger
    DvcsHisto hfd     = new DvcsHisto();//Forward Detector
    int times=0;

    int ndvcs=0;

    int counter=0;

    HashMap<Integer, Double> hmap=createrunmap();



    //System.out.println(hmap.get(6310));

    for ( int i=0; i<inputParam.getNfiles(); i++) {
      HipoReader reader = new HipoReader();
      reader.open(inputParam.getFileName(i));
      System.out.println(inputParam.getFileName(i));
      reader.getEvent(event,0); //Reads the first event and resets to the begining of the file
      Bank  runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
      event.read(runconfig);
      if(hmap.get(runconfig.getInt("run",0))!=null){
        ev.BeamEnergy=hmap.get(runconfig.getInt("run",0));
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);

        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
      }
      else {
        System.out.println("Uknown beam energy for this run setting to default of"+ev.vBeam.e() );
      }
    //loop over the events
    while(reader.hasNext()==true){
      Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
      Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
      Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
      Bank  hel       = new Bank(reader.getSchemaFactory().getSchema("HEL::online"));


      reader.nextEvent(event);
      event.read(particles);
      event.read(scint);
      event.read(hel);




      if(ev.FilterParticles(particles,scint,hel)){
        hNC.fillBasicHisto(ev);
        if(ev.DVCScut()){
          ndvcs++;
          //if(vMMass.mass2()>-1 && vMMass.mass2()<1 && (vphoton.theta()*180./Math.PI)<5){
          //    MMom.fill(vMMom.p());
          hDC.fillBasicHisto(ev);
          //Math.abs(ev.X("eh").mass2())<3  && ev.X("ehg").e()<1 (Math.toDegrees(ev.vphoton.theta())<5) &&  Math.abs(ev.X("ehg").e())<2 && (Math.toDegrees(ev.vphoton.theta())<5)   Math.abs(ev.deltaPhiPlane2())<20 (ev.beta()-ev.BetaCalc())>-0.3  &&  Math.abs(ev.deltaPhiPlane())<1 &&  && (ev.beta()-ev.BetaCalc())>-0.3
          if( ev.Exclusivitycut() ) {
            //&& (ev.X("ehg").e()<2) && (ev.X("ehg").pz()<0.8)
            hAC.fillBasicHisto(ev);
            counter++;
          }
        }
      }
    }
    reader.close();
  }
    //if(counter==0)break;
    //counter--;

    System.out.println("total dvcs events: " + ndvcs);
    System.out.println("total deuteron event : " + ev.tmpdeut);
    System.out.println("total deuteron event with CTOF info: " + ev.tmpdeutctof);
    System.out.println("total deuteron event with no CTOF info: " + ev.tmpdeutnoctof);
    System.out.println("total deuteron event with CND info: " + ev.tmpdeutcnd);
    System.out.println("total helicity+: " + ev.helicityplus);
    System.out.println("total helicity-: " + ev.helicityminus);
    System.out.println("total events after excl cuts: " + counter);
    //TCanvas ec = new TCanvas("Before cuts",1200,1000);
    //hNC.DrawBasic( ec);
    //TCanvas ec2 = new TCanvas("After DVCS cuts",1200,1000);
    //hDC.DrawBasic( ec2);

    TCanvas ec4 = new TCanvas("Excl after DVCS cuts",1500,1500);
    hDC.DrawMissing(ec4);

    TCanvas ec5 = new TCanvas("Excl after DVCS and exc cuts",1500,1500);
    hAC.DrawMissing(ec5);

    TCanvas ec6 = new TCanvas("AllNoCuts",1200,1000);
    hNC.DrawAll(ec6);
    TCanvas ec7 = new TCanvas("AllDVCSCuts",1200,1000);
    hDC.DrawAll(ec7);
    TCanvas ec8 = new TCanvas("AllDVCSexcCuts",1200,1000);
    hAC.DrawAll(ec8);

    TCanvas ec9 = new TCanvas("AllNoCuts",1200,1000);
    hNC.DrawAll2(ec9);
    TCanvas ec10 = new TCanvas("AllDVCSCuts",1200,1000);
    hDC.DrawAll2(ec10);
    TCanvas ec11 = new TCanvas("AllDVCSexcCuts",1200,1000);
    hAC.DrawAll2(ec11);

    TCanvas ecA = new TCanvas("Asymmetry",1200,1200);
    hAC.drawAsym(ecA);

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

  static HashMap<Integer, Double> createrunmap(){
    HashMap<Integer, Double> hmap = new HashMap<Integer, Double>();
    Double beam10p6=10.5986;
    Double beam10p2=10.1998;
    //5nA runs
        hmap.put(6226,beam10p6);
        hmap.put(6322,beam10p6);
        hmap.put(6323,beam10p6);
        hmap.put(6371,beam10p6);
        hmap.put(6373,beam10p6);
        hmap.put(6374,beam10p6);
        hmap.put(6446,beam10p2);
        hmap.put(6447,beam10p2);
        hmap.put(6448,beam10p2);
    //regular runs
        hmap.put(6303,beam10p6);
        hmap.put(6305,beam10p6);
        hmap.put(6307,beam10p6);
        hmap.put(6310,beam10p6);
        hmap.put(6311,beam10p6);
        hmap.put(6313,beam10p6);
        hmap.put(6321,beam10p6);
        hmap.put(6326,beam10p6);
        hmap.put(6327,beam10p6);
        hmap.put(6328,beam10p6);
        hmap.put(6346,beam10p6);
        hmap.put(6347,beam10p6);
        hmap.put(6349,beam10p6);

        hmap.put(6420,beam10p2);
        hmap.put(6428,beam10p2);
        hmap.put(6433,beam10p2);
        hmap.put(6442,beam10p2);
        hmap.put(6450,beam10p2);
        hmap.put(6467,beam10p2);
        hmap.put(6474,beam10p2);
        hmap.put(6481,beam10p2);
        hmap.put(6492,beam10p2);
        hmap.put(6501,beam10p2);
        hmap.put(6515,beam10p2);
        hmap.put(6522,beam10p2);
        hmap.put(6524,beam10p2);
        hmap.put(6546,beam10p2);
        hmap.put(6559,beam10p2);
        hmap.put(6571,beam10p2);
        hmap.put(6586,beam10p2);
        hmap.put(6595,beam10p2);

    return hmap;

  }


}
