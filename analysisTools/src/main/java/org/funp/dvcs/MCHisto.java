package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.fitter.*;
import org.jlab.groot.math.*;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.jlab.groot.ui.TCanvas;
import org.jlab.groot.data.TDirectory;

//import org.funp.dvcs.DvcsEvent;;

public class MCHisto {
  public H1F edgXmissingE; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingM2 ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingP ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPx;// missing px of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPy;// missing py of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPz;// missing pz of a complete DVCS final state e hadron gamma

  public H1F edXmissingE;

  public H1F edXmissingM2; // missing mass of hadron electron final state (to be compared with gamma)
  public H1F egXmissingM2; // missing mass of gamma electron final state (to be compared with hadron)
  public H1F egXmissingM;
  public H1F edXmissingM;
  public H2F egXmissingM2vsTh;

  public H1F MomentumX_elec;
  public H1F MomentumY_elec;
  public H1F MomentumZ_elec;
  public H1F Momentum_elec;
  public H1F Theta_elec;

  public H1F MomentumX_phot;
  public H1F MomentumY_phot;
  public H1F MomentumZ_phot;
  public H1F Momentum_phot;
  public H1F Theta_phot;

  public H1F MomentumX_deut;
  public H1F MomentumY_deut;
  public H1F MomentumZ_deut;
  public H1F Momentum_deut;
  public H1F Theta_deut;

  public H2F WvsQ2;
  public H2F Q2vsXbj;

  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'

  public H2F tvsq2;
  public H1F thisto;

public H1F DeltaPhiPlaneHist; //angle planes Q2/hadron and gamma/hadrom
  public H1F DeltaPhiPlaneMattHist;//angle planes Q2/hadron and Q2/gamma

  public String outputdir=new String(".");//changed to public for now
  public void setOutputDir(String otherdir){
    
    this.outputdir=otherdir;
    System.out.println("**** setting out dir for plots to" + this.outputdir);
  }

