package controleur;
/**
 * 
 */

/**
 * @author test
 *
 */
public class ExceptionInitBase extends Exception {
	
	
	private String url; 
	private String user;

	/**
	 * 
	 */
	private static final long serialVersionUID = -304400874919096900L;

	
	/**
	 * @param cause
	 * @param user 
	 * @param url 
	 */
	public ExceptionInitBase(Throwable cause, String url, String user) {
		super(cause);
		this.url=url;
		this.user=user;
		
	}


	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	

}
