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
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

import org.jlab.groot.ui.TCanvas;

//import org.funp.dvcs.DvcsEvent;;

public class DvcsHisto {


  public H2F dedxCTOFvsP;
  public H2F dedxCNDvsP;

  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'
  public H1F hadmom;
  public H2F WvsQ2;
  public H2F Q2vsXbj;
//Nick Add
  public H1F VertexElectron;
  public H1F VertexDuetron;
  public H2F vertexElecVSvertexDeut;


  public H2F dedxDeutvsP;
  //Missing quantities
  //public H1F MMass;// missing mass of a complete DVCS final state e hadron gamma
  //public H1F MMom;// missing mom of a complete DVCS final state e hadron gamma

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

  public H2F ThvsPhi;//Theta vs phi for hadron
  public H2F elecThvsPhi;
  public H2F photThvsPhi;
  public H2F ThvsP;//Theta vs mom for hadron
  public H2F elecThvsP;
  public H2F photThvsP;

  public H2F tvsq2;

  //public H2F MMvsMpz;
  //public H2F MpxvsMpz;

  public H1F hgTh;//theta gamma
  public H1F hgEn;//Energy gamma
  //public H1F DAngleGammaHist ; //angle between gamma vector and missing hadron+e vector
  public H1F ConeAngleHist;//angle between gamma vector and missing hadron+e vector
  public H1F MissThetaHist;//theta missing hadron+e vector
  public H1F PhiPlaneHist ; //angle between electrons plane and hadron/gamma plane
  public H1F DPhiHist ;//phi gamma minus phi missing hadron+e vector
  public H1F DeltaPhiPlaneHist; //angle planes Q2/hadron and gamma/hadrom
  public H1F DeltaPhiPlaneMattHist;//angle planes Q2/hadron and Q2/gamma

  public H1F ConeAngleBtElectronPhotonFD;

  public H2F coneanglevsedgXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
  public H2F coneanglevsedXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehX
  public H2F coneanglevspperp;
  public H2F coneanglevsegXM2;
  public H1F betahadhisto;
  public H1F betacalchisto;
  public H2F betavsP;
  public H2F betavsPdeut;
  public H2F betavsPprot;
  public H2F betavsPpion;
  public H2F betavsPkaon;
  public H2F betavsPplus;
  public H2F betacalcvsP;
  public H1F deltabeta;


  public H2F ctofdedxvsp;
  public H2F ctofdedxvspplus;
  public H2F ctofdedxvspdeut;
  public H2F ctofdedxvspprot;
  public H2F ctofdedxvsppion;
  public H2F ctofdedxvspkaon;

  public H2F phivshelicityPlus;
  public H2F phivshelicityMinus;
  public H2F XvsY_electron;
  //public H2F XvsY_electron_after;


  public H1F chisqHad;

  public H2F chi2vsdeltabeta;

  public H1F helicityhisto;
  public H1F helicityrawhisto;

  public H1F Phiplus;
  public H1F Phiminus;

  public H1F thisto;
  public H1F pPerphisto;

  private String outputdir=new String(".");
  public void setOutputDir(String otherdir){
    
    this.outputdir=otherdir;
    System.out.println("**** setting out dir for plots to" + this.outputdir);
  }

