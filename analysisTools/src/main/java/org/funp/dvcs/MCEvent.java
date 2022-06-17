package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

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


public class MCEvent {
  
  double MNUC=1.875612;
  public double BeamEnergy=10.3;
  public LorentzVector  vBeam   = new LorentzVector(0.0,0.0,BeamEnergy,BeamEnergy);
  public LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,MNUC);
  public LorentzVector  velectron = new LorentzVector();
  public LorentzVector  vphoton = new LorentzVector();
  public LorentzVector  vhadron = new LorentzVector();
  int PIDNUC=45;
  int nelec_MC=0;
  int nphot_MC=0;
  int ndeut_MC=0;
  int nother_MC = 0;
  int ne_MC=-1;
  int ng_MC=-1;
  int nd_MC=-1;
  double el_en_max=0;
  double ph_en_max=0;
  double d_en_max=0;

   public MCEvent() {
    // This constructor no parameter.
    //System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }
  public MCEvent(double mass) {
    // This constructor no parameter.
    MNUC=mass;
    System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }

  public boolean MCFilterParticles(Bank lund){
    boolean goodEvent = false;
    
    nelec_MC=0;
    nphot_MC=0;
    ndeut_MC=0;
    nother_MC=0;
    ne_MC=-1;
    ng_MC=-1;
    nd_MC=-1;
    el_en_max=0;
    ph_en_max=0;
    d_en_max=0;
    LorentzVector  vtmp = new LorentzVector();
    if(lund.getRows()>0){//loop over the events
      for(int npart=0; npart<lund.getRows(); npart++){
        int pid = lund.getInt("pid", npart);
       // System.out.println(pid);
        if(pid==11 /*&& Math.abs(status)>=2000 && Math.abs(status)<3000*/){
          nelec_MC++;
          vtmp.setPxPyPzM(lund.getFloat("px",npart),
          lund.getFloat("py",npart),
          lund.getFloat("pz",npart),
          0.0005);
          if(vtmp.e()>el_en_max){
            ne_MC=npart;
            el_en_max=vtmp.e();
          }
        }

        else if(pid==22 /*&& Math.abs(status)<4000*/){

          nphot_MC++;
          vtmp.setPxPyPzM(lund.getFloat("px",npart),
          lund.getFloat("py",npart),
          lund.getFloat("pz",npart),
          0.0);

          if(vtmp.e()>ph_en_max){
            ng_MC=npart;
            ph_en_max=vtmp.e();
            // if(status<=2000)conf=1;
            // else if(status>=2000 && status<4000)conf=2;

          }
        }

        else if(pid==1000010020 /*&& Math.abs(status)>=4000*/ ){
                    
            //System.out.println("hello deuteron");

          
            ndeut_MC++;
            vtmp.setPxPyPzM(lund.getFloat("px",npart),
            lund.getFloat("py",npart),
            lund.getFloat("pz",npart),
            MNUC);
            if(vtmp.e()>d_en_max){
              nd_MC=npart;
              d_en_max=vtmp.e();
            }
        }
        


      }
      if( ndeut_MC>=1 && nelec_MC>=1 && nphot_MC>=1){
        setElectron(lund,ne_MC);
        setHadron(lund,nd_MC);
        setPhoton(lund,ng_MC);

        //this.setHelicity(hel,runNumber);
        goodEvent=true;
        
      }
      
    }    
return goodEvent;
  }

   public  void setElectron(Bank lund,  int ne) {
    velectron.setPxPyPzM(lund.getFloat("px",ne),
    lund.getFloat("py",ne),
    lund.getFloat("pz",ne),
    0.0005);
    //System.out.println(velectron.p());
    
  }

   public  void setPhoton(Bank lund,int ng) {
    

    //System.out.println("The end lowest energy is " + initialRemainingEnergy);
    vphoton.setPxPyPzM( lund.getFloat("px",ng),
    lund.getFloat("py",ng),
    lund.getFloat("pz",ng),
    0.0);

  }

  public  void setHadron(Bank lund, int nh) {
    //tmpdeut++;
 
    vhadron.setPxPyPzM(lund.getFloat("px",nh),
    lund.getFloat("py",nh),
    lund.getFloat("pz",nh),
    MNUC);
    // betahad=particles.getFloat("beta",nh);
    // chi2pidhad=particles.getFloat("chi2pid",nh);
    
  }

  public  LorentzVector X(String listpart){
    //System.out.println(listpart);
    //String newlistpart=Stream.of("cda").sorted(Comparator.comparingInt(o -> Character.toLowerCase(o.charAt(0)))).collect(Collectors.joining());


    listpart = Stream.of( listpart.split("") )
    .sorted()
    .collect(Collectors.joining());

    //System.out.println(listpart);

    LorentzVector  tmp = new LorentzVector();
    tmp.copy(W());
    // tmp.add(vTarget);
    // tmp.sub(velectron);
    if(listpart.equals("egh")){
      tmp.sub(vphoton);
      tmp.sub(vhadron);
    }
    else if(listpart.equals("eg")){
      tmp.sub(vphoton);

    }
    else if(listpart.equals("eh")){
      tmp.sub(vhadron);
    }
    else {
      System.out.println(listpart+" combination of particle to calculate the missing particle is not supported, use e,g,h" );
    }
    return tmp;
  }
   public  LorentzVector W(){
    LorentzVector  tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.add(vTarget);
    tmp.sub(velectron);
    return tmp;

  }
  public  LorentzVector Q(){
    LorentzVector  tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.sub(velectron);
    return tmp;

  }

  public LorentzVector t(){
    LorentzVector tmp = new LorentzVector();
    tmp.copy(this.Q());
    tmp.sub(vphoton);
    return tmp;
  }
   public double Xb(){
    return (-this.Q().mass2())/(2*0.938*(this.vBeam.e()-this.velectron.e()));
  }

  public double deltaPhiPlane(){
    double deltaphi;
    // LorentzVector tmp=new LorentzVector();
    // tmp.copy(vBeam);
    // tmp.sub(velectron);
    Vector3 norm_Had_VPho = (vhadron.vect().cross(Q().vect()));
    Vector3 norm_Had_Pho = (vhadron.vect().cross(vphoton.vect()));
    //deltaphi = Math.toDegrees(norm_Had_Pho.angle(norm_Had_VPho));
    deltaphi = norm_Had_Pho.theta(norm_Had_VPho);
    if(norm_Had_VPho.dot(vphoton.vect()) < 0 ) deltaphi = -1*deltaphi;
    return deltaphi;
  }
  public double deltaPhiPlane2(){
    double deltaphiplane;
    Vector3 norm_Had_VPho = (vhadron.vect().cross(this.Q().vect()));
    Vector3 norm_VPho_Pho = (this.Q().vect().cross(vphoton.vect()));
    //deltaphiplane = Math.toDegrees(norm_Had_VPho.angle(norm_VPho_Pho));
    deltaphiplane = norm_Had_VPho.theta(norm_VPho_Pho);
    if(norm_Had_VPho.dot(vphoton.vect()) < 0 ) deltaphiplane = -1*deltaphiplane;
    return deltaphiplane;
  }

}