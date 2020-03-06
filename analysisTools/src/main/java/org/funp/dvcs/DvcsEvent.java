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


//import org.jlab.io.base.DataEvent;
//import org.jlab.io.base.DataBank;


import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class DvcsEvent {
  double MNUC=1.875612;
  //Dmass = 1.8756;
  //double MNUC=0.938;
  public double BeamEnergy=10.1998;
  public LorentzVector  vBeam   = new LorentzVector(0.0,0.0,BeamEnergy,BeamEnergy);
  public LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,MNUC);
  public LorentzVector  velectron = new LorentzVector();
  public LorentzVector  vphoton = new LorentzVector();
  public LorentzVector  vhadron = new LorentzVector();
  public LorentzVector  vpositive = new LorentzVector();

  double el_en_max=0;
  double ph_en_max=0;
  double d_en_max=0;
  int PIDNUC=45;
  //int PIDNUC=2212;
  int nelec=0;
  int nphot=0;
  int ndeut=0;
  int nother=0;
  int npositives=0;
  int ne=-1;
  int ng=-1;
  int nd=-1;
  int np=-1;
  boolean FoundEvent= false;
  boolean FoundPositives = false;
  //boolean NewEvent=false;
  double betahad=-10;
  double betapos=-10;
  double ctofenergyhad=-10;
  double ctofenergypos=-10;
  double chi2pidhad=-10;
  public int tmpdeutctof=0;
  public int tmpdeut=0;
  public int tmpdeutcnd=0;
  public int tmpdeutnoctof=0;
  public int helicityplus=0;
  public int helicityminus=0;
  int helicity=-3;
  int helicityraw=-3;
  //conf is 1 for gamma in FT and e FD, 2 is for gamma and e in FD
  int conf=0;


  public DvcsEvent() {
    // This constructor no parameter.
    //System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }
  public DvcsEvent(double mass) {
    // This constructor no parameter.
    MNUC=mass;
    System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }

  public void setElectron(Bank particles, int ne) {
    velectron.setPxPyPzM(particles.getFloat("px",ne),
    particles.getFloat("py",ne),
    particles.getFloat("pz",ne),
    0.0005);
  }

  public void setPhoton(Bank particles, int ng) {
    vphoton.setPxPyPzM( particles.getFloat("px",ng),
    particles.getFloat("py",ng),
    particles.getFloat("pz",ng),
    0.0);
  }
  public void setHadron(Bank particles, Bank scint, int nh) {
    tmpdeut++;
    Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
    vhadron.setPxPyPzM(particles.getFloat("px",nh),
    particles.getFloat("py",nh),
    particles.getFloat("pz",nh),
    this.MNUC);
    betahad=particles.getFloat("beta",nh);
    chi2pidhad=particles.getFloat("chi2pid",nh);
    if(scintMap.get(nh)!=null){
      for (int iscint : scintMap.get(nh)) {
        //System.out.println(scintMap.get(nh));
        final byte layer = scint.getByte("layer",iscint);
        final byte detector = scint.getByte("detector",iscint);
        //System.out.println(detector);
        if(detector==4){
          ctofenergyhad = scint.getFloat("energy",iscint);
          tmpdeutctof++;
        }
        else if(detector==3) {
          tmpdeutcnd++;
        }
        else  {
          tmpdeutnoctof++;
          //particles.show();
          //scint.show();
        }
      }
    }
  }
  public void setPositives(Bank particles, Bank scint, int np){
    vpositive.setPxPyPzM(particles.getFloat("px",np),
    particles.getFloat("py",np),
    particles.getFloat("pz",np),
    this.MNUC);
    betapos=particles.getFloat("beta",np);
  }
  public void setHelicity(Bank hel){
    helicity = hel.getInt("helicity", 0);
    helicityraw = hel.getInt("helicityRaw", 0);
    if(helicity>0) helicityplus++;
    else if (helicity<0) helicityminus++;
  }
  public  void pidStudies(Bank particles, Bank scint){

  }
  public  boolean FilterParticles(Bank particles, Bank scint, Bank hel) {
    LorentzVector  vtmp = new LorentzVector();
    FoundEvent= false;
    this.el_en_max=0;
    this.ph_en_max=0;
    this.d_en_max=0;

    Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");

    nelec=0;
    nphot=0;
    ndeut=0;
    nother=0;
    ne=-1;
    ng=-1;
    nd=-1;

    double ctofen=-10;

    if(particles.getRows()>0){
      for(int npart=0; npart<particles.getRows(); npart++){
        ctofen=-10;
        int pid = particles.getInt("pid", npart);
        int status = particles.getInt("status", npart);
        float beta = particles.getFloat("beta", npart);


        if(scintMap.get(npart)!=null){
          for (int iscint : scintMap.get(npart)) {
            //System.out.println(scintMap.get(nh));
            final byte layer = scint.getByte("layer",iscint);
            final byte detector = scint.getByte("detector",iscint);
            //System.out.println(detector);
            if(detector==4){
              ctofen = scint.getFloat("energy",iscint);
            }
          }
        }


        if(pid==11 && Math.abs(status)>=2000 && Math.abs(status)<3000){
          nelec++;
          vtmp.setPxPyPzM(particles.getFloat("px",npart),
          particles.getFloat("py",npart),
          particles.getFloat("pz",npart),
          0.0005);
          if(vtmp.e()>this.el_en_max){
            ne=npart;
            this.el_en_max=vtmp.e();
          }
        }
        else if(pid==22 && Math.abs(status)<4000){
          nphot++;
          vtmp.setPxPyPzM(particles.getFloat("px",npart),
          particles.getFloat("py",npart),
          particles.getFloat("pz",npart),
          0.0);
          if(vtmp.e()>this.ph_en_max){
            ng=npart;
            this.ph_en_max=vtmp.e();
            if(status<=2000)conf=1;
            else if(status>=2000 && status<4000)conf=2;

          }
        }
        else if(pid==PIDNUC && beta>0.16 && Math.abs(status)>=4000 && ctofen>5){
          ndeut++;
          vtmp.setPxPyPzM(particles.getFloat("px",npart),
          particles.getFloat("py",npart),
          particles.getFloat("pz",npart),
          this.MNUC);
          if(vtmp.e()>this.d_en_max){
            nd=npart;
            this.d_en_max=vtmp.e();
          }
        }
        else {
          nother++;
        }

      }
      //
      if( ndeut>=1 && nelec>=1 && nphot>=1){
        this.setElectron(particles,ne);
        this.setPhoton(particles,ng);
        this.setHadron(particles,scint,nd);
        this.setHelicity(hel);
        FoundEvent=true;
      }
    }
    //NewEvent=true;
    return FoundEvent;
  }

  public boolean FilterPositives(Bank particles, Bank scint){
    Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");

    LorentzVector  vtmp = new LorentzVector();
    double mpos;
    double ctofenpos=-10;
    FoundPositives = false;

    if(particles.getRows()>0){
      for(int npart=0; npart<particles.getRows(); npart++){
        int pid = particles.getInt("pid", npart);
        int status = particles.getInt("status", npart);
        float beta = particles.getFloat("beta", npart);
        int charge = particles.getInt("charge",npart);

        if(scintMap.get(npart)!=null){
          for (int iscint : scintMap.get(npart)) {
            //System.out.println(scintMap.get(nh));
            final byte layer = scint.getByte("layer",iscint);
            final byte detector = scint.getByte("detector",iscint);
            //System.out.println(detector);
            if(detector==4){
              ctofenergypos = scint.getFloat("energy",iscint);
            }
          }
        }

        if(charge >= 0 && pid==45){
          FoundPositives = true;
          npositives++;
          np = npart;
          if(pid==45){
            mpos = this.MNUC;
          }
          this.setPositives(particles,scint,np);
        }
      }
    }
    return FoundPositives;
  }

  public LorentzVector W(){
    LorentzVector  tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.add(vTarget).sub(velectron);
    return tmp;

  }
  public LorentzVector Q(){
    LorentzVector  tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.sub(velectron);
    return tmp;

  }
  public LorentzVector t(){
    LorentzVector tmp = new LorentzVector();
    tmp.copy(vphoton);
    tmp.sub(velectron);
    tmp.sub(vBeam);
    tmp.sub(vphoton);
    return tmp;
  }
  public double pPerp(){
    double px=(this.vBeam.px()-this.velectron.px()-this.vhadron.px()-this.vphoton.px());
    double py=(this.vBeam.py()-this.velectron.py()-this.vhadron.py()-this.vphoton.py());
    return Math.sqrt(px*px+py*py);
  }
  // public LorentzVector DVCSmissX(){
  //     LorentzVector  tmp = new LorentzVector();
  //     tmp.copy(vBeam);
  //     tmp.add(vTarget);
  //     tmp.sub(velectron);
  //     tmp.sub(vphoton);
  //     return tmp;
  //
  // }
  // public LorentzVector ehehgX(){
  //     LorentzVector  tmp = new LorentzVector();
  //     tmp.copy(vBeam);
  //     tmp.add(vTarget);
  //     tmp.sub(velectron);
  //     tmp.sub(vphoton);
  //     tmp.sub(vhadron);
  //     return tmp;
  // }
  // public LorentzVector ehehX(){
  //     LorentzVector  tmp = new LorentzVector();
  //     tmp.copy(vBeam);
  //     tmp.add(vTarget);
  //     tmp.sub(velectron);
  //     tmp.sub(vhadron);
  //     return tmp;
  // }
  //public double MM2(){
  // return this.X("ehg").mass2();
  //}
  //public double Mp(){
  //     return this.X("ehg").p();
  //}
  //public double Mpx(){
  //   return this.X("ehg").px();
  //}
  //public double Mpy(){
  //   return this.X("ehg").py();
  //}
  //public double Mpz(){
  //   return this.X("ehg").pz();
  //}
  public boolean DVCScut(){
    //&& Math.toDegrees(this.vphoton.theta())<5
    boolean cut=(-this.Q().mass2()>1 && this.W().mass()>2  && this.vhadron.p()<2  && this.vphoton.e()>2);
    return cut;
  }
  public boolean Exclusivitycut(){
    boolean cut=false;
    if (conf==1){
      cut=(this.X("eh").mass2() < (-1.5* this.coneangle()+2) && this.X("eh").mass2() >-2  && ((this.beta()-this.BetaCalc()) > (0.05*this.chi2pid()-0.25)) && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()+0.25)) && this.X("ehg").e()<2 && this.pPerp()<0.5);
    }
    else if (conf==2){
      cut=(this.X("eh").mass2() < (-1* this.coneangle()+2) && this.X("eh").mass2()>-2 && ((this.beta()-this.BetaCalc()) > (0.05*this.chi2pid()-0.25)) && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()+0.25)) && this.X("ehg").mass2()>-0.75 && this.X("ehg").e()<3 && this.pPerp()<0.5);
    }
    return cut;
  }
  public double Xb(){
    return (-this.Q().mass2())/(2*0.938*(this.vBeam.p()-this.velectron.p()));
  }

  public double DPhi(){
    //     LorentzVector temp = new LorentzVector();
    //     temp.copy(this.X("eh"));
    // return vphoton.vect().phi() - temp.vect().phi();
    return vphoton.vect().phi() - this.X("eh").vect().phi();
  }
  public double PhiPlane(){
    double Phi;
    Vector3 leptonicPlane=vBeam.vect().cross(velectron.vect());
    Vector3 hadronicPlane=vhadron.vect().cross(vphoton.vect());
    Phi = Math.toDegrees(leptonicPlane.angle(hadronicPlane));
    // Vector3 leptonicPlane = new Vector3();
    //     leptonicPlane.copy(vBeam.vect().cross(velectron.vect()));
    // Vector3 hadronicPlane = new Vector3();
    //     hadronicPlane.copy(vhadron.vect().cross(vphoton.vect()));
    // Phi = Math.toDegrees(leptonicPlane.angle(hadronicPlane));
    //System.out.println("Angle = " + Phi);
if(leptonicPlane.dot(vphoton.vect()) < 0){
	return 360 - Phi;
}
	else return Phi;
  }
  public double deltaPhiPlane(){
    double deltaphi;
    // LorentzVector tmp=new LorentzVector();
    // tmp.copy(vBeam);
    // tmp.sub(velectron);
    Vector3 norm_Had_VPho = (vhadron.vect().cross(Q().vect()));
    Vector3 norm_Had_Pho = (vhadron.vect().cross(vphoton.vect()));
    deltaphi = Math.toDegrees(norm_Had_Pho.angle(norm_Had_VPho));
    if(norm_Had_VPho.dot(vphoton.vect()) < 0 ) deltaphi = -1*deltaphi;
    return deltaphi;
  }
  public double deltaPhiPlane2(){
    double deltaphiplane;
    Vector3 norm_Had_VPho = (vhadron.vect().cross(this.Q().vect()));
    Vector3 norm_VPho_Pho = (this.Q().vect().cross(vphoton.vect()));
    deltaphiplane = Math.toDegrees(norm_Had_VPho.angle(norm_VPho_Pho));
    if(norm_Had_VPho.dot(vphoton.vect()) < 0 ) deltaphiplane = -1*deltaphiplane;
    return deltaphiplane;
  }
  public double coneangle(){
    LorentzVector temp = new LorentzVector();
    temp.copy(this.X("eh"));
    return Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
  }
  public double DTheta(){
    //     LorentzVector temp = new LorentzVector();
    //     temp.copy(this.X("eh"));
    // return Math.toDegrees(vphoton.vect().angle(temp.vect()));
    return Math.toDegrees(vphoton.vect().angle(this.X("eh").vect()));
  }
  //this function returns the missing vector for a given list of possible particles in a dvcs events
  //could be ehg or eg eh
  public LorentzVector X(String listpart){
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
  public double BetaCalc(){
    double betaCalc = vhadron.p() / Math.sqrt(MNUC*MNUC+vhadron.p()*vhadron.p());
    return betaCalc;
  }
  public double beta(){
    return betahad;
  }
  public double betapos(){
    return betapos;
  }
  public double ctofen(){
    return ctofenergyhad;
  }
  public double ctofenpos(){
    return ctofenergypos;
  }
  public double chi2pid(){
    return chi2pidhad;
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
