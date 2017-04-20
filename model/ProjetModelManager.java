/**
 * 
 */
package model;

import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class ProjetModelManager extends ModelManager{

	/**
	 * @throws SQLException 
	 * 
	 */
	public ProjetModelManager() throws Exception {
		super();
		
	}
	
	/**
	 * Charge les données dans la liste des projets
	 * @param listModelProjet
	 * @param listModelFonction
	 * @throws ExceptionTraitementSQL 
	 */
	public void loadProjetsInView(ListModelProjet listModelProjet) throws ExceptionTraitementSQL {
		log.traceEntry("loadProjetsInView(ListModelProjet listModelProjet)");
		Vector <DataInfoProjet> lesProjets = tProjets.getList();
		copyVectorInListModel(listModelProjet, lesProjets);
		log.traceExit("loadProjetsInView()");	
	}
	
	public void loadProjetsArchiveInView(ListModelProjet listModelProjet) throws ExceptionTraitementSQL {
		log.traceEntry("loadProjetsArchiveInView(ListModelProjet listModelProjet)");
		Vector <DataInfoProjet> lesProjets = tProjets.getListArchive();
		copyVectorInListModel(listModelProjet, lesProjets);
		log.traceExit("loadProjetsArchiveInView()");	
	}

	/**
	 * @param infoProjetSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void stopArchiveProjet(DataInfoProjet infoProjetSelection) throws ExceptionTraitementSQL {
		log.traceEntry("stopArchiveProjet()");
		tProjets.updateArchive(infoProjetSelection.getId(),false);
		tFonctions.archiveFonctionDuProjet(infoProjetSelection.getId(), false);
		log.traceExit("stopArchiveProjet()");	
		
	}
	/**
	 * @param infoFonctionSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void stopArchiveFonction(DataInfoFonction fonc) throws ExceptionTraitementSQL {

		log.traceEntry("stopArchiveFonction()");
		tProjets.updateArchive(fonc.getIdProj(),false);
		tFonctions.archiveFonction(fonc.getId(), false);
		log.traceExit("stopArchiveFonction()");	

	}
	
	public void loadProjetsReportInView(ListModelProjet listModelProjet) throws ExceptionTraitementSQL {
		log.traceEntry("loadProjetsReportInView(ListModelProjet listModelProjet)");
		Vector <DataInfoProjet> lesProjets = tProjets.getListReport();
		copyVectorInListModel(listModelProjet, lesProjets);
		log.traceExit("loadProjetsReportInView()");	
	}

	/**
	 * @param infoProjetSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void stopReportProjet(DataInfoProjet elProj) throws ExceptionTraitementSQL {
		
		log.traceEntry("stopReportProjet()");
		tProjets.updateReport(elProj.getId(),false);
		tFonctions.updateReportProjet(elProj.getId(), false);
		log.traceExit("stopReportProjet()");	
		
	}
	/**
	 * @param infoFonctionSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void stopReportFonction(DataInfoFonction elFonc) throws ExceptionTraitementSQL {
		log.traceEntry("stopReportProjet()");
		tProjets.updateReport(elFonc.getIdProj(),false);
		tFonctions.updateReport(elFonc.getId(), false);
		log.traceExit("stopReportProjet()");	
		
	}
	
	/**
	 * Charge les données dans la liste des fonctions
	 * @param listModelFonction
	 * @param infoProjetSelection
	 * @throws ExceptionTraitementSQL 
	 */
	public void loadFonctionsInView(ListModelFonction listModelFonction, DataInfoProjet infoProjetSelection) throws ExceptionTraitementSQL {
		log.traceEntry("loadFonctionsInView(ListModelFonction listModelFonction, DataInfoProjet infoProjetSelection)");
		listModelFonction.clear();
		Vector<DataInfoFonction> liste=tFonctions.getList(infoProjetSelection.getId());
		// Copier la liste dans le model
		for(int index=0;index<liste.size();index++)
			listModelFonction.addElement(liste.get(index));
		
		log.traceExit("loadFonctionsInView()");	
	}
	
	public void loadFonctionsArchiveInView(ListModelFonction listModelFonction, DataInfoProjet infoProjetSelection) throws ExceptionTraitementSQL {
		log.traceEntry("loadFonctionsArchiveInView(ListModelFonction listModelFonction, DataInfoProjet infoProjetSelection)");
		listModelFonction.clear();
		Vector<DataInfoFonction> liste=tFonctions.getListArchive(infoProjetSelection.getId());
		// Copier la liste dans le model
		for(int index=0;index<liste.size();index++)
			listModelFonction.addElement(liste.get(index));
		
		log.traceExit("loadFonctionsArchiveInView()");	
	}
	
	public void loadFonctionsReportInView(ListModelFonction listModelFonction, DataInfoProjet infoProjetSelection) throws ExceptionTraitementSQL {
		log.traceEntry("loadFonctionsRepportInView(ListModelFonction listModelFonction, DataInfoProjet infoProjetSelection)");
		listModelFonction.clear();
		Vector<DataInfoFonction> liste=tFonctions.getListReport(infoProjetSelection.getId());
		// Copier la liste dans le model
		for(int index=0;index<liste.size();index++)
			listModelFonction.addElement(liste.get(index));
		
		log.traceExit("loadFonctionsArchiveInView()");	
	}
	/**
	 * @param elementAt
	 * @throws ExceptionTraitementSQL 
	 */
	public void deleteFonction(DataInfoFonction elementAt) throws ExceptionTraitementSQL {
		log.traceEntry("deleteFonction(DataInfoFonction elementAt)");
		// System.out.println("deleteFonction : INDEX : "+Integer.toString(elementAt.getIndex())+ " ID = "+Integer.toString(elementAt.getId()));
		// Supprimer les mesures des temps de la fonction  
		tTime.deleteFonction(elementAt.getId());
		
		// Suppression de la fonction  
		tFonctions.delete(elementAt.getId());
		log.traceExit("deleteFonction()");	
	}
	
	/**
	 * @param listProjet
	 * @param selectedIndex
	 * @throws ExceptionTraitementSQL 
	 */
	public void deleteProjet(DataInfoProjet infoProjet) throws ExceptionTraitementSQL {
		log.traceEntry("deleteProjet(DataInfoProjet infoProjet="+infoProjet+")");
		// Supprimer les mesures des temps du projet
		tTime.deleteProjet(infoProjet.getId());
		
		// Suppression des fonctions du projets
		tFonctions.deleteProjet(infoProjet.getId());
		
		// Suppression du projet dans la bases de données
		tProjets.delete(infoProjet.getId());
		log.traceExit("deleteProjet(DataInfoProjet infoProjet)");	
	}
	
	/**
	 * @param oldindex
	 * @param b
	 * @throws ExceptionTraitementSQL 
	 */
	public void setProjetByDefault(int id, boolean b) throws ExceptionTraitementSQL {
		log.traceEntry("setProjetByDefault(int id="+id+", boolean b="+b+")") ;
		tProjets.updateSelectionByDefault(id, b);
		log.traceExit("setProjetByDefault");	
	}
	
	
	
	/**
	 * @param idCoeff 
	 * @param dateSouhaitee 
	 * @param text
	 * @param element
	 * @param index
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateProjet(String libelle, int idpareto, int idobjectif,int idtypologie,int idmotivation,int idCoeff, Date dateSouhaitee, DataInfoProjet element, int index) throws ExceptionTraitementSQL {
		log.traceEntry("updateProjet(String libelle="+libelle+", DataInfoProjet  element, int index="+index+")");
		element.setIndex(index);
		element.setLibelle(libelle);
		element.setIdpareto(idpareto);
		element.setIdobjectif(idobjectif);
		element.setIdtypologie(idtypologie);
		element.setIdmotivation(idmotivation);
		element.setIdcoeff(idCoeff);
		element.setDateSouhaitee(dateSouhaitee);
		
		java.sql.Date dt = new java.sql.Date (dateSouhaitee.getTime());
		
		tProjets.updateProjet(element.getId(),libelle,idpareto, idobjectif,idtypologie,idmotivation,idCoeff,dt);
		log.traceExit("updateProjet()");	
		
	}
	
	/**
	 * @param text
	 * @param index
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public DataInfoProjet addProjet(String nameProjet, int idpareto, int idobjectif,int idtypologie,int idmotivation, int idcoeff, java.util.Date date, int index) throws ExceptionTraitementSQL {
		log.traceEntry("DataInfoProjet addProjet(String nameProjet, int index)") ;
		java.sql.Date dt = new java.sql.Date (date.getTime());
		int id = tProjets.add(nameProjet, idpareto, idobjectif,idtypologie,idmotivation,idcoeff,dt);
		DataInfoProjet element = new DataInfoProjet(id,nameProjet,false,idpareto, idobjectif, idtypologie, idmotivation, idcoeff, date,index);
		log.traceExit("addProjet() (id="+id+",nameProjet="+nameProjet+",false,index="+index+")");		
		return element;
	}
	/**
	 * @param text
	 * @param element
	 * @param index
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateFonction(String libelle,int idcomplexite, double estimation, int idpareto, int ideisenhower,int idmotivation, DataInfoFonction element, int index) throws ExceptionTraitementSQL {
		log.traceEntry("updateFonction(String libelle="+libelle+", DataInfoFonction element, int index="+index+")");
		element.setIndex(index);
		element.setLibelle(libelle);
		element.setIdpareto(idpareto);
		element.setIdComplexite(idcomplexite);
		element.setEstimation(estimation);
		element.setIdeisenhower(ideisenhower);
		element.setIdmotivation(idmotivation);
		tFonctions.updateFonction(element.getId(),libelle,idcomplexite, estimation, idpareto, ideisenhower,idmotivation);
		log.traceExit("updateFonction()");	
	}
	
	/**
	 * @param nameFonc
	 * @param infoProjetSelection
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public DataInfoFonction addFoncProjet(String nameFonc, DataInfoProjet infoProjetSelection,int idpareto, int ideisenhower,int idcomplexite,double estimation,int idmotivation,int index) throws ExceptionTraitementSQL {

		log.traceEntry("addFoncProjet(String nameFonc="+nameFonc+", DataInfoProjet infoProjetSelection,int idpareto="+idpareto+", int ideisenhower="+ideisenhower+",int idcomplexite="+idcomplexite+",double estimation="+estimation+",int index="+index+")") ;
		
		int idprojet = infoProjetSelection.getId();
		//TODO Modifier estimation en fonction de l'unité
		int id = tFonctions.add(nameFonc,idprojet, idpareto, ideisenhower, idcomplexite, estimation,idmotivation);
		DataInfoFonction element = new DataInfoFonction(id,nameFonc,idprojet,false, idpareto,ideisenhower, idcomplexite, estimation, idmotivation,index);
		
		log.traceExit("addFoncProjet() (id="+id+",nameFonc="+nameFonc+",idprojet="+idprojet+",false,index="+index+")");	
		return element;
				
	}

	/**
	 * @param id
	 * @param b
	 */
	public void setFonctionByDefault(int id, boolean b) throws ExceptionTraitementSQL{
		log.traceEntry("setFonctionByDefault(int id="+id+", boolean b="+b+")") ;
		tFonctions.updateSelectionByDefault(id, b);
		log.traceExit("setFonctionByDefault");	
		
	}

	
	/**
	 * @param id
	 */
	public void archiveProjet(int id) throws ExceptionTraitementSQL {
		log.traceEntry("archiveProjet(int id="+id+")");
		
		tProjets.updateArchive(id,true);
	
		// Archivage les mesures des temps de la fonction  
		tTime.archiveTempsDuProjet(id,true);
		
		// Archivage des  fonctions du projet  
		tFonctions.archiveFonctionDuProjet(id,true);
		log.traceExit("archiveFonction()");
		
		
	}
	/**
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void reportProjet(int id) throws ExceptionTraitementSQL {
		log.traceEntry("reportProjet(int id="+id+")");
		// Archivage les mesures des temps de la fonction  
		tProjets.updateReport(id,true);
		tFonctions.updateReportProjet(id,true);

		log.traceExit("reportProjet()");
		
	}
	/**
	 * @param idfonc
	 * @throws ExceptionTraitementSQL 
	 */
	public void archiveFonction(int idfonc) throws ExceptionTraitementSQL {
		log.traceEntry("archiveFonction(int idfonc="+idfonc+")");
		// Archivage les mesures des temps de la fonction  
		tTime.archiveFonction(idfonc,true);
		
		// Archivage de la fonction  
		tFonctions.archiveFonction(idfonc,true);
		log.traceExit("archiveFonction()");
	}



	/**
	 * @param idfonc
	 * @throws ExceptionTraitementSQL 
	 */
	public void reportFonction(int idfonc) throws ExceptionTraitementSQL {
		log.traceEntry("reportFonction(int idfonc="+idfonc+")");
		// Archivage les mesures des temps de la fonction  
	//	tTime.reportFonction(idfonc,true);
		
		// Archivage de la fonction  
		tFonctions.updateReport(idfonc,true);
		tFonctions.updateReport(idfonc,true);
		log.traceExit("reportFonction()");
	}

	/**
	 * @param cmbTypologie
	 */
	public void loadTypologieInCombobox(JComboBox<DataInfoTypologie> cmbTypologie) throws ExceptionTraitementSQL {
		Vector<DataInfoTypologie> liste = tTypologie.getList();
		
		copyVectorInComboBoxTypo(cmbTypologie,liste);
		
	}
	protected int copyVectorInComboBoxTypo(JComboBox<DataInfoTypologie>  cmb, Vector<DataInfoTypologie> liste) {
		log.traceEntry("copyVectorInComboBoxTypo()");
		int selected=-1;
		
		if (cmb.getItemCount()!=0) cmb.removeAllItems();
		for (int i=0;i<liste.size();i++){
			
			cmb.addItem(liste.get(i));	
		}
		log.traceExit("copyVectorInComboBoxFonction() selected="+selected+" size="+cmb.getItemCount());	
		return selected;
		
	}











	

}
