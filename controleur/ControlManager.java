/**
 * 
 */
package controleur;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.util.Date;





import org.apache.logging.log4j.LogManager;


import model.DataInfoTimer;

import model.ComplexiteModelManager;
import model.EstimationModelManager;
import model.MainModelManager;
import model.ModelManager;
import model.ParamModelManager;
import model.ProjetModelManager;


//import vue.SuiviView;


/**
 * @author test
 *
 */
public class ControlManager {

	private ModelManager model;
	
	
//	static public ResourceBundle config;
//	static public ResourceBundle traduction ;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( ControlManager.class );  
	
	
	

	/**
	 * @throws ExceptionTraitementSQL 
	 * @throws Exception 
	 * @throws Exception 
	 * 
	 */
	public ControlManager() throws ExceptionInitBase, ExceptionTraitementSQL {
		log.traceEntry("ControlManager()") ;
		

		// Création du model principal
		model = new ModelManager();
		
		
	
		//Principale.logger.exiting("ControlManager", "ControlManager");
		log.traceExit("ControlManager");
		
	}

	public double calculEcart(long debut, long fin) {

		long ecart= (fin - debut) ;
		
		log.trace("calculEcart : ecart="+Long.toString(ecart));
		double resultat = ecart/ 1000d;
		
		resultat = resultat / 3600d; // exprimer en heure
		
		log.trace("calculEcart : resultat="+resultat);
		
	
		return resultat;
		
	}

	/**
	 * @param dataInfoTime 
	 * 
	 */
	public void calculEcart(DataInfoTimer dataInfoTime) {
		
		double resultat;
		log.traceEntry("calculEcart(DataInfoTimer dataInfoTime)") ;
		
		Date stopTime = dataInfoTime.getStop();
		Date startTime = dataInfoTime.getStart();
		
		resultat = calculEcart(startTime.getTime(), stopTime.getTime());
		
		
		//TODO INTEGRER LE CALCUL HORS ET DANS PLAGE
		//dataInfoTime.setDelai(resultat);
		dataInfoTime.setCharge(resultat);
		
		log.traceExit("calculEcart(DataInfoTimer dataInfoTime)") ;
		
	}
	

	public ControlManager getControlManager(String name){
		
		return this;
	}
	
	/**
	 * Getter pour le model Manager
	 * @return model;
	 */
	public ModelManager getModelManager(){
		
		return model;
	}

	public ModelManager getModelManager(String name) throws Exception{
		log.traceEntry("getModelManager(String name="+name+")") ;
		
		if (name=="ProjectView" || name=="ArchiveView" || name=="ReportView" ) return getModelManagerProjet();		
		if (name=="DialogComplexiteView") return getModelManagerComplexite();
		if (name=="MainView") return getModelManagerMain();
		if (name=="EstimationView") return getModelManagerEstimation();
		if (name=="TableauModelPresence") return getModelManagerParam();
		if (name=="TableauModelVacation") return getModelManagerParam();
		
		return model;
	}
	/**
	 * @return
	 * @throws Exception 
	 */
	private ModelManager getModelManagerParam() throws Exception {
		try {
			return new ParamModelManager();
		} catch (Exception e) {
			 throw new Exception(e); 
		}
	}


	/**
	 * @return
	 * @throws Exception 
	 */
	private ModelManager getModelManagerEstimation() throws Exception {
		 
		try {
			return new EstimationModelManager();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}




	/**
	 * @return
	 * @throws Exception 
	 */
	private ModelManager getModelManagerMain() throws Exception {
		try {
			return new MainModelManager();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}




	/**
	 * @return
	 * @throws Exception 
	 */
	private ModelManager getModelManagerComplexite() throws Exception {
		try {
			return new ComplexiteModelManager();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	private ModelManager getModelManagerProjet() throws Exception {
		try {
			return new ProjetModelManager();
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}




	/**
	 * Permet d'enregistrer temporairement un timer
	 * @param instance
	 */
	public void serialize(DataInfoTimer instance){
		log.traceEntry("serialize(DataInfoTime instance)") ;
		
		XMLEncoder encoder = null;

		try {
			encoder =  new XMLEncoder(new BufferedOutputStream(new FileOutputStream("timer.xml")));
			encoder.writeObject(instance);		
			encoder.flush();

		} catch (final java.io.IOException e) {

			e.printStackTrace();

		} finally {

			if (encoder != null) {

				encoder.close();

			}


		}
			
		log.traceExit("serialize(DataInfoTime instance)") ;
	}
	
	/**
	 * Permet de relire l'objet timer sérialisé
	 * @return 
	 */
	public DataInfoTimer deSerialize()
	{
		log.traceEntry("DataInfoTime deSerialize()") ;
		
		DataInfoTimer instance;
		XMLDecoder decoder = null;
		
		try {
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("timer.xml")));
			instance = (DataInfoTimer) decoder.readObject();		

		} catch (final java.io.IOException e) {

			e.printStackTrace();
			instance = null;

		} finally {

			if (decoder != null) {

				decoder.close();

			}


		}
		
		log.traceExit("DataInfoTime deSerialize()") ;
		return instance;
		
		
	}

	/**
	 * @param text
	 * @return
	 */
	public double calculEstimationHeure(String valnumJour) {
		double val = (double) (Double.valueOf(valnumJour) * Principale.nombreDHeureTravailleeDanslaJournee);
		return val;
	}




	
}
