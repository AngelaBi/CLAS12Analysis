package org.funp;
//package org.funp.dvcs;
import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jline.terminal.impl.jna.freebsd.CLibrary.winsize;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;
//import org.jlab.jnp.reader.*;

import org.funp.dvcs.*;
import org.funp.utilities.*;


import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jlab.groot.data.TDirectory;


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

public class HistoReader{
    static TDirectory hipobasedir;
    static TCanvas ec4;
    static TCanvas ecA;
    static DvcsHisto hDCFT;
    static DvcsHisto hACFT;
    

    
    public static void main( String[] args ) throws FileNotFoundException, IOException 
  {
    hDCFT=new DvcsHisto();
    hACFT=new DvcsHisto();
    processInput inputParam=new processInput(args);
    hipobasedir = new TDirectory();
    hipobasedir.readFile("Angela.hipo");
    hDCFT= new DvcsHisto(hipobasedir,"DC","FT");
    hACFT= new DvcsHisto(hipobasedir,"AC","FT");


    TCanvas ec = new TCanvas("Exclusivity cuts for selection of edg FT",1200,1000);
    DisplayExcCuts(ec,hDCFT);
    TCanvas ec2 = new TCanvas("Exclusivity cuts for selection of edg FT",1200,1000);
    DisplayExcCuts(ec2,hACFT);

    //TCanvas ec = new TCanvas("Exclusivity cuts for selection of edg FT",1200,1000);
    //DrawExcCuts( ec, "DC","FT");//changed this line
    
 
  

  }
public static void DisplayExcCuts(TCanvas ec,DvcsHisto h){
  
  ec.divide(3,3); 
  ec.cd(0).draw(h.coneanglevsedXM2);
  ec.cd(1).draw(h.edXmissingM2);
  ec.cd(2).draw(h.edgXmissingE);
  ec.cd(3).draw(h.pPerphisto);
  ec.cd(4).draw(h.edgXmissingP);
  ec.cd(5).draw(h.edXmissingM);
  ec.cd(6).draw(h.chisqHad);
  ec.cd(7).draw(h.VertexElectron);
  ec.cd(8).draw(h.vertexElecVSvertexDeut);

}

public static void DrawExcCuts(TCanvas ec4, String basedir,String conf){
  /* FT

        *      (this.X("eh").mass2() < (-1.5* this.coneangle()+2) HIST EXISTS coneanglevsedXM2 HIPO
        *       && this.X("eh").mass2() >-2   HIST EXISTS edXmissingM2. ??
        *       && this.X("ehg").e()<2.       HIST EXISTS edgXmissingE
        *       && this.pPerp()<0.5.           HIST EXISTS  pPerphisto
        *       &&this.X("ehg").p()<1.5      HIST EXISTS edgXmissingP
        *       && Math.abs(this.chi2pid()) < 3.5 TO REMOVE?
        *       && this.X("eh").mass() < 0.7  HIST EXISTS edXmissingM
        *       && vertexCut HISTS exist VertexElectron VertexDuetron


  */
  /* 
      (this.X("eh").mass2() < (-1* this.coneangle()+2)  different
      && this.X("eh").mass2()>-2   same
      && this.X("ehg").mass2()>-0.75    redudants?
      && this.X("ehg").e()<3  different 
      && this.pPerp()<0.5 same
      &&this.X("ehg").p()<1.5 same
      && Math.abs(this.chi2pid()) < 3.5 
      && this.X("eh").mass() < 0.7 same
      && vertexCut

  */
  
  String dir=basedir+conf+"/Excl/";
  H2F coneanglevsedXM2 =(H2F) hipobasedir.getObject(dir,"coneanglevsedXM2");
  // H1F edXmissingM2=(H1F) hipobasedir.getObject(dir,"edXmissingM2");
  // H1F edgXmissingE=(H1F) hipobasedir.getObject(dir,"edgXmissingE");
  // H1F pPerphisto=(H1F) hipobasedir.getObject(dir,"pPerp");
  // H1F edgXmissingP=(H1F) hipobasedir.getObject(dir,"edgXmissingP");
  // H1F eDXmissingM=(H1F) hipobasedir.getObject(dir,"eDXmissingM");
  // H1F Chi2Pid=(H1F) hipobasedir.getObject("Kinematics DVCS Cut "+conf+"/","Chi2Pid");
  // H1F VertexE=(H1F) hipobasedir.getObject("Kinematics DVCS Cut "+conf+"/","VertexElectron");
  // H1F VertexD=(H1F) hipobasedir.getObject("Kinematics DVCS Cut "+conf+"/","VertexDeuteron");
  
 

  ec4.divide(3,3);
  ec4.cd(0).draw(coneanglevsedXM2);
  //ec4.cd(1).draw(edXmissingM2);
  // drawCut(-2.,edXmissingM2,ec4,1);
 
  // ec4.cd(2).draw(edgXmissingE);
  // double cc=0;
  // if(conf=="FT") cc=2;
  // else cc=3;
  // drawCut(cc,edgXmissingE,ec4,2);
 
  // ec4.cd(3).draw(pPerphisto);
  // drawCut(0.5,pPerphisto,ec4,3);
  // ec4.cd(4).draw(edgXmissingP);
  // drawCut(1.5,edgXmissingP,ec4,4);
  // ec4.cd(5).draw(eDXmissingM);
  // drawCut(0.7,eDXmissingM,ec4,5);
  // ec4.cd(6).draw(Chi2Pid);
  // ec4.cd(7).draw(VertexE);
  // drawCut(-6.5,VertexE,ec4,7);
  // drawCut(0.,VertexE,ec4,7);
  // ec4.cd(8).draw(VertexD);



 
}
public static void drawCut(double cut,H1F histo,TCanvas canvas,int pad){
  DataLine line = new DataLine(cut,0,cut,histo.getMax());
  line.setLineColor(2);
  line.setLineWidth(2);
  canvas.cd(pad).draw(line);
}


}



