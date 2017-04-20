/**
 * 
 */
package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import controleur.ControlManager;
import controleur.Principale;

/**
 * @author test
 *
 */
public class DataInfoProjet extends DataInfo {
	String libelle;
	int id;
	boolean selected;
	int idpareto; 
	int idobjectif; 
	int idtypologie;
	int idmotivation;
	int idcoeff;
	Date dateSouhaitee;
	String dateSouhaiteeStr;
	double estimationtotale;
	double chargetotale;
	double resteafairetotal;
	Date dateFinEstimee; // date du jour + reste à faire + estimation initial- chargetotale 

	
	
	double estimationDeLEncours;
	double estimationDuRealise;

	double resteafaireDeLEncours;
	java.sql.Date debut;
	
	int index;
	private boolean affichageSimple;
	
	/**
	 * @param libelle
	 * @param id
	 * @param b
	 */
//	public DataInfoProjet(int id, String libelle, boolean b) {
//		super();
//		this.libelle = libelle;
//		this.id = id;
//		this.selected = b;
//		setModeAffichageSimple();
//		
//	}
	/**
	 * @param id
	 * @param libelle
	 * @param b
	 * @param index
	 */
	public DataInfoProjet(int id, String libelle, boolean b,int idpareto, int idobjectif,int idtypologie, int idmotivation,int idcoeff,
			Date dateSouhaitee, int index) {
		super();
		this.libelle = libelle;
		this.id = id;
		this.selected = b;
		this.index=index;
		this.idpareto=idpareto; 
		this.idobjectif=idobjectif;
		this.idtypologie=idtypologie;
		this.idmotivation=idmotivation;
		this.idcoeff = idcoeff;
		this.dateSouhaitee = dateSouhaitee;
		this.dateSouhaiteeStr = formatDtSouhaitee();
		
		this.estimationDuRealise = 0d;
		this.resteafaireDeLEncours =0d;

		this.resteafaireDeLEncours=0d;
		this.debut = null;
		
		
		setModeAffichageSimple();
		
	}
	
	
	
	/**
	 * @param libelle
	 */
	public DataInfoProjet(String libelle) {
		super();
		this.libelle = libelle;
		this.id=-1;
		setModeAffichageSimple();
	}



	/**
	 * @param dateSouhaitee2
	 * @return
	 */
	private String formatDtSouhaitee() {
		 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		this.dateSouhaiteeStr = sdf.format(this.dateSouhaitee);
		return this.dateSouhaiteeStr;
	}

	private String formatDtEstimee() {
		 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Calendar c = Calendar.getInstance();
		//Calcule la date estimmée Date du jour + resteafaire expimé en jour ...
		
		int nbrJourEstimee = (int) Math.round(resteafairetotal / (double)Principale.nombreDHeureTravailleeDanslaJournee);
		
		c.add(Calendar.DAY_OF_YEAR, nbrJourEstimee);
		
//		System.out.println("DataInfoProjet.formatDtEstimee() 114 : resteafaire = "+resteafairetotal+" nbrJour="+nbrJourEstimee);
		this.dateFinEstimee = c.getTime();
		return sdf.format(this.dateFinEstimee);
	}
	
	/**
	 * @return the dateSouhaitee
	 */
	public Date getDateSouhaitee() {
		return dateSouhaitee;
	}

	public String getDateSouhaiteeStr() {
		return dateSouhaiteeStr;
	}

	/**
	 * @param dateSouhaitee the dateSouhaitee to set
	 */
	public void setDateSouhaitee(Date dateSouhaitee) {
		this.dateSouhaitee = dateSouhaitee;
		this.dateSouhaiteeStr = formatDtSouhaitee();
	}


	/**
	 * @return the idcoeff
	 */
	public int getIdcoeff() {
		return idcoeff;
	}



	/**
	 * @param idcoeff the idcoeff to set
	 */
	public void setIdcoeff(int idcoeff) {
		this.idcoeff = idcoeff;
	}

	/**
	 * @return
	 */
	private String getCoeff() {
		ControlManager theCtrl = Principale.ctrlManager;
		ModelManager theManager = theCtrl.getModelManager();
		
		return theManager.getMurphy(idcoeff) ;
	}
	/**
	 * @return the idpareto
	 */
	public int getIdpareto() {
		return idpareto;
	}
	
	public String getPareto() {
		
		ControlManager theCtrl = Principale.ctrlManager;
		ModelManager theManager = theCtrl.getModelManager();
		
		return theManager.getPareto(idpareto) ;
	}

	/**
	 * @param idpareto the idpareto to set
	 */
	public void setIdpareto(int idpareto) {
		this.idpareto = idpareto;
	}
	/**
	 * @return the idobjectif
	 */
	public int getIdobjectif() {
		return idobjectif;
	}
	/**
	 * @param idobjectif the idobjectif to set
	 */
	public void setIdobjectif(int idobjectif) {
		this.idobjectif = idobjectif;
	}
	
