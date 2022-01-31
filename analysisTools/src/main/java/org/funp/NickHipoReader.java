package org.funp;
//package org.funp.dvcs;
import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
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

public class NickHipoReader{
    static TDirectory dir2;
    static TCanvas ec4;
    static TCanvas ecA;
    public static void main( String[] args ) throws FileNotFoundException, IOException 
  {
processInput inputParam=new processInput(args);
    dir2 = new TDirectory();
    //dir2.readFile("NickRichardson.hipo");
    dir2.readFile("Bplots.hipo");

    boolean showNOCUT_kinematics_ALL = false;
    boolean showNOCUT_kinematics_FT = false;
    boolean showNOCUT_kinematics_FD = false;

    boolean showNOCUT_missing_quants_ALL=false;
    boolean showNOCUT_missing_quants_FT=false;
    boolean showNOCUT_missing_quants_FD=false;

    boolean showDVCS_kinematics_All = false;
    boolean showDVCS_kinematics_FT = false;
    boolean showDVCS_kinematics_FD = false;

    boolean showDVCS_missing_quants_ALL = false;
    boolean showDVCS_missing_quants_FT = true;
    boolean showDVCS_missing_quants_FD = false;

    boolean showExcl_kinematicss_ALL = false;
    boolean showExcl_kinematics_FT = true;
    boolean showExcl_kinematics_FD = true;

    boolean showExcl_missing_quants_ALL = false;
    boolean showExcl_missing_quants_FT = true;
    boolean showExcl_missing_quants_FD = true;

    boolean showParticleComparison_NO_CUTS = false;
    boolean showParticleComparison_DVCS_CUTS = false;
    boolean showParticleComparison_Excl_CUTS = false;


    boolean showConeAngle_NO_CUTS_All = true;
    boolean showConeAngle_DVCS_CUTS_All = true;
    boolean showConeAngle_Excl_CUTS_All = true;

    boolean showConeAngle_NO_CUTS_FT = true;
    boolean showConeAngle_DVCS_CUTS_FT = true;
    boolean showConeAngle_Excl_CUTS_FT = true;

    boolean showConeAngle_NO_CUTS_FD = true;
    boolean showConeAngle_DVCS_CUTS_FD = true;
    boolean showConeAngle_Excl_CUTS_FD = true;


    boolean showAsymm_All = true;
    boolean showAsymm_FT = true;
    boolean showAsymm_FD = true;

    TCanvas BinnedQ2_1 = new TCanvas("Asymmetry Q2_1",1200,1200);
    draw_asymmetry(BinnedQ2_1, "Asymmetry Q2_1");

    TCanvas BinnedQ2_2 = new TCanvas("Asymmetry Q2_2",1200,1200);
    draw_asymmetry(BinnedQ2_2, "Asymmetry Q2_2");

    TCanvas BinnedQ2_3 = new TCanvas("Asymmetry Q2_3",1200,1200);
    draw_asymmetry(BinnedQ2_3, "Asymmetry Q2_3");

    TCanvas BinnedXb_1 = new TCanvas("Asymmetry Xb_1",1200,1200);
    draw_asymmetry(BinnedXb_1, "Asymmetry Xb_1");

    TCanvas BinnedXb_2 = new TCanvas("Asymmetry Xb_2",1200,1200);
    draw_asymmetry(BinnedXb_2, "Asymmetry Xb_2");

    TCanvas BinnedXb_3 = new TCanvas("Asymmetry Xb_3",1200,1200);
    draw_asymmetry(BinnedXb_3, "Asymmetry Xb_3");

    TCanvas Binnedt_1 = new TCanvas("Asymmetry t_1",1200,1200);
    draw_asymmetry(Binnedt_1, "Asymmetry t_1");

    TCanvas Binnedt_2 = new TCanvas("Asymmetry t_2",1200,1200);
    draw_asymmetry(Binnedt_2, "Asymmetry t_2");

    TCanvas Binnedt_3 = new TCanvas("Asymmetry t_3",1200,1200);
    draw_asymmetry(Binnedt_3, "Asymmetry t_3");


    if (showParticleComparison_NO_CUTS){
     TCanvas ecNC = new TCanvas("Particle Comparsion No Cuts",1500,1500);
     Draw_Particle_Comparison(ecNC, "Particle Comparsion No Cuts/"); 
      
    }
    if (showParticleComparison_DVCS_CUTS){
      TCanvas ecDC = new TCanvas("Particle Comparsion DVCS Cuts",1500,1500);
      Draw_Particle_Comparison(ecDC , "Particle Comparsion DVCS Cuts/");
    }
    if (showParticleComparison_Excl_CUTS){
      TCanvas ecAC = new TCanvas("Particle Comparsion Excl Cuts",1500,1500);
      Draw_Particle_Comparison(ecAC,"Particle Comparsion Excl Cuts/" );
    }

    if (showNOCUT_missing_quants_ALL){
      TCanvas ec114 = new TCanvas("Excl after No cuts",1500,1500);
      Draw_Missing_Quants(ec114,"Excl after No cuts");
    }

    if (showDVCS_missing_quants_ALL){
        TCanvas ec4123 = new TCanvas("Excl after DVCS cuts",1500,1500);
        Draw_Missing_Quants(ec4123,"Excl after DVCS cuts");
    }
    
    if (showDVCS_missing_quants_FT){
        TCanvas ec40 = new TCanvas("Excl after DVCS cuts FT",1500,1500);
        Draw_Missing_Quants(ec40,"Excl after DVCS cuts FT");
    }
    if (showDVCS_missing_quants_FD){
        TCanvas ec401 = new TCanvas("Excl after DVCS cuts FD",1500,1500);
        Draw_Missing_Quants(ec401,"Excl after DVCS cuts FD");
    }

    if (showExcl_missing_quants_ALL){
        TCanvas ec5 = new TCanvas("Excl after DVCS and exc cuts",1500,1500);
        Draw_Missing_Quants(ec5,"Excl after DVCS and exc cuts");
    }

    
     if (showExcl_missing_quants_FD){
      TCanvas ec5551 = new TCanvas("Excl after DVCS and exc cuts FD",1500,1500);
      Draw_Missing_Quants( ec5551, "Excl after DVCS and exc cuts FD");
    }
    if (showExcl_missing_quants_FT){
      TCanvas ec555 = new TCanvas("Excl after DVCS and exc cuts FT",1500,1500);
      Draw_Missing_Quants( ec555,"Excl after DVCS and exc cuts FT");
    }
    
    if (showNOCUT_kinematics_ALL){
      TCanvas ec6 = new TCanvas("No Cuts All Kinematics",1200,1000);
      Draw_Kinematics(ec6, "Kinmeatics NO CUT All");
    }

    if (showNOCUT_kinematics_FT){
     TCanvas ec66 = new TCanvas("No Cuts FT Kinematics",1200,1000);
      Draw_Kinematics(ec66,"Kinematics No Cut FT");
    }
    
    if (showNOCUT_kinematics_FD){
        TCanvas ec666 = new TCanvas("No Cuts FD Kinematics",1200,1000);
        Draw_Kinematics(ec666,  "Kinematics No cut FD");
    }
    
    if (showDVCS_kinematics_All){
      TCanvas ec7 = new TCanvas("DVCS Cuts All Kinematics",1200,1000);
      Draw_Kinematics( ec7,"Kinematics DVCS Cut All");
    }

    if (showDVCS_kinematics_FT){
      TCanvas ec77 = new TCanvas("DVCS Cuts FT Kinematics",1200,1000);
      Draw_Kinematics( ec77, "Kinematics DVCS Cut FT");
    }

    if (showDVCS_kinematics_FD){
     TCanvas ec777 = new TCanvas("DVCS Cuts FD Kinematics",1200,1000);
      Draw_Kinematics(ec777, "Kinematics DVCS Cut FD");
    }

   if (showExcl_kinematicss_ALL){
     TCanvas ec8 = new TCanvas("DVCS and Excl Cuts All Kinematics",1200,1000);
      Draw_Kinematics(ec8, "Kinenamtics Excl Cuts All");
   }

   if (showExcl_kinematics_FT){
    TCanvas ec88 = new TCanvas("DVCS and Excl Cuts FT Kinematics",1200,1000);
    Draw_Kinematics( ec88, "Kinematics Excl Cuts FT");//changed this line
   }

   if (showExcl_kinematics_FD){
    TCanvas ec89 = new TCanvas("DVCS and Excl Cuts FD Kinematics",1200,1000);
    Draw_Kinematics( ec89, "Kinematics Excl Cuts FD");//changed this line
   }
    
  //   if (showConeAngle_NO_CUTS_All){
  //     //TCanvas ec9 = new TCanvas("AllNoCuts ConeAngle All",1200,1000);
  //     hNC.DrawConeAngle(dir,"AllNoCuts ConeAngle All");
  //   }

  //   if (showConeAngle_DVCS_CUTS_All){
  //    // TCanvas ec10 = new TCanvas("AllDVCSCuts ConeAngle All",1200,1000);
  //     hDC.DrawConeAngle(dir, "AllDVCSCuts ConeAngle All");
  //   }

  //   if (showConeAngle_Excl_CUTS_All){
  //     //TCanvas ec11 = new TCanvas("AllDVCSexcCuts ConeAngle All",1200,1000);
  //     hAC.DrawConeAngle(dir,"AllDVCSexcCuts ConeAngle All" );
  //   }

  //   if (showConeAngle_NO_CUTS_FT){
  //     //TCanvas ec91 = new TCanvas("AllNoCuts ConeAngle FT",1200,1000);
  //     hNCFT.DrawConeAngle( dir ,"AllNoCuts ConeAngle FT" );
  //   }

  //   if (showConeAngle_DVCS_CUTS_FT){
  //    // TCanvas ec101 = new TCanvas("AllDVCSCuts ConeAngle FT",1200,1000);
  //     hDCFT.DrawConeAngle( dir,"AllDVCSCuts ConeAngle FT" );
  //   }

  //   if (showConeAngle_Excl_CUTS_FT){
  //    // TCanvas ec111 = new TCanvas("AllDVCSexcCuts ConeAngle FT",1200,1000);
  //     hACFT.DrawConeAngle( dir ,"AllDVCSexcCuts ConeAngle FT" );
  //   }

  //   if (showConeAngle_NO_CUTS_FD){
  //     //TCanvas ec911 = new TCanvas("AllNoCuts ConeAngle FD",1200,1000);
  //     hNCFD.DrawConeAngle( dir ,"AllNoCuts ConeAngle FD" );
  //   }

  //   if (showConeAngle_DVCS_CUTS_FD){
  //     //TCanvas ec1011 = new TCanvas("AllDVCSCuts ConeAngle FD",1200,1000);
  //     hDCFD.DrawConeAngle(dir , "AllDVCSCuts ConeAngle FD");
  //   }

  //   if (showConeAngle_Excl_CUTS_FD){
  //     //TCanvas ec1111 = new TCanvas("AllDVCSexcCuts ConeAngle FD",1200,1000);
  //     hACFD.DrawConeAngle( dir, "AllDVCSexcCuts ConeAngle FD");
  //   }

  if (showAsymm_All){
    TCanvas ecA = new TCanvas("Asymmetry",1200,1200);
    draw_asymmetry(ecA, "Asymmetry");
  }

  if (showAsymm_FT){
     TCanvas ecAV = new TCanvas("Asymmetry FT",1200,1200);
    draw_asymmetry(ecAV, "Asymmetry FT");
  }
    
    
  if (showAsymm_FD){
    TCanvas ecAVV = new TCanvas("Asymmetry FD",1200,1200);
    draw_asymmetry(ecAVV, "Asymmetry FD");
  } 
    
   
    
   
    //processInput inputParam=new processInput(args);

  }

