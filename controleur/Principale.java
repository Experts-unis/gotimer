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
		//Création du contrôleur principal 
		
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
					
					// TODO Modifier l'affichage pour avoir un message facile à lire
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
				
					// Création de la fenêtre principale
					mainView = new MainView();

					// Lance la fenêtre principal		
					mainView.showMainView();
				}
		 }
		 
		 

//	      TRACE : correspond à des messages de traces d'exécution (depuis la version 1.2.12)
//	      DEBUG : correspond à des messages de débogage
//	      INFO : correspond à des messages d'information
//	      WARN : correspond à des messages d'avertissement
//	      ERROR : correspond à des messages d'erreur
//	      FATAL : correspond à des messages liés à un arrêt imprévu de l'application
  
		   
		
//		log.trace("msg de trace");
//		log.debug("msg de debogage");
//		log.info("msg d'information");
//		log.warn("msg d'avertissement");
//		log.error("msg d'erreur");
//		log.fatal("msg d'erreur fatale");
		


		log.debug("SORT : main");
	}

}