  public DvcsHisto() {
    
    XvsY_electron = new H2F("X vs Y", "X vs Y",100,-400,400,100,-400,400);
    //XvsY_electron_before = new H2F("X vs Y", "X vs Y",100,-400,400,100,-400,400);
    
    dedxCTOFvsP = new H2F("dedx CTOF vs P", "dedx CTOF vs P", 100,0,2,100,0,30);
    dedxCTOFvsP.setTitle("dE/dx CTOF vs P");

    dedxCNDvsP = new H2F("dedx CND vs P", "dedx CND vs P", 100,0,2,100,0,30);
    dedxCNDvsP.setTitle("dE/dx CND vs P");


    W= new H1F("W" ,100, 0, 10.0);
    W.setTitleX("W [GeV]");
    Q2 = new H1F("Q2",100, 0.1, 4.0);
    Q2.setTitleX("Q^2 [GeV/c^2]^2");
    hadmom = new H1F("Hadron Momentum",100,0,10.0);
    hadmom.setTitleX("Deteron momentum [GeV/c]");
    WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
    WvsQ2.setTitle("W [GeV]");
    WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj = new H2F("X_b vs Q^2","X_b vs Q^2",100,0,1,100,0,10);
    Q2vsXbj.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj.setTitle("X_b");

    //Nick Add
    dedxDeutvsP = new H2F("dedxDeutvsP","dedxDeutvsP",100,0,2,100,0,50);
    coneanglevspperp = new H2F("Cone Angle vs Pperp", "Cone Angle vs Pperp", 100,0,10,100,0,1);
    coneanglevspperp.setTitle("Cone Angle vs Pperp");
    VertexElectron = new H1F("Vertex Electron", 100,-10.0,10.0);
    VertexElectron.setTitleX("Vertex Electron");
    VertexDuetron = new H1F("Vertex Dueteron", 100,-10.0,10.0);
    VertexDuetron.setTitleX("Vertex Dueteron");
    vertexElecVSvertexDeut = new H2F ("Vertex Electron vs Vertex Deuteron","Vertex Electron vs Vertex Deuteron",100,-10,10,100,-10,10);
    vertexElecVSvertexDeut.setTitle("Vertex electron vs Vertex Deuteron");

    phivshelicityPlus = new H2F("phi vs helicity", "phi vs helicity", 100,0,360,100,-4,4);
    phivshelicityPlus.setTitle("helicity vs phi (Plus)");
    phivshelicityMinus = new H2F("phi vs helicity", "phi vs helicity", 100,0,360,100,-4,4);
    phivshelicityMinus.setTitle("helicity vs phi (Plus)");
    //MMass = new H1F("MMass",100,-10,10);
    //MMass.setTitleX("Missing Mass Squared");
    //MMom = new H1F("MMom",100,0,10);
    //MMom.setTitleX("Missing Momentum");


    edgXmissingE = new H1F("edgXmissingE",100,0,10);
    edgXmissingE.setTitleX("eDγX Missing Energy [GeV]");
    edXmissingE = new H1F ("edXmissingE",100,-5,5);
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
    ThvsPhi = new H2F("Deuteron #theta vs #phi","Deuteron #theta vs #phi",100,-180,180,100,0,180);
    ThvsPhi.setTitleX("#phi [Degrees]");
    ThvsPhi.setTitleY("#theta [Degrees]");
    ThvsP = new H2F("Deuteron p vs #theta","Deuteron p vs #theta ",100,0,180,100,0,10.6);
    ThvsP.setTitleY("p [GeV/c]");
    ThvsP.setTitle("#theta [Degrees]");
    tvsq2 = new H2F("t vs Q2", "t vs Q2",100,0,5,100,0,5);
    tvsq2.setTitle("t vs Q2");
    tvsq2.setTitleX("Q2");
    elecThvsPhi = new H2F("Electron #theta vs #phi","Electron #theta vs #phi",100,-180,180,100,0,100);
    elecThvsPhi.setTitleX("#phi [Degrees]");
    elecThvsPhi.setTitleY("#theta [Degrees]");
    elecThvsP = new H2F("Electron p vs #theta","Electron p vs #theta ",100,0,40,100,0,10.6);
    elecThvsP.setTitle("p [GeV/c]");
    elecThvsP.setTitleX("#theta [Degrees]");

    photThvsPhi = new H2F("Photon #theta vs #phi","Photon #theta vs #phi",100,-180,180,100,0,40);
    photThvsPhi.setTitleX("#phi [Degrees]");
    photThvsPhi.setTitleY("#theta [Degrees]");
    photThvsP = new H2F("Photon p vs #theta","Photon p vs #theta ",100,0,40,100,0,10.6);
    photThvsP.setTitleY("p [GeV/c]");
    photThvsP.setTitleX("#theta [Degrees]");

    //MMvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MMvsMpz.setTitleX("Missing Mass");
    //MMvsMpz.setTitleY("Missing Z Momentum");
    //MpxvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MpxvsMpz.setTitleX("Missing X Momentum");
    //MpxvsMpz.setTitleY("Missing Z Momentum");
    hgTh = new H1F("hgTh",100,0,50);
    hgTh.setTitleX("Photon Theta");
    hgEn = new H1F("Photon energy",100,0,10);
    hgEn.setTitleX("Photon Energy [GeV/c^2]");
    //DAngleGammaHist = new H1F("DAngleGammaHist",100,-15,100);
    //DAngleGammaHist.setTitle("Angle between gamma and missing eDX");
    ConeAngleHist = new H1F("ConeAngleHist",100,-3,10);
    ConeAngleHist.setTitleX("Angle between gamma and missing eDX");
    MissThetaHist = new H1F("MissThetaHist",100,0,180);
    MissThetaHist.setTitleX("Missing Photon Theta");
    PhiPlaneHist = new H1F("PhiPlaneHist",100,0,360);
    PhiPlaneHist.setTitleX("#phi, the angle between the Leptonic and Hadronic planes");
    DPhiHist = new H1F("DPhiHist",100,-10,10);
    DPhiHist.setTitle("DPhi");
    DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,-10,10);
    DeltaPhiPlaneHist.setTitleX("Delta Phi Plane");
    DeltaPhiPlaneMattHist = new H1F("DeltaPhiPlane",100,-100,100);
    DeltaPhiPlaneMattHist.setTitleX("Delta Phi Plane Hattawy");

