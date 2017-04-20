/**
 * 
 */
package vue;


import javax.swing.JLabel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.awt.event.ActionEvent;

/**
 * @author test
 *
 */
public class AProposView extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param w
	 * @param h
	 */
	public AProposView() {
		super(null, "A Propos", true, 450, 250);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel lblTimer = new JLabel("<html><body><h1>Timer : Outil d'aide \u00E0 la gestion du temps.</h1><br />\r\nVersion : 1.00 <br />\r\nAuteur : AGD<br />\r\nSite : <a href=\"http://www.anniegarciadutaitre.com/timer\"> www.anniegarciadutaitre.com</a></body></html>");
		lblTimer.setBounds(10, 11, 384, 156);
		getContentPane().add(lblTimer);
		
		
		
		lblTimer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				String plainText = "http://www.anniegarciadutaitre.com";
				try {
                    Desktop.getDesktop().browse(new URI(plainText));
                } catch (URISyntaxException ex) {
                    
                } catch (IOException ex) {
                    
                }
				
			}
		});
		

		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnRetour.setBounds(305, 178, 89, 23);
		getContentPane().add(btnRetour);
		
	}
}
