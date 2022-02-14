package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
//import org.jlab.jnp.physics.*;
//import org.jlab.jnp.reader.*;

import java.util.Comparator;
//import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.funp.utilities.*;
import java.lang.Math;
import org.jlab.clas.physics.*;
//import org.jlab.io.base.DataEvent;
//import org.jlab.io.base.DataBank;
//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

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;



public class DvcsEvent {
  public PrintWriter pw = null;
  public StringBuilder builder;
  public String [] args;
  public static boolean isML = false;
  public  void setArgs(String [] argss){
        args = argss;
        for(int i = 0; i < argss.length;i++){
              System.out.println(argss[i]);
        }
        
  }

   public static void isML(boolean var){
        isML = var;
        
  }
  


  processInput inputParam=new processInput(args);
  public void makecsv(){
    
    try{
      pw = new PrintWriter(new File("ThisIsATest.csv"));
    }catch (FileNotFoundException e){
      e.printStackTrace();
    }

    builder = new StringBuilder();
    String columnNames = "dedxCTOF,dedxCND,beta,momentum,chi2,particle";
    builder.append(columnNames+"\n");

  }
    

  // StringBuilder builder = new StringBuilder();
  // String columnNames = "dedx,momentum,particle" ;
  // builder.append(columnNames+"\n");
  double MNUC=1.875612;
  double MPION = 0.139570;
  double MKAON = 0.4977;
  double MPROT = 0.93828;
  public double mpos;
  //Dmass = 1.8756;
  //double MNUC=0.938;
  public double BeamEnergy=10.1998;
  public LorentzVector  vBeam   = new LorentzVector(0.0,0.0,BeamEnergy,BeamEnergy);
  public LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,MNUC);
  public LorentzVector  velectron = new LorentzVector();
  public LorentzVector  vphoton = new LorentzVector();
  public LorentzVector  vhadron = new LorentzVector();
  public LorentzVector  vpositive = new LorentzVector();
  public LorentzVector  vdeuteron = new LorentzVector();
  public LorentzVector  vproton = new LorentzVector();
  public LorentzVector  vpion = new LorentzVector();
  public LorentzVector  vkaon = new LorentzVector();

