/**
 * 
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import controleur.ControlManager;
import controleur.Principale;
//import java.util.Timer;

/**
 * @author test
 *
 */
public class DataInfoPeriodeActivite {

	String debut;
	String fin;
	int indexDebut;
	int indexFin;
	
	int id;
	private Date dateDebut;
	private Date dateFin;
	double duree;
	private Date dateBase;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DataInfoPeriodeActivite.class ); 



	/**
	 * @param debut
	 * @param fin
	 * @param id
	 */
	public DataInfoPeriodeActivite(String debut, String  fin, int id) {
		super();

		log.traceEntry("DataInfoPeriodeActivite(String debut="+debut+", String  fin="+fin+", int id="+id+")");
		
		
		//Date date = new Date(System.currentTimeMillis());
		
		dateBase = new Date(System.currentTimeMillis());
		
		SimpleDateFormat sdf= new SimpleDateFormat("YYYY-MM-dd");
		
		this.debut =  debut.trim();
		this.fin = fin.trim();
		
		String str = sdf.format(dateBase) +" "+ this.debut  + ":00";
		
		SimpleDateFormat sdf2= new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		try {
			this.dateDebut = sdf2.parse(str);
		} catch (ParseException e) {
			// TODO msg err utilisateur
			e.printStackTrace();
		}
		
		str = sdf.format(dateBase) + " "+this.fin + ":00";
		
		
		try {
			this.dateFin = sdf2.parse(str);
		} catch (ParseException e) {
			//  msg err utilisateur
			e.printStackTrace();
		}
		
		this.id = id;
		this.duree = calculDuree();
		log.traceExit("DataInfoPeriodeActivite() duree ="+this.duree);
	}

	/**
	 * @return the debut
	 */
	public String getDebut() {
		return this.debut;
	}

	/**
	 * @param debut the debut to set
	 */
	public void setDebut(String debut) {
		this.debut = debut;
	}

	/**
	 * @return the fin
	 */
	public String getFin() {
		return fin;
	}

	/**
	 * @param fin the fin to set
	 */
	public void setFin(String fin) {
		this.fin = fin;
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

	public double calculDuree() {
		
		log.traceEntry("DataInfoPeriodeActivite.calculDuree()");
		
		ControlManager ctrl = Principale.ctrlManager;
		double res=ctrl.calculEcart( dateDebut.getTime(), dateFin.getTime());
		if (res<=0) res +=24;
		
		log.traceExit("DataInfoPeriodeActivite.calculDuree()");
		return		res;
	}

	/**
	 * @return
	 */
	public String getDuree() {
		
		log.traceEntry("DataInfoPeriodeActivite.getDuree()");

		return		String.format("%6.2f", calculDuree());
		
		
	}
	public double getDureeSQL() {
		
		log.traceEntry("DataInfoPeriodeActivite.getDureeSQL () "+this.duree);

		return this.duree;
		
		
	}

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the dateBase
	 */
	public Date getDateBase() {
		return dateBase;
	}

	/**
	 * @param dateBase the dateBase to set
	 */
	public void setDateBase(Date dateBase) {
		this.dateBase = dateBase;
	}

	/**
	 * @param debut2
	 * @param heure
	 * @return
	 */
	private int getIndex(String value, Vector<String> heure) {
		
		return heure.indexOf(value);
	}

	public void setIndex(Vector<String> heure){
		
		indexDebut = getIndex(this.debut, heure);
		indexFin = getIndex(this.fin, heure);
	}

	/**
	 * @return the indexDebut
	 */
	public int getIndexDebut() {
		return indexDebut;
	}

	/**
	 * @param indexDebut the indexDebut to set
	 */
	public void setIndexDebut(int indexDebut) {
		this.indexDebut = indexDebut;
	}

	/**
	 * @return the indexFin
	 */
	public int getIndexFin() {
		return indexFin;
	}

	/**
	 * @param indexFin the indexFin to set
	 */
	public void setIndexFin(int indexFin) {
		this.indexFin = indexFin;
	}
	
	


	
}
