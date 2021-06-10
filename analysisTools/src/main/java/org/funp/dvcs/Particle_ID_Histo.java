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

public class Particle_ID_Histo {

    public H2F betavsPprot;
    public H2F betavsPpion;
    public H2F betavsPkaon;
    public H2F betavsPdeut;
    public H2F ctofdedxvspdeut;
    public H2F ctofdedxvspprot;
    public H2F ctofdedxvsppion;
    public H2F ctofdedxvspkaon;
    private String outputdir=new String(".");

    public Particle_ID_Histo(){
        //emtpy constructor
        betavsPprot = new H2F("beta vs P Prot", "beta vs P Prot", 100,0,2.5,100,0,1.1);
        betavsPpion = new H2F("beta vs P Pion", "beta vs P Pion", 100,0,2.5,100,0,1.1);
        betavsPkaon = new H2F("beta vs P Kaon", "beta vs P Kaon", 100,0,2.5,100,0,1.1);
        betavsPdeut = new H2F("beta vs P Deut", "beta vs P Deut", 100,0,2.5,100,0,1.1);
        ctofdedxvspprot = new H2F("dedx vs P Prot", "dedx vs P Prot", 100,0,2,100,0,25);
        ctofdedxvsppion = new H2F("dedx vs P Pion", "dedx vs P Pion", 100,0,2,100,0,25);
        ctofdedxvspkaon = new H2F("dedx vs P Kaon", "dedx vs P Kaon", 100,0,2,100,0,25);
        ctofdedxvspdeut = new H2F("dedx vs P Deut", "dedx vs P Deut", 100,0,2,100,0,25);
    }

   

    public void fillDeut(PositiveEvent pos_ev){
        
        if (pos_ev.dedxDeut<4 && pos_ev.dedxDeut>0 ){
            ctofdedxvspdeut.fill(pos_ev.vdeuteron.p(),pos_ev.dedxDeut);
            ctofdedxvspdeut.setTitle("dE/dx vs Momentum Deuteron");
            betavsPdeut.fill(pos_ev.vdeuteron.p(),pos_ev.betadeut);
            betavsPdeut.setTitle("Beta vs Momentum Deuteron");
        }
        
    }

    public void fillProt(PositiveEvent pos_ev){
        
        if (pos_ev.dedxProt<4 && pos_ev.dedxProt>0){
            ctofdedxvspprot.fill(pos_ev.vproton.p(),pos_ev.dedxProt);
            ctofdedxvspprot.setTitle("dE/dx vs Momentum Proton");
            betavsPprot.fill(pos_ev.vproton.p(),pos_ev.betaprot);
            betavsPprot.setTitle("Beta vs Momentum Proton");
        }
    }

    public void fillPion(PositiveEvent pos_ev){
        
        if (pos_ev.dedxPion<4 && pos_ev.dedxPion>0){
            ctofdedxvsppion.fill(pos_ev.vpion.p(),pos_ev.dedxPion);
            ctofdedxvsppion.setTitle("dE/dx vs Momentum #pi+");
            betavsPpion.fill(pos_ev.vpion.p(),pos_ev.betapion);
            betavsPpion.setTitle("Beta vs Momentum #pi+");
        }
    }

    public void fillKaon(PositiveEvent pos_ev){
        
        if (pos_ev.dedxKaon<4 && pos_ev.dedxKaon>0){
            ctofdedxvspkaon.fill(pos_ev.vkaon.p(),pos_ev.dedxKaon);
            ctofdedxvspkaon.setTitle("dE/dx vs Momentum #kappa");
            betavsPkaon.fill(pos_ev.vkaon.p(),pos_ev.betakaon);
            betavsPkaon.setTitle("Beta vs Momentum #kappa");
        }
    }

    public void drawPositives(TCanvas ecP){
        ecP.divide(2,4);
        ecP.cd(0).draw(betavsPdeut);
        ecP.cd(1).draw(betavsPprot);
        ecP.cd(2).draw(betavsPpion);
        ecP.cd(3).draw(betavsPkaon);
        ecP.cd(4).draw(ctofdedxvspdeut);
        ecP.cd(5).draw(ctofdedxvspprot);
        ecP.cd(6).draw(ctofdedxvsppion);
        ecP.cd(7).draw(ctofdedxvspkaon);
    }
    public void setOutputDir(String otherdir){
    
        this.outputdir=otherdir;
        System.out.println("**** setting out dir for plots to" + this.outputdir);
        
    }


}