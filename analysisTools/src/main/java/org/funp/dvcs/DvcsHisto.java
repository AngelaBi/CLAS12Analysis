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

//import org.funp.dvcs.DvcsEvent;;

public class DvcsHisto {

  
  public double[] q2bins={0,1.75,2.5,10};
  public double[] tbins={0,0.3,0.6,10};//{0,0.39,0.57,10}
  public double[] xbbins={0,0.128,0.182,10};

  public H1F Xbj;
  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'

  public H1F hadrP;
  public H1F elecP;
  public H1F photP;//momentum gamma
  public H1F hadrTh;
  public H1F elecTh;
  public H1F photTh;//theta gamma

  public H2F hadrThvsPhi;//Theta vs phi for hadron
  public H2F elecThvsPhi;
  public H2F photThvsPhi;

  public H2F hadrThvsP;//Theta vs mom for hadron
  public H2F elecThvsP;
  public H2F photThvsP;

  public H2F WvsQ2;
  public H2F Q2vsXbj;
  public H2F tvsxb;
  public H2F q2vst;
  public H1F VertexElectron;
  public H1F VertexDuetron;
  public H2F vertexElecVSvertexDeut;

  public H1F targetmass;
  public H1F hadronmass;

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
  public H1F tfxhisto;
  public H2F tvstfx;

  public H1F Phiplus;
  public H1F Phiminus;
  public H1F Phi;
  public H2F tvsPhi;

  //Exclusivity plots - All these are in Missing quant folder
  public H1F edgXmissingE; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingM2 ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingP ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPx;// missing px of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPy;// missing py of a complete DVCS final state e hadron gamma
  public H1F edgXmissingPz;// missing pz of a complete DVCS final state e hadron gamma
  public H1F pPerphisto;

  public H1F edgXmissingE_mis; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingM2_mis ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingP_mis ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F pPerphisto_mis;

  public H1F edXmissingE;
  public H1F edXmissingM2; // missing mass of hadron electron final state (to be compared with gamma)
  public H1F edXmissingTh;//theta missing hadron+e vector
  //public H1F edXmissingM;

  public H1F edXmissingM2_mis; // missing mass of hadron electron final state (to be compared with gamma)
  
  public H2F edgXmissingE_D_vs_mis;
  public H2F edXmissingM2_D_vs_mis;
  public H2F edXmissingM2_misvsegXmissingM2;
  
  public H1F egXmissingM2; // missing mass of gamma electron final state (to be compared with hadron)
  public H1F egXmissingM;
  public H2F egXmissingM2vsTh;
  public H2F egXmissingM2vsEg;
  public H2F egXmissingM2vsPd;
  
  public H1F egXmissingM2_mis;
  public H2F egXmissingM_D_vs_mis;

  public H1F DeltaPhiPlaneHist; //angle planes Q2/hadron and gamma/hadrom
  public H1F DeltaPhiPlaneMattHist;//angle planes Q2/hadron and Q2/gamma
  
  public H1F ConeAngleHist;//angle between gamma vector and missing hadron+e vector

  public H2F coneanglevsedgXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
  public H2F coneanglevsedXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehX
  public H2F coneanglevspperp;
  public H2F coneanglevsegXM2;
  public H2F dphiPlanevsdphiPlane2;

  public H2F coneanglevsedXM2_mis;//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
 
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

  public H1F pionmass2;

  public H1F[] phiplusQ2bin;
  public H1F[] phiminusQ2bin;
  public H1F[] phiplustbin;
  public H1F[] phiminustbin;
  public H1F[] phiplusxbbin;
  public H1F[] phiminusxbbin;

  public H1F[] phipluspi0tbin;
  public H1F[] phiminuspi0tbin;

  public H2F photThgvElecTh;
  ArrayList<Object> kinehistos;
  ArrayList<Object> exclhistos;
  ArrayList<Object> asymhistos;
  ArrayList<Object> pidhistos;
  boolean readMode=false;
  private TDirectory rootDirfile;
  private String baseDir;//NC DC AC
  private String Config;//FT,FT or nothing
  private boolean pi0analysis=false;
  private String excl3part;
  private String excl2part;
  private String excl1part;
  private String gm;

