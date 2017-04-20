package model;

public class DataRefLibelle {

	protected int id;
	protected String libelle;
	protected int index;
	/**
	 * @param id
	 * @param libelle
	 */
	public DataRefLibelle(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.setIndex(-1);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//return "DataRefLibelle [id=" + id + ", libelle=" + libelle + "]";
		return libelle;
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
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void clear() {
		this.setId(-1);
		this.setLibelle("");
		this.setIndex(-1);
		
	}

	
	

}
