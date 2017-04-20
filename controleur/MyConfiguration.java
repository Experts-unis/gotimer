/**
 * 
 */
package controleur;

import java.io.*;
import java.util.Locale;

//import java.util.Enumeration;
//import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import model.DataBundle;

 
/**
 * @author test
 *
 */
public class MyConfiguration {

//	private static final long serialVersionUID = 1L;
	static private org.apache.logging.log4j.Logger log = LogManager . getLogger ( MyConfiguration.class ); 
	
	private Properties prop;

	//private final String  nameFile=""config.properties"";
	private final String  nameFile="config.properties";
	//private final String  nameFile="timer/config.properties";
	
	private Vector<DataBundle> tableReference;
	
	Locale[] locales = { Locale.FRANCE, Locale.ENGLISH };
	
	/**
	 * @throws Exception 
	 * 
	 */
	public MyConfiguration() throws Exception {
		log.traceEntry("Config()");

		LoadProperties();
		
		tableReference = new Vector<DataBundle>();
		
		log.traceExit("Config()");
	}

	/**
	 * @throws Exception 
	 * 
	 */
	private void LoadProperties() throws Exception {
		
		log.traceEntry("LoadProperties()");
		
		prop = new Properties();
		InputStream input = null;

		try {
			
			File monFichier = new File(nameFile);
			
			if (monFichier.exists()){
				log.trace("LoadProperties() : File configuration OK : "+nameFile);
			} else{
				log.trace("LoadProperties() : File KO "+nameFile);
				boolean b = monFichier.createNewFile();
				if (b) creatDefaultProperties();
			}
			
			input = new FileInputStream(monFichier);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			//prop.
//			Iterable list = prop.keySet();
//			Iterator it = list.iterator();
//			while (it.hasNext()){
//				System.out.println("Config.LoadProperties() : "+it.next());
//			}
//			prop.getProperty("database");
//			System.out.println(prop.getProperty("dbuser"));
//			System.out.println(prop.getProperty("dbpassword"));
			
			
			
//			Enumeration<?> e = prop.propertyNames();
//			while (e.hasMoreElements()) {
//				String key = (String) e.nextElement();
//				String value = prop.getProperty(key);
//				System.out.println("Key : " + key + ", Value : " + value);
//			}
			

		} catch (IOException ex) {
			log.fatal("LoadProperties() : Erreur 001 ");
			log.fatal("LoadProperties() : printStackTrace " , ex);
			//ex.printStackTrace();
		} finally {
			if (input != null) {
				log.trace("LoadProperties() : input != null ");
				try {
					input.close();
				} catch (IOException e) {
					log.fatal("LoadProperties() : Erreur 002");
					log.fatal("LoadProperties() : printStackTrace " , e);

				}
			}
		}
		
	}

	public void setProperty(String key,String value){
		log.traceEntry("setProperty(String key="+key+",String value="+value+")");
		
		prop.setProperty(key,value);
		
		log.traceExit("setProperty()");
	}
	
	public String getProperty(String key){
		
		String ret = prop.getProperty(key);
		log.traceExit("getProperty(String key="+key+")="+ret);
		return ret;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void save() throws Exception
	{

		log.traceEntry("save()");
		//TODO Trier la liste avant de l'enregistrer
		OutputStream output = null;
		

		try {

			output = new FileOutputStream(nameFile);
		

			// save properties to project root folder
			
			prop.store(output, "CONFIG");
			

		} catch (IOException io) {
			throw new Exception(io);
		} finally {
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		
		log.traceExit("save()");
	
	}
	/**
	 * @throws Exception 
	 * 
	 */
	private void creatDefaultProperties() throws Exception {

		log.traceEntry("creatDefaultProperties()");
		
		OutputStream output = null;

		try {

			output = new FileOutputStream(nameFile);


			//PARAMETRE BASE		
			prop.setProperty("sgbd.name","timerpro");
			prop.setProperty("sgbd.user","postgres");
			prop.setProperty("sgbd.mdp","superutilisateur");

			//PARAM LANGUE
			
			prop.setProperty("langue.preference","fr");
			
			
			//PARAM PROFIL

			prop.setProperty("profil.user","0");

			//PARAM ACTIVITE
			prop.setProperty("jour.lundi","1");
			prop.setProperty("jour.mardi","1");
			prop.setProperty("jour.mercredi","1");
			prop.setProperty("jour.jeudi","1");
			prop.setProperty("jour.vendredi","1");
			prop.setProperty("jour.samedi","1");
			prop.setProperty("jour.dimanche","0");

			//PARAM VACATION
			prop.setProperty("vacation.desactive","1");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			throw new Exception(io);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		

	log.traceExit("creatDefaultProperties()");
	
	}

	/**
	 * @param key
	 * @param selected
	 */
	public void setProperty(String key, boolean selected) {

		if (selected) 
			setProperty(key, "true");
		else
			setProperty(key, "false");
		
	}

	/**
	 * @param nameClass
	 * @return
	 */
	public ResourceBundle getTraduction(String nameClass) {
		// Charge le fichier du paramétrage de l'application
		//ResourceBundle config = ResourceBundle.getBundle("timer.properties.config"); 

		// Charge le fichier des strings en fonction du paramétrage de l'application
		String prefLangue = getProperty("langue.preference"); //"fr";
		log.trace("getTraduction : prefLangue = "+prefLangue) ;
		String nameFile="properties."+prefLangue+"."+nameClass+"_"+prefLangue+"_"+prefLangue.toUpperCase();
		ResourceBundle traduction = ResourceBundle.getBundle(nameFile);

		return traduction;
	}

	public ResourceBundle getTableRef(String nameTable)
	{
		int index = -1;
		boolean trouver=false;
		for (int i=0;i<tableReference.size();i++){
			if (tableReference.get(i).getNameTable().equals(nameTable)){
				index=i;
				i=tableReference.size();
				trouver=true;
			}
		}
		
		if (trouver){
			return tableReference.get(index).getData();
		} else {
			//Chargement de la table de référence
			ResourceBundle data = getTable(nameTable);
			
			tableReference.add(new DataBundle(nameTable,data));
			return data;
		}
		
		
	}
	
	
	/**
	 * @param nameClass
	 * @return
	 */
	private ResourceBundle getTable(String nameTable) {
		// Charge le fichier des données de la table

		log.trace("getTable : chargement de la table : "+nameTable) ;
		
		// Charge le fichier des strings en fonction du paramétrage de l'application
		String prefLangue = getProperty("langue.preference"); //"fr";
		log.trace("getTable : prefLangue = "+prefLangue) ;
		String nameFile="properties."+prefLangue+"."+nameTable.toLowerCase()+"_"+prefLangue+"_"+prefLangue.toUpperCase();
		ResourceBundle table = ResourceBundle.getBundle(nameFile);

		return table;
	}

	
}