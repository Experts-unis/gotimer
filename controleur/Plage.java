/**
 * 
 */
package controleur;

/**
 * @author test
 *
 */
public class Plage {

	private long bornInf;
	private long bornSup;
	/**
	 * @param bornInf
	 * @param bornSup
	 */
	public Plage(long bornInf, long bornSup) {
		super();
		this.bornInf = bornInf;
		this.bornSup = bornSup;
	}

	/**
	 * @param bornInf the bornInf to set
	 */
	public void setBornInf(long bornInf) {
		this.bornInf = bornInf;
	}

	/**
	 * @param bornSup the bornSup to set
	 */
	public void setBornSup(long bornSup) {
		this.bornSup = bornSup;
	}

	public boolean isIn(long value){
		
		if (value>=bornInf && value <= bornSup) return true;
		
		return false;
			
	}
	public boolean isOut(long value){
		if (value < bornInf || value > bornSup) return true;
		return false;
	}

	public boolean isBefor(long value){
		if (value < bornInf ) return true;
		return false;
	}

	public boolean isAfter(long value){
		if (value > bornSup) return true;
		return false;
	}
	
	public boolean isPlageIn(long bornInf, long bornSup) {
	
		if (isIn(bornInf) && isIn(bornSup)) return true;
		
		return false;
	}
	
	public boolean isPlageOut(long bornInf, long bornSup) {
		
		if (isBefor(bornInf) && isBefor(bornSup)) return true;
		if (isAfter(bornInf) && isAfter(bornSup)) return true;
		
		return false;
	}

	public Plage getIntersection(long bornInf, long bornSup) {
		
		long bornInfIntersec = bornInf;
		long bornSupIntersec = bornSup;

		if (isPlageOut(bornInf, bornSup)) return null;
		
		if (isBefor(bornInf)) bornInfIntersec=this.bornInf;
		if (isAfter(bornSup)) bornInfIntersec=this.bornSup;
		
		return new Plage(bornInfIntersec,bornSupIntersec);
	}
		
}
