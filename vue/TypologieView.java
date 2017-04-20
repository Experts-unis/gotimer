/**
 * 
 */
package vue;




import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;



import javax.swing.border.TitledBorder;


import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import model.DataInfoTypologie;

import model.ListModelTypo;


import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.util.ResourceBundle;



/**
 * @author test
 *
 */
public class TypologieView extends DialogBase {
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("typologieview");
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final String TYPOLOGIEVIEW = "TypologieView";
	private JTextField txtNomTypo;
	private JList<DataInfoTypologie> listTypo;
	
	
	private ListModelTypo listModelTypo;
	private JRadioButton rdbtnSolo;
	
	private DataInfoTypologie elementSelection;
	private JRadioButton rdbtnPersonnel;
	private JButton btnEnregistrer;
	private JRadioButton rdbtnEnquipe;
	private JRadioButton rdbtnProfessionnel;
	private JMenuItem mntmSupprimer;

	/**
	 * 
	 */
	public TypologieView() {
		super(null,TRADUCTION.getString("TypologieView.title.text"),true,440,508);
		
		//setBounds(0,0,440,508); 
		setResizable(false);
		
		this.setName("TypologieView"); 
		
		initCompoments();
		elementSelection=null;
		
		
		
	}

	/**
	 * 
	 */
	public void initCompoments() {
		getContentPane().setLayout(null);
		
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBounds(10, 349, 417, 68);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		
		initList();
		initSolo(contentPanel);
		initPerso(contentPanel);
		//initMotsCls(contentPanel);
		initButton();
	
	
	}

	/**
	 * @param contentPanel
	 */
// POUR UNE PROCHAINE VERSION	
//	public void initMotsCls(JPanel contentPanel) {
//		
//		JButton btnMotsCls = new JButton("Mots cl\u00E9s :");
//		btnMotsCls.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		btnMotsCls.setBounds(12, 57, 116, 23);
//		contentPanel.add(btnMotsCls);
//		
//		JTextArea textArea = new JTextArea();
//		textArea.setBorder(new TitledBorder(null, "Liste des mots cl\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		textArea.setRows(20);
//		textArea.setColumns(10);
//		textArea.setBounds(10, 91, 389, 134);
//		contentPanel.add(textArea);
//	}

	/**
	 * @param contentPanel
	 */
	public void initPerso(JPanel contentPanel) {
		JPanel panelType = new JPanel();
		panelType.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelType.setBounds(12, 11, 218, 35);
		contentPanel.add(panelType);
		panelType.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		ButtonGroup bg2 = new ButtonGroup();
		
		rdbtnProfessionnel = new JRadioButton(TRADUCTION.getString("TypologieView.rdbtnProfessionnel.text")); //$NON-NLS-1$
		panelType.add(rdbtnProfessionnel);
		
		 rdbtnPersonnel = new JRadioButton(TRADUCTION.getString("TypologieView.rdbtnPersonnel.text")); //$NON-NLS-1$
		panelType.add(rdbtnPersonnel);
		bg2.add(rdbtnPersonnel);
		bg2.add(rdbtnProfessionnel);
	}

	/**
	 * @param contentPanel
	 */
	public void initSolo(JPanel contentPanel) {
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(240, 11, 159, 35);
		contentPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		ButtonGroup bg1 = new ButtonGroup();
		rdbtnSolo = new JRadioButton(TRADUCTION.getString("TypologieView.rdbtnSolo.text")); //$NON-NLS-1$
		panel.add(rdbtnSolo);
		
		
		rdbtnEnquipe = new JRadioButton(TRADUCTION.getString("TypologieView.rdbtnEnquipe.text")); //$NON-NLS-1$
		panel.add(rdbtnEnquipe);
		
		bg1.add(rdbtnSolo);
		bg1.add( rdbtnEnquipe);
		
	}

