package org.funp.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.*;
import java.io.*;

public class processInput {
	private static final Logger log = Logger.getLogger(processInput.class.getName());
	private String[] args = null;
	private Options options = new Options();

	//public String DataLocation=new String(".");
	public String FileListName=new String("fileslist.txt");
	public String hipoFilename=new String("Angela.hipo");//input file name for HistoReader and for DcoDe
	public String detectorType=new String("FT");
	public String cutType=new String("DC");
	public List<String> filenames = new ArrayList<String>();
	public String OutputLocation=new String(".");
	private static boolean MCmode=false;
	private static boolean RGAmode=false;
	private static boolean nTmode=false;
	private static boolean MLmode=false;
	private static boolean pass0=false;
	private static boolean pi0mode=false;
	public processInput(String[] args) {

		this.args = args;
		//if true that option is requires an argument
		options.addOption("h", "help", false, "show help.");
		//options.addOption("l", "input dir", true, "Set DATA files location .");
		options.addOption("f", "file", true, "Set input file with data files list .");
		options.addOption("o", "outputdir", true, "Set output file dir .");
		options.addOption("hf","hipoFile",true,"Set hipo histo file name for input/output");
		options.addOption("MC", "Montecarlofiles", false, "Enable MC mode");
		options.addOption("pass0", "Pass0Files", false, "Pass0");
		options.addOption("ML", "CreateMLfiles", false, "Create ML files");
		options.addOption("nT", "noTags", false, "Enable analysis on files without tags");
		options.addOption("pi0","pion0",false,"Enable pi0 analysis");
		options.addOption("dt", "detectortype", true, "Set FT/FD in historeader");
		options.addOption("ct", "cuttype", true, "Set DC/CC/AC in historeader");
		options.addOption("rga", "rgafiles", false, "enable rga mode");
		this.parse();
		this.GetFileNames(this.FileListName);
		//String inputParam.DataLocation="/Users/biselli/Data/clas12/rgB/pass0v16/";
		//System.out.println(this.DataLocation);
		System.out.println(this.FileListName);

	}
	private void parse() {

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("h"))
			help();
			// if (cmd.hasOption("l")) {
			// 	//System.out.println("here"+cmd.getOptionValue("l"));
			// 	DataLocation=cmd.getOptionValue("l")+"/";
			// 	log.log(Level.INFO, "Using processInput argument -l=" + DataLocation+"/");
			// 	// Whatever you want to do with the setting goes here
			// } else {
			// 	log.log(Level.INFO, "Using location data default value=" + DataLocation);
			// 	//log.log(Level.SEVERE, "MIssing l option");
			// 	//help();
			// }
			if (cmd.hasOption("MC")){
				System.out.println("setting MC mode");
				MCmode=true;
			}
			if (cmd.hasOption("rga")){
				System.out.println("setting rga mode");
				RGAmode=true;
			}
			if (cmd.hasOption("nT")){
				System.out.println("setting no tags mode");
				nTmode=true;
			}
			if (cmd.hasOption("ML")){
				System.out.println("setting up for ML DataFile Creation");
				MLmode=true;
			}
			if (cmd.hasOption("pass0")){
				System.out.println("This is pass 0 run");
				pass0 = true;
			}
			if (cmd.hasOption("pi0")){
				System.out.println("pi0 analysis");
				pi0mode = true;
			}
			if (cmd.hasOption("o")) {
				//System.out.println("here"+cmd.getOptionValue("l"));
				OutputLocation=cmd.getOptionValue("o")+"/";
				log.log(Level.INFO, "Using processInput argument -o=" + OutputLocation+"/");
				// Whatever you want to do with the setting goes here
			} else {
				log.log(Level.INFO, "Using location output default value=" + OutputLocation);
				//log.log(Level.SEVERE, "MIssing l option");
				//help();
			}
			if (cmd.hasOption("f")) {
				//System.out.println("here"+cmd.getOptionValue("l"));
				FileListName=cmd.getOptionValue("f");
				log.log(Level.INFO, "Using processInput argument -f=" + FileListName);
				// Whatever you want to do with the setting goes here
			} else {
				log.log(Level.INFO, "Using default file with list of data filename=" + FileListName);
				//log.log(Level.SEVERE, "MIssing l option");
				//help();
			}
			if (cmd.hasOption("dt")) {
				//System.out.println("here"+cmd.getOptionValue("l"));
				detectorType=cmd.getOptionValue("dt");
				log.log(Level.INFO, "Using processInput argument -dt=" + detectorType);
				// Whatever you want to do with the setting goes here
			} else {
				log.log(Level.INFO, "Using default file with list of data filename=" + detectorType);
				//log.log(Level.SEVERE, "MIssing l option");
				//help();
			}
			if (cmd.hasOption("ct")) {
				//System.out.println("here"+cmd.getOptionValue("l"));
				cutType=cmd.getOptionValue("ct");
				log.log(Level.INFO, "Using processInput argument -ct=" + cutType);
				// Whatever you want to do with the setting goes here
			} else {
				log.log(Level.INFO, "Using default file with list of data filename=" + cutType);
				//log.log(Level.SEVERE, "MIssing l option");
				//help();
			}
			if (cmd.hasOption("hf")) {
				//System.out.println("here"+cmd.getOptionValue("l"));
				hipoFilename=cmd.getOptionValue("hf");
				log.log(Level.INFO, "Using processInput argument -hf=" + hipoFilename);
				// Whatever you want to do with the setting goes here
			} else {
				log.log(Level.INFO, "Using default name for hipo histo file=" + hipoFilename);
				//log.log(Level.SEVERE, "MIssing l option");
				//help();
			}
		} catch (ParseException e) {
			log.log(Level.SEVERE, "Failed to parse comand line properties", e);
			help();
		}
	}
	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);
	}



	private void GetFileNames(String FileListName){

		//String[] filenames= new String[2];
		//int user=0;//user==1 is justin
		//HipoReader[] reader = new HipoReader[2];
		//reader[0] = new HipoReader();
		//reader[1] = new HipoReader(); // Create a reader obejct
		// Create a reader obejct
		//String inputParam.DataLocation="/Users/biselli/Data/clas12/rgB/v8hipo4/";
		try
		{
			File file = new File(FileListName);
			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null){
				if(!st.startsWith("#")){
					System.out.println(st);
					filenames.add(st);
			}
			else System.out.println("Skipping "+st);
			}
			System.out.println("Found "+filenames.size()+" inputfiles");
			br.close();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println(fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		// necessary to convert back to String[]



		//filenames[0]="out_6489_2xx.hipo";
		//filenames[1]="out_6489_2xx.hipo";
		//filenames[0]="dst_edeut_006596.hipo";
		//filenames[1]="dst_inc_006596.hipo";
		//reader.open("/home/jnp/data/out_6489_2xx_3xx.hipo"); // open a file
		// if(user==1)
		// reader.open("/home/justind/DATA/out_6489_2xx_3xx.hipo"); // open a file
		// else
		// reader.open("/Users/biselli/Data/clas12/rgB/v8hipo4/out_6489_2xx.hipo");
		//reader[1].open("/Users/biselli/Data/clas12/rgB/pass0v15/out_6595_2xx-3xx.hipo"); // open a file
		// // open a file
		//return filenames;
	}
	public int getNfiles(){
		return this.filenames.size();

	}
	public static boolean  getMCmode(){
		return MCmode;
	}
	public static boolean  getRGAmode(){
		return RGAmode;
	}

	public static boolean getnTmode(){
		return nTmode;
	}
	public static boolean getMLmode(){
		return MLmode;
	}

	public static boolean getPass0(){
		return pass0;
	}
	public static boolean getPi0mode(){
		return pi0mode;
	}
	public String getFileName(int i){
		//String tmp=new String();
		//return this.DataLocation+this.filenames.get(i);
		return this.filenames.get(i);
	}
	public String getOutputDir(){
		return this.OutputLocation;
	}
	public String gethipoFile(){
		return this.hipoFilename;
	}
}
