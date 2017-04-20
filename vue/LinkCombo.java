/**
 * 
 */
package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.apache.logging.log4j.LogManager;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import model.DataInfoFonction;
import model.DataInfoProjet;


/**
 * @author test
 *
 */
public class LinkCombo<P, F> {

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( LinkCombo.class ); 
	JComboBox<DataInfoProjet> source;
	JComboBox<DataInfoFonction> destination;
	int idProjet;
	ControlManager controlManager;
	private boolean ecouter;
	protected String nameClass;
	
	/**
	 * @param source
	 * @param destination
	 * @throws ExceptionTraitementSQL 
	 */
	public LinkCombo(JComboBox<DataInfoProjet> source, JComboBox<DataInfoFonction> destination,ControlManager controlManager) throws ExceptionTraitementSQL {
		super();
		
		log.traceEntry("LinkCombo(JComboBox<DataInfoProjet> source, JComboBox<DataInfoFonction> destination,ControlManager controlManager)") ;
		
		this.source = source;
		this.destination = destination;
	
		this.controlManager = controlManager;
		
		setEcouter(false);
		load();
	
		source.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChange();
			}
		});

		log.traceExit("LinkCombo(JComboBox<DataInfoProjet> source, JComboBox<DataInfoFonction> destination,ControlManager controlManager)") ;
		
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
	
	public void load() throws ExceptionTraitementSQL
	{
		ecouter =false;
		
		int indexSelected;
		try {
			indexSelected = controlManager.getModelManager(this.getName()).loadProjetInCombo(this.source,false );
			if (indexSelected !=-1)
				this.idProjet = this.source.getItemAt(indexSelected).getId();
			else if (this.source.getItemCount() !=0) 
			 		this.idProjet =0;
			else
				this.idProjet =-1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		//this.destination.removeAll();
		if (this.idProjet !=-1) {
			try {
				controlManager.getModelManager(this.getName()).loadFonctionInCombo(this.idProjet,this.destination );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ecouter =true;
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	protected void actionChange()  {
		
		log.traceEntry("actionChange() ecouter="+ecouter);
		if (!isEcouter()) return;
		int index = source.getSelectedIndex();

		// System.out.println(" Action sur la combo source  ...." + Integer.toString(index));
		DataInfoProjet info = source.getItemAt(index);

		destination.removeAllItems();
		if (index!=-1){
			try {
			
					controlManager.getModelManager(this.getName()).loadFonctionInCombo(info.getId(),destination);
			
			} catch (Exception e) {
				// TODO Afficher un message d'erreur sur le chargement
				e.printStackTrace();
			}
		}
		
		log.traceExit("actionChange()");
		
	}
	public void initSelect(int idProjet,int idFonc){
		
		log.traceEntry("initSelect(int idProjet="+idProjet+",int idFonc="+idFonc+")");
		int i,index=0;
		
		for(i=0;i<source.getItemCount();i++)
			if (source.getItemAt(i).getId()==idProjet){
				index = i;
				i=source.getItemCount();
				break;
			}
		
		source.setSelectedIndex(index);
		
		for(i=0;i<destination.getItemCount();i++)
			if (destination.getItemAt(i).getId()==idFonc){
				index = i;
				i=destination.getItemCount();
				break;
			}
		
		destination.setSelectedIndex(index);
	}
	/**
	 * @return the ecouter
	 */
	public boolean isEcouter() {
		return ecouter;
	}
	/**
	 * @param ecouter the ecouter to set
	 */
	public void setEcouter(boolean ecouter) {
		this.ecouter = ecouter;
	}
	
}
