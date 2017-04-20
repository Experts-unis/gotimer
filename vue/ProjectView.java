package vue;



import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JDialog;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.border.LineBorder;

import model.ComplexiteModelManager;
import model.DataInfoFonction;
import model.DataInfoProjet;
import model.DataInfoTypologie;
import model.DataRefLibelle;

import model.ListModelFonction;
import model.ListModelProjet;
import model.ProjetModelManager;

import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JMenuItem;


import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;

import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Font;
import javax.swing.JComboBox;



import javax.swing.JSeparator;
import java.util.ResourceBundle;

import java.awt.Cursor;
import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;


public class ProjectView extends DialogBase  {
	
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("projectsview");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PROJECTVIEW = "projectView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( ProjectView.class );  
	
	ControlManager controlManager;
	ProjetModelManager managerModel;
	
	private JPanel contentPanelPrincipal ;
	private JTextField txtProjet;
	private JTextField txtFonction;
	private JScrollPane scrollPaneProjet;
	private JScrollPane scrollPaneFonc;

	private JList<DataInfoProjet> listProjet;
	private ListModelProjet listModelProjet;
	private DataInfoProjet infoProjetSelection;

	private JList<DataInfoFonction> listFonctions;
	ListModelFonction listModelFonction;
	private DataInfoFonction infoFonctionSelection;

	private JButton btnAjouterFonc;

	// PopUp Menu des projets
	private JMenuItem mntmSelectionnerProjetParDefaut;
	private JMenuItem mntmSupprimer;
	private JMenuItem mntmEditer;
	private JMenuItem mntmArchiverProjet;
	private JMenuItem mntmEditerFonc;
	private JMenuItem mntmEstimerFonc;
	private JMenuItem mntmSupprimerFonc;
	private JMenuItem mntmArchiverFonc;

	private boolean modeEditionProjet;
	private boolean modeEditionFonction;
	private boolean ecouter=false;

	private DataInfoProjet elementEditionProjet;

	private DataInfoFonction elementEditionFonction;

	private ProjectListView fDialogArchive;	
	private EstimationView fDialogEstimation;
	private JTextField txtEstimation;
	private JComboBox<DataRefLibelle> cmbComplexite;
	private JMenuItem mntmReporterFonction;
//	private JMenuItem mntmDleguer;
	private JMenuItem mntmFonctionParDefaut;
//	private JComboBox cmbDeleguerFonction;
	private JComboBox<DataRefLibelle> cmbParetoFonction;
	private JComboBox<DataRefLibelle> cmbEisenhower;
	private JComboBox<DataRefLibelle> cmbUnite;
	private JComboBox<DataInfoTypologie> cmbTypologie;
	private JComboBox<DataRefLibelle> cmbObjectifs;
	private JComboBox<DataRefLibelle> cmbParetoProjet;
	private JComboBox<DataRefLibelle> cmbPlaisirProjet;
	private JMenuItem mntmReporterProjet;
	private JComboBox<DataRefLibelle> cmbPlaisirFonction;
	private JButton btnEstimation;
	private DialogEstimationView fDialogEstimationComplexite;
	private JMenuItem mntmProjetAffichageSimple;
	private JMenuItem mntmFonctionAffichageSimple;
	private JSeparator separator;
	private JSeparator separator_1;
	private JMenuItem mntmProjetAffichageDtaille;
	private JMenuItem mntmFonctionAffichageDtaille;
	private TypologieView fDialogTypo;
	private boolean modeAffichageProjet;
	@SuppressWarnings("unused")
	private boolean modeAffichageFonction;

	private ReportView fDialogReport;

	private JComboBox<DataRefLibelle> cmbMurphy;
	private JTextField txtDateFinSouhaitee;

	private LinkTxtBtnCalendar lkDateSouhaitee;

	

	



