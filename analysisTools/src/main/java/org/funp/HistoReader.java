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

public class HistoReader {
  static TDirectory hipobasedir;
  static TCanvas ec4;
  static TCanvas ecA;
  static DvcsHisto hDCFT;
  static DvcsHisto hDCFD;
  static DvcsHisto hCCFT;
  static DvcsHisto hCCFD;
  static DvcsHisto hACFT;
  
  static DvcsHisto hACFD;
  static processInput inputParam;

  public static void main(String[] args) throws FileNotFoundException, IOException {
    inputParam = new processInput(args);
    onefilePlots();
    
    //mergeThreeRunperiods();

  }
  public static void onefilePlots(){
    
    hipobasedir = new TDirectory();
    hipobasedir.readFile(inputParam.gethipoFile());
    hDCFT = new DvcsHisto(hipobasedir, "DC", "FT");
    hDCFD = new DvcsHisto(hipobasedir, "DC", "FD");
    hCCFT = new DvcsHisto(hipobasedir, "CC", "FT");
    hCCFD = new DvcsHisto(hipobasedir, "CC", "FD");
    hACFT = new DvcsHisto(hipobasedir, "AC", "FT");
    hACFD = new DvcsHisto(hipobasedir, "AC", "FD");

    TCanvas ec = new TCanvas("Exclusivity cuts for selection of edg FT", 1200, 1000);
    displayExcCuts(ec, hDCFT);
    TCanvas ec2 = new TCanvas("Exclusivity cuts for selection of edg FT after coneangle cuts", 1200, 1000);
    displayExcCuts(ec2, hCCFT);
    TCanvas ec3 = new TCanvas("Exclusivity cuts for selection of edg FT", 1200, 1000);
    displayExcCuts(ec3, hACFT);
    TCanvas ec4 = new TCanvas("Exclusivity cuts for selection of edg FD", 1200, 1000);
    displayExcCuts(ec4, hDCFD);
    TCanvas ec5 = new TCanvas("Exclusivity cuts for selection of edg FD  after coneangle cuts", 1200, 1000);
    displayExcCuts(ec5, hCCFD);
    TCanvas ec6 = new TCanvas("Exclusivity cuts for selection of edg FD", 1200, 1000);
    displayExcCuts(ec6, hACFD);
    //TCanvas ecA = new TCanvas("Asym FT", 1200, 1000);
    //drawAsym(ecA, hACFT);
    //TCanvas ecA2 = new TCanvas("Asym FD", 1200, 1000);
    //drawAsym(ecA2, hACFD);
    TCanvas ect1 = new TCanvas("Asym FT", 1200, 500);
    drawAsymtbins(ect1, hACFT);
    TCanvas ect2 = new TCanvas("Asym FD", 1200, 500);
    drawAsymtbins(ect2, hACFD);

  }
  
  public static void displayOthercuts(TCanvas c, DvcsHisto h){
    c.divide(2, 2);
    c.cd(0).draw(h.VertexElectron);
    drawCut(-6.5, h.VertexElectron, c, 7);
    drawCut(0., h.VertexElectron, c, 7);

    c.cd(1).draw(h.vertexElecVSvertexDeut);
  }
  public static void displayExcCuts(TCanvas ec, DvcsHisto h) {

    ec.divide(3, 3);
    ec.cd(0).draw(h.coneanglevsedXM2);

    ec.cd(1).draw(h.edXmissingM2);
    drawCut(-1., h.edXmissingM2, ec, 1);

    ec.cd(2).draw(h.edgXmissingE);
    drawCut(1, h.edgXmissingE, ec, 2);
    drawCut(2, h.edgXmissingE, ec, 2);

    ec.cd(3).draw(h.pPerphisto);
    drawCut(0.5, h.pPerphisto, ec, 3);

    ec.cd(4).draw(h.edgXmissingP);
    drawCut(0.5, h.edgXmissingP, ec, 4);
    drawCut(0.8, h.edgXmissingP, ec, 4);

    ec.cd(5).draw(h.edXmissingM);
    //drawCut(0.7, h.edXmissingM, ec, 5);

    ec.cd(6).draw(h.chisqHad);

    //ec.cd(7).draw(h.coneanglevsegXM2);

    ec.cd(8).draw(h.egXmissingM2);

    

  }

