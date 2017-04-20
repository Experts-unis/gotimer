/**
 * 
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import model.DataInfoFonction;
import model.DataRefLibelle;
import model.ComplexiteModelManager;

import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.ResourceBundle;



/**
 * @author test
 *
 */
public class DialogEstimationView extends DialogBase {
	
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("dcomplexite"); 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String COMPLEXITETVIEW = "complexiteView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DialogEstimationView.class );  
	private final JPanel contentPanel = new JPanel();
	private JComboBox<DataRefLibelle> cmbComplexite;
	private ControlManager controlManager;
	private ComplexiteModelManager manager;
	private DataInfoFonction element;
	private JTextField txtEstimation;
	private JComboBox<DataRefLibelle> cmbUnite;
	private JComboBox<DataRefLibelle> cmbPareto;
	private JComboBox<DataRefLibelle> cmbEisenhower;
	private JComboBox<DataRefLibelle> cmbPlaisirFonction;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogEstimationView dialog = new DialogEstimationView(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogEstimationView(JDialog parent) {
		super(parent, TRADUCTION.getString("DialogComplexiteView.title.text"), true, 340, 230);
		
		log.traceEntry("DialogComplexiteView(JDialog parent)");
		//setBounds(0, 0, 340, 230);
		
		initComponents();		
		
		initComplexite();

		initEstimation();
		
		initButton();
		
		this.controlManager=Principale.ctrlManager;
		
		this.setName("DialogComplexiteView"); //$NON-NLS-1$
		
		try {
			manager = (ComplexiteModelManager) controlManager.getModelManager(this.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.loadComplexiteInCombo(cmbComplexite);
		manager.loadUniteInCombo(cmbUnite);
		manager.loadParetoInCombo(cmbPareto);
		manager.loadEisenhowerInCombo(cmbEisenhower);
		manager.loadPlaisirInCombo(cmbPlaisirFonction);
		
		JLabel lblMotivation = new JLabel(TRADUCTION.getString("DialogComplexiteView.lblMotivation.text")); //$NON-NLS-1$
		lblMotivation.setBounds(30, 163, 75, 14);
		contentPanel.add(lblMotivation);


		
		log.traceExit("DialogComplexiteView()");
	}

	/**
	 * 
	 */
	public void initEstimation() {
		JLabel lblEstimation = new JLabel(TRADUCTION.getString("DialogComplexiteView.lblEstimation.text")); //$NON-NLS-1$
		lblEstimation.setBounds(30, 59, 75, 14);
		contentPanel.add(lblEstimation);
		
		cmbUnite = new JComboBox<DataRefLibelle>();
		cmbUnite.setBounds(208, 50, 75, 24);
		contentPanel.add(cmbUnite);
		// TODO Gestion des unités. Si on change la valeur des unités doit changer et on doit toujours enregistrer la valeur en heure
		
		txtEstimation = new JTextField();
		txtEstimation.setBounds(111, 50, 86, 23);
		contentPanel.add(txtEstimation);
		txtEstimation.setColumns(10);
		
		JLabel lblPareto = new JLabel(TRADUCTION.getString("DialogComplexiteView.lblPareto.text")); //$NON-NLS-1$
		lblPareto.setBounds(30, 92, 46, 14);
		contentPanel.add(lblPareto);
		
		cmbPareto = new JComboBox<DataRefLibelle>();
		cmbPareto.setToolTipText(TRADUCTION.getString("DialogComplexiteView.cmbPareto.toolTipText")); //$NON-NLS-1$
		cmbPareto.setBounds(111, 88, 48, 20);
		contentPanel.add(cmbPareto);
		
		JLabel lblEisenhower = new JLabel(TRADUCTION.getString("DialogComplexiteView.lblEisenhower.text")); //$NON-NLS-1$
		lblEisenhower.setBounds(30, 123, 71, 14);
		contentPanel.add(lblEisenhower);
		
		cmbEisenhower = new JComboBox<DataRefLibelle>();
		cmbEisenhower.setToolTipText(TRADUCTION.getString("DialogComplexiteView.cmbEisenhower.toolTipText")); //$NON-NLS-1$
		cmbEisenhower.setBounds(111, 123, 172, 20);
		contentPanel.add(cmbEisenhower);
		
		cmbPlaisirFonction = new JComboBox<DataRefLibelle>();
		cmbPlaisirFonction.setBounds(111, 160, 172, 20);
		contentPanel.add(cmbPlaisirFonction);

		

		
		
	}

	/**
	 * 
	 */
	public void initComplexite() {

		JLabel lblComplexit = new JLabel(TRADUCTION.getString("DialogComplexiteView.lblComplexit.text")); //$NON-NLS-1$
		lblComplexit.setBounds(30, 18, 84, 14);
		contentPanel.add(lblComplexit);

		
		cmbComplexite = new JComboBox<DataRefLibelle>();
		//cmbComplexite.setModel(new DefaultComboBoxModel<String>(new String[] {"Faible", "Moyenne", "Forte"}));
		cmbComplexite.setBounds(111, 15, 172, 20);
		contentPanel.add(cmbComplexite);
		
	}

	/**
	 * 
	 */
	public void initButton() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
			JButton btnValider = new JButton(TRADUCTION.getString("DialogComplexiteView.btnValider.text")); //$NON-NLS-1$
			btnValider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actionEnregistrer();
				}
			});
			btnValider.setActionCommand(TRADUCTION.getString("DialogComplexiteView.btnValider.actionCommand")); //$NON-NLS-1$
			buttonPane.add(btnValider);
			getRootPane().setDefaultButton(btnValider);
		
		
			JButton btnAnnuler = new JButton(TRADUCTION.getString("DialogComplexiteView.btnAnnuler.text")); //$NON-NLS-1$
			btnAnnuler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionQuitter();
				}
			});
			buttonPane.add(btnAnnuler);
	}

	/**
	 * 
	 */
	protected void actionEnregistrer() {
		
		log.traceEntry("actionEnregistrer()");
		int sel = cmbComplexite.getSelectedIndex();
		String lib = cmbComplexite.getItemAt(sel).getLibelle();
		
		element.setInfoComplexite(cmbComplexite.getItemAt(sel).getId(),lib);
		//spJours ,spDemiJour
		
		int unite=cmbUnite.getSelectedIndex();

		Double val;
		
		
		//Demande au controleur de calculer l'estimation en heure
		if (unite==0){ // Selection de l'unité jour
			// Un jour vaux X heures de charge en fonction du paramétrage
			// le user pouvant travailler moins de 8 heures par jour
			val = controlManager.calculEstimationHeure(txtEstimation.getText());
			
			log.trace(" Estimation en heure ="+val);			
		}else{
			val = (double) (Integer.valueOf(txtEstimation.getText())) ;
		}
		
		element.setEstimation(val);
		element.setIdpareto(cmbPareto.getItemAt(cmbPareto.getSelectedIndex()).getId());
		element.setIdeisenhower(cmbEisenhower.getItemAt(cmbEisenhower.getSelectedIndex()).getId());
		element.setIdmotivation(cmbPlaisirFonction.getItemAt(cmbPlaisirFonction.getSelectedIndex()).getId());
		try {
			manager.modifEstimation(element);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(COMPLEXITETVIEW,"err01.projets","Impossible de mettre à jour la complexité du projet", e);
		}
		setVisible(false);
		log.traceExit("actionEnregistrer()");
		
	}

	
	public void initComponents()  {
		// Centre la fenêtre
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (dimScreen.width / 2) - 340/2 ;
		int y = (dimScreen.height / 2) - 230/2 -100; 
		Point centreEcran = new Point(x,y);
		this.setLocation(centreEcran);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				actionQuitter();
			}
		});
		
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
	}

	/**
	 * @param elementAt
	 */
	public void showDialog(DataInfoFonction element) {
		
		this.element=element;
		this.setTitle(element.getLibelle()+" ("+element.getId()+")");
		initFormulaire();
		super.showDialog();
		
	}

	/**
	 * 
	 */
	private void initFormulaire() {
		
		cmbComplexite.setSelectedIndex(element.getIdComplexite());
		txtEstimation.setText(element.getEstimation().toString());
		cmbUnite.setSelectedIndex(1);
		cmbPareto.setSelectedIndex(element.getIdpareto());
		cmbEisenhower.setSelectedIndex(element.getIdeisenhower());
		
		
	}
}