	/**
	 * Create the dialog.
	 */
	public ProjectView(JDialog parent) {
		super(parent, "Projets", true,1050,732);
		
		log.traceEntry("ProjectView(JDialog parent)");
		this.setName("ProjectView"); //$NON-NLS-1$
		modeAffichageProjet=true;
		modeAffichageFonction=true;	
		
		setEcouter(false);
		
		this.controlManager=Principale.ctrlManager;
		
		try {
			managerModel = (ProjetModelManager)controlManager.getModelManager(this.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		modeEditionProjet = false;
		
		initComponents();
		
		initListeProjet();		
		initListeFonction();
		initPanneauBoutons();

		
		//Charge les données statiques  
		//this.getName()
		
		
		ComplexiteModelManager manager;
		try {
			manager = (ComplexiteModelManager) controlManager.getModelManager("DialogComplexiteView");
			
			manager.loadComplexiteInCombo(cmbComplexite);
			
			managerModel.loadUniteInCombo(cmbUnite);
			managerModel.loadParetoInCombo(cmbParetoFonction);
			managerModel.loadEisenhowerInCombo(cmbEisenhower);
			managerModel.loadPlaisirInCombo(cmbPlaisirFonction);
			
			
			managerModel.loadParetoInCombo(cmbParetoProjet);
			managerModel.loadObjectifInCombo(cmbObjectifs);
			managerModel.loadPlaisirInCombo(cmbPlaisirProjet);
			managerModel.loadMurphyInCombo(cmbMurphy);
		
			
			
		} catch (Exception e) {
			
			MainView.showMsgError(PROJECTVIEW,"err10.projets","Impossible charger les tables proterties", e);
			
			log.trace("Impossible charger les tables proterties, PILE : ",e);
		}
		
		
		// CHArge les données de l'utilisateurs

		try {
			managerModel.loadTypologieInCombobox(cmbTypologie);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(PROJECTVIEW,"err01.projets","Impossible charger les typologies", e);
			log.trace("Impossible charger les typologies, PILE : ",e);
		}

		

		
		
		txtProjet.requestFocusInWindow();
		setEcouter(true);		
		listProjet.setSelectedIndex(0);
		
	
		

		
	
		

		

		

		
		
		log.traceExit("ProjectView()");
	}



	/**
	 * 
	 */
	private void initComponents() {
		
		log.traceEntry("initComponents()");

		setBounds(0, 0, 1050, 732);
		setResizable(false);
		getContentPane().setLayout(null);
		contentPanelPrincipal = new JPanel();
		contentPanelPrincipal.setBounds(0, 0, 1044, 655);
		contentPanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanelPrincipal);
		contentPanelPrincipal.setLayout(null);
		
		log.traceExit("initComponents()");
	}



	/**
	 * 
	 */
	private void initPanneauBoutons() {
		log.traceEntry("initPanneauBoutons()");
			
		
		//TODO A mettre dans la version 2
		
		
		//				JButton btnImporter = new JButton("Importer");
		//				btnImporter.addActionListener(new ActionListener() {
		//					public void actionPerformed(ActionEvent e) {
		//					}
		//				});
		//				btnImporter.setBounds(122, 5, 100, 23);
		//				buttonPane.add(btnImporter);



		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 670, 1044, 33);
		getContentPane().add(buttonPane);
		buttonPane.setLayout(null);

		JButton cancelButton = new JButton(TRADUCTION.getString("ProjectView.cancelButton.text")); //$NON-NLS-1$
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		cancelButton.setBounds(939, 5, 85, 23);
		buttonPane.add(cancelButton);


		JButton btnArchive = new JButton(TRADUCTION.getString("ProjectView.btnArchive.text")); //$NON-NLS-1$
		btnArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionArchiveView();
				} catch (ExceptionTraitementSQL e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnArchive.setBounds(120, 5, 89, 23);
		buttonPane.add(btnArchive);

		JButton btnTypologie = new JButton(TRADUCTION.getString("ProjectView.btnTypologie.text")); //$NON-NLS-1$
		btnTypologie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionTypologieView();
			}
		});
		btnTypologie.setBounds(10, 5, 100, 23);
		buttonPane.add(btnTypologie);

		JButton btnReport = new JButton(TRADUCTION.getString("ProjectView.btnReport.text")); //$NON-NLS-1$
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionReportView();
				} catch (ExceptionTraitementSQL e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReport.setBounds(223, 5, 89, 23);
		buttonPane.add(btnReport);

		btnEstimation = new JButton(TRADUCTION.getString("ProjectView.btnEstimation.text")); //$NON-NLS-1$
		btnEstimation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEstimationView();
			}
		});
		btnEstimation.setBounds(523, 5, 123, 23);
		buttonPane.add(btnEstimation);

		log.traceExit("initPanneauBoutons()");
	}



	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	protected void actionReportView() throws ExceptionTraitementSQL {
		if (fDialogReport == null){
			fDialogReport = new ReportView(this);
		}
		
		fDialogReport.loadProjet();
		fDialogReport.showDialog();
		
		listModelFonction.removeAllElements();
		listModelProjet.removeAllElements();
		managerModel.loadProjetsInView(listModelProjet);
		setEnabledPopUpProjet(false); 

		
	}



	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	protected void actionArchiveView() throws ExceptionTraitementSQL {
		if (fDialogArchive == null){
			fDialogArchive = new ArchiveView(this);
		}
		
		fDialogArchive.loadProjet();
		fDialogArchive.showDialog();
		
		listModelFonction.removeAllElements();
		listModelProjet.removeAllElements();
		managerModel.loadProjetsInView(listModelProjet);
		setEnabledPopUpProjet(false); 
		
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */


	/**
	 * 
	 */
	protected void actionTypologieView() {
		
		if (fDialogTypo == null){
			fDialogTypo = new TypologieView();
		}
		
		fDialogTypo.showDialog();
		
		try {
			managerModel.loadTypologieInCombobox(cmbTypologie);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(PROJECTVIEW,"err01.projets","Impossible charger les typologies", e);
		}
	}



	/**
	 * 
	 */
	protected void actionEstimationView() {
		
		if (fDialogEstimation == null){
			fDialogEstimation = new EstimationView();
		}
		
		fDialogEstimation.showDialog();
		
	}



	/**
	 * 
	 */
	private void initBtnAndTxtFonction() {

		log.traceEntry("initBtnAndTxtFonction()");
		
		btnAjouterFonc = new JButton(TRADUCTION.getString("ProjectView.btnAjouterFonc.text")); //$NON-NLS-1$
		btnAjouterFonc.setActionCommand(TRADUCTION.getString("ProjectView.btnAjouterFonc.actionCommand")); //$NON-NLS-1$
		
		btnAjouterFonc.setBounds(983, 558, 41, 23);
		btnAjouterFonc.setEnabled(false);
		
		ListnerTxtBtnFonction leListenerBtnTxtFonction = new ListnerTxtBtnFonction(btnAjouterFonc);
		btnAjouterFonc.addActionListener(leListenerBtnTxtFonction);
		
		contentPanelPrincipal.add(btnAjouterFonc);
		
		txtFonction = new JTextField(100);
		txtFonction.addActionListener(leListenerBtnTxtFonction);
		txtFonction.getDocument().addDocumentListener(leListenerBtnTxtFonction);

		txtFonction.setToolTipText(TRADUCTION.getString("ProjectView.txtFonction.toolTipText")); //$NON-NLS-1$

		txtFonction.setBackground(Color.GRAY);
	
		txtFonction.setEnabled(false);
		txtFonction.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtFonction.setBounds(522, 559, 250, 20);
		txtFonction.setText(""); //$NON-NLS-1$
		//txtFonction.setBackground(new Color(255, 255, 255));
		//txtFonction.setColumns(100);
	
		contentPanelPrincipal.add(txtFonction);
	
		cmbComplexite = new JComboBox<DataRefLibelle>();
	//	cmbComplexite.setModel(new DefaultComboBoxModel(new String[] {"Faible", "Moyenne ", "Forte"}));
		cmbComplexite.setToolTipText(TRADUCTION.getString("ProjectView.cmbComplexite.toolTipText")); //$NON-NLS-1$
		cmbComplexite.setBounds(782, 559, 73, 20);
		contentPanelPrincipal.add(cmbComplexite);
		
		
		//TODO créer un object JTextField qui accepte uniquement des valeurs numériques
		txtEstimation = new JTextField();
		txtEstimation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//TODO Test les valeurs entrées au clavier
			}
		});
		txtEstimation.setToolTipText(TRADUCTION.getString("ProjectView.txtEstimation.toolTipText")); //$NON-NLS-1$
		txtEstimation.setBounds(865, 559, 41, 20);
		contentPanelPrincipal.add(txtEstimation);
		txtEstimation.setColumns(10);
		
		
		
		cmbParetoFonction = new JComboBox<DataRefLibelle>();
		cmbParetoFonction.setToolTipText(TRADUCTION.getString("ProjectView.cmbParetoFonction.toolTipText")); //$NON-NLS-1$
		//cmbParetoFonction.setModel(new DefaultComboBoxModel(new String[] {"20%", "80%"}));
		cmbParetoFonction.setBounds(522, 590, 48, 20);
		contentPanelPrincipal.add(cmbParetoFonction);
		
		cmbEisenhower = new JComboBox<DataRefLibelle>();
	//	cmbEisenhower.setModel(new DefaultComboBoxModel(new String[] {"ROUGE - URGENT et IMPORTANT", "JAUNE - URGENT et peu important", "ORANGE - peu urgent et IMPORTANT", "BLEU - peu urgent et peu important "}));
		cmbEisenhower.setToolTipText(TRADUCTION.getString("ProjectView.cmbEisenhower.toolTipText")); //$NON-NLS-1$
		cmbEisenhower.setBounds(580, 590, 192, 20);
		contentPanelPrincipal.add(cmbEisenhower);
		
		
		cmbUnite = new JComboBox<DataRefLibelle>();
		cmbUnite.setBounds(916, 559, 60, 20);
		contentPanelPrincipal.add(cmbUnite);
		
		cmbPlaisirFonction = new JComboBox<DataRefLibelle>();
		cmbPlaisirFonction.setBounds(782, 590, 124, 20);
		contentPanelPrincipal.add(cmbPlaisirFonction);
		
		log.traceExit("initBtnAndTxtFonction()");
	}

	

	/**
	 * 
	 */
	private void initBtnAndTxtProjet() {
		
		log.traceEntry("initBtnAndTxtProjet()");
		
		JButton btnAjouter = new JButton(TRADUCTION.getString("ProjectView.btnAjouter.text")); //$NON-NLS-1$
		btnAjouter.setActionCommand(TRADUCTION.getString("ProjectView.btnAjouter.actionCommand")); //$NON-NLS-1$
		btnAjouter.setBounds(471, 558, 41, 23);
		btnAjouter.setEnabled(false);
		ListnerTxtBtnProjet leListenerBtnTxtProjet = new ListnerTxtBtnProjet(btnAjouter);
		btnAjouter.addActionListener(leListenerBtnTxtProjet);
		
		contentPanelPrincipal.add(btnAjouter);
		


		txtProjet = new JTextField(100);
		txtProjet.addActionListener(leListenerBtnTxtProjet);
		txtProjet.getDocument().addDocumentListener(leListenerBtnTxtProjet);
		txtProjet.setToolTipText(TRADUCTION.getString("ProjectView.txtProjet.toolTipText")); //$NON-NLS-1$
		txtProjet.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		txtProjet.setBounds(10, 559, 284, 20);
		txtProjet.setText(""); 
		
		contentPanelPrincipal.add(this.txtProjet);
		
		cmbTypologie = new JComboBox<DataInfoTypologie>();
		cmbTypologie.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		cmbTypologie.setToolTipText(TRADUCTION.getString("ProjectView.cmbTypologie.toolTipText")); //$NON-NLS-1$
		cmbTypologie.setBounds(304, 559, 157, 20);
		contentPanelPrincipal.add(cmbTypologie);
		
		
		cmbObjectifs = new JComboBox<DataRefLibelle>();
	//	cmbObjectifs.setModel(new DefaultComboBoxModel<String>(new String[] {"Gros cailloux (tr\u00E8s important)", "Petit cailloux (important)", "Sable (peut attendre)", "Eau (plus tard)"}));
		cmbObjectifs.setToolTipText(TRADUCTION.getString("ProjectView.cmbObjectifs.toolTipText")); //$NON-NLS-1$
		cmbObjectifs.setBounds(68, 590, 157, 20);
		contentPanelPrincipal.add(cmbObjectifs);
		
		cmbParetoProjet = new JComboBox<DataRefLibelle>();
	//	cmbParetoProjet.setModel(new DefaultComboBoxModel<String>(new String[] {"20%", "80%"}));
		cmbParetoProjet.setToolTipText(TRADUCTION.getString("ProjectView.cmbParetoProjet.toolTipText")); //$NON-NLS-1$
		cmbParetoProjet.setBounds(10, 590, 48, 20);
		contentPanelPrincipal.add(cmbParetoProjet);
		
		cmbPlaisirProjet = new JComboBox<DataRefLibelle>();
		cmbPlaisirProjet.setToolTipText(TRADUCTION.getString("ProjectView.cmbPlaisirProjet.toolTipText")); //$NON-NLS-1$
		cmbPlaisirProjet.setBounds(304, 590, 157, 20);
		contentPanelPrincipal.add(cmbPlaisirProjet);
		
		cmbMurphy = new JComboBox<DataRefLibelle>();
		cmbMurphy.setToolTipText(TRADUCTION.getString("ProjectView.cmbMurphy.toolTipText")); //$NON-NLS-1$
		cmbMurphy.setBounds(235, 590, 59, 20);
		contentPanelPrincipal.add(cmbMurphy);
		
		txtDateFinSouhaitee = new JTextField();
		txtDateFinSouhaitee.setToolTipText("Date de fin souhait\u00E9e");
		txtDateFinSouhaitee.setBounds(10, 624, 111, 20);
		contentPanelPrincipal.add(txtDateFinSouhaitee);
		txtDateFinSouhaitee.setEditable(false);
		txtDateFinSouhaitee.setColumns(10);
		
		JButton btnDateFinSouhaitee = new JButton("");
		btnDateFinSouhaitee.setBounds(131, 623, 20, 22);
		btnDateFinSouhaitee.setIcon(new ImageIcon("src/images/cal216.png"));
		contentPanelPrincipal.add(btnDateFinSouhaitee);
		
		setLkDateSouhaitee(new LinkTxtBtnCalendar(this, txtDateFinSouhaitee, btnDateFinSouhaitee,"yyyy-MM-dd"));
		
		log.traceExit("initBtnAndTxtProjet()");
	}

	/**
	 * 
	 */
	private void initListeFonction() {
		
		log.traceEntry("initListeFonction()");
		
		// Création du model
		listModelFonction = new ListModelFonction();
		
		// Création de la liste		
		listFonctions = new JList<DataInfoFonction>(listModelFonction);
		listFonctions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		listFonctions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), TRADUCTION.getString("ProjectView.listFonctions.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128))); //$NON-NLS-2$
		listFonctions.setLayoutOrientation(JList.VERTICAL);
		listFonctions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFonctions.setToolTipText(TRADUCTION.getString("ProjectView.listFonctions.toolTipText")); //$NON-NLS-1$
	
		listFonctions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			    if (e.getClickCount() == 2){  
			        actionEditerFonc();
			    }
			      
			}
		});

		
		listFonctions.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				actionSelectionFonction(e);
			}
		});
			
		initPopUpListeFonction();
		
		initBtnAndTxtFonction();
		
		setEnabledFonction(false);

		scrollPaneFonc = new JScrollPane();
		scrollPaneFonc.setBounds(522, 26, 502, 521);
		scrollPaneFonc.setViewportView(listFonctions);

		contentPanelPrincipal.add(scrollPaneFonc);
	
		
		//TODO Ajouter cette fonctionnalité en V2
