/**
 * 
 */
package model;

import controleur.ControlManager;
import controleur.Principale;

/**
 * @author test
 *
 */
public class DataInfoFonction {
	
	private int index;
	private int id;
	private String libFonc;
	private String libProjet;
	private int idProj;
	private boolean selected;
	private int idComplexite;
	private String complexite;
	private Double estimation=0d;
	int idpareto;
	int ideisenhower;
	int idmotivation;
	private boolean affichageSimple;

	/**
	 * @param id
	 * @param lib
	 * @param idProj
	 * @param selected
	 */
//	public DataInfoFonction(int id, String lib,int idProj,boolean selected) {
//
//		this.id = id;
//		this.libFonc = lib;
//		this.idProj  = idProj;
//		this.selected=selected;
//		this.index = -1;
//	}
	/**
	 * @param id
	 * @param lib
	 * @param idProj
	 * @param selected
	 * @param index
	 */
	public DataInfoFonction(int id, String lib,int idProj,boolean selected,int idpareto, int ideisenhower,int idComplexite, double estimation,int idmotivation, int index) {

		this.id = id;
		this.libFonc = lib;
		this.idProj  = idProj;
		this.selected=selected;
		this.index = index;
		
		this.idpareto=idpareto; 
		this.ideisenhower=ideisenhower;
		this.idComplexite=idComplexite; 
		this.estimation=estimation;
		this.idmotivation=idmotivation;
		setModeAffichageSimple();
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
	 * @return the idComplexite
	 */
	public int getIdComplexite() {
		return idComplexite;
	}
	/**
	 * @param idComplexite the idComplexite to set
	 */
	public void setIdComplexite(int idComplexite) {
		this.idComplexite = idComplexite;
	}
	/**
	 * @return the complexite
	 */
	public String getComplexite() {
		return complexite;
	}
	/**
	 * @param complexite the complexite to set
	 */
	public void setComplexite(String complexite) {
		this.complexite = complexite;
	}
	
	public void setInfoComplexite(int id,String complexite)
	{
		this.complexite= complexite;
		this.idComplexite = id;
	}
	
	
	/**
	 * @return the estimation
	 */
	public Double getEstimation() {
		return estimation;
	}
	/**
	 * @param estimation the estimation to set
	 */
	public void setEstimation(Double estimation) {
		this.estimation = estimation;
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
	 * @return the lib
	 */
	public String getLibelle() {
		return libFonc;
	}
	/**
	 * @param lib the lib to set
	 */
	public void setLibelle(String lib) {
		this.libFonc = lib;
	}
	/**
	 * @return the libProjet
	 */
	public String getLibProjet() {
		return libProjet;
	}
	/**
	 * @param libProjet the libProjet to set
	 */
	public void setLibProjet(String libProjet) {
		this.libProjet = libProjet;
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
	 * @return the ideisenhower
	 */
	public int getIdeisenhower() {
		return ideisenhower;
	}

	public String  getEisenhower() {
		ControlManager theCtrl = Principale.ctrlManager;
		ModelManager theManager = theCtrl.getModelManager();
		
		return theManager.getEisenhower(ideisenhower);
		
	}
	/**
	 * @param ideisenhower the ideisenhower to set
	 */
	public void setIdeisenhower(int ideisenhower) {
		this.ideisenhower = ideisenhower;
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
		
		return String.format("%s %s, %s - %s, %2.00f ", getLibelle() ,strEtoile, getPareto(), getEisenhower(),estimation);
		
		//return "InfoFonc [id=" + id + ", lib=" + lib + ", idProj=" + idProj + "]";
	}
	/**
	 * @return the idProj
	 */
	public int getIdProj() {
		return idProj;
	}
	/**
	 * @param idProj the idProj to set
	 */
	public void setIdProj(int idProj) {
		this.idProj = idProj;
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


	
	/**
	 * @return the affichageSimple
	 */
	public boolean isAffichageSimple() {
		return affichageSimple;
	}




	/**
	 * @param bAffiche
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

}
