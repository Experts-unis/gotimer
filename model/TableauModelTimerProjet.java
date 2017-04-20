/**
 * 
 */
package model;





import java.util.ResourceBundle;

import controleur.ControlManager;
//import controleur.ExceptionTraitementSQL;
import controleur.Principale;

/**
 * @author test
 *
 */
public class TableauModelTimerProjet extends TableauModel<DataInfoTimer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("timerprojectview");
	private int unite;
	
	
	

	/**
	 * @throws Exception 
	 * 
	 */
	public TableauModelTimerProjet(ControlManager controlManager) throws Exception {
		super();
		unite=1;
		entete = new String[] {
				TRADUCTION.getString("TableauModelTimerProjet.col0.id"),
				TRADUCTION.getString("TableauModelTimerProjet.col1.projet"), 
				TRADUCTION.getString("TableauModelTimerProjet.col2.fonction"), 
				TRADUCTION.getString("TableauModelTimerProjet.col3.datestart"), 
				TRADUCTION.getString("TableauModelTimerProjet.col4.start"),
				TRADUCTION.getString("TableauModelTimerProjet.col5.datestop"), 
				TRADUCTION.getString("TableauModelTimerProjet.col6.stop"),
				TRADUCTION.getString("TableauModelTimerProjet.col7.charge"),
				TRADUCTION.getString("TableauModelTimerProjet.col8.horsplage"),
				TRADUCTION.getString("TableauModelTimerProjet.col9.dansplage"),
				TRADUCTION.getString("TableauModelTimerProjet.col10.pro")
		};
		columnTypes = new Class[] {
				String.class,
				String.class,
				String.class, 
				String.class, 
				String.class, 
				String.class,
				String.class,
				String.class,
				String.class,
				String.class,
				Boolean.class
			};
		// Chargement des lignes
		this.controlManager = controlManager;
		reload(-1);
			
	}

	/**
	 * @param idProjet 
	 * @param controlManager
	 * @throws Exception 
	 */
	public void reload(int idProjet) throws Exception {
		data = controlManager.getModelManager(this.getName()).loadDataTimer(idProjet);
		this.fireTableDataChanged();
	}
	
	
	public  void addRow(DataInfoTimer value ) throws Exception
	{
	
		controlManager.getModelManager(this.getName()).addTime(value);
	
		super.addRow(value);
		
	}
	
	public  void updateRow(DataInfoTimer value ) throws Exception
	{
		controlManager.getModelManager(this.getName()).updateTime(value);	
		//this.fireTableDataChanged();
		super.fireTableRowsUpdated(value.getRow(), value.getRow());
		
	}

	public void removeRow(int position) throws Exception{
		//Supprmer  les données tecritures de la base
		DataInfoTimer element = data.elementAt(position);
		controlManager.getModelManager(this.getName()).suppDataTimeDetail(element);
		super.removeRow(position);
	}

	


	//Retourne vrai si la cellule est éditable : celle-ci sera donc éditable

	public boolean isCellEditable(int row, int col){

	  
	  return false;

	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// "ID", "Projet", "Fonction", "Date", "Start", "Date", "Stop" ,"Charge", "Hors plage","Dans la plage","Pro"	
		
		DataInfoTimer row = data.get(rowIndex);
		row.setUnite(unite);
		switch(columnIndex){
		

		case 0 : // Projet
			return (Object)row.getId();

		case 1 : // Projet
			return (Object)row.getLibProjet();
		case 2: // Fonction
			return (Object)row.getLibFonction();
		case 3 : // Date
			return (Object)row.getStartStr();

		case 4:
			return (Object)row.getStartHeureStr();
		case 5 : // Date
			return (Object)row.getStopStr();

		case 6:
			return (Object)row.getStopHeureStr();
		case 7: //charge
			return (Object)String.format("%6.2f",row.getCharge());
		case 8: // hors plage
			return (Object)String.format("%6.2f", row.getHorsplage());

		case 9: // dans plage
			return (Object)String.format("%6.2f", row.getDansplage());
			
		case 10: // Pro
			return row.isPro();
	
		}
		return "";
	}

	/**
	 * @param selectedIndex
	 */
	public void setUnite(int unite) {
		this.unite = unite;
		this.fireTableDataChanged();
		
	}

	/**
	 * @return the unite
	 */
	public int getUnite() {
		return unite;
	}



}
