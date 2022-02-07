package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;


import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.fitter.*;
import org.jlab.groot.math.*;

import java.util.ArrayList;

//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.jlab.groot.ui.TCanvas;
import org.jlab.groot.data.TDirectory;

//import org.funp.dvcs.DvcsEvent;;

public class DvcsHisto {

  public H1F Xbj;
  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'
  public H1F hadmom;
  public H2F WvsQ2;
  public H2F Q2vsXbj;
  public H2F tvsxb;
  public H2F q2vst;
  public H1F VertexElectron;
  public H1F VertexDuetron;
  public H2F vertexElecVSvertexDeut;

  public H2F ThvsPhi;//Theta vs phi for hadron
  public H2F elecThvsPhi;
  public H2F photThvsPhi;
  public H2F ThvsP;//Theta vs mom for hadron
  public H2F elecThvsP;
  public H2F photThvsP;

  public H1F hgTh;//theta gamma
  public H1F hgEn;//Energy gamma
  public H1F PhiPlaneHist ; //angle between electrons plane and hadron/gamma plane
  public H1F DPhiHist ;//phi gamma minus phi missing hadron+e vector

  public H1F ConeAngleBtElectronPhotonFD;

  public H2F phivshelicityPlus;
  public H2F phivshelicityMinus;
  public H1F helicityhisto;
  public H1F helicityrawhisto;

  public H2F XvsY_electron;
  
  public H1F chisqHad;
  public H1F thisto;

  public H1F Phiplus;
  public H1F Phiminus;

  //Exclusivity plots - All these are in Missing quant folder
  public H1F edgXmissingE; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingM2 ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingP ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPx;// missing px of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPy;// missing py of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPz;// missing pz of a complete DVCS final state e hadron gamma
  public H1F pPerphisto;

  public H1F edXmissingE;
  public H1F edXmissingM2; // missing mass of hadron electron final state (to be compared with gamma)
  public H1F edXmissingM;
  
  public H1F egXmissingM2; // missing mass of gamma electron final state (to be compared with hadron)
  public H1F egXmissingM;
  public H2F egXmissingM2vsTh;

  public H1F DeltaPhiPlaneHist; //angle planes Q2/hadron and gamma/hadrom
  public H1F DeltaPhiPlaneMattHist;//angle planes Q2/hadron and Q2/gamma
  public H1F MissThetaHist;//theta missing hadron+e vector
  public H1F ConeAngleHist;//angle between gamma vector and missing hadron+e vector

  public H2F coneanglevsedgXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
  public H2F coneanglevsedXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehX
  public H2F coneanglevspperp;
  public H2F coneanglevsegXM2;
  //pid id histos 
  public H2F betacalcvsP;//y
  public H1F betahadhisto;//y
  public H1F betacalchisto;//y
  public H2F betavsP;//y
  public H2F deltabetavschi2;//y
  public H1F deltabeta;//y
  public H2F ctofdedxvsp;
  public H2F dedxDeutvsP;
  public H2F dedxCTOFvsP;
  public H2F dedxCNDvsP;

  public H2F thgvsthe;
  ArrayList<Object> kinehistos;
  ArrayList<Object> exclhistos;
  ArrayList<Object> asymhistos;
  ArrayList<Object> pidhistos;
  boolean readMode=false;
  private TDirectory rootDirfile;
  private String baseDir;//NC DC AC
  private String Config;//FT,FT or nothing



  private String outputdir=new String(".");

  public void setOutputDir(String otherdir){
    this.outputdir=otherdir;
    System.out.println("**** setting out dir for plots to" + this.outputdir);
}
public DvcsHisto(TDirectory rootdir, String basedir,String conf){
  readMode=true;
  rootDirfile=rootdir;
  baseDir=basedir;
  Config=conf;
  SetHisto();
  
}
  