public byte detectorHad;
public byte detectorProt;

  double dedxDeutCTOF=-10;
  double dedxDeutCND = -10;
  double dedxDeutCTOF_prot=-10;
  double dedxDeutCND_prot = -10;
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
  boolean FoundDeuteron = false;
  boolean FoundProton = false;
  boolean FoundKaon = false;
  boolean FoundPion = false;
  //boolean NewEvent=false;
  double betahad=-10;
  double betaprot_ML=-10;
  double betadeut_ML=-10;
  double betapos=-10;
  double betadeut=-10;
  double betaprot=-10;
  double betakaon=-10;
  double betapion=-10;
  double ctofenergyhad=-10;
  double ctofenergypos=-10;
  double ctofenergydeut=-10;
  double ctofenergyprot=-10;
  double ctofenergykaon=-10;
  double ctofenergypion=-10;
  double chi2pidhad=-10;
  public int tmpdeutctof=0;
  public int tmpdeut=0;
  public int tmpdeutcnd=0;
  public int tmpdeutnoctof=0;
  public int helicityplus=0;
  public int helicityminus=0;
  int helicity=-3;
  int helicityraw=-3;
  public double elec_w;
  public double elec_v;
  public double elec_x;
  public double elec_y;
  public byte elec_sector;

  public double photon_w;
  public double photon_v;
  public double photon_x;
  public double photon_y;
  public byte photon_sector;
  public double beforeFidCut=0;
  // public double elec_layer_4_x;
  // public double elec_layer_4_y;
  // public double elec_layer_7_x;
  // public double elec_layer_7_y;
  public byte electron_layer;
  public byte photon_layer;
  public boolean inCTOF;
  public boolean inCND;
 

  //Nick Add
  double vertexElectron=-100;
  double vertexDeuteron=-100;
  
  //conf is 1 for gamma in FT and e FD, 2 is for gamma and e in FD
  int conf=0;
  public int GetConf() {

    return conf;
  }

  public DvcsEvent() {
    // This constructor no parameter.
    //System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }
  public DvcsEvent(double mass) {
    // This constructor no parameter.
    MNUC=mass;
    System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }

  public void setElectron(Bank particles, Bank calos, int ne) {
    velectron.setPxPyPzM(particles.getFloat("px",ne),
    particles.getFloat("py",ne),
    particles.getFloat("pz",ne),
    0.000511);
    if (!inputParam.getMCmode()){
    if(Math.abs(particles.getInt("status", ne)) >= 1000 && Math.abs(particles.getInt("status", ne)) < 2000){
      velectron=Correct_FT_E(velectron, 0.000511);
    }
  }
    vertexElectron = particles.getFloat("vz", ne);
    elec_v = -10;
    elec_w = -10;
    // elec_layer_4_x = -10000;
    // elec_layer_4_y = -10000;
    // elec_layer_7_x = -10000;
    // elec_layer_7_y = -10000;
    
      
    
    Map<Integer,List<Integer>> caloMap = loadMapByIndex(calos,"pindex");

    if(caloMap.get(ne)!=null){
      for (int icalo : caloMap.get(ne)) {
        //System.out.println(scintMap.get(nh));
        electron_layer = calos.getByte("layer",icalo);
        final byte electron_detector = calos.getByte("detector",icalo);
        elec_sector = calos.getByte("sector",icalo);
       // System.out.println(electron_detector);
        //System.out.println(detector);
        if(electron_detector==7 && electron_layer==1){//This is for ECAL in PCAL
          elec_w = calos.getFloat("lw",icalo);
          elec_v = calos.getFloat("lv",icalo);
          elec_x = calos.getFloat("x",icalo);
          elec_y = calos.getFloat("y",icalo);
         // beforeFidCut++;
         

         
        }
        
      }
    }

    
  }

  public void setPhoton(Bank particles, Bank calos, int ng/* ArrayList<Integer> photonsNumber*/) {
    

    //LorentzVector  remainingVector = new LorentzVector();
    //LorentzVector  vtmp = new LorentzVector();
   // double energyLeft = 100000;
  //  System.out.println(photonsNumber);
  //   remainingVector.copy(this.vBeam);
  //   remainingVector.add(this.vTarget).sub(this.velectron);
  //   remainingVector.sub(this.vhadron);
  //   double initialRemainingEnergy = remainingVector.e();
  //   boolean positiveMissing = false;
    
  //   if (initialRemainingEnergy < 0){
  //     return positiveMissing;
  //   }else{

  //     vtmp.setPxPyPzM(particles.getFloat("px",photonsNumber.get(0)),
  //     particles.getFloat("py",photonsNumber.get(0)),
  //     particles.getFloat("pz",photonsNumber.get(0)),
  //     0.0);
  //     energyLeft = Math.abs(remainingVector.e() - vtmp.e());  
  //     System.out.println("the initial  energy is "+ vtmp.e());

  //     ng = photonsNumber.get(0);
  //     for(int i = 1; i < photonsNumber.size(); i++){
  //       int pid = particles.getInt("pid", photonsNumber.get(i));
  //       int status = particles.getInt("status", photonsNumber.get(i));
  //       float beta = particles.getFloat("beta", photonsNumber.get(i));
        
  //       vtmp.setPxPyPzM(particles.getFloat("px",photonsNumber.get(i)),
  //       particles.getFloat("py",photonsNumber.get(i)),
  //       particles.getFloat("pz",photonsNumber.get(i)),
  //       0.0);

  //       System.out.println("The energy for this photon is " + Math.abs(vtmp.e()));
  //       if(energyLeft > Math.abs(remainingVector.e()-vtmp.e())){
  //         ng=photonsNumber.get(i);
  //         energyLeft = Math.abs(remainingVector.e()-vtmp.e());
  //         status = particles.getInt("status", photonsNumber.get(i));
  //         beta = particles.getFloat("beta", photonsNumber.get(i));

  //         if(status<2000)conf=1;
  //         else if(status>=2000 && status<4000)conf=2;

  //       }
        
  //     }
  //   positiveMissing = true;
  //   }

  // System.out.println("end of event");
    

    //System.out.println("The end lowest energy is " + initialRemainingEnergy);
    vphoton.setPxPyPzM( particles.getFloat("px",ng),
    particles.getFloat("py",ng),
    particles.getFloat("pz",ng),
    0.0);
    
    // 
    //This correction holds only for data 
    if (!inputParam.getMCmode()){
    if(Math.abs(particles.getInt("status", ng)) >= 1000 && Math.abs(particles.getInt("status", ng)) < 2000){
      //vphoton.print();
      vphoton=Correct_FT_E(vphoton, 0.0);
      //vphoton.print();
    }
    else {
      System.out.println("here");
      vphoton=Correct_FD_E(vphoton, 0.0);
    }
  }
    // 

    Map<Integer,List<Integer>> caloMap = loadMapByIndex(calos,"pindex");
    // photon_v = -10;
    // photon_w = -10;
      if(caloMap.get(ng)!=null){
        for (int icalo : caloMap.get(ng)) {
          //System.out.println(scintMap.get(nh));
          photon_layer = calos.getByte("layer",icalo);
          final byte photon_detector = calos.getByte("detector",icalo);
          photon_sector = calos.getByte("sector",icalo);
        // System.out.println(electron_detector);
          //System.out.println(detector);
          if(photon_detector==7 && photon_layer==1){//This is for ECAL in PCAL
            photon_w = calos.getFloat("lw",icalo);
            photon_v = calos.getFloat("lv",icalo);
            photon_x = calos.getFloat("x",icalo);
            photon_y = calos.getFloat("y",icalo);
           // beforeFidCut++;
          

          
          }
          
        }
      }

  }

  public void setHadron(Bank particles, Bank scint, Bank scintExtras, int nh) {
    tmpdeut++;
    dedxDeutCND=-999999;
    dedxDeutCTOF = -999999;
    inCTOF = false;
    inCND = false;
    Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
    vhadron.setPxPyPzM(particles.getFloat("px",nh),
    particles.getFloat("py",nh),
    particles.getFloat("pz",nh),
    this.MNUC);
    betahad=particles.getFloat("beta",nh);
    chi2pidhad=particles.getFloat("chi2pid",nh);
    vertexDeuteron = particles.getFloat("vz", nh);
    if(scintMap.get(nh)!=null){
      for (int iscint : scintMap.get(nh)) {
        //System.out.println(scintMap.get(nh));
        final byte layer = scint.getByte("layer",iscint);
        detectorHad = scint.getByte("detector",iscint);
        //System.out.println(detector);
        if(detectorHad==4){
          ctofenergyhad = scint.getFloat("energy",iscint);
          dedxDeutCTOF = scintExtras.getFloat("dedx", iscint);
          tmpdeutctof++;
          inCTOF = true;
          // builder.append(dedxDeutCTOF + ",");
          
          

        }
        if(detectorHad==3) {
          dedxDeutCND = scintExtras.getFloat("dedx", iscint);
          
          // builder.append(dedxDeutCND + ",");
          
          inCND = true;

          tmpdeutcnd++;
        }
        // builder.append(vhadron.p() + ",");
        // builder.append("1\n");
        // else  {
        //   tmpdeutnoctof++;
        //   //particles.show();
        //   //scint.show();
        // }
      }
    }


    // this iswhat i commneted out on jan 26
    if(isML){
              builder.append(dedxDeutCTOF + ",");
              builder.append(dedxDeutCND + ",");
              builder.append(betahad+ ",");
              
              builder.append(vhadron.p() + ",");
              builder.append(chi2pidhad + ",");
              builder.append("1\n");
    }
              
  }
  LorentzVector Correct_FT_E(LorentzVector x, double mass) {

    double E_new, Px_el, Py_el, Pz_el;
    LorentzVector el_new = new LorentzVector();
    //System.out.println(x.e());
    E_new = x.e() - 0.03689 + 0.1412 * x.e() - 0.04316 * Math.pow(x.e(), 2) + 0.007046 * Math.pow(x.e(), 3)
        - 0.0004055 * Math.pow(x.e(), 4);
      //  System.out.println(E_new);
    Px_el = E_new * (x.px() / x.vect().mag());
    Py_el = E_new * (x.py() / x.vect().mag());
    Pz_el = E_new * (x.pz() / x.vect().mag());

    el_new.setPxPyPzM(Px_el, Py_el, Pz_el, mass);

    return el_new;
  }
  LorentzVector Correct_FD_E(LorentzVector x, double mass) {

    double E_new, Px_el, Py_el, Pz_el;
    LorentzVector el_new = new LorentzVector();
    //System.out.println(x.e());
    E_new = x.e() + 0.527;
      //  System.out.println(E_new);
    Px_el = E_new * (x.px() / x.vect().mag());
    Py_el = E_new * (x.py() / x.vect().mag());
    Pz_el = E_new * (x.pz() / x.vect().mag());

    el_new.setPxPyPzM(Px_el, Py_el, Pz_el, mass);

    return el_new;
  }


  public void setPositives(Bank particles, Bank scint, int np){
    if (this.FoundDeuteron==true){
    vdeuteron.setPxPyPzM(particles.getFloat("px",np),
    particles.getFloat("py",np),
    particles.getFloat("pz",np),
    this.MNUC);
    betadeut=particles.getFloat("beta",np);
  }
  else if (this.FoundProton==true){
    vproton.setPxPyPzM(particles.getFloat("px",np),
    particles.getFloat("py",np),
    particles.getFloat("pz",np),
    this.MPROT);
    betaprot=particles.getFloat("beta",np);
  }
  else if (this.FoundPion==true){
    vpion.setPxPyPzM(particles.getFloat("px",np),
    particles.getFloat("py",np),
    particles.getFloat("pz",np),
    this.MPION);
    betapion=particles.getFloat("beta",np);
  }
  else if (this.FoundKaon==true){
    vkaon.setPxPyPzM(particles.getFloat("px",np),
    particles.getFloat("py",np),
    particles.getFloat("pz",np),
    this.MKAON);
    betakaon=particles.getFloat("beta",np);
  }
}
  public void setHelicity(Bank hel, int runNumber){
    helicity = hel.getInt("helicity", 0);
    if (runNumber<6700 && runNumber != 6378){
      helicity*=-1;
    }
  
    helicityraw = hel.getInt("helicityRaw", 0);
    if(helicity>0) helicityplus++;
    else if (helicity<0) helicityminus++;
  }
  public  void pidStudies(Bank particles, Bank scint){

  }
  public  boolean FilterParticles(Bank particles, Bank scint, Bank hel, Bank scintExtras,Bank calos, int runNumber) {
    LorentzVector  vtmp = new LorentzVector();
    FoundEvent= false;
    this.el_en_max=0;
    this.ph_en_max=0;
    this.d_en_max=0;
    ArrayList<Integer> photonsNumber =  new ArrayList<Integer>();
    photonsNumber.clear();
    Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");
    int status = 0;
    nelec=0;
    nphot=0;
    ndeut=0;
    nother=0;
    ne=-1;
    ng=-1;
    nd=-1;

    double ctofen=-10;

    if(particles.getRows()>0){//loop over the events
      for(int npart=0; npart<particles.getRows(); npart++){//loop over the particles in
        ctofen=-10;
        dedxDeutCTOF = -10;
        dedxDeutCTOF = -10;
        vertexDeuteron = -100;
        vertexElectron = -100;
        int pid = particles.getInt("pid", npart);
        status = particles.getInt("status", npart);
        float beta = particles.getFloat("beta", npart);


        

        //status 2000-2999 is FD
        //if(pid==11 && Math.abs(status)>=2000 && Math.abs(status)<3000){

        if(pid==11 && Math.abs(status)>=1000 && Math.abs(status)<4000){
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
        // status  1000-3999 is FT FD
        //else if(pid==22 && Math.abs(status)<4000){
        else if(pid==22 && Math.abs(status)<4000){
          photonsNumber.add(npart);
          nphot++;
          // vtmp.setPxPyPzM(particles.getFloat("px",npart),
          // particles.getFloat("py",npart),
          // particles.getFloat("pz",npart),
          // 0.0);

          // if(vtmp.e()>this.ph_en_max){
          //   ng=npart;
          //   this.ph_en_max=vtmp.e();
          //   if(status<=2000)conf=1;
          //   else if(status>=2000 && status<4000)conf=2;

          // }
        }
        else if (pid == 2212 && Math.abs(status)>=4000 ){
          dedxDeutCTOF_prot = -999999;
          dedxDeutCND_prot = -999999;
          vtmp.setPxPyPzM(particles.getFloat("px",npart),
                particles.getFloat("py",npart),
                particles.getFloat("pz",npart),
                this.MPROT);
          betaprot_ML=particles.getFloat("beta",npart);

              if(scintMap.get(npart)!=null){
      for (int iscint : scintMap.get(npart)) {
        //System.out.println(scintMap.get(nh));
        final byte layer = scint.getByte("layer",iscint);
        detectorProt = scint.getByte("detector",iscint);
        //System.out.println(detector);
        if(detectorProt==4){
         // ctofenergyhad = scint.getFloat("energy",iscint);
          dedxDeutCTOF_prot = scintExtras.getFloat("dedx", iscint);
          // tmpdeutctof++;
          // inCTOF = true;
          
          

        }
        if(detectorProt==3) {
          dedxDeutCND_prot = scintExtras.getFloat("dedx", iscint);
          
          
          //inCND = true;

          //tmpdeutcnd++;
        }
        

        }
        }

          // builder.append(dedxDeutCTOF_prot + ",");
          // builder.append(dedxDeutCND_prot + ",");
          // builder.append(betaprot_ML + ",");
          // builder.append(vtmp.p() + ",");
          // builder.append("0\n");


        }
        //status 4000 is FD
        //else if(pid==PIDNUC && beta>0.16 && Math.abs(status)>=4000 && ctofen>5){
        else if(pid==PIDNUC && Math.abs(status)>=4000 ){
             
              dedxDeutCTOF = -999999;
              dedxDeutCND = -999999;
              ctofen = -10;  
              betadeut_ML = particles.getFloat("beta",npart);   
              if(scintMap.get(npart)!=null){//check if there scintillator info
                for (int iscint : scintMap.get(npart)) {
                  //System.out.println(scintMap.get(nh));
                  final byte layer = scint.getByte("layer",iscint);
                  final byte detector = scint.getByte("detector",iscint);
                  //System.out.println(detector);
                  if(detector==4){
                    ctofen = scint.getFloat("energy",iscint);
                    dedxDeutCTOF = scintExtras.getFloat("dedx",iscint);
                  }
                  if(detector==3) {
                    dedxDeutCND = scintExtras.getFloat("dedx", iscint);
                    
                    
                    
                    //inCND = true;

                    //tmpdeutcnd++;
                  }
                  





                }
              }
              // builder.append(dedxDeutCTOF + ",");
              // builder.append(dedxDeutCND + ",");
              // builder.append(betadeut_ML+ ",");
              // vtmp.setPxPyPzM(particles.getFloat("px",npart),
              // particles.getFloat("py",npart),
              // particles.getFloat("pz",npart),
              //   this.MNUC);
              // builder.append(vtmp.p() + ",");
              // builder.append("1\n");

              if (beta>0.16 && ctofen>5  && dedxDeutCTOF>1){
                //THis is for no ML
                ndeut++;
                vtmp.setPxPyPzM(particles.getFloat("px",npart),
                particles.getFloat("py",npart),
                particles.getFloat("pz",npart),
                this.MNUC);
                if(vtmp.e()>this.d_en_max){
                  nd=npart;
                  this.d_en_max=vtmp.e();
                }
                

                // if(dedxDeutCND>0){
                //   vtmp.setPxPyPzM(particles.getFloat("px",npart),
                //   particles.getFloat("py",npart),
                //   particles.getFloat("pz",npart),
                //   this.MNUC);
                //   double value = dedxDeutCTOF* 0.486 + dedxDeutCND * 0.469 + vtmp.p() * 6.87 -12.22;
                //   if (1/(1+Math.exp(-value)) > 0.5){
                //     ndeut++;
                //     vtmp.setPxPyPzM(particles.getFloat("px",npart),
                //     particles.getFloat("py",npart),
                //     particles.getFloat("pz",npart),
                //     this.MNUC);
                //     if(vtmp.e()>this.d_en_max){
                //       nd=npart;
                //       this.d_en_max=vtmp.e();
                // }
                //   }
                // }else{//this is the condition if there is only CTOF dedx

                //   vtmp.setPxPyPzM(particles.getFloat("px",npart),
                //   particles.getFloat("py",npart),
                //   particles.getFloat("pz",npart),
                //   this.MNUC);
                //   double value = dedxDeutCTOF* 0.3241  + vtmp.p() * 2.2909 - 4.58;
                //   if (1/(1+Math.exp( -value)) > 0.5){
                //     ndeut++;
                //     vtmp.setPxPyPzM(particles.getFloat("px",npart),
                //     particles.getFloat("py",npart),
                //     particles.getFloat("pz",npart),
                //     this.MNUC);
                //     if(vtmp.e()>this.d_en_max){
                //       nd=npart;
                //       this.d_en_max=vtmp.e();
                //     }
                //   }
                // }


                }
                
              
          
        }
        else {
          nother++;
        }

      }

      //beginning of new way to select photon

    

      //
      if( ndeut>=1 && nelec>=1 && nphot>=1){
        this.setElectron(particles,calos,ne);
        this.setHadron(particles,scint, scintExtras, nd);
        
        //double mass2MissingHadron = 1000.0;
        double missingmass_chi2 = 0.0;
        double chi2ofPhoton=10000.0;
        double coneangle_chi2=0.0;
        int ng = -1;
        //Photon Chi2 Selection hehe :)

        

        for (int i = 0; i< photonsNumber.size(); i++){
            LorentzVector  tmp1 = new LorentzVector();
            tmp1.copy(vBeam);
            tmp1.add(vTarget);
            tmp1.sub(velectron);
            vphoton.setPxPyPzM( particles.getFloat("px",photonsNumber.get(i)),
            particles.getFloat("py",photonsNumber.get(i)),
            particles.getFloat("pz",photonsNumber.get(i)),
            0.0);
            tmp1.sub(vphoton);
            tmp1.sub(vhadron);
            // System.out.println(photonsNumber.size());
            // System.out.println(tmp1.mass2()- (1.87* 1.87));
            // if (Math.abs(tmp1.mass2() - (1.87* 1.87)) < mass2MissingHadron){ //Math.abs(tmp1.mass2() - (MNUC*MNUC))
            //   mass2MissingHadron = Math.abs(tmp1.mass2() - (1.87*1.87));// Math.abs(tmp1.mass2() - MNUC**2);
            //   ng = photonsNumber.get(i);
              
            // }

            missingmass_chi2 = Math.abs(tmp1.mass());
            //System.out.println("The missing mass of the everything is : " + missingmass_chi2);
            LorentzVector temp = new LorentzVector();
            temp.copy(this.X("eh"));
            //coneangle_chi2 = Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
            coneangle_chi2 = this.vphoton.vect().theta(temp.vect());
            //System.out.println("The cone angle of the photon is  :" + coneangle_chi2);
            if (chi2ofPhoton > coneangle_chi2 + missingmass_chi2){

              chi2ofPhoton = coneangle_chi2 + missingmass_chi2;
              ng = photonsNumber.get(i);

            }



        }
        // System.out.println("FFFFFFFFFFFFFFFFFFFF The missing mass of the everything is : " + missingmass_chi2);
        // System.out.println("FFFFFFFFFFFFFFFFFFFFFThe cone angle of the photon is  :" + coneangle_chi2);
        // System.out.println(" FFFFFFFFFFFFFFFFFFFFFFFThe total chi2 used to select the photon is "+ chi2ofPhoton);
        status = particles.getInt("status", ng);
        if(Math.abs(status)<=2000)conf=1;
        else if(Math.abs(status)>2000 && Math.abs(status)<4000)conf=2;
      
        this.setPhoton(particles,calos,ng/*photonsNumber*/);

        this.setHelicity(hel,runNumber);
        FoundEvent=true;
        
      }
    }
    //NewEvent=true;
    return FoundEvent;
  }

  public boolean FilterPositives(Bank particles, Bank scint){
    Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");

    LorentzVector  vtmp = new LorentzVector();
    double ctofenpos=-10;
    FoundPositives = false;

    if(particles.getRows()>0){
      for(int npart=0; npart<particles.getRows(); npart++){
        int pid = particles.getInt("pid", npart);
        //int status = particles.getInt("status", npart);
        float beta = particles.getFloat("beta", npart);
        int charge = particles.getInt("charge",npart);


        if(charge >= 0){
          FoundPositives = true;
          FoundDeuteron = false;
          FoundProton = false;
          FoundKaon = false;
          FoundPion = false;
          FoundProton = false;
          npositives++;
          np = npart;
          if(Math.abs(pid)==45){
            mpos = this.MNUC;
            FoundDeuteron = true;
          }
          else if(Math.abs(pid)==211){
            mpos = this.MPION;
            FoundPion = true;
          }
          else if(Math.abs(pid)==321){
            mpos = this.MKAON;
            FoundKaon = true;
          }
          else if(Math.abs(pid)==2212){
            mpos = this.MPROT;
            FoundProton = true;
          }
          this.setPositives(particles,scint,np);
        }

        if(scintMap.get(npart)!=null){
          for (int iscint : scintMap.get(npart)) {
            //System.out.println(scintMap.get(nh));
            final byte layer = scint.getByte("layer",iscint);
            final byte detector = scint.getByte("detector",iscint);
            //System.out.println(detector);
            if(detector==4){
              if(FoundDeuteron==true){
                ctofenergydeut = scint.getFloat("energy",iscint);
              }
              if (FoundKaon==true){
                ctofenergykaon = scint.getFloat("energy",iscint);
              }
              if (FoundProton==true){
                ctofenergyprot = scint.getFloat("energy",iscint);
              }
              if (FoundPion==true){
                ctofenergypion = scint.getFloat("energy",iscint);
              }
            }
          }
        }
      }
    }
    if (FoundDeuteron==true || FoundProton==true || FoundKaon==true|| FoundPion==true){
      FoundPositives = true;
    }
    return FoundPositives;
  }

  public LorentzVector W(){
    LorentzVector  tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.add(vTarget);
    tmp.sub(velectron);
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
    tmp.copy(this.Q());
    tmp.sub(vphoton);
    return tmp;
  }
  public LorentzVector tH(){
    LorentzVector tmp = new LorentzVector();
    tmp.copy(vTarget);
    tmp.sub(vhadron);
    return tmp;
  }
  public double tFX(){
    double q2=this.Q().mass2();
    double mu=this.Q().e();
    double costhetagvg=(this.Q().vect().dot(this.vphoton.vect()))/this.Q().vect().mag()/this.vphoton.vect().mag();
    double THETA=Math.sqrt(Math.pow(mu,2)+q2)*costhetagvg;
    return -(MNUC*q2+2*MNUC*mu*(mu-THETA))/(MNUC+mu-THETA);

  }
  public double pPerp(){
    double px=(this.vBeam.px()-this.velectron.px()-this.vhadron.px()-this.vphoton.px());
    double py=(this.vBeam.py()-this.velectron.py()-this.vhadron.py()-this.vphoton.py());
    return Math.sqrt(px*px+py*py);
  }
  

  public boolean TagEventsDVCScut(){
    return -this.Q().mass2()>1 && this.W().mass()>2;
  }

  public boolean TagEventsExclusivityCut(){
    boolean keeptop= (this.X("eh").mass2() < (-20.0/6.0* this.coneangle()+10) )|| (this.X("eh").mass2() <1) ;
    boolean keepbottom = this.X("eh").mass2() > -2;
    return keeptop && keepbottom;
  }

  public boolean DVCScut(){
    //&& Math.toDegrees(this.vphoton.theta())<5
    
    boolean fiducialCutElectron = false;
    //System.out.println(elec_sector);
    if ((elec_sector==1 && elec_v >9.7824 && elec_v < 402.06 && elec_w>0.47359 && elec_w<393.895)|| (elec_sector ==  2 && elec_v>8.62768 && elec_v<402.389 && elec_w>8.57818 && elec_w<402.064)|| (elec_sector ==  3 && elec_v>9.23112 && elec_v<403.875 && elec_w>8.23956 && elec_w<403.622)||(elec_sector ==  4 && elec_v>19.2814 && elec_v<403.021 && elec_w>8.26354 && elec_w<392.355)|| (elec_sector ==  5 && elec_v>8.73336 && elec_v<402.915 && elec_w>9.28017 && elec_w<403.634)||(elec_sector ==  6 && elec_v>9.12088 && elec_v<403.581 && elec_w>8.13996 && elec_w<403.886)){
      fiducialCutElectron = true;
    }

    // boolean fiducialCutPhoton = false;
    // //System.out.println(elec_sector);
    // if ((photon_sector==1 && photon_v >9.7824 && photon_v < 402.06 && photon_w>0.47359 && photon_w<393.895)|| (photon_sector ==  2 && photon_v>8.62768 && photon_v<402.389 && photon_w>8.57818 && photon_w<402.064)|| (photon_sector ==  3 && photon_v>9.23112 && photon_v<403.875 && photon_w>8.23956 && photon_w<403.622)||(photon_sector ==  4 && photon_v>19.2814 && photon_v<403.021 && photon_w>8.26354 && photon_w<392.355)|| (photon_sector ==  5 && photon_v>8.73336 && photon_v<402.915 && photon_w>9.28017 && photon_w<403.634)||(photon_sector ==  6 && photon_v>9.12088 && photon_v<403.581 && photon_w>8.13996 && photon_w<403.886)){
    //   fiducialCutPhoton = true;
    // }
    // return fiducialCutElectron;
    boolean cut=
    (-this.Q().mass2()>1 
    && this.W().mass()>2  
    && this.vhadron.p()<2  
    && this.vphoton.e()>2 //changed from 1
    && this.angleBetweenElectronPhoton()>8
    // && fiducialCutPhoton
     && fiducialCutElectron
    );
    return cut;
  }
  public boolean PrelimExclusivitycut(){
    boolean cut=false;
    if (conf==1){
      cut=
      //(this.X("eh").mass2() < (-20/6* this.coneangle()+10) This is a test for when i do tag evetns
      (this.X("eh").mass2() < (-1.5* this.coneangle()+2) 
      && this.X("eh").mass2() >-1) ; 
    }
    else if (conf==2){
    cut=
       (this.X("eh").mass2() < (-1* this.coneangle()+2) 
      && this.X("eh").mass2()>-1
      //&& this.X("eg").mass2()< +12-1.5*this.coneangle()
      );
    }
    return cut;
  }
  public boolean Exclusivitycut(int runNumber){
    boolean cut=false;
    //boolean dedxCut = true;
    boolean vertexCut = false;
      //THESE ARE THE dedx cuts done manually, they should be removed  since we are looking into ML stuff
      // if (inCTOF && vhadron.p() < 1.1 && vhadron.p() > 0.6 && dedxDeutCTOF < 4.3654 *Math.pow(vhadron.p(),-1.851)){
      //   dedxCut = false;
      // }
      // if (inCTOF && dedxDeutCTOF > 11.464 *Math.pow(vhadron.p(),-1.161)){
      //   dedxCut = false;
      // }
      // if (inCND && vhadron.p() < 1.1 && vhadron.p() > 0.8 && dedxDeutCND < 3.628 *Math.pow(vhadron.p(),-2.398)){
      //   dedxCut = false;
      // }
      
      if (runNumber < 6700 && vertexElectron > -6 && vertexElectron < 0 && vertexElectron > (-1.5 + vertexDeuteron) && vertexElectron < (1.5 + vertexDeuteron)){
        vertexCut = true;
      } else if (runNumber > 6700 && vertexElectron > -7 && vertexElectron < 0 && vertexElectron > (-1.8 + vertexDeuteron) && vertexElectron < (1.8 + vertexDeuteron)){
        vertexCut = true;
      }

    if (conf==1){
      cut=
      //(this.X("eh").mass2() < (-20/6* this.coneangle()+10) This is a test for when i do tag evetns
      (
      //  this.X("eh").mass2() < (-1.5* this.coneangle()+2) 
      //&& this.X("eh").mass2() >-2  && //Commenting these two lines becasue I move the coneangle cut in the PrelimExclusivitycut
      //&& ((this.beta()-this.BetaCalc()) > (0.05*this.chi2pid()-0.25)) 
      /* && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()+0.25)) */
      this.X("ehg").mass2()>-0.75 && this.X("ehg").mass2()<0.25 && //was none
      this.X("ehg").e()<1 //Was 2, not a big change of FT
      && this.pPerp()<0.5
      &&this.X("ehg").p()<0.5//was 1.5
      //&& Math.abs(this.chi2pid()) < 3.5
      //&& this.X("eh").mass() < 1.5//was 0.7
      && vertexCut
      //&& dedxCut
      //&& -1*this.t().mass2()  < 1.4
      /*&& getDedxDeut()> (-30*vhadron.p() +30)*/);
    }
    else if (conf==2){

      
      cut=
       (
      //   this.X("eh").mass2() < (-1* this.coneangle()+2) 
      //&& this.X("eh").mass2()>-2 && //Commenting these two lines becasue I move the coneangle cut in the PrelimExclusivitycut
      //&& ((this.beta()-this.BetaCalc()) > (0.05*this.chi2pid()-0.25)) 
      /*&& ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()+0.25)) */
      this.X("ehg").mass2()>-0.25 && this.X("ehg").mass2()<0.25 //>-0.75
      && this.X("ehg").e()<2//was 3 //was 2// probably removing pions
      && this.pPerp()<0.5
      &&this.X("ehg").p()<0.8//was 1.5
      // && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()-0.1)
      //&& Math.abs(this.chi2pid()) < 3.5
      && this.X("eh").mass() < 0.7//was 1.5//was nothing - desperate attempt to reduce pion background
      && vertexCut
      //&& dedxCut
      //&& -1*this.t().mass2() < 1.4
     /* && getDedxDeut()> (-30*vhadron.p()+30)*/);
    } 
    return cut;
  }
  public double Xb(){
    return (-this.Q().mass2())/(2*0.938*(this.vBeam.e()-this.velectron.e()));
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
    //Phi = Math.toDegrees(leptonicPlane.angle(hadronicPlane));
    Phi = leptonicPlane.theta(hadronicPlane);
    // Vector3 leptonicPlane = new Vector3();
    // leptonicPlane.copy(vBeam.vect().cross(velectron.vect()));
    // Vector3 hadronicPlane = new Vector3();
    // hadronicPlane.copy(vhadron.vect().cross(vphoton.vect()));
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
  public double coneangle(){
    LorentzVector temp = new LorentzVector();
    temp.copy(this.X("eh"));
    //return Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
    return this.vphoton.vect().theta(temp.vect());
  }

  public double angleBetweenElectronPhoton(){

    //return Math.toDegrees(this.vphoton.vect().angle(velectron.vect()));
    return this.vphoton.vect().theta(velectron.vect());

  }
  public double DTheta(){
    //     LorentzVector temp = new LorentzVector();
    //     temp.copy(this.X("eh"));
    // return Math.toDegrees(vphoton.vect().angle(temp.vect()));
    //return Math.toDegrees(vphoton.vect().angle(this.X("eh").vect()));
    return vphoton.vect().theta(this.X("eh").vect());
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

  public double getVertexElectron(){
    return vertexElectron;
  }

  public double getVertexDeuteron(){
    return vertexDeuteron;
  }

  public double getDedxDeut(){
    return dedxDeutCTOF;
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
