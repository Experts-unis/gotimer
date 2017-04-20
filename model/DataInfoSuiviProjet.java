/**
 * 
 */
package model;


import java.sql.Date;


import org.apache.logging.log4j.LogManager;



/**
 * Utiliser pour afficher le résultat des tempo enregistrées
 * @author AGD
 *
 */
public class DataInfoSuiviProjet extends DataInfo {
	
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DataInfoSuiviProjet.class ); 
	//"Date", "Projet", "Fonction", "Start", "Heure d\u00E9but", "Stop", "Heure arr\u00EAt", "D\u00E9pense temps"
	private Date dt;	
	private String projetName;
	private String foncName;

	private double charge;
	private double resteafaire;
	private double estimationinit;
	private double oldraf;
	private double estimation;
	
	java.sql.Date debut;
	java.sql.Date fin;
	int delai;

	
	private boolean exporter;
	private  DataRefLibelleProperties complexite;
	
	
	/**
	 * @param dt
	 * @param projetName
	 * @param foncName
	 * @param start
	 * @param heure_debut
	 * @param stop
	 * @param heure_fin
	 * @param delai
	 */
	public DataInfoSuiviProjet( String projetName, String foncName,int idComplexite,  java.sql.Date debut, java.sql.Date fin,
			int delai,boolean archive,double  charge, double estimationinitiale,double oldraf, double  resteafaire) {
		super();
		this.projetName = projetName;
		this.foncName = foncName;
		
		this.estimationinit = estimationinitiale;
		this.oldraf = oldraf;
		this.estimation = this.estimationinit + this.oldraf;
		this.charge = charge;
		this.resteafaire = resteafaire;
		this.fin=fin;
		this.debut=debut;
		this.delai=delai;
		
		
		if (this.complexite == null)
		this.complexite = new DataRefLibelleProperties(idComplexite, "TREF_COMPLEXITE");

		this.dt= new Date(System.currentTimeMillis());
	}
	/**
	 * 
	 */

	/**
	 * @return the dt
	 */
	public Date getDt() {
		return dt;
	}

	/**
	 * @param dt the dt to set
	 */
	public void setDt(Date dt) {
		this.dt = dt;
	}

	/**
	 * @return the debut
	 */
	public String getDebut() {
		if (debut == null) return " ";
		return debut.toString();
	}
	

	/**
	 * @return the fin
	 */
	public String getFin() {
		if (fin == null) return " ";
		return fin.toString();
	}
	/**
	 * @return the projetName
	 */
	public String getProjetName() {
		return projetName;
	}

	/**
	 * @param projetName the projetName to set
	 */
	public void setProjetName(String projetName) {
		this.projetName = projetName;
	}

	/**
	 * @return the foncName
	 */
	public String getFoncName() {
		return foncName;
	}

	/**
	 * @param foncName the foncName to set
	 */
	public void setFoncName(String foncName) {
		this.foncName = foncName;
	}
 
	/**
	 * @return the charge
	 */
	public double getCharge() {
		return getConvertUnitHeureEnJour(charge);
	}

	/**
	 * @param charge the charge to set
	 */
	public void setCharge(double charge) {
		this.charge = setConvertUnitJourEnHeure(charge);
	}

	/**
	 * @return the resteafaire
	 */
	public double getResteafaire() {
		if (debut == null) return getEstimation();
		return getConvertUnitHeureEnJour(resteafaire);
	}

	/**
	 * @param resteafaire the resteafaire to set
	 */
	public void setResteafaire(double resteafaire) {
		this.resteafaire = setConvertUnitJourEnHeure(resteafaire);
	}

	/**
	 * @return the libComplexite
	 */
	public String getLibComplexite() {
		return complexite.getLibelle();
	}



	/**
	 * @return the exporter
	 */
	public boolean isExporter() {
		return exporter;
	}

	/**
	 * @param exporter the exporter to set
	 */
	public void setExporter(boolean exporter) {
		this.exporter = exporter;
	}

	/**
	 * @return
	 */
	public double getEstimation() {
		
		return getConvertUnitHeureEnJour(this.estimation);
	}

	/**
	 * @return
	 */
	public String  getFinEstimmee() {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * @return
	 */
	public int getDelai() {

		
		return this.delai;
	}

	/**
	 * @param currentinfoProjet 
	 * @return
	 */
	public double getMarge(DataInfoProjet currentinfoProjet) {

		double marge=0d;
		double somme = (estimation + resteafaire);
		
		double coeff = currentinfoProjet.getIdcoeff()+1;
		
		coeff = coeff*5d/100d;
		marge = somme * coeff;
//		System.out.println(" idco "+currentinfoProjet.getIdcoeff()+"  coeff="+Double.toString(coeff) + " total="+somme+" marge="+marge);
		
		
		return getConvertUnitHeureEnJour(marge);
	}



	
	
}