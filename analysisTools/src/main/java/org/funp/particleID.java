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

//import org.funp.*;

import java.util.*;
import java.io.*;

public class particleID {


    public static Event event;
    public static PositiveEvent pos_ev ;
    public static Particle_ID_Histo histo_CTOF;
    public static Particle_ID_Histo histo_CND;
    
    public static HashMap<Integer, List<Double>> runMap;
    public static double el_en_max;
    static double vertexElectron;

    public static void main( String[] args ) throws FileNotFoundException, IOException {
   
        processInput inputParam=new processInput(args);

        event = new Event();
        pos_ev    = new PositiveEvent();
        histo_CTOF     = new Particle_ID_Histo();
        histo_CTOF.setOutputDir(inputParam.getOutputDir());

        histo_CND     = new Particle_ID_Histo();
        histo_CND.setOutputDir(inputParam.getOutputDir());
        runMap = runUtil.createMapGagikStyle();
        int index = 0;
        el_en_max=0;


		

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

                // ev.BeamEnergy = Double.valueOf(runMap.get(String.valueOf(runNumber)).get(2));
                // System.out.println("Beam energy found is "+ev.BeamEnergy);
                // ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);
        
                // System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
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
                  
                if (runMap.get(runNumber).get(0) == 0.0 && runMap.get(runNumber).get(1) == 0.0){//all events are good
                        goodEvent(particles,scint,scintExtras);
        
                } else if (runMap.get(runNumber).get(0) ==0.0 && runMap.get(runNumber).get(1) != 0.0){//start from beginning and go until event number
                    if (runconfig.getInt("event",0)< runMap.get(runNumber).get(1)){
                        goodEvent(particles,scint,scintExtras);
        
                    }
                } else if (runMap.get(runNumber).get(0) != 0.0 && runMap.get(runNumber).get(1) == 0.0){//start from event and go to end
                    if (runconfig.getInt("event",0) > runMap.get(runNumber).get(0)){
                        goodEvent(particles,scint,scintExtras);
        
                    }
                } else{
                    if (runconfig.getInt("event",0) < runMap.get(runNumber).get(1) && runconfig.getInt("event",0) > runMap.get(runNumber).get(0)){//event min and event max
                        
                        goodEvent(particles,scint,scintExtras);
                
                    }
                }
            
                }
                reader.close();


            }
            else{
                    System.out.println("THIS RUN IS BEING SKIPPED \n\n\n\n\n");
            }      
        
        }//end of the loop over files

    TCanvas ec4 = new TCanvas("Positive Plots CTOF",1500,1500);
    histo_CTOF.drawPositivesCTOF(ec4);

    TCanvas ec5 = new TCanvas("Positive Plots CND",1500,1500);
    histo_CND.drawPositivesCND(ec5);
    System.out.println("Num deut evetns:" +pos_ev.getDeutEvents());

    System.out.println("The number of deut in CTOF before cuts:"+ pos_ev.num_deut_CTOF);
    System.out.println("The number of deut in CTOF after cuts :"+ histo_CTOF.numDeutsCTOF);
    System.out.println("The number of deut in CND before cuts :"+ pos_ev.num_deut_CND);
    System.out.println("The number of deut in CND after  cuts :"+ histo_CND.numDeutsCND);
    System.out.println("The number of pion in CTOF :"+ pos_ev.num_pion_CTOF);
    System.out.println("The number of pion in CND  :"+ pos_ev.num_pion_CND);
    System.out.println("The number of kaon in CTOF :"+ pos_ev.num_kaon_CTOF);
    System.out.println("The number of kaon in CND  :"+ pos_ev.num_kaon_CND);
    System.out.println("The number of prot in CTOF :"+ pos_ev.num_prot_CTOF);
    System.out.println("The number of prot in CND  :"+ pos_ev.num_prot_CND);



    }//end of main method


    public static void goodEvent(Bank particles, Bank scint,Bank scintExtras){
        // Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");

        LorentzVector  vtmp = new LorentzVector();
        LorentzVector  mostEnergetic = new LorentzVector();
        el_en_max = -1;
        

        //finds the most energetic electron then checks to see if vertex and momentum are good

        boolean goodElectron = false;
        if(particles.getRows()>0){
            int mostEnergeticNum = -1;
            vertexElectron = -10;


            //This is to find the most energetic electron and see if it meets the criteria
            
            // for(int npart=0; npart<particles.getRows(); npart++){

            //     int pid = particles.getInt("pid", npart);
            //     int status = particles.getInt("status", npart);

            //     if(pid==11 /*&& Math.abs(status)>=2000 && Math.abs(status)<3000*/){
                    
            //         vtmp.setPxPyPzM(particles.getFloat("px",npart),
            //         particles.getFloat("py",npart),
            //         particles.getFloat("pz",npart),
            //         0.0005);
            //         if(vtmp.e()>el_en_max){
            //             mostEnergeticNum = npart;
            //             el_en_max=vtmp.e();
            //             mostEnergetic.setPxPyPzM(particles.getFloat("px",mostEnergeticNum), particles.getFloat("py",mostEnergeticNum), particles.getFloat("pz",mostEnergeticNum), 0.0005);
            //             vertexElectron = particles.getFloat("vz", mostEnergeticNum);
            //         //this Lorentz Vector holds the most energetic of the electrons
            //         }
            //     }


            // }


        }
        if (vertexElectron>-5 && vertexElectron<-1 && mostEnergetic.p()>1){
            goodElectron = true;
        }

        if(goodElectron||!goodElectron){
            if(particles.getRows()>0){
            for(int npart=0; npart<particles.getRows(); npart++){
                int pid = particles.getInt("pid", npart);
                //int status = particles.getInt("status", npart);
                //float beta = particles.getFloat("beta", npart);
                int charge = particles.getInt("charge",npart);
                
                if (charge>0){
                    if(Math.abs(pid)==45){
                        // if(pos_ev.setDeut(particles,scint,scintExtras,npart)){
                        //     histo_CTOF.fillDeut(pos_ev);
                        // } 
                        pos_ev.setDeut(particles,scint,scintExtras,npart);
                        if (pos_ev.found_in_CTOF){

                            histo_CTOF.fillDeutCTOF(pos_ev);
                        }
                        if (pos_ev.found_in_CND){
                            histo_CND.fillDeutCND(pos_ev);
                        }
                        
                    }
                    else if(Math.abs(pid)==211){
                        pos_ev.setPion(particles,scint,scintExtras,npart);

                        if (pos_ev.found_in_CTOF){
                            histo_CTOF.fillPionCTOF(pos_ev);
                        }
                        if (pos_ev.found_in_CND){
                            histo_CND.fillPionCND(pos_ev);
                        }
                        
                    }
                    else if(Math.abs(pid)==321){
                        pos_ev.setKaon(particles,scint,scintExtras,npart);

                        if (pos_ev.found_in_CTOF){
                            histo_CTOF.fillKaonCTOF(pos_ev);
                        }
                        if (pos_ev.found_in_CND){
                            histo_CND.fillKaonCND(pos_ev);
                        }
                        
                    }
                    else if(Math.abs(pid)==2212){
                        pos_ev.setProton(particles,scint,scintExtras,npart);

                        if (pos_ev.found_in_CTOF){
                            histo_CTOF.fillProtCTOF(pos_ev);
                        }
                        if (pos_ev.found_in_CND){
                            histo_CND.fillProtCND(pos_ev);
                        }
                        
                    }
                }
               
            }
        }

        }
        


    }

    
    
    
}//end of class