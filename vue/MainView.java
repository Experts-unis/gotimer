package vue;


import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;


import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
//import controleur.MyConfiguration;
import controleur.Principale;
import model.DataInfoProjet;
import model.DB;
import model.DataInfoFonction;
import model.DataInfoTimer;
import model.MainModelManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.util.Calendar;

import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.ComponentOrientation;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainView extends JFrame {
	private static final ResourceBundle TRADUCTION = ResourceBundle.getBundle("properties.fr.mainview_fr_FR"); //$NON-NLS-1$

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( MainView.class ); 
	
	
	private static final float vesion = 1.00f;
	private static final String MAINVIEW = "MainView";
	private JPanel contentPane;
	ControlManager controlManager;
	private boolean start=true;
	private Date startTime;
	private Date stopTime;
	
	
	private int idProjet;
	private JComboBox<DataInfoFonction> cmbFonctions;
	private JButton btnGo ;
	
	// Dialogue

	private ParamActiviteView fDialogParamActivite;
//	private ParamPrefView fDialogParamPref;
	private TimerProjetView fDialogSuiviProjet;
	private ProjectView fDialogProjet;
	private EstimationView fDialogEstimation;
	private SuiviProjetView fDialogSuivi;
	private StatistiqueView fDialogStat;
	private AProposView fDialogAPropos;

	

	// Autre
	private DataInfoTimer currentInfoTime;
	private JTextArea txtAff;
	private JScrollPane scrollPane;
	private JComboBox<DataInfoProjet> cmbProjets;

	private JButton btnParam;
	private LinkCombo<DataInfoProjet,DataInfoFonction> linkCmbProjetFonction;
	

	
	private MainModelManager modelManager;
	private JMenuBar menuBar;
	private JMenu mnTimer;
	private JMenuItem mntmProjets;
	private JMenuItem mntmTimers;
	private JMenuItem mntmEstimation;
	private JMenuItem mntmSuivi;
	private JMenuItem mnQuitter;
	private JMenu mnParam;
	private TypologieView fDialogTypo;

	private ParamPreferenceView fDialogParamPref;
	
		
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public MainView() {

		log.traceEntry("MainView(ControlManager controlManager)") ;
		init();
		
	
		this.controlManager=Principale.ctrlManager;
		
		this.setName("MainView");
		try {
			modelManager = (MainModelManager)controlManager.getModelManager(this.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		initCompoment();

		
		File file = null;

		file=new File("timer.xml");

		if (file.exists())
		{

			// Traitement du fichier timer

			this.setCurrentInfoTime(controlManager.deSerialize());
			startTime = getCurrentInfoTime().getStart();
			// Suppression de l'objet timer
			file.delete();
			this.idProjet =getCurrentInfoTime().getIdproj();
			this.start=false;
			btnGo.setText(TRADUCTION.getString("Stop"));
			
			
			txtAff.setText( getCurrentInfoTime().getStartInfo(TRADUCTION));

		}else{

			this.setCurrentInfoTime(new DataInfoTimer());
		}
		
		try {
			linkCmbProjetFonction = new LinkCombo<DataInfoProjet,DataInfoFonction>(cmbProjets,cmbFonctions,controlManager);
			
			
		} catch (ExceptionTraitementSQL e) {

			
			showMsgError(MAINVIEW,"err01.projets", " Impossible de charger les projets et leurs fonctions",e);
			
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				actionQuitter();
			}
		});
		
		log.traceExit("MainView(ControlManager controlManager,int idProjet)");
		
	}

	/**
	 * 
	 */
	private void actionProjets() {
		log.traceEntry("actionProjets()") ;
		if (fDialogProjet == null){
			fDialogProjet = new ProjectView(null);
		} 
		
		fDialogProjet.showDialog();
		
		actionMAJProjet();
		
		
		// System.out.println("actionBtnProjets: OUT )" );
		log.traceExit("actionProjets()") ;
		
	}

	/**
	 * @param e
	 * @throws HeadlessException
	 */
	public static void showMsgError(String nameView,String numMsg, String msgDefault,Exception e)
			throws HeadlessException {
		
		String messageError = " "+ msgDefault +" \n";
		
		//TODO Internationnalisation
		// Chargement des données de configuration
//		MyConfiguration config = Principale.config;
//		ResourceBundle traderr = Principale.config.getTraduction(nameView);
		
		if (e instanceof ExceptionTraitementSQL){
			messageError += "CODE : "+ ((ExceptionTraitementSQL) e).getErrorCode() +" \n";
			messageError += " "+((ExceptionTraitementSQL) e).getMessage()+" \n";
			messageError += " QUERY : "+((ExceptionTraitementSQL) e).getQuery();
		}
		
	//	String[] data=messageError.split(messageError, 100);
		//data[0].con
		JOptionPane.showMessageDialog(null, messageError, "Erreur", JOptionPane.ERROR_MESSAGE);
		
		log.trace("MSG ERREUR : "+messageError,e);
	}

	public static void showMsgUser(String nameView,String numMsg, String msgDefault, String titreDefaut) {
		
		// TODO Internationnaliser les messages utilisateurs
		JOptionPane.showMessageDialog(null,  msgDefault ,titreDefaut ,JOptionPane.ERROR_MESSAGE);
		
	}


	
	
	public void showMainView(){
		log.traceEntry("<->showMainView()") ;
		this.setVisible(true);
	}



	/**
	 * 
	 */
	private void init() {
		log.traceEntry("init()") ;
		start=true;
		startTime=null;
		stopTime=null;
		
		log.traceExit("init()");
	}

	/**
	 * 
	 */
	private void initCompoment() {
		log.traceEntry("initCompoment()") ;
		
		// Fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 525, 381);
		// Centre la fenêtre
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (dimScreen.width / 2) - 450/2 ;
		int y = (dimScreen.height / 2) - 400/2 -100; 
		Point centreEcran = new Point(x,y);
		this.setLocation(centreEcran);
		setTitle("Les experts-unis (www.experts-unis.com) - Timer " + Float.toString(vesion)+ " - "+DB.nameBase) ;
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(true); //content panes must be opaque
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initMenu();
		
		
		JPanel panelNord = new JPanel();
		panelNord.setBounds(0, 240, 504, 64);
		contentPane.add(panelNord);
		panelNord.setLayout(null);
		
		btnGo = new JButton(TRADUCTION.getString("Start"));
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnGo();
			}
		});
		btnGo.setFont(new Font("Times New Roman", Font.BOLD, 40));
		btnGo.setBounds(58, 11, 381, 50);
		panelNord.add(btnGo);

		
		
		// Centre
		JPanel panelCentre = new JPanel();
		panelCentre.setBounds(0, 11, 504, 233);
		contentPane.add(panelCentre);
		panelCentre.setLayout(null);
				
				
		JLabel lblProjet = new JLabel(TRADUCTION.getString("Projet")+" : "); //"Projets"
		lblProjet.setBounds(10, 11, 183, 14);
		panelCentre.add(lblProjet);
		
		cmbProjets = new JComboBox<DataInfoProjet>();
		cmbProjets.setActionCommand("projet"); //$NON-NLS-1$
		cmbProjets.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		cmbProjets.setAutoscrolls(true);
		cmbProjets.setBounds(10, 36, 183, 31);
		cmbProjets.setFont(new Font("Times New Roman", Font.BOLD, 20));	

		panelCentre.add(cmbProjets);

		
		JLabel lblFonctionnalits = new JLabel(TRADUCTION.getString("Fonctionnalite")+" : "); //"Fonctionnalit\u00E9s :"
		lblFonctionnalits.setBounds(215, 11, 204, 14);
		panelCentre.add(lblFonctionnalits);
		
		cmbFonctions = new JComboBox<DataInfoFonction>();
		cmbFonctions.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		cmbFonctions.setActionCommand("fonction");

		cmbFonctions.setAutoscrolls(true);
		cmbFonctions.setFont(new Font("Times New Roman", Font.BOLD, 20));
		cmbFonctions.setBounds(215, 36, 279, 31);
		panelCentre.add(cmbFonctions);
		
		txtAff = new JTextArea();
		txtAff.setColumns(20);
		txtAff.setRows(15);
		txtAff.setAutoscrolls(true);
		
		scrollPane = new JScrollPane(txtAff);
		scrollPane.setBounds(10, 78, 484, 144);
		panelCentre.add(scrollPane);
		

		//initBoutton();
		log.traceExit("initCompoment()") ;
	}
	
	private void initMenu()  {
		// MENU
		menuBar = new JMenuBar();
		
		// Timer
		menuBar.add(initMenuTimer());
				  
		// PARAMETRAGE
		menuBar.add(initMenuPreferences());
		  
		// AIDE
		menuBar.add(initMenuAide());
		  
	    setJMenuBar(menuBar);

	   
	}

	/**
	 * @return
	 */
	private JMenu initMenuAide() {
		JMenu mnAide = new JMenu(TRADUCTION.getString("MainView.mnAide.text")); //$NON-NLS-1$

		JMenuItem mntmAPropos = new JMenuItem(TRADUCTION.getString("MainView.mntmAPropos.text")); //$NON-NLS-1$
		mntmAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					actionAPropos();
			}
		});
		mnAide.add(mntmAPropos);

		return mnAide;
		
		

	}




	/**
	 * @return
	 */
	private JMenu initMenuPreferences() {
		
		mnParam = new JMenu(TRADUCTION.getString("MainView.mnParam.text")); //$NON-NLS-1$
		JMenuItem mntmPrfrences = new JMenuItem(TRADUCTION.getString("MainView.mntmPrfrences.text")); //$NON-NLS-1$
		mntmPrfrences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		
		mntmPrfrences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionParamPref();
			}
		});
		
		JMenuItem mntmActivit = new JMenuItem(TRADUCTION.getString("MainView.mntmActivit.text")); //$NON-NLS-1$
		mntmActivit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionParamActivite();
			}
		});
		mntmActivit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnParam.add(mntmActivit);
		
		JMenuItem mntmTypologie = new JMenuItem(TRADUCTION.getString("MainView.mntmTypologie.text")); //$NON-NLS-1$
		mnParam.add(mntmTypologie);
		mntmTypologie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionTypologie();
			}
		});
		mntmTypologie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		
		JSeparator separator = new JSeparator();
		mnParam.add(separator);
		
		mnParam.add(mntmPrfrences);
		
		return mnParam;
	}


	/**
	 * 
	 */
	protected void actionParamPref() {
		
		log.traceEntry("actionParamPref()") ;
		
		if (fDialogParamPref == null){
			fDialogParamPref = new ParamPreferenceView();
		} 
		
		fDialogParamPref.showDialog();

		log.traceExit("actionParamPref()") ;
		
	}


	/**
	 * @return
	 */
	private JMenu initMenuTimer() {
		mnTimer = new JMenu(TRADUCTION.getString("MainView.mnTimer.text")); //$NON-NLS-1$
		//mnTimer.setBounds(0, 0, 107, 22);
		
		
		mntmProjets = new JMenuItem(TRADUCTION.getString("MainView.mntmProjets.text")); //$NON-NLS-1$
		mntmProjets.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mntmProjets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionProjets();
			}
		});
		
		mntmTimers = new JMenuItem(TRADUCTION.getString("MainView.mntmTimers.text")); //$NON-NLS-1$
		mntmTimers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmTimers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionTimer();
				
			}
		});
		mnTimer.add(mntmTimers);
		
		JSeparator separator = new JSeparator();
		mnTimer.add(separator);
		
		mnTimer.add(mntmProjets);
		
		mntmEstimation = new JMenuItem(TRADUCTION.getString("MainView.mntmEstimation.text")); //$NON-NLS-1$
		mntmEstimation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mntmEstimation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEstimation();
			}
		});
		
		mntmSuivi = new JMenuItem(TRADUCTION.getString("MainView.mntmSuivi.text")); //$NON-NLS-1$
		mntmSuivi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmSuivi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSuiviProjet();
			}
		});
		mnTimer.add(mntmSuivi);
		mnTimer.add(mntmEstimation);
		
		mnQuitter = new JMenuItem(TRADUCTION.getString("MainView.mnQuitter.text")); //$NON-NLS-1$
		mnQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		
		JMenuItem mntmStatistique = new JMenuItem(TRADUCTION.getString("MainView.mntmStatistique.text")); //$NON-NLS-1$
		mntmStatistique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionStat();
			}
		});
		mntmStatistique.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		mnTimer.add(mntmStatistique);
		
		JSeparator separator_2 = new JSeparator();
		mnTimer.add(separator_2);
		mnTimer.add(mnQuitter);
		
		return mnTimer;
		
	}


	/**
	 * 
	 */
	protected void actionTypologie() {
		if (fDialogTypo == null){
			fDialogTypo = new TypologieView();
		}
		
		fDialogTypo.showDialog();
		
	}


	/**
	 * 
	 */
	protected void actionStat() {
		log.traceEntry("<->actionStat()") ;
		
		if (fDialogStat == null){
			fDialogStat = new StatistiqueView();
		} 
		
		fDialogStat.showDialog();
		
	}


	/**
	 * 
	 */
	protected void actionSuiviProjet() {
		log.traceEntry("<->actionSuiviProjet()") ;
		
		if (fDialogSuivi == null){
			fDialogSuivi = new SuiviProjetView();
		} 
		
		fDialogSuivi.showDialog();
		
	}


	/**
	 * 
	 */
	protected void actionEstimation() {
		
		if (fDialogEstimation == null){
			fDialogEstimation = new EstimationView();
		}
		
		fDialogEstimation.showDialog();
		
	}

	/**
	 * 
	 */
	protected void actionAPropos() {

		if (fDialogAPropos == null){
			fDialogAPropos = new AProposView();
		}
		
		fDialogAPropos.showDialog();
		
	}


	/**
	 * 
	 */
	public void initBoutton() {
		
		log.traceEntry("initBoutton()") ;
		// Les boutons

		JButton btnQuitter = new JButton(TRADUCTION.getString("Quitter")); //"Quitter"
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnQuitter.setBounds(415, 49, 89, 23);
		contentPane.add(btnQuitter);
		
		btnParam = new JButton(TRADUCTION.getString("Parametrage")); //"Param\u00E9trage"
		btnParam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}


		});
		btnParam.setBounds(288, 49, 117, 23);
		contentPane.add(btnParam);
		
		JButton btnSuivi = new JButton(TRADUCTION.getString("Timer")); // "Timer"
		btnSuivi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		btnSuivi.setBounds(19, 49, 89, 23);
		contentPane.add(btnSuivi);

		
		log.traceExit("initBoutton()") ;
	}

	

	/**
	 * 
	 */
	protected void actionTimer() {
		
		log.traceEntry("actionTimer()") ;
		
		if (fDialogSuiviProjet == null){
			fDialogSuiviProjet = new TimerProjetView(null);
		} else{
			fDialogSuiviProjet.reload();
		}

		//fDialogSuiviProjet.showDialog();
		fDialogSuiviProjet.setVisible(true);
		
		// Remettre à jour les combobox
	
		actionMAJProjet(); 
				
		log.traceExit("actionTimer()") ;		
		
	}


	/**
	 * @throws HeadlessException
	 */
	public void actionMAJProjet() throws HeadlessException {
		linkCmbProjetFonction.setEcouter(false);
		
		try {
			this.idProjet = modelManager.isSelectProjet();
		} catch (ExceptionTraitementSQL e) {
			showMsgError(MAINVIEW,"err02.projets","Aucun projet sélectionné", e);
		}
		cmbProjets.removeAllItems();

		try {
			modelManager.loadProjetInCombo(cmbProjets, false );
		} catch (ExceptionTraitementSQL e) {
			showMsgError(MAINVIEW,"err03.projets","Impossible de charger les projets", e);
		}
		
		cmbFonctions.removeAllItems();
		
		try {
			modelManager.loadFonctionInCombo(this.idProjet,cmbFonctions );
		} catch (ExceptionTraitementSQL e) {
			showMsgError(MAINVIEW,"err04.projets","Impossible de charger les fonctions du projets "+this.idProjet, e);
		}

		linkCmbProjetFonction.setEcouter(true);
	}
	/**
	 * 
	 */
	protected void actionParamActivite() {
		log.traceEntry("actionParamActivite()") ;
		
		if (fDialogParamActivite == null){
			fDialogParamActivite = new ParamActiviteView();
		} 
		
		fDialogParamActivite.showDialog();

		log.traceExit("actionParamActivite()") ;
		
	}

	/**
	 * @return the currentInfoTime
	 */
	public DataInfoTimer getCurrentInfoTime() {
		log.traceEntry("<->getCurrentInfoTime()") ;
		return currentInfoTime;
	}

	/**
	 * @param currentInfoTime the currentInfoTime to set
	 */
	public void setCurrentInfoTime(DataInfoTimer currentInfoTime) {
		log.traceEntry("setCurrentInfoTime(DataInfoTime currentInfoTime)") ;
		
		this.currentInfoTime = currentInfoTime;
		
		if (log.isTraceEnabled())
			if (currentInfoTime != null)
				log.trace("currentInfoTime : "+currentInfoTime.toString());
			else
				log.trace("currentInfoTime : null");
	
		log.traceExit("setCurrentInfoTime()") ;
	}


	/**
	 * @param lblAff
	 * @param btnGo
	 */
	private void actionBtnGo() {
		log.traceEntry("actionBtnGo()") ;
		Calendar c= Calendar.getInstance();
		
		if (start){
			log.trace("SART");
			btnGo.setText( TRADUCTION.getString("Stop") );
			start=false;
			startTime = c.getTime();
			getCurrentInfoTime().setStart(new Date(startTime.getTime()));
			int index = cmbFonctions.getSelectedIndex();

			getCurrentInfoTime().setInfoDataFonction(cmbFonctions.getItemAt(index));
			DataInfoProjet elProjet = cmbProjets.getItemAt(cmbProjets.getSelectedIndex());
			getCurrentInfoTime().setLibProjet(elProjet.getLibelle());
			//lblAff.setText();
			
			txtAff.setText(getCurrentInfoTime().getStartInfo(TRADUCTION));
		}else{
			log.trace("STOP");
			btnGo.setText( TRADUCTION.getString("Start") );
			start=true;
			stopTime = c.getTime();
			getCurrentInfoTime().setStop(new Date(stopTime.getTime()));
			
			
			controlManager.calculEcart(getCurrentInfoTime());
			
			
			txtAff.setText(getCurrentInfoTime().getStartInfo(TRADUCTION));
			
			
			try {
				modelManager.addTime(getCurrentInfoTime());
			} catch (ExceptionTraitementSQL e) {
				showMsgError(MAINVIEW,"err01.timer","Impossible d'enregistrer le timer", e);
			}
			
		}
		log.traceExit("actionBtnGo()") ;
	}

	
	

	
	private void actionQuitter() {
		log.traceEntry("<->actionBtnQuitter()") ;
		if (! start){
			this.controlManager.serialize(getCurrentInfoTime());
		}
		// System.out.println("Cloture de la fenêtre principale vMain");
		System.exit(0);	
	}
}