  public DvcsHisto() {
    SetHisto();
  }
  public void SetHisto(){
    kinehistos= new ArrayList<Object>();
    exclhistos= new ArrayList<Object>();
    asymhistos = new ArrayList<Object>();
    pidhistos = new ArrayList<Object>();
    
    Xbj=createHisto("Xbj","x_b_j", "", 100, 0., 1.,"Kine");
    W=createHisto("W","W","W [GeV]",100,0,10,"Kine");
    Q2=createHisto("Q2","Q2","Q^2 [GeV/c^2]^2",100, 0.1, 4.0,"Kine");
    hadmom=createHisto("hadmom","Deuteron Momentum","p [GeV/c]",100,0,10.0,"Kine");
    WvsQ2=createHisto("Q2vsW", "Q2 vs W", "W [GeV]","Q^2 [GeV/c^2]", 100,0,7,100,0,10,"Kine");
    Q2vsXbj=createHisto("Q2vsXb","Q^2 vs X_b", "X_b", "Q^2 [GeV/c^2]", 100,0,1,100,0,10, "Kine");
    q2vst=createHisto("Q2vst",  "Q2 vs t", "t", "Q2", 100,0,5,100,0,5, "Kine");
    VertexElectron=createHisto("VertexElectron","Vertex Electron", "z",100,-10.0,10.0, "Kine");
    VertexDuetron=createHisto("VertexDeuteron","Vertex Dueteron", "z",  100,-10.0,10.0, "Kine");
    vertexElecVSvertexDeut=createHisto("VertexElectronvsVertexDeuteron","Vertex Electron vs Vertex Deuteron", "", "", 100,-10,10,100,-10,10, "Kine");
    
    ThvsPhi=createHisto("Deuteronthvsphi","Deuteron #theta vs #phi", "#phi [Degrees", "#theta [Degrees]", 100,-180,180,100,0,180, "Kine");
    photThvsPhi=createHisto("Photonthvsphi","Photon #theta vs #phi", "#phi [Degrees]", "#theta [Degrees]", 100,-180,180,100,0,180, "Kine");
    elecThvsPhi=createHisto("Electronthvsphi","Electron #theta vs #phi", "#phi [Degrees]" ,"#theta [Degrees]", 100,-180,180,100,0,180, "Kine");
    ThvsP=createHisto("Deuteronpvsth", "Deuteron p vs #theta ", "p [GeV/c]", "#theta [Degrees]", 100,0,180,100,0,10.6, "Kine");
    elecThvsP=createHisto("Electronpvstheta", "Electron p vs #theta ", "p [GeV/c]", "#theta [Degrees]", 100,0,180,100,0,10.6, "Kine");
    photThvsP=createHisto("Photonpvstheta", "Photon p vs #theta ", "p [GeV/c]", "#theta [Degrees]", 100,0,180,100,0,10.6, "Kine");
    hgTh=createHisto("hgTh", "Photon Theta", "#theta_#gamma",100,0,50,"Kine");
    hgEn=createHisto("Photonenergy", "Photon energy","E_#gamma",100,0,50,"Kine");
    PhiPlaneHist=createHisto("PhiPlaneHist", "PhiPlaneHist","",100,0,50,"Kine" );
    DPhiHist=createHisto("DPhiHist", "DPhi", "", 100,-10,10, "Kine");
    ConeAngleBtElectronPhotonFD=createHisto("ConeAngleBtElectronandPhoton", "Cone Angle Between Electron and Photon", "", 100,0,80, "Kine");
    phivshelicityPlus=createHisto("phivshelicityP", "phi vs helicity plus", "", "", 100,0,360,100,-4,4, "Kine");
    phivshelicityMinus=createHisto("phivshelicityM", "phi vs helicity minus", "", "", 100,0,360,100,-4,4, "Kine");
    helicityhisto=createHisto("Helicity", "Beam Helicity", "", 9,-4,4, "Kine");
    helicityrawhisto=createHisto("HelicityRaw", "Beam Raw Helicity", "", 9,-4,4, "Kine");
    XvsY_electron=createHisto("XvsY", "X vs Y","","",100,-400,400,100,-400,400,"Kine");
    chisqHad=createHisto("chisqHad","#chi^2 PID hadron","",100,-25,25,"Kine");
    thisto=createHisto("mt","-t","-t [GeV/c]^2",100,0,5,"Kine");
    Phiplus=createHisto("Phiplus","Phi Plus","",10,0,360,"Asym");
    Phiminus=createHisto("Phiminus","Phi Minus","",10,0,360,"Asym");
   
    //START OF EXC POTS
    //edgX
    edgXmissingE =createHisto("edgXmissingE", "eD#gammaX Missing Energy", "E_e_D_#gamma_X [GeV]", 100,0,10, "Excl");
    edgXmissingM2=createHisto("edgXmissingM2", "eD#gammaX Missing Mass^2", "M^2_x [GeV/c^2]^2", 100,-4,4, "Excl");
    edgXmissingP=createHisto("edgXmissingP", "eD#gammaX Missing p", "p_x [GeV/c]", 100,0,4, "Excl");
    edgXmissingPx=createHisto("MMomx", "Missing X Momentum", "", 100,-1,1, "Excl");
    edgXmissingPy=createHisto("MMomy", "Missing Y Momentum", "", 100,-1,1, "Excl");
    edgXmissingPz=createHisto("MMomz", "Missing Z Momentum", "", 100,-5,5, "Excl");
    pPerphisto=createHisto("pPerp", "pPerp", "", 100,0,3, "Excl");
    //edX
    edXmissingE =createHisto("edXmissingE", "eDX Missing Energy", "", 100,-5,5, "Excl");
    edXmissingM2=createHisto("edXmissingM2", "eDX Missing Mass^2", "", 100,-10,10, "Excl");//M_e_D_X^2 [GeV/c^2]^2
    edXmissingM=createHisto("eDXmissingM", "eDX Missing Mass", "", 100,-0,5, "Excl");
    //egX
    egXmissingM2=createHisto("egXmissingM2","e#gammaX Missing Mass2","",100,-0,10, "Excl");//M_e_#gamma_X^2 [GeV/c^2]^2
    egXmissingM=createHisto("egXmissingM","e#gammaX Mass","",100,-0,5, "Excl");//M_e_#gamma_X [GeV/c^2]
    egXmissingM2vsTh=createHisto("egXmissingM2vsTh","","","",100,0,140,100,0,10, "Excl");//e#gammaX MM^2 vs #theta
    //Phi planes
    DeltaPhiPlaneHist=createHisto("DeltaPhiPlane", "Delta Phi Plane", "", 100,-10,10, "Excl");
    DeltaPhiPlaneMattHist=createHisto("DeltaPhiPlane2", "Delta Phi Plane Hattawy", "", 100,-10,10, "Excl");
    MissThetaHist=createHisto("MissThetaHist", "MissThetaHist", "", 100,0,180, "Excl");
    //cone angles
    ConeAngleHist=createHisto("ConeAngleHist", "Angle between gamma and missing eDX", "", 100,-3,10, "Excl");
    coneanglevsedgXM2=createHisto("coneanglevsedgXM2", "eDGammaX missing M2 vs Cone Angle", "Cone Angle (deg.)", "eDGammaX missing M2 (GeV)", 100,0,20,100,-1,0.5, "Excl");
    coneanglevsedXM2=createHisto("coneanglevsedXM2","M^2_x (ed#rarrow edX) vs #theta_#gamma_x","#theta#_{#gamma,x} [deg.]","(M_x)^2(ed#rarrowed#X) [GeV/c^2]",100,0,20,100,-10,10, "Excl");
    coneanglevspperp=createHisto("ConeAnglevsPperp","Cone Angle vs Pperp","Pperp","Cone Angle", 100,0,10,100,0,1,"Excl");
    coneanglevsegXM2=createHisto("coneanglevsegXM2","egX missing M2 vs Cone Angle","Cone Angle (deg.)","egX missing M2 (GeV)",100,0,20,100,0,20,"Excl");
    //Pid histograms
    betahadhisto=createHisto("beta","#beta","Measured #beta",100,0,1,"Pid");
    betacalchisto=createHisto("betacalc","#beta_calc","#beta calculated from relativistic momentum",100,0,1,"Pid");
    betacalcvsP=createHisto("BetaCalcvsP","BetaCalc vs P", "", "", 100,0,10.2,100,0,1.1, "Pid");
    betavsP=createHisto("BetavsP","Beta vs P", "", "", 100,0,10.2,100,0,1.1, "Pid");
    deltabetavschi2=createHisto("Deltabeta_dvschi2PID","#Delta#beta_d vs #chi^2_PID", "#chi^2_PID", "#Delta#beta_d", 100,-30,30,100,-0.6,0., "Pid");
    deltabeta=createHisto("BetamBetaCalc", "Beta - BetaCalc", "" ,100,-0.6,0., "Pid");
    ctofdedxvsp=createHisto("ctofdedxvsp", "ctofdedxvsp", "", "", 100,0,2,100,0,100, "Pid");
    dedxDeutvsP =createHisto("dedxDeutvsP","de/dx Deut vs P", "", "", 100,0,2,100,0,30, "Pid");
    dedxCTOFvsP= createHisto("dedxCTOFvsP", "de/dx CTOF vs P", "","",100,0,2,100,0,30, "Pid");
    dedxCNDvsP=createHisto("dedxCNDvsP", "de/dx CND vs P", "", "", 100,0,2,100,0,30, "Pid");
    thgvsthe=createHisto("thgvsthe", "#theta_#gamma vs #theta_e", "", "", 100, 0, 40, 100, 0, 40, "Kine");
    System.out.println("creating histograms"  );
  }
  public H1F createHisto(String name,
      String title,String titlex,
      int nbins,double xmin,double xmax,String type){
        H1F h;
        if(readMode){
          System.out.print("getting the histo "+name+" in "+baseDir+Config+"/"+type+"\n");
          rootDirfile.ls();
          h=(H1F)rootDirfile.getObject(baseDir+Config+"/"+type+"/",name); 
        }
        else{
        h=new H1F(name,title,nbins,xmin,xmax);
        h.setTitleX(titlex);
        if(type == "Kine"){
          kinehistos.add(h);
        }
        else if(type == "Excl"){
          exclhistos.add(h);
        }
        else if(type == "Pid"){
          pidhistos.add(h);
        }
        else if(type == "Asym"){
          asymhistos.add(h);
        }
      }
      return h;
      
  }
  public H2F createHisto(String name,
      String title,String titlex,String titley,
      int nbinsx,double xmin,double xmax,
      int nbinsy,double ymin,double ymax,
      String type){
        H2F h;
        if(readMode){
          System.out.print("getting the histo "+name+" in "+baseDir+Config+"/"+type+"/"+"\n");
          h=(H2F)rootDirfile.getObject(baseDir+Config+"/"+type+"/",name); 
          
        }
        else{

        h=new H2F(name,title,nbinsx,xmin,xmax,nbinsy,ymin,ymax);
        h.setTitleX(titlex);
        h.setTitleY(titley);
        if(type == "Kine"){
          kinehistos.add(h);
        }
        else if(type == "Excl"){
          exclhistos.add(h);
        }
        else if(type == "Pid"){
          pidhistos.add(h);
        }
      }
      return h;
      
  }

