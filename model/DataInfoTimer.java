/**
 * 
 */
package model;

import java.util.Date;
import java.util.ResourceBundle;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author test
 *
 */

// Exemple de convertion entre java.util.Date et java.sql.Date
//java.util.Date utilStartDate = jDateChooserStart.getDate();
//java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());


public class DataInfoTimer  extends DataInfo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public int id;
	public Date dt;
	public int idproj;
	public int idfonc;
	public String libProjet;
	public String libFonction;
	
	public boolean pro;
	public double horsplage;
	public double dansplage;
	public double charge;
	public Date start;
	public Date stop;
	private int row;
	
	
	
	


	
	
	/**
	 * @param id - dans la table TTIME de la DB
	 * @param dt
	 * @param idfonc
	 * @param delai
	 * @param charge
	 * @param start
	 * @param stop
	 */

	public DataInfoTimer()
	{
		super();
		Calendar c;
		
		c = Calendar.getInstance();
		this.id = -1;
		this.dt = c.getTime();
		// info sur la fonction
		this.idfonc = -1;
		this.libProjet="";
		this.libFonction="";
		this.idproj= -1;
		this.pro = false;
		
		// info sur le temps écoulé
		this.horsplage = 0d;
		this.dansplage = 0d;
		this.charge = 0d;
		this.start = null;
		this.stop = null;
	}



	/**
	 * @param id
	 * @param dt
	 * @param idproj
	 * @param idfonc
	 * @param libProjet
	 * @param libFonction
	 * @param pro
	 * @param horsplage
	 * @param dansplage
	 * @param charge
	 * @param start
	 * @param stop
	 */
	public DataInfoTimer(int id, java.sql.Date dt, int idproj, String libProjet,
			int idfonc, String libFonction, boolean pro, double horsplage,
			double dansplage, double charge, Timestamp start, Timestamp stop) {
		super();
		this.id = id;
		this.dt = dt;
		
		// info sur projet et  fonction
		this.idproj = idproj;
		this.idfonc = idfonc;
		this.libProjet = libProjet;
		this.libFonction = libFonction;
		this.pro = pro;
		
		// info sur le temps écoulé
		this.horsplage = horsplage;
		this.dansplage = dansplage;
		this.charge = charge;
		this.start = start;
		this.stop = stop;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public String getIdStr() {
		return String.format("%6d", id);
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	public String getDtStr() {
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		
		DateFormat df = DateFormat.getDateInstance();
		
		return df.format(this.dt);
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
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	public String getStartStr() {
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		//df.setNumberFormat(arg0);
		return df.format(start);
		
	}
	public String getStartHeureStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		//DateFormat df= DateFormat.getDateTimeInstance();
		//df.h
		// String.format("%2dj,%s",3,sdf.format(start));
		return sdf.format(start);
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the stop
	 */
	public Date getStop() {
		return stop;
	}

	/**
	 * @param stop the stop to set
	 */
	public void setStop(Date stop) {
		this.stop = stop;
	}
	public String getStopStr() {
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		//df.setNumberFormat(arg0);
		return df.format(stop);
		
	}
	public String getStopHeureStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		return sdf.format(stop);
	}
	
	


	/**
	 * @return the charge
	 */
	public Double getCharge() {
		return getConvertUnitHeureEnJour(charge);
		//return charge;
	}


	/**
	 * @param charge the charge to set
	 */
	public void setCharge(Double charge) {
		this.charge = setConvertUnitJourEnHeure(charge);
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
	 * @return the libFonction
	 */
	public String getLibFonction() {
		return libFonction;
	}

	/**
	 * @param libFonction the libFonction to set
	 */
	public void setLibFonction(String libFonction) {
		this.libFonction = libFonction;
	}

	/**
	 * @param traduction 
	 * @return
	 */
	public String getStartInfo(ResourceBundle traduction) {
		
		
		String str = "";
		
		//TODO récupérer le libellé du projet
		
		str += traduction.getString("Projet") + " : " + libProjet + "\n";
		str += traduction.getString("Fonctionnalite")  + " : " + libFonction + "\n";
		
		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/YYYY HH:mm");
		
		str += traduction.getString("Debut")  +" : "+sdf.format(start) +"\n";	
		if (this.stop != null)
		str += traduction.getString("Fin")  +" : "+sdf.format(stop) +"\n";
		
		return str;
	}
	
	/**
	 * @param selectedItem
	 */
	public void setInfoDataFonction(DataInfoFonction selectedItem) {
		
		// mettre à jour les informations sur la fonction et le projet
		setIdfonc(selectedItem.getId());
		setIdproj(selectedItem.getIdProj());
		setLibFonction(selectedItem.getLibelle());
		
	}

	/**
	 * @return
	 */
	public Timestamp getSQLDt() {
		 
		 return new java.sql.Timestamp(dt.getTime());
	}

	/**
	 * @return
	 */
	public Timestamp getSQLStop() {
		return new java.sql.Timestamp(stop.getTime());
	}
	
	/**
	 * @return
	 */
	public Timestamp getSQLStart() {
		return new java.sql.Timestamp(start.getTime());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataInfoTime [\ndt=" + dt + ",\n "+ ",\n start=" + start + ",\n stop=" + stop + "\n]";
	}


	/**
	 * @param rowSel
	 */
	public void setRow(int rowSel) {
		row = rowSel;
		
	}
	public int getRow() {
		return row; 
		
	}



	/**
	 * @return the pro
	 */
	public boolean isPro() {
		return pro;
	}



	/**
	 * @param pro the pro to set
	 */
	public void setPro(boolean pro) {
		this.pro = pro;
	}



	/**
	 * @return the horsplage
	 */
	public double getHorsplage() {
		
		return getConvertUnitHeureEnJour(horsplage);
	}



	/**
	 * @param horsplage the horsplage to set
	 */
	public void setHorsplage(double horsplage) {
		this.horsplage = setConvertUnitJourEnHeure(horsplage);
	}



	/**
	 * @return the dansplage
	 */
	public double getDansplage() {
		
		return getConvertUnitHeureEnJour(dansplage);
	}



	/**
	 * @param dansplage the dansplage to set
	 */
	public void setDansplage(double dansplage) {
		
		this.dansplage = setConvertUnitJourEnHeure(dansplage);
	}


}