  public static void Draw_ConeAngle(TCanvas ec4, String dir){

  }

  public static void Draw_Kinematics(TCanvas ec4, String dir){

    H2F WvsQ2 = (H2F) dir2.getObject(dir + "/", "Q2 vs W");
    H2F Q2vsXbj = (H2F) dir2.getObject(dir + "/", "Q^2 vs X_b");
    H1F X_b = (H1F) dir2.getObject(dir + "/", "X_b");
    H2F betacalcvsP = (H2F) dir2.getObject(dir + "/", "BetaCalc vs P");
    H2F chi2vsdeltabeta = (H2F) dir2.getObject(dir + "/", "#Delta#beta_d vs #chi^2_PID");
    H1F W = (H1F) dir2.getObject(dir + "/", "W");
    H1F hgTh = (H1F) dir2.getObject(dir + "/", "hgTh");
    H1F hgEn = (H1F) dir2.getObject(dir + "/", "Photon energy");
    H1F Q2 = (H1F) dir2.getObject(dir + "/", "Q2");
    H1F ConeAngleHist = (H1F) dir2.getObject(dir + "/", "ConeAngleHist");
    H1F ConeAngleBtElectronPhotonFD = (H1F) dir2.getObject(dir + "/", "Cone Angle Bt Electron and Photon");
    H1F PhiPlaneHist = (H1F) dir2.getObject(dir + "/", "PhiPlaneHist");
    H1F DPhiHist = (H1F) dir2.getObject(dir + "/", "DPhiHist");
    
    H2F tvsq2 = (H2F) dir2.getObject(dir + "/", "Q2 vs t");
    H2F phivshelicityMinus = (H2F) dir2.getObject(dir + "/", "phi vs helicity");
    H1F chisqHad = (H1F) dir2.getObject(dir + "/", "Chi2Pid");
    H1F thisto = (H1F) dir2.getObject(dir + "/", "-t");
    H1F VertexElectron = (H1F) dir2.getObject(dir + "/", "Vertex Electron");
    H1F VertexDuetron = (H1F) dir2.getObject(dir + "/", "Vertex Dueteron");
    H2F XvsY_electron = (H2F) dir2.getObject(dir + "/", "X vs Y");
    H2F dedxCNDvsP = (H2F) dir2.getObject(dir + "/", "dedx CTOF vs P");
    H2F dedxCTOFvsP = (H2F) dir2.getObject(dir + "/", "dedx CND vs P");
    H1F betacalchisto = (H1F) dir2.getObject(dir + "/", "#beta_calc");
    H1F betahadhisto = (H1F) dir2.getObject(dir + "/", "#beta");
    ec4.divide(6,4);
    ec4.cd(0).draw(WvsQ2);
    ec4.cd(1).draw(Q2vsXbj);
    ec4.cd(2).draw(betacalcvsP);
    ec4.cd(3).draw(chi2vsdeltabeta);
    ec4.cd(4).draw(W);
    ec4.cd(5).draw(hgTh);
    ec4.cd(6).draw(hgEn);
    ec4.cd(7).draw(Q2);
    ec4.cd(8).draw(ConeAngleHist);
    ec4.cd(9).draw(ConeAngleBtElectronPhotonFD);
    ec4.cd(10).draw(PhiPlaneHist);
    ec4.cd(11).draw(DPhiHist);
    ec4.cd(12).draw(tvsq2);
    ec4.cd(13).draw(phivshelicityMinus);
    ec4.cd(14).draw(chisqHad);
    ec4.cd(15).draw(thisto);
    ec4.cd(16).draw(VertexElectron);
    ec4.cd(17).draw(VertexDuetron);
    ec4.cd(18).draw(XvsY_electron);
    ec4.cd(19).draw(dedxCNDvsP);
    ec4.cd(20).draw(dedxCTOFvsP);
    ec4.cd(21).draw(betacalchisto);
    ec4.cd(22).draw(betahadhisto);
    ec4.cd(23).draw(X_b);
   
   
    
   
    
  }

