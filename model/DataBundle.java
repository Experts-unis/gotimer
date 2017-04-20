/**
 * 
 */
package model;

import java.util.ResourceBundle;

/**
 * @author test
 *
 */
public class DataBundle {

	String nameTable;
	ResourceBundle data;
	/**
	 * @param data 
	 * 
	 */
	public DataBundle(String nameTable, ResourceBundle data) {
		this.nameTable=nameTable;
		this.data=data;
	}
	/**
	 * @return the nameTable
	 */
	public String getNameTable() {
		return nameTable;
	}
	/**
	 * @param nameTable the nameTable to set
	 */
	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}
	/**
	 * @return the data
	 */
	public ResourceBundle getData() {
		return data;
	}


}