     ConeAngleBtElectronPhotonFD = new H1F("Cone Angle Bt Electron and Photon", 100,0,80);
     ConeAngleBtElectronPhotonFD.setTitleX("Cone Angle Between Electron and Photon");
    coneanglevsedgXM2 = new H2F("eDGammaX missing M2 vs Cone Angle","eDGammaX missing M2 vs Cone Angle",100,0,20,100,-1,0.5);
    coneanglevsedgXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsedgXM2.setTitleY("eDGammaX missing M2 (GeV)");
    coneanglevsedXM2 = new H2F("(M_x)^2(ed#rarrowededX) vs #theta#_{#gamma,x} ","(M_x)^2(ed#rarrowededX) vs #theta#_{#gamma,x} ",100,0,20,100,-10,10);
    coneanglevsedXM2.setTitleX("#theta#_{#gamma,x} [deg.]");
    coneanglevsedXM2.setTitleY("(M_x)^2(ed#rarrowed#X) [GeV/c^2]");
    coneanglevsegXM2 = new H2F("egX missing M2 vs Cone Angle","egX missing M2 vs Cone Angle",100,0,20,100,0,20);
    coneanglevsegXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsegXM2.setTitleY("egX missing M2 (GeV)");

    //pid histograms
    betavsP = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betavsPplus = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betavsPdeut = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betavsPprot = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betavsPpion = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betavsPkaon = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betacalchisto = new H1F("#beta_calc","#beta_calc",100,0,1);
    betacalchisto.setTitleX("#beta calculated from relativistic momentum");
    betahadhisto = new H1F("#beta","#beta",100,0,1);
    betacalchisto.setTitleX("Measured #beta");
    betacalcvsP = new H2F("BetaCalc vs P","BetaCalc vs P", 100,0,10.2,100,0,1.1);
    deltabeta = new H1F("Beta - BetaCalc",100,-0.6,0.2);
    deltabeta.setTitleX("Beta - BetaCalc");

    ctofdedxvsp=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspplus=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspdeut=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspprot=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvsppion=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspkaon=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    chisqHad=new H1F("Chi2Pid",100,-25,25);
    chisqHad.setTitle("ChiSquared PID");

    chi2vsdeltabeta=new H2F("#Delta#beta_d vs #chi^2_PID","#Delta#beta_d vs #chi^2_PID",100,-30,30,100,-0.6,0.6);
    chi2vsdeltabeta.setTitleX("#chi^2_PID");
    chi2vsdeltabeta.setTitleY("#Delta#beta_d");

    helicityhisto=new H1F("Helicity",9,-4,4);
    helicityhisto.setTitleX("Beam Helicity");
    helicityrawhisto=new H1F("Helicity Raw",9,-4,4);
    helicityrawhisto.setTitleX("Helicity Raw");

