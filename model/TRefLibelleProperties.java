/**
 * 
 */
package model;


import java.util.ResourceBundle;


import controleur.Principale;

/**
 * @author test
 *
 */
public class TRefLibelleProperties {

	private ResourceBundle data;	
	private String nomDeLaTable;
	
	/**
	 * @param string
	 */
	public TRefLibelleProperties(String nomDeLaTable) {

		this.nomDeLaTable = nomDeLaTable;
		data=null;

		
	}

	/**
	 * @return
	 */
//	protected Vector<DataRefLibelle> getList() {
//		if (liste==null){
//			loadData();
//		}
//		return liste;
//	}


 
	/**
	 * @return the nomDeLaTable
	 */
	public String getNomDeLaTable() {
		return nomDeLaTable;
	}

	/**
	 * @param nomDeLaTable the nomDeLaTable to set
	 */
	public void loadData() {
		// Charger le fichier de valeur contenu dans la table
		data = Principale.config.getTableRef(nomDeLaTable);
	}

	/**
	 * @return
	 */
	public String get(int index) {
		if (data==null) loadData();
		
		return data.getString(Integer.toString(index));
	}

	/**
	 * @return
	 */
	public ResourceBundle getData() {
		if (data==null) loadData();
		return data;
	}


	
	
	
	
}
