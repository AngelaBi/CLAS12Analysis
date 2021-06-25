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
import java.lang.Math;

//import org.funp.dvcs.DvcsEvent;;

public class Particle_ID_Histo {

    public H2F ctof_betavsPprot;
    public H2F ctof_betavsPpion;
    public H2F ctof_betavsPkaon;
    public H2F ctof_betavsPdeut;
    public H2F ctof_dedxvspdeut;
    public H2F ctof_dedxvspprot;
    public H2F ctof_dedxvsppion;
    public H2F ctof_dedxvspkaon;
    public H1F ctof_chi2pidDeutBefore;
    public H1F ctof_chi2pidDeutAfter;

    public H2F cnd_betavsPprot;
    public H2F cnd_betavsPpion;
    public H2F cnd_betavsPkaon;
    public H2F cnd_betavsPdeut;
    public H2F cnd_dedxvspdeut;
    public H2F cnd_dedxvspprot;
    public H2F cnd_dedxvsppion;
    public H2F cnd_dedxvspkaon;
    public H1F cnd_chi2pidDeutBefore;
    public H1F cnd_chi2pidDeutAfter;
    private String outputdir=new String(".");

    public int numDeutsCTOF;
    public int numDeutsCND;


    public Particle_ID_Histo(){
        //emtpy constructor
        ctof_betavsPprot = new H2F("beta vs P Prot", "beta vs P Prot", 100,0,2.5,100,0,1.1);
        ctof_betavsPpion = new H2F("beta vs P Pion", "beta vs P Pion", 100,0,2.5,100,0,1.1);
        ctof_betavsPkaon = new H2F("beta vs P Kaon", "beta vs P Kaon", 100,0,2.5,100,0,1.1);
        ctof_betavsPdeut = new H2F("beta vs P Deut", "beta vs P Deut", 100,0,2.5,100,0,1.1);
        ctof_dedxvspprot = new H2F("dedx vs P Prot", "dedx vs P Prot", 100,0,2,100,0,25);
        ctof_dedxvsppion = new H2F("dedx vs P Pion", "dedx vs P Pion", 100,0,2,100,0,25);
        ctof_dedxvspkaon = new H2F("dedx vs P Kaon", "dedx vs P Kaon", 100,0,2,100,0,25);
        ctof_dedxvspdeut = new H2F("dedx vs P Deut", "dedx vs P Deut", 100,0,2,100,0,25);
        ctof_chi2pidDeutBefore = new H1F("chi2pid Deut",100,-20,20);
        ctof_chi2pidDeutAfter = new H1F("chi2pid Deut",100,-20,20);

        cnd_betavsPprot = new H2F("beta vs P Prot", "beta vs P Prot", 100,0,2.5,100,0,1.1);
        cnd_betavsPpion = new H2F("beta vs P Pion", "beta vs P Pion", 100,0,2.5,100,0,1.1);
        cnd_betavsPkaon = new H2F("beta vs P Kaon", "beta vs P Kaon", 100,0,2.5,100,0,1.1);
        cnd_betavsPdeut = new H2F("beta vs P Deut", "beta vs P Deut", 100,0,2.5,100,0,1.1);
        cnd_dedxvspprot = new H2F("dedx vs P Prot", "dedx vs P Prot", 100,0,2,100,0,25);
        cnd_dedxvsppion = new H2F("dedx vs P Pion", "dedx vs P Pion", 100,0,2,100,0,25);
        cnd_dedxvspkaon = new H2F("dedx vs P Kaon", "dedx vs P Kaon", 100,0,2,100,0,25);
        cnd_dedxvspdeut = new H2F("dedx vs P Deut", "dedx vs P Deut", 100,0,2,100,0,25);
        cnd_chi2pidDeutBefore = new H1F("chi2pid Deut",100,-20,20);
        cnd_chi2pidDeutAfter = new H1F("chi2pid Deut",100,-20,20);
        numDeutsCND = 0;
        numDeutsCTOF = 0;
    }

   