    Phiplus = new H1F("Phiplus",10,0,360);
    Phiplus.setTitleX("Phi Plus");
    Phiminus = new H1F("Phiminus",10,0,360);
    Phiminus.setTitleX("Phi Minus");
    thisto = new H1F("-t",100,0,5);
    thisto.setTitleX("-t [GeV/c]^2");
    pPerphisto = new H1F("pPerp","pPerp",100,0,3);
    //System.out.println("creating histograms"  );
  }
  

  public void fillBasicHisto(DvcsEvent ev) {
    dedxCTOFvsP.fill(ev.vhadron.p(),ev.dedxDeutCTOF);
    dedxCNDvsP.fill(ev.vhadron.p(),ev.dedxDeutCND);
  
    coneanglevspperp.fill(ev.coneangle(), ev.pPerp());
    XvsY_electron.fill(ev.elec_x,ev.elec_y);
   // XvsY_electron_after.fill(ev.elec_x,ev.elec_y);
    W.fill(ev.W().mass());
    Q2.fill(-ev.Q().mass2());
    hadmom.fill(ev.vhadron.p());
    WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());
    Q2vsXbj.fill(ev.Xb(),-ev.Q().mass2());
    tvsq2.fill( -1*ev.Q().mass2(),-1*ev.t().mass2());

    //missing quantities of a complete DVCS final state e hadron gamma
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

    ThvsPhi.fill(Math.toDegrees(ev.vhadron.phi()),Math.toDegrees(ev.vhadron.theta()));
    elecThvsPhi.fill(Math.toDegrees(ev.velectron.phi()),Math.toDegrees(ev.velectron.theta()));
    photThvsPhi.fill(Math.toDegrees(ev.vphoton.phi()),Math.toDegrees(ev.vphoton.theta()));
    ThvsP.fill(Math.toDegrees(ev.vhadron.theta()),ev.vhadron.p());
    elecThvsP.fill(Math.toDegrees(ev.velectron.theta()),ev.velectron.p());
    photThvsP.fill(Math.toDegrees(ev.vphoton.theta()),ev.vphoton.p());
