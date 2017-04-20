/**
 * 
 */
package model;

import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import vue.EstimationView;



/**
 * @author test
 *
 */
public class TableauModelEstimation extends TableauModel<DataInfoEstimation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TableauModelEstimation.class ); 
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("estimationview");
	private int unite;




	EstimationModelManager managerModel;
	private JLabel lblEnregistrementAFaire;
	private String txtLable;
	private int idProjet;
	private EstimationView parent;
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public TableauModelEstimation(EstimationModelManager managerModel) throws ExceptionTraitementSQL {
		super();
		
		log.traceEntry("TableauModelEstimation(EstimationModelManager managerModel)");
		
		unite=1;
		this.managerModel=managerModel;
		entete = new String[] {
				TRADUCTION.getString("TableauModelEstimation.col01.fonction"), 
				TRADUCTION.getString("TableauModelEstimation.col02.pareto"),
				TRADUCTION.getString("TableauModelEstimation.col03.eisen"), 
				TRADUCTION.getString("TableauModelEstimation.col04.motiv"),
				TRADUCTION.getString("TableauModelEstimation.col05.complex"), 
				TRADUCTION.getString("TableauModelEstimation.col06.conso"),
				TRADUCTION.getString("TableauModelEstimation.col07.estim0"),
				TRADUCTION.getString("TableauModelEstimation.col08.resteafaire"),
				TRADUCTION.getString("TableauModelEstimation.col09.total")
		};
		
		// TODO Spec des estimations pour le suivi des projets
		// Estimation = Estimations initiales + somme des RAF
		// Conso = somme des chronos
		// Resteafaire=0 au départ. Si le reste à faire change on enregistre et on crée une nouvelle estimation
		// Les fonctions affichées en estimations ne sont pas archivées et ne sont pas non plus reportées
		
		columnTypes = new Class[] {
				String.class,  
				JComboBox.class, 
				JComboBox.class, 
				JComboBox.class, 
				JComboBox.class, 
				Double.class,
				Double.class, 
				Double.class, 
				String.class
			};
		
		columnEditables = new boolean[] {
				false, 
				true,
				true,
				true,
				true,  
				false,  
				true ,  
				true,  
				false
			};
	
		this.setName("TableauModelEstimation");
		idProjet =-1;
		reload(-1);
		log.traceExit("TableauModelEstimation()");
	}
	


	/**
	 * @param controlManager
	 * @throws ExceptionTraitementSQL 
	 */
	public void reload(int idprojet) throws ExceptionTraitementSQL {
		log.traceEntry("void reload(int idprojet="+idprojet+")");
		idProjet =idprojet;
		data = managerModel.loadDataEstimation(idprojet);
		this.fireTableDataChanged();
		log.traceExit("void reload(int idprojet="+idprojet+")");
		
	}
	

	//Retourne vrai si la cellule est éditable : celle-ci sera donc éditable

	public boolean isCellEditable(int row, int col){

		return(columnEditables[col]);
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		log.traceEntry("getValueAt(int rowIndex="+rowIndex+", int columnIndex="+columnIndex+")");
		//"Fonction", "Pareto","Eisenhower", "Motivation","Complexité", "Consommé","Estimation 0","Reste à faire","Total"
		
		
		
		DataInfoEstimation row = data.get(rowIndex);
		row.setUnite(unite);
		switch(columnIndex){
		
		case 0 : // Fonction
			return (Object)row.getLibelle();
		case 1 : // Pareto
			return (Object)String.format("%s", row.getPareto());
		case 2: // Eisenhower
			return (Object)String.format("%s", row.getEisenhower());

		case 3: // Motivation
			return (Object)String.format("%s", row.getMotivation());
		
		case 4: // Complexité
			return (Object)row.getComplexite();	
			
		case 5: // Charge consommée
			return (Object)String.format("%6.2f", row.getCharge());
		case 6: //Estimation
			return (Object)String.format("%6.2f", row.getEstimation());			
		case 7: // Reste à faire
			return (Object)String.format("%6.2f", row.getResteafaire());
		case 8: // Total
			return (Object)String.format("%6.2f", row.getTotal());	
			
 
			
		} 
		
		return "";
	}



	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		//super.setValueAt(value, row, col);
		log.traceEntry("setValueAt(Object value="+value+", int row="+row+", int col="+col+")");
		
		//"Fonction", "Pareto","Eisenhower", "Motivation","Estimation initiale","Complexité","Consommé", "Estimation","Reste à faire",Total

 		DataInfoEstimation element = data.get(row);
		element.setUnite(unite);

		switch(col){

			case 1 : // Pareto	
				element.setIdPareto(((DataRefLibelle) value).getId());
				setModif();
				break;
			case 2: // Eisenhower
				element.setIdEisenhower(((DataRefLibelle) value).getId());
				setModif();
				break;
			case 3: // Motivation				
				element.setIdMotivation(((DataRefLibelle) value).getId());
				setModif();
				break;

			case 4: // Complexité
				element.setComplexite((DataRefLibelle) value);
				setModif();
				break;

			case 6: //Estimation 
				element.setEstimationInit( (Double) value); 
				fireTableCellUpdated(row, 3);
				setModif();
				break;				 

			case 7: //"Reste à faire
				element.setResteafaire((Double) value);
				fireTableCellUpdated(row, 3);
				setModif();
				break;


				
		}
		 
	}



	/**
	 * 
	 */
	public void setModif() {
		//lblEnregistrementAFaire.setText(txtLable+ "*");
		//lblEnregistrementAFaire.repaint();
		this.parent.setTitle(txtLable + "*");
		this.parent.setEnregistrerEnable(true);
	}



	/**
	 * 
	 */
	public void removeAll() {
		// pas utilisé
		
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
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public void saveData() throws ExceptionTraitementSQL {
		managerModel.saveDataEstimation(data);
		
	}



	/**
	 * @param lblEnregistrement
	 */
	public void setLabelModif2(JLabel lblEnregistrement) {
		
		this.lblEnregistrementAFaire = lblEnregistrement; 
		this.txtLable = lblEnregistrement.getText();
		this.lblEnregistrementAFaire.setText("");
	}

public void setViewParent(EstimationView parent) {
		this.parent = parent;
		this.txtLable = parent.getTitle();  
	}



	/**
	 * @param rowSel
	 * @throws ExceptionTraitementSQL 
	 */
	public void archive(int rowSel) throws ExceptionTraitementSQL {
		//DataInfoEstimation element = (DataInfoEstimation) getDataItem(rowSel);
		//managerModel.archiveFonction( element.getIdfonc());
		reload(this.idProjet) ;
	}
}