    public void fillDeutCTOF(PositiveEvent pos_ev){
        
        // if (pos_ev.dedxDeutCTOF>0 && pos_ev.chi2piddeutCTOF<3 && pos_ev.chi2piddeutCTOF>-3) {
        //     ctof_dedxvspdeut.fill(pos_ev.vdeuteron.p(),pos_ev.dedxDeutCTOF);
        //     ctof_dedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
        //     ctof_betavsPdeut.fill(pos_ev.vdeuteron.p(),pos_ev.betadeutCTOF);
        //     ctof_betavsPdeut.setTitle("Beta vs Momentum Deuteron");
        //     ctof_chi2pidDeutBefore.fill(pos_ev.chi2piddeutCTOF);
        // }
        ctof_chi2pidDeutBefore.fill(pos_ev.chi2piddeutCTOF);
        if (/*((pos_ev.vdeuteron.p() < 0.75 /*&& pos_ev.dedxDeutCTOF>64*pos_ev.vdeuteron.p()-6.4) || (pos_ev.vdeuteron.p() >= 0.75 && pos_ev.dedxDeutCTOF > (-4.8707* Math.pow(pos_ev.vdeuteron.p(),6) +23.095*Math.pow(pos_ev.vdeuteron.p(),5) + -12.201*Math.pow(pos_ev.vdeuteron.p(),4)-109.96*Math.pow(pos_ev.vdeuteron.p(), 3) +251.9*Math.pow(pos_ev.vdeuteron.p(),2)-212.96*pos_ev.vdeuteron.p()+68.095)))&&*/ pos_ev.dedxDeutCTOF>0 && !(pos_ev.dedxDeutCTOF<3.5&&pos_ev.vdeuteron.p()<0.5)){
            ctof_dedxvspdeut.fill(pos_ev.vdeuteron.p(),pos_ev.dedxDeutCTOF);
            ctof_dedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
            ctof_betavsPdeut.fill(pos_ev.vdeuteron.p(),pos_ev.betadeutCTOF);
            ctof_betavsPdeut.setTitle("Beta vs Momentum Deuteron");
            ctof_chi2pidDeutAfter.fill(pos_ev.chi2piddeutCTOF);
            numDeutsCTOF++;
        }
        
    }
    public void fillDeutCND(PositiveEvent pos_ev){
        
        if (pos_ev.dedxDeutCND>0 && pos_ev.chi2piddeutCND<3 && pos_ev.chi2piddeutCND>-3){
            cnd_dedxvspdeut.fill(pos_ev.vdeuteron.p(),pos_ev.dedxDeutCND);
            cnd_dedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
            cnd_betavsPdeut.fill(pos_ev.vdeuteron.p(),pos_ev.betadeutCND);
            cnd_betavsPdeut.setTitle("Beta vs Momentum Deuteron");
            cnd_chi2pidDeutBefore.fill(pos_ev.chi2piddeutCND);
            numDeutsCND++;
        }
        cnd_chi2pidDeutBefore.fill(pos_ev.chi2piddeutCND);
        // if ((pos_ev.vdeuteron.p() >= 0.4 && pos_ev.dedxDeutCND > (-39.148*Math.pow(pos_ev.vdeuteron.p(),6) +302.12 *Math.pow(pos_ev.vdeuteron.p(),5) -926.66 * Math.pow(pos_ev.vdeuteron.p(),4) + 1420.5 * Math.pow(pos_ev.vdeuteron.p(),3)-1107.9*Math.pow(pos_ev.vdeuteron.p(),2) +381*pos_ev.vdeuteron.p()-27.727)) || (pos_ev.vdeuteron.p() < 0.4 && pos_ev.dedxDeutCND > (160*pos_ev.vdeuteron.p() -48))){
        //     cnd_dedxvspdeut.fill(pos_ev.vdeuteron.p(),pos_ev.dedxDeutCND);
        //     cnd_dedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
        //     cnd_betavsPdeut.fill(pos_ev.vdeuteron.p(),pos_ev.betadeutCND);
        //     cnd_betavsPdeut.setTitle("Beta vs Momentum Deuteron");
        //     cnd_chi2pidDeutAfter.fill(pos_ev.chi2piddeutCND);
        //     numDeutsCND++;
        // }
        
    }


    public void fillProtCTOF(PositiveEvent pos_ev){
        
        if (pos_ev.dedxProtCTOF>0){
            ctof_dedxvspprot.fill(pos_ev.vproton.p(),pos_ev.dedxProtCTOF);
            ctof_dedxvspprot.setTitle("dE/dx vs Momentum Proton");
            ctof_betavsPprot.fill(pos_ev.vproton.p(),pos_ev.betaprotCTOF);
            ctof_betavsPprot.setTitle("Beta vs Momentum Proton");
        }

    }
    public void fillProtCND(PositiveEvent pos_ev){
        
        if (pos_ev.dedxProtCND>0){
            cnd_dedxvspprot.fill(pos_ev.vproton.p(),pos_ev.dedxProtCND);
            cnd_dedxvspprot.setTitle("dE/dx vs Momentum Proton");
            cnd_betavsPprot.fill(pos_ev.vproton.p(),pos_ev.betaprotCND);
            cnd_betavsPprot.setTitle("Beta vs Momentum Proton");
           
        }
    }

