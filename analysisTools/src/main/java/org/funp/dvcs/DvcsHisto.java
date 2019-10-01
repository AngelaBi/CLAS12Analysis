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

import org.jlab.groot.ui.TCanvas;

//import org.funp.dvcs.DvcsEvent;;

public class DvcsHisto {
  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'
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
  public H2F egXmissingM2vsTh;

  public H2F ThvsPhi;//Theta vs phi for hadron
  public H2F ThvsP;//Theta vs mom for hadron

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
  public H2F betavsP ;
  public H2F betacalcvsP;
  public H1F deltabeta;

  public H2F ctofdedxvsp;




  public DvcsHisto() {
    W= new H1F("W" ,100, 0, 10.0);
    W.setTitleX("W [GeV]");
    Q2 = new H1F("Q2",100, 0.1, 4.0);
    Q2.setTitleX("Q^2 [GeV/c^2]");
    WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
    WvsQ2.setTitleX("W [GeV]");
    WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,0,10,100,0,1);
    Q2vsXbj.setTitleX("Q^2 [GeV/c^2]");
    Q2vsXbj.setTitleY("Xbj");

    //MMass = new H1F("MMass",100,-10,10);
    //MMass.setTitleX("Missing Mass Squared");
    //MMom = new H1F("MMom",100,0,10);
    //MMom.setTitleX("Missing Momentum");


    edgXmissingE = new H1F("edgXmissingE",100,0,10);
    edgXmissingE.setTitle("eDGammaX Missing Energy");
    edgXmissingM2 = new H1F("edgXmissingM2",100,-4,4);
    edgXmissingM2.setTitle("eDGammaX Missing Mass2");
    edgXmissingP = new H1F("edgXmissingP",100,0,4);
    edgXmissingP.setTitle("eDGammaX Missing Momentum");
    edgXmissingPx = new H1F("MMomx",100,-1,1);
    edgXmissingPx.setTitleX("Missing X Momentum");
    edgXmissingPy = new H1F("MMomy",100,-1,1);
    edgXmissingPy.setTitleX("Missing Y Momentum");
    edgXmissingPz = new H1F("MMomz",100,-5,5);
    edgXmissingPz.setTitleX("Missing Z Momentum");

    edXmissingM2 = new H1F("edXmissingM2",100,-10,10);
    edXmissingM2.setTitle("eDX Missing Mass2");
    egXmissingM2 = new H1F("egXmissingM2",100,-0,10);
    egXmissingM2.setTitle("eGammaX Missing Mass2");
    egXmissingM2vsTh =new H2F("egXmissingM2vsTh",100,0,140,100,0,10);

    ThvsPhi = new H2F("Theta vs Phi","Theta vs Phi",100,-180,180,100,0,180);
    ThvsPhi.setTitleX("Phi [Degrees]");
    ThvsPhi.setTitleY("Theta [Degrees]");
    ThvsP = new H2F("Theta vs Phi","Theta vs Phi",100,0,1,100,0,140);
    ThvsP.setTitleX("p [GeV]");
    ThvsP.setTitleY("Theta [Degrees]");

