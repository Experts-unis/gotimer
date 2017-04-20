/**
 * 
 */
package vue;

import java.util.ResourceBundle;

import javax.swing.JDialog;

import controleur.ExceptionTraitementSQL;
import controleur.Principale;

/**
 * @author test
 *
 */
public class ReportView extends ProjectListView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("reportview");

	/**
	 * @param parent
	 * @param title
	 */
	public ReportView(JDialog parent) {
		super(parent, TRADUCTION.getString("reportview.title.text"));
		
	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#loadProjet()
	 */
	@Override
	public void loadProjet() 
	{
		try {
			listModelProjet.removeAllElements();
			managerModel.loadProjetsReportInView(listModelProjet);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError("","err01.projets","Impossible charger les informations des projets", e);
		}

	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#loadFonction()
	 */
	@Override
	protected void loadFonction() 	
	{
		try {

			managerModel.loadFonctionsReportInView(listModelFonction,infoProjetSelection);
		} catch (ExceptionTraitementSQL e1) {

			MainView.showMsgError("","err04.projets","Impossible de charger les fonctions de ce projet ", e1);
		}

	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#getTitreMenu()
	 */
	@Override
	protected String getTitreMenu() {
		return TRADUCTION.getString("reportview.menu.title") ;
	}



	/* (non-Javadoc)
	 * @see vue.ProjectListView#actionProjet()
	 */
	@Override
	protected void actionProjet() throws ExceptionTraitementSQL {
		managerModel.stopReportProjet(infoProjetSelection);
		
	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#actionFonction()
	 */
	@Override
	protected void actionFonction() throws ExceptionTraitementSQL {
		managerModel.stopReportFonction(infoFonctionSelection);
		
	}

}
