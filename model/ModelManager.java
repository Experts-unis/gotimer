/**
 * 
 */
package model;




import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;


import javax.swing.JComboBox;


import org.apache.logging.log4j.LogManager;

import controleur.ExceptionInitBase;
import controleur.ExceptionTraitementSQL;







/**
 * @author test
 *
 */
public class ModelManager {

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( ModelManager.class );  


	
	
	protected TFonctionsDB tFonctions;
	protected TProjetsDB tProjets;
	protected TTypologieDB tTypologie;
	protected TTimeDB tTime;
	protected TEstimationDB tEstimations;
	
	protected TRefLibelleProperties tComplexite;
	protected TRefLibelleProperties tUnite;
	protected TRefLibelleProperties tPareto;
	protected TRefLibelleProperties tEisenhower,tEisenhowerShort;
	protected TRefLibelleProperties tObjectifs;
	protected TRefLibelleProperties tObjectifsShort;
	protected TRefLibelleProperties tPlaisir;
	protected TRefLibelleProperties tPlaisirSymbol;
	protected TRefLibelleProperties tMurphy;
	protected TRefLibelleProperties tMotCls;
	
	



	private DBProgres driver;


	/**
	 * @throws ExceptionTraitementSQL 
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 * 
	 */
	public ModelManager() throws ExceptionInitBase, ExceptionTraitementSQL  {
		
		log.traceEntry("ModelManager()") ;

		setDriver(new DBProgres());
		tFonctions = new TFonctionsDB();
		tProjets = new TProjetsDB();
		tTime = new TTimeDB();
		tTypologie = new TTypologieDB ();
		tEstimations = new TEstimationDB();
		
		// Table de référence statique.
		tPlaisir = new TRefLibelleProperties("TREF_PLAISIR");
		tPlaisirSymbol = new TRefLibelleProperties("TREF_PLAISIRSYMBOL");
		tMurphy = new TRefLibelleProperties("TREF_COEFF_MURPHY");
		tMotCls = new TRefLibelleProperties("TREF_MOTSCLS");
		tComplexite = new TRefLibelleProperties("TREF_COMPLEXITE");
		tUnite = new TRefLibelleProperties("TREF_UNITE");
		tPareto = new TRefLibelleProperties("TREF_PARETO");
		tEisenhower = new TRefLibelleProperties("TREF_EISENHOWER");
		tEisenhowerShort= new TRefLibelleProperties("TREF_EISENHOWER_SHORT");
		tObjectifs  = new TRefLibelleProperties("TREF_OBJECTIFS");
		tObjectifsShort  = new TRefLibelleProperties("TREF_OBJECTIFSSHORT");

		
				
		
		
		log.traceExit("ModelManager()") ;

	}

	public int isSelectProjet() throws ExceptionTraitementSQL {
		// Un projet est il sélectionné ? Implémenter model.isSelectProjet()
		log.traceEntry("isSelectProjet()") ;
		int ret=tProjets.isSelectProjetExist();
		log.traceExit("isSelectProjet() " + ret);
		return ret;
	}

	/**
	 * @param idProjet
	 * @param cmbProjets
	 * @throws ExceptionTraitementSQL 
	 */
	public int loadProjetInCombo( JComboBox<DataInfoProjet> cmbProjets, boolean aveclesestimations) throws ExceptionTraitementSQL {
		log.traceEntry("loadProjetInCombo( JComboBox<DataInfoProjet> cmbProjets)");
		int indexSelected;
		Vector <DataInfoProjet> lesProjets = tProjets.getList();
		if (aveclesestimations){
			tProjets.getEstimationsProjets(lesProjets);
			tProjets.getConsommationProjets(lesProjets);
		}	
		indexSelected = copyVectorInComboBox(cmbProjets, lesProjets);
		//indexSelected = copyVectorInComboBox(cmbProjets, lesProjets);
		cmbProjets.setSelectedIndex(indexSelected);
		log.traceExit("loadProjetInCombo() indexSelected="+indexSelected);	
		return indexSelected;
	}
	
	/**
	 * @param idProjet
	 * @param cmbFonction
	 * @throws ExceptionTraitementSQL 
	 */
	public void loadFonctionInCombo(int idProjet, JComboBox<DataInfoFonction> cmbFonction) throws ExceptionTraitementSQL {
		log.traceEntry("loadFonctionInCombo(int idProjet="+idProjet+", JComboBox<DataInfoFonction> cmbFonction)");
		Vector<DataInfoFonction> liste = tFonctions.getList(idProjet);
		
		copyVectorInComboBoxFonction(cmbFonction,liste);
		log.traceExit("");	
	}
	/**
	 * @param cmbUnite
	 */
	public void loadUniteInCombo(JComboBox<DataRefLibelle> cmbUnite) {
		
		log.traceEntry("loadUniteInCombo");
		copyBundleInComboBoxSimple(cmbUnite, tUnite.getData());
		cmbUnite.setSelectedIndex(0);
		log.traceExit("loadUniteInCombo");
	}

