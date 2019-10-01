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

/**
* testing LorentzVector
*
*/
public class testDcoDe
{
  public static void main( String[] args )
  {
    int user=0;//user==1 is justin
    HipoReader reader = new HipoReader(); // Create a reader obejct
    //reader.open("/home/jnp/data/out_6489_2xx_3xx.hipo"); // open a file
    if(user==1)
    reader.open("/home/justind/DATA/out_6489_2xx_3xx.hipo"); // open a file
    else
    reader.open("/Users/biselli/Data/clas12/rgB/pass0v15/out_6595_2xx-3xx.hipo"); // open a file
    //reader.open("/Users/biselli/Data/clas12/rgB/v8hipo4/out_6489_2xx.hipo"); // open a file
    Event     event = new Event();
    Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
    Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
    Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
    DvcsEvent ev    = new DvcsEvent();
    DvcsHisto hNC     = new DvcsHisto();//No cuts
    DvcsHisto hDC     = new DvcsHisto();//DVCS cuts
    DvcsHisto hAC     = new DvcsHisto();//All cuts

    reader.getEvent(event,0); //Reads the first event and resets to the begining of the file
    int times=0;

    int ndvcs=0;

    int counter=0;


    //loop over the events
    while(reader.hasNext()==true){
      reader.nextEvent(event);
      event.read(particles);
      event.read(scint);
      if(ev.FilterParticles(particles,scint)){
        hNC.fillBasicHisto(ev);
        if(ev.DVCScut()){
          ndvcs++;
          //if(vMMass.mass2()>-1 && vMMass.mass2()<1 && (vphoton.theta()*180./Math.PI)<5){
          //    MMom.fill(vMMom.p());
          hDC.fillBasicHisto(ev);
          if((Math.toDegrees(ev.vphoton.theta())<5)  && ev.coneangle()<3 && ev.X("ehg").mass2()<15){
            hAC.fillBasicHisto(ev);
            counter++;
          }
        }
      }
    }
    //if(counter==0)break;
    //counter--;

    System.out.println("total dvcs events: " + ndvcs);
    TCanvas ec = new TCanvas("c",1200,1000);
    hNC.DrawBasic( ec);

    TCanvas ec4 = new TCanvas("c2",1500,1500);
    hDC.DrawMissing(ec4);

    TCanvas ec5 = new TCanvas("c3",1500,1500);
    hAC.DrawMissing(ec5);

    TCanvas ec6 = new TCanvas("call1",1200,1000);
    TCanvas ec7 = new TCanvas("call2",1200,1000);
    //hNC.DrawAll( ec6,ec7);





}
}
