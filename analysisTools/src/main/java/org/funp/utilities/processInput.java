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

	public String DataLocation=new String("/Users/biselli/Data/clas12/rgB/pass0v16/");
	public String FileListName=new String("/Users/biselli/Data/clas12/rgB/pass0v16/fileslist.txt");
	public List<String> filenames = new ArrayList<String>();
	public processInput(String[] args) {

		this.args = args;
		//if true that option is requires an argument
		options.addOption("h", "help", false, "show help.");
		options.addOption("l", "location", true, "Set DATA files location .");
		options.addOption("f", "file", true, "Set input file with data files list .");

		this.parse();
		this.GetFileNames(this.FileListName);
		//String inputParam.DataLocation="/Users/biselli/Data/clas12/rgB/pass0v16/";
		System.out.println(this.DataLocation);
		System.out.println(this.FileListName);

	}
	private void parse() {

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("h"))
			help();
			if (cmd.hasOption("l")) {
				//System.out.println("here"+cmd.getOptionValue("l"));
				DataLocation=cmd.getOptionValue("l");
				log.log(Level.INFO, "Using processInput argument -l=" + DataLocation);
				// Whatever you want to do with the setting goes here
			} else {
				log.log(Level.INFO, "Using location data default value=" + DataLocation);
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
	public String getFileName(int i){
		//String tmp=new String();
		return this.DataLocation+this.filenames.get(i);
	}
}
