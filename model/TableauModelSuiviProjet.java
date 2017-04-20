/**
 * 
 */
package model;







import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;
import controleur.Principale;



/**
 * @author test
 *
 */
public class TableauModelSuiviProjet extends  TableauModel<DataInfoSuiviProjet>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TableauModelSuiviProjet.class );
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("suiviprojetview");
	private ModelManager managerModel;
	private int idProjet; 
	private int unite=1;
	private boolean archive;
	DataInfoProjet currentinfoProjet;
	
	
	
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public TableauModelSuiviProjet(ModelManager manager, boolean archive) throws ExceptionTraitementSQL {
		super();
		
		this.managerModel=manager;
		
		// TODO A mettre dans la V2 , une colonne "Exporter" 

		this.archive=archive;
		this.setName("TableauModelSuiviProjet");

		if (archive){
			entete = new String[] {
					TRADUCTION.getString("TableauModelSuiviProjet.col01.fonction"),
					TRADUCTION.getString("TableauModelSuiviProjet.col02.complexite"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col03.estimation"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col04.fin"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col05.charge"),
					TRADUCTION.getString("TableauModelSuiviProjet.col06.delai"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col07.marge")
			};
		}else{
			entete = new String[] {
					TRADUCTION.getString("TableauModelSuiviProjet.col01.fonction"),
					TRADUCTION.getString("TableauModelSuiviProjet.col02.complexite"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col03.estimation"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col04.debut"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col05.charge"),
					TRADUCTION.getString("TableauModelSuiviProjet.col06.resteafaire"), 
					TRADUCTION.getString("TableauModelSuiviProjet.col07.marge") 
			};

		}
		
		columnEditables = new boolean[] {
				false, false, false,false, false, false ,false
			};
		
		columnTypes = new Class[] {
				String.class, String.class,String.class, String.class, String.class,String.class,String.class  
			};
			

		
		setIdProjet(-1);
		reload(null);

	}


	/**
	 * @param controlManager
	 * @throws ExceptionTraitementSQL 
	 */
	public void reload(DataInfoProjet currentinfoProjet) throws ExceptionTraitementSQL {
		 
		// Avant l'affichage des infos de suivi de projet
		int idprojet = -1;
		this.currentinfoProjet=currentinfoProjet;
		
		if (currentinfoProjet != null) idprojet = currentinfoProjet.getId();
		data = managerModel.loadSuiviProjet(idprojet,archive);
		this.fireTableDataChanged();
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		// "Fonction","Complexite","Estimation","Début", "Charge","Reste à faire" 
		DataInfoSuiviProjet row = data.get(rowIndex);
		row.setUnite(unite);
		switch(columnIndex){
		
		case 0: // Fonction
			return (Object)row.getFoncName();
		case 1: // Complexite
			return (Object)row.getLibComplexite();
			
		case 2: // Estimation
			return (Object)String.format("%6.2f", row.getEstimation());

		case 3: // Début ou fin
			if (archive)
				return (Object)row.getFin();
			else
				return (Object)row.getDebut();
		
		case 4: // Charge
			return (Object)String.format("%6.2f", row.getCharge());
		
		case 5: // Reste à faire
			if (archive)
				return (Object)String.format("%3d", row.getDelai());
			else
				return (Object)String.format("%6.2f", row.getResteafaire());
 
		case 6 :
			if (archive)
				return (Object)"";
			else
			return  (Object)String.format("%6.2f", row.getMarge(currentinfoProjet));
//		case 6: // Exportable
//			return (Object)row.isExporter();

		}
		return "";
	}
	
//    public void setValueAt(Object value, int row, int col) {
//    	
//    	DataInfoSuiviProjet rowData = data.get(row);
//		if (rowData.isExporter())
//			rowData.setExporter(false);
//		else
//			rowData.setExporter(true);
//		
//        fireTableCellUpdated(row, col);
//    }

	public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
	}


	/**
	 * @return the unite
	 */
	public int getUnite() {
		return unite;
	}



	/**
	 * @param unite the unite to set
	 */
	public void setUnite(int unite) {
		this.unite = unite;
		this.fireTableDataChanged();
	}


	/**
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
//	public void updateChargeDelai(int idprojet) throws ExceptionTraitementSQL {
//		
//		managerModel.updateChargeDelai(idprojet);
//		reload(idprojet);
//		
//	}


	/**
	 * @return the idProjet
	 */
	public int getIdProjet() {
		return idProjet;
	}


	/**
	 * @param idProjet the idProjet to set
	 */
	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}
		
	
}
