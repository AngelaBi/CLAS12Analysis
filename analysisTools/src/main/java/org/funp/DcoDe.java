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



    Event     event = new Event();

    DvcsEvent ev    = new DvcsEvent();
    DvcsHisto hNC     = new DvcsHisto();//No cuts
    DvcsHisto hDC     = new DvcsHisto();//DVCS cuts
    DvcsHisto hAC     = new DvcsHisto();//All cuts
    int times=0;

    int ndvcs=0;

    int counter=0;

    for ( int i=0; i<inputParam.getNfiles(); i++) {
      HipoReader reader = new HipoReader();
      reader.open(inputParam.getFileName(i));
      System.out.println(inputParam.getFileName(i));
      reader.getEvent(event,0); //Reads the first event and resets to the begining of the file


    //loop over the events
    while(reader.hasNext()==true){
      Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
      Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
      Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
      reader.nextEvent(event);
      event.read(particles);
      event.read(scint);

      if(ev.FilterParticles(particles,scint)){
        hNC.fillBasicHisto(ev);
        if(ev.DVCScut()){
          ndvcs++;
          if(vMMass.mass2()>-1 && vMMass.mass2()<1){
          //    MMom.fill(vMMom.p());
          hDC.fillBasicHisto(ev);
          //Math.abs(ev.X("eh").mass2())<3   (Math.toDegrees(ev.vphoton.theta())<5) && (Math.toDegrees(ev.vphoton.theta())<5)   Math.abs(ev.deltaPhiPlane2())<20   &&  Math.abs(ev.deltaPhiPlane())<1 &&  && (ev.beta()-ev.BetaCalc())>-0.3 &&
          if(   ev.coneangle()<3  && Math.abs(ev.X("ehg").mass2())<1  ){
            //&& ev.X("ehg").e()<2
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
    TCanvas ec = new TCanvas("Before cuts",1200,1000);
    hNC.DrawBasic( ec);
    TCanvas ec2 = new TCanvas("After DVCS cuts",1200,1000);
    hDC.DrawBasic( ec2);

    TCanvas ec4 = new TCanvas("Excl after DVCS cuts",1500,1500);
    hDC.DrawMissing(ec4);

    TCanvas ec5 = new TCanvas("Excl after DVCS and exc cuts",1500,1500);
    hAC.DrawMissing(ec5);

    TCanvas ec6 = new TCanvas("AllNoCuts",1200,1000);
    hNC.DrawAll(ec6);
    TCanvas ec7 = new TCanvas("AllDVCSCuts",1200,1000);
    hDC.DrawAll(ec7);
    //TCanvas ec7 = new TCanvas("call2",1200,1000);






}


}
