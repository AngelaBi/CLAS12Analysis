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
import org.funp.utilities.*;
import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
/**
* testing LorentzVector
*
*/
public class readBanks
{
  public static void main( String[] args )  throws FileNotFoundException, IOException
  {
     int pindexPhoton = -1;
    float elec_w = -1;
    byte electron_layer = -1;
    byte elec_sector = -1;
    byte electron_detector = -1;
    double beamenergy=-10;
    HipoReader reader = new HipoReader(); // Create a reader obejct
    //reader.open("/Users/biselli/Data/clas12/rgB/v8hipo4/out_6489_2xx.hipo"); // open a file
    //reader.open("/Users/biselli/Data/clas12/rgB/pass0v16/dst_inc_006596.hipo"); // open a file
    //reader.open("/Users/biselli/Data/clas12/rgB/pass0justin/dst_edeut_006467_trimmed.hipo"); // open a file
    reader.open("/DATA_DISK/TaggedSpring2019/dst_edeut_6216_trimmed.hipo");  // open a file
   HashMap<Integer, List<Double>> hmap = runUtil.createMapGagikStyle();

    if(hmap.get(6216)!=null){
      beamenergy=hmap.get(6216).get(2);
      System.out.println(beamenergy);
    }
    else {
      System.out.println("Uknown beam energy for this run setting to default of 10.6 GeV");
      beamenergy=10.5986;
    }


    Event     event = new Event();
    Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
    Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));
    Bank  runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));

    Bank calos      = new Bank(reader.getSchemaFactory().getSchema("REC::Calorimeter"));
    Bank scint      = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
    Bank scintExtra      = new Bank(reader.getSchemaFactory().getSchema("REC::ScintExtras"));

    Bank evn      = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));


    reader.getEvent(event,0); //Reads the first event and resets to the begining of the file
    reader.nextEvent(event);
    
    // runconfig.show();
    // evn.show();
    event.read(particles);
    
    event.read(scint);
    event.read(scintExtra);
    event.read(evn);
    event.read(runconfig);
    System.out.println("This is particle bank");
    particles.show();
    // scint.show();
    // scintExtra.show();
    event.read(calos);
    scint.show();
    System.out.println("This is scintextra bank");
    scintExtra.show();

    
    
    
    System.out.println("n rows  part : " + particles.getRows());
    //System.out.println("n rows  calo : " + calos.getRows());
    System.out.println("n rows  scint : " + scint.getRows());
    System.out.println("n rows  scintExtra : " + scintExtra.getRows());
    System.out.println("pid of xx.   : " + particles.getInt("pid",3));


    Map<Integer,List<Integer>> caloMap = loadMapByIndex(calos,"pindex");
    System.out.println("This is the calomap");
    System.out.println(caloMap);
    // Bank parts = event.getBank("REC::Particle");
    // for (int ipart=0; ipart<particles.getRows(); ipart++) {
    //     System.out.println("hi");
    //     System.out.println(caloMap.get(ipart));
        
    //       for (int icalo : caloMap.get(ipart)) {
    //         System.out.println(particles.getInt("pid",ipart));
    //         if (particles.getInt("pid",ipart)==2112){
    //           System.out.println("photon found");
    //           final float lu = calos.getFloat("lu",icalo);
    //           final float lw = calos.getFloat("lw",icalo);
    //           System.out.println("lu:" + lu + " lw:" + lw);
    //         }else{
    //           System.out.println("not found");
    //         }
    //       }
    //   //System.out.println("new ieration");
        
    // 
   
    for (int ipart=0; ipart<particles.getRows(); ipart++) {
      if (particles.getInt("pid",ipart)==22){
        pindexPhoton =ipart;
        break;
      }
    }
    if(caloMap.get(pindexPhoton)!=null){
      for (int icalo : caloMap.get(pindexPhoton)) {
        //System.out.println(scintMap.get(nh));
        electron_layer = calos.getByte("layer",icalo);
        electron_detector = calos.getByte("detector",icalo);
        elec_sector = calos.getByte("sector",icalo);
        System.out.println(electron_detector);
        //System.out.println(detector);
        if(electron_detector==7){//This is for ECAL in PCAL
          elec_w = calos.getFloat("lw",icalo);
           System.out.println("the w of phton"+ elec_w);
          

        }


      }
    }
    System.out.println("the w of phton"+ elec_w);
  }
/*
   * @param fromBank the bank containing the index variable
   * @param idxVarName the name of the index variable
   * @return map with keys being the index in toBank and values the indices in fromBank
   */
  public static Map<Integer,List<Integer>> loadMapByIndex(
           Bank fromBank,
           String idxVarName) {

       Map<Integer,List<Integer>> map=new HashMap<Integer,List<Integer>>();
       if (fromBank!=null) {

           for (int iFrom=0; iFrom<fromBank.getRows(); iFrom++) {
               final int iTo = fromBank.getInt(idxVarName,iFrom);
               if (!map.containsKey(iTo)) map.put(iTo,new ArrayList<Integer>());
               map.get(iTo).add(iFrom);
           }
       }
       return map;
   }

 
   static HashMap<Integer, Double> createrunmap(){
     HashMap<Integer, Double> hmap = new HashMap<Integer, Double>();
     Double beam10p6=10.5986;
     Double beam10p2=10.1998;
     hmap.put(6302,beam10p6);
     hmap.put(6303,beam10p6);
     hmap.put(6305,beam10p6);
     hmap.put(6307,beam10p6);
     hmap.put(6310,beam10p6);
     hmap.put(6313,beam10p6);

     hmap.put(6428,beam10p2);
     hmap.put(6433,beam10p2);
     hmap.put(6442,beam10p2);
     hmap.put(6450,beam10p2);
     hmap.put(6467,beam10p2);
     hmap.put(6475,beam10p2);
     hmap.put(6481,beam10p2);
     hmap.put(6492,beam10p2);
     return hmap;

   }

}
