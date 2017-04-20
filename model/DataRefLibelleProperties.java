/**
 * 
 */
package model;



/**
 * @author test
 *
 */
public class DataRefLibelleProperties extends DataRefLibelle {


	private TRefLibelleProperties tTableRef;

	/**
	 * @param id
	 * @param libelle
	 */
	public DataRefLibelleProperties(int id,String tableName) {
		super(id, "");
		
		tTableRef = new TRefLibelleProperties(tableName);
		
		setLibelle(tTableRef.get(id));
		
	}

	/* (non-Javadoc)
	 * @see model.DataRefLibelle#setId(int)
	 */
	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		super.setId(id);
		setLibelle(tTableRef.get(id));
		
	}
	

}
