/**
 * 
 */
package vue;

import javax.swing.JDialog;

import controleur.ExceptionTraitementSQL;
import controleur.Principale;


import java.util.ResourceBundle;

/**
 * @author test
 *
 */
public class ArchiveView extends ProjectListView {
	
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("archiveview"); //$NON-NLS-1$

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param parent
	 * @param title
	 */
	public ArchiveView(JDialog parent) {
		super(parent, TRADUCTION.getString("ArchiveView.title.text"));
		
	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#loadProjet()
	 */
	@Override
	public void loadProjet() 
	{
		try {
			listModelProjet.removeAllElements();
			managerModel.loadProjetsArchiveInView(listModelProjet);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError("ARCHIVEVIEW","err01.projets","Impossible charger les informations des projets", e);
		}

	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#loadFonction()
	 */
	@Override
	protected void loadFonction() 	
	{
		try {

			managerModel.loadFonctionsArchiveInView(listModelFonction,infoProjetSelection);
		} catch (ExceptionTraitementSQL e1) {

			MainView.showMsgError("ARCHIVEVIEW","err04.projets","Impossible de charger les fonctions de ce projet ", e1);
		}

	}
	
	protected String getTitreMenu() {
		return TRADUCTION.getString("ArchiveView.menu.title") ;
	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#actionFonction()
	 */
	@Override
	protected void actionFonction() throws ExceptionTraitementSQL {
		
		managerModel.stopArchiveFonction(infoFonctionSelection);	
		
	}

	/* (non-Javadoc)
	 * @see vue.ProjectListView#actionProjet()
	 */
	@Override
	protected void actionProjet() throws ExceptionTraitementSQL {
		
		managerModel.stopArchiveProjet(infoProjetSelection);
		
		
	}
	
	

}