    //MMvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MMvsMpz.setTitleX("Missing Mass");
    //MMvsMpz.setTitleY("Missing Z Momentum");
    //MpxvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MpxvsMpz.setTitleX("Missing X Momentum");
    //MpxvsMpz.setTitleY("Missing Z Momentum");
    hgTh = new H1F("hgTh",100,0,50);
    hgTh.setTitle("Photon Theta");
    hgEn = new H1F("Photon energy",100,0,10);
    hgEn.setTitle("Photon Energy");
    //DAngleGammaHist = new H1F("DAngleGammaHist",100,-15,100);
    //DAngleGammaHist.setTitle("Angle between gamma and missing eDX");
    ConeAngleHist = new H1F("ConeAngleHist",100,-3,10);
    ConeAngleHist.setTitle("Angle between gamma and missing eDX");
    MissThetaHist = new H1F("MissThetaHist",100,0,180);
    MissThetaHist.setTitle("Missing Photon Theta");
    PhiPlaneHist = new H1F("PhiPlaneHist",100,0,180);
    PhiPlaneHist.setTitle("Photon Phi Plane");
    DPhiHist = new H1F("DPhiHist",100,-10,10);
    DPhiHist.setTitle("DPhi");
    DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,-10,10);
    DeltaPhiPlaneHist.setTitle("Delta Phi Plane");
    DeltaPhiPlaneMattHist = new H1F("DeltaPhiPlane",100,-100,100);
    DeltaPhiPlaneMattHist.setTitle("Delta Phi Plane Hattawy");


    coneanglevsedgXM2 = new H2F("Cone Angle vs eDGammaX missing M2","Cone Angle vs eDGammaX missing M2",100,0,180,100,-30,30);
    coneanglevsedgXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsedgXM2.setTitleY("eDGammaX missing M2 (GeV)");
    coneanglevsedXM2 = new H2F("Cone Angle vs eDX missing M2","Cone Angle vs eDX missing M2",100,0,180,100,-30,30);
    coneanglevsedXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsedXM2.setTitleY("eDX missing M2 (GeV)");

    //pid histograms
    betavsP = new H2F("Beta vs P","Beta vs P", 100,0,10.2,100,0,1.1);
    betacalcvsP = new H2F("BetaCalc vs P","BetaCalc vs P", 100,0,10.2,100,0,1.1);
    deltabeta = new H1F("Beta - BetaCalc",100,-0.6,0.2);
    deltabeta.setTitle("Beta - BetaCalc");

    ctofdedxvsp=new H2F("CTOF energy vs p",100,0,2,100,0,100);



    //System.out.println("creating histograms"  );
  }
  public void fillBasicHisto(DvcsEvent ev) {
    W.fill(ev.W().mass());
    Q2.fill(-ev.Q().mass2());
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
    egXmissingM2vsTh.fill(Math.toDegrees(ev.vhadron.theta()),ev.X("eg").mass2());

    ThvsPhi.fill(Math.toDegrees(ev.vhadron.phi()),Math.toDegrees(ev.vhadron.theta()));
    ThvsP.fill(ev.vhadron.p(),Math.toDegrees(ev.vhadron.theta()));
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

    betavsP.fill(ev.vhadron.p(),ev.beta());
    betacalcvsP.fill(ev.vhadron.p(),ev.BetaCalc());
    deltabeta.fill(ev.beta()-ev.BetaCalc());

    ctofdedxvsp.fill(ev.vhadron.p(),ev.ctofen());





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
    ec.cd(6).draw(edgXmissingPx);
    ec.cd(7).draw(edgXmissingPy);
    ec.cd(8).draw(edgXmissingPz);
    ec.getCanvas().getScreenShot();
    ec.getCanvas().save("test.png");


  }

  public void DrawMissing(TCanvas ec4){

    ec4.divide(3,4);
    ec4.cd(0).draw(DeltaPhiPlaneHist);
    ec4.cd(1).draw(DeltaPhiPlaneMattHist);
    ec4.cd(2).draw(ConeAngleHist);
    ec4.cd(3).draw(edgXmissingE);
    ec4.cd(4).draw(edgXmissingM2);
    ec4.cd(5).draw(edgXmissingP);
    ec4.cd(6).draw(edXmissingM2);
    ec4.cd(7).draw(egXmissingM2);

    ec4.cd(8).draw(hgEn);
    ec4.cd(9).draw(edgXmissingPx);
    ec4.cd(10).draw(edgXmissingPy);
    ec4.cd(11).draw(edgXmissingPz);
    //ec4.getScreenShot();


  }
  public void DrawAll(TCanvas ec){
    ec.divide(5,6);
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
    ec.cd(10).draw(edXmissingM2);
    ec.cd(11).draw(egXmissingM2);
    ec.cd(12).draw(ThvsPhi);
    ec.cd(13).draw(hgTh);
    ec.cd(14).draw(hgEn);

    ec.cd(16).draw(ConeAngleHist);
    ec.cd(17).draw(MissThetaHist);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(18).draw(PhiPlaneHist);
    ec.cd(19).draw(DPhiHist);
    ec.cd(20).draw(DeltaPhiPlaneHist);
    ec.cd(21).draw(DeltaPhiPlaneMattHist);
    ec.cd(22).draw(coneanglevsedgXM2);
    ec.cd(23).draw(coneanglevsedXM2);
    ec.cd(24).draw(betavsP);
    ec.cd(25).draw(betacalcvsP);
    ec.cd(26).draw(deltabeta);
    ec.cd(26).draw(ctofdedxvsp);

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