	/**
	 * 
	 */
	public void initButton() {
		JPanel panelButton = new JPanel();
		panelButton.setBounds(10, 428, 417, 45);
		getContentPane().add(panelButton);
		panelButton.setLayout(null);
		
		btnEnregistrer = new JButton(TRADUCTION.getString("TypologieView.btnEnregistrer.text")); //$NON-NLS-1$
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnregistrer();
			}
		});
		btnEnregistrer.setBounds(198, 11, 118, 23);
		btnEnregistrer.setEnabled(false);
		panelButton.add(btnEnregistrer);
		
		JButton btnRetour = new JButton(TRADUCTION.getString("TypologieView.btnRetour.text")); //$NON-NLS-1$
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnRetour.setBounds(326, 11, 89, 23);
		panelButton.add(btnRetour);
		
		JButton btnAjouter = new JButton(TRADUCTION.getString("TypologieView.btnAjouter.text")); //$NON-NLS-1$
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionNouvelle();
			}

		
		});
		btnAjouter.setBounds(10, 11, 89, 23);
		panelButton.add(btnAjouter);
		

	}

	private void actionNouvelle() {
		
		txtNomTypo.setText("Nouvelle");
		rdbtnSolo.setSelected(true);
		rdbtnPersonnel.setSelected(false);
		elementSelection = null;
		btnEnregistrer.setEnabled(true);
		
	}
	
	/**
	 * 
	 */
	protected void actionEnregistrer() {
		if (elementSelection!=null){
			
			elementSelection.setName(txtNomTypo.getText());
			elementSelection.setSolo(rdbtnSolo.isSelected());
			elementSelection.setPerso(rdbtnPersonnel.isSelected());
			
		
			try {
				listModelTypo.modifElement(listTypo, elementSelection);
			} catch (ExceptionTraitementSQL e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			elementSelection = new DataInfoTypologie(-1, txtNomTypo.getText(), rdbtnSolo.isSelected(), rdbtnPersonnel.isSelected());
			try {
				listModelTypo.addTypo(elementSelection);
			} catch (ExceptionTraitementSQL e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		txtNomTypo.setText("");
		rdbtnSolo.setSelected(true);
		rdbtnPersonnel.setSelected(false);
		elementSelection = null;
		btnEnregistrer.setEnabled(false);
		
	}
	protected void actionSelectionTypo() {
		
		int index = listTypo.getSelectedIndex();
		if (listTypo.getSelectedIndex() !=-1){
			 elementSelection = listModelTypo.getElementAt(index);
			txtNomTypo.setText(elementSelection.getName());
			
			if (elementSelection.isSolo())
				rdbtnSolo.setSelected(true);
			else
				rdbtnEnquipe.setSelected(true);
			
			if (elementSelection.isPerso())
				rdbtnPersonnel.setSelected(true);
			else
				rdbtnProfessionnel.setSelected(true);
			btnEnregistrer.setEnabled(true);
			mntmSupprimer.setEnabled(true);
		}

	}
	/**
	 * 
	 */
	protected void actionSupprimer() {
		int index = listTypo.getSelectedIndex();
		if (listTypo.getSelectedIndex() !=-1){
		
			try {
				listModelTypo.supprimer(listTypo, elementSelection,index);
			} catch (ExceptionTraitementSQL e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mntmSupprimer.setEnabled(false);
			txtNomTypo.setText("");
			rdbtnSolo.setSelected(true);
			rdbtnPersonnel.setSelected(false);
			elementSelection = null;
			btnEnregistrer.setEnabled(false);
		}
		
	}
	/**
	 * 
	 */
	private void initList() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 417, 264);
		getContentPane().add(scrollPane);
		
		// Création du model
		try {
			listModelTypo = new ListModelTypo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		listTypo = new JList<DataInfoTypologie>(listModelTypo);
		listTypo.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				actionSelectionTypo();
			}
		});
		listTypo .setBorder(new TitledBorder(null, TRADUCTION.getString("TypologieView.listTypo.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$
		scrollPane.setViewportView(listTypo );
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(listTypo, popupMenu);
		
		mntmSupprimer = new JMenuItem(TRADUCTION.getString("TypologieView.mntmSupprimer.text")); //$NON-NLS-1$
		mntmSupprimer.setEnabled(false);
		mntmSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSupprimer();
			}
		});
		popupMenu.add(mntmSupprimer);
		
		
		JLabel lblNom = new JLabel(TRADUCTION.getString("TypologieView.lblNom.text")); //$NON-NLS-1$
		lblNom.setBounds(10, 286, 389, 14);
		getContentPane().add(lblNom);
		
		txtNomTypo = new JTextField();
		txtNomTypo.setBounds(10, 311, 417, 20);
		getContentPane().add(txtNomTypo);
		txtNomTypo.setColumns(10);
	}


	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
