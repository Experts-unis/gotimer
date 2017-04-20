/**
 * 
 */
package model;


import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;

/**
 * @author test
 *
 */
public abstract class TableauModel<P> extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] entete;
	boolean[] columnEditables;
	Vector <P> data;
	
	ControlManager controlManager;
	Class<?>[] columnTypes;
	private String nameClass;
	protected ResourceBundle traduction;
	

	
	/**
	 * @param controlManager
	 */
	public TableauModel() {
		super();
		// Chargement des lignes
		this.controlManager = Principale.ctrlManager;
		nameClass="no defined";
		
		data = new Vector <P> ();
		
	}

	public String getName()
	{
		return nameClass;
	}
	/**
	 * @param string
	 */
	public void setName(String nameClass) {
		
		this.nameClass=nameClass;
	}
	public void refresh()
	{
		 this.fireTableDataChanged();
	}
	
	public  void addRow(P value ) throws ExceptionTraitementSQL, Exception
	{
		data.addElement(value);		
		this.fireTableDataChanged();
	}
	
	public void removeRow(int position) throws ExceptionTraitementSQL, Exception
	{
		data.removeElementAt(position);
		this.fireTableDataChanged();
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return entete[column];
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return data.size();
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return entete.length; 
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	
		return columnTypes[columnIndex];
	
	}	


	/**
	 * @return the data
	 */
	public Object getDataItem(int index) {
		return data.get(index);
	}

	/**
	 * @return the data
	 */
	public Vector<P> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Vector<P> data) {
		this.data = data;
	}

}
