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

import java.util.*;
import java.io.*;

/**
* testing LorentzVector
*
*/
public class readBanks
{
  public static void main( String[] args )
  {
    HipoReader reader = new HipoReader(); // Create a reader obejct
    //reader.open("/Users/biselli/Data/clas12/rgB/v8hipo4/out_6489_2xx.hipo"); // open a file
    //reader.open("/Users/biselli/Data/clas12/rgB/pass0v16/dst_inc_006596.hipo"); // open a file
    reader.open("/Users/biselli/Data/clas12/rgB/pass0v16/dst_edeut_006596.hipo"); // open a file

    Event     event = new Event();
    Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
    Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));

    Bank calos      = new Bank(reader.getSchemaFactory().getSchema("REC::Calorimeter"));
    Bank scint      = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
    Bank evn      = new Bank(reader.getSchemaFactory().getSchema("REC::Event"));


    reader.getEvent(event,0); //Reads the first event and resets to the begining of the file

    reader.nextEvent(event);
    event.read(particles);
    event.read(calos);
    event.read(scint);
    event.read(evn);
    particles.show();
    scint.show();
    //calos.show();
    evn.show();
    System.out.println("n rows  part : " + particles.getRows());
    //System.out.println("n rows  calo : " + calos.getRows());
    System.out.println("n rows  scint : " + scint.getRows());
    System.out.println("pid of xx.   : " + particles.getInt("pid",3));
    Map<Integer,List<Integer>> caloMap = loadMapByIndex(scint,"pindex");
    System.out.println(evn.getFloat("startTime",0));






}
/**
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
}