  public static void drawCut(double cut, H1F histo, TCanvas canvas, int pad) {
    DataLine line = new DataLine(cut, 0, cut, histo.getMax());
    line.setLineColor(2);
    line.setLineWidth(2);
    canvas.cd(pad).draw(line);
  }

  public static H1F buildAsym(H1F hp,H1F hm) {
    H1F num;
    H1F denom;
    H1F Asym;
    num = new H1F("num", 10, 0, 360);
    denom = new H1F("denom", 10, 0, 360);
    Asym = new H1F("Asymmetry", "Asymmetry", 10, 0, 360);
    num.add(hp);
    num.sub(hm);
    denom.add(hp);
    denom.add(hm);
    
    // num.add(h.phiplustbin[0]);
    // num.sub(h.phiminustbin[0]);
    // denom.add(h.phiplustbin[0]);
    // denom.add(h.phiminustbin[0]);

    Asym = H1F.divide(num, denom);
    Asym.divide(0.8);
    Asym.setTitleX("#phi [deg.]");
    Asym.setTitleY("A_LU(#phi)");

    // H1F Asym = (H1F) dir2.getObject(dir + "/", directory);
    return Asym;
  }

  public static void drawAsym(TCanvas ec, DvcsHisto h) {
    ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw((buildAsym(h.Phiplus,h.Phiminus)), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(h.Phiplus,h.Phiminus), "");
    ec.draw(Asymfunc, "same");
  }
  public static void drawAsymtbins(TCanvas ec, DvcsHisto h) {
    ec.divide(3,1);
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw((buildAsym(h.phiplustbin[i],h.phiminustbin[i])), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(h.phiplustbin[i],h.phiminustbin[i]), "");
    ec.draw(Asymfunc, "same");
    }
  }
  //BAD programming to quickly merge run periods
  public static void mergeThreeRunperiods(){
    TDirectory hipobasedir1 = new TDirectory();
    TDirectory hipobasedir2 = new TDirectory();
    TDirectory hipobasedir3 = new TDirectory();
    hipobasedir1.readFile("S19.hipo");
    hipobasedir2.readFile("F19.hipo");
    hipobasedir3.readFile("S20.hipo");
    DvcsHisto hACFT1= new DvcsHisto(hipobasedir1, "AC", "FT");
    DvcsHisto hACFD1 = new DvcsHisto(hipobasedir1, "AC", "FD");
    DvcsHisto hACFT2= new DvcsHisto(hipobasedir2, "AC", "FT");
    DvcsHisto hACFD2 = new DvcsHisto(hipobasedir2, "AC", "FD");
    DvcsHisto hACFT3= new DvcsHisto(hipobasedir3, "AC", "FT");
    DvcsHisto hACFD3 = new DvcsHisto(hipobasedir3, "AC", "FD");
    TCanvas ect1 = new TCanvas("Asym FT", 1200, 500);
    drawAsymtbins3runperiods(ect1, hACFT1,hACFT2,hACFT3);
    TCanvas ect2 = new TCanvas("Asym FD", 1200, 500);
    drawAsymtbins3runperiods(ect2, hACFD1, hACFD2, hACFD3);
  }
  public static void drawAsymtbins3runperiods(TCanvas ec, DvcsHisto h1, DvcsHisto h2, DvcsHisto h3){
    ec.divide(3,1);
    H1F totPhip= new H1F("totPhip", 10, 0, 360);
    H1F totPhim= new H1F("totPhim", 10, 0, 360);
    for(int i=0;i<3;i++){
      ec.cd(i);
      ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
      totPhip.add(h1.phiplustbin[i]);
      //totPhip.add(h2.phiplustbin[i]);
      totPhip.add(h3.phiplustbin[i]);
      totPhim.add(h1.phiminustbin[i]);
      //totPhim.add(h2.phiminustbin[i]);
      totPhim.add(h3.phiminustbin[i]);
      ec.draw((buildAsym(totPhip,totPhim)), "E");
      F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
      Asymfunc.setParameter(0, 0.1);
      DataFitter.fit(Asymfunc, buildAsym(totPhip,totPhim), "");
      ec.draw(Asymfunc, "same");
    }

  }
}
