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

import java.util.*;
//import java.io.*;

public class tagevents
{
  public static void main( String[] args )
  {

    processInput inputParam=new processInput(args);
    //runUtil runInfo=new runUtil();

    //int ndeut=0;
    //int nphot=0;
    //int nelec=0;
    int totalcounter=0;
    int ndegamma=0;
    //int dvcscounter=0;

    Event     event = new Event();
    DvcsEvent ev    = new DvcsEvent();


    //HashMap<Integer, Double> hmap=createrunmap();
    HashMap<Integer, Double> hmap=runUtil.createrunmap();

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


      
      
      HipoWriterSorted writer = new HipoWriterSorted();
      String outfilename = new String(inputParam.OutputLocation+ "/dst_edeut_" + Integer.toString(runNumber) + "_trimmed.hipo");
      writer.getSchemaFactory().copy(reader.getSchemaFactory());
      writer.open(outfilename);
	    //writer.open("/home/justind/DATA/dst_edeut_006467_trimmed.hipo");
      System.out.println(outfilename);
      

      //map beam energies
      if(hmap.get(runNumber)!=null){
        ev.BeamEnergy=hmap.get(runconfig.getInt("run",0));
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);

        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
      }
      else {
        System.out.println("Uknown beam energy for this run setting to default of"+ev.vBeam.e() );
      }


      while(reader.hasNext()==true){
        Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
        Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
        Bank  hel     = new Bank(reader.getSchemaFactory().getSchema("HEL::online"));
        Bank  scintExtras     = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));

        reader.nextEvent(event);
        event.read(particles);
	    event.read(scint);
        event.read(scintExtras);

        totalcounter++;

        if(ev.FilterParticles(particles,scint,hel,scintExtras)){
          //if(((ev.beta()-ev.BetaCalc()) > (0.05*ev.chi2pid()-0.25))){
            writer.addEvent(event);
            ndegamma++;
            //dvcscounter++;
          //}
          /*hNC.fillBasicHisto(ev);
          if(ev.DVCScut()){
            //ndvcs++;
            //if(vMMass.mass2()>-1 && vMMass.mass2()<1 && (vphoton.theta()*180./Math.PI)<5){
            //    MMom.fill(vMMom.p());
            hDC.fillBasicHisto(ev);
            //Math.abs(ev.X("eh").mass2())<3  && ev.X("ehg").e()<1 (Math.toDegrees(ev.vphoton.theta())<5) &&  Math.abs(ev.X("ehg").e())<2 && (Math.toDegrees(ev.vphoton.theta())<5)   Math.abs(ev.deltaPhiPlane2())<20 (ev.beta()-ev.BetaCalc())>-0.3  &&  Math.abs(ev.deltaPhiPlane())<1 &&  && (ev.beta()-ev.BetaCalc())>-0.3
            if( ev.X("eh").mass2() < (-1.5* ev.coneangle()+2)  && ev.X("eh").mass2() >-2  && ((ev.beta()-ev.BetaCalc()) > (0.05*ev.chi2pid()-0.25)) ){
              hAC.fillBasicHisto(ev);
              //counter++;
            }
          }
        if(ev.W().mass() > 2 && -ev.Q().mass2() > 1){
            dvcscounter++;
            event.setEventTag(11);
        }*/
      }
      //writer.addEvent(event,event.getEventTag());
      //System.out.println("my tag is " + event.getEventTag());
    }
      writer.close();
      System.out.println("total counter: " + totalcounter);
      System.out.println("total e d gamma events: " + ndegamma);
      reader.close();
    }



}
  

}
