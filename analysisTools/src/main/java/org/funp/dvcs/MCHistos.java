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

public class MCHistos {

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

  public H1F targetmass;
  public H1F hadronmass;

 
  public H1F pionmass2;

  ArrayList<Object> kinehistosMC;
  ArrayList<Object> exclhistosMC;
  ArrayList<Object> asymhistosMC;
  ArrayList<Object> pidhistosMC;
  
  boolean readMode=false;
  private TDirectory rootDirfile;
  private String baseDir;//NC DC AC
  private String Config;//FT,FT or nothing
  private boolean pi0analysis=false;
  private String excl3part;
  private String excl2part;
  private String excl1part;
  private String gm;

  


  private String outputdir=new String(".");

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
public MCHistos(TDirectory rootdir, String basedir,String conf){
  readMode=true;
  rootDirfile=rootdir;
  baseDir=basedir;
  Config=conf;
  SetMCHisto();
  
}
  

  public MCHistos(boolean pi0mode,String otherdir) {
    this.outputdir=otherdir;
    this.pi0analysis=pi0mode;
    setAnalysisStrings();
    SetMCHisto();
  }
  public void SetMCHisto(){
    
  
    
    this.kinehistosMC= new ArrayList<Object>();
    
    
   
    hadrP=createMCHisto("hadrP","Deuteron Momentum","p [GeV/c]",100,0,3,"KineMC");
    photP=createMCHisto("photP", gm+" Energy","E "+gm,100,0,12,"KineMC");
    elecP=createMCHisto("elecP", "Electron energy","E elec",100,0,12,"KineMC");
    hadrTh=createMCHisto("hadrTh", "Deuteron Theta", "th "+gm,100,30,140,"KineMC");
    elecTh=createMCHisto("elecTh", "Electron Theta", "th "+gm,100,0,35,"KineMC");
    photTh=createMCHisto("photTh", gm+" Theta", "th "+gm,100,0,35,"KineMC");
     
    targetmass=createMCHisto("targetmass","target mass","",100,0,3,"KineMC");
    hadronmass=createMCHisto("hadronmass","target mass","",100,0,3,"KineMC");

    hadrThvsPhi=createMCHisto("hadrThvsPhi","Deuteron th vs phi", "", "", 100,-180,180,100,0,180, "KineMC");
    photThvsPhi=createMCHisto("photThvsPhi","Photon th vs phi", "", "", 100,-180,180,100,0,35, "KineMC");
    elecThvsPhi=createMCHisto("elecThvsPhi","Electron th vs phi", "" ,"", 100,-180,180,100,0,35, "KineMC");
    hadrThvsP=createMCHisto("hadrThvsP", "Deuteron th bs p", "", "", 100,0,3.00,100,0,180, "KineMC");
    elecThvsP=createMCHisto("elecThvsP", "Electron th vs p ", "", "",100,0,10.6,100,0,35, "KineMC");
    photThvsP=createMCHisto("photThvsP", "Photon th vs   p", "", "", 100,0,10.6,100,0,35, "KineMC");
    
    pionmass2=createMCHisto("pionmass2", "invariant mass gamma gamma", "", 100, -0.01, 0.05, "KineMC");

   
  }
  public H1F createMCHisto(String name,
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
        if(type == "KineMC"){
          this.kinehistosMC.add(h);
        }
        else if(type == "ExclMC"){
          this.exclhistosMC.add(h);
        }
        else if(type == "PidMC"){
          this.pidhistosMC.add(h);
        }
        else if(type == "AsymMC"){
          this.asymhistosMC.add(h);
        }
      }
      return h;
      
  }
  public H2F createMCHisto(String name,
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
        if(type == "KineMC"){
          this.kinehistosMC.add(h);
        }
        else if(type == "ExclMC"){
          this.exclhistosMC.add(h);
        }
        else if(type == "PidMC"){
          this.pidhistosMC.add(h);
        }
        else if(type == "AsymMC"){
          this.asymhistosMC.add(h);
        }
      }
      return h;
      
  }

  

  public void fillMCBasicHisto(DvcsEvent ev) {
    

    
   
    


    //missing quantities of a complete DVCS final state e hadron gamma

     

    hadrThvsPhi.fill(Math.toDegrees(ev.vhadronMC.phi()),Math.toDegrees(ev.vhadronMC.theta()));
    elecThvsPhi.fill(Math.toDegrees(ev.velectronMC.phi()),Math.toDegrees(ev.velectronMC.theta()));
    photThvsPhi.fill(Math.toDegrees(ev.vphotonMC.phi()),Math.toDegrees(ev.vphotonMC.theta()));
    hadrThvsP.fill(ev.vhadronMC.p(),Math.toDegrees(ev.vhadronMC.theta()));
    elecThvsP.fill(ev.velectronMC.p(),Math.toDegrees(ev.velectronMC.theta()));
    photThvsP.fill(ev.vphotonMC.p(),Math.toDegrees(ev.vphotonMC.theta()));

    
    pionmass2.fill(ev.vpionMC.mass2());
    //System.out.println(ev.vpionMC.mass2());
    //Xbj=ev.Xb();
    if(gm=="pion"){
      photTh.fill(Math.toDegrees(ev.vpionMC.theta()));
      photP.fill(ev.vpionMC.p());
    }
    else if(gm=="gamma"){
      photTh.fill(Math.toDegrees(ev.vphotonMC.theta()));
      photP.fill(ev.vphotonMC.p());
    }
    
    hadrTh.fill(Math.toDegrees(ev.vhadronMC.theta()));
    elecTh.fill(Math.toDegrees(ev.velectronMC.theta()));
    
    hadrP.fill(ev.vhadronMC.p());
    elecP.fill(ev.velectronMC.p());
    
    targetmass.fill(ev.vTarget.mass());
    hadronmass.fill(ev.vhadronMC.mass());


  }

  public  void writeMCHipooutput(TDirectory rootdir,String directory){
    String hipodirectory = "/"+directory;

    String[] sub={hipodirectory+"/KineMC",hipodirectory+"/ExclMC",hipodirectory+"/PidMC",hipodirectory+"/AsymMC"};
    
    
    rootdir.mkdir(sub[0]);
    rootdir.cd(sub[0]);
    for (Object obj: this.kinehistosMC) {
      if (obj instanceof H1F){
        //System.out.println("**** " + sub[0] +"/"+((H1F) obj).getName());
        rootdir.addDataSet((H1F) obj);
      } else if (obj instanceof H2F) {
        //System.out.println("**** " + sub[0] +"/"+((H2F) obj).getName());
        rootdir.addDataSet((H2F) obj);      } 
    }
    // rootdir.mkdir(sub[1]);
    // rootdir.cd(sub[1]);
    // for (Object obj: this.exclhistosMC) {
    //   if (obj instanceof H1F){
    //     rootdir.addDataSet((H1F) obj);
    //   } else if (obj instanceof H2F) {
    //     rootdir.addDataSet((H2F) obj);      } 
    // }
    // rootdir.mkdir(sub[2]);
    // rootdir.cd(sub[2]);
    // for (Object obj: this.pidhistosMC) {
    //   if (obj instanceof H1F){
    //     rootdir.addDataSet((H1F) obj);
    //   } else if (obj instanceof H2F) {
    //     rootdir.addDataSet((H2F) obj);      } 
    // }
    // rootdir.mkdir(sub[3]);
    // rootdir.cd(sub[3]);
    // for (Object obj: this.asymhistosMC) {
    //   if (obj instanceof H1F){
    //     rootdir.addDataSet((H1F) obj);
    //   } else if (obj instanceof H2F) {
    //     rootdir.addDataSet((H2F) obj);      } 
    // }
    
   
  
  }
 

}

  
  
  
