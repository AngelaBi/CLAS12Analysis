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
    
    double betadeut=-10;
    double betaprot=-10;
    double betakaon=-10;
    double betapion=-10;
   
    double dedxDeut=-10;
    double dedxProt=-10;
    double dedxKaon=-10;
    double dedxPion=-10;

    double MNUC = 1.875612;
    double MPION = 0.139570;
    double MKAON = 0.4977;
    double MPROT = 0.93828;

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
        betadeut=-10;
        dedxDeut = -10;
        //chi2pidhad=particles.getFloat("chi2pid",npart);
        //vertexDeuteron = particles.getFloat("vz", npart);
        boolean goodDeut = false;
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==3){
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    dedxDeut = scintExtras.getFloat("dedx", iscint);
                    betadeut=particles.getFloat("beta",npart);
                    // if (particles.getFloat("vz",npart)<-1 && particles.getFloat("vz",npart)>-5){
                    //     goodDeut = true;
                    //     numDeutEvents++;
                    // }
                    
                    

                    //tmpdeutctof++;
                }
                else if(detector==3) {
                    //tmpdeutcnd++;
                    //dedxDeut = scintExtras.getFloat("dedx", iscint);

                }
                else  {
                    //tmpdeutnoctof++;
                //particles.show();
                //scint.show();
                }
            }
            

        }
       //return goodDeut;

    }

    public void setPion(Bank particles, Bank scint, Bank scintExtras, int npart){
        

        Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
        vpion.setPxPyPzM(particles.getFloat("px",npart),
        particles.getFloat("py",npart),
        particles.getFloat("pz",npart),
        this.MPION);
        betapion=-10;
        dedxPion=-10;
        //chi2pidhad=particles.getFloat("chi2pid",npart);
       // vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==3){
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    dedxPion = scintExtras.getFloat("dedx", iscint);
                    betapion=particles.getFloat("beta",npart);

                    //tmpdeutctof++;
                }
                else if(detector==3) {
                    //tmpdeutcnd++;
                }
                else  {
                    //tmpdeutnoctof++;
                //particles.show();
                //scint.show();
                }
            }

        }
        
    }
    public void setKaon(Bank particles, Bank scint, Bank scintExtras, int npart){
        


        Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
        vkaon.setPxPyPzM(particles.getFloat("px",npart),
        particles.getFloat("py",npart),
        particles.getFloat("pz",npart),
        this.MKAON);
        betakaon=-10;
        dedxKaon=-10;
        //chi2pidhad=particles.getFloat("chi2pid",npart);
        //vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==3){
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    dedxKaon = scintExtras.getFloat("dedx", iscint);
                    betakaon=particles.getFloat("beta",npart);

                    //tmpdeutctof++;
                }
                else if(detector==3) {
                    //tmpdeutcnd++;
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
        betaprot = -10;
        dedxProt = -10;
        //chi2pidhad=particles.getFloat("chi2pid",npart);
        //vertexDeuteron = particles.getFloat("vz", npart);
        if(scintMap.get(npart)!=null){
            for (int iscint : scintMap.get(npart)) {
                //System.out.println(scintMap.get(nh));
                final byte layer = scint.getByte("layer",iscint);
                final byte detector = scint.getByte("detector",iscint);
                //System.out.println(detector);
                if(detector==3){
                    
                    //ctofenergyhad = scint.getFloat("energy",iscint);
                    dedxProt = scintExtras.getFloat("dedx", iscint);
                    betaprot=particles.getFloat("beta",npart);

                    //tmpdeutctof++;
                }
                else if(detector==3) {
                    //tmpdeutcnd++;
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