/*		cmbDeleguerFonction = new JComboBox();
		cmbDeleguerFonction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cmbDeleguerFonction.setVisible(false);
		cmbDeleguerFonction.setBounds(782, 590, 242, 20);
		contentPanelPrincipal.add(cmbDeleguerFonction);
*/
		
		log.traceExit("initListeFonction()");
	}

	/**
	 * @param e
	 */
	private void actionSelectionFonction(ListSelectionEvent e) {
		log.traceEntry("actionSelectionFonction(ListSelectionEvent e)");
		if (! isEcouter()) return;
		if (listModelFonction.size()==0) return;
		if (isModeEditionFonction()){
	        txtFonction.setText("");
	        cmbComplexite.setSelectedIndex(0);
	        txtEstimation.setText("");
	        cmbUnite.setSelectedIndex(1);
	        cmbParetoFonction.setSelectedIndex(0);
	        cmbEisenhower.setSelectedIndex(0);
	        cmbPlaisirFonction.setSelectedIndex(0);
	        setModeEditionFonction(false);
		}
		
		
		setEnabledPopUpFonction(true);
		infoFonctionSelection = listModelFonction.getElementAt(listFonctions.getSelectedIndex());
		log.traceExit("actionSelectionFonction()");
	}
	/**
	 * 
	 */
	protected void actionSelectionParDefautFonction() {
		log.traceEntry("actionSelectionParDefautFonction()");
		// Cette fonction n'est accessible que lorsque une fonction a été sélectionnée
		
		//Supprimer l'étoile  qui été avant par défaut
		
		// Parcours la liste des projets
		
		for(int i=0;i<listModelFonction.size();i++){
			
				if (listModelFonction.getElementAt(i).isSelected())
				{
					// Efface la selection de l'ancien projet en base
					try {
						managerModel.setFonctionByDefault(listModelFonction.getElementAt(i).getId(), false);
					} catch (ExceptionTraitementSQL e) {
						MainView.showMsgError(PROJECTVIEW,"err08.projets","Impossible de désélectionner cette fonction par defaut", e);
					}
					
					listModelFonction.getElementAt(i).setSelected(false);
					
					// Stop le parcours
					i=listModelFonction.size();
					
					
				}
		}
		
		// Ajoute une étoile sur le projet sélectionné
		infoFonctionSelection.setSelected(true);
		try {
			managerModel.setFonctionByDefault(infoFonctionSelection.getId(), true);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(PROJECTVIEW,"err09.projets","Impossible de sélectionner cette fonction par defaut", e);
		}

		listModelFonction.refresh(listFonctions);
		log.traceExit("actionSelectionParDefautFonction()");
	}

	/**
	 * 
	 */
	private void initPopUpListeFonction() {
		log.traceEntry("initPopUpListeFonction()");
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(listFonctions, popupMenu);

		
		mntmEstimerFonc = new JMenuItem(TRADUCTION.getString("ProjectView.mntmEstimerFonc.text")); //$NON-NLS-1$
		mntmEstimerFonc.setEnabled(false);
		mntmEstimerFonc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEstimerFonc();
			}
		});
		
		
		mntmFonctionParDefaut = new JMenuItem(TRADUCTION.getString("ProjectView.mntmFonctionParDefaut.text")); //$NON-NLS-1$
		mntmFonctionParDefaut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSelectionParDefautFonction();
			}
		});
		mntmFonctionParDefaut.setEnabled(false);
		popupMenu.add(mntmFonctionParDefaut);
		mntmEstimerFonc.setMnemonic('E');
		popupMenu.add(mntmEstimerFonc);

		
		mntmEditerFonc = new JMenuItem(TRADUCTION.getString("ProjectView.mntmEditerFonc.text")); //$NON-NLS-1$
		mntmEditerFonc.setMnemonic('d');
		mntmEditerFonc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEditerFonc();
			}
		});
		mntmEditerFonc.setEnabled(false);
		popupMenu.add(mntmEditerFonc);
		
		mntmReporterFonction = new JMenuItem(TRADUCTION.getString("ProjectView.mntmReporterFonction.text")); //$NON-NLS-1$
		mntmReporterFonction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionReportFonction();
			}
		});
		
		mntmFonctionAffichageSimple = new JMenuItem(TRADUCTION.getString("ProjectView.mntmFonctionAffichageSimple.text")); //$NON-NLS-1$
		mntmFonctionAffichageSimple.setVisible(false);
		mntmFonctionAffichageSimple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				affichageSimpleFonction(true);
			}
		});
		
		JSeparator separator_2 = new JSeparator();
		popupMenu.add(separator_2);
		
		popupMenu.add(mntmFonctionAffichageSimple);
		
		mntmFonctionAffichageDtaille = new JMenuItem(TRADUCTION.getString("ProjectView.mntmFonctionAffichageDtaille.text")); //$NON-NLS-1$
		mntmFonctionAffichageDtaille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				affichageSimpleFonction(false);
			}
			
		});
		
		popupMenu.add(mntmFonctionAffichageDtaille);
		
		JSeparator separator_3 = new JSeparator();
		popupMenu.add(separator_3);
		mntmReporterFonction.setEnabled(false);
		popupMenu.add(mntmReporterFonction);
		
		mntmArchiverFonc = new JMenuItem(TRADUCTION.getString("ProjectView.mntmArchiverFonc.text")); //$NON-NLS-1$
		mntmArchiverFonc.setEnabled(false);
		mntmArchiverFonc.setMnemonic('A');
		mntmArchiverFonc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionArchiverFonc();
			}
		});
		popupMenu.add(mntmArchiverFonc);
		
		
		
		
		mntmSupprimerFonc = new JMenuItem(TRADUCTION.getString("ProjectView.mntmSupprimerFonc.text")); //$NON-NLS-1$
		mntmSupprimerFonc.setMnemonic('S');
		
				mntmSupprimerFonc.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						actionSuppFonc();
					}
				});
						
						separator = new JSeparator();
						popupMenu.add(separator);
				
						mntmSupprimerFonc.setEnabled(false);
						popupMenu.add(mntmSupprimerFonc);


		//TODO Ajouter cette fonctionnalité en V2