  public void fillBasicHisto(DvcsEvent ev) {
    double[] q2bins={0,1.75,2.5,10};
    double[] tbins={0,0.39,0.57,10};
      double[] xbbin={0,0.128,0.182,10};


    dedxCTOFvsP.fill(ev.vhadron.p(),ev.dedxDeutCTOF);
    dedxCNDvsP.fill(ev.vhadron.p(),ev.dedxDeutCND);
  
    coneanglevspperp.fill(ev.pPerp(), ev.coneangle());
    XvsY_electron.fill(ev.elec_x,ev.elec_y);
   // XvsY_electron_after.fill(ev.elec_x,ev.elec_y);
    W.fill(ev.W().mass());
    Q2.fill(-ev.Q().mass2());
    hadmom.fill(ev.vhadron.p());
    WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());
    Q2vsXbj.fill(ev.Xb(),-ev.Q().mass2());
    q2vst.fill( -1*ev.t().mass2(),-1*ev.Q().mass2());
    Xbj.fill(ev.Xb());
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

    thgvsthe.fill(Math.toDegrees(ev.velectron.theta()),Math.toDegrees(ev.vphoton.theta()));
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
    deltabetavschi2.fill(ev.chi2pid(),ev.beta()-ev.BetaCalc());
    

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

  public  void writeHipooutput(TDirectory rootdir,String directory){
    String hipodirectory = "/"+directory;

    String[] sub={hipodirectory+"/Kine",hipodirectory+"/Excl",hipodirectory+"/Pid",hipodirectory+"/Asym"};
    
  
    rootdir.mkdir(sub[0]);
    rootdir.cd(sub[0]);
    for (Object obj: kinehistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    rootdir.mkdir(sub[1]);
    rootdir.cd(sub[1]);
    for (Object obj: exclhistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    rootdir.mkdir(sub[2]);
    rootdir.cd(sub[2]);
    for (Object obj: pidhistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    rootdir.mkdir(sub[3]);
    rootdir.cd(sub[3]);
    for (Object obj: asymhistos) {
      if (obj instanceof H1F){
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        rootdir.addDataSet((H2F) obj);      } 
    }
    
    // rootdir.mkdir(sub2);
    // rootdir.cd(sub2);
    // for (Object obj: exclhistos) {
    //   if (obj instanceof H1F){
    //     rootdir.addDataSet((H1F) obj);
    //   } else if (obj instanceof H2F) {
    //     rootdir.addDataSet((H2F) obj);      } 
    // }
    
    //rootdir.addDataSet(coneanglevspperp);
    //rootdir.addDataSet(coneanglevsedXM2);
    //rootdir.addDataSet(coneanglevsegXM2);
    rootdir.writeFile(this.outputdir + "/Angela.hipo");
  
  }
  public H1F buildAsym(TDirectory dir, String directory){
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
  String hipodirectory = "/"+directory;
  dir.mkdir(hipodirectory);
  dir.cd(hipodirectory);
    
  dir.addDataSet(num);
  dir.addDataSet(denom);
  dir.addDataSet(Asym);
  dir.addDataSet(Phiplus);
  dir.addDataSet(Phiminus);
  dir.writeFile(this.outputdir + "/NickRichardson.hipo");
  
  return Asym;
 }
  // public void drawPlot(TCanvas ecP){
  //   //ecP.getPad().getAxisZ().setLog(true);
  //   this.drawAsym(ecP);
  // }
  public void drawAsym( TDirectory dir, String directory){
  // ecA.getPad().setAxisRange(0, 360, -0.6, 0.6);
	// ecA.draw((this.buildAsym(dir, directory)),"E");

  F1D Asymfunc = new F1D("Asymfunc","[A]*sin([B]x)+[C]",0,360); //possibly try without c constant /*+[C]*/
  Asymfunc.setParameter(0,0.2);
  Asymfunc.setParameter(1,0.01);
  Asymfunc.setParameter(2,-0.2);
  DataFitter.fit(Asymfunc,this.buildAsym(dir, directory),"");
  // ecA.draw(Asymfunc,"same");
  // ecA.getCanvas().save(this.outputdir+"/"+ecA.getTitle()+".png");
  

  

}

  
  
  
// //Here are the methods used by DoCode to fill the NickRichardson.hipo file 

//   public void DrawParticleComparison(/*TCanvas ecPP,*/ TDirectory dir, String directory ){


//     String hipodirectory = "/"+directory;
//     dir.mkdir(hipodirectory);
//     dir.cd(hipodirectory);
    
//     dir.addDataSet(elecThvsPhi);
//     dir.addDataSet(elecThvsP);
//     dir.addDataSet(photThvsPhi);
//     dir.addDataSet(photThvsP);
//     dir.addDataSet(ThvsPhi);
//     dir.addDataSet(ThvsP);
//     dir.addDataSet(dedxDeutvsP);
//     dir.writeFile(this.outputdir + "/NickRichardson.hipo");
//     // ecPP.divide(2,4);
    
//     // ecPP.cd(0).draw(elecThvsPhi);
//     // ecPP.cd(1).draw(elecThvsP);
//     // ecPP.cd(2).draw(photThvsPhi);
//     // ecPP.cd(3).draw(photThvsP);
//     // ecPP.cd(4).draw(ThvsPhi);
//     // ecPP.cd(5).draw(ThvsP);
//     // ecPP.cd(6).draw(dedxDeutvsP);
//     // ecPP.getCanvas().getScreenShot();
//     // System.out.println(this.outputdir+"/"+ecPP.getTitle()+".png" );
//     // ecPP.getCanvas().save(this.outputdir+"/"+ecPP.getTitle()+".png");
    

    

//   }

//   public void DrawMissingQuants(/*TCanvas ec4, */TDirectory dir, String directory){

//     //ec4.divide(4,4);
//     String hipodirectory = "/"+directory;
//     dir.mkdir(hipodirectory);
//     dir.cd(hipodirectory);
    
//     dir.addDataSet(edgXmissingE);
//     dir.addDataSet(edgXmissingM2);
//     dir.addDataSet(edgXmissingP);
//     dir.addDataSet(edgXmissingPx);
//     dir.addDataSet(edgXmissingPy);
//     dir.addDataSet(edgXmissingPz);
//     dir.addDataSet(pPerphisto);
    
//     dir.addDataSet(edXmissingE);
//     dir.addDataSet(edXmissingM2);
//     dir.addDataSet(edXmissingM);

//     dir.addDataSet(egXmissingM2);
//     dir.addDataSet(egXmissingM);

//     dir.addDataSet(DeltaPhiPlaneHist);
//     dir.addDataSet(DeltaPhiPlaneMattHist);
//     dir.addDataSet(MissThetaHist);
//     dir.addDataSet(ConeAngleHist);

//     dir.addDataSet(coneanglevspperp);
//     dir.addDataSet(coneanglevsedXM2);
//     dir.addDataSet(coneanglevsegXM2);
//     dir.addDataSet(coneanglevsegXM2);


//     dir.writeFile(this.outputdir + "/NickRichardson.hipo");
    



//   }
//   public void DrawKinematics(/*TCanvas ec,*/ TDirectory dir, String directory){
    
//     String hipodirectory = "/"+directory;
//     dir.mkdir(hipodirectory);
//     dir.cd(hipodirectory);
    
//     dir.addDataSet(Xbj);
//     dir.addDataSet(Q2);
//     dir.addDataSet(W);
//     dir.addDataSet(hadmom);
//     dir.addDataSet(WvsQ2);
//     dir.addDataSet(Q2vsXbj);
//     //dir.addDataSet(tvsxb);
//     dir.addDataSet(q2vst);
    

//     dir.addDataSet(VertexElectron);
//     dir.addDataSet(VertexDuetron);
//     dir.addDataSet(vertexElecVSvertexDeut);
//     dir.addDataSet(dedxDeutvsP);
//     dir.addDataSet(dedxCTOFvsP);
//     dir.addDataSet(dedxCNDvsP);
    

//     dir.addDataSet(ThvsPhi);
//     dir.addDataSet(elecThvsPhi);
//     dir.addDataSet(photThvsPhi);
//     dir.addDataSet(ThvsP);
//     dir.addDataSet(elecThvsP);
//     dir.addDataSet(photThvsP);

//     dir.addDataSet(hgTh);
//     dir.addDataSet(hgEn);
//     dir.addDataSet(PhiPlaneHist);
//     dir.addDataSet(DPhiHist);

//     dir.addDataSet(betahadhisto);
//     dir.addDataSet(betacalchisto);
//     dir.addDataSet(betacalcvsP);
//     dir.addDataSet(deltabetavschi2);
//     dir.addDataSet(deltabeta);
   
//     dir.addDataSet(ConeAngleBtElectronPhotonFD);

//     //dir.addDataSet(phivshelicityPlus);
//     dir.addDataSet(phivshelicityMinus);
//     //dir.addDataSet(helicityhisto);
//     //dir.addDataSet(helicityrawhisto);

//     dir.addDataSet(XvsY_electron);

//     dir.addDataSet(chisqHad);
//     dir.addDataSet(thisto);
//     dir.addDataSet(ConeAngleHist);//Should be in missing
//     dir.writeFile(this.outputdir + "/NickRichardson.hipo");
//     // ec.divide(5,5);
//     // ec.cd(0).draw(WvsQ2);
//     // ec.cd(1).draw(Q2vsXbj);
//     // ec.cd(2).draw(betacalcvsP);

//     // ec.cd(3).draw(chi2vsdeltabeta);
//     // //ec.getPad(1).getAxisZ().setLog(true);
//     // ec.cd(4).draw(W);
//     // ec.cd(5).draw(hgTh);
//     // ec.cd(6).draw(hgEn);
//     // ec.cd(7).draw(Q2);
//     // ec.cd(8).draw(ConeAngleHist);
//     // ec.cd(9).draw(ConeAngleBtElectronPhotonFD);
//     // //ec.getPad(1).getAxisZ().setLog(true);
//     // ec.cd(10).draw(PhiPlaneHist);
//     // ec.cd(11).draw(DPhiHist);
//     // ec.cd(12).draw(q2vst);
//     // //ec.cd(25).draw(helicityrawhisto);
//     // ec.cd(13).draw(thisto);
//     // ec.cd(14).draw(phivshelicityMinus);
//     // //ec.cd(14).draw(deltabeta);
//     // ec.cd(15).draw(chisqHad);
//     // ec.cd(16).draw(betacalchisto);
//     // ec.cd(17).draw(betahadhisto);
//     // ec.cd(18).draw(XvsY_electron);
//     // ec.cd(19).draw(thisto);
//     // ec.cd(20).draw(VertexElectron);
//     // ec.cd(21).draw(VertexDuetron);
//     // ec.cd(22).draw(vertexElecVSvertexDeut);
//     // ec.cd(23).draw(dedxCNDvsP);
//     // ec.cd(24).draw(dedxCTOFvsP);
//     // ec.cd(23).draw(Phiplus);
//     // ec.cd(24).draw(Phiminus);


//     //i removed this jsut because its not useful and i wanted to make plot 4/5
// //    ec.cd(14).draw(helicityhisto);
    

//     // ec.getCanvas().getScreenShot();
//     // ec.getCanvas().save(this.outputdir+"/"+ec.getTitle()+".png");


//     //ec.divide(4,3);
//     //ec2.cd(0).draw(DAngleGammaHist);

//     // ec2.cd(10).draw(ThvsPhi);
//     // ec2.cd(11).draw(MMomx);
//     // ec2.cd(12).draw(MMomy);
//     // ec2.cd(13).draw(MMomz);
//     // ec2.cd(14).draw(edgXmissingE);
//     // ec2.cd(15).draw(edgXmissingM2);
//     // ec2.cd(16).draw(edgXmissingP);
//     // ec2.cd(17).draw(edXmissingM2);
//     // ec2.cd(18).draw(egXmissingM2);
//     // ec2.cd(19).draw(ThvsPhi);
//     // ec2.cd(20).draw(hgTh);
//   }
//   public void DrawConeAngle(/*TCanvas ec,*/ TDirectory dir,  String directory ){

//     String hipodirectory = "/"+directory;
//     dir.mkdir(hipodirectory);
//     dir.cd(hipodirectory);

//     dir.addDataSet(coneanglevspperp);
//     dir.addDataSet(coneanglevsedXM2);
//     dir.addDataSet(coneanglevsegXM2);
//     dir.addDataSet(betavsP);
//     dir.writeFile(this.outputdir + "/NickRichardson.hipo");
//     // ec.divide(2,2);
//     // ec.cd(0).draw(coneanglevspperp);
//     // ec.cd(1).draw(coneanglevsedXM2);
//     // ec.cd(2).draw(coneanglevsegXM2);

//     // ec.cd(3).draw(betavsP);
//     // ec.getPad().getAxisZ().setLog(true);
//     // ec.getCanvas().save(this.outputdir+"/"+ec.getTitle()+".png");


//     //ec.divide(4,3);
//     //ec2.cd(0).draw(DAngleGammaHist);

//     // ec2.cd(10).draw(ThvsPhi);
//     // ec2.cd(11).draw(MMomx);
//     // ec2.cd(12).draw(MMomy);
//     // ec2.cd(13).draw(MMomz);
//     // ec2.cd(14).draw(edgXmissingE);
//     // ec2.cd(15).draw(edgXmissingM2);
//     // ec2.cd(16).draw(edgXmissingP);
//     // ec2.cd(17).draw(edXmissingM2);
//     // ec2.cd(18).draw(egXmissingM2);
//     // ec2.cd(19).draw(ThvsPhi);
//     // ec2.cd(20).draw(hgTh);
//   }
  // public void writeHipo(){
  //   dir.writeFile("myfile.hipo");
  // }

  
  
//OLD histo def
    //dir = new TDirectory();
    //Xbj=new H1F("Xbj","X_b",100,0,1.0);
    //kinehistos.add(Xbj);
    //W= new H1F("W","W" ,100, 0, 10.0);
    //kinehistos.add(W);
    //W.setTitleX("W [GeV]");
    //Q2 = new H1F("Q2","Q2",100, 0.1, 4.0);
    //kinehistos.add(Q2);
    //Q2.setTitleX("Q^2 [GeV/c^2]^2");
    //hadmom = new H1F("hadmom","Hadron Momentum",100,0,10.0);
    //kinehistos.add(hadmom);
    //hadmom.setTitleX("Deteron momentum [GeV/c]");
    //WvsQ2 = new H2F("Q2vsW", "Q2 vs W", 100,0,7,100,0,10);
    //kinehistos.add(WvsQ2);
    //WvsQ2.setTitleX("W [GeV]");
    //WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    //Q2vsXbj = new H2F("Q2vsXb","Q^2 vs X_b",100,0,1,100,0,10);
    //kinehistos.add(Q2vsXbj);
    //Q2vsXbj.setTitleY("Q^2 [GeV/c^2]");
    //Q2vsXbj.setTitleX("X_b");
    //q2vst = new H2F("Q2vst", "Q2 vs t",100,0,5,100,0,5);
    //kinehistos.add(q2vst);
    //q2vst.setTitleY("Q2");
    //q2vst.setTitleX("t");
    //VertexElectron = new H1F("VertexElectron","Vertex Electron", 100,-10.0,10.0);
    //kinehistos.add(VertexElectron);
   // VertexElectron.setTitleX("Vertex Electron");
    //VertexDuetron = new H1F("VertexDeuteron","Vertex Dueteron", 100,-10.0,10.0);
    //kinehistos.add(VertexDuetron);
    //VertexDuetron.setTitleX("z");
    //vertexElecVSvertexDeut = new H2F ("VertexElectronvsVertexDeuteron","Vertex Electron vs Vertex Deuteron",100,-10,10,100,-10,10);
    //kinehistos.add(vertexElecVSvertexDeut);
    //dedxDeutvsP = new H2F("dedxDeutvsP","dedxDeutvsP",100,0,2,100,0,50);
    //kinehistos.add(dedxDeutvsP);
    //dedxCTOFvsP = new H2F("dedxCTOFvsP", "dedx CTOF vs P", 100,0,2,100,0,30);
    //kinehistos.add(dedxCTOFvsP);
    //dedxCNDvsP = new H2F("dedxCNDvsP", "de/dx CND vs P", 100,0,2,100,0,30);
    //kinehistos.add(dedxCNDvsP);
    // ThvsPhi = new H2F("Deuteronthvsphi","Deuteron #theta vs #phi",100,-180,180,100,0,180);
    // kinehistos.add(ThvsPhi);
    // ThvsPhi.setTitleX("#phi [Degrees]");
    // ThvsPhi.setTitleY("#theta [Degrees]");
    // photThvsPhi = new H2F("Photonthvsphi","Photon #theta vs #phi",100,-180,180,100,0,40);
    // kinehistos.add(photThvsPhi);
    // photThvsPhi.setTitleX("#phi [Degrees]");
    // photThvsPhi.setTitleY("#theta [Degrees]");
    // elecThvsPhi = new H2F("Electronthvsphi","Electron #theta vs #phi",100,-180,180,100,0,100);
    // kinehistos.add(elecThvsPhi);
    // elecThvsPhi.setTitleX("#phi [Degrees]");
    // elecThvsPhi.setTitleY("#theta [Degrees]");

    // ThvsP = new H2F("Deuteronpvsth","Deuteron p vs #theta ",100,0,180,100,0,10.6);
    // kinehistos.add(ThvsP);
    // ThvsP.setTitleY("p [GeV/c]");
    // ThvsP.setTitleX("#theta [Degrees]");
    // elecThvsP = new H2F("Electronpvstheta","Electron p vs #theta ",100,0,40,100,0,10.6);
    // kinehistos.add(elecThvsPhi);
    // elecThvsP.setTitle("p [GeV/c]");
    // elecThvsP.setTitleX("#theta [Degrees]");
    // kinehistos.add(elecThvsP);
    // photThvsP = new H2F("Photonpvstheta","Photon p vs #theta ",100,0,40,100,0,10.6);
    // kinehistos.add(photThvsP);
    // photThvsP.setTitleY("p [GeV/c]");
    // photThvsP.setTitleX("#theta [Degrees]");
    //hgTh = new H1F("hgTh",100,0,50);
    //kinehistos.add(hgTh);
    //hgTh.setTitleX("Photon Theta");
    //hgEn = new H1F("Photonenergy","Photon energy",100,0,10);
    //kinehistos.add(hgEn);
    //hgEn.setTitleX("Photon Energy [GeV/c^2]");
    //PhiPlaneHist = new H1F("PhiPlaneHist",100,0,360);
    //kinehistos.add(PhiPlaneHist);
    //PhiPlaneHist.setTitleX("#phi, the angle between the Leptonic and Hadronic planes");
    //DPhiHist = new H1F("DPhiHist",100,-10,10);
    //kinehistos.add(DPhiHist);
    //DPhiHist.setTitle("DPhi");
    //ConeAngleBtElectronPhotonFD = new H1F("ConeAngleBtElectronandPhoton", 100,0,80);
    //kinehistos.add(ConeAngleBtElectronPhotonFD);
    //ConeAngleBtElectronPhotonFD.setTitleX("Cone Angle Between Electron and Photon");
      //phivshelicityPlus = new H2F("phivshelicityP", "phi vs helicity plus", 100,0,360,100,-4,4);
    //kinehistos.add(phivshelicityPlus);
    //phivshelicityMinus = new H2F("phivshelicityM", "phi vs helicity minus", 100,0,360,100,-4,4);
    //kinehistos.add(phivshelicityMinus);

    //helicityhisto=new H1F("Helicity",9,-4,4);
    //kinehistos.add(helicityhisto);
    //helicityhisto.setTitleX("Beam Helicity");
    //helicityrawhisto=new H1F("HelicityRaw",9,-4,4);
    //kinehistos.add(helicityrawhisto);
    //helicityrawhisto.setTitleX("Helicity Raw");
        
    //phivshelicityPlus = new H2F("phivshelicityP", "phi vs helicity plus", 100,0,360,100,-4,4);
    //kinehistos.add(phivshelicityPlus);
    //phivshelicityMinus = new H2F("phivshelicityM", "phi vs helicity minus", 100,0,360,100,-4,4);
    //kinehistos.add(phivshelicityMinus);

    //helicityhisto=new H1F("Helicity",9,-4,4);
    //kinehistos.add(helicityhisto);
    //helicityhisto.setTitleX("Beam Helicity");
    //helicityrawhisto=new H1F("HelicityRaw",9,-4,4);
    //kinehistos.add(helicityrawhisto);
    //helicityrawhisto.setTitleX("Helicity Raw");
     //XvsY_electron = new H2F("XvsY", "X vs Y",100,-400,400,100,-400,400);
    ////XvsY_electron_before = new H2F("X vs Y", "X vs Y",100,-400,400,100,-400,400);
    //kinehistos.add(XvsY_electron);
    
    //chisqHad=new H1F("chisqHad","#chi^2 PID hadron",100,-25,25);
    //kinehistos.add(chisqHad);
    // thisto = new H1F("mt","-t",100,0,5);
    // kinehistos.add(thisto);
    // thisto.setTitleX("-t [GeV/c]^2");
    // Phiplus = new H1F("Phiplus",10,0,360);
    // kinehistos.add(Phiplus);
    // Phiplus.setTitleX("Phi Plus");
    // Phiminus = new H1F("Phiminus",10,0,360);
    // kinehistos.add(Phiminus);
    // Phiminus.setTitleX("Phi Minus");
    
    //MMass = new H1F("MMass",100,-10,10);
    //MMass.setTitleX("Missing Mass Squared");
    //MMom = new H1F("MMom",100,0,10);
    //MMom.setTitleX("Missing Momentum");

    // edgXmissingE = new H1F("edgXmissingE",100,0,10);
    // exclhistos.add(edgXmissingE);
    // edgXmissingE.setTitleX("eDγX Missing Energy [GeV]");
    // exclhistos.add(edgXmissingE);
    // edgXmissingM2 = new H1F("edgXmissingM2",100,-4,4);
    // exclhistos.add(edgXmissingM2);
    // edgXmissingM2.setTitleX("eDγX Missing Mass^2 [GeV/c^2]^2");
    // edgXmissingP = new H1F("edgXmissingP",100,0,4);
    // exclhistos.add(edgXmissingP);
    // edgXmissingP.setTitleX("eDγX Missing Momentum [GeV/c]");
    // edgXmissingPx = new H1F("MMomx",100,-1,1);
    // exclhistos.add(edgXmissingPx);
    // edgXmissingPx.setTitleX("Missing X Momentum");
    // edgXmissingPy = new H1F("MMomy",100,-1,1);
    // exclhistos.add(edgXmissingPy);
    // edgXmissingPy.setTitleX("Missing Y Momentum");
    // edgXmissingPz = new H1F("MMomz",100,-5,5);
    // exclhistos.add(edgXmissingPz);
    // edgXmissingPz.setTitleX("Missing Z Momentum [GeV/c]");
    // pPerphisto = new H1F("pPerp","pPerp",100,0,3);
    // exclhistos.add(pPerphisto);

 // edXmissingE = new H1F ("edXmissingE",100,-5,5);
    // exclhistos.add(edXmissingE);
    // edXmissingE.setTitleX("eDX Missing Energy [GeV]");
    
    // edXmissingM2 = new H1F("edXmissingM2",100,-10,10);
    // exclhistos.add(edXmissingM2);
    // edXmissingM2.setTitleX("eDX Missing Mass^2 [GeV/c^2]^2");

    // edXmissingM = new H1F("eDXmissingM",100,-0,5);
    // exclhistos.add(edXmissingM);
    // edXmissingM.setTitleX("eDX Missing Mass");


    // egXmissingM2 = new H1F("egXmissingM2",100,-0,10);
    // exclhistos.add(egXmissingM2);
    // egXmissingM2.setTitleX("eGammaX Missing Mass2");
    // egXmissingM = new H1F("egXmissingM",100,-0,5);
    // exclhistos.add(egXmissingM);
    // egXmissingM.setTitleX("M_x(ed#rarrowe#gammaX)");
    // egXmissingM2vsTh =new H2F("egXmissingM2vsTh","egXmissing M2 vs Th",100,0,140,100,0,10);
    // exclhistos.add(egXmissingM2vsTh);
    // egXmissingM2vsTh.setTitleX("hadron theta");
    // DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,-10,10);
    // exclhistos.add(DeltaPhiPlaneHist);
    // DeltaPhiPlaneHist.setTitleX("Delta Phi Plane");
    // DeltaPhiPlaneMattHist = new H1F("DeltaPhiPlane2",100,-100,100);
    // exclhistos.add(DeltaPhiPlaneMattHist);
    // DeltaPhiPlaneMattHist.setTitleX("Delta Phi Plane Hattawy");

    // MissThetaHist = new H1F("MissThetaHist",100,0,180);
    // exclhistos.add(MissThetaHist);
    // MissThetaHist.setTitleX("Missing Photon Theta");

    // ConeAngleHist = new H1F("ConeAngleHist",100,-3,10);
    // exclhistos.add(ConeAngleHist);
    // ConeAngleHist.setTitleX("Angle between gamma and missing eDX");
   
    // coneanglevsedgXM2 = new H2F("coneanglevsedgXM2","eDGammaX missing M2 vs Cone Angle",100,0,20,100,-1,0.5);
    // exclhistos.add(coneanglevsedgXM2);
    // coneanglevsedgXM2.setTitleX("Cone Angle (deg.)");
    // coneanglevsedgXM2.setTitleY("eDGammaX missing M2 (GeV)");
   // coneanglevsedXM2 = new H2F("coneanglevsedXM2","M^2_x (ed#rarrow edX) vs #theta_#gamma_,_x",100,0,20,100,-10,10);
    // exclhistos.add(coneanglevsedXM2);
    // coneanglevsedXM2.setTitleX("#theta#_{#gamma,x} [deg.]");
    // coneanglevsedXM2.setTitleY("(M_x)^2(ed#rarrowed#X) [GeV/c^2]");
    
    // coneanglevspperp = new H2F("ConeAnglevsPperp", "Cone Angle vs Pperp", 100,0,10,100,0,1);
    // exclhistos.add(coneanglevspperp);
    // coneanglevspperp.setTitleX("Pperp");
    // coneanglevspperp.setTitleY("Cone Angle");
    // coneanglevsegXM2 = new H2F("coneanglevsegXM2","egX missing M2 vs Cone Angle",100,0,20,100,0,20);
    // exclhistos.add(coneanglevsegXM2);
    // coneanglevsegXM2.setTitleX("Cone Angle (deg.)");
    // coneanglevsegXM2.setTitleY("egX missing M2 (GeV)");
    // exclhistos.add(coneanglevsegXM2);



    //MMvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MMvsMpz.setTitleX("Missing Mass");
    //MMvsMpz.setTitleY("Missing Z Momentum");
    //MpxvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MpxvsMpz.setTitleX("Missing X Momentum");
    //MpxvsMpz.setTitleY("Missing Z Momentum");
    
    //DAngleGammaHist = new H1F("DAngleGammaHist",100,-15,100);
    //DAngleGammaHist.setTitle("Angle between gamma and missing eDX");
 
    

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
   // betahadhisto = new H1F("beta","#beta",100,0,1);
    // pidhistos.add(betahadhisto);
    // betacalchisto = new H1F("betacalc","#beta_calc",100,0,1);
    // pidhistos.add(betacalchisto);
    // betahadhisto.setTitleX("Measured #beta");
    // betacalchisto.setTitleX("#beta calculated from relativistic momentum");
   
    // betacalcvsP = new H2F("BetaCalcvsP","BetaCalc vs P", 100,0,10.2,100,0,1.1);
    // pidhistos.add(betacalcvsP);
    // betavsP = new H2F("BetavsP","Beta vs P", 100,0,10.2,100,0,1.1);
    // pidhistos.add(betavsP);
    // deltabetavschi2=new H2F("Deltabeta_dvschi2PID","#Delta#beta_d vs #chi^2_PID",100,-30,30,100,-0.6,0.6);
    // pidhistos.add(deltabetavschi2);
    // deltabetavschi2.setTitleX("#chi^2_PID");
    // deltabetavschi2.setTitleY("#Delta#beta_d");

    // deltabeta = new H1F("BetamBetaCalc",100,-0.6,0.2);
    // pidhistos.add(deltabeta);
    // deltabeta.setTitleX("Beta - BetaCalc");

        
    
    //ctofdedxvsp=new H2F("ctofdedxvsp",100,0,2,100,0,100);
    //pidhistos.add(ctofdedxvsp);
}





//BACKUP
// public  DvcsHisto(TDirectory rootdir, String basedir, String conf){

//   String sub1=basedir+conf+"/Kine/";
//   String sub2=basedir+conf+"/Excl/";
//   String sub3=basedir+conf+"/Pid/";
//   String sub="";
//   System.out.println(sub2);
//   sub=sub1;
//    Xbj=(H1F)rootdir.getObject(sub,"Xbj");

//    W=(H1F)rootdir.getObject(sub,"W"); //invariant mass of e target -> e' X
//    Q2=(H1F)rootdir.getObject(sub,"Q2");//Momentum transfer squared  of e-e'
//    hadmom=(H1F)rootdir.getObject(sub,"hadmom");
//    WvsQ2=(H2F)rootdir.getObject(sub,"Q2vsW");
//    Q2vsXbj=(H2F)rootdir.getObject(sub,"Q2vsXb");
//    //tvsxb=(H2F)rootdir.getObject(sub,"");
//    q2vst=(H2F)rootdir.getObject(sub,"Q2vst");

//    VertexElectron=(H1F)rootdir.getObject(sub,"VertexElectron");
//    VertexDuetron=(H1F)rootdir.getObject(sub,"VertexDeuteron");
//    vertexElecVSvertexDeut=(H2F)rootdir.getObject(sub,"VertexElectronvsVertexDeuteron");

   
//    ThvsPhi=(H2F)rootdir.getObject(sub,"Deuteronthvsphi");//Theta vs phi for hadron
//    elecThvsPhi=(H2F)rootdir.getObject(sub,"Electronthvsphi");
//    photThvsPhi=(H2F)rootdir.getObject(sub,"Photonthvsphi");
//    ThvsP=(H2F)rootdir.getObject(sub,"Deuteronpvsth");//Theta vs mom for hadron
//    elecThvsP=(H2F)rootdir.getObject(sub,"Electronpvstheta");
//    photThvsP=(H2F)rootdir.getObject(sub,"Photonpvstheta");

//    hgTh=(H1F)rootdir.getObject(sub,"hgTh");//theta gamma
//    hgEn=(H1F)rootdir.getObject(sub,"Photonenergy");//Energy gamma
//    PhiPlaneHist =(H1F)rootdir.getObject(sub,"PhiPlaneHist"); //angle between electrons plane and hadron/gamma plane
//    DPhiHist =(H1F)rootdir.getObject(sub,"DPhiHist");//phi gamma minus phi missing hadron+e vector


   
//    ConeAngleBtElectronPhotonFD=(H1F)rootdir.getObject(sub,"ConeAngleBtElectronandPhoton");

//    phivshelicityPlus=(H2F)rootdir.getObject(sub,"phivshelicityP");
//    phivshelicityMinus=(H2F)rootdir.getObject(sub,"phivshelicityM");
//    helicityhisto=(H1F)rootdir.getObject(sub,"Helicity");
//    helicityrawhisto=(H1F)rootdir.getObject(sub,"HelicityRaw");

//    XvsY_electron=(H2F)rootdir.getObject(sub,"XvsY");
  
//    chisqHad=(H1F)rootdir.getObject(sub,"chisqHad");
//    thisto=(H1F)rootdir.getObject(sub,"mt");



//    Phiplus=(H1F)rootdir.getObject(sub,"Phiplus");
//    Phiminus=(H1F)rootdir.getObject(sub,"Phiminus");
//    sub=sub2; 
//    edgXmissingE=(H1F)rootdir.getObject(sub,"edgXmissingE"); // missing mass of a complete DVCS final state e hadron gamma
//    edgXmissingM2 =(H1F)rootdir.getObject(sub,"edgXmissingM2"); // missing mass of a complete DVCS final state e hadron gamma
//    edgXmissingP =(H1F)rootdir.getObject(sub,"edgXmissingP"); // missing mass of a complete DVCS final state e hadron gamma
//    edgXmissingPx=(H1F)rootdir.getObject(sub,"MMomx");// missing px of a complete DVCS final state e hadron gamma
//    edgXmissingPy=(H1F)rootdir.getObject(sub,"MMomy");// missing py of a complete DVCS final state e hadron gamma
//    edgXmissingPz=(H1F)rootdir.getObject(sub,"MMomz");// missing pz of a complete DVCS final state e hadron gamma
//    pPerphisto=(H1F)rootdir.getObject(sub,"pPerp");

//    edXmissingE=(H1F)rootdir.getObject(sub,"edXmissingE");
//    edXmissingM2=(H1F)rootdir.getObject(sub,"edXmissingM2"); // missing mass of hadron electron final state (to be compared with gamma)
//    edXmissingM=(H1F)rootdir.getObject(sub,"eDXmissingM");
  
//    egXmissingM2=(H1F)rootdir.getObject(sub,"egXmissingM2"); // missing mass of gamma electron final state (to be compared with hadron)
//    egXmissingM=(H1F)rootdir.getObject(sub,"egXmissingM");
//    egXmissingM2vsTh=(H2F)rootdir.getObject(sub,"egXmissingM2vsTh");

//    DeltaPhiPlaneHist=(H1F)rootdir.getObject(sub,"DeltaPhiPlane"); //angle planes Q2/hadron and gamma/hadrom
//    DeltaPhiPlaneMattHist=(H1F)rootdir.getObject(sub,"DeltaPhiPlane2");//angle planes Q2/hadron and Q2/gamma
//    MissThetaHist=(H1F)rootdir.getObject(sub,"MissThetaHist");//theta missing hadron+e vector
//    ConeAngleHist=(H1F)rootdir.getObject(sub,"ConeAngleHist");//angle between gamma vector and missing hadron+e vector

//    coneanglevsedgXM2=(H2F)rootdir.getObject(sub,"coneanglevsedgXM2");//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
//    coneanglevsedXM2=(H2F)rootdir.getObject(sub,"coneanglevsedXM2");//angle between gamma vector and missing hadron+e vector vs missin mass square ehX
//    coneanglevspperp=(H2F)rootdir.getObject(sub,"ConeAnglevsPperp");
//    coneanglevsegXM2=(H2F)rootdir.getObject(sub,"coneanglevsegXM2");
//    sub=sub3;
   
   
//    betahadhisto=(H1F)rootdir.getObject(sub,"beta");
//    betacalchisto=(H1F)rootdir.getObject(sub,"betacalc");
//    betavsP=(H2F)rootdir.getObject(sub,"BetavsP");
//    deltabetavschi2=(H2F)rootdir.getObject(sub,"BetaCalcvsP");
//    deltabeta=(H1F)rootdir.getObject(sub,"BetamBetaCalc");
//    betacalcvsP=(H2F)rootdir.getObject(sub,"betacalcvsP");
//    ctofdedxvsp=(H2F)rootdir.getObject(sub,"ctofdedxvsp");
//    dedxDeutvsP=(H2F)rootdir.getObject(sub,"dedxDeutvsP");
//    dedxCTOFvsP=(H2F)rootdir.getObject(sub,"dedxCTOFvsP");
//    dedxCNDvsP=(H2F)rootdir.getObject(sub,"dedxCNDvsP");

// }
// public DvcsHisto() {
//   kinehistos= new ArrayList<Object>();
//   exclhistos= new ArrayList<Object>();
//   pidhistos = new ArrayList<Object>();
//   Xbj=createHisto("Xbj","x_b_j", "", 100, 0., 1.,"kine");
//   W=createHisto("W","W","W [GeV]",100,0,10,"kine");
//   Q2=createHisto("Q2","Q2","Q^2 [GeV/c^2]^2",100, 0.1, 4.0,"kine");
//   hadmom=createHisto("hadmom","Deuteron Momentum","p [GeV/c]",100,0,10.0,"kine");
//   WvsQ2=createHisto("Q2vsW", "Q2 vs W", "W [GeV]","Q^2 [GeV/c^2]", 100,0,7,100,0,10,"kine");
//   Q2vsXbj=createHisto("Q2vsXb","Q^2 vs X_b", "X_b", "Q^2 [GeV/c^2]", 100,0,1,100,0,10, "kine");
//   q2vst=createHisto("Q2vst",  "Q2 vs t", "t", "Q2", 100,0,5,100,0,5, "kine");
//   VertexElectron=createHisto("VertexElectron","Vertex Electron", "z",100,-10.0,10.0, "kine");
//   VertexDuetron=createHisto("VertexDeuteron","Vertex Dueteron", "z",  100,-10.0,10.0, "kine");
//   vertexElecVSvertexDeut=createHisto("VertexElectronvsVertexDeuteron","Vertex Electron vs Vertex Deuteron", "", "", 100,-10,10,100,-10,10, "kine");
  
//   ThvsPhi=createHisto("Deuteronthvsphi","Deuteron #theta vs #phi", "#phi [Degrees", "#theta [Degrees]", 100,-180,180,100,0,180, "kine");
//   photThvsPhi=createHisto("Photonthvsphi","Photon #theta vs #phi", "#phi [Degrees]", "#theta [Degrees]", 100,-180,180,100,0,180, "kine");
//   elecThvsPhi=createHisto("Electronthvsphi","Electron #theta vs #phi", "#phi [Degrees]" ,"#theta [Degrees]", 100,-180,180,100,0,180, "kine");
//   ThvsP=createHisto("Deuteronpvsth", "Deuteron p vs #theta ", "p [GeV/c]", "#theta [Degrees]", 100,0,180,100,0,10.6, "kine");
//   elecThvsP=createHisto("Electronpvstheta", "Electron p vs #theta ", "p [GeV/c]", "#theta [Degrees]", 100,0,180,100,0,10.6, "kine");
//   photThvsP=createHisto("Photonpvstheta", "Photon p vs #theta ", "p [GeV/c]", "#theta [Degrees]", 100,0,180,100,0,10.6, "kine");
//   hgTh=createHisto("hgTh", "Photon Theta", "#theta_#gamma",100,0,50,"kine");
//   hgEn=createHisto("Photonenergy", "Photon energy","E_#gamma",100,0,50,"kine");
//   PhiPlaneHist=createHisto("PhiPlaneHist", "PhiPlaneHist","",100,0,50,"kine" );
//   DPhiHist=createHisto("DPhiHist", "DPhi", "", 100,-10,10, "kine");
//   ConeAngleBtElectronPhotonFD=createHisto("ConeAngleBtElectronandPhoton", "Cone Angle Between Electron and Photon", "", 100,0,80, "kine");
//   phivshelicityPlus=createHisto("phivshelicityP", "phi vs helicity plus", "", "", 100,0,360,100,-4,4, "kine");
//   phivshelicityMinus=createHisto("phivshelicityM", "phi vs helicity minus", "", "", 100,0,360,100,-4,4, "kine");
//   helicityhisto=createHisto("Helicity", "Beam Helicity", "", 9,-4,4, "kine");
//   helicityrawhisto=createHisto("HelicityRaw", "Beam Raw Helicity", "", 9,-4,4, "kine");
//   XvsY_electron=createHisto("XvsY", "X vs Y","","",100,-400,400,100,-400,400,"kine");
//   chisqHad=createHisto("chisqHad","#chi^2 PID hadron","",100,-25,25,"kine");
//   thisto=createHisto("mt","-t","-t [GeV/c]^2",100,0,5,"kine");
//   Phiplus=createHisto("Phiplus","Phi Plus","",10,0,360,"kine");
//   Phiminus=createHisto("Phiminus","Phi Minus","",10,0,360,"kine");
 
//   //START OF EXC POTS
//   //edgX
//   edgXmissingE =createHisto("edgXmissingE", "eD#gammaX Missing Energy", "E_e_D_#gamma_X [GeV]", 100,0,10, "Excl");
//   edgXmissingM2=createHisto("edgXmissingM2", "eD#gammaX Missing Mass^2", "M^2_x [GeV/c^2]^2", 100,-4,4, "Excl");
//   edgXmissingP=createHisto("edgXmissingP", "eD#gammaX Missing p", "p_x [GeV/c]", 100,0,4, "Excl");
//   edgXmissingPx=createHisto("MMomx", "Missing X Momentum", "", 100,-1,1, "Excl");
//   edgXmissingPy=createHisto("MMomy", "Missing Y Momentum", "", 100,-1,1, "Excl");
//   edgXmissingPz=createHisto("MMomz", "Missing Z Momentum", "", 100,-5,5, "Excl");
//   pPerphisto=createHisto("pPerp", "pPerp", "", 100,0,3, "Excl");
//   //edX
//   edXmissingE =createHisto("edXmissingE", "eDX Missing Energy", "", 100,-5,5, "Excl");
//   edXmissingM2=createHisto("edXmissingM2", "eDX Missing Mass^2", "", 100,-10,10, "Excl");//M_e_D_X^2 [GeV/c^2]^2
//   edXmissingM=createHisto("eDXmissingM", "eDX Missing Mass", "", 100,-0,5, "Excl");
//   //egX
//   egXmissingM2=createHisto("egXmissingM2","e#gammaX Missing Mass2","",100,-0,10, "Excl");//M_e_#gamma_X^2 [GeV/c^2]^2
//   egXmissingM=createHisto("egXmissingM","e#gammaX Mass","",100,-0,5, "Excl");//M_e_#gamma_X [GeV/c^2]
//   egXmissingM2vsTh=createHisto("egXmissingM2vsTh","","","",100,0,140,100,0,10, "Excl");//e#gammaX MM^2 vs #theta
//   //Phi planes
//   DeltaPhiPlaneHist=createHisto("DeltaPhiPlane", "Delta Phi Plane", "", 100,-10,10, "Excl");
//   DeltaPhiPlaneMattHist=createHisto("DeltaPhiPlane2", "Delta Phi Plane Hattawy", "", 100,-10,10, "Excl");
//   MissThetaHist=createHisto("MissThetaHist", "MissThetaHist", "", 100,0,180, "Excl");
//   //cone angles
//   ConeAngleHist=createHisto("ConeAngleHist", "Angle between gamma and missing eDX", "", 100,-3,10, "Excl");
//   coneanglevsedgXM2=createHisto("coneanglevsedgXM2", "eDGammaX missing M2 vs Cone Angle", "Cone Angle (deg.)", "eDGammaX missing M2 (GeV)", 100,0,20,100,-1,0.5, "Excl");
//   coneanglevsedXM2=createHisto("coneanglevsedXM2","M^2_x (ed#rarrow edX) vs #theta_#gamma_x","#theta#_{#gamma,x} [deg.]","(M_x)^2(ed#rarrowed#X) [GeV/c^2]",100,0,20,100,-10,10, "Excl");
//   coneanglevspperp=createHisto("ConeAnglevsPperp","Cone Angle vs Pperp","Pperp","Cone Angle", 100,0,10,100,0,1,"Excl");
//   coneanglevsegXM2=createHisto("coneanglevsegXM2","egX missing M2 vs Cone Angle","Cone Angle (deg.)","egX missing M2 (GeV)",100,0,20,100,0,20,"Excl");
//   //pid histograms
//   betahadhisto=createHisto("beta","#beta","Measured #beta",100,0,1,"pid");
//   betacalchisto=createHisto("betacalc","#beta_calc","#beta calculated from relativistic momentum",100,0,1,"pid");
//   betacalcvsP=createHisto("BetaCalcvsP","BetaCalc vs P", "", "", 100,0,10.2,100,0,1.1, "pid");
//   betavsP=createHisto("BetavsP","Beta vs P", "", "", 100,0,10.2,100,0,1.1, "pid");
//   deltabetavschi2=createHisto("Deltabeta_dvschi2PID","#Delta#beta_d vs #chi^2_PID", "#chi^2_PID", "#Delta#beta_d", 100,-30,30,100,-0.6,0., "pid");
//   deltabeta=createHisto("BetamBetaCalc", "Beta - BetaCalc", "" ,100,-0.6,0., "pid");
//   ctofdedxvsp=createHisto("ctofdedxvsp", "ctofdedxvsp", "", "", 100,0,2,100,0,100, "pid");
//   dedxDeutvsP =createHisto("dedxDeutvsP","de/dx Deut vs P", "", "", 100,0,2,100,0,30, "pid");
//   dedxCTOFvsP= createHisto("dedxCTOFvsP", "de/dx CTOF vs P", "","",100,0,2,100,0,30, "pid");
//   dedxCNDvsP=createHisto("dedxCNDvsP", "de/dx CND vs P", "", "", 100,0,2,100,0,30, "pid");
//   System.out.println("creating histograms"  );
// }
