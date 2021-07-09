package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

import java.util.Comparator;
//import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.lang.Math;
//import org.jlab.io.base.DataEvent;
//import org.jlab.io.base.DataBank;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class PositiveEvent{

    public PositiveEvent() {
        // This constructor no parameter.
    }

    public LorentzVector  vdeuteron = new LorentzVector();
    public LorentzVector  vproton = new LorentzVector();
    public LorentzVector  vpion = new LorentzVector();
    public LorentzVector  vkaon = new LorentzVector();

    
    double betadeutCTOF=-10;
    double betaprotCTOF=-10;
    double betakaonCTOF=-10;
    double betapionCTOF=-10;

    double betadeutCND=-10;
    double betaprotCND=-10;
    double betakaonCND=-10;
    double betapionCND=-10;
   
    double dedxDeutCTOF=-10;
    double dedxProtCTOF=-10;
    double dedxKaonCTOF=-10;
    double dedxPionCTOF=-10;

    double dedxDeutCND=-10;
    double dedxProtCND=-10;
    double dedxKaonCND=-10;
    double dedxPionCND=-10;

    double chi2piddeutCTOF=-10;
    double chi2pidprotCTOF=-10;
    double chi2pidkaonCTOF=-10;
    double chi2pidpionCTOF=-10;

    double chi2piddeutCND=-10;
    double chi2pidprotCND=-10;
    double chi2pidkaonCND=-10;
    double chi2pidpionCND=-10;

    double MNUC = 1.875612;
    double MPION = 0.139570;
    double MKAON = 0.4977;
    double MPROT = 0.93828;

    double chi2pidhad=-10;
    

    public static boolean found_in_CND = false;
    public static boolean found_in_CTOF = false;
    public int num_deut_CND = 0;
    public int num_deut_CTOF = 0;
    public int num_pion_CND = 0;
    public int num_pion_CTOF = 0;
    public int num_kaon_CND = 0;
    public int num_kaon_CTOF = 0;
    public int num_prot_CND = 0;
    public int num_prot_CTOF = 0;


    int numDeutEvents = 0;
    public int getDeutEvents(){
        return numDeutEvents;
    }

    public void setDeut(Bank particles, Bank scint, Bank scintExtras, int npart){


        Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
        vdeuteron.setPxPyPzM(particles.getFloat("px",npart),
        particles.getFloat("py",npart),
        particles.getFloat("pz",npart),
        this.MNUC);
        betadeutCTOF=-10;
        betadeutCND=-10;
        dedxDeutCTOF = -10;
        dedxDeutCND = -10;
        chi2piddeutCND = -100;
        chi2piddeutCTOF = -100;
        found_in_CND = false;

        found_in_CTOF = false;
        chi2pidhad=particles.getFloat("chi2pid",npart);
        //vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==4){ //CTOF

                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    chi2piddeutCTOF=particles.getFloat("chi2pid",npart);
                    dedxDeutCTOF = scintExtras.getFloat("dedx", iscint);
                    betadeutCTOF=particles.getFloat("beta",npart);
                    found_in_CTOF = true;
                    num_deut_CTOF++;
                    // if (particles.getFloat("vz",npart)<-1 && particles.getFloat("vz",npart)>-5){
                    //     goodDeut = true;
                    //     numDeutEvents++;
                    // }
                    
                    

                    //tmpdeutctof++;
                }
                else if(detector==3) {//CND
                    found_in_CND = true;
                    chi2piddeutCND=particles.getFloat("chi2pid",npart);
                    dedxDeutCND = scintExtras.getFloat("dedx", iscint);
                    betadeutCND=particles.getFloat("beta",npart);
                    //tmpdeutcnd++;
                    num_deut_CND++;
                    //dedxDeut = scintExtras.getFloat("dedx", iscint);

                }
                else  {
                    //tmpdeutnoctof++;
                //particles.show();
                //scint.show();
                }
            }
            

        }

    }

    public void setPion(Bank particles, Bank scint, Bank scintExtras, int npart){
        

        Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
        vpion.setPxPyPzM(particles.getFloat("px",npart),
        particles.getFloat("py",npart),
        particles.getFloat("pz",npart),
        this.MPION);
        betapionCND=-10;
        betapionCTOF=-10;
        dedxPionCTOF=-10;
        dedxPionCND=-10;
        int detected_in = -1;
        //chi2pidhad=particles.getFloat("chi2pid",npart);
       // vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==4){ //CTOF
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    found_in_CTOF = true;
                    dedxPionCTOF = scintExtras.getFloat("dedx",iscint);
                    betapionCTOF=particles.getFloat("beta",npart);
                    num_pion_CTOF++;
                    // break;
                    //tmpdeutctof++;
                }
                else if(detector==3) { //CND
                    //tmpdeutcnd++;
                    found_in_CND = true;
                    dedxPionCND = scintExtras.getFloat("dedx",iscint);
                    betapionCND=particles.getFloat("beta",npart);
                    num_pion_CND++;
                    // dedxPion = scintExtras.getFloat("dedx",iscint);
                    // betapion=particles.getFloat("beta",npart);
                    // break;
                }
                // else  {
                //     //tmpdeutnoctof++;
                // //particles.show();
                // //scint.show();
                // }
            }

        }
        
    }
    public void setKaon(Bank particles, Bank scint, Bank scintExtras, int npart){
        
        Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
        vkaon.setPxPyPzM(particles.getFloat("px",npart),
        particles.getFloat("py",npart),
        particles.getFloat("pz",npart),
        this.MKAON);
        betakaonCTOF=-10;
        betakaonCND=-10;
        dedxKaonCTOF=-10;
        dedxKaonCND=-10;
        found_in_CND = false;
        found_in_CTOF = false;
        //chi2pidhad=particles.getFloat("chi2pid",npart);
        //vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==4){ //CTOF
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    found_in_CTOF = true;
                    dedxKaonCTOF = scintExtras.getFloat("dedx", iscint);
                    betakaonCTOF=particles.getFloat("beta",npart);
                    num_kaon_CTOF++;

                    //tmpdeutctof++;
                }
                else if(detector==3) { //CND
                    //tmpdeutcnd++;
                    found_in_CND = true;
                    dedxKaonCND = scintExtras.getFloat("dedx", iscint);
                    betakaonCND=particles.getFloat("beta",npart);
                    num_kaon_CND++;
                }
                else  {
                    //tmpdeutnoctof++;
                //particles.show();
                //scint.show();
                }
            }

        }
    }
    public void setProton(Bank particles, Bank scint, Bank scintExtras, int npart){
    

        Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
            vproton.setPxPyPzM(particles.getFloat("px",npart),
                    particles.getFloat("py",npart),
                    particles.getFloat("pz",npart),
                    this.MPROT);

        betaprotCTOF = -10;
        betaprotCND = -10;
        dedxProtCND = -10;
        dedxProtCTOF = -10;
        chi2pidprotCND=-10; 
        chi2pidprotCTOF=-10; 
        int detected_in = -1;
        found_in_CND = false;
        found_in_CTOF = false;
        //vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==4){ //CTOF
                    found_in_CTOF = true;
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    dedxProtCTOF = scintExtras.getFloat("dedx", iscint);
                    betaprotCTOF=particles.getFloat("beta",npart);
                    chi2pidprotCTOF=particles.getFloat("chi2pid",npart);
                    num_prot_CTOF++;
                    //tmpdeutctof++;
                }
                else if(detector==3) { //CND
                    //tmpdeutcnd++;
                    found_in_CND = true;
                    dedxProtCND = scintExtras.getFloat("dedx", iscint);
                    betaprotCND =particles.getFloat("beta",npart);
                    chi2pidprotCND=particles.getFloat("chi2pid",npart);
                    num_prot_CND++;
                }
                else  {
                    //tmpdeutnoctof++;
                //particles.show();
                //scint.show();
                }
            }

        }
        
    }

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