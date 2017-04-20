/**
 * 
 */
package controleur;

/**
 * @author test
 *
 */
public class PlageIndex {

	int bornInf;
	int bornSup;
	/**
	 * @param bornInf
	 * @param bornSup
	 */
	public PlageIndex(int bornInf, int bornSup) {
		super();
		this.bornInf = bornInf;
		this.bornSup = bornSup;
		
		System.out.println("PlageIndex 23 CREATION PLAGE "+bornInf+", "+bornSup);
	}
	
	public boolean isIN(int index){
		
		if (index>= bornInf && index<=bornSup) return true;
		
		return false;
	}
	
	public boolean isJuxtoposee(int index){
		
		if (index== bornInf-1) return true;
		
		return false;
	}
	
	/**
	 * @return the bornInf
	 */
	public int getBornInf() {
		return bornInf;
	}
	/**
	 * @param bornInf the bornInf to set
	 */
	public void setBornInf(int bornInf) {
		this.bornInf = bornInf;
	}
	/**
	 * @return the bornSup
	 */
	public int getBornSup() {
		return bornSup;
	}
	/**
	 * @param bornSup the bornSup to set
	 */
	public void setBornSup(int bornSup) {
		this.bornSup = bornSup;
	}
	
	

}
