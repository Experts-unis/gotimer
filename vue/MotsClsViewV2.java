/**
 * 
 */
package vue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;

/**
 * @author test
 *
 */
public class MotsClsViewV2 extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MotsClsViewV2() {
		
		JButton btnRetour = new JButton("Retour");
		getContentPane().add(btnRetour, BorderLayout.SOUTH);
	}

}
