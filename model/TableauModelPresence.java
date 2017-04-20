/**
 * 
 */
package model;



import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;
import controleur.PlageIndex;
import controleur.Principale;

;

/**
 * @author test
 *
 */
public class TableauModelPresence extends TableauModel<DataInfoPeriodeActivite> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TableauModelPresence.class ); 

	ParamModelManager managerModel;

	
	/**
	 * @throws Exception 
	 * 
	 */
	public TableauModelPresence() throws Exception {
		super();

		
		log.traceEntry("TableauModelPresence()");
		
		
		
		setName("TableauModelPresence");
		
		try {
			this.managerModel=(ParamModelManager) controlManager.getModelManager(getName());
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		traduction = Principale.config.getTraduction("paramview");

		
		entete = new String[3];
		entete[0]=traduction.getString("activite.heure.debut");
		entete[1]=traduction.getString("activite.heure.fin");
		entete[2]=traduction.getString("activite.heure.duree");
		

		columnTypes = new Class[] {
				String.class, String.class, String.class
			};
		
		columnEditables = new boolean[] {
				false, false, false
			};
	
		//reload(-1);
		log.traceExit("TableauModelPresence()");
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		// "D\u00E9but", "Fin", "Dur\u00E9e en heure"
		log.traceEntry("TableauModelPresence.getValueAt(int rowIndex="+rowIndex+", int colIndex="+colIndex+")");
		DataInfoPeriodeActivite row = data.get(rowIndex);
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

	public  void addRow(DataInfoPeriodeActivite value ) throws Exception
	{
		//controlManager.getModelManager(this.getName()).addTime(value);	
		managerModel.addActivite(value);
		super.addRow(value);
		
	}
	
	public  void updateRow(DataInfoPeriodeActivite value )
	{
		//controlManager.getModelManager(this.getName()).updateTime(value);	
		//this.fireTableDataChanged();
//		super.fireTableRowsUpdated(value.getRow(), value.getRow());
		
	}

	public void removeRow(int position, Vector<PlageIndex> listePlageHoraire) throws Exception{
		//Supprmer  les données tecritures de la base
		DataInfoPeriodeActivite element = data.elementAt(position);
		
		int debutPlg=element.getIndexDebut();
		int indexASupprimer=-1;
		PlageIndex plg;
		
		for (int index=0;index<listePlageHoraire.size();index++){
			plg = listePlageHoraire.get(index);
			if (plg.getBornInf()==debutPlg){
				indexASupprimer=index;
				break;
			}
		}
		if (indexASupprimer != -1)
		listePlageHoraire.remove(indexASupprimer);
		
		managerModel.suppPresence(element);
		super.removeRow(position);
	}

	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public void load() throws ExceptionTraitementSQL {
		
		data =	managerModel.loadPresence();
		this.fireTableDataChanged();
		
	}
	
}
