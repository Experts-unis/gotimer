/**
 * 
 */
package model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import controleur.ControlManager;
import controleur.Principale;

/**
 * @author test
 *
 */
public class DataInfoVacation {

	Date debut;
	Date fin;
	double duree;
	int id;
	private SimpleDateFormat sdf;

	/**
	 * @param debut
	 * @param fin
	 * @param duree
	 * @param id
	 */
	public DataInfoVacation(String debut, String  fin, int id) {
		super();
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			this.debut = sdf.parse(debut);
			this.fin = sdf.parse(fin);
		} catch (ParseException e) {
			// TODO Message d'erreur utilisateur
			e.printStackTrace();
		}
		
		ControlManager ctrl = Principale.ctrlManager;
		
		this.duree = 1+ ctrl.calculEcart(this.debut.getTime(), this.fin.getTime()) / 24;
		this.id = id; 
	}
	/**
	 * @return the debut
	 */
	public String getDebut() {
		return sdf.format(debut);
	}
	/**
	 * @return
	 */
	public Timestamp getSQLDebut() {
		return new java.sql.Timestamp(debut.getTime());
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
	public String getFin() {
		return sdf.format(fin);
	}
	/**
	 * @return
	 */
	public Timestamp getSQLFin() {
		return new java.sql.Timestamp(fin.getTime());
	}
	/**
	 * @param fin the fin to set
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}
	/**
	 * @return the duree
	 */
	public int getDuree() {
		return (int)duree;
	}
	/**
	 * @param duree the duree to set
	 */
	public void setDuree(double duree) {
		this.duree = duree;
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

}
