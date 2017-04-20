/**
 * 
 */
package model;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;

/**
 * @author test
 *
 */
public class ListModelTypo extends DefaultListModel<DataInfoTypologie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    ControlManager controlManager;
	private  ModelManager managerModel;
	
	/**
	 * @param controlManager
	 * @throws Exception 
	 */
	public ListModelTypo() throws Exception {
		
		this.controlManager=Principale.ctrlManager ;
		managerModel = controlManager.getModelManager("TypologieView");

		load();
		
		
	}
	/**
	 * 
	 */
	public void refresh(JList <DataInfoTypologie> list) {
		fireContentsChanged(list,0,size());
		
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public  void load() throws Exception {
		
		
		managerModel.loadTypologieInView(this);
		
		
	}
	/**
	 * @param listTypo 
	 * @param elementSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void modifElement(JList<DataInfoTypologie> listTypo, DataInfoTypologie elementSelection) throws ExceptionTraitementSQL {

		managerModel.updateTypologieElement(elementSelection);
		refresh(listTypo);
		
		
	}

	public void addTypo(DataInfoTypologie element) throws ExceptionTraitementSQL {

		managerModel.addTypologie(element);
		
		addElement(element);
		
		
		
	}
	/**
	 * @param listTypo
	 * @param elementSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void supprimer(JList<DataInfoTypologie> listTypo, DataInfoTypologie elementSelection,int index) throws ExceptionTraitementSQL {
		managerModel.deleteTypologieElement(elementSelection);
		remove(index);
		refresh(listTypo);
		
	}
}
