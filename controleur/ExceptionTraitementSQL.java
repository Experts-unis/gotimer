/**
 * 
 */
package controleur;

import java.sql.SQLException;

/**
 * @author test
 *
 */
public class ExceptionTraitementSQL extends SQLException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String query;
	/**
	 * 
	 */


	/**
	 * @param cause
	 */
	public ExceptionTraitementSQL(Throwable cause,String query) {
		super(cause);
		this.query = query;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}



}
