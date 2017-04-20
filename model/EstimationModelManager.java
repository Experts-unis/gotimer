/**
 * 
 */
package model;


import java.util.Vector;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class EstimationModelManager  extends ComplexiteModelManager {

	
	private int idprojet;
	/**
	 * 
	 */
	public EstimationModelManager() throws Exception {
		
		
	}

	/**
	 * @param idprojet 
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector<DataInfoEstimation> loadDataEstimation(int idprojet) throws ExceptionTraitementSQL {
		
		log.traceEntry("loadDataEstimation(int idprojet="+idprojet+")");
		this.idprojet = idprojet;
		Vector<DataInfoEstimation> data = new Vector<DataInfoEstimation>();
		
		if (idprojet !=-1){
			
 			data = tEstimations.getList(idprojet);
			
			Vector<DataInfoConso> conso;
			conso = tTime.getDataConso(this.idprojet);
			
			for (DataInfoEstimation element :data){
				element.setCharge(0d);
				
				for (DataInfoConso consommation : conso)
					if (element.getIdfonc()==consommation.getIdFonc()){
						element.setCharge(consommation.getCharge());
						element.setDelai(consommation.getDelai());
						element.setLibelle(consommation.getLibelle());
						element.calculEstimation();
						break;
					}
				
				
			}
			
		}
		log.traceExit("loadDataEstimation(int idprojet)");
		//= new Vector<DataInfoEstimation>();
		return data;
	}


	
	public void saveDataEstimation(Vector<DataInfoEstimation> data) throws ExceptionTraitementSQL {
		
		
		// Enregistre les données initiales
		for(DataInfoEstimation element : data){
			element.setUnite(1);
			
			//TODO Enregistre les nouvelles informations pareto etc
			tFonctions.updateComplexite(
					element.getIdfonc(), 
					element.getIdComplexite(),
					element.getEstimationInit(), element.getIdPareto(), element.getIdEisenhower(), element.getIdModivation());
			
			
			
			//Enregistre les données de reste à faire
			
			if (element.getId() != 0)
				tEstimations.update(element.getId(),element.getResteafaire());
			else{
				element.setId(tEstimations.add(element.getIdproj(),element.getIdfonc(),element.getDelai(),element.getCharge(),element.getResteafaire()));
			}
			
		}
		
	}
}