/*		mntmDleguer = new JMenuItem("D\u00E9l\u00E9guer");
		mntmDleguer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mntmDleguer.setEnabled(false);
		popupMenu.add(mntmDleguer);
*/	
	

		log.traceExit("initPopUpListeFonction()");
	}

	/**
	 * @param bAffiche
	 */
	protected void affichageSimpleFonction(boolean bAffiche) {
		
		// Pour tous les éléments de la liste mettre un affichage Simple bAffiche=true ou détaillé bAffiche=False
		
		modeAffichageFonction = bAffiche;
		
		for (int i =0;i<listModelFonction.getSize();i++ ){
			
			listModelFonction.get(i).setModeAffichage(bAffiche);
			
		}
		
		mntmFonctionAffichageSimple.setVisible(!bAffiche);
		mntmFonctionAffichageDtaille.setVisible(bAffiche);
		listModelFonction.refresh(listFonctions);
		
	}



	/**
	 * 
	 */
	protected void actionReportFonction() {
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listFonctions.getSelectedIndex();
        
        // System.out.println("actionSuppFonc : INDEX fonction à supprimer : "+Integer.toString(index));
        //deleteFonction sur la DB        
        try {
			managerModel.reportFonction(listModelFonction.getElementAt(index).getId());
		} catch (ExceptionTraitementSQL e) {
			log.fatal("actionArchiverFonc() : PB SQL ",e);
			
			return;
		}
    
        
    	// supprime de la liste
        setEcouter(false);
    	listModelFonction.remove(index);
    	 setEcouter(true);
    	
    	 setEnabledPopUpFonction(false);

	}

	/**
	 * 
	 */
	protected void actionArchiverFonc() {
		log.traceEntry("actionArchiverFonc()");
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listFonctions.getSelectedIndex();
        
        // System.out.println("actionSuppFonc : INDEX fonction à supprimer : "+Integer.toString(index));
        //deleteFonction sur la DB        
        try {
			managerModel.archiveFonction(listModelFonction.getElementAt(index).getId());
		} catch (ExceptionTraitementSQL e) {
			log.fatal("actionArchiverFonc() : PB SQL ",e);
			
			return;
		}
    
        
    	// supprime de la liste
        setEcouter(false);
    	listModelFonction.remove(index);
    	setEcouter(true);
    	
    	setEnabledPopUpFonction(false);

        log.traceExit("actionArchiverFonc()");
	}

	/**
	 * 
	 */
	protected void actionEstimerFonc() {
		log.traceEntry("actionEstimerFonc()");
		if (fDialogEstimationComplexite == null){
			fDialogEstimationComplexite = new DialogEstimationView(this);
		} 
		
		 int index = listFonctions.getSelectedIndex();      
		
		 fDialogEstimationComplexite.showDialog(listModelFonction.getElementAt(index));
		log.traceExit("actionEstimerFonc()");
	}

	/**
	 * 
	 */
	protected void actionSuppFonc() {
		log.traceEntry("actionSuppFonc()");

		setEcouter(false);

		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listFonctions.getSelectedIndex();
        
        // System.out.println("actionSuppFonc : INDEX fonction à supprimer : "+Integer.toString(index));
        //deleteFonction sur la DB        
        
        try {
			managerModel.deleteFonction(listModelFonction.getElementAt(index));
		} catch (ExceptionTraitementSQL e) {
			// TODO Affichier un message correct pour 
			e.printStackTrace();
		}
    	
   
    	// supprime de la liste
    	listModelFonction.remove(index);
    	

        int size = listModelFonction.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            
        	setEnabledFonction(false);

        } else { //Select an index.
            if (index == listModelFonction.getSize()) {
                //removed item in last position
                index--;
            }

            listFonctions.setSelectedIndex(index);
            listFonctions.ensureIndexIsVisible(index);
        }
        setEcouter(true);
        log.traceExit("actionSuppFonc()");
		
	}

	/**
	 * 
	 */
	protected void actionEditerFonc() {
		// Editer une fonction
		log.traceEntry("actionEditerFonc()");
		
		if (isModeEditionFonction()) 
			return;
		
		setModeEditionFonction(true);
		
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listFonctions.getSelectedIndex();
        
        elementEditionFonction = listModelFonction.getElementAt(index);
        
        txtFonction.setText(elementEditionFonction.getLibelle());
        cmbComplexite.setSelectedIndex(elementEditionFonction.getIdComplexite());
        txtEstimation.setText(elementEditionFonction.getEstimation().toString());
        cmbUnite.setSelectedIndex(1);
        cmbParetoFonction.setSelectedIndex(elementEditionFonction.getIdpareto());
        cmbEisenhower.setSelectedIndex(elementEditionFonction.getIdeisenhower());
        cmbPlaisirFonction.setSelectedIndex(elementEditionFonction.getIdmotivation());
        log.traceExit("");
	}

	/**
	 * 
	 */
	private void initListeProjet() {
		log.traceEntry("initListeProjet()");
		
		// Création du model
		try {
			listModelProjet = new ListModelProjet();
			managerModel.loadProjetsInView(listModelProjet);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Création de la liste		
		listProjet = new JList<DataInfoProjet>(listModelProjet);

		listProjet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		listProjet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), TRADUCTION.getString("ProjectView.listProjet.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 128))); //$NON-NLS-2$
		listProjet.setToolTipText(TRADUCTION.getString("ProjectView.listProjet.toolTipText")); //$NON-NLS-1$
		listProjet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listProjet.setLayoutOrientation(JList.VERTICAL);

		
		listProjet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			    if (e.getClickCount() == 2){  
			        actionEditerProjet();
			    }
			      
			}
		});
		
		//list.addListSelectionListener(this);
		listProjet.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				actionSelectionProjet(e);
			}
		});

		initPopUpListeProjet();
		initBtnAndTxtProjet();
		
		scrollPaneProjet = new JScrollPane();
		scrollPaneProjet.setBounds(10, 26, 502, 521);		

		scrollPaneProjet.setViewportView(listProjet);

		contentPanelPrincipal.add(scrollPaneProjet);
		
		log.traceExit("initListeProjet()");
	}

	/**
	 * 
	 */
	private void initPopUpListeProjet() {
		
		log.traceEntry("initPopUpListeProjet()");
		JPopupMenu popupMenuProjet = new JPopupMenu();

		addPopup(listProjet, popupMenuProjet);

		mntmSelectionnerProjetParDefaut = new JMenuItem(TRADUCTION.getString("ProjectView.mntmSelectionnerProjetParDefaut.text")); //$NON-NLS-1$
		mntmSelectionnerProjetParDefaut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSelectionParDefautProjet();
			}
		});
		mntmSelectionnerProjetParDefaut.setEnabled(false);
		popupMenuProjet.add(mntmSelectionnerProjetParDefaut);

		mntmEditer = new JMenuItem(TRADUCTION.getString("ProjectView.mntmEditer.text")); //$NON-NLS-1$
		mntmEditer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEditerProjet();
			}
		});
		mntmEditer.setEnabled(false);
		popupMenuProjet.add(mntmEditer);

		mntmArchiverProjet = new JMenuItem(TRADUCTION.getString("ProjectView.mntmArchiverProjet.text")); //$NON-NLS-1$
		mntmArchiverProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionArchiverProjet();
			}
		});
		
		mntmReporterProjet = new JMenuItem(TRADUCTION.getString("ProjectView.mntmReporterProjet.text")); //$NON-NLS-1$
		mntmReporterProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionReportProjet();
			}
		});
		
		mntmProjetAffichageSimple = new JMenuItem(TRADUCTION.getString("ProjectView.mntmProjetAffichageSimple.text")); //$NON-NLS-1$
		mntmProjetAffichageSimple.setVisible(false);
		
		mntmProjetAffichageSimple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				affichageSimpleProjet(true);
			}
		});
		
		JSeparator separator_2 = new JSeparator();
		popupMenuProjet.add(separator_2);
		
		popupMenuProjet.add(mntmProjetAffichageSimple);
		
		mntmProjetAffichageDtaille = new JMenuItem(TRADUCTION.getString("ProjectView.mntmProjetAffichageDtaille.text")); //$NON-NLS-1$
		mntmProjetAffichageDtaille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				affichageSimpleProjet(false);
			}
		});
	
		popupMenuProjet.add(mntmProjetAffichageDtaille);
		
		JSeparator separator_3 = new JSeparator();
		popupMenuProjet.add(separator_3);
		mntmReporterProjet.setEnabled(false);
		popupMenuProjet.add(mntmReporterProjet);
		mntmArchiverProjet.setEnabled(false);
		popupMenuProjet.add(mntmArchiverProjet);
		
		//TODO AJOUTER A LA VERSION 2