	/**
	 * @param cmbPareto
	 */
	public void loadParetoInCombo(JComboBox<DataRefLibelle> cmbPareto) {
		log.traceEntry("loadParetoInCombo");
		copyBundleInComboBoxSimple(cmbPareto, tPareto.getData());
		cmbPareto.setSelectedIndex(0);	
		log.traceExit("loadParetoInCombo");
	}
	public String getPareto(int index){
		
	
		return tPareto.get(index);
	}

	/**
	 * @param cmbEisenhower
	 */
	public void loadEisenhowerInCombo(JComboBox<DataRefLibelle> cmbEisenhower) {

		log.traceEntry("loadEisenhowerInCombo");
		copyBundleInComboBoxSimple(cmbEisenhower, tEisenhower.getData());
		cmbEisenhower.setSelectedIndex(0);
		log.traceExit("loadEisenhowerInCombo");
	}
	public String getEisenhower(int index){
		
		return tEisenhowerShort.get(index);
		
	}
	/**
	 * @param cmbPlaisir
	 */
	public void loadPlaisirInCombo(JComboBox<DataRefLibelle> cmbPlaisir) {
		log.traceEntry("loadPlaisirInCombo");
		copyBundleInComboBoxSimple(cmbPlaisir, tPlaisir.getData());
		cmbPlaisir.setSelectedIndex(0);
		log.traceExit("loadPlaisirInCombo");
	}
	
	public String getPlaisir(int index){

		return tPlaisirSymbol.get(index);
	}

	/**
	 * @param cmbMurphy
	 */
	public void loadMurphyInCombo(JComboBox<DataRefLibelle> cmbMurphy) {
		log.traceEntry("loadMurphyInCombo");
		copyBundleInComboBoxSimple(cmbMurphy, tMurphy.getData());
		cmbMurphy.setSelectedIndex(0);
		log.traceExit("loadMurphyInCombo");
	}

	public String getMurphy(int index){
		
		return tMurphy.get(index);
	}
	


	/**
	 * @param cmbObjectifs
	 */
	public void loadObjectifInCombo(JComboBox<DataRefLibelle> cmbObjectifs) {

		log.traceEntry("loadObjectifInCombo");
		
		copyBundleInComboBoxSimple(cmbObjectifs, tObjectifs.getData());
		cmbObjectifs.setSelectedIndex(0);
		log.traceExit("loadObjectifInCombo");
		
	}

	public String getObjectif(int index){
		return tObjectifsShort.get(index);
	}
	
	/** Permet d'enregistrer une info timer sur la DB
	 * @param currentInfoTime
	 * @throws ExceptionTraitementSQL 
	 */
	public void addTime(DataInfoTimer currentInfoTime) throws ExceptionTraitementSQL {
		log.traceEntry("addTime(DataInfoTime currentInfoTime)");
		//(dt, idproj, idfonc, seconde, start, stop, archive=false)
		
		int id=tTime.add(currentInfoTime.getSQLDt(), 
				  currentInfoTime.getIdproj(), 
				  currentInfoTime.getIdfonc(), 
				  currentInfoTime.getCharge(), 
				  currentInfoTime.getHorsplage(),
				  currentInfoTime.getDansplage(), 
				  currentInfoTime.isPro(), 
				  currentInfoTime.getSQLStart(), currentInfoTime.getSQLStop());
		
		currentInfoTime.setId(id);
		log.traceExit("addTime()");	
	}

