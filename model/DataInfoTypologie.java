/**
 * 
 */
package model;

/**
 * @author test
 *
 */
public class DataInfoTypologie {

	int id;
	String name;
	boolean solo;
	boolean perso;

	/**
	 * @param id
	 * @param name
	 * @param solo
	 * @param perso
	 */
	public DataInfoTypologie(int id, String name, boolean solo, boolean perso) {
		super();
		this.id = id;
		this.name = name;
		this.solo = solo;
		this.perso = perso;
		
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the solo
	 */
	public boolean isSolo() {
		return solo;
	}
	/**
	 * @param solo the solo to set
	 */
	public void setSolo(boolean solo) {
		this.solo = solo;
	}
	/**
	 * @return the perso
	 */
	public boolean isPerso() {
		return perso;
	}
	/**
	 * @param perso the perso to set
	 */
	public void setPerso(boolean perso) {
		this.perso = perso;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return name ;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return name;
	//	return "[id=" + id + ", name=" + name + ", solo="
	//			+ solo + ", perso=" + perso + "]";
	}
	
	
}