//		JMenuItem mntmEquipier = new JMenuItem("Equipier");
//		mntmEquipier.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		mntmEquipier.setEnabled(false);
//		popupMenuProjet.add(mntmEquipier);
		
		
		
				mntmSupprimer = new JMenuItem(TRADUCTION.getString("ProjectView.mntmSupprimer.text")); //$NON-NLS-1$
				mntmSupprimer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						actionSuppProjet();
					}
				});
				
				separator_1 = new JSeparator();
				popupMenuProjet.add(separator_1);
				mntmSupprimer.setEnabled(false);
				popupMenuProjet.add(mntmSupprimer);
		log.traceExit("initPopUpListeProjet()");
	}
	

	/**
	 * @param bAffiche
	 */
	protected void affichageSimpleProjet(boolean bAffiche) {

		// Pour tous les éléments de la liste mettre un affichage Simple bAffiche=true ou détaillé bAffiche=False
		
		modeAffichageProjet = bAffiche;
		
		for (int i =0;i<listModelProjet.getSize();i++ ){
			
			listModelProjet.get(i).setModeAffichage(bAffiche);
			
		}
		
		mntmProjetAffichageSimple.setVisible(!bAffiche);
		mntmProjetAffichageDtaille.setVisible(bAffiche);
		listModelProjet.refresh(listProjet);
		
	}



	/**
	 * Supprimer un projet, les fonctionnalités et les mesures de temps
	 */
	protected void actionSuppProjet() {
		
		log.traceEntry("actionSuppProjet()");
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listProjet.getSelectedIndex();
        
        //deleteProjet sur la DB        
        try {
			managerModel.deleteProjet(listModelProjet.getElementAt(index));
		} catch (ExceptionTraitementSQL e) {
			// TODO Afficher le message d'erreur correctement !
			e.printStackTrace();
		}
  
        
    	// supprime de la liste
    	listModelProjet.remove(index);
    	
    	//Efface la liste des fonctions du projet
    	listModelFonction.clear();
    	setEnabledPopUpFonction(false);

        int size = listModelProjet.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            setEnabledPopUpProjet(false);
        	setEnabledFonction(false);
        	setTitle("Projet : ");

        } else { //Select an index.
            if (index == listModelProjet.getSize()) {
                //removed item in last position
                index--;
            }

            listProjet.setSelectedIndex(index);
            listProjet.ensureIndexIsVisible(index);
        }
        log.traceExit("actionSuppProjet()");
        		
	}

	/**
	 * 
	 */
	protected void actionReportProjet() {
		log.traceEntry("actionReportProjet()");
		
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listProjet.getSelectedIndex();
        
        // System.out.println("actionSuppFonc : INDEX fonction à supprimer : "+Integer.toString(index));
        //deleteFonction sur la DB        
        try {
			managerModel.reportProjet(listModelProjet.getElementAt(index).getId());
		} catch (ExceptionTraitementSQL e) {
			log.fatal("actionReportProjet() : PB SQL ",e);
			
			return;
		}
    
        
    	// supprime de la liste
        listModelProjet.remove(index);
        listModelFonction.removeAllElements();

        setEnabledPopUpProjet (false);

		
		log.traceExit("actionReportProjet()");
		
	}

	/**
	 * 
	 */
	protected void actionArchiverProjet() {
		
		log.traceEntry("actionArchiverProjet()");
		
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listProjet.getSelectedIndex();
        
        // System.out.println("actionSuppFonc : INDEX fonction à supprimer : "+Integer.toString(index));
        //deleteFonction sur la DB        
        try {
			managerModel.archiveProjet(listModelProjet.getElementAt(index).getId());
		} catch (ExceptionTraitementSQL e) {
			log.fatal("actionArchiverProjet() : PB SQL ",e);
			
			return;
		}
    
        
    	// supprime de la liste
        listModelProjet.remove(index);
        listModelFonction.removeAllElements();

        setEnabledPopUpProjet (false);

		
		log.traceExit("actionArchiverProjet()");
	}

	/**
	 * 
	 */
	protected void actionEditerProjet() {
		log.traceEntry("actionEditerProjet()");
		// Editer un projet
		
		if (isModeEditionProjet()) 
			return;
		
		setModeEditionProjet(true);
		
		//This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = listProjet.getSelectedIndex();
        
        elementEditionProjet = listModelProjet.getElementAt(index);
        
        txtProjet.setText(elementEditionProjet.getLibelle());
        cmbParetoProjet.setSelectedIndex(elementEditionProjet.getIdpareto());
        cmbObjectifs.setSelectedIndex(elementEditionProjet.getIdobjectif());
        cmbPlaisirProjet.setSelectedIndex(elementEditionProjet.getIdmotivation());
        cmbTypologie.setSelectedIndex(elementEditionProjet.getIdtypologie());
        txtDateFinSouhaitee.setText(elementEditionProjet.getDateSouhaitee().toString());
        
        
        log.traceExit("actionEditerProjet()");
	}

	/**
	 * 
	 */
	protected void actionSelectionParDefautProjet() {
		log.traceEntry("actionSelectionParDefautProjet()");
		// Cette fonction n'est accessible que lorsque un projet a été sélectionné
		
		//Supprimer l'étoile du projet qui été avant par défaut
		
		// Parcours la liste des projets
		
		for(int i=0;i<listModelProjet.size();i++){
			
				if (listModelProjet.getElementAt(i).isSelected())
				{
					// Efface la selection de l'ancien projet en base
					try {
						managerModel.setProjetByDefault(listModelProjet.getElementAt(i).getId(), false);
					} catch (ExceptionTraitementSQL e) {
						MainView.showMsgError(PROJECTVIEW,"err02.projets","Impossible de désélectionner ce projet par defaut", e);
					}
					
					listModelProjet.getElementAt(i).setSelected(false);
					
					// Stop le parcours
					i=listModelProjet.size();
					
					
				}
		}
		
		// Ajoute une étoile sur le projet sélectionné
		infoProjetSelection.setSelected(true);
		try {
			managerModel.setProjetByDefault(infoProjetSelection.getId(), true);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(PROJECTVIEW,"err03.projets","Impossible de sélectionner ce projet par defaut", e);
		}

		listModelProjet.refresh(listProjet);
		log.traceExit("actionSelectionParDefautProjet()");
	}

	private void actionSelectionProjet(ListSelectionEvent e) {
		log.traceEntry("actionSelectionProjet(ListSelectionEvent e)");
		
		if (! isEcouter()) return;
		
		if (isModeEditionProjet()){
	        txtProjet.setText("");
	        cmbTypologie.setSelectedIndex(0);
	        cmbParetoProjet.setSelectedIndex(0);
	        cmbObjectifs.setSelectedIndex(0);
	        cmbPlaisirProjet.setSelectedIndex(0);
	        cmbMurphy.setSelectedIndex(0);
	        setModeEditionProjet(false);
		}
		
		
        if (e.getValueIsAdjusting() == false) {

            if (listProjet.getSelectedIndex() == -1) {
            //No selection, disable fire button.
            	setEnabledFonction(false);
            	setEnabledPopUpProjet(false);
            	setEnabledPopUpFonction(false);

            } else {
            //Selection, enable the fire button.
        		cmbParetoProjet.setSelectedIndex(0);
        		cmbObjectifs.setSelectedIndex(0);
        		cmbTypologie.setSelectedIndex(0);
        		cmbPlaisirProjet.setSelectedIndex(0);
        		cmbMurphy.setSelectedIndex(0);

            	setEnabledFonction(true);
            	setEnabledPopUpProjet(true);
    			infoProjetSelection = listModelProjet.getElementAt(listProjet.getSelectedIndex());

            	setTitle("Projet : " + infoProjetSelection.getLibelle());
            	setEcouter(false);
            	
            	try {
					managerModel.loadFonctionsInView(listModelFonction,infoProjetSelection);
				} catch (ExceptionTraitementSQL e1) {
					
					MainView.showMsgError(PROJECTVIEW,"err04.projets","Impossible de charger les fonctions de ce projet ", e1);
				}
            	setEcouter(true);
            	if (listModelFonction.size() != 0){
            		listFonctions.setSelectedIndex(0);
            		setEnabledPopUpFonction(true);
            		mntmFonctionAffichageSimple.setVisible(false);
            		mntmFonctionAffichageDtaille.setVisible(true);
            	}else{
            		setEnabledPopUpFonction(false);
            		mntmFonctionAffichageSimple.setVisible(false);
            		mntmFonctionAffichageDtaille.setVisible(false);
            	}
        		
            }
        }
        log.traceExit("actionSelectionProjet()");
        	
	}

	/**
	 * @param b
	 */
	private void setEnabledPopUpProjet(boolean b) {
		log.traceEntry("setEnabledPopUpProjet(boolean b="+b+")");
		mntmSelectionnerProjetParDefaut.setEnabled(b);
		mntmSupprimer.setEnabled(b);
		mntmEditer.setEnabled(b);
		mntmArchiverProjet.setEnabled(b);
		mntmReporterProjet.setEnabled(b);
		log.traceExit("setEnabledPopUpProjet()");
		
	}
	
	/**
	 * @param b
	 */
	private void setEnabledPopUpFonction(boolean b) {
		log.traceEntry("setEnabledPopUpFonction(boolean b="+b+")");
		mntmEditerFonc.setEnabled(b);
		mntmSupprimerFonc.setEnabled(b);
		mntmEstimerFonc.setEnabled(b);
		mntmArchiverFonc.setEnabled(b);
		mntmReporterFonction.setEnabled(b);
	
		// TODO A METTRE DANS LA VERSION 2
		//mntmDleguer.setEnabled(b);
		mntmFonctionParDefaut.setEnabled(b);
		log.traceExit("setEnabledPopUpFonction()");
	}

	private void setEnabledFonction(boolean b) {
		log.traceEntry("setEnabledFonction(boolean b="+b+")");
		if (b){
			txtFonction.setBackground(Color.WHITE);
        	txtFonction.setEnabled(true);
        	btnAjouterFonc.setEnabled(true);
		} else {
			txtFonction.setBackground(Color.GRAY);
			//txtFonction.setBackground(new Color(211, 211, 211));
        	txtFonction.setEnabled(false);
        	btnAjouterFonc.setEnabled(false);
		}
		log.traceExit("setEnabledFonction()");
		
	}

	/**
	 * @return the bEdition
	 */
	public boolean isModeEditionProjet() {
		log.traceEntry("isModeEditionProjet() modeEditionProjet="+modeEditionProjet);
		return modeEditionProjet;
	}

	/**
	 * @param bEdition the bEdition to set
	 */
	public void setModeEditionProjet(boolean bEdition) {
		log.traceEntry("setModeEditionProjet(boolean bEdition ="+bEdition+" )");
		this.modeEditionProjet = bEdition;
		mntmEditer.setEnabled(!bEdition);
		log.traceExit("setModeEditionProjet()");
	}

	/**
	 * @return the modeEditionFonction
	 */
	public boolean isModeEditionFonction() {
		log.traceEntry("isModeEditionFonction() modeEditionFonction="+modeEditionFonction);
		return modeEditionFonction;
	}

	/**
	 * @param modeEditionFonction the modeEditionFonction to set
	 */
	public void setModeEditionFonction(boolean modeEditionFonction) {
		log.traceEntry("setModeEditionFonction(boolean modeEditionFonction="+modeEditionFonction+")");
		this.modeEditionFonction = modeEditionFonction;
		mntmEditerFonc.setEnabled(!modeEditionFonction);
		log.traceExit("setModeEditionFonction()");
	}

	/**
	 * @return the elementEdition
	 */
	public DataInfoProjet getElementEditionProjet() {
		log.traceEntry("getElementEditionProjet()",elementEditionProjet);
		
		return elementEditionProjet;
	}

	/**
	 * @param elementEdition the elementEdition to set
	 */
	public void setElementEditionProjet(DataInfoProjet elementEdition) {
		log.traceEntry("setElementEditionProjet(DataInfoProjet elementEdition)",elementEdition);
		this.elementEditionProjet = elementEdition;
		log.traceExit("setElementEditionProjet()");
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
	


    

/**
	 * @return the ecouter
	 */
	public boolean isEcouter() {
		return ecouter;
	}

	/**
	 * @param ecouter the ecouter to set
	 */
	public void setEcouter(boolean ecouter) {
		this.ecouter = ecouter;
	}





/**
	 * @return the lkDateSouhaitee
	 */
	public LinkTxtBtnCalendar getLkDateSouhaitee() {
		return lkDateSouhaitee;
	}



	/**
	 * @param lkDateSouhaitee the lkDateSouhaitee to set
	 */
	public void setLkDateSouhaitee(LinkTxtBtnCalendar lkDateSouhaitee) {
		this.lkDateSouhaitee = lkDateSouhaitee;
	}





/**
 * @author test
 *
 */
public class ListnerTxtBtnProjet implements ActionListener, DocumentListener {
	private org.apache.logging.log4j.Logger log = LogManager . getLogger ( ListnerTxtBtnProjet.class );  
	private boolean alreadyEnabled = false;
    private JButton button;
    
    
	/**
	 * 
	 */
	public ListnerTxtBtnProjet(JButton button) {
		log.traceEntry("ListnerTxtBtnProjet(JButton button)");
		this.button = button;
		log.traceExit("ListnerTxtBtnProjet()");
    }
	
	


    
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		log.traceEntry("insertUpdate(DocumentEvent e)"); 
		enableButton();
		log.traceExit("insertUpdate(DocumentEvent e)");
	}
	 //Required by DocumentListener.
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		log.traceEntry("removeUpdate(DocumentEvent e)");
		handleEmptyTextField(e);
		log.traceExit("removeUpdate(DocumentEvent e)");
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		log.traceEntry("changedUpdate(DocumentEvent e)");
		if (!handleEmptyTextField(e)) {
            enableButton();
        }
		log.traceExit("changedUpdate(DocumentEvent e)");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Ajouter un projet
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		log.traceEntry("ListnerTxtBtnProjet.actionPerformed(ActionEvent e)",e);   
		
		String name = txtProjet.getText();



		
		// Unique nom dans la liste en ajout
		if (!isModeEditionProjet()){
			if (name.equals("") || alreadyInList(name)) {
				estDejaDansLaListe();
				return;
			}
		}else
			if (name.equals("") ) {
				estDejaDansLaListe();
				return;
			}
		

		int index = listProjet.getSelectedIndex(); //get selected index
		if (index == -1) { //no selection, so insert at beginning
			index = 0;
		} else {           //add after the selected item
			index++;
		}

		DataInfoProjet element ;

		// Nous sommes en mode édition
		if (isModeEditionProjet())
		{
			// update Projet en mode edition
			log.trace("actionPerformed : projet en mode édition");
			//Modifier un élément Projet en base
			element = listModelProjet.getElementAt(index-1);
	
			
				try {
					int idpareto=cmbParetoProjet.getSelectedIndex();
					int idobjectif=cmbObjectifs.getSelectedIndex();
					int idtypologie=cmbTypologie.getItemAt(cmbTypologie.getSelectedIndex()).getId();
					int idmotivation=cmbPlaisirProjet.getSelectedIndex();
					int idCoeff = cmbMurphy.getSelectedIndex();
					lkDateSouhaitee.getDt();
					managerModel.updateProjet(txtProjet.getText(), idpareto, idobjectif,idtypologie, idmotivation,idCoeff,lkDateSouhaitee.getDt(), element ,index);
				} catch (ExceptionTraitementSQL e1) {
					MainView.showMsgError(PROJECTVIEW,"err05.projets","Impossible de modifier ce projet ", e1);
				}
				
			
			listModelProjet.refresh(listProjet);
			setModeEditionProjet(false); // stop le mode Edition


		}else{
			//Ajouter un élément Projet en base
			log.trace("actionPerformed : Projet en mode ajout");

			try {
				
				int idpareto=cmbParetoProjet.getSelectedIndex();
				int idobjectif=cmbObjectifs.getSelectedIndex();
				int idtypologie=cmbTypologie.getItemAt(cmbTypologie.getSelectedIndex()).getId();
				int idmotivation=cmbPlaisirProjet.getSelectedIndex();
				int idCoeff = cmbMurphy.getSelectedIndex();
				
				element = managerModel.addProjet(txtProjet.getText(),idpareto, idobjectif,idtypologie,idmotivation, idCoeff, lkDateSouhaitee.getDt(),index);
				element.setModeAffichage(modeAffichageProjet);
				//Ajouter l'élément à la liste
				listModelProjet.insertElementAt(element, index);
				setEnabledPopUpFonction(false);
				
			} catch (ExceptionTraitementSQL e1) {
				MainView.showMsgError(PROJECTVIEW,"err06.projets","Impossible d'ajouter ce projet ", e1);
			}
			
		

		}

		//If we just wanted to add to the end, we'd do this:
		//listModelProjet.addElement(employeeName.getText());

		//Reset the text field.
		txtProjet.requestFocusInWindow();
		txtProjet.setText("");
		
		cmbParetoProjet.setSelectedIndex(0);
		cmbObjectifs.setSelectedIndex(0);
		cmbTypologie.setSelectedIndex(0);
		cmbPlaisirProjet.setSelectedIndex(0);
		cmbMurphy.setSelectedIndex(0);
		
		txtProjet.setBorder(BorderFactory.createLineBorder(Color.black));

		//Select the new item and make it visible.
		listProjet.setSelectedIndex(index);
		listProjet.ensureIndexIsVisible(index);
		log.traceExit("ListnerTxtBtnProjet.actionPerformed()");

	}





	/**
	 * 
	 */
	private void estDejaDansLaListe() {
		Toolkit.getDefaultToolkit().beep();
		txtProjet.requestFocusInWindow();
		txtProjet.selectAll();
		txtProjet.setBorder(BorderFactory.createLineBorder(Color.red));
		log.trace("actionPerformed : Ce nom est déjà dans la liste "+txtProjet.getText());
	}

    private void enableButton() {
    	log.traceEntry("enableButton()");
    	
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
        log.traceExit("enableButton()");
    }

    private boolean handleEmptyTextField(DocumentEvent e) {
    	log.traceEntry("handleEmptyTextField(DocumentEvent e)",e);
    	
    	if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            log.traceExit("handleEmptyTextField() true");
            return true;
        }
    	log.traceExit("handleEmptyTextField() false");
        return false;
    }

    protected boolean alreadyInList(String name) {
    	log.traceEntry("alreadyInList(String name="+name+")");
    	boolean ret = listModelProjet.contains(name); 
    	log.traceExit("alreadyInList(String name) ret="+ret);
    	return ret;
    }
	
}
    
    


