/**
 * 
 */
package controleur;



import org.apache.logging.log4j.LogManager;





import javax.swing.JOptionPane;

import controleur.ControlManager;
import vue.MainView;
import vue.ProjectView;


/**
 * @author test
 *
 */
public class Principale {

	 static public ControlManager ctrlManager;
	 static public MainView mainView;

	 static org.apache.logging.log4j.Logger log = LogManager . getLogger ( Principale.class );  
	   
	 public static final int  nombreDHeureTravailleeDanslaJournee=5;  
	 public static MyConfiguration config;

	 private static boolean configOK;  
	 public static final String formatDeLaDate="dd/MM/YYYY";

	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		//Cr�ation du contr�leur principal 
		
		log.debug("ENTRE : main ");

		configOK=false;
		 try {
			config = new MyConfiguration();
			configOK=true;
			
			
		} catch (Exception e1) {
			
			
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			
			
		}
		
		 if (configOK){
			 
				try {
					ctrlManager = new ControlManager();
				} catch ( ExceptionInitBase | ExceptionTraitementSQL e) {
					
					// TODO Modifier l'affichage pour avoir un message facile � lire
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					
				}
				
				
				int idProjetSelection=-1;
				try {
					idProjetSelection = ctrlManager.getModelManager().isSelectProjet();
				} catch (ExceptionTraitementSQL e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (idProjetSelection==-1)
				{
					ProjectView fDialogProjet=new ProjectView(null);
					fDialogProjet.showDialog();
					
				}
				try {
					idProjetSelection = ctrlManager.getModelManager().isSelectProjet();
				} catch (ExceptionTraitementSQL e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (idProjetSelection==-1){
					System.exit(0);
				}
				
				else{
				
//					TimeView fDialogNew = new TimeView(null,this); 
//					fDialogNew.showDialog();
				
					// Cr�ation de la fen�tre principale
					mainView = new MainView();

					// Lance la fen�tre principal		
					mainView.showMainView();
				}
		 }
		 
		 

//	      TRACE : correspond � des messages de traces d'ex�cution (depuis la version 1.2.12)
//	      DEBUG : correspond � des messages de d�bogage
//	      INFO : correspond � des messages d'information
//	      WARN : correspond � des messages d'avertissement
//	      ERROR : correspond � des messages d'erreur
//	      FATAL : correspond � des messages li�s � un arr�t impr�vu de l'application
  
		   
		
//		log.trace("msg de trace");
//		log.debug("msg de debogage");
//		log.info("msg d'information");
//		log.warn("msg d'avertissement");
//		log.error("msg d'erreur");
//		log.fatal("msg d'erreur fatale");
		


		log.debug("SORT : main");
	}

}
