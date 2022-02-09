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

  public static void main(String[] args) throws FileNotFoundException, IOException {
    hDCFT = new DvcsHisto();
    hACFT = new DvcsHisto();
    processInput inputParam = new processInput(args);
    hipobasedir = new TDirectory();
    hipobasedir.readFile("Angela.hipo");
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
    TCanvas ecA = new TCanvas("Asym FT", 1200, 1000);
    drawAsym(ecA, hACFT);
    TCanvas ecA2 = new TCanvas("Asym FD", 1200, 1000);
    drawAsym(ecA2, hACFD);

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
    drawCut(-2., h.edXmissingM2, ec, 1);

    ec.cd(2).draw(h.edgXmissingE);
    double cc = 0;
    // if(conf=="FT") cc=2;
    // else
    cc = 3;
    drawCut(cc, h.edgXmissingE, ec, 2);

    ec.cd(3).draw(h.pPerphisto);
    drawCut(0.5, h.pPerphisto, ec, 3);

    ec.cd(4).draw(h.edgXmissingP);
    drawCut(1.5, h.edgXmissingP, ec, 4);

    ec.cd(5).draw(h.edXmissingM);
    drawCut(0.7, h.edXmissingM, ec, 5);

    ec.cd(6).draw(h.chisqHad);

    ec.cd(7).draw(h.coneanglevsegXM2);

    ec.cd(8).draw(h.egXmissingM2);

    

  }

  public static void drawCut(double cut, H1F histo, TCanvas canvas, int pad) {
    DataLine line = new DataLine(cut, 0, cut, histo.getMax());
    line.setLineColor(2);
    line.setLineWidth(2);
    canvas.cd(pad).draw(line);
  }

  public static H1F buildAsym(DvcsHisto h) {
    H1F num;
    H1F denom;
    H1F Asym;
    num = new H1F("num", 10, 0, 360);
    denom = new H1F("denom", 10, 0, 360);
    Asym = new H1F("Asymmetry", "Asymmetry", 10, 0, 360);
    num.add(h.Phiplus);
    num.sub(h.Phiminus);
    denom.add(h.Phiplus);
    denom.add(h.Phiminus);

    Asym = H1F.divide(num, denom);
    Asym.divide(0.8);
    Asym.setTitleX("#phi [deg.]");
    Asym.setTitleY("A_LU(#phi)");

    // H1F Asym = (H1F) dir2.getObject(dir + "/", directory);
    return Asym;
  }

  public static void drawAsym(TCanvas ec, DvcsHisto h) {
    ec.getPad().setAxisRange(0, 360, -0.8, 0.8);
    ec.draw((buildAsym(h)), "E");

    F1D Asymfunc = new F1D("Asymfunc", "[A]*sin(x * 2 * 3.14 /360)  ", 0, 360);
    // Asymfunc.setParameter(0,0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.1);
    Asymfunc.setParameter(0, 0.1);
    // Asymfunc.setParameter(1,0.01);
    // Asymfunc.setParameter(2,-0.01);
    DataFitter.fit(Asymfunc, buildAsym(h), "");
    ec.draw(Asymfunc, "same");
  }
}
