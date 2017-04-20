/**
 * 
 */
package vue;


import javax.swing.JComboBox;

import controleur.MyConfiguration;
import controleur.Pair;
import controleur.Principale;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

/**
 * @author test
 *
 */
public class ParamPreferenceView extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("paramview");
	private JComboBox<Pair<String>> cmbLangue;
	private MyConfiguration config;

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param w
	 * @param h
	 */
	public ParamPreferenceView() {
		super(null, "Préférence", true, 240, 225);

		// Chargement des données de configuration
		config = Principale.config;
		
		
		
		getContentPane().setLayout(null);
		//setBounds(0, 0, 240, 140);
		
		JLabel label = new JLabel("Langue");
		label.setBounds(10, 9, 212, 14);
		getContentPane().add(label);

		DefaultComboBoxModel<Pair<String>> modelLangue = new DefaultComboBoxModel<Pair<String>>();
		  
		 modelLangue.addElement( new Pair<String>("en",TRADUCTION.getString("langue.en")));
		 modelLangue.addElement( new Pair<String>("fr",TRADUCTION.getString("langue.fr")));
		 
		cmbLangue = new JComboBox<Pair<String>>();
		cmbLangue.setBounds(10, 26, 93, 20);
		cmbLangue.setModel(modelLangue);
		getContentPane().add(cmbLangue);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 57, 202, 2);
		getContentPane().add(separator);
		
		JLabel lblFormatDesDates = new JLabel("Format des dates");
		lblFormatDesDates.setBounds(10, 64, 93, 14);
		getContentPane().add(lblFormatDesDates);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("jj/mm/aaaa");
		rdbtnNewRadioButton.setToolTipText("la repr\u00E9sentation textuelle d'une valeur de date et d'heure.");
		rdbtnNewRadioButton.setBounds(10, 81, 109, 23);
		getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("yyyy-MM-dd");
		rdbtnNewRadioButton_1.setBounds(10, 108, 109, 23);
		getContentPane().add(rdbtnNewRadioButton_1);
		
		JButton btnNewButton = new JButton("Enregistrer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnEnregistrer();
			}
		});
		
		JSeparator separator2 = new JSeparator();
		separator2.setBounds(10, 138, 202, 2);
		getContentPane().add(separator2);
		btnNewButton.setBounds(10, 151, 99, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnAnnuler.setBounds(123, 151, 89, 23);
		getContentPane().add(btnAnnuler);
		
		
		 
		 
		 
		 String str = config.getProperty("langue.preference");
			if (str=="fr")
				cmbLangue.setSelectedIndex(0);
			else
				cmbLangue.setSelectedIndex(1);
	}

	/**
	 * 
	 */
	protected void actionBtnEnregistrer() {
		int index;
		index = cmbLangue.getSelectedIndex();
		Pair<String> lang = cmbLangue.getItemAt(index);
		config.setProperty("langue.preference",lang.getKey());
		
		try {
			config.save();
			JOptionPane.showMessageDialog(this, TRADUCTION.getString("msg.enreg.ok") , TRADUCTION.getString("msg.enreg.titre.ok"), JOptionPane.INFORMATION_MESSAGE);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(this, TRADUCTION.getString("msg.enreg.err")+"\n " + e.getMessage(), TRADUCTION.getString("msg.enreg.titre.err"), JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