//Xbj=ev.Xb();

    hgTh.fill(Math.toDegrees(ev.vphoton.theta()));
    hgEn.fill(ev.vphoton.e());
    //DAngleGammaHist.fill(ev.DTheta());
    ConeAngleHist.fill(ev.coneangle());
    MissThetaHist.fill(Math.toDegrees(ev.X("eh").theta()));
    PhiPlaneHist.fill(ev.PhiPlane());
    DPhiHist.fill(ev.DPhi());
    DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());
    DeltaPhiPlaneMattHist.fill(ev.deltaPhiPlane2());

    coneanglevsedgXM2.fill(ev.coneangle(),ev.X("egh").mass2());
    coneanglevsedXM2.fill(ev.coneangle(),ev.X("eh").mass2());
    coneanglevsegXM2.fill(ev.coneangle(),ev.X("eg").mass2());

    ConeAngleBtElectronPhotonFD.fill(ev.angleBetweenElectronPhoton()); 

    betavsP.fill(ev.vhadron.p(),ev.beta());
    betahadhisto.fill(ev.beta());
    betacalchisto.fill(ev.BetaCalc());
    betacalcvsP.fill(ev.vhadron.p(),ev.BetaCalc());
    deltabeta.fill(ev.beta()-ev.BetaCalc());

    ctofdedxvsp.fill(ev.vhadron.p(),ev.ctofen());
    //NickAdd
    VertexDuetron.fill(ev.getVertexDeuteron());
    VertexElectron.fill(ev.getVertexElectron());
    vertexElecVSvertexDeut.fill(ev.getVertexElectron(),ev.getVertexDeuteron());
    dedxDeutvsP.fill(ev.vhadron.p(),ev.getDedxDeut());
    chisqHad.fill(ev.chi2pid());
    chi2vsdeltabeta.fill(ev.chi2pid(),ev.beta()-ev.BetaCalc());

    helicityhisto.fill(ev.helicity);
    helicityrawhisto.fill(ev.helicityraw);
    thisto.fill(-1*ev.t().mass2());
    pPerphisto.fill(ev.pPerp());

    if(ev.helicity==1){
      Phiplus.fill(ev.PhiPlane());
      phivshelicityPlus.fill(ev.PhiPlane(), ev.helicity);
    }
    else if (ev.helicity==-1){
      Phiminus.fill(ev.PhiPlane());
      phivshelicityMinus.fill(ev.PhiPlane(), ev.helicity);
    }


  }

  /*This stuff is all wrong for positive particles */

  // public void fillPositives(DvcsEvent ev){
  //   //betavsPplus.fill(ev.vpositive.p(),ev.betapos());
  //   //ctofdedxvspplus.fill(ev.vpositive.p(),ev.ctofenpos());
  //   //if (ev.FoundDeuteron==true){
  //     betavsPdeut.fill(ev.vdeuteron.p(),ev.betadeut);
  //     betavsPdeut.setTitle("Beta vs Momentum Deuteron");
  //     ctofdedxvspdeut.fill(ev.vdeuteron.p(),ev.ctofenergydeut);
  //     ctofdedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
  //   //}
  //   //else if (ev.FoundProton==true){
  //     betavsPprot.fill(ev.vproton.p(),ev.betaprot);
  //     betavsPprot.setTitle("Beta vs Momentum Proton");
  //     ctofdedxvspdeut.fill(ev.vproton.p(),ev.ctofenergyprot);
  //     ctofdedxvspdeut.setTitle("dE/dx vs Momentum Proton");
  //   //}
  //   //else if (ev.FoundPion==true){
  //     betavsPpion.fill(ev.vpion.p(),ev.betapion);
  //     betavsPpion.setTitle("Beta vs Momentum #pi+");
  //     ctofdedxvspdeut.fill(ev.vpion.p(),ev.ctofenergypion);
  //     ctofdedxvspdeut.setTitle("dE/dx vs Momentum #pi+");
  //   //}
  //   //else if (ev.FoundKaon==true){
  //     betavsPkaon.fill(ev.vkaon.p(),ev.betakaon);
  //     betavsPkaon.setTitle("Beta vs Momentum #kappa");
  //     ctofdedxvspdeut.fill(ev.vkaon.p(),ev.ctofenergykaon);
  //     ctofdedxvspdeut.setTitle("dE/dx vs Momentum #kappa");
  //   //}
  // }

  // public void drawPositives(TCanvas ecP){
  //   ecP.divide(2,2);
  //   /*betavsPplus.add(betavsPprot);
  //   betavsPplus.add(betavsPdeut);
  //   betavsPplus.add(betavsPpion);
  //   betavsPplus.add(betavsPkaon);
  //   //ecP.getPad().getAxisZ().setLog(true);
  //   ecP.draw(betavsPplus);*/
  //   ecP.cd(0).draw(betavsPdeut);
  //   ecP.cd(1).draw(betavsPprot);
  //   ecP.cd(2).draw(betavsPpion);
  //   ecP.cd(3).draw(betavsPkaon);
  // }

  public H1F buildAsym(){
  H1F num;
  H1F denom;
  H1F Asym;
  num = new H1F("num",10,0,360);
  denom = new H1F("denom",10,0,360);
  Asym = new H1F("Asymmetry","Asymmetry",10,0,360);
  num.add(this.Phiplus);
  num.sub(this.Phiminus);
  denom.add(this.Phiplus);
  denom.add(this.Phiminus);
  denom.normalize(0.8);
  Asym = Asym.divide(num,denom);
  Asym.setTitleX("#phi [deg.]");
  Asym.setTitleY("A_LU(#phi)");
  return Asym;
 }
  public void drawPlot(TCanvas ecP){
    //ecP.getPad().getAxisZ().setLog(true);
    this.drawAsym(ecP);
  }
  public void drawAsym(TCanvas ecA){
  ecA.getPad().setAxisRange(0, 360, -0.6, 0.6);
	ecA.draw((this.buildAsym()),"E");

  F1D Asymfunc = new F1D("Asymfunc","[A]*sin([B]x)+[C]",0,360);
  Asymfunc.setParameter(0,0.2);
  Asymfunc.setParameter(1,0.01);
  Asymfunc.setParameter(2,-0.2);
  DataFitter.fit(Asymfunc,this.buildAsym(),"");
  ecA.draw(Asymfunc,"same");
  ecA.getCanvas().save(this.outputdir+"/"+ecA.getTitle()+".png");
}

  
  
  


  public void DrawParticleComparison(TCanvas ecPP){
    ecPP.divide(2,4);
    ecPP.cd(0).draw(elecThvsPhi);
    ecPP.cd(1).draw(elecThvsP);
    ecPP.cd(2).draw(photThvsPhi);
    ecPP.cd(3).draw(photThvsP);
    ecPP.cd(4).draw(ThvsPhi);
    ecPP.cd(5).draw(ThvsP);
    ecPP.cd(6).draw(dedxDeutvsP);
    ecPP.getCanvas().getScreenShot();
    System.out.println(this.outputdir+"/"+ecPP.getTitle()+".png" );
    ecPP.getCanvas().save(this.outputdir+"/"+ecPP.getTitle()+".png");

  }

  public void DrawMissingQuants(TCanvas ec4){

    ec4.divide(4,4);
    //ec4.cd(0).draw(edgXmissingE);
    ec4.cd(0).draw(edXmissingE);
    ec4.cd(1).draw(edgXmissingM2);
    ec4.cd(2).draw(edgXmissingP);
   

    ec4.cd(3).draw(edXmissingM);
    ec4.cd(4).draw(edXmissingM2);
    ec4.cd(5).draw(egXmissingM);
    ec4.cd(6).draw(egXmissingM2);

    ec4.cd(7).draw(edgXmissingPx);
    ec4.cd(8).draw(edgXmissingPy);
    ec4.cd(9).draw(edgXmissingPz);
    ec4.cd(10).draw(MissThetaHist);
    ec4.cd(11).draw(DeltaPhiPlaneHist);
    ec4.cd(12).draw(DeltaPhiPlaneMattHist);
    ec4.getCanvas().getScreenShot();
    System.out.println(this.outputdir+"/"+ec4.getTitle()+".png" );
    ec4.getCanvas().save(this.outputdir+"/"+ec4.getTitle()+".png");
    //ec4.getScreenShot();



  }
  public void DrawKinematics(TCanvas ec){
    ec.divide(5,5);
    ec.cd(0).draw(WvsQ2);
    ec.cd(1).draw(Q2vsXbj);
    ec.cd(2).draw(betacalcvsP);

    ec.cd(3).draw(chi2vsdeltabeta);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(4).draw(W);
    ec.cd(5).draw(hgTh);
    ec.cd(6).draw(hgEn);
    ec.cd(7).draw(Q2);
    ec.cd(8).draw(ConeAngleHist);
    ec.cd(9).draw(ConeAngleBtElectronPhotonFD);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(10).draw(PhiPlaneHist);
    ec.cd(11).draw(DPhiHist);
    
    ec.cd(12).draw(tvsq2);
    //ec.cd(25).draw(helicityrawhisto);
    ec.cd(13).draw(phivshelicityPlus);
    ec.cd(14).draw(phivshelicityMinus);
    //ec.cd(14).draw(deltabeta);
    ec.cd(15).draw(chisqHad);
    ec.cd(16).draw(betacalchisto);
    ec.cd(17).draw(betahadhisto);
    ec.cd(18).draw(XvsY_electron);
    ec.cd(19).draw(thisto);
    ec.cd(20).draw(VertexElectron);
    ec.cd(21).draw(VertexDuetron);
    ec.cd(22).draw(vertexElecVSvertexDeut);
    // ec.cd(23).draw(dedxCNDvsP);
    // ec.cd(24).draw(dedxCTOFvsP);
    ec.cd(23).draw(Phiplus);
    ec.cd(24).draw(Phiminus);


    //i removed this jsut because its not useful and i wanted to make plot 4/5
//    ec.cd(14).draw(helicityhisto);
    

    ec.getCanvas().getScreenShot();
    ec.getCanvas().save(this.outputdir+"/"+ec.getTitle()+".png");


    //ec.divide(4,3);
    //ec2.cd(0).draw(DAngleGammaHist);

    // ec2.cd(10).draw(ThvsPhi);
    // ec2.cd(11).draw(MMomx);
    // ec2.cd(12).draw(MMomy);
    // ec2.cd(13).draw(MMomz);
    // ec2.cd(14).draw(edgXmissingE);
    // ec2.cd(15).draw(edgXmissingM2);
    // ec2.cd(16).draw(edgXmissingP);
    // ec2.cd(17).draw(edXmissingM2);
    // ec2.cd(18).draw(egXmissingM2);
    // ec2.cd(19).draw(ThvsPhi);
    // ec2.cd(20).draw(hgTh);
  }
  public void DrawConeAngle(TCanvas ec){
    ec.divide(2,2);
    ec.cd(0).draw(coneanglevspperp);
    ec.cd(1).draw(coneanglevsedXM2);
    ec.cd(2).draw(coneanglevsegXM2);

    ec.cd(3).draw(betavsP);
    ec.getPad().getAxisZ().setLog(true);
    ec.getCanvas().save(this.outputdir+"/"+ec.getTitle()+".png");


    //ec.divide(4,3);
    //ec2.cd(0).draw(DAngleGammaHist);

    // ec2.cd(10).draw(ThvsPhi);
    // ec2.cd(11).draw(MMomx);
    // ec2.cd(12).draw(MMomy);
    // ec2.cd(13).draw(MMomz);
    // ec2.cd(14).draw(edgXmissingE);
    // ec2.cd(15).draw(edgXmissingM2);
    // ec2.cd(16).draw(edgXmissingP);
    // ec2.cd(17).draw(edXmissingM2);
    // ec2.cd(18).draw(egXmissingM2);
    // ec2.cd(19).draw(ThvsPhi);
    // ec2.cd(20).draw(hgTh);
  }

}
