/**
 * 
 */
package controleur;

/**
 * @author test
 *
 */
public class Pair <P>{

	private String key;
	private P value;



	/**
	 * @param key
	 * @param value
	 */
	public Pair(String key, P value) {
		super();
		this.key = key;
		this.value = value;
	}



	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}



	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}



	/**
	 * @return the value
	 */
	public P getValue() {
		return value;
	}



	/**
	 * @param value the value to set
	 */
	public void setValue(P value) {
		this.value = value;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (String) value ;
	}

}