	/**
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateTime(DataInfoTimer value) throws ExceptionTraitementSQL {
	
		log.traceEntry("updateTime(DataInfoTime currentInfoTime)");
		//(dt, idproj, idfonc, seconde, start, stop, archive=false)
		
		tTime.update( value.getId(), value.getSQLDt(), 
				value.getIdproj(), 
				value.getIdfonc(), 
				value.getCharge(), 
				value.getHorsplage(), 
				value.getDansplage(), 
				value.isPro(), 
				value.getSQLStart(), value.getSQLStop());
		
		log.traceExit("updateTime()");	
	
		
	}
	
	protected int copyVectorInComboBox(JComboBox<DataInfoProjet> cmb, Vector<DataInfoProjet> liste) {
		log.traceEntry("copyVectorInComboBox(JComboBox<DataInfoProjet> cmb, Vector<DataInfoProjet> liste)");
		int selected=-1;

		if (cmb.getItemCount() != 0) cmb.removeAllItems();
		for (int i=0;i<liste.size();i++){
			if (liste.get(i).isSelected()) selected=i;

			DataInfoProjet element = liste.get(i);
			element.setModeAffichageSimple();
			cmb.addItem(element);	
		}
		log.traceExit("copyVectorInComboBox() focused="+selected+" size="+cmb.getItemCount());	
		return selected;

	}
	/**
	 * @param cmbComplexite
	 * @param liste
	 * @return
	 */
	protected void copyVectorInComboBoxSimple(JComboBox<DataRefLibelle> cmb, Vector<DataRefLibelle> liste) {
		
		log.traceEntry("copyVectorInComboBox2(JComboBox cmb, Vector liste)");
		
		
		for (int i=0;i<liste.size();i++){
			cmb.addItem(liste.get(i));	
		}
		
		log.traceExit("copyVectorInComboBox2()  size="+cmb.getItemCount());	

	}
	protected void copyBundleInComboBoxSimple(JComboBox<DataRefLibelle> cmb, ResourceBundle data) {
		
		log.traceEntry("copyBundleInComboBoxSimple(JComboBox cmb, ResourceBundle data)");

		
		Enumeration<String> e = data.getKeys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = data.getString(key);
			cmb.addItem(new DataRefLibelle(new Integer(key), value));
		}
		log.traceExit("copyBundleInComboBoxSimple()  size="+cmb.getItemCount());	

	}
	protected int copyVectorInComboBoxFonction(JComboBox<DataInfoFonction> cmb, Vector<DataInfoFonction> liste) {
		log.traceEntry("copyVectorInComboBoxFonction(JComboBox<DataInfoFonction> cmb, Vector<DataInfoFonction> liste)");
		int selected=-1;
		
		if (cmb.getItemCount()!=0) cmb.removeAllItems();
		for (int i=0;i<liste.size();i++){
			if (liste.get(i).isSelected()) selected=i-1;
			cmb.addItem(liste.get(i));	
		}
		log.traceExit("copyVectorInComboBoxFonction() selected="+selected+" size="+cmb.getItemCount());	
		return selected;
		
	}
	protected  void copyVectorInListModel(ListModelProjet listeModel, Vector<DataInfoProjet> liste) {
		log.traceEntry("copyVectorInListModel(ListModelProjet listeModel, Vector<DataInfoProjet> liste)");
		listeModel.clear();
		for (int i=0;i<liste.size();i++)
			listeModel.addElement(liste.get(i));
		log.traceExit("copyVectorInListModel() size "+listeModel.size());	
	}

	/**
	 * @param archive 
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector <DataInfoSuiviProjet> loadSuiviProjet(int idprojet, boolean archive) throws ExceptionTraitementSQL {
		log.traceEntry("Vector <DataInfoLigneTemps> loadSuiviProjet()");
		
		// Chargement de la table principale
		Vector <DataInfoSuiviProjet> data = new Vector <DataInfoSuiviProjet>();
		
		if (idprojet !=-1){
				data = tTime.getDataSuiviProjet(idprojet,archive);
		}
		

		
		log.traceExit("loadSuiviProjet()  size="+data.size() );	
		return data;
	}
	/**
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector <DataInfoTimer> loadDataTimer(int idProjet) throws ExceptionTraitementSQL {
		log.traceEntry("Vector <DataInfoTime> loadDataTimer(int idProjet="+idProjet+")");
		
		Vector <DataInfoTimer> v = tTime.getDataTimer(idProjet);
		// Chargement de la table enregistrement des timers
		log.traceExit("Vector <DataInfoTime> loadDataTimer()");	
		return v;
	}
	/**
	 * @param element
	 * @throws ExceptionTraitementSQL 
	 */
	public void suppDataTimeDetail(DataInfoTimer element) throws ExceptionTraitementSQL {
		log.traceEntry("suppDataTimeDetail(DataInfoTime element) "+element.getId());
		tTime.delete(element.getId());
		log.traceExit("suppDataTimeDetail() ");
	}



	/**
	 * @param value
	 */
	public void addEstimation(DataInfoEstimation value) {
		// TODO A quel moment les Estimations du reste à faire doivent être renouvellée ?
		
	}


	
	
	/**
	 * @return the driver
	 */
	public DBProgres getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(DBProgres driver) {
		this.driver = driver;
	}

	/**
	 * @param idprojet
	 * @throws ExceptionTraitementSQL 
	 */
//	public void updateRest(int idprojet) throws ExceptionTraitementSQL {
//		
//		tEstimations.update(idprojet);
//		
//	}

	/**
	 * @param listTypo
	 * @throws ExceptionTraitementSQL 
	 */
	public void loadTypologieInView(ListModelTypo listTypo) throws ExceptionTraitementSQL {
		Vector <DataInfoTypologie> list;
		list = tTypologie.getList();
		for (int i=0;i<list.size();i++)
			listTypo.addElement(list.get(i));
	}

	/**
	 * @param elementSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateTypologieElement(DataInfoTypologie elt) throws ExceptionTraitementSQL {
		
		tTypologie.update(elt.getId(),elt.getName(),elt.isSolo(),elt.isPerso());
		
	}

	/**
	 * @param element
	 * @throws ExceptionTraitementSQL 
	 */
	public void addTypologie(DataInfoTypologie elt) throws ExceptionTraitementSQL {
		elt.setId(tTypologie.add(elt.getName(),elt.isSolo(),elt.isPerso()));
		
	}

	/**
	 * @param elementSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void deleteTypologieElement(DataInfoTypologie elt) throws ExceptionTraitementSQL {
		tTypologie.delete(elt.getId());
		
	}









	

}
	


	
