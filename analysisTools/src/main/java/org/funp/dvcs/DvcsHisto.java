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
  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'
  public H1F hadmom;
  public H2F WvsQ2;
  public H2F Q2vsXbj;
  //Missing quantities
  //public H1F MMass;// missing mass of a complete DVCS final state e hadron gamma
  //public H1F MMom;// missing mom of a complete DVCS final state e hadron gamma

  public H1F edgXmissingE; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingM2 ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingP ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPx;// missing px of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPy;// missing py of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPz;// missing pz of a complete DVCS final state e hadron gamma

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

  public H2F coneanglevsedgXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
  public H2F coneanglevsedXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehX
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

  public H1F chisqHad;

  public H2F chi2vsdeltabeta;

  public H1F helicityhisto;
  public H1F helicityrawhisto;

  public H1F Phiplus;
  public H1F Phiminus;

  public H1F thisto;
  public H1F pPerphisto;


  public DvcsHisto() {
    W= new H1F("W" ,100, 0, 10.0);
    W.setTitleX("W [GeV]");
    Q2 = new H1F("Q2",100, 0.1, 4.0);
    Q2.setTitleX("Q^2 [GeV/c^2]^2");
    hadmom = new H1F("Hadron Momentum",100,0,10.0);
    hadmom.setTitleX("Deteron momentum [GeV/c]");
    WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
    WvsQ2.setTitleX("W [GeV]");
    WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj = new H2F("Q^2 vs X_bj","Q^2 vs X_bj",100,0,10,100,0,1);
    Q2vsXbj.setTitleX("Q^2 [GeV/c^2]");
    Q2vsXbj.setTitleY("X_bj");

    //MMass = new H1F("MMass",100,-10,10);
    //MMass.setTitleX("Missing Mass Squared");
    //MMom = new H1F("MMom",100,0,10);
    //MMom.setTitleX("Missing Momentum");


    edgXmissingE = new H1F("edgXmissingE",100,0,10);
    edgXmissingE.setTitleX("eDγX Missing Energy [GeV]");
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
    egXmissingM2.setTitle("eGammaX Missing Mass2");
    edXmissingM = new H1F("eDXmissingM",100,-0,5);
    edXmissingM.setTitle("eDX Missing Mass");
    egXmissingM = new H1F("egXmissingM",100,-0,5);
    egXmissingM.setTitleX("M_x(ed#rarrowe#gammaX)");
    egXmissingM2vsTh =new H2F("egXmissingM2vsTh",100,0,140,100,0,10);

    ThvsPhi = new H2F("Deuteron #theta vs #phi","Deuteron #theta vs #phi",100,-180,180,100,0,180);
    ThvsPhi.setTitleX("#phi [Degrees]");
    ThvsPhi.setTitleY("#theta [Degrees]");
    ThvsP = new H2F("Deuteron p vs #theta","Deuteron p vs #theta ",100,0,180,100,0,10.6);
    ThvsP.setTitleY("p [GeV/c]");
    ThvsP.setTitleX("#theta [Degrees]");

    elecThvsPhi = new H2F("Electron #theta vs #phi","Electron #theta vs #phi",100,-180,180,100,0,100);
    elecThvsPhi.setTitleX("#phi [Degrees]");
    elecThvsPhi.setTitleY("#theta [Degrees]");
    elecThvsP = new H2F("Electron p vs #theta","Electron p vs #theta ",100,0,40,100,0,10.6);
    elecThvsP.setTitleY("p [GeV/c]");
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
    hgTh.setTitle("Photon Theta");
    hgEn = new H1F("Photon energy",100,0,10);
    hgEn.setTitleX("Photon Energy [GeV/c^2]");
    //DAngleGammaHist = new H1F("DAngleGammaHist",100,-15,100);
    //DAngleGammaHist.setTitle("Angle between gamma and missing eDX");
    ConeAngleHist = new H1F("ConeAngleHist",100,-3,10);
    ConeAngleHist.setTitle("Angle between gamma and missing eDX");
    MissThetaHist = new H1F("MissThetaHist",100,0,180);
    MissThetaHist.setTitle("Missing Photon Theta");
    PhiPlaneHist = new H1F("PhiPlaneHist",100,0,360);
    PhiPlaneHist.setTitleX("#phi, the angle between the Leptonic and Hadronic planes");
    DPhiHist = new H1F("DPhiHist",100,-10,10);
    DPhiHist.setTitle("DPhi");
    DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,-10,10);
    DeltaPhiPlaneHist.setTitle("Delta Phi Plane");
    DeltaPhiPlaneMattHist = new H1F("DeltaPhiPlane",100,-100,100);
    DeltaPhiPlaneMattHist.setTitle("Delta Phi Plane Hattawy");


    coneanglevsedgXM2 = new H2F("Cone Angle vs eDGammaX missing M2","Cone Angle vs eDGammaX missing M2",100,0,20,100,-2,2);
    coneanglevsedgXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsedgXM2.setTitleY("eDGammaX missing M2 (GeV)");
    coneanglevsedXM2 = new H2F("#theta#_{#gamma,x} vs (M_x)^2(ed#rarrowededX)","#theta#_{#gamma,x} vs (M_x)^2(ed#rarrowedX)",100,0,20,100,-10,10);
    coneanglevsedXM2.setTitleX("#theta#_{#gamma,x} [deg.]");
    coneanglevsedXM2.setTitleY("(M_x)^2(ed#rarrowed#X) [GeV/c^2]");
    coneanglevsegXM2 = new H2F("Cone Angle vs egX missing M2","Cone Angle vs egX missing M2",100,0,20,100,0,20);
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
    deltabeta.setTitle("Beta - BetaCalc");

    ctofdedxvsp=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspplus=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspdeut=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspprot=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvsppion=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    ctofdedxvspkaon=new H2F("CTOF energy vs p",100,0,2,100,0,100);
    chisqHad=new H1F("Chi2Pid",100,-5,5);
    chisqHad.setTitle("ChiSquared PID");

    chi2vsdeltabeta=new H2F("#chi^2_PID vs #Delta#beta_d","#chi^2_PID vs #Delta#beta_d",100,-30,30,100,-0.6,0.6);
    chi2vsdeltabeta.setTitleX("#chi^2_PID");
    chi2vsdeltabeta.setTitleY("#Delta#beta_d");

    helicityhisto=new H1F("Helicity",9,-4,4);
    helicityhisto.setTitleX("Beam Helicity");
    helicityrawhisto=new H1F("Helicity Raw",9,-4,4);
    helicityrawhisto.setTitle("Helicity Raw");

    Phiplus = new H1F("Phiplus",10,0,360);
    Phiminus = new H1F("Phiminus",10,0,360);
    thisto = new H1F("-t",100,0,20);
    thisto.setTitleX("-t [GeV/c]^2");
    pPerphisto = new H1F("pPerp","pPerp",100,0,3);
    //System.out.println("creating histograms"  );
  }
  public void fillBasicHisto(DvcsEvent ev) {
    W.fill(ev.W().mass());
    Q2.fill(-ev.Q().mass2());
    hadmom.fill(ev.vhadron.p());
    WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());
    Q2vsXbj.fill(-ev.Q().mass2(),ev.Xb());

    //missing quantities of a complete DVCS final state e hadron gamma
    edgXmissingE.fill(ev.X("ehg").e());
    edgXmissingM2.fill(ev.X("ehg").mass2());
    edgXmissingP.fill(ev.X("ehg").p());
    edgXmissingPx.fill(ev.X("ehg").px());
    edgXmissingPy.fill(ev.X("ehg").py());
    edgXmissingPz.fill(ev.X("ehg").pz());

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

    betavsP.fill(ev.vhadron.p(),ev.beta());
    betahadhisto.fill(ev.beta());
    betacalchisto.fill(ev.BetaCalc());
    betacalcvsP.fill(ev.vhadron.p(),ev.BetaCalc());
    deltabeta.fill(ev.beta()-ev.BetaCalc());

    ctofdedxvsp.fill(ev.vhadron.p(),ev.ctofen());
    chisqHad.fill(ev.chi2pid());
    chi2vsdeltabeta.fill(ev.chi2pid(),ev.beta()-ev.BetaCalc());

    helicityhisto.fill(ev.helicity);
    helicityrawhisto.fill(ev.helicityraw);
    thisto.fill(-1*ev.t().mass2());
    pPerphisto.fill(ev.pPerp());

    if(ev.helicity==1){
      Phiplus.fill(ev.PhiPlane());
    }
    else if (ev.helicity==-1){
      Phiminus.fill(ev.PhiPlane());
    }


  }

  public void fillPositives(DvcsEvent ev){
    //betavsPplus.fill(ev.vpositive.p(),ev.betapos());
    //ctofdedxvspplus.fill(ev.vpositive.p(),ev.ctofenpos());
    //if (ev.FoundDeuteron==true){
      betavsPdeut.fill(ev.vdeuteron.p(),ev.betadeut);
      betavsPdeut.setTitle("Beta vs Momentum Deuteron");
      ctofdedxvspdeut.fill(ev.vdeuteron.p(),ev.ctofenergydeut);
      ctofdedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
    //}
    //else if (ev.FoundProton==true){
      betavsPprot.fill(ev.vproton.p(),ev.betaprot);
      betavsPprot.setTitle("Beta vs Momentum Proton");
      ctofdedxvspdeut.fill(ev.vproton.p(),ev.ctofenergyprot);
      ctofdedxvspdeut.setTitle("dE/dx vs Momentum Proton");
    //}
    //else if (ev.FoundPion==true){
      betavsPpion.fill(ev.vpion.p(),ev.betapion);
      betavsPpion.setTitle("Beta vs Momentum #pi+");
      ctofdedxvspdeut.fill(ev.vpion.p(),ev.ctofenergypion);
      ctofdedxvspdeut.setTitle("dE/dx vs Momentum #pi+");
    //}
    //else if (ev.FoundKaon==true){
      betavsPkaon.fill(ev.vkaon.p(),ev.betakaon);
      betavsPkaon.setTitle("Beta vs Momentum #kappa");
      ctofdedxvspdeut.fill(ev.vkaon.p(),ev.ctofenergykaon);
      ctofdedxvspdeut.setTitle("dE/dx vs Momentum #kappa");
    //}
  }

  public void drawPositives(TCanvas ecP){
    ecP.divide(2,2);
    /*betavsPplus.add(betavsPprot);
    betavsPplus.add(betavsPdeut);
    betavsPplus.add(betavsPpion);
    betavsPplus.add(betavsPkaon);
    //ecP.getPad().getAxisZ().setLog(true);
    ecP.draw(betavsPplus);*/
    ecP.cd(0).draw(betavsPdeut);
    ecP.cd(1).draw(betavsPprot);
    ecP.cd(2).draw(betavsPpion);
    ecP.cd(3).draw(betavsPkaon);
  }

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
}

  public void DrawBasic(TCanvas ec){
    ec.divide(3,3);
    //ec.getPad(0).getAxisZ().setLog(true);
    ec.cd(0).draw(WvsQ2);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(1).draw(Q2vsXbj);
    ec.cd(2).draw(W);
    ec.cd(3).draw(Q2);
    ec.cd(4).draw(ThvsPhi);
    ec.cd(5).draw(ThvsP);
    ec.cd(6).draw(deltabeta);
    ec.cd(7).draw(edgXmissingPy);
    ec.cd(8).draw(edgXmissingPz);
    ec.getCanvas().getScreenShot();
    ec.getCanvas().save(ec.getTitle()+".png");


  }

  public void DrawMissing(TCanvas ec4){

    ec4.divide(4,4);
    ec4.cd(0).draw(DeltaPhiPlaneHist);
    ec4.cd(1).draw(DeltaPhiPlaneMattHist);
    ec4.cd(2).draw(ConeAngleHist);
    ec4.cd(4).draw(edgXmissingE);
    ec4.cd(5).draw(edgXmissingM2);
    ec4.cd(6).draw(edgXmissingP);
    ec4.cd(8).draw(edXmissingM);
    ec4.cd(9).draw(edXmissingM2);
    ec4.cd(10).draw(egXmissingM);
    ec4.cd(11).draw(egXmissingM2);

    ec4.cd(12).draw(hgEn);
    ec4.cd(13).draw(edgXmissingPx);
    ec4.cd(14).draw(edgXmissingPy);
    ec4.cd(15).draw(edgXmissingPz);
    ec4.getCanvas().getScreenShot();
    ec4.getCanvas().save(ec4.getTitle()+".png");
    //ec4.getScreenShot();



  }
  public void DrawAll(TCanvas ec){
    ec.divide(6,5);
    ec.cd(0).draw(W);
    ec.cd(1).draw(Q2);
    ec.cd(2).draw(WvsQ2);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(3).draw(Q2vsXbj);
    ec.cd(4).draw(edgXmissingE);
    ec.cd(5).draw(edgXmissingM2);
    ec.cd(6).draw(edgXmissingP);
    ec.cd(7).draw(edgXmissingPx);
    ec.cd(8).draw(edgXmissingPy);
    ec.cd(9).draw(edgXmissingPz);
    ec.cd(10).draw(edXmissingM);
    ec.cd(11).draw(egXmissingM);
    ec.cd(12).draw(ThvsPhi);
    ec.cd(13).draw(hgTh);
    ec.cd(14).draw(hgEn);
    ec.cd(15).draw(chi2vsdeltabeta);
    ec.cd(16).draw(ConeAngleHist);
    ec.cd(17).draw(MissThetaHist);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(18).draw(PhiPlaneHist);
    ec.cd(19).draw(DPhiHist);
    ec.cd(20).draw(DeltaPhiPlaneHist);
    ec.cd(21).draw(DeltaPhiPlaneMattHist);
    //ec.cd(22).draw(coneanglevsedgXM2);
    ec.cd(23);
    ec.getPad().getAxisZ().setLog(true);
    ec.draw(coneanglevsedXM2);
    //ec.getPad().getAxisZ().setLog(true);
    //ec.cd(24).draw(coneanglevsegXM2);

    ec.cd(25).draw(betavsP);
    //ec.getPad().getAxisZ().setLog(true);
    ec.cd(22).draw(betacalcvsP);
    //ec.cd(22).draw(deltabeta);
    //ec.cd(23).draw(ctofdedxvsp);
    ec.cd(24).draw(helicityhisto);
    //ec.cd(25).draw(helicityrawhisto);
    ec.cd(26).draw(deltabeta);
    ec.cd(27).draw(chisqHad);
    ec.cd(28).draw(betacalchisto);
    ec.cd(29).draw(betahadhisto);

    ec.getCanvas().getScreenShot();
    ec.getCanvas().save(ec.getTitle()+".png");


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
  public void DrawAll2(TCanvas ec){
    ec.divide(2,2);
    ec.cd(0).draw(coneanglevsedgXM2);
    ec.cd(1).draw(coneanglevsedXM2);
    ec.cd(2).draw(coneanglevsegXM2);

    ec.cd(3).draw(betavsP);
    ec.getPad().getAxisZ().setLog(true);



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
