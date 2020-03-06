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

public class PositivesStudies
  {
    public static void main( String[] args )
    {

      processInput inputParam=new processInput(args);

      Event     event = new Event();
      DvcsEvent ev    = new DvcsEvent();
      DvcsHisto hNC     = new DvcsHisto();//No cuts
      DvcsHisto hDC     = new DvcsHisto();//DVCS cuts
      DvcsHisto hAC     = new DvcsHisto();//All cuts

      HashMap<Integer, Double> hmap=createrunmap();

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


        if(ev.FilterPositives(particles,scint)){
          hNC.fillPositives(ev);
        }
      }
    }
    TCanvas ec = new TCanvas("Before cuts",1200,1000);
    hNC.drawPositives(ec);
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