  public static void Draw_Missing_Quants(TCanvas ec4, String dir){
    H1F edXmissingE = (H1F) dir2.getObject(dir + "/", "edXmissingE");
    H1F edgXmissingM2 = (H1F) dir2.getObject(dir + "/", "edgXmissingM2");
    H1F egXmissingM = (H1F) dir2.getObject(dir + "/", "egXmissingM");
    H1F DeltaPhiPlaneHist = (H1F) dir2.getObject(dir + "/", "DeltaPhiPlane");
    H1F DeltaPhiPlaneMattHist = (H1F) dir2.getObject(dir + "/", "DeltaPhiPlane2");
    H1F edgXmissingPx = (H1F) dir2.getObject(dir + "/", "MMomx");
    H1F edgXmissingPy = (H1F) dir2.getObject(dir + "/", "MMomy");
    H1F edgXmissingPz = (H1F) dir2.getObject(dir + "/", "MMomz");
    H1F MissThetaHist = (H1F) dir2.getObject(dir + "/", "MissThetaHist");
    H1F egXmissingM2 = (H1F) dir2.getObject(dir + "/", "egXmissingM2");

    ec4.divide(4,3);
    ec4.cd(0).draw(edXmissingE);
    ec4.cd(1).draw(edgXmissingM2);
    ec4.cd(2).draw(egXmissingM);
    ec4.cd(3).draw(DeltaPhiPlaneHist);
    ec4.cd(4).draw(DeltaPhiPlaneMattHist);
    ec4.cd(5).draw(edgXmissingPx);
    ec4.cd(6).draw(edgXmissingPy);
    ec4.cd(7).draw(edgXmissingPz);
    ec4.cd(8).draw(MissThetaHist);
    ec4.cd(9).draw(egXmissingM2);



   
  }

