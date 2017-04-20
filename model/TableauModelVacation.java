/**
 * 
 */
package model;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;
import controleur.Principale;

/**
 * @author test
 *
 */
public class TableauModelVacation extends TableauModel<DataInfoVacation> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TableauModelVacation.class ); 

	ParamModelManager managerModel;
	
	

	/**
	 * @throws Exception 
	 * 
	 */
	public TableauModelVacation() throws Exception {
		super();
		
		log.traceEntry("TableauModelVacation()");
		
		
		traduction = Principale.config.getTraduction("paramview");

		
		setName("TableauModelVacation");
		
		this.managerModel=(ParamModelManager) controlManager.getModelManager(getName());
		

		entete = new String[3];
		entete[0]=traduction.getString("vacation.date.debut");
		entete[1]=traduction.getString("vacation.date.fin");
		entete[2]=traduction.getString("vacation.date.duree");
		

		columnTypes = new Class[] {
				String.class, String.class, String.class
			};
		
		columnEditables = new boolean[] {
				false, false,false 
			};
	
		//reload(-1);
		log.traceExit("TableauModelVacation()");
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		// "D\u00E9but", "Fin", "Dur\u00E9e en heure"

		log.traceEntry("TableauModelVacation.getValueAt(int rowIndex="+rowIndex+", int colIndex="+colIndex+")");
		DataInfoVacation row = data.get(rowIndex);
		switch(colIndex){


		case 0 : // Début
			return (Object)row.getDebut();

		case 1 : // Projet
			return (Object)row.getFin();
		case 2: // Fonction
			return (Object)row.getDuree();
		


		}
		return "";

	}
	
	public  void addRow(DataInfoVacation value ) throws Exception
	{
		managerModel.addVacation(value);	
		super.addRow(value);
		
	}
	
	public  void updateRow(DataInfoVacation value )
	{
		//controlManager.getModelManager(this.getName()).updateTime(value);	
		//this.fireTableDataChanged();
//		super.fireTableRowsUpdated(value.getRow(), value.getRow());
		
	}

	public void removeRow(int position) throws Exception{
		//Supprmer  les données tecritures de la base
		DataInfoVacation element = data.elementAt(position);
		managerModel.suppVacation(element);
		super.removeRow(position);
	}


	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public void load() throws ExceptionTraitementSQL {
		data =	managerModel.loadVacation();
		
		this.fireTableDataChanged();
		
	}
}
