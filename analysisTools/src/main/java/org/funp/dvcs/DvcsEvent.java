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

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class DvcsEvent {
  public PrintWriter pw = null;
  
  //CODE TO CREATE A CSV FILE
  public StringBuilder builder;
  public void makecsv() {

    try {
      pw = new PrintWriter(new File("ThisIsATest.csv"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    builder = new StringBuilder();
    String columnNames = "dedxCTOF,dedxCND,beta,momentum,chi2,particle";
    builder.append(columnNames + "\n");
  }


  //double MNUC = 1.875612;
  //TMP
  double MNUC =0.93828;
  double MPIONP = 0.139570;
  double MPION = 0.1349768;
  double MKAON = 0.4977;
  double MPROT = 0.93828;
  
  // Dmass = 1.8756;
  // double MNUC=0.938;
  public double BeamEnergy = 10.1998;
  public LorentzVector vBeam = new LorentzVector(0.0, 0.0, BeamEnergy, BeamEnergy);
  public LorentzVector vTarget = new LorentzVector(0.0, 0.0, 0.0, MNUC);
  public LorentzVector vTargetP = new LorentzVector(0.0, 0.0, 0.0, MPROT);
  public LorentzVector velectron = new LorentzVector();
  public LorentzVector vphoton = new LorentzVector();
  public LorentzVector vhadron = new LorentzVector();
  public LorentzVector vhadron_mis = new LorentzVector();
  //public LorentzVector vdeuteron = new LorentzVector();
  //public LorentzVector vproton = new LorentzVector();
  public LorentzVector vpion = new LorentzVector();
  

  public byte detectorHad;
  public byte detectorProt;

  double dedxDeutCTOF = -10;
  double dedxDeutCND = -10;
  double dedxDeutCTOF_prot = -10;
  double dedxDeutCND_prot = -10;
  double el_en_max = 0;
  double ph_en_max = 0;
  double d_en_max = 0;
  int PIDNUC = 45;
  //TMP
  //int PIDNUC=2212;
  int nelec = 0;
  int nphot = 0;
  int ndeut = 0;
  int nother = 0;
  int npositives = 0;
  int ne = -1;
  int ng = -1;
  int nd = -1;
  int np = -1;
  boolean FoundEvent = false;
  boolean FoundPositives = false;
  boolean FoundDeuteron = false;
  boolean FoundProton = false;
  boolean FoundKaon = false;
  boolean FoundPion = false;
 
  double betahad = -10;
  double betaprot_ML = -10;
  double betadeut_ML = -10;
  double ctofenergyhad = -10;
  
  double chi2pidhad = -10;
  public int tmpdeutctof = 0;
  public int tmpdeut = 0;
  public int tmpdeutcnd = 0;
  public int tmpdeutnoctof = 0;
  public int helicityplus = 0;
  public int helicityminus = 0;
  int helicity = -3;
  int helicityraw = -3;
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
  
  public byte electron_layer;
  public byte photon_layer;
  public boolean inCTOF;
  public boolean inCND;

  // Nick Add
  double vertexElectron = -100;
  double vertexDeuteron = -100;

  // conf is 1 for gamma in FT and e FD, 2 is for gamma and e in FD
  int conf = 0;

  public int GetConf() {

    return conf;
  }

  public DvcsEvent() {
    // This constructor no parameter.
    // System.out.println("setting the default DVCS event for hadron :" + MNUC );
  }

  public DvcsEvent(double mass) {
    // This constructor no parameter.
    MNUC = mass;
    System.out.println("setting the default DVCS event for hadron :" + MNUC);
  }

  public void setElectron(Bank particles, Bank calos, int ne) {
    velectron.setPxPyPzM(particles.getFloat("px", ne),
        particles.getFloat("py", ne),
        particles.getFloat("pz", ne),
        0.000511);
    if (!processInput.getMCmode()) {
      if (Math.abs(particles.getInt("status", ne)) >= 1000 && Math.abs(particles.getInt("status", ne)) < 2000) {
        velectron = Correct_FT_E(velectron, 0.000511);
      }
    }
    vertexElectron = particles.getFloat("vz", ne);
    elec_v = -10;
    elec_w = -10;
    

    Map<Integer, List<Integer>> caloMap = loadMapByIndex(calos, "pindex");

    if (caloMap.get(ne) != null) {
      for (int icalo : caloMap.get(ne)) {
        // System.out.println(scintMap.get(nh));
        electron_layer = calos.getByte("layer", icalo);
        final byte electron_detector = calos.getByte("detector", icalo);
        elec_sector = calos.getByte("sector", icalo);
        // System.out.println(electron_detector);
        // System.out.println(detector);
        if (electron_detector == 7 && electron_layer == 1) {// This is for ECAL in PCAL
          elec_w = calos.getFloat("lw", icalo);
          elec_v = calos.getFloat("lv", icalo);
          elec_x = calos.getFloat("x", icalo);
          elec_y = calos.getFloat("y", icalo);
          // beforeFidCut++;

        }

      }
    }

  }
  public void setPhoton(Bank particles, Bank calos, ArrayList<Integer> photonsNumber ) {
 // double mass2MissingHadron = 1000.0;
 double missingmass_chi2 = 0.0;
 double chi2ofPhoton = 10000.0;
 double coneangle_chi2 = 0.0;
 int ng = -1;
 // Photon Chi2 Selection hehe :)

 for (int i = 0; i < photonsNumber.size(); i++) {
   LorentzVector tmp1 = new LorentzVector();
   tmp1.copy(vBeam);
   tmp1.add(vTarget);
   tmp1.sub(velectron);
   vphoton.setPxPyPzM(particles.getFloat("px", photonsNumber.get(i)),
       particles.getFloat("py", photonsNumber.get(i)),
       particles.getFloat("pz", photonsNumber.get(i)),
       0.0);
   tmp1.sub(vphoton);
   tmp1.sub(vhadron);

   missingmass_chi2 = Math.abs(tmp1.mass());
   // System.out.println("The missing mass of the everything is : " +
   // missingmass_chi2);
   LorentzVector temp = new LorentzVector();
   temp.copy(this.X("eh"));
   // coneangle_chi2 = Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
   coneangle_chi2 = this.vphoton.vect().theta(temp.vect());
   // System.out.println("The cone angle of the photon is :" + coneangle_chi2);
   if (chi2ofPhoton > coneangle_chi2 + missingmass_chi2) {

     chi2ofPhoton = coneangle_chi2 + missingmass_chi2;
     ng = photonsNumber.get(i);

   }

 }
 
 int status = particles.getInt("status", ng);
 if (Math.abs(status) < 2000)
   conf = 1;
 else if (Math.abs(status) >= 2000 && Math.abs(status) < 4000)
   conf = 2;


    
    // System.out.println("The end lowest energy is " + initialRemainingEnergy);
    vphoton.setPxPyPzM(particles.getFloat("px", ng),
        particles.getFloat("py", ng),
        particles.getFloat("pz", ng),
        0.0);

    //
    // This correction holds only for data
    if (!processInput.getMCmode()) {
      if (Math.abs(particles.getInt("status", ng)) >= 1000 && Math.abs(particles.getInt("status", ng)) < 2000) {
        // vphoton.print();
        // System.out.println("here");
        vphoton = Correct_FT_E(vphoton, 0.0);
        vphoton = Correct_FT_theta(vphoton);
        // vphoton.print();
      } else {
        // System.out.println("here"+!processInput.getMCmode());
        //vphoton = Correct_FD_E(vphoton, 0.0);
      }
    }
    //

    Map<Integer, List<Integer>> caloMap = loadMapByIndex(calos, "pindex");
    // photon_v = -10;
    // photon_w = -10;
    if (caloMap.get(ng) != null) {
      for (int icalo : caloMap.get(ng)) {
        // System.out.println(scintMap.get(nh));
        photon_layer = calos.getByte("layer", icalo);
        final byte photon_detector = calos.getByte("detector", icalo);
        photon_sector = calos.getByte("sector", icalo);
        // System.out.println(electron_detector);
        // System.out.println(detector);
        if (photon_detector == 7 && photon_layer == 1) {// This is for ECAL in PCAL
          photon_w = calos.getFloat("lw", icalo);
          photon_v = calos.getFloat("lv", icalo);
          photon_x = calos.getFloat("x", icalo);
          photon_y = calos.getFloat("y", icalo);
          // beforeFidCut++;

        }

      }
    }

  }
  public void setPion(Bank particles, Bank calos, ArrayList<Integer> photonsNumber ) {
    //System.out.println(photonsNumber.size());
    LorentzVector tmpphoton1=new LorentzVector();
    LorentzVector tmpphoton2=new LorentzVector();
    LorentzVector pair=new LorentzVector();
    LorentzVector pairtmp=new LorentzVector();
    // tmpphoton1.setPxPyPzM(particles.getFloat("px", photonsNumber.get(0)),
    //   particles.getFloat("py", photonsNumber.get(0)),
    //   particles.getFloat("pz", photonsNumber.get(0)),0.0);
    // tmpphoton2.setPxPyPzM(particles.getFloat("px", photonsNumber.get(1)),
    //   particles.getFloat("py", photonsNumber.get(1)),
    //   particles.getFloat("pz", photonsNumber.get(1)),0.0);
    // pair.copy(tmpphoton1);
    // pair.add(tmpphoton2);
    //pair.print();
    
    // double masspair=pair.mass2();
    // double diff=Math.abs(masspair-MPION*MPION);
    double diff=100000;
    //System.out.println(pair.mass2()+" "+diff);
    for (int i = 0; i < photonsNumber.size(); i++) {
      for (int j = 0; j < photonsNumber.size(); j++) {
       //&&( !(i==0 && j==1))
        if(i<j ){
          tmpphoton1.setPxPyPzM(particles.getFloat("px", photonsNumber.get(i)),
          particles.getFloat("py", photonsNumber.get(i)),
          particles.getFloat("pz", photonsNumber.get(i)),0.0);
          tmpphoton2.setPxPyPzM(particles.getFloat("px", photonsNumber.get(j)),
          particles.getFloat("py", photonsNumber.get(j)),
          particles.getFloat("pz", photonsNumber.get(j)),0.0);
          
          pairtmp.copy(tmpphoton1);
          pairtmp.add(tmpphoton2);
          //pairtmp.print();
          double difftmp=Math.abs(pairtmp.mass2()-MPION*MPION);
          //System.out.println(pairtmp.mass2()+" "+difftmp);
          if(difftmp<diff ){
            //System.out.println("i="+i+" j="+j+" "+difftmp);
            
            pair.copy(pairtmp);
            //vpion.setPxPyPzE(pairtmp.px(), pairtmp.py(), pairtmp.pz(), pairtmp.e());;
            diff=difftmp;
            //pairtmp.print();
            //pair.print();
            //vpion.print();
            //System.out.println("better");
          }
        }
      }
    }
    //System.out.println("out");
    //pair.print();
    vpion.setPxPyPzE(pair.px(), pair.py(), pair.pz(), pair.e());;
    //vpion.print();
  }
  

  public void setHadron(Bank particles, Bank scint, Bank scintExtras, int nh) {
    tmpdeut++;
    dedxDeutCND = -999999;
    dedxDeutCTOF = -999999;
    inCTOF = false;
    inCND = false;
    Map<Integer, List<Integer>> scintMap = loadMapByIndex(scint, "pindex");
    vhadron.setPxPyPzM(particles.getFloat("px", nh),
        particles.getFloat("py", nh),
        particles.getFloat("pz", nh),
        this.MNUC);
    vhadron_mis.setPxPyPzM(particles.getFloat("px", nh),
        particles.getFloat("py", nh),
        particles.getFloat("pz", nh),
        this.MPROT);
    betahad = particles.getFloat("beta", nh);
    chi2pidhad = particles.getFloat("chi2pid", nh);
    vertexDeuteron = particles.getFloat("vz", nh);
    if (scintMap.get(nh) != null) {
      for (int iscint : scintMap.get(nh)) {
        // System.out.println(scintMap.get(nh));
        final byte layer = scint.getByte("layer", iscint);
        detectorHad = scint.getByte("detector", iscint);
        // System.out.println(detector);
        if (detectorHad == 4) {
          ctofenergyhad = scint.getFloat("energy", iscint);
          dedxDeutCTOF = scintExtras.getFloat("dedx", iscint);
          tmpdeutctof++;
          inCTOF = true;
          

        }
        if (detectorHad == 3) {
          dedxDeutCND = scintExtras.getFloat("dedx", iscint);
         

          inCND = true;

          tmpdeutcnd++;
        }

      }
    }



  }

  LorentzVector Correct_FT_E(LorentzVector x, double mass) {

    double E_new, Px_el, Py_el, Pz_el;
    LorentzVector el_new = new LorentzVector();
    // System.out.println(x.e());
    E_new = x.e() - 0.03689 + 0.1412 * x.e() - 0.04316 * Math.pow(x.e(), 2) + 0.007046 * Math.pow(x.e(), 3)
        - 0.0004055 * Math.pow(x.e(), 4);
    // System.out.println(E_new);
    Px_el = E_new * (x.px() / x.vect().mag());
    Py_el = E_new * (x.py() / x.vect().mag());
    Pz_el = E_new * (x.pz() / x.vect().mag());

    el_new.setPxPyPzM(Px_el, Py_el, Pz_el, mass);

    return el_new;
  }

  LorentzVector Correct_FD_E(LorentzVector x, double mass) {

    double E_new, Px_el, Py_el, Pz_el;
    LorentzVector el_new = new LorentzVector();
    // System.out.println(x.e());
    E_new = x.e() + 0.527;
    // System.out.println(E_new);
    Px_el = E_new * (x.px() / x.vect().mag());
    Py_el = E_new * (x.py() / x.vect().mag());
    Pz_el = E_new * (x.pz() / x.vect().mag());

    el_new.setPxPyPzM(Px_el, Py_el, Pz_el, mass);

    return el_new;
  }

  LorentzVector Correct_FT_theta(LorentzVector photon) {
    LorentzVector newphoton = new LorentzVector();
    double mag = photon.p();
    double phi = photon.phi();
    double theta = photon.theta();
    // System.out.println(vertexElectron);
    theta = Math.atan(Math.tan(theta) * 194.3 / (196.3 - (vertexElectron)));
    Vector3 vec = new Vector3();
    vec.setMagThetaPhi(mag, theta, phi);
    newphoton.setVectM(vec, 0);
    // photon.print();
    // newphoton.print();
    return newphoton;

  }

  
  public void setHelicity(Bank hel, int runNumber) {
    helicity = hel.getInt("helicity", 0);
    if (runNumber < 6700 && runNumber != 6378) {
      helicity *= -1;
    }

    helicityraw = hel.getInt("helicityRaw", 0);
    if (helicity > 0)
      helicityplus++;
    else if (helicity < 0)
      helicityminus++;
  }

  public void pidStudies(Bank particles, Bank scint) {

  }

  public boolean FilterParticles(Bank particles, Bank scint, Bank hel, Bank scintExtras, Bank calos, int runNumber) {
    LorentzVector vtmp = new LorentzVector();
    FoundEvent = false;
    this.el_en_max = 0;
    this.ph_en_max = 0;
    this.d_en_max = 0;
    ArrayList<Integer> photonsNumber = new ArrayList<Integer>();
    photonsNumber.clear();
    Map<Integer, List<Integer>> scintMap = loadMapByIndex(scint, "pindex");
    int status = 0;
    nelec = 0;
    nphot = 0;
    ndeut = 0;
    nother = 0;
    ne = -1;
    ng = -1;
    nd = -1;
    vpion.setPxPyPzM(99, 99, 99, 99);

    double ctofen = -10;

    if (particles.getRows() > 0) {// loop over the events
      for (int npart = 0; npart < particles.getRows(); npart++) {// loop over the particles in
        ctofen = -10;
        dedxDeutCTOF = -10;
        dedxDeutCTOF = -10;
        vertexDeuteron = -100;
        vertexElectron = -100;
        int pid = particles.getInt("pid", npart);
        status = particles.getInt("status", npart);
        float beta = particles.getFloat("beta", npart);

        // status 2000-2999 is FD
        // if(pid==11 && Math.abs(status)>=2000 && Math.abs(status)<3000){

        if (pid == 11 && Math.abs(status) >= 1000 && Math.abs(status) < 4000) {
          nelec++;
          vtmp.setPxPyPzM(particles.getFloat("px", npart),
              particles.getFloat("py", npart),
              particles.getFloat("pz", npart),
              0.0005);
          if (vtmp.e() > this.el_en_max) {
            ne = npart;
            this.el_en_max = vtmp.e();
          }
        }
        // status 1000-3999 is FT FD
        // else if(pid==22 && Math.abs(status)<4000){
        else if (pid == 22 && Math.abs(status) < 4000) {
          photonsNumber.add(npart);
          nphot++;
          //Postponing the choice of the photon since instead of picking up the most energentic 
          //We want to keep the one that fits best the DVCS channel

          // vtmp.setPxPyPzM(particles.getFloat("px",npart),
          // particles.getFloat("py",npart),
          // particles.getFloat("pz",npart),
          // 0.0);

          // if(vtmp.e()>this.ph_en_max){
          // ng=npart;
          // this.ph_en_max=vtmp.e();
          // if(status<=2000)conf=1;
          // else if(status>=2000 && status<4000)conf=2;

          // }
        } else if (pid == 1010101 && Math.abs(status) >= 4000) {//sub with 2212
          dedxDeutCTOF_prot = -999999;
          dedxDeutCND_prot = -999999;
          vtmp.setPxPyPzM(particles.getFloat("px", npart),
              particles.getFloat("py", npart),
              particles.getFloat("pz", npart),
              this.MPROT);
          betaprot_ML = particles.getFloat("beta", npart);

          if (scintMap.get(npart) != null) {
            for (int iscint : scintMap.get(npart)) {
              // System.out.println(scintMap.get(nh));
              final byte layer = scint.getByte("layer", iscint);
              detectorProt = scint.getByte("detector", iscint);
              // System.out.println(detector);
              if (detectorProt == 4) {
                // ctofenergyhad = scint.getFloat("energy",iscint);
                dedxDeutCTOF_prot = scintExtras.getFloat("dedx", iscint);
                // tmpdeutctof++;
                // inCTOF = true;

              }
              if (detectorProt == 3) {
                dedxDeutCND_prot = scintExtras.getFloat("dedx", iscint);

                // inCND = true;

                // tmpdeutcnd++;
              }

            }
          }
          if (processInput.getMLmode()) {
          builder.append(dedxDeutCTOF_prot + ",");
          builder.append(dedxDeutCND_prot + ",");
          builder.append(betaprot_ML + ",");
          builder.append(vtmp.p() + ",");
          builder.append("0\n");
          }
        }
        // status 4000 is FD
        // else if(pid==PIDNUC && beta>0.16 && Math.abs(status)>=4000 && ctofen>5){
        else if (pid == PIDNUC && Math.abs(status) >= 4000) {
          //System.out.println("found proton");

          dedxDeutCTOF = -999999;
          dedxDeutCND = -999999;
          ctofen = -10;
          betadeut_ML = particles.getFloat("beta", npart);
          if (scintMap.get(npart) != null) {// check if there scintillator info
            for (int iscint : scintMap.get(npart)) {
              // System.out.println(scintMap.get(nh));
              final byte layer = scint.getByte("layer", iscint);
              final byte detector = scint.getByte("detector", iscint);
              // System.out.println(detector);
              if (detector == 4) {
                ctofen = scint.getFloat("energy", iscint);
                dedxDeutCTOF = scintExtras.getFloat("dedx", iscint);
              }
              if (detector == 3) {
                dedxDeutCND = scintExtras.getFloat("dedx", iscint);

                // inCND = true;

                // tmpdeutcnd++;
              }

            }
          }
          if (processInput.getMLmode()) {
          builder.append(dedxDeutCTOF + ",");
          builder.append(dedxDeutCND + ",");
          builder.append(betadeut_ML+ ",");
          vtmp.setPxPyPzM(particles.getFloat("px",npart),
          particles.getFloat("py",npart),
          particles.getFloat("pz",npart),
          this.MNUC);
          builder.append(vtmp.p() + ",");
          builder.append("1\n");
          }
          //System.out.println(beta);
          //System.out.println(ctofen);
          //System.out.println(dedxDeutCTOF);
          //if (beta > 0.16 && ctofen > 5 && dedxDeutCTOF > 1) {
            vtmp.setPxPyPzM(particles.getFloat("px", npart),
                particles.getFloat("py", npart),
                particles.getFloat("pz", npart),
                this.MNUC);
          if (beta > 0.16 && ctofen > 5 && dedxDeutCTOF > 3 &&  vtmp.p()>0.4 ) {
          //if (beta > 0.16 && ctofen > 5 ) {
            //System.out.println("good 45");
            // THis is for no ML
            ndeut++;
            
            //if (vtmp.e() > this.d_en_max) {
              if (vtmp.e() > this.d_en_max) {
              nd = npart;
              this.d_en_max = vtmp.e();
            }



          }

        } else {
          nother++;
        }

      }

      // beginning of new way to select photon

      //
      int mingamma=1;
      if(processInput.getPi0mode())mingamma=2;
      if (ndeut >= 1 && nelec >= 1 && nphot >= mingamma) {
        this.setElectron(particles, calos, ne);
        this.setHadron(particles, scint, scintExtras, nd);
        this.setPhoton(particles, calos, photonsNumber);
        if(nphot>=2) this.setPion(particles, calos, photonsNumber);
        //vpion.print();
        this.setHelicity(hel, runNumber);
        FoundEvent = true;

      }
    }
    // NewEvent=true;
    return FoundEvent;
  }

  public LorentzVector W() { //does not make any sense?
    LorentzVector tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.add(vTarget);
    tmp.sub(velectron);
    return tmp;

  }
  public LorentzVector Wp() {
    LorentzVector tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.add(vTargetP);
    tmp.sub(velectron);
    return tmp;

  }

  public LorentzVector Q() {
    LorentzVector tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.sub(velectron);
    return tmp;

  }

  public LorentzVector t() {
    LorentzVector tmp = new LorentzVector();
    tmp.copy(this.Q());
    tmp.sub(vphoton);
    return tmp;
  }

  public LorentzVector tH() {
    LorentzVector tmp = new LorentzVector();
    tmp.copy(vTarget);
    tmp.sub(vhadron);
    return tmp;
  }

  public double tFX() {
    double q2 = this.Q().mass2();
    double mu = this.Q().e();
    double costhetagvg = (this.Q().vect().dot(this.vphoton.vect())) / this.Q().vect().mag() / this.vphoton.vect().mag();
    double THETA = Math.sqrt(Math.pow(mu, 2) + q2) * costhetagvg;
    return -(MNUC * q2 + 2 * MNUC * mu * (mu - THETA)) / (MNUC + mu - THETA);

  }
  public double tFX_mis() {
    double q2 = this.Q().mass2();
    double mu = this.Q().e();
    double costhetagvg = (this.Q().vect().dot(this.vphoton.vect())) / this.Q().vect().mag() / this.vphoton.vect().mag();
    double THETA = Math.sqrt(Math.pow(mu, 2) + q2) * costhetagvg;
    return -(MPROT * q2 + 2 * MPROT * mu * (mu - THETA)) / (MPROT + mu - THETA);

  }
  public double pPerp() {
    double px = (this.vBeam.px() - this.velectron.px() - this.vhadron.px() - this.vphoton.px());
    double py = (this.vBeam.py() - this.velectron.py() - this.vhadron.py() - this.vphoton.py());
    return Math.sqrt(px * px + py * py);
  }

  public double pPerp(String particle) {//particle can be "g" or "m" meson)
  double px=this.vBeam.px() - this.velectron.px() - this.vhadron.px();
  double py=this.vBeam.py() - this.velectron.py() - this.vhadron.py();
    if(particle == "m"){
     px = ( px- this.vpion.px());
     py = ( py- this.vpion.py());
    }
    else if(particle =="g"){
    px = (px - this.vphoton.px());
    py = (py - this.vphoton.py());
    }
    return Math.sqrt(px * px + py * py);
  }

  
  public double pPerp_mis() {
    double px = (this.vBeam.px() - this.velectron.px() - this.vhadron_mis.px() - this.vphoton.px());
    double py = (this.vBeam.py() - this.velectron.py() - this.vhadron_mis.py() - this.vphoton.py());
    return Math.sqrt(px * px + py * py);
  }


  public boolean TagEventsDVCScut() {
    return -this.Q().mass2() > 1 && this.Wp().mass() > 2;
  }

  public boolean TagEventsExclusivityCut() {
    boolean keeptop = (this.X("eh").mass2() < (-20.0 / 6.0 * this.coneangle() + 10)) || (this.X("eh").mass2() < 1);
    boolean keepbottom = this.X("eh").mass2() > -2;
    return keeptop && keepbottom;
  }
  public boolean FiducialCuts(){
    boolean fiducialCutElectron = false;
    // System.out.println(elec_sector);
    if ((elec_sector == 1 && elec_v > 9.7824 && elec_v < 402.06 && elec_w > 0.47359 && elec_w < 393.895)
        || (elec_sector == 2 && elec_v > 8.62768 && elec_v < 402.389 && elec_w > 8.57818 && elec_w < 402.064)
        || (elec_sector == 3 && elec_v > 9.23112 && elec_v < 403.875 && elec_w > 8.23956 && elec_w < 403.622)
        || (elec_sector == 4 && elec_v > 19.2814 && elec_v < 403.021 && elec_w > 8.26354 && elec_w < 392.355)
        || (elec_sector == 5 && elec_v > 8.73336 && elec_v < 402.915 && elec_w > 9.28017 && elec_w < 403.634)
        || (elec_sector == 6 && elec_v > 9.12088 && elec_v < 403.581 && elec_w > 8.13996 && elec_w < 403.886)) {
      fiducialCutElectron = true;
    }
      // boolean fiducialCutPhoton = false;
    // //System.out.println(elec_sector);
    // if ((photon_sector==1 && photon_v >9.7824 && photon_v < 402.06 &&
    // photon_w>0.47359 && photon_w<393.895)|| (photon_sector == 2 &&
    // photon_v>8.62768 && photon_v<402.389 && photon_w>8.57818 &&
    // photon_w<402.064)|| (photon_sector == 3 && photon_v>9.23112 &&
    // photon_v<403.875 && photon_w>8.23956 && photon_w<403.622)||(photon_sector ==
    // 4 && photon_v>19.2814 && photon_v<403.021 && photon_w>8.26354 &&
    // photon_w<392.355)|| (photon_sector == 5 && photon_v>8.73336 &&
    // photon_v<402.915 && photon_w>9.28017 && photon_w<403.634)||(photon_sector ==
    // 6 && photon_v>9.12088 && photon_v<403.581 && photon_w>8.13996 &&
    // photon_w<403.886)){
    // fiducialCutPhoton = true;
    // }
    // return fiducialCutElectron;
    return fiducialCutElectron;
  }

  public boolean DVCScut() {
    
    boolean cut = (-this.Q().mass2() > 1.0 // TEMP!!!!!!
        && this.Wp().mass() > 2
        && this.vhadron.p() < 2
        && this.vphoton.e() > 1 // changed from 2//proton analysis was 1 not sure when we changed it to 2
        //with the shift in FD cut in 2 reduces the statistics
        && this.angleBetweenElectronPhoton() > 8
        // && fiducialCutPhoton
        );
    return cut;
  }

  public boolean MLSelection(){
    boolean cut = true;
    double value = 0.0;
    value =  -0.5840943 + -0.1449291 * dedxDeutCTOF +  4.02766262 * this.vhadron.p() ;
    value = -5.58524383 + 0.08681531 * dedxDeutCTOF +  8.99244494 * this.vhadron.p();
    value = -6.37328647 + 0.210012661 * dedxDeutCTOF + 6.960000 * this.vhadron.p();
    // value = -6.81325828 + 0.26339882 * dedxDeutCTOF +  6.4551572 *  this.vhadron.p();
    // value = -12.23304327 - 0.00530526205  * dedxDeutCTOF + 23.9827928 *this.vhadron.p();

    value = 1/(1+ Math.pow(Math.exp(1),-1*value));
      if (value < 0.3){
        cut = false;
      }
    if (this.vhadron.p() > 0.8) {
      value = -1.43514474 - 1.43125974 * 1 -.339326333 * dedxDeutCTOF -1.50607950 * this.vhadron.p()  -0.000460865322  *Math.pow(dedxDeutCTOF,2) + 1.18195972 * dedxDeutCTOF * this.vhadron.p() -.00666171502 * Math.pow(this.vhadron.p(),2);
      value = 1/(1+ Math.pow(Math.exp(1),-1*value));
      if (value < 0.5){
        cut = false;
      }
    }
    return cut;
  }

  public boolean PrelimExclusivitycut() {
    boolean cut = false;
    String missingpart="g";
    if(processInput.getPi0mode()){
      missingpart="m";
    }
    if (conf == 1) {
      cut =
          // (this.X("eh").mass2() < (-20/6* this.coneangle()+10) This is a test for when
          // i do tag evetns
          (this.X("eh").mass2() < (-1.5 * this.coneangle(missingpart) + 2)
              && this.X("eh").mass2() > -1 && this.X("eh").mass2()<1);
    } else if (conf == 2) {
      cut = (this.X("eh").mass2() < (-1 * this.coneangle(missingpart) + 2)
          && this.X("eh").mass2() > -1 && this.X("eh").mass2()<1
      // && this.X("eg").mass2()< +12-1.5*this.coneangle()
      );
    }
    return cut;
  }
  public boolean SelectPion(){
    boolean IsAPion=false;
    double mean=1.703491e-02;
    double sigma=3.581490e-03;
    if(vpion.px()<50){//This ensure there were two photons
      if(vpion.mass2()<mean+3*sigma && vpion.mass2()>mean-3*sigma){
        IsAPion=true;
      }
    }
    return IsAPion;
  }
  public boolean VertexCut(int runNumber){
    boolean vertexCut = false;
    if(processInput.getRGAmode()){
      if ( vertexElectron > -4 && vertexElectron < -1 && vertexElectron > (-1.5 + vertexDeuteron)
      && vertexElectron < (1.5 + vertexDeuteron)) {
    vertexCut = true;
      } 
    }
    else{
    if ( vertexElectron > -6 && vertexElectron < 0 && vertexElectron > (-1.5 + vertexDeuteron)
    && vertexElectron < (1.5 + vertexDeuteron)) {
  vertexCut = true;
    } 
// else if (runNumber > 6700 && vertexElectron > -7 && vertexElectron < 0 && vertexElectron > (-1.8 + vertexDeuteron)
//     && vertexElectron < (1.8 + vertexDeuteron)) {
//   vertexCut = true;
// }
    }
  return vertexCut;
  }
  public boolean PIDdeutCut(){
  boolean dedxCut = true;
// THESE ARE THE dedx cuts done manually, they should be removed since we are
    // looking into ML stuff
    if (inCTOF && vhadron.p() < 1.1 && vhadron.p() > 0.6 && dedxDeutCTOF < 4.3654
    *Math.pow(vhadron.p(),-1.851)){
    dedxCut = false;
    }
    if (inCTOF && dedxDeutCTOF > 11.464 *Math.pow(vhadron.p(),-1.161)){
    dedxCut = false;
    }
    if (inCND && vhadron.p() < 1.1 && vhadron.p() > 0.8 && dedxDeutCND < 3.628
    *Math.pow(vhadron.p(),-2.398)){
    dedxCut = false;
    }

   
    return dedxCut;
  }
  public boolean Exclusivitycut() {
    boolean cut = false;
    String missingpart="g";
    if(processInput.getPi0mode()){
      missingpart="m";
    }
    
   
    

    if (conf == 1) {
      cut =
          // (this.X("eh").mass2() < (-20/6* this.coneangle()+10) This is a test for when
          // i do tag evetns
          (
          // this.X("eh").mass2() < (-1.5* this.coneangle()+2)
          // && this.X("eh").mass2() >-2 && //Commenting these two lines becasue I move
          // the coneangle cut in the PrelimExclusivitycut
          // && ((this.beta()-this.BetaCalc()) > (0.05*this.chi2pid()-0.25))
          /* && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()+0.25)) */
          this.X("eh"+missingpart).mass2() > -0.75 && this.X("eh"+missingpart).mass2() < 0.25 && // was none
              this.X("eh"+missingpart).e() < 1 // Was 2, not a big change of FT
              && this.pPerp(missingpart) < 0.5
              && this.X("eh"+missingpart).p() < 0.5// was 1.5
              //&& this.tH().mass2() -this.tFX() >-0.24 &&  this.tH().mass2() -this.tFX() <0.12//NEW CUT on Delta t!
              //&& (this.tH().mass2() -this.tFX())>0.2-1.5*(-1*this.tFX())//NEW cut to remove low t Meh
              //&& this.X("e"+missingpart).mass2()<5 //(trying to remove the peak at 5)
              // && Math.abs(this.chi2pid()) < 3.5
              // && this.X("eh").mass() < 1.5//was 0.7
          // && dedxCut
          /* && getDedxDeut()> (-30*vhadron.p() +30) */);
    } else if (conf == 2) {

      cut = (
      // this.X("eh").mass2() < (-1* this.coneangle()+2)
      // && this.X("eh").mass2()>-2 && //Commenting these two lines becasue I move the
      // coneangle cut in the PrelimExclusivitycut
      // && ((this.beta()-this.BetaCalc()) > (0.05*this.chi2pid()-0.25))
      /* && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()+0.25)) */
      //tight this.X("eh"+missingpart).mass2() > -0.25 && this.X("ehg").mass2() < 0.08
      //tight && this.X("eh"+missingpart).e() < 2// was 3 //was 2// probably removing pions
      //tight && this.X("eh"+missingpart).p() < 0.3// was 1.5 //was 0.8
      this.X("eh"+missingpart).mass2() > -0.75 && this.X("ehg").mass2() < 0.25 // >-0.75 was <0.25
          && this.X("eh"+missingpart).e() < 3
          && this.pPerp(missingpart) < 0.5 
          && this.X("eh"+missingpart).p() < 0.8
          && this.deltaPhiPlane()>-0.5 && this.deltaPhiPlane()<0.5
          //&& this.tH().mass2() -this.tFX() >-0.3 &&  this.tH().mass2() -this.tFX() <0.09//NEW CUT on Delta t!
          //&& (this.tH().mass2() -this.tFX())>0.-0.6*(-1*this.tFX())//NEW CUT to remove low t MEH
          //&& this.X("e"+missingpart).mass2()<5 //(trying to remove the peak at 5)
          // && ((this.beta()-this.BetaCalc()) < (0.05*this.chi2pid()-0.1)
          // && Math.abs(this.chi2pid()) < 3.5
          // && this.X("eh").mass() < 0.7// was 1.5//was nothing - desperate attempt to reduce pion background
      // && dedxCut
      /* && getDedxDeut()> (-30*vhadron.p()+30) */);
    }
    return cut;
  }

  public double Xb() {
    return (-this.Q().mass2()) / (2 * 0.938 * (this.vBeam.e() - this.velectron.e()));
  }

  public double DPhi() {
    // LorentzVector temp = new LorentzVector();
    // temp.copy(this.X("eh"));
    // return vphoton.vect().phi() - temp.vect().phi();
    return vphoton.vect().phi() - this.X("eh").vect().phi();
  }

  public double PhiPlane() {
    double Phi;
    Vector3 leptonicPlane = vBeam.vect().cross(velectron.vect());
    Vector3 hadronicPlane = vhadron.vect().cross(vphoton.vect());
    
    Phi = leptonicPlane.theta(hadronicPlane);
    if (leptonicPlane.dot(vphoton.vect()) < 0) {
      return 360 - Phi;
    } else
      return Phi;
  }
  public double PhiPlanePi0() {
    double Phi;
    Vector3 leptonicPlane = vBeam.vect().cross(velectron.vect());
    Vector3 hadronicPlane = vhadron.vect().cross(vpion.vect());
    Phi = leptonicPlane.theta(hadronicPlane);
    if (leptonicPlane.dot(vphoton.vect()) < 0) {
      return 360 - Phi;
    } else
    return Phi;
  }
  public double deltaPhiPlane() {
    double deltaphi;
    // LorentzVector tmp=new LorentzVector();
    // tmp.copy(vBeam);
    // tmp.sub(velectron);

    //vector perp to hadron and the two electrons
    Vector3 norm_Had_VPho = (vhadron.vect().cross(Q().vect()));;
    //vector perp hadron and photon
    Vector3 norm_Had_Pho = (vhadron.vect().cross(vphoton.vect()));

    deltaphi= Math.toDegrees(Math.acos(norm_Had_VPho.dot(norm_Had_Pho)/norm_Had_VPho.mag()/norm_Had_Pho.mag()));
    // deltaphi = Math.toDegrees(norm_Had_Pho.angle(norm_Had_VPho));
    //angle between the plane containing electrons and hadron and plane containing hadron and photn
    //deltaphi = norm_Had_Pho.theta(norm_Had_VPho);
    
    if (norm_Had_VPho.dot(vphoton.vect()) < 0)
      deltaphi = -1 * deltaphi;
    // System.out.println(deltaphi); 
    // System.out.println(norm_Had_VPho.mag()); 
    // System.out.println(norm_Had_Pho.mag()); 
    // vhadron.print();
    // Q().print();
    return deltaphi;
  }

  public double deltaPhiPlane2() {
    //Different way to test coplanarity of the exclusive reaction
    double deltaphiplane;
    //vector perp to hadron and the two electrons
    Vector3 norm_Had_VPho = (vhadron.vect().cross(this.Q().vect()));
    //vector perp to two electrons and the photon (hadron is not used here!)
    Vector3 norm_VPho_Pho = (this.Q().vect().cross(vphoton.vect()));
    // deltaphiplane = Math.toDegrees(norm_Had_VPho.angle(norm_VPho_Pho));
    deltaphiplane = norm_Had_VPho.theta(norm_VPho_Pho);
    // Phi is always [0,180] but we want Phi's [0, 360] when hadronic plane
    // is below leptonic
    if (norm_Had_VPho.dot(vphoton.vect()) < 0)
      deltaphiplane = -1 * deltaphiplane;
    return deltaphiplane;
  }
  public double coneangle() {//default with no arguments is dvcs gamma
    return coneangle("g");
  }
  public double coneangle(String particle) {//particle can be "g" or "m" meson)
    LorentzVector temp = new LorentzVector();
    temp.copy(this.X("eh"));
    // return Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
    double result=0;
    if(particle == "m")
      result=this.vpion.vect().theta(temp.vect());
    else if(particle =="g")
      result= this.vphoton.vect().theta(temp.vect());
    return result;
  }
  public double coneangle_mis(String particle) {//particle can be "g" or "m" meson)
    LorentzVector temp = new LorentzVector();
    temp.copy(this.X_mis("eh"));
    // return Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
    double result=0;
    if(particle == "m")
      result=this.vpion.vect().theta(temp.vect());
    else if(particle =="g")
      result= this.vphoton.vect().theta(temp.vect());
    return result;
  }
  // public double coneanglepi0() {
  //   LorentzVector temp = new LorentzVector();
  //   temp.copy(this.X("eh"));
  //   // return Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
  //   return this.vpion.vect().theta(temp.vect());
  // }

  public double angleBetweenElectronPhoton() {

    // return Math.toDegrees(this.vphoton.vect().angle(velectron.vect()));
    return this.vphoton.vect().theta(velectron.vect());

  }

  public double DTheta() {
    // LorentzVector temp = new LorentzVector();
    // temp.copy(this.X("eh"));
    // return Math.toDegrees(vphoton.vect().angle(temp.vect()));
    // return Math.toDegrees(vphoton.vect().angle(this.X("eh").vect()));
    return vphoton.vect().theta(this.X("eh").vect());
  }

  // this function returns the missing vector for a given list of possible
  // particles in a dvcs events
  // could be ehg or eg eh
  public LorentzVector X(String listpart) {
    // System.out.println(listpart);
    // String newlistpart=Stream.of("cda").sorted(Comparator.comparingInt(o ->
    // Character.toLowerCase(o.charAt(0)))).collect(Collectors.joining());

    listpart = Stream.of(listpart.split(""))
        .sorted()
        .collect(Collectors.joining());

    // System.out.println(listpart);

    LorentzVector tmp = new LorentzVector();
    tmp.copy(W());
    // tmp.add(vTarget);
    // tmp.sub(velectron);
    if (listpart.equals("egh")) { //e hadron gamma
      tmp.sub(vphoton);
      tmp.sub(vhadron);
    } else if(listpart.equals("ehm")){//e hadron meson
      tmp.sub(vpion);
      tmp.sub(vhadron);
    } else if (listpart.equals("em")) {//e meson
      tmp.sub(vpion);
    } else if (listpart.equals("eg")) {//e gamma
      tmp.sub(vphoton);
    } else if (listpart.equals("eh")) {//e hadron
      tmp.sub(vhadron);
    } else {
      System.out
          .println(listpart + " combination of particle to calculate the missing particle is not supported, use e,g,h");
    }
    return tmp;
  }


  public LorentzVector X_mis(String listpart) {//Calculate X quantities  assumming proton for target and final particle
    // System.out.println(listpart);
    // String newlistpart=Stream.of("cda").sorted(Comparator.comparingInt(o ->
    // Character.toLowerCase(o.charAt(0)))).collect(Collectors.joining());

    listpart = Stream.of(listpart.split(""))
        .sorted()
        .collect(Collectors.joining());

    // System.out.println(listpart);

    LorentzVector tmp = new LorentzVector();
    tmp.copy(vBeam);
    tmp.add(vTargetP);
    tmp.sub(velectron);
    // tmp.add(vTarget);
    // tmp.sub(velectron);
    if (listpart.equals("egh")) { //e hadron gamma
      tmp.sub(vphoton);
      tmp.sub(vhadron_mis);
    } else if(listpart.equals("ehm")){//e hadron meson
      tmp.sub(vpion);
      tmp.sub(vhadron_mis);
    } else if (listpart.equals("em")) {//e meson
      tmp.sub(vpion);
    } else if (listpart.equals("eg")) {//e gamma
      tmp.sub(vphoton);
    } else if (listpart.equals("eh")) {//e hadron
      tmp.sub(vhadron_mis);
    } else {
      System.out
          .println(listpart + " combination of particle to calculate the missing particle is not supported, use e,g,h");
    }
    return tmp;
  }

  public double BetaCalc() {
    double betaCalc = vhadron.p() / Math.sqrt(MNUC * MNUC + vhadron.p() * vhadron.p());
    return betaCalc;
  }

  public double beta() {
    return betahad;
  }



  public double ctofen() {
    return ctofenergyhad;
  }

  
  public double chi2pid() {
    return chi2pidhad;
  }

  public double getVertexElectron() {
    return vertexElectron;
  }

  public double getVertexDeuteron() {
    return vertexDeuteron;
  }



  public static Map<Integer, List<Integer>> loadMapByIndex(
      Bank fromBank,
      String idxVarName) {

    Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    if (fromBank != null) {

      for (int iFrom = 0; iFrom < fromBank.getRows(); iFrom++) {
        final int iTo = fromBank.getInt(idxVarName, iFrom);
        if (!map.containsKey(iTo))
          map.put(iTo, new ArrayList<Integer>());
        map.get(iTo).add(iFrom);
      }
    }
    return map;
  }





  // public boolean FilterPositives(Bank particles, Bank scint){
  // Map<Integer,List<Integer>> scintMap = loadMapByIndex(scint,"pindex");

  // LorentzVector vtmp = new LorentzVector();
  // double ctofenpos=-10;
  // FoundPositives = false;

  // if(particles.getRows()>0){
  // for(int npart=0; npart<particles.getRows(); npart++){
  // int pid = particles.getInt("pid", npart);
  // //int status = particles.getInt("status", npart);
  // float beta = particles.getFloat("beta", npart);
  // int charge = particles.getInt("charge",npart);


//public double mpos;


  // if(charge >= 0){
  // FoundPositives = true;
  // FoundDeuteron = false;
  // FoundProton = false;
  // FoundKaon = false;
  // FoundPion = false;
  // FoundProton = false;
  // npositives++;
  // np = npart;
  // if(Math.abs(pid)==45){
  // mpos = this.MNUC;
  // FoundDeuteron = true;
  // }
  // else if(Math.abs(pid)==211){
  // mpos = this.MPION;
  // FoundPion = true;
  // }
  // else if(Math.abs(pid)==321){
  // mpos = this.MKAON;
  // FoundKaon = true;
  // }
  // else if(Math.abs(pid)==2212){
  // mpos = this.MPROT;
  // FoundProton = true;
  // }
  // this.setPositives(particles,scint,np);
  // }

  // if(scintMap.get(npart)!=null){
  // for (int iscint : scintMap.get(npart)) {
  // //System.out.println(scintMap.get(nh));
  // final byte layer = scint.getByte("layer",iscint);
  // final byte detector = scint.getByte("detector",iscint);
  // //System.out.println(detector);
  // if(detector==4){
  // if(FoundDeuteron==true){
  // ctofenergydeut = scint.getFloat("energy",iscint);
  // }
  // if (FoundKaon==true){
  // ctofenergykaon = scint.getFloat("energy",iscint);
  // }
  // if (FoundProton==true){
  // ctofenergyprot = scint.getFloat("energy",iscint);
  // }
  // if (FoundPion==true){
  // ctofenergypion = scint.getFloat("energy",iscint);
  // }
  // }
  // }
  // }
  // }
  // }
  // if (FoundDeuteron==true || FoundProton==true || FoundKaon==true||
  // FoundPion==true){
  // FoundPositives = true;
  // }
  // return FoundPositives;
  // }

  //BACKUP CODE
  //this code was after setHadron


        // double mass2MissingHadron = 1000.0;
        // double missingmass_chi2 = 0.0;
        // double chi2ofPhoton = 10000.0;
        // double coneangle_chi2 = 0.0;
        // int ng = -1;
        // // Photon Chi2 Selection hehe :)

        // for (int i = 0; i < photonsNumber.size(); i++) {
        //   LorentzVector tmp1 = new LorentzVector();
        //   tmp1.copy(vBeam);
        //   tmp1.add(vTarget);
        //   tmp1.sub(velectron);
        //   vphoton.setPxPyPzM(particles.getFloat("px", photonsNumber.get(i)),
        //       particles.getFloat("py", photonsNumber.get(i)),
        //       particles.getFloat("pz", photonsNumber.get(i)),
        //       0.0);
        //   tmp1.sub(vphoton);
        //   tmp1.sub(vhadron);
        //   // System.out.println(photonsNumber.size());
        //   // System.out.println(tmp1.mass2()- (1.87* 1.87));
        //   // if (Math.abs(tmp1.mass2() - (1.87* 1.87)) < mass2MissingHadron){
        //   // //Math.abs(tmp1.mass2() - (MNUC*MNUC))
        //   // mass2MissingHadron = Math.abs(tmp1.mass2() - (1.87*1.87));//
        //   // Math.abs(tmp1.mass2() - MNUC**2);
        //   // ng = photonsNumber.get(i);

        //   // }

        //   missingmass_chi2 = Math.abs(tmp1.mass());
        //   // System.out.println("The missing mass of the everything is : " +
        //   // missingmass_chi2);
        //   LorentzVector temp = new LorentzVector();
        //   temp.copy(this.X("eh"));
        //   // coneangle_chi2 = Math.toDegrees(this.vphoton.vect().angle(temp.vect()));
        //   coneangle_chi2 = this.vphoton.vect().theta(temp.vect());
        //   // System.out.println("The cone angle of the photon is :" + coneangle_chi2);
        //   if (chi2ofPhoton > coneangle_chi2 + missingmass_chi2) {

        //     chi2ofPhoton = coneangle_chi2 + missingmass_chi2;
        //     ng = photonsNumber.get(i);

        //   }

        // }
        // // System.out.println("FFFFFFFFFFFFFFFFFFFF The missing mass of the everything
        // // is : " + missingmass_chi2);
        // // System.out.println("FFFFFFFFFFFFFFFFFFFFFThe cone angle of the photon is :" +
        // // coneangle_chi2);
        // // System.out.println(" FFFFFFFFFFFFFFFFFFFFFFFThe total chi2 used to select the
        // // photon is "+ chi2ofPhoton);
        // status = particles.getInt("status", ng);
        // if (Math.abs(status) <= 2000)
        //   conf = 1;
        // else if (Math.abs(status) > 2000 && Math.abs(status) < 4000)
        //   conf = 2;

        // this.setPhoton_old(particles, calos, ng/* photonsNumber */);








        //MACHINE LEARNING CODE
                    // if(dedxDeutCND>0){
            // vtmp.setPxPyPzM(particles.getFloat("px",npart),
            // particles.getFloat("py",npart),
            // particles.getFloat("pz",npart),
            // this.MNUC);
            // double value = dedxDeutCTOF* 0.486 + dedxDeutCND * 0.469 + vtmp.p() * 6.87
            // -12.22;
            // if (1/(1+Math.exp(-value)) > 0.5){
            // ndeut++;
            // vtmp.setPxPyPzM(particles.getFloat("px",npart),
            // particles.getFloat("py",npart),
            // particles.getFloat("pz",npart),
            // this.MNUC);
            // if(vtmp.e()>this.d_en_max){
            // nd=npart;
            // this.d_en_max=vtmp.e();
            // }
            // }
            // }else{//this is the condition if there is only CTOF dedx

            // vtmp.setPxPyPzM(particles.getFloat("px",npart),
            // particles.getFloat("py",npart),
            // particles.getFloat("pz",npart),
            // this.MNUC);
            // double value = dedxDeutCTOF* 0.3241 + vtmp.p() * 2.2909 - 4.58;
            // if (1/(1+Math.exp( -value)) > 0.5){
            // ndeut++;
            // vtmp.setPxPyPzM(particles.getFloat("px",npart),
            // particles.getFloat("py",npart),
            // particles.getFloat("pz",npart),
            // this.MNUC);
            // if(vtmp.e()>this.d_en_max){
            // nd=npart;
            // this.d_en_max=vtmp.e();
            // }
            // }
            // }

            public void setPhoton_old(Bank particles, Bank calos, int ng/* ArrayList<Integer> photonsNumber */) {

              // LorentzVector remainingVector = new LorentzVector();
              // LorentzVector vtmp = new LorentzVector();
              // double energyLeft = 100000;
              // System.out.println(photonsNumber);
              // remainingVector.copy(this.vBeam);
              // remainingVector.add(this.vTarget).sub(this.velectron);
              // remainingVector.sub(this.vhadron);
              // double initialRemainingEnergy = remainingVector.e();
              // boolean positiveMissing = false;
          
              // if (initialRemainingEnergy < 0){
              // return positiveMissing;
              // }else{
          
              // vtmp.setPxPyPzM(particles.getFloat("px",photonsNumber.get(0)),
              // particles.getFloat("py",photonsNumber.get(0)),
              // particles.getFloat("pz",photonsNumber.get(0)),
              // 0.0);
              // energyLeft = Math.abs(remainingVector.e() - vtmp.e());
              // System.out.println("the initial energy is "+ vtmp.e());
          
              // ng = photonsNumber.get(0);
              // for(int i = 1; i < photonsNumber.size(); i++){
              // int pid = particles.getInt("pid", photonsNumber.get(i));
              // int status = particles.getInt("status", photonsNumber.get(i));
              // float beta = particles.getFloat("beta", photonsNumber.get(i));
          
              // vtmp.setPxPyPzM(particles.getFloat("px",photonsNumber.get(i)),
              // particles.getFloat("py",photonsNumber.get(i)),
              // particles.getFloat("pz",photonsNumber.get(i)),
              // 0.0);
          
              // System.out.println("The energy for this photon is " + Math.abs(vtmp.e()));
              // if(energyLeft > Math.abs(remainingVector.e()-vtmp.e())){
              // ng=photonsNumber.get(i);
              // energyLeft = Math.abs(remainingVector.e()-vtmp.e());
              // status = particles.getInt("status", photonsNumber.get(i));
              // beta = particles.getFloat("beta", photonsNumber.get(i));
          
              // if(status<2000)conf=1;
              // else if(status>=2000 && status<4000)conf=2;
          
              // }
          
              // }
              // positiveMissing = true;
              // }
          
              // System.out.println("end of event");
          
              // System.out.println("The end lowest energy is " + initialRemainingEnergy);
              vphoton.setPxPyPzM(particles.getFloat("px", ng),
                  particles.getFloat("py", ng),
                  particles.getFloat("pz", ng),
                  0.0);
          
              //
              // This correction holds only for data
              if (!processInput.getMCmode()) {
                if (Math.abs(particles.getInt("status", ng)) >= 1000 && Math.abs(particles.getInt("status", ng)) < 2000) {
                  // vphoton.print();
                  // System.out.println("here");
                  vphoton = Correct_FT_E(vphoton, 0.0);
                  vphoton = Correct_FT_theta(vphoton);
                  // vphoton.print();
                } else {
                  // System.out.println("here"+!processInput.getMCmode());
                  // vphoton = Correct_FD_E(vphoton, 0.0);
                }
              }
              //
          
              Map<Integer, List<Integer>> caloMap = loadMapByIndex(calos, "pindex");
              // photon_v = -10;
              // photon_w = -10;
              if (caloMap.get(ng) != null) {
                for (int icalo : caloMap.get(ng)) {
                  // System.out.println(scintMap.get(nh));
                  photon_layer = calos.getByte("layer", icalo);
                  final byte photon_detector = calos.getByte("detector", icalo);
                  photon_sector = calos.getByte("sector", icalo);
                  // System.out.println(electron_detector);
                  // System.out.println(detector);
                  if (photon_detector == 7 && photon_layer == 1) {// This is for ECAL in PCAL
                    photon_w = calos.getFloat("lw", icalo);
                    photon_v = calos.getFloat("lv", icalo);
                    photon_x = calos.getFloat("x", icalo);
                    photon_y = calos.getFloat("y", icalo);
                    // beforeFidCut++;
          
                  }
          
                }
              }
          
            }
          }



          // public void setPositives(Bank particles, Bank scint, int np){
  // if (this.FoundDeuteron==true){
  // vdeuteron.setPxPyPzM(particles.getFloat("px",np),
  // particles.getFloat("py",np),
  // particles.getFloat("pz",np),
  // this.MNUC);
  // betadeut=particles.getFloat("beta",np);
  // }
  // else if (this.FoundProton==true){
  // vproton.setPxPyPzM(particles.getFloat("px",np),
  // particles.getFloat("py",np),
  // particles.getFloat("pz",np),
  // this.MPROT);
  // betaprot=particles.getFloat("beta",np);
  // }
  // else if (this.FoundPion==true){
  // vpion.setPxPyPzM(particles.getFloat("px",np),
  // particles.getFloat("py",np),
  // particles.getFloat("pz",np),
  // this.MPION);
  // betapion=particles.getFloat("beta",np);
  // }
  // else if (this.FoundKaon==true){
  // vkaon.setPxPyPzM(particles.getFloat("px",np),
  // particles.getFloat("py",np),
  // particles.getFloat("pz",np),
  // this.MKAON);
  // betakaon=particles.getFloat("beta",np);
  // }
  // }



   // public String [] args;
  // public void setArgs(String [] argss){
  // System.out.println("Processing");
  // args = argss;
  // for(int i = 0; i < argss.length;i++){
  // System.out.println(argss[i]);
  // }

  // }


  // public boolean isML = false;
 

  // public void isML(boolean var) {
  //   isML = var;

  // }
  // processInput inputParam=new processInput(args);
//public double beforeFidCut = 0;
  // public double elec_layer_4_x;
  // public double elec_layer_4_y;
  // public double elec_layer_7_x;
  // public double elec_layer_7_y;
  // && Math.toDegrees(this.vphoton.theta())<5

  // public LorentzVector vpositive = new LorentzVector();
  // public LorentzVector vkaon = new LorentzVector();


  // elec_layer_4_x = -10000;
    // elec_layer_4_y = -10000;
    // elec_layer_7_x = -10000;
    // elec_layer_7_y = -10000;


     // boolean NewEvent=false;
  //double betapos = -10;
  //double betadeut = -10;
  //double betaprot = -10;
  //double betakaon = -10;
  //double betapion = -10;
  
  //double ctofenergypos = -10;
  //double ctofenergydeut = -10;
  //double ctofenergyprot = -10;
  //double ctofenergykaon = -10;
  //double ctofenergypion = -10;


  // public double betapos() {
  //   return betapos;
  // }
  // public double ctofenpos() {
  //   return ctofenergypos;
  // }



          // builder.append(vhadron.p() + ",");
        // builder.append("1\n");
        // else {
        // tmpdeutnoctof++;
        // //particles.show();
        // //scint.show();
        // }
          // StringBuilder builder = new StringBuilder();
  // String columnNames = "dedx,momentum,particle" ;
  // builder.append(columnNames+"\n");
   // builder.append(dedxDeutCTOF + ",");
          // builder.append(dedxDeutCND + ",");

          //was inside setHadron
    //           // this iswhat i commneted out on jan 26
    // if (processInput.getMLmode()) {
    //   builder.append(dedxDeutCTOF + ",");
    //   builder.append(dedxDeutCND + ",");
    //   builder.append(betahad + ",");

    //   builder.append(vhadron.p() + ",");
    //   builder.append(chi2pidhad + ",");
    //   builder.append("1\n");
    // }