  //binning
  


  private String outputdir=new String(".");

//   public void setOutputDir(String otherdir){
//     this.outputdir=otherdir;
//     //System.out.println("**** setting out dir for plots to" + this.outputdir);
// }
  // public void setAnalysisType(boolean pi0mode){
  //   this.pi0analysis=pi0mode;
  //   setAnalysisStrings();
  // }
  public void setAnalysisStrings(){
    if(pi0analysis){//swtich to pion
      excl3part="emh";
      excl2part="em";
      excl1part="m";
      gm="pion";
    }
    else {excl3part="egh";
    excl2part="eg";
    excl1part="g";
    gm="gamma";
    }
  }
public DvcsHisto(TDirectory rootdir, String basedir,String conf){
  readMode=true;
  rootDirfile=rootdir;
  baseDir=basedir;
  Config=conf;
  SetHisto();
  
}
  

  public DvcsHisto(boolean pi0mode,String otherdir) {
    this.outputdir=otherdir;
    this.pi0analysis=pi0mode;
    setAnalysisStrings();
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

    hadrP=createHisto("hadrP","Deuteron Momentum","p [GeV/c]",100,0,3,"Kine");
    photP=createHisto("photP", "Photon energy","E #gamma",100,0,12,"Kine");
    elecP=createHisto("elecP", "Electron energy","E elec",100,0,12,"Kine");
    hadrTh=createHisto("hadrTh", gm+" Deuteron Theta", "th "+gm,100,30,140,"Kine");
    elecTh=createHisto("elecTh", gm+" Electron Theta", "th "+gm,100,0,35,"Kine");
    photTh=createHisto("photTh", gm+" Photon Theta", "th "+gm,100,0,35,"Kine");
    

    
    WvsQ2=createHisto("Q2vsW", "Q2 vs W", "W [GeV]","Q^2 [GeV/c^2]", 100,0,7,100,0,10,"Kine");
    Q2vsXbj=createHisto("Q2vsXb","Q^2 vs X_b", "X_b", "Q^2 [GeV/c^2]", 100,0,1,100,0,10, "Kine");
    q2vst=createHisto("Q2vst",  "Q2 vs t", "t", "Q2", 100,0,2,100,0,4, "Kine");
    VertexElectron=createHisto("VertexElectron","Vertex Electron", "z",100,-10.0,10.0, "Kine");
    VertexDuetron=createHisto("VertexDeuteron","Vertex Dueteron", "z",  100,-10.0,10.0, "Kine");
    vertexElecVSvertexDeut=createHisto("VertexElectronvsVertexDeuteron","Vertex Electron vs Vertex Deuteron", "", "", 100,-10,10,100,-10,10, "Kine");
    
    targetmass=createHisto("targetmass","target mass","",100,0,3,"Kine");
    hadronmass=createHisto("hadronmass","target mass","",100,0,3,"Kine");

    hadrThvsPhi=createHisto("hadrThvsPhi","Deuteron th vs phi", "", "", 100,-180,180,100,0,180, "Kine");
    photThvsPhi=createHisto("photThvsPhi","Photon th vs phi", "", "", 100,-180,180,100,0,35, "Kine");
    elecThvsPhi=createHisto("elecThvsPhi","Electron th vs phi", "" ,"", 100,-180,180,100,0,35, "Kine");
    hadrThvsP=createHisto("hadrThvsP", "Deuteron th bs p", "", "", 100,0,3.00,100,0,180, "Kine");
    elecThvsP=createHisto("elecThvsP", "Electron th vs p ", "", "",100,0,10.6,100,0,35, "Kine");
    photThvsP=createHisto("photThvsP", "Photon th vs   p", "", "", 100,0,10.6,100,0,35, "Kine");
    
    PhiPlaneHist=createHisto("PhiPlaneHist", "PhiPlaneHist","",100,0,50,"Kine" );
    DPhiHist=createHisto("DPhiHist", "phi measured gamma minus phi of edX", "", 100,-180,180, "Excl");
    ConeAngleBtElectronPhotonFD=createHisto("ConeAngleBtElectronandPhoton", "Cone Angle Between Electron and Photon", "", 100,0,80, "Kine");
    phivshelicityPlus=createHisto("phivshelicityP", "phi vs helicity plus", "", "", 100,0,360,100,-4,4, "Kine");
    phivshelicityMinus=createHisto("phivshelicityM", "phi vs helicity minus", "", "", 100,0,360,100,-4,4, "Kine");
    helicityhisto=createHisto("Helicity", "Beam Helicity", "", 9,-4,4, "Kine");
    helicityrawhisto=createHisto("HelicityRaw", "Beam Raw Helicity", "", 9,-4,4, "Kine");
    XvsY_electron=createHisto("XvsY", "X vs Y","","",100,-400,400,100,-400,400,"Kine");
    chisqHad=createHisto("chisqHad","chi^2 PID hadron","",100,-25,25,"Kine");
    thisto=createHisto("mt","-t","-t [GeV/c]^2",100,0,2,"Kine");
    tfxhisto=createHisto("mtfx","-t","-t [GeV/c]^2",100,0,2,"Kine");
    tvstfx=createHisto("tvstfx", "t vs t fx", "", "", 100, 0, 2, 100, 0, 2, "Kine");
    Phiplus=createHisto("Phiplus","Phi Plus","",10,0,360,"Asym");
    Phiminus=createHisto("Phiminus","Phi Minus","",10,0,360,"Asym");
    Phi=createHisto("Phi","Phi","",10,0,360,"Asym");
    tvsPhi=createHisto("tvsPhi","tvsPhi","","",50,0,360,50,0,2,"Kine");
   
    //START OF EXC POTS
    //edgX
    edgXmissingE =createHisto("edgXmissingE", "eDgammaX Missing Energy", "", 100,-3,3, "Excl");
    edgXmissingM2=createHisto("edgXmissingM2", "eDgammaX Missing Mass^2", "", 100,-0.6,0.6, "Excl");
    edgXmissingP=createHisto("edgXmissingP", "eDgammaX Missing p", "", 100,0,2, "Excl");
    edgXmissingPx=createHisto("edgXmissingPx", "eDgammaX Missing px", "", 100,-0.6,0.6, "Excl");
    edgXmissingPy=createHisto("edgXmissingPy", "eDgammaX Missing py", "", 100,-0.6,0.6, "Excl");
    edgXmissingPz=createHisto("edgXmissingPz", "eDgammaX Missing pz", "", 100,-3,3, "Excl");
    pPerphisto=createHisto("edgXmissingpPerp", "eDgammaX pPerp", "", 100,0,0.8, "Excl");

    edgXmissingE_mis =createHisto("edgXmissingE_mis", "eDgammaX Missing Energy with proton", "", 100,-3,3, "Excl");
    edgXmissingM2_mis=createHisto("edgXmissingM2_mis", "edgammaX Missing Mass^2 with proton", "", 100,-0.6,0.6, "Excl");
    edgXmissingP_mis=createHisto("edgXmissingP_mis", "eDgammaX Missing p with proton", "", 100,0,2, "Excl");
    pPerphisto_mis=createHisto("edgXmissingpPerp_mis", "eDgammaX pPerp with proton", "", 100,0,0.8, "Excl");
     //edX
    edXmissingE =createHisto("edXmissingE", "eDX Missing Energy", "", 100,0,10, "Excl");
    edXmissingM2=createHisto("edXmissingM2", "eDX Missing Mass^2", "", 100,-6,6, "Excl");//M_e_D_X^2 [GeV/c^2]^2
    edXmissingTh=createHisto("edXmissingTh", "eDX missing Theta", "", 100,0,20, "Excl");
    //edXmissingM=createHisto("edXmissingM", "edX Missing Mass", "", 100,-0,5, "Excl");

    edXmissingM2_mis=createHisto("edXmissingM2_mis", "eDX Missing Mass^2 with proton", "", 100,-6,6, "Excl");//M_e_D_X^2 [GeV/c^2]^2
    edgXmissingE_D_vs_mis=createHisto("edgXE_vs_mis","eDXmissingE D vs mis D","","",100,-8,8,100,-8,8,"Excl");
    edXmissingM2_D_vs_mis=createHisto("edXM2_vs_mis","eDXmissingM2 D vs mis D","","",100,-8,8,100,-8,8,"Excl");
    edXmissingM2_misvsegXmissingM2=createHisto("edXprotvsegX", "edXprotvsegX", "", "", 100, 0, 10, 100, -4, 4, "Excl");
    //egX
    egXmissingM2=createHisto("egXmissingM2","egammaX Missing Mass2","",100,-0,10, "Excl");//M_e_gamma_X^2 [GeV/c^2]^2
    egXmissingM=createHisto("egXmissingM","egammaX Mass","",100,-0,5, "Excl");//M_e_gamma_X [GeV/c^2]
    egXmissingM2vsTh=createHisto("egXmissingM2vsTh","","","",100,0,140,100,0,10, "Excl");//egammaX MM^2 vs th
    egXmissingM2vsEg=createHisto("egXmissingM2vsEg","","","",100,0,12,100,0,10, "Excl");//egammaX MM^2 vs th
    egXmissingM2vsPd=createHisto("egXmissingM2vsPd","","","",100,0,3,100,0,10, "Excl");//egammaX MM^2 vs th

    egXmissingM_D_vs_mis=createHisto("egXmissingM_D_vs_mis","egXmissingM D vs mis D","","",100,0,5,100,0,5,"Excl");
    egXmissingM2_mis=createHisto("egXmissingM2_mis","egammaX Missing Mass^2 with proton","",100,-0,10, "Excl");//M_e_gamma_X^2 [GeV/c^2]^2
    //Phi planes
    DeltaPhiPlaneHist=createHisto("DeltaPhiPlane", "Delta Phi between planes Q2D and Dgamma", "", 100,-8,8, "Excl");
    DeltaPhiPlaneMattHist=createHisto("DeltaPhiPlane2", "Delta Phi between planes Q2D and Q2gamma", "", 100,-8,8, "Excl");
    dphiPlanevsdphiPlane2=createHisto("phiPlanevsPhiPlane2", "phiPlane vs PhiPlane2", "", "", 100, -10, 10, 100, -10, 10, "Excl");
    //cone angles
    ConeAngleHist=createHisto("ConeAngleHist", "Angle between gamma and missing eDX", "", 100,0,10, "Excl");
    coneanglevsedgXM2=createHisto("coneanglevsedgXM2", "eDGammaX MM2 vs thgammaX", "", ")", 100,0,15,100,-3,4, "Excl");
    coneanglevsedXM2=createHisto("coneanglevsedXM2","edX MM2 vs thgammaX","","",100,0,15,100,-10,10, "Excl");
    coneanglevspperp=createHisto("coneanglevsPperp","Pperp vs thgammaX","","", 100,0,15,100,0,3,"Excl");
    coneanglevsegXM2=createHisto("coneanglevsegXM2","egX MM2 vs thgammaX","","",100,0,15,100,0,7,"Excl");
    
    coneanglevsedXM2_mis=createHisto("coneanglevsedXM2_mis","MM2 edX vs thgammaX with proton","","",100,0,15,100,-10,10, "Excl");
   
    
    //Pid histograms
    betahadhisto=createHisto("beta","beta","Measured beta",100,0,1,"Pid");
    betacalchisto=createHisto("betacalc","beta calc","beta calculated from relativistic momentum",100,0,1,"Pid");
    betacalcvsP=createHisto("BetaCalcvsP","Beta calc vs P", "", "", 100,0,10.2,100,0,1.1, "Pid");
    betavsP=createHisto("BetavsP","Beta vs P", "", "", 100,0,10.2,100,0,1.1, "Pid");
    deltabetavschi2=createHisto("Deltabeta_dvschi2PID","Deltabeta_d vs chi^2 PID", "chi^2 PID", "Deltabeta_d", 100,-30,30,100,-0.6,0., "Pid");
    deltabeta=createHisto("BetamBetaCalc", "Beta - BetaCalc", "" ,100,-0.6,0., "Pid");
    ctofdedxvsp=createHisto("ctofdedxvsp", "ctofdedxvsp", "", "", 100,0,2,100,0,100, "Pid");
    dedxDeutvsP =createHisto("dedxDeutvsP","de/dx Deut vs P", "", "", 100,0,2,100,0,30, "Pid");
    dedxCTOFvsP= createHisto("dedxCTOFvsP", "de/dx CTOF vs P", "","",100,0,2,100,0,30, "Pid");
    dedxCNDvsP=createHisto("dedxCNDvsP", "de/dx CND vs P", "", "", 100,0,2,100,0,30, "Pid");
    photThgvElecTh=createHisto("photThgvElecTh", "th_{#gamma} vs th_e", "", "", 100, 0, 40, 100, 0, 40, "Kine");
    

    pionmass2=createHisto("pionmass2", "invariant mass gamma gamma", "", 100, -0.01, 0.05, "Kine");

    phiplusQ2bin=new H1F[q2bins.length-1];
    phiminusQ2bin=new H1F[q2bins.length-1];
    for (int i=0;i<q2bins.length-1;i++){
      phiplusQ2bin[i]=createHisto("PhiplusQ2bin"+i, "PhiplusQ2", "", 10, 0, 360, "Asym");
      phiminusQ2bin[i]=createHisto("PhiminusQ2bin"+i, "PhiminusQ2", "", 10, 0, 360, "Asym");
    }
    phiplustbin=new H1F[tbins.length-1];
    phiminustbin=new H1F[tbins.length-1];
    for (int i=0;i<tbins.length-1;i++){
      phiplustbin[i]=createHisto("Phiplustbin"+i, "Phiplust", "", 10, 0, 360, "Asym");
      phiminustbin[i]=createHisto("Phiminustbin"+i, "Phiminust", "", 10, 0, 360, "Asym");
    }
    phiplusxbbin=new H1F[xbbins.length-1];
    phiminusxbbin=new H1F[xbbins.length-1];
    for (int i=0;i<xbbins.length-1;i++){
      phiplusxbbin[i]=createHisto("Phiplusxbbin"+i, "Phiplusxb", "", 10, 0, 360, "Asym");
      phiminusxbbin[i]=createHisto("Phiminusxbbin"+i, "Phiminusxb", "", 10, 0, 360, "Asym");
    }
    //pi0 asymm
    phipluspi0tbin=new H1F[tbins.length-1];
    phiminuspi0tbin=new H1F[tbins.length-1];
    for (int i=0;i<q2bins.length-1;i++){
      phipluspi0tbin[i]=createHisto("Phiplustpi0bin"+i, "Phipluspi0t", "", 10, 0, 360, "Asym");
      phiminuspi0tbin[i]=createHisto("Phiminustpi0bin"+i, "Phiminuspi0t", "", 10, 0, 360, "Asym");
    }
    //System.out.println("creating histograms"  );
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
        else if(type == "Asym"){
          asymhistos.add(h);
        }
      }
      return h;
      
  }

  

  public void fillBasicHisto(DvcsEvent ev) {
    

    dedxCTOFvsP.fill(ev.vhadron.p(),ev.dedxDeutCTOF);
    dedxCNDvsP.fill(ev.vhadron.p(),ev.dedxDeutCND);
  
    
    XvsY_electron.fill(ev.elec_x,ev.elec_y);
   // XvsY_electron_after.fill(ev.elec_x,ev.elec_y);
    W.fill(ev.Wp().mass());
    Q2.fill(-ev.Q().mass2());
    
    WvsQ2.fill(ev.Wp().mass(),-ev.Q().mass2());
    Q2vsXbj.fill(ev.Xb(),-ev.Q().mass2());
    q2vst.fill( -1*ev.t().mass2(),-1*ev.Q().mass2());
    Xbj.fill(ev.Xb());

    


    //missing quantities of a complete DVCS final state e hadron gamma

      edgXmissingE.fill(ev.X(excl3part).e());
      edgXmissingM2.fill(ev.X(excl3part).mass2());
      edgXmissingP.fill(ev.X(excl3part).p());
      edgXmissingPx.fill(ev.X(excl3part).px());
      edgXmissingPy.fill(ev.X(excl3part).py());
      edgXmissingPz.fill(ev.X(excl3part).pz());

      edgXmissingE_mis.fill(ev.X_mis(excl3part).e());
      edgXmissingM2_mis.fill(ev.X_mis(excl3part).mass2());
      edgXmissingP_mis.fill(ev.X_mis(excl3part).p());
    

      egXmissingM2.fill(ev.X(excl2part).mass2());
      egXmissingM.fill(ev.X(excl2part).mass());

      egXmissingM2_mis.fill(ev.X_mis(excl2part).mass2());
      egXmissingM_D_vs_mis.fill(ev.X_mis(excl2part).mass(),ev.X(excl2part).mass());
      edXmissingM2_misvsegXmissingM2.fill(ev.X(excl2part).mass2(),ev.X_mis(excl2part).mass2());

      egXmissingM2vsTh.fill(Math.toDegrees(ev.vhadron.theta()),ev.X(excl2part).mass2());
      egXmissingM2vsPd.fill(ev.vhadron.p(),ev.X(excl2part).mass2());
      egXmissingM2vsEg.fill(ev.vphoton.p(),ev.X(excl2part).mass2());
      coneanglevsedgXM2.fill(ev.coneangle(excl1part),ev.X(excl3part).mass2());
      coneanglevsegXM2.fill(ev.coneangle(excl1part),ev.X(excl2part).mass2());
      coneanglevsedXM2.fill(ev.coneangle(excl1part),ev.X("eh").mass2());

      coneanglevsedXM2_mis.fill(ev.coneangle(excl1part),ev.X_mis("eh").mass2());
    
      coneanglevspperp.fill(ev.coneangle(excl1part),ev.pPerp() );
      ConeAngleHist.fill(ev.coneangle(excl1part));
    


    edXmissingE.fill(ev.X("eh").e());
    edXmissingM2.fill(ev.X("eh").mass2());
    //edXmissingM.fill(ev.X("eh").mass());

    edXmissingM2_mis.fill(ev.X_mis("eh").mass2());
    edgXmissingE_D_vs_mis.fill(ev.X_mis("ehg").e(),ev.X("ehg").e());
    edXmissingM2_D_vs_mis.fill(ev.X_mis("eh").mass2(),ev.X("eh").mass2());

    

    hadrThvsPhi.fill(Math.toDegrees(ev.vhadron.phi()),Math.toDegrees(ev.vhadron.theta()));
    elecThvsPhi.fill(Math.toDegrees(ev.velectron.phi()),Math.toDegrees(ev.velectron.theta()));
    photThvsPhi.fill(Math.toDegrees(ev.vphoton.phi()),Math.toDegrees(ev.vphoton.theta()));
    hadrThvsP.fill(ev.vhadron.p(),Math.toDegrees(ev.vhadron.theta()));
    elecThvsP.fill(ev.velectron.p(),Math.toDegrees(ev.velectron.theta()));
    photThvsP.fill(ev.vphoton.p(),Math.toDegrees(ev.vphoton.theta()));

    photThgvElecTh.fill(Math.toDegrees(ev.velectron.theta()),Math.toDegrees(ev.vphoton.theta()));
    pionmass2.fill(ev.vpion.mass2());
    //System.out.println(ev.vpion.mass2());
//Xbj=ev.Xb();

    photTh.fill(Math.toDegrees(ev.vphoton.theta()));
    hadrTh.fill(Math.toDegrees(ev.vhadron.theta()));
    elecTh.fill(Math.toDegrees(ev.velectron.theta()));
    photP.fill(ev.vphoton.p());
    hadrP.fill(ev.vhadron.p());
    elecP.fill(ev.velectron.p());
    //DAngleGammaHist.fill(ev.DTheta());
    
    edXmissingTh.fill(Math.toDegrees(ev.X("eh").theta()));
    PhiPlaneHist.fill(ev.PhiPlane());
    DPhiHist.fill(Math.toDegrees(ev.DPhi()));
    DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());
    DeltaPhiPlaneMattHist.fill(ev.deltaPhiPlane2());
    dphiPlanevsdphiPlane2.fill(ev.deltaPhiPlane2(),ev.deltaPhiPlane());

    

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


    targetmass.fill(ev.vTarget.mass());
    hadronmass.fill(ev.vhadron.mass());
    dedxDeutvsP.fill(ev.vhadron.p(),ev.getDedxDeut());
    chisqHad.fill(ev.chi2pid());
    deltabetavschi2.fill(ev.chi2pid(),ev.beta()-ev.BetaCalc());
    

    helicityhisto.fill(ev.helicity);
    helicityrawhisto.fill(ev.helicityraw);
    thisto.fill(-1*ev.t().mass2());
    tfxhisto.fill(-1*ev.tFX());
    tvstfx.fill(-1*ev.tFX(),-1*ev.t().mass2());
    pPerphisto.fill(ev.pPerp());
    pPerphisto_mis.fill(ev.pPerp_mis());


    
    Phi.fill(ev.PhiPlane());
    tvsPhi.fill(ev.PhiPlane(),-1*ev.t().mass2());
    if(ev.helicity==1){
      Phiplus.fill(ev.PhiPlane());
      phivshelicityPlus.fill(ev.PhiPlane(), ev.helicity);
    }
    else if (ev.helicity==-1){
      Phiminus.fill(ev.PhiPlane());
      phivshelicityMinus.fill(ev.PhiPlane(), ev.helicity);
    }
    for (int i=0;i<q2bins.length-1;i++){
      if(-ev.Q().mass2()>q2bins[i] && -ev.Q().mass2()<q2bins[i+1]){
      if(ev.helicity==1){       
        phiplusQ2bin[i].fill(ev.PhiPlane());    
      }
      else if (ev.helicity==-1){
        phiminusQ2bin[i].fill(ev.PhiPlane());
      }
    }
    }
    for (int i=0;i<xbbins.length-1;i++){
      if(ev.Xb()>xbbins[i] && ev.Xb()<xbbins[i+1]){
      if(ev.helicity==1){       
        phiplusxbbin[i].fill(ev.PhiPlane());    
      }
      else if (ev.helicity==-1){
        phiminusxbbin[i].fill(ev.PhiPlane());
      }
    }
    }
    for (int i=0;i<tbins.length-1;i++){
      //old t value (-1*ev.t().mass2()>tbins[i] && -1*ev.t().mass2()<tbins[i+1])
      if(-1*ev.tFX()>tbins[i] && -1*ev.tFX()<tbins[i+1]){
      if(ev.helicity==1){       
        phiplustbin[i].fill(ev.PhiPlane()); 
        if(ev.SelectPion()){
          phipluspi0tbin[i].fill(ev.PhiPlanePi0()); 
        }   
      }
      else if (ev.helicity==-1){
        phiminustbin[i].fill(ev.PhiPlane());
        if(ev.SelectPion()){
          phiminuspi0tbin[i].fill(ev.PhiPlanePi0()); 
        } 
      }
    }
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
    //rootdir.writeFile(this.outputdir + "/Angela.hipo");
  
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
}

  
  
  