	public String getObjectif() {
		
		ControlManager theCtrl = Principale.ctrlManager;
		ModelManager theManager = theCtrl.getModelManager();
		
		return theManager.getObjectif(idobjectif) ;
	}

	
	/**
	 * @return the idtypologie
	 */
	public int getIdtypologie() {
		return idtypologie;
	}
	/**
	 * @param idtypologie the idtypologie to set
	 */
	public void setIdtypologie(int idtypologie) {
		this.idtypologie = idtypologie;
	}
	/**
	 * @return the idmotivation
	 */
	public int getIdmotivation() {
		return idmotivation;
	}
	/**
	 * @param idmotivation the idmotivation to set
	 */
	public void setIdmotivation(int idmotivation) {
		this.idmotivation = idmotivation;
	}
	/**
	 * @return
	 */
	private Object getMotivation() {
		ControlManager theCtrl = Principale.ctrlManager;
		ModelManager theManager = theCtrl.getModelManager();
		
		return theManager.getPlaisir(idmotivation);	
	}
	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isAffichageSimple())
		 if (selected) return "* " +getLibelle()  ;
		 else return getLibelle();

		String strEtoile="   ";
		if (selected) strEtoile= " * "  ;  
		
		return String.format("%s %s, %s - %s, %s - %s", getLibelle() ,strEtoile, getPareto(), getObjectif(),getMotivation(),getCoeff());
		
	}



	/**
	 * 
	 */
	public void setModeAffichageSimple() {

		this.affichageSimple=true;
		
	}
	public void setModeAffichageDetail() {

		this.affichageSimple=false;
		
	}
	public void setModeAffichage(boolean bMode) {

		this.affichageSimple=bMode;
		
	}
	/**
	 * @return the affichageSimple
	 */
	public boolean isAffichageSimple() {
		return affichageSimple;
	}



	/**
	 * @return the estimationtotale
	 */
	public double getEstimationtotale() {
		return getConvertUnitHeureEnJour(estimationtotale);
	}



	/**
	 * @param estimationtotale the estimationtotale to set
	 */
	public void setEstimationtotale(double estimationtotale) {
		this.estimationtotale = estimationtotale;
	}



	/**
	 * @return the chargetotale
	 */
	public double getChargetotale() {
		return getConvertUnitHeureEnJour(chargetotale);
	}



	/**
	 * @param chargetotale the chargetotale to set
	 */
	public void setChargetotale(double chargetotale) {
		this.chargetotale = chargetotale;
	}



	/**
	 * @return the resteafairetotal
	 */
	public double getResteafairetotal() {
		return getConvertUnitHeureEnJour(resteafairetotal);
	}



	/**
	 * @param resteafairetotal the resteafairetotal to set
	 */
	public void setResteafairetotal(double resteafairetotal) {
		this.resteafairetotal = resteafairetotal;
	}



	/**
	 * @return the dateFinEstimee
	 */
	public Date getDateFinEstimee() {
		return dateFinEstimee;
	}



	/**
	 * @param dateFinEstimee the dateFinEstimee to set
	 */
	public void setDateFinEstimee(Date dateFinEstimee) {
		this.dateFinEstimee = dateFinEstimee;
	}



	/**
	 * @return
	 */
	public String getDateFinEstimeeStr() {
		
		return formatDtEstimee();
	}



	/**
	 * @param int1
	 * @param string
	 * @param boolean1
	 * @param double1
	 * @param double2
	 * @param double3
	 */
	public void setEstimation(boolean terminer,
			double estimationInitiale, double resteafaire, double oldresteafaire) {
		
//		double estimationDeLEncours;
//		double estimationDuRealise;
//		double chargeDeLEncours;
//		double chargeDuRealise;
//		double resteafaireDeLEncours;
//		Date debut;
		

		if (terminer) // Tâches sont archivées
		{
			estimationDuRealise +=estimationInitiale + oldresteafaire;
			
		}
		else { // Tâche en cours de réalisation
			
			estimationDeLEncours +=estimationInitiale + oldresteafaire;
			resteafaireDeLEncours += resteafaire;
		}
		
		calculEstimationtotale();
	}



	/**
	 * 
	 */
	private void calculEstimationtotale() {
		estimationtotale = estimationDuRealise + estimationDeLEncours;
		resteafairetotal = estimationDeLEncours + resteafaireDeLEncours;
	}



	/**
	 * @param debut
	 * @param charge
	 */
	public void setConsommation(java.sql.Date debut, double charge) {
		
		System.out.println("setConsommation(java.sql.Date debut, double charge="+charge+") ");
		
		this.debut = debut;
		this.chargetotale = charge;
		
	}






	

}