/**
* @author test
*
*/
public class ListnerTxtBtnFonction implements ActionListener, DocumentListener {
   
  private org.apache.logging.log4j.Logger log = LogManager . getLogger ( ListnerTxtBtnFonction.class );  
  private boolean alreadyEnabled = false;
  private JButton button;
   
   
	/**
	 * 
	 */
	public ListnerTxtBtnFonction(JButton button) {
		log.traceEntry("ListnerTxtBtnFonction(JButton button)");
		this.button = button;
		log.traceExit("ListnerTxtBtnFonction()");
   }
	
	


   
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		log.traceEntry("insertUpdate(DocumentEvent e)",e); 
		enableButton();
		log.traceExit("insertUpdate()");
	}
	 //Required by DocumentListener.
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		log.traceEntry("removeUpdate(DocumentEvent e)");
		handleEmptyTextField(e);
		log.traceExit("removeUpdate(DocumentEvent e)");
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		log.traceEntry("changedUpdate(DocumentEvent e)");
		if (!handleEmptyTextField(e)) {
           enableButton();
       }
		log.traceExit("changedUpdate(DocumentEvent e)");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Ajouter une fonction 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		log.traceEntry("ListnerTxtBtnFonction.actionPerformed(ActionEvent e)",e);   
		
		String name = txtFonction.getText();

		// Unique nom dans la liste en ajout
		if (!isModeEditionProjet()){
			if (name.equals("") || alreadyInList(name)) {
				estDejaDansLaListe();
				return;
			}
		}else
			if (name.equals("") ) {
				estDejaDansLaListe();
				return;
			}
		


          int index = listFonctions.getSelectedIndex(); //get selected index
          if (index == -1) { //no selection, so insert at beginning
              index = 0;
          } else {           //add after the selected item
              index++;
          }

         
          DataInfoFonction  element ;

          // Nous sommes en mode édition
          if (isModeEditionFonction())
          {
        	  //En mode edition
        	  log.trace("actionPerformed : en mode edition");

        	  // Modifier un élément Projet en base si le text a changé
        	  element = listModelFonction.getElementAt(index-1);
        	  // System.out.println("Modification de select  txt = "+element.getLibelle()+" index "+Integer.toString(index));
        	 // if ( ! txtFonction.getText().equals(element.getLibelle())){
        		  try {

        			  int idcomplexite=cmbComplexite.getSelectedIndex();
        			  String str = txtEstimation.getText();
        			  if (str.isEmpty()) str="1.0d";
        			  log.trace("estimatio = "+str);
        			  double estimation=Double.parseDouble( str);
        			  int idpareto=cmbParetoFonction.getSelectedIndex();
        			  int ideisenhower=cmbEisenhower.getSelectedIndex();
        			  int idmotivation=cmbPlaisirFonction.getSelectedIndex();


        			  managerModel.updateFonction(txtFonction.getText(), idcomplexite, estimation, idpareto, ideisenhower,idmotivation,element ,index);
        		  } catch (ExceptionTraitementSQL e1) {
        			  MainView.showMsgError(PROJECTVIEW,"err08.projets","Impossible de modifier cette fonction ", e1);
        		  }

        	 //}
        	  listModelFonction.refresh(listFonctions);
        	  setModeEditionFonction(false); // stop le mode Edition


          }else{
        	  //Ajouter un élément Fonction en base
        	  log.trace("actionPerformed : en mode ajout");

        	  //Enregistre la fonction en base
        	  setEcouter(false);
        	  //TODO mettre la modification du model dans le listModelFonction
        	  
        	  try {
        		  //addFoncProjet(String nameFonc, DataInfoProjet infoProjetSelection,int idpareto, int ideisenhower,int idcomplexite,double estimation,int index)
        		  /* cmbComplexite
        			txtEstimation
        			cmbParetoFonction
        			cmbEisenhower
        			cmbUnite
        			*/
        		  //TODO Créer une classe dérivant de txtField et acceptant que des digits et des virgules en français ou des points en anglais
    			  int idcomplexite=cmbComplexite.getSelectedIndex();
    			  String str = txtEstimation.getText();
    			  if (str.isEmpty()) str="1.0d";
    			  log.trace("estimatio = "+str);
    			  double estimation=Double.parseDouble( str);
    			  int idpareto=cmbParetoFonction.getSelectedIndex();
    			  int ideisenhower=cmbEisenhower.getSelectedIndex();
    			  int idmotivation=cmbPlaisirFonction.getSelectedIndex();
    			  
    			 
        		  
    			  element= managerModel.addFoncProjet(txtFonction.getText(), infoProjetSelection, idpareto , ideisenhower, idcomplexite, estimation, idmotivation, index);

    			  // Ajoute la fonctions dans la liste
        		  listModelFonction.insertElementAt(element, index);

        	  } catch (ExceptionTraitementSQL e1) {
        		  MainView.showMsgError(PROJECTVIEW,"err07.projets","Impossible d'ajouter une fonction à ce projet ", e1);
        	  }
        	  setEcouter(true);
          }

          //Reset the text field.
          txtEstimation.setText("");
          cmbComplexite.setSelectedIndex(0);
          cmbParetoFonction.setSelectedIndex(0);
          cmbEisenhower.setSelectedIndex(0);
          cmbUnite.setSelectedIndex(0);
          cmbPlaisirFonction.setSelectedIndex(0);
          
          
          txtFonction.requestFocusInWindow();
          txtFonction.setText("");
          txtFonction.setBorder(BorderFactory.createLineBorder(Color.black));

          //Select the new item and make it visible.
          listFonctions.setSelectedIndex(index);
          listFonctions.ensureIndexIsVisible(index);
          
          log.traceExit("ListnerTxtBtnFonction.actionPerformed(ActionEvent e)");
	}





	/**
	 * @param name
	 */
	private void estDejaDansLaListe() {
		Toolkit.getDefaultToolkit().beep();
		  txtFonction.requestFocusInWindow();
		  txtFonction.selectAll();
		  txtFonction.setBorder(BorderFactory.createLineBorder(Color.red));
		  log.trace("actionPerformed : name="+txtFonction.getText()+" est déjà dans la liste");
	}





	/**
	 * @return
	 * @throws NumberFormatException
	 */
	public void prepareAttribut(int idcomplexite,double estimation,int idpareto,int ideisenhower,int idmotivation ) throws NumberFormatException {
		

		
	}

   private void enableButton() {
	   log.traceEntry("enableButton()");
       if (!alreadyEnabled) {
           button.setEnabled(true);
       }
       log.traceExit("enableButton()");
   }

   private boolean handleEmptyTextField(DocumentEvent e) {
	   log.traceEntry("handleEmptyTextField(DocumentEvent e)",e);
	   if (e.getDocument().getLength() <= 0) {
           button.setEnabled(false);
           alreadyEnabled = false;
           log.traceExit("handleEmptyTextField(DocumentEvent e) true");
           return true;
       }
	   log.traceExit("handleEmptyTextField(DocumentEvent e) false");
       return false;
   }

   protected boolean alreadyInList(String name) {
	   log.traceEntry("alreadyInList(String name="+name+")");
	   boolean ret= listModelFonction.contains(name);
	   log.traceExit("alreadyInList() ret="+ret);
	   return ret;
   }
	
}    
}