    public void fillPionCTOF(PositiveEvent pos_ev){
        
        if (/*pos_ev.dedxPion<4 &&*/ pos_ev.dedxPionCTOF>0){
            ctof_dedxvsppion.fill(pos_ev.vpion.p(),pos_ev.dedxPionCTOF);
            ctof_dedxvsppion.setTitle("dE/dx vs Momentum #pi+");
            ctof_betavsPpion.fill(pos_ev.vpion.p(),pos_ev.betapionCTOF);
            ctof_betavsPpion.setTitle("Beta vs Momentum #pi+");
        }
    }

     public void fillPionCND(PositiveEvent pos_ev){
        
        if (/*pos_ev.dedxPion<4 &&*/ pos_ev.dedxPionCND>0){
            cnd_dedxvsppion.fill(pos_ev.vpion.p(),pos_ev.dedxPionCND);
            cnd_dedxvsppion.setTitle("dE/dx vs Momentum #pi+");
            cnd_betavsPpion.fill(pos_ev.vpion.p(),pos_ev.betapionCND);
            cnd_betavsPpion.setTitle("Beta vs Momentum #pi+");
        }
    }

    public void fillKaonCTOF(PositiveEvent pos_ev){
        
        if (/*pos_ev.dedxKaon<4 &&*/ pos_ev.dedxKaonCTOF>0){
            ctof_dedxvspkaon.fill(pos_ev.vkaon.p(),pos_ev.dedxKaonCTOF);
            ctof_dedxvspkaon.setTitle("dE/dx vs Momentum #kappa");
            ctof_betavsPkaon.fill(pos_ev.vkaon.p(),pos_ev.betakaonCTOF);
            ctof_betavsPkaon.setTitle("Beta vs Momentum #kappa");

        }
    }
    public void fillKaonCND(PositiveEvent pos_ev){
        
        if (/*pos_ev.dedxKaon<4 &&*/ pos_ev.dedxKaonCND>0){
            cnd_dedxvspkaon.fill(pos_ev.vkaon.p(),pos_ev.dedxKaonCND);
            cnd_dedxvspkaon.setTitle("dE/dx vs Momentum #kappa");
            cnd_betavsPkaon.fill(pos_ev.vkaon.p(),pos_ev.betakaonCND);
            cnd_betavsPkaon.setTitle("Beta vs Momentum #kappa");

        }
    }

    public void drawPositivesCTOF(TCanvas ecP){
        ecP.divide(3,4);
        ecP.cd(0).draw(ctof_betavsPdeut);
        ecP.cd(1).draw(ctof_betavsPprot);
        ecP.cd(2).draw(ctof_betavsPpion);
        ecP.cd(3).draw(ctof_betavsPkaon);
        ecP.cd(4).draw(ctof_dedxvspdeut);
        ecP.cd(5).draw(ctof_dedxvspprot);
        ecP.cd(6).draw(ctof_dedxvsppion);
        ecP.cd(7).draw(ctof_dedxvspkaon);
        ecP.cd(8).draw(ctof_chi2pidDeutBefore);
        ecP.cd(9).draw(ctof_chi2pidDeutAfter);

    }
    public void drawPositivesCND(TCanvas ecP){
        ecP.divide(3,4);
        ecP.cd(0).draw(cnd_betavsPdeut);
        ecP.cd(1).draw(cnd_betavsPprot);
        ecP.cd(2).draw(cnd_betavsPpion);
        ecP.cd(3).draw(cnd_betavsPkaon);
        ecP.cd(4).draw(cnd_dedxvspdeut);
        ecP.cd(5).draw(cnd_dedxvspprot);
        ecP.cd(6).draw(cnd_dedxvsppion);
        ecP.cd(7).draw(cnd_dedxvspkaon);
        ecP.cd(8).draw(cnd_chi2pidDeutBefore);
        ecP.cd(9).draw(cnd_chi2pidDeutAfter);

    }
    public void setOutputDir(String otherdir){
    
        this.outputdir=otherdir;
        System.out.println("**** setting out dir for plots to" + this.outputdir);

    }


}