/**
 * 
 */
package model;

import controleur.Principale;

/**
 * @author test
 *
 */
public class DataInfo {

	private int unite; // 0=jour 1=heure
	/**
	 * 
	 */
	public DataInfo() {
		unite=1; // Par defaut on exprime l'unité en heure
	}
	
	public double getConvertUnitHeureEnJour(double val){
		if (unite==0) //exprimer la val en nbr de jour
			return val / (double)Principale.nombreDHeureTravailleeDanslaJournee;
		return val;
	}
	public double setConvertUnitJourEnHeure(double val){
		if (unite==0) //unite est en jour => exprimer la val en nbr d'heure
			return val * (double)Principale.nombreDHeureTravailleeDanslaJournee;
		return val;
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
		

	}

}
