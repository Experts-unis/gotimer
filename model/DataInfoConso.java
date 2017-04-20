/**
 * 
 */
package model;

import java.util.Date;

/**
 * @author test
 *
 */
public class DataInfoConso {
	
	int idprojet;
	int idFonc;
	String libelle; 
	Date debut; 
	Date fin; 
	double delai; // en jour 
	int  nbrchronos;
	double charge;
	

	
	/**
	 * @param idprojet
	 * @param idFonc
	 * @param libelle
	 * @param debut
	 * @param fin
	 * @param delai
	 * @param nbrchronos
	 * @param charge
	 */
	public DataInfoConso(int idprojet, int idFonc, String libelle, java.sql.Date debut,
			java.sql.Date  fin, double delai,  double charge,int nbrchronos) {
		super();
		this.idprojet = idprojet;
		this.idFonc = idFonc;
		this.libelle = libelle;
		if (debut != null)
		this.debut = new Date (debut.getTime());
		if (fin != null)
		this.fin = new Date (fin.getTime());
		this.delai = delai;
		this.nbrchronos = nbrchronos;
		this.charge = charge;
	}
	/**
	 * @return the idFonc
	 */
	public int getIdFonc() {
		return idFonc;
	}
	/**
	 * @param idFonc the idFonc to set
	 */
	public void setIdFonc(int idFonc) {
		this.idFonc = idFonc;
	}
	/**
	 * @return the charge
	 */
	public double getCharge() {
		return charge;
	}
	/**
	 * @param charge the charge to set
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}
	/**
	 * @return the idprojet
	 */
	public int getIdprojet() {
		return idprojet;
	}
	/**
	 * @param idprojet the idprojet to set
	 */
	public void setIdprojet(int idprojet) {
		this.idprojet = idprojet;
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
	 * @return the debut
	 */
	public Date getDebut() {
		return debut;
	}
	/**
	 * @param debut the debut to set
	 */
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	/**
	 * @return the fin
	 */
	public Date getFin() {
		return fin;
	}
	/**
	 * @param fin the fin to set
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}
	/**
	 * @return the delai
	 */
	public double getDelai() {
		return delai;
	}
	/**
	 * @param delai the delai to set
	 */
	public void setDelai(double delai) {
		this.delai = delai;
	}
	/**
	 * @return the nbrchronos
	 */
	public int getNbrchronos() {
		return nbrchronos;
	}
	/**
	 * @param nbrchronos the nbrchronos to set
	 */
	public void setNbrchronos(int nbrchronos) {
		this.nbrchronos = nbrchronos;
	}
	
	

}
