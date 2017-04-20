/**
 * 
 */
package model;

import java.util.Date;



/**
 * @author test
 *
 */
public class DataInfoEstimation extends DataInfo {

	private int id; // reference de l'estimation en cours
	private int idfonc=-1;
	private String libelle="";

	private double estimationInit=0d;
	private int idproj=-1;
	private double estimation=0d;
	private double charge=0d;
	private double delai=0d;
	Date debut;
	private double resteafaire=0d;
	private double oldresteafaire=0d;
	
	private DataRefLibelleProperties complexite;

	private DataRefLibelleProperties pareto;
	private DataRefLibelleProperties eisenhower;
	private DataRefLibelleProperties motivation;
	
	private Date dt;
	private double total;
	
	/**
	 * 
	 */
	public DataInfoEstimation() {
		
		pareto = new DataRefLibelleProperties(0,"TREF_PARETO");
		eisenhower = new DataRefLibelleProperties(0,"TREF_EISENHOWER");
		motivation = new DataRefLibelleProperties(0,"TREF_PLAISIR");
		complexite = new DataRefLibelleProperties(0,"TREF_COMPLEXITE");
	}



	/**
	 * @return the idPareto
	 */
	public int getIdPareto() {
		return pareto.getId();
	}



	/**
	 * @param idPareto the idPareto to set
	 */
	public void setIdPareto(int idPareto) {
		this.pareto.setId(idPareto);
	}
	

	/**
	 * @return
	 */
	public String getPareto() {
		
		return this.pareto.getLibelle();
	}



	/**
	 * @return the idEisenhower
	 */
	public int getIdEisenhower() {
		return eisenhower.getId();
	}



	/**
	 * @param idEisenhower the idEisenhower to set
	 */
	public void setIdEisenhower(int idEisenhower) {
		this.eisenhower.setId(idEisenhower);
	}

	/**
	 * @return
	 */
	public String getEisenhower() {
		
		return this.eisenhower.getLibelle();
	}



	/**
	 * @param value
	 */
	public void setIdMotivation(int idModivation) {
		this.motivation.setId(idModivation);
		
	}


	/**
	 * @return the idModivation
	 */
	public int getIdModivation() {
		return this.motivation.getId();
	}

 

	/**
	 * @return
	 */
	public String getMotivation() {
		
		return this.motivation.getLibelle();
	}

	/**
	 * @return the idfonc
	 */
	public int getIdfonc() {
		return idfonc;
	}

	/**
	 * @param idfonc the idfonc to set
	 */
	public void setIdfonc(int idfonc) {
		this.idfonc = idfonc;
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
	 * @return the estimationInit
	 */
	public double getEstimationInit() {
		
		return getConvertUnitHeureEnJour(estimationInit);
	}
	public String getEstimationInitStr() {
		
		
		return String.format("%5.2f", getEstimationInit());
			
	}

	/**
	 * @param estimationInit the estimationInit to set
	 */
	public void setEstimationInit(double estimationInit) {
		
		this.estimationInit =setConvertUnitJourEnHeure(estimationInit);
		calculEstimation();
	}

	/**
	 * @return the complexite
	 */
	public String getComplexite() {
		return complexite.getLibelle();
	}

	/**
	 * @param value the complexite to set
	 */
	public void setComplexite(DataRefLibelle value) {
		this.complexite.setId(value.getId());  		
	}

	/**
	 * @return the idComplexite
	 */
	public int getIdComplexite() {
		return this.complexite.getId();
	}

	/**
	 * @param idComplexite the idComplexite to set
	 */
	public void setIdComplexite(int idComplexite) {
		this.complexite.setId(idComplexite);
	}

	/**
	 * @return the idproj
	 */
	public int getIdproj() {
		return idproj;
	}

	/**
	 * @param idproj the idproj to set
	 */
	public void setIdproj(int idproj) {
		this.idproj = idproj;
	}

	/**
	 * @return the estimation
	 */
	public double getEstimation() {
				
		return getConvertUnitHeureEnJour(estimation);
	}
	public String getEstimationStr() {
		return String.format("%5.2f", getEstimation());
	}

	/**
	 * @param estimation the estimation to set
	 */
	public void setEstimation(Double estimation) {
		this.estimation = setConvertUnitJourEnHeure(estimation);
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
	 * @return the depense
	 */
	public double getCharge() {
		return getConvertUnitHeureEnJour(charge);
	}

	/**
	 * @param depense the depense to set
	 */
	public void setCharge(Double charge) {
		this.charge = setConvertUnitJourEnHeure(charge);
	}

	/**
	 * @return the resteafaire
	 */
	public double getResteafaire() {
		return getConvertUnitHeureEnJour(resteafaire) ;
	}

	/**
	 * @param resteafaire the resteafaire to set
	 */
	public void setResteafaire(double resteafaire) {
	
		this.resteafaire = setConvertUnitJourEnHeure(resteafaire) ;
		calculEstimation();
	}
	

	/**
	 * @return the oldresteafaire
	 */
	public double getOldresteafaire() {
		return oldresteafaire;
	}



	/**
	 * @param oldresteafaire the oldresteafaire to set
	 */
	public void setOldresteafaire(double oldresteafaire) {
		this.oldresteafaire = oldresteafaire;
	}



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
	 * 
	 */
	public void calculEstimation() {
		this.estimation = this.estimationInit + this.oldresteafaire + this.resteafaire - this.charge ;
		
	}



	/**
	 * @param double1
	 */
	public void setDelai(double delai) {
		this.delai = delai;
		
	}



	/**
	 * @return
	 */
	public double getDelai() {
		
		return this.delai;
	}



	/**
	 * @param dataInfoConso
	 */
	public void setInfoConso(DataInfoConso elem) {
		
		this.charge =elem.getCharge();
//		this.delai = elem.getDelai();
//		this.debut = elem.getDebut();
		//TODO c
		
	}



	/**
	 * @return
	 */
	public double getTotal() {
		total = estimationInit + oldresteafaire + resteafaire;
		return getConvertUnitHeureEnJour(total);
	}



	













}
