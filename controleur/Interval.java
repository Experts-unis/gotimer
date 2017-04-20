/**
 * 
 */
package controleur;


import java.util.Date;

/**
 * @author AGD
 *
 */
public class Interval {
	
	Date min;
	Date max;
	/**
	 * @param min
	 * @param max
	 */
	public Interval(Date min, Date max) {
		super();
		this.min = min;
		this.max = max;
		

		
		
	}
	
	/**
	 * @return the min
	 */
	public Date getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Date min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Date getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Date max) {
		this.max = max;
	}

	public boolean contient (Interval autre) {
		
		// La borne min de l'autre est dans l'interval
		
		if (this.min.getTime() <= autre.getMin().getTime() && this.max.getTime() > autre.getMin().getTime()) return true;
		return false;
		
	}
	
	public boolean contientTotal (Interval autre) {
		
		// La borne min de l'autre est dans l'interval
		
		if (contient(autre) && autre.getMax().getTime()< this.max.getTime() ) return true;
		return false;
		
	}
	
	

}