  public static void Draw_Particle_Comparison(TCanvas ec4, String dir){
    H2F elecThvsPhi = (H2F) dir2.getObject(dir+ "/", "Electron #theta vs #phi");
    H2F elecThvsP = (H2F) dir2.getObject(dir+ "/", "Electron p vs #theta");
    H2F photThvsPhi = (H2F) dir2.getObject(dir+ "/", "Photon #theta vs #phi");
    H2F photThvsP = (H2F) dir2.getObject(dir+ "/", "Photon p vs #theta");
    H2F ThvsPhi = (H2F) dir2.getObject(dir+ "/", "Deuteron #theta vs #phi");
    H2F ThvsP = (H2F) dir2.getObject(dir+ "/", "Deuteron p vs #theta");
    H2F dedxDeutvsP = (H2F) dir2.getObject(dir, "dedxDeutvsP");
    ec4.divide(4,2);
    ec4.cd(0).draw(elecThvsPhi);
    ec4.cd(1).draw(elecThvsP);
    ec4.cd(2).draw(photThvsPhi);
    ec4.cd(3).draw(photThvsP);
    ec4.cd(4).draw(ThvsPhi);
    ec4.cd(5).draw(ThvsP);
    ec4.cd(6).draw(dedxDeutvsP);


  }

  
    public static H1F buildAsym(TCanvas ecA, String dir){
        H1F Phiplus = (H1F) dir2.getObject(dir + "/", "Phiplus");
        H1F Phiminus = (H1F) dir2.getObject(dir + "/", "Phiminus");
        H1F num;
        H1F denom;
        H1F Asym;
        num = new H1F("num",10,0,360);
        denom = new H1F("denom",10,0,360);
        Asym = new H1F("Asymmetry","Asymmetry",10,0,360);
        num.add(Phiplus);
        num.sub(Phiminus);
        denom.add(Phiplus);
        denom.add(Phiminus);
        
        Asym = Asym.divide(num,denom);
        Asym.divide(0.8);
        Asym.setTitleX("#phi [deg.]");
        Asym.setTitleY("A_LU(#phi)");
        
        // H1F Asym = (H1F) dir2.getObject(dir + "/", directory);
        return Asym;
    }

    public static void draw_asymmetry(TCanvas ecA, String dir){
        ecA.getPad().setAxisRange(0, 360, -0.8, 0.8);
        ecA.draw((buildAsym(ecA,dir)),"E");

        F1D Asymfunc = new F1D("Asymfunc","[A]*sin(x * 2 * 3.14 /360)  ",0,360);
        // Asymfunc.setParameter(0,0.1);
        // Asymfunc.setParameter(1,0.01);
        // Asymfunc.setParameter(2,-0.1);
        Asymfunc.setParameter(0,0.1);
        //Asymfunc.setParameter(1,0.01);
        // Asymfunc.setParameter(2,-0.01);
        DataFitter.fit(Asymfunc,buildAsym(ecA, dir),"");
        ecA.draw(Asymfunc,"same");
    }
}