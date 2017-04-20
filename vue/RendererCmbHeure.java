/**
 * 
 */
package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import controleur.PlageIndex;

/**
 * @author test
 *
 */
public class RendererCmbHeure extends JLabel implements ListCellRenderer<Object> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<PlageIndex> listePlageHoraire;
	Font normal;
	/**
	 * 
	 */
	public RendererCmbHeure(Vector<PlageIndex> listePlageHoraire) {

		this.listePlageHoraire = listePlageHoraire;
		
	}



/* (non-Javadoc)
 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
 */
@SuppressWarnings("rawtypes")
public Component getListCellRendererComponent(JList list, Object value,
		int index, boolean isSelected, boolean cellHasFocus) {
	
	
	setText(value.toString()); 
	  
	if (isSelected) { 
		setBackground(list.getSelectionBackground()); 
		setForeground(list.getSelectionForeground()); 
	} 
	else { 
		setBackground(list.getBackground()); 
		setForeground(list.getForeground()); 
	} 
	
	boolean inPlage = false;
	
	for ( PlageIndex plageHoraire : this.listePlageHoraire ) {
		
		if (plageHoraire.isIN(index) || plageHoraire.isJuxtoposee(index))
		{
			inPlage = true;
			break;
		}
	}
	if (inPlage){
		setBackground(Color.LIGHT_GRAY);
		setFont(new Font("Tahoma", Font.ITALIC, 11));
		setForeground(Color.RED);
	}else{
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setForeground(Color.BLACK);
	}
//
//	// Ici, il faut tester si on veut changer la couleur et changer par la  
//	// couleur de notre choix 
//	if (taCondition == true){ 
//		setBackground(couleur); 
//	} 
//
//	setEnabled(list.isEnabled()); 
//	setFont(list.getFont()); 
 	setOpaque(true); 
	

	
	return this; 
	
} 

}
