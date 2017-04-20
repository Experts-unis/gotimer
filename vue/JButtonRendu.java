/**
 * 
 */
package vue;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author test
 *
 */
public class JButtonRendu extends JButton implements TableCellRenderer {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {


	    //On retourne notre bouton

	    return this;

	  }

	}