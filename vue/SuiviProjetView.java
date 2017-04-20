package vue;

import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.HeadlessException;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;

import model.DataInfoProjet;

import model.ModelManager;
import model.TableauModelSuiviProjet;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.logging.log4j.LogManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
//import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JTextField;

import java.util.ResourceBundle;

public class SuiviProjetView extends DialogBase {

	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("suiviprojetview");
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SUIVIPROJECTVIEW = "suiviProjectView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( SuiviProjetView.class ); 
	private final JPanel contentPanel = new JPanel();
	private JTable tableauRAF;
	private ControlManager controlManager;
	private JComboBox<DataInfoProjet> cmbProjet;
	private JComboBox<String> cmbUnite;
	private ModelManager managerModel;
	private boolean ecouter=false;
	private TableauModelSuiviProjet modelTableauRAF;
	private JTable tableauArchive;
	private JTextField txtEstimationTotale;
	private JTextField txtResteAFaireTotal;
	private TableauModelSuiviProjet modelTableauFait;
	private EstimationView fDialogEstimation;
	private JTextField txtChargeTotale;
	private JTextField txtDateAttenduPour;
	private JTextField txtDateEstimee;


	private DataInfoProjet currentinfoProjet;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SuiviProjetView dialog = new SuiviProjetView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SuiviProjetView() {
		super(null, "Suivi des projets", true, 1050, 725);
		log.traceEntry("SuiviProjetView()") ;
		
		ecouter=false;
		
		this.controlManager = Principale.ctrlManager;
		setName("SuiviProjetView");
		try {
			managerModel = controlManager.getModelManager(this.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		initComponents(managerModel);
		
		chargeListeProjet();
		

		
		ecouter=true;;
		actionChangeProjet();
		log.traceExit("SuiviProjetView()") ;	
	}

	/**
	 * @throws HeadlessException
	 */
	private void chargeListeProjet() throws HeadlessException {
		int indexSelected;
		ecouter=false;
		try {
			indexSelected = managerModel.loadProjetInCombo(this.cmbProjet,true );
			cmbProjet.setSelectedIndex(indexSelected);
		

		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(SUIVIPROJECTVIEW,"err01.projets","Impossible de charger les projets ", e);
		}
		ecouter=true;
	}

	/**
	 * 
	 */
	public void initButton() {
		log.traceEntry("void initButton()") ;
		

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 645, 1034, 35);
		contentPanel.add(buttonPane);

		JButton cancelButton = new JButton(TRADUCTION.getString("SuiviProjetView.cancelButton.text")); //$NON-NLS-1$
		cancelButton.setBounds(941, 5, 83, 23);
		cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		buttonPane.setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(211, 15, 250, 2);
		separator.setPreferredSize(new Dimension(250, 2));
		separator.setOrientation(SwingConstants.VERTICAL);
		buttonPane.add(separator);
		cancelButton.setActionCommand(TRADUCTION.getString("SuiviProjetView.cancelButton.actionCommand")); //$NON-NLS-1$
		buttonPane.add(cancelButton);


//		JButton btnMiseJour = new JButton(TRADUCTION.getString("SuiviProjetView.btnMiseJour.text")); //$NON-NLS-1$
//		btnMiseJour.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				actionMiseAJour();
//			}
//		});
//		btnMiseJour.setBounds(10, 5, 108, 23);
//		buttonPane.add(btnMiseJour);

		JButton btnEstimations = new JButton(TRADUCTION.getString("SuiviProjetView.btnEstimations.text")); //$NON-NLS-1$
		btnEstimations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnEstimation();

			}
		});
		btnEstimations.setBounds(10, 5, 101, 23);
		buttonPane.add(btnEstimations);

		log.traceExit("void initButton()") ;
	}

	/**
	 * 
	 */
	protected void actionBtnEstimation() {

		log.traceEntry("actionBtnEstimation()") ;
		
		if (fDialogEstimation == null){
			fDialogEstimation = new EstimationView();
		}
		
		fDialogEstimation.showDialog();
		log.traceExit("actionBtnEstimation()") ;		
	}
	/**
	 * 
	 */