  public MCHisto() {

      MomentumX_elec = new H1F("pze",100,-3,3);
      MomentumX_elec.setTitleX("momentum x electron");
      MomentumY_elec = new H1F("pye",100,-3,3);
      MomentumY_elec.setTitleX("momentum y electron");
      MomentumZ_elec = new H1F("pze",100,-3,10);
      MomentumZ_elec.setTitleX("momentum Z electron");
      Momentum_elec = new H1F("pe",100,0,11);
      Momentum_elec.setTitleX("momentum electron");
      Theta_elec=new H1F("the",100,0,60);

      MomentumX_phot = new H1F("pxg",100,-3,3);
      MomentumX_phot.setTitleX("momentum x photon");
      MomentumY_phot = new H1F("pyg",100,-3,3);
      MomentumY_phot.setTitleX("momentum y photon");
      MomentumZ_phot = new H1F("pzg",100,-3,10);
      MomentumZ_phot.setTitleX("momentum Z photon");
      Momentum_phot = new H1F("pg",100,0,11);
      Momentum_phot.setTitleX("momentum photon");
      Theta_phot=new H1F("thg",100,0,60);

      MomentumX_deut = new H1F("pxd",100,-3,3);
      MomentumX_deut.setTitleX("momentum x deuteron");
      MomentumY_deut = new H1F("pyd",100,-3,3);
      MomentumY_deut.setTitleX("momentum y deuteron");
      MomentumZ_deut = new H1F("pzd",100,-3,3);
      MomentumZ_deut.setTitleX("momentum Z deuteron");
      Momentum_deut = new H1F("pd",100,0,3);
      Momentum_deut.setTitleX("momentum deuteron");
      Theta_deut=new H1F("thd",100,0,100);

      edgXmissingE = new H1F("edgXmissingE",100,0,10);
    edgXmissingE.setTitleX("eDγX Missing Energy [GeV]");
    edXmissingE = new H1F ("edXmissingE",100,-5,5);
    edXmissingE.setTitleX("edXmissingE");
    edgXmissingM2 = new H1F("edgXmissingM2",100,-4,4);
    edgXmissingM2.setTitleX("eDγX Missing Mass^2 [GeV/c^2]^2");
    edgXmissingP = new H1F("edgXmissingP",100,0,4);
    edgXmissingP.setTitleX("eDγX Missing Momentum [GeV/c]");
    edgXmissingPx = new H1F("MMomx",100,-1,1);
    edgXmissingPx.setTitleX("Missing X Momentum");
    edgXmissingPy = new H1F("MMomy",100,-1,1);
    edgXmissingPy.setTitleX("Missing Y Momentum");
    edgXmissingPz = new H1F("MMomz",100,-5,5);
    edgXmissingPz.setTitleX("Missing Z Momentum [GeV/c]");

    edXmissingM2 = new H1F("edXmissingM2",100,-10,10);
    edXmissingM2.setTitleX("eDX Missing Mass^2 [GeV/c^2]^2");
    egXmissingM2 = new H1F("egXmissingM2",100,-0,10);
    egXmissingM2.setTitleX("eGammaX Missing Mass2");
    edXmissingM = new H1F("eDXmissingM",100,-0,5);
    edXmissingM.setTitleX("eDX Missing Mass");
    egXmissingM = new H1F("egXmissingM",100,-0,5);
    egXmissingM.setTitleX("M_x(ed#rarrowe#gammaX)");
    egXmissingM2vsTh =new H2F("egXmissingM2vsTh",100,0,140,100,0,10);
    egXmissingM2vsTh.setTitle("egXmissing M2 vs Th");
    egXmissingM2vsTh.setTitleX("hadron theta");

    

    W= new H1F("W" ,100, 0, 10.0);
    W.setTitleX("W [GeV]");
    Q2 = new H1F("Q2",100, 0.1, 4.0);
    Q2.setTitleX("Q^2 [GeV/c^2]^2");
    

    thisto = new H1F("-t",100,0,5);
    thisto.setTitleX("-t [GeV/c]^2");

    WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
    WvsQ2.setTitleX("W [GeV]");
    WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj = new H2F("X_b vs Q^2","X_b vs Q^2",100,0,1,100,0,10);
    Q2vsXbj.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj.setTitle("X_b");
    tvsq2 = new H2F("t vs Q2", "t vs Q2",100,0,5,100,0,5);
    tvsq2.setTitle("t vs Q2");
    tvsq2.setTitleX("Q2");

    DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,-10,10);
    DeltaPhiPlaneHist.setTitleX("Delta Phi Plane");
    DeltaPhiPlaneMattHist = new H1F("DeltaPhiPlane",100,-100,100);
    DeltaPhiPlaneMattHist.setTitleX("Delta Phi Plane Hattawy");

  }

  public void fillBasicHisto(MCEvent ev) {

    W.fill(ev.W().mass());
    Q2.fill(-ev.Q().mass2());
    
    WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());
    Q2vsXbj.fill(ev.Xb(),-ev.Q().mass2());
    tvsq2.fill( -1*ev.Q().mass2(),-1*ev.t().mass2());
    thisto.fill(-1*ev.t().mass2());
    MomentumX_elec.fill(ev.velectron.px());
    MomentumY_elec.fill(ev.velectron.py());
    MomentumZ_elec.fill(ev.velectron.pz());
    Momentum_elec.fill(ev.velectron.p());
    Theta_elec.fill((Math.toDegrees(ev.velectron.theta())));
    Theta_phot.fill((Math.toDegrees(ev.vphoton.theta())));
    Theta_deut.fill((Math.toDegrees(ev.vhadron.theta())));


    MomentumX_phot.fill(ev.vphoton.px());
    MomentumY_phot.fill(ev.vphoton.py());
    MomentumZ_phot.fill(ev.vphoton.pz());
    Momentum_phot.fill(ev.vphoton.p());

    MomentumX_deut.fill(ev.vhadron.px());
    MomentumY_deut.fill(ev.vhadron.py());
    MomentumZ_deut.fill(ev.vhadron.pz());
    Momentum_deut.fill(ev.vhadron.p());


    edgXmissingE.fill(ev.X("ehg").e());
    edgXmissingM2.fill(ev.X("ehg").mass2());
    edgXmissingP.fill(ev.X("ehg").p());
    edgXmissingPx.fill(ev.X("ehg").px());
    edgXmissingPy.fill(ev.X("ehg").py());
    edgXmissingPz.fill(ev.X("ehg").pz());

    edXmissingE.fill(ev.X("eg").e());
    edXmissingM2.fill(ev.X("eh").mass2());
    egXmissingM2.fill(ev.X("eg").mass2());
    edXmissingM.fill(ev.X("eh").mass());
    egXmissingM.fill(ev.X("eg").mass());
    egXmissingM2vsTh.fill(Math.toDegrees(ev.vhadron.theta()),ev.X("eg").mass2());
    DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());
    DeltaPhiPlaneMattHist.fill(ev.deltaPhiPlane2());

  }

  public void DrawMissingQuants(TCanvas ec4){

    ec4.divide(4,4);
    String homeDir = System.getenv("HOME");
    TDirectory dir = new TDirectory();
    String directory = "/test";
    dir.mkdir(directory);
    dir.cd(directory);
    dir.addDataSet(edXmissingE);
    dir.writeFile("myfile.hipo");
    //ec4.cd(0).draw(edgXmissingE);
    ec4.cd(0).draw(edXmissingE);
    ec4.cd(1).draw(edgXmissingM2);
    ec4.cd(2).draw(edgXmissingP);
   

    ec4.cd(3).draw(edXmissingM);
    ec4.cd(4).draw(edXmissingM2);
    ec4.cd(5).draw(egXmissingM);
    
    ec4.cd(6).draw(DeltaPhiPlaneHist);
    ec4.cd(7).draw(DeltaPhiPlaneMattHist);
    ec4.cd(8).draw(edgXmissingPx);
    ec4.cd(9).draw(edgXmissingPy);
    ec4.cd(10).draw(edgXmissingPz);
    ec4.cd(12).draw(egXmissingM2);
    ec4.getCanvas().getScreenShot();
    System.out.println(this.outputdir+"/"+ec4.getTitle()+".png" );
    ec4.getCanvas().save(this.outputdir+"/"+ec4.getTitle()+".png");
    //ec4.getScreenShot();



  }

  public void DrawMomentums(TCanvas ec4){

    ec4.divide(3,3);
   
    //ec4.cd(0).draw(edgXmissingE);
    ec4.cd(0).draw(MomentumX_elec);
    ec4.cd(1).draw(MomentumY_elec);
    ec4.cd(2).draw(MomentumZ_elec);
   

    ec4.cd(3).draw(MomentumX_phot);
    ec4.cd(4).draw(MomentumY_phot);
    ec4.cd(5).draw(MomentumZ_phot);

    ec4.cd(6).draw(MomentumX_deut);
    ec4.cd(7).draw(MomentumY_deut);
    ec4.cd(8).draw(MomentumZ_deut);
  
    ec4.getCanvas().getScreenShot();
    System.out.println(this.outputdir+"/"+ec4.getTitle()+".png" );
    ec4.getCanvas().save(this.outputdir+"/"+ec4.getTitle()+".png");
    //ec4.getScreenShot();



  }

  public void DrawKinematics(TCanvas ec4){

    ec4.divide(3,2);
   
    //ec4.cd(0).draw(edgXmissingE);
    ec4.cd(0).draw(W);
    ec4.cd(1).draw(Q2);
    ec4.cd(2).draw(thisto);
    ec4.cd(3).draw(WvsQ2);
   

    ec4.cd(4).draw(Q2vsXbj);
    ec4.cd(5).draw(tvsq2);
   
    ec4.getCanvas().getScreenShot();
    System.out.println(this.outputdir+"/"+ec4.getTitle()+".png" );
    ec4.getCanvas().save(this.outputdir+"/"+ec4.getTitle()+".png");
    //ec4.getScreenShot();



  }
  public  void writeHipooutput(TDirectory rootdir,String directory){
    String hipodirectory = "/"+directory;
    String[] sub={hipodirectory+"/Kine",hipodirectory+"/Excl",hipodirectory+"/Pid",hipodirectory+"/Asym"};
    rootdir.mkdir(sub[0]);
    rootdir.cd(sub[0]);
    rootdir.addDataSet(MomentumX_elec);
    rootdir.addDataSet(MomentumY_elec);
    rootdir.addDataSet(MomentumZ_elec);
    rootdir.addDataSet(Momentum_elec);
    rootdir.addDataSet(Theta_elec);

    rootdir.addDataSet(MomentumX_phot);
    rootdir.addDataSet(MomentumY_phot);
    rootdir.addDataSet(MomentumZ_phot);
    rootdir.addDataSet(Momentum_phot);
    rootdir.addDataSet(Theta_phot);

    rootdir.addDataSet(MomentumX_deut);
    rootdir.addDataSet(MomentumY_deut);
    rootdir.addDataSet(MomentumZ_deut);
    rootdir.addDataSet(Momentum_deut);
    rootdir.addDataSet(Theta_deut);
    rootdir.addDataSet(WvsQ2);

    rootdir.addDataSet(Q2vsXbj);
    rootdir.addDataSet(W);
    rootdir.addDataSet(Q2);
    rootdir.addDataSet(tvsq2);
    rootdir.addDataSet(thisto);

  }


}