//	protected void actionMiseAJour() {
//		
//		try {
//			modelTableauRAF.updateChargeDelai(cmbProjet.getItemAt(cmbProjet.getSelectedIndex()).getId());
//		} catch (ExceptionTraitementSQL e) {
//			MainView.showMsgError(SUIVIPROJECTVIEW,"err03.projets","Impossible de mettre à jour les informations sur les projets", e);
//		}
//	}

	/**
	 * 
	 */
	public void initFiltre() {
		log.traceEntry("initFiltre()") ;
		
		JPanel panelFiltre = new JPanel();
		panelFiltre.setBorder(new TitledBorder(null, TRADUCTION.getString("SuiviProjetView.panelFiltre.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$
		panelFiltre.setBounds(10, 11, 1012, 85);
		contentPanel.add(panelFiltre);
		panelFiltre.setLayout(null);
		
		cmbProjet = new JComboBox<DataInfoProjet>();
		cmbProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionChangeProjet();
			}
		});
		cmbProjet.setBounds(10, 54, 280, 20);
		panelFiltre.add(cmbProjet);
		
		JLabel lblProjet = new JLabel(TRADUCTION.getString("SuiviProjetView.lblProjet.text")); //$NON-NLS-1$
		lblProjet.setBounds(10, 29, 146, 14);
		panelFiltre.add(lblProjet);
	
		// TODO A Mettre dans la version 2
//		JCheckBox chckbxTous = new JCheckBox("Tout le projet");
//		chckbxTous.setBounds(897, 53, 109, 23);
//		panelFiltre.add(chckbxTous);
		
		cmbUnite = new JComboBox<String>();
		cmbUnite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChangeUnite();
			}
			
		});
		//TODO Internationnalisation de cmbunit
		cmbUnite.setModel(new DefaultComboBoxModel<String>(new String[] {"Jour(s)", "Heure(s)"}));
		cmbUnite.setBounds(891, 54, 111, 20);
		panelFiltre.add(cmbUnite);
		
		txtEstimationTotale = new JTextField();
		txtEstimationTotale.setBackground(new Color(255, 255, 255));
		txtEstimationTotale.setBounds(313, 54, 86, 20);
		panelFiltre.add(txtEstimationTotale);
		txtEstimationTotale.setEditable(false);
		txtEstimationTotale.setColumns(10);
		
		JLabel lblChargeTotale = new JLabel(TRADUCTION.getString("SuiviProjetView.lblChargeTotale.text")); //$NON-NLS-1$
		lblChargeTotale.setBounds(313, 29, 100, 14);
		panelFiltre.add(lblChargeTotale);
		
		JLabel lblResteFaire = new JLabel(TRADUCTION.getString("SuiviProjetView.lblResteFaire.text")); //$NON-NLS-1$
		lblResteFaire.setBounds(505, 29, 95, 14);
		panelFiltre.add(lblResteFaire);
		
		txtResteAFaireTotal = new JTextField();
		txtResteAFaireTotal.setBackground(new Color(255, 255, 255));
		txtResteAFaireTotal.setBounds(505, 54, 86, 20);
		panelFiltre.add(txtResteAFaireTotal);
		txtResteAFaireTotal.setEditable(false);
		txtResteAFaireTotal.setColumns(10);
		
		JLabel lblConsomm = new JLabel(TRADUCTION.getString("SuiviProjetView.lblConsomm.text")); //$NON-NLS-1$
		lblConsomm.setBounds(409, 29, 82, 14);
		panelFiltre.add(lblConsomm);
		
		txtChargeTotale = new JTextField();
		txtChargeTotale.setBackground(new Color(255, 255, 255));
		txtChargeTotale.setEditable(false);
		txtChargeTotale.setBounds(409, 54, 86, 20);
		panelFiltre.add(txtChargeTotale);
		txtChargeTotale.setColumns(10);
		
		JLabel lblDlai = new JLabel(TRADUCTION.getString("SuiviProjetView.lblDlai.text")); //$NON-NLS-1$
		lblDlai.setBounds(601, 29, 86, 14);
		panelFiltre.add(lblDlai);
		
		txtDateAttenduPour = new JTextField();
		txtDateAttenduPour.setBackground(new Color(255, 255, 255));
		txtDateAttenduPour.setEditable(false);
		txtDateAttenduPour.setBounds(601, 54, 86, 20);
		panelFiltre.add(txtDateAttenduPour);
		txtDateAttenduPour.setColumns(10);
		
		txtDateEstimee = new JTextField();
		txtDateEstimee.setBackground(new Color(255, 255, 255));
		txtDateEstimee.setEditable(false);
		txtDateEstimee.setBounds(697, 54, 86, 20);
		panelFiltre.add(txtDateEstimee);
		txtDateEstimee.setColumns(10);
		
		JLabel lblDateFinEstime = new JLabel(TRADUCTION.getString("SuiviProjetView.lblDateFinEstime.text")); //$NON-NLS-1$
		lblDateFinEstime.setBounds(697, 29, 111, 14);
		panelFiltre.add(lblDateFinEstime);
		log.traceExit("initFiltre()") ;
	}

	/**
	 * @param manager
	 */
	public void initTableau(ModelManager manager) {
		
		log.traceEntry("initTableau(ModelManager manager)") ;

		try {
			modelTableauRAF = new TableauModelSuiviProjet(manager,false);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(SUIVIPROJECTVIEW,"err02.projets","Impossible de charger les fonctions des projets ", e);
		}


		JScrollPane scrollPaneRAF = new JScrollPane();
		scrollPaneRAF.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), TRADUCTION.getString("SuiviProjetView.scrollPaneRAF.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))); //$NON-NLS-2$
		scrollPaneRAF.setAutoscrolls(true);
		scrollPaneRAF.setBounds(10, 107, 1012, 248);
		contentPanel.add(scrollPaneRAF);

		tableauRAF = new JTable(modelTableauRAF);
		scrollPaneRAF.setViewportView(tableauRAF);


		tableauRAF.getTableHeader().setBackground(Color.LIGHT_GRAY);
		tableauRAF.getTableHeader().setForeground(Color.BLACK);

		Dimension dim=tableauRAF.getTableHeader().getPreferredSize();
		dim.setSize(dim.width,70);
		tableauRAF.getTableHeader().setPreferredSize(dim);

		// Agrandir toutes les ligne du tableau
		tableauRAF.setRowHeight(20);
		
		
		
		final int FONCTION=0;
		final int COMPLEXITE=1;
		final int ESTIMATION=2;
		final int DATEDEBFIN=3;
		final int CHARGE=4;
		final int RESTEAFAIREDELAI=5;
		
		tableauRAF.getColumnModel().getColumn(FONCTION).setPreferredWidth(250);
		tableauRAF.getColumnModel().getColumn(FONCTION).setResizable(false);
		
		tableauRAF.getColumnModel().getColumn(COMPLEXITE).setPreferredWidth(150);
		tableauRAF.getColumnModel().getColumn(COMPLEXITE).setResizable(false);

		tableauRAF.setColumnSelectionAllowed(true);
		tableauRAF.setCellSelectionEnabled(true);
		tableauRAF.setBorder(null);
		
		
		try {
			modelTableauFait = new TableauModelSuiviProjet(manager,true);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(SUIVIPROJECTVIEW,"err02.projets","Impossible de charger les fonctions des projets ", e);
		}
		
		
		JScrollPane scrollPaneArchive = new JScrollPane();
		scrollPaneArchive.setBorder(new TitledBorder(null, TRADUCTION.getString("SuiviProjetView.scrollPaneArchive.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$
		scrollPaneArchive.setBounds(10, 366, 1012, 248);
		contentPanel.add(scrollPaneArchive);
		
		

		
		tableauArchive = new JTable(modelTableauFait);
		scrollPaneArchive.setViewportView(tableauArchive);

		tableauArchive.getTableHeader().setPreferredSize(dim);
		tableauArchive.getColumnModel().getColumn(FONCTION).setPreferredWidth(250);
		tableauArchive.getColumnModel().getColumn(FONCTION).setResizable(false);
		
		tableauArchive.getColumnModel().getColumn(COMPLEXITE).setPreferredWidth(150);
		tableauArchive.getColumnModel().getColumn(COMPLEXITE).setResizable(false);

		tableauArchive.setColumnSelectionAllowed(true);
		tableauArchive.setCellSelectionEnabled(true);
		tableauArchive.setBorder(null);
		
		
		DefaultTableCellRenderer rendererRight = new DefaultTableCellRenderer();
		rendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		
		DefaultTableCellRenderer rendererCenter = new DefaultTableCellRenderer();
		rendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
		
		tableauRAF.getColumnModel().getColumn(FONCTION).setCellRenderer(rendererRight);
		tableauArchive.getColumnModel().getColumn(FONCTION).setCellRenderer(rendererRight);
		
		tableauRAF.getColumnModel().getColumn(COMPLEXITE).setCellRenderer(rendererCenter);
		tableauArchive.getColumnModel().getColumn(COMPLEXITE).setCellRenderer(rendererCenter);
		
		tableauRAF.getColumnModel().getColumn(ESTIMATION).setCellRenderer(rendererCenter);
		tableauArchive.getColumnModel().getColumn(ESTIMATION).setCellRenderer(rendererCenter);
		
		tableauRAF.getColumnModel().getColumn(DATEDEBFIN).setCellRenderer(rendererCenter);
		tableauArchive.getColumnModel().getColumn(DATEDEBFIN).setCellRenderer(rendererCenter);
		
		tableauRAF.getColumnModel().getColumn(CHARGE).setCellRenderer(rendererCenter);
		tableauArchive.getColumnModel().getColumn(CHARGE).setCellRenderer(rendererCenter);
		
		tableauRAF.getColumnModel().getColumn(RESTEAFAIREDELAI).setCellRenderer(rendererCenter);
		tableauArchive.getColumnModel().getColumn(RESTEAFAIREDELAI).setCellRenderer(rendererCenter);
		
		log.traceExit("initTableau(ControlManager controlManager)") ;
	}

	/**
	 * @param controlManager
	 */
	public void initComponents(ModelManager manager) {
		log.traceEntry("initComponents(ModelManager manager)") ;
		
		//setBounds(0, 0, 1050, 725);

		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 1034, 686);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		initTableau(manager);
		initFiltre();
		initButton();
		log.traceExit("initComponents()") ;
	}

	/**
	 * 
	 */
	protected void actionChangeProjet() {

		if (!ecouter) return;
		
		int index = cmbProjet.getSelectedIndex();

		currentinfoProjet = cmbProjet.getItemAt(index);
		
		
		afficheInfoProjet();
		

		actionChangeUnite();
		try {
			modelTableauRAF.reload(currentinfoProjet);
			modelTableauFait.reload(currentinfoProjet);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(SUIVIPROJECTVIEW,"err04.projets","Impossible de charger les fonctions du projet", e);
		}
		
		
	}

	/**
	 * 
	 */
	private void afficheInfoProjet() {
		txtEstimationTotale.setText(String.format("%6.2f",currentinfoProjet.getEstimationtotale()));
		txtChargeTotale.setText(String.format("%6.2f",currentinfoProjet.getChargetotale()));
		txtResteAFaireTotal.setText(String.format("%6.2f",currentinfoProjet.getResteafairetotal()));
		txtDateAttenduPour.setText(currentinfoProjet.getDateSouhaiteeStr());
		txtDateEstimee.setText(currentinfoProjet.getDateFinEstimeeStr());
	}
	
	/**
	 * 
	 */
	protected void actionChangeUnite() {
		
		modelTableauRAF.setUnite(cmbUnite.getSelectedIndex());
		modelTableauFait.setUnite(cmbUnite.getSelectedIndex());
		currentinfoProjet.setUnite(cmbUnite.getSelectedIndex());
		afficheInfoProjet();
		
	}



	/**
	 * @return the controlManager
	 */
	public ControlManager getControlManager() {
		log.traceEntry("getControlManager()") ;
		
		return controlManager;
	}

	/**
	 * @param controlManager the controlManager to set
	 */
	public void setControlManager(ControlManager controlManager) {
		log.traceEntry("setControlManager(ControlManager controlManager)") ;
		this.controlManager = controlManager;
		log.traceExit("setControlManager()") ;
	}

	public void showDialog(){
		log.traceEntry("showDialog()") ;
		//Début du dialogue
		chargeListeProjet();
		cmbProjet.setSelectedIndex(0);
		this.setVisible(true);
		log.traceExit("showDialog()") ;

	}
}
