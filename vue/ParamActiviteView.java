/**
 * 
 */
package vue;


import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import javax.swing.JScrollPane;
import javax.swing.JTable;


import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;
import controleur.MyConfiguration;


import controleur.PlageIndex;
import controleur.Principale;
import model.DataInfoPeriodeActivite;
import model.DataInfoVacation;
import model.TableauModelPresence;
import model.TableauModelVacation;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.ImageIcon;



/**
 * @author test
 *
 */
public class ParamActiviteView extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PARAMVIEW = "paramView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( ParamActiviteView.class );
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("paramview");
	
	private final JPanel contentPanel = new JPanel();
	private JTable tableVacation;
	private JTable tablePresence;
	private JTextField txtDateDebut;
	private JTextField txtDateFin;
	private LinkTxtBtnCalendar lkDateStart;
	private LinkTxtBtnCalendar lkDateStop;
	private TableauModelPresence modelTableauPresence;
	private JComboBox<String> cmbPAHeureDeb;
	private JComboBox<String> cmbPAHeureFin;
	private JCheckBox chckbxActiver;
	private TableauModelVacation modelTableauVacation;
	private JCheckBox chckbxLundi;
	private JCheckBox chckbxMardi;
	private JCheckBox chckbxMercredi;
	private JCheckBox chckbxJeudi;
	private JCheckBox chckbxVendredi;
	private JCheckBox chckbxSamedi ;
	private JCheckBox chckbxDimanche ;
	private MyConfiguration config;
	

	
	private JButton btnEnregistrer;
	private Vector<String> heure;

	private Vector<PlageIndex> listePlageHoraire;
	private JButton btnAjouterPA;
	private JMenuItem mntmSupprimerTout;

	private static JMenuItem mntmSupprimerVacation;
	private static JMenuItem mntmSupprimerPresence;
	private RendererCmbHeure render;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ParamActiviteView dialog = new ParamActiviteView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ParamActiviteView() {
		super(null, "", true, 610, 612);
		
		log.traceEntry("ParamView()") ;
		
	
		
		// Chargement des données de configuration
		config = Principale.config;
		
		
		 
		initComponents();
		initParam();
		initActiviteJourDeLaSemaine();
		initActiviteHeureDuJour();		
		initVacation();
		initButton();
		
//		ResourceBundle config = ResourceBundle.getBundle("timer.properties.config"); 
//		
//		String str = config.getString("jour.lundi");
		
		
		chckbxLundi.setSelected(Boolean.valueOf(config.getProperty("jour.lundi")));		
		chckbxMardi.setSelected(Boolean.valueOf(config.getProperty("jour.mardi")));
		chckbxMercredi.setSelected(Boolean.valueOf(config.getProperty("jour.mercredi")));
		chckbxJeudi.setSelected(Boolean.valueOf(config.getProperty("jour.jeudi")));
		chckbxVendredi.setSelected(Boolean.valueOf(config.getProperty("jour.vendredi")));
		chckbxSamedi.setSelected(Boolean.valueOf(config.getProperty("jour.samedi")));
		chckbxDimanche.setSelected(Boolean.valueOf(config.getProperty("jour.dimanche")));
		
		chckbxActiver.setSelected(Boolean.valueOf(config.getProperty("vacation.desactive"))); 
		
		
		
		
		
		try {
			modelTableauPresence.load();
			Vector<DataInfoPeriodeActivite> data = modelTableauPresence.getData();
			
			int bornInf,bornSup;
			for (DataInfoPeriodeActivite element : data){
				element.setIndex(heure);
				bornInf = element.getIndexDebut();
				bornSup = element.getIndexFin();
				listePlageHoraire.add(new PlageIndex(bornInf, bornSup));
				System.out.println("ParamArctivite 172 :  plage "+bornInf+", "+bornSup);
			}
			cmbPAHeureDeb.setSelectedIndex(0);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(PARAMVIEW,"err01.load.presence","Impossible de charger la configuration Heure de présence", e);
		}
		
		
		try {
			modelTableauVacation.load();
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(PARAMVIEW,"err02.load.vacation","Impossible de charger la configuration jour de Vacation", e);
		}
		
		
		log.traceExit("ParamView()") ;
		
	}

	/**
	 * 
	 */
	public void initButton() {
		
		log.traceEntry("initButton()");
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 534, 594, 38);
		contentPanel.add(buttonPane);
		

		btnEnregistrer = new JButton(TRADUCTION.getString("btn.enreg"));
		btnEnregistrer.setBounds(365, 5, 109, 23);
		btnEnregistrer.setEnabled(false);
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionEnregistrer();
			}
		});
		buttonPane.setLayout(null);
		btnEnregistrer.setActionCommand("OK");
		buttonPane.add(btnEnregistrer);
		getRootPane().setDefaultButton(btnEnregistrer);


		JButton btnRetour = new JButton(TRADUCTION.getString("btn.annuler"));
		btnRetour.setBounds(484, 5, 81, 23);
		btnRetour.setActionCommand("Cancel");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		buttonPane.add(btnRetour);
		log.traceExit("initButton()");
	}

	/**
	 * 
	 */
	protected void actionEnregistrer() {

		log.traceEntry("actionEnregistrer()");


		config.setProperty("jour.lundi", chckbxLundi.isSelected());
		config.setProperty("jour.mardi", chckbxMardi.isSelected());
		config.setProperty("jour.mercredi",chckbxMercredi.isSelected());
		config.setProperty("jour.jeudi",chckbxJeudi.isSelected());
		config.setProperty("jour.vendredi",chckbxVendredi.isSelected());
		config.setProperty("jour.samedi",chckbxSamedi.isSelected());
		config.setProperty("jour.dimanche",chckbxDimanche.isSelected());
		
		
		
		try {
			config.save();
			JOptionPane.showMessageDialog(this, TRADUCTION.getString("msg.enreg.ok") , TRADUCTION.getString("msg.enreg.titre.ok"), JOptionPane.INFORMATION_MESSAGE);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(this, TRADUCTION.getString("msg.enreg.err")+"\n " + e.getMessage(), TRADUCTION.getString("msg.enreg.titre.err"), JOptionPane.ERROR_MESSAGE);
		}
		actionEnableButtonEnregistrer(false);
		
		log.traceEntry("actionEnregistrer()");
	}

	/**
	 * 
	 */

	public void initActiviteHeureDuJour() {
		
		log.traceEntry("initActiviteHeureDuJour()");
		
		JLabel lblHeureDbut = new JLabel(TRADUCTION.getString("activite.heure.debut")+ " : ");
		lblHeureDbut.setBounds(30, 127, 153, 14);
		contentPanel.setBounds(0, 0, 594, 623);
		contentPanel.add(lblHeureDbut);
		
		//TODO Charger les plages horaires déjà enregistrées
		listePlageHoraire = new Vector<PlageIndex>(); 
		
		
		render = new RendererCmbHeure (listePlageHoraire);
		cmbPAHeureDeb = new JComboBox<String>();
		cmbPAHeureDeb.setRenderer(render);

		cmbPAHeureDeb.setBounds(30, 152, 153, 20);
		contentPanel.add(cmbPAHeureDeb);
		
		
		JLabel lblHeureFin = new JLabel(TRADUCTION.getString("activite.heure.fin")+" : ");
		lblHeureFin.setBounds(232, 127, 131, 14);
		contentPanel.add(lblHeureFin);
		
		cmbPAHeureFin = new JComboBox<String>();
		cmbPAHeureFin.setBounds(230, 152, 146, 20);
		cmbPAHeureFin.setEnabled(false); 
		contentPanel.add(cmbPAHeureFin);
		
		heure = new Vector<String>(); 
		String str; 
		for (int i=0;i<=23;i++){
			
			str = String.format("%02d:00",i);
			heure.addElement(str);
			cmbPAHeureDeb.addItem(str);
			
			str = String.format("%02d:30",i);
			heure.addElement(str);
			cmbPAHeureDeb.addItem(str);
		}
		str = String.format("%02d:00",24);
		heure.addElement(str);
		//cmbPAHeureDeb.addItem(str);

		cmbPAHeureDeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSelectionHeureDebut();
			}
		});

		cmbPAHeureFin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSelectionHeureFin();
				
			}

		});

		btnAjouterPA = new JButton(TRADUCTION.getString("activite.heure.ajouter"));
		btnAjouterPA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAjouterPeriodeTravail();
			}
		});
		btnAjouterPA.setEnabled(false);
		btnAjouterPA.setBounds(449, 151, 118, 23);
		contentPanel.add(btnAjouterPA);
		
		JScrollPane scrollPanePresence = new JScrollPane();
		scrollPanePresence.setBounds(30, 185, 537, 114);
		contentPanel.add(scrollPanePresence);
		
		try {
			modelTableauPresence = new TableauModelPresence();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tablePresence = new JTable(modelTableauPresence);
		tablePresence.setName("tablePresence");
		
		tablePresence.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPanePresence.setViewportView(tablePresence);

			
		
		JPopupMenu popupMenuPresence = new JPopupMenu();
		addPopup(tablePresence, popupMenuPresence);
		
		popupMenuPresence.setBounds(0, 0, 105, 50);
		
		mntmSupprimerPresence = new JMenuItem(TRADUCTION.getString("activite.heure.supprimer"));
		mntmSupprimerPresence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionSupprimerPeriodeTravail();
			}
		});
		mntmSupprimerPresence.setEnabled(false);
		popupMenuPresence.add(mntmSupprimerPresence);
		
		mntmSupprimerTout = new JMenuItem(TRADUCTION.getString("activite.heure.supprimertout"));
		mntmSupprimerTout .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionSupprimerToutPeriodeTravail();
			}
		});
		mntmSupprimerTout.setEnabled(false);
		popupMenuPresence.add(mntmSupprimerTout);

		tablePresence.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			    if (e.getClickCount() == 1){  
			        
			    }
			      
			}
		});
		tablePresence.getColumnModel().getColumn(0).setResizable(false);
		tablePresence.getColumnModel().getColumn(1).setResizable(false);
		tablePresence.getColumnModel().getColumn(2).setResizable(false);
		
		log.traceExit("initActiviteHeureDuJour()");
	}

	/**
	 * 
	 */
	protected void actionSelectionHeureFin() {

		// Authoriser l'enregistrement de la plage
		btnAjouterPA.setEnabled(true);
		
	}

	/**
	 * 
	 */
	protected void actionSupprimerToutPeriodeTravail() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param e
	 */
	protected void actionSelectionHeureDebut() {

		int index = cmbPAHeureDeb.getSelectedIndex();
		
		
		cmbPAHeureFin.removeAllItems();

		for (int i=index+1;i<heure.size();i++){
			
			boolean inPlage = false;
			
			for ( PlageIndex plageHoraire : this.listePlageHoraire ) {
				
				if (plageHoraire.isIN(i))
				{
					inPlage = true;
					break;
				}
			}
			
			if (! inPlage) 			
				cmbPAHeureFin.addItem(heure.get(i));
			else{
				// Stop remplissage de la combo de fin
				i=heure.size();
			}
			
			if (cmbPAHeureFin.getItemCount() !=0){
				cmbPAHeureFin.setEnabled(true);
			}
		}
		
	}

	/**
	 * 
	 */
	protected void actionSupprimerPeriodeTravail() {
	 
		log.traceEntry("actionSupprimerHorraireTravail()");

		int rowSel = tablePresence.getSelectedRow();

		
		try {
			modelTableauPresence.removeRow(rowSel,listePlageHoraire);
			
		} catch (Exception e) {
			MainView.showMsgError(PARAMVIEW,"err03.supp.presence","Impossible de supprimer ce créneau", e);
		}
		
		log.traceExit("actionSupprimerHorraireTravail()");
		
	}

	/**
	 * 
	 */
	protected void actionAjouterPeriodeTravail() {
		
		log.traceEntry("actionAjouterPeriodeTravail()");
		
		String debut;
		String fin;
		
		
		
		debut = cmbPAHeureDeb.getItemAt(cmbPAHeureDeb.getSelectedIndex());
		fin = cmbPAHeureFin.getItemAt(cmbPAHeureFin.getSelectedIndex());
		
		try {
			DataInfoPeriodeActivite periode = new DataInfoPeriodeActivite(debut, fin, -1);
			
			periode.setIndex(heure);			
			modelTableauPresence.addRow(periode);
			listePlageHoraire.add(new PlageIndex(periode.getIndexDebut(), periode.getIndexFin()));
			cmbPAHeureFin.removeAll();
			cmbPAHeureFin.setEnabled(false); 
			btnAjouterPA.setEnabled(false);
			

		} catch (Exception e) {

			MainView.showMsgError(PARAMVIEW,"err04.add.presence","Impossible d'ajouter ce créneau", e);

		}
		
		
		actionEnableButtonEnregistrer(true);
		log.traceExit("actionAjouterPeriodeTravail()");
		
	}

//	private int getIndex(String heureFin) {
//
//		boolean trouve=false;
//		int index=0;
//		for (index=0;index<cmbPAHeureDeb.getItemCount();index++){
//			
//			//System.out.println(" index="+index+" => "+cmbPAHeureDeb.getItemAt(index)+" ?= "+heureFin);
//			
//			if (cmbPAHeureDeb.getItemAt(index).equals(heureFin)){
//				trouve = true;
//				//System.out.println(" TROUVER index="+index+" !");
//				break;
//			}
//		}
//		if( trouve) return index;
//		return -1;
//	}




	/**
	 * 
	 */
	public void initVacation() {
		
		log.traceEntry("initVacation()");
		JSeparator sepVacances = new JSeparator();
		sepVacances.setBounds(10, 310, 574, 2);
		contentPanel.add(sepVacances);
		
		JLabel lblVacances = new JLabel(TRADUCTION.getString("vacation.periode")+" :");
		lblVacances.setBounds(30, 327, 283, 14);
		contentPanel.add(lblVacances);
		
		chckbxActiver = new JCheckBox(TRADUCTION.getString("vacation.activation"));
		chckbxActiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxActiver.setSelected(true);
		chckbxActiver.setBounds(467, 323, 97, 23);
		contentPanel.add(chckbxActiver);
		
		JLabel lblQ = new JLabel(TRADUCTION.getString("vacation.date.debut")+" : ");
		lblQ.setBounds(30, 352, 131, 14);
		contentPanel.add(lblQ);

		txtDateDebut = new JTextField();
		txtDateDebut.setBounds(30, 379, 138, 20);
		contentPanel.add(txtDateDebut);
		txtDateDebut.setColumns(10);

		
		JButton btnDebutVacation = new JButton("");
		btnDebutVacation.setBounds(171, 378, 20, 22);
		btnDebutVacation.setIcon(new ImageIcon("src/images/cal216.png"));

		contentPanel.add(btnDebutVacation);
		
		setLkDateStart(new LinkTxtBtnCalendar(this, txtDateDebut, btnDebutVacation,"yyyy-MM-dd"));
		
		JLabel lblFinVac = new JLabel(TRADUCTION.getString("vacation.date.fin")+ " : ");
		lblFinVac.setBounds(234, 354, 146, 14);
		contentPanel.add(lblFinVac);

		txtDateFin = new JTextField();
		txtDateFin.setBounds(234, 379, 110, 20);
		contentPanel.add(txtDateFin);
		txtDateFin.setColumns(10);
		
		JButton btnFinVacation = new JButton("");
		btnFinVacation.setBounds(347, 378, 20, 22);
		btnFinVacation.setIcon(new ImageIcon("src/images/cal216.png"));
		contentPanel.add(btnFinVacation);

		setLkDateStop(new LinkTxtBtnCalendar(this, txtDateFin, btnFinVacation,"yyyy-MM-dd"));
				
		JButton btnAjouterVacation = new JButton(TRADUCTION.getString("vacation.ajouter"));
		btnAjouterVacation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAjouterVacation();
			}
		});
		btnAjouterVacation.setBounds(451, 378, 118, 23);
		contentPanel.add(btnAjouterVacation);
		
		
		
		JScrollPane scrollPaneVacation = new JScrollPane();
		scrollPaneVacation.setBounds(30, 412, 539, 114);
		contentPanel.add(scrollPaneVacation);

		try {
			modelTableauVacation = new TableauModelVacation();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tableVacation = new JTable(modelTableauVacation);
		tableVacation.setName("tableVacation");
		tableVacation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableVacation.getColumnModel().getColumn(0).setPreferredWidth(130);
		tableVacation.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneVacation.setViewportView(tableVacation);

		
		JPopupMenu popupMenuVacation = new JPopupMenu();
		addPopup(tableVacation, popupMenuVacation);
		
		mntmSupprimerVacation = new JMenuItem("Supprimer");
		mntmSupprimerVacation.setEnabled(false);
		mntmSupprimerVacation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSupprimerVacation();
			}
			
		});
		mntmSupprimerVacation.setMnemonic('S');
		popupMenuVacation.add(mntmSupprimerVacation);
		
//		JMenuItem mntmEditerVacation = new JMenuItem("Editer");
//		mntmEditerVacation.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				actionEditerVacation();
//			}
//		});
//		mntmEditerVacation.setMnemonic('E');
//		mntmEditerVacation.setMnemonic(KeyEvent.VK_E);
//		popupMenuVacation.add(mntmEditerVacation);
		
		log.traceExit("initVacation()");
	}

	/**
	 * 
	 */
	protected void actionAjouterVacation() {
	
		log.traceEntry("actionAjouterVacation()");
		
		if (ValidationVacation()){
			try {
				modelTableauVacation.addRow(new DataInfoVacation(txtDateDebut.getText(), txtDateFin.getText(), -1));
			} catch (Exception e) {
				MainView.showMsgError(PARAMVIEW,"err05.add.vacation","Impossible d'ajouter ce créneau", e);
			}
		}else{
			// Message d'erreur
			MainView.showMsgUser(PARAMVIEW, "1001", "La date de début doit être inférieur à la date de fin de vacation", "Erreur de saisie"); 
		}
		
		actionEnableButtonEnregistrer(true);
		
		log.traceExit("actionAjouterVacation()");
	}



	/**
	 * @return
	 * @throws ParseException 
	 */
	private boolean ValidationVacation() {
		
		
		// Il ne peut pas y avoir d'erreur de saisie puisque les dates sont sélectionnées depuis un calendrier
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateDebut = sdf.parse(txtDateDebut.getText());
			Date dateFin = sdf.parse(txtDateFin.getText());
			return dateDebut.before(dateFin) || dateDebut.equals(dateFin);

		} catch (ParseException e) {
			// Ce bloque ne sera jamais utilisé SAUF en cas d'erreur de codage ....
			System.out.println("ValidationVacation.ValidationVacation ERREUR DE CODE");  
			e.printStackTrace();
			return false;
		}
		
 
	}

	/**
	 * 
	 */
	protected void actionSupprimerVacation() {

		log.traceEntry("actionSupprimerVacation()");
		int rowSel = tableVacation.getSelectedRow();
		
		try {
			modelTableauVacation.removeRow(rowSel);
		} catch (Exception e) {
			MainView.showMsgError(PARAMVIEW,"err06.supp.vacation","Impossible de supprimer ce créneau", e);
		}
		log.traceExit("actionSupprimerVacation()");
		
		
	}

	/**
	 * 
	 */
	public void initParam() {
		
		log.traceEntry("initParam()");

//		JLabel lblProfil = new JLabel("Profil :");
//		lblProfil.setBounds(258, 11, 118, 14);
//		contentPanel.add(lblProfil);
//
//		JComboBox cmbProfil = new JComboBox();
//		cmbProfil.setModel(new DefaultComboBoxModel(new String[] {"D\u00E9butant", "Interm\u00E9diaire", "Expert"}));
//		cmbProfil.setBounds(258, 36, 118, 20);
//		contentPanel.add(cmbProfil);
		
		log.traceExit("initParam()");
	}

	/**
	 * 
	 */
	protected void actionEnableButtonEnregistrer(boolean b) {
	
		log.traceEntry("actionEnableButton("+b+")");
		btnEnregistrer.setEnabled(b);
		log.traceExit("actionEnableButton(boolean b)");
		
	}

	/**
	 * @throws HeadlessException
	 */
	public void initComponents() {
		
		log.traceEntry("initComponents()");
		
		//setBounds(0, 0, 610, 703);
		setTitle(TRADUCTION.getString("titre"));
		
		// Centre la fenêtre
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (dimScreen.width / 2) - 610/2 ;
		int y = (dimScreen.height / 2) - 650/2 -100; 
		Point centreEcran = new Point(x,y);
		this.setLocation(centreEcran);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				actionQuitter();
			}
		});
		getContentPane().setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		log.traceExit("initComponents()");
	}

	/**
	 * 
	 */
	public void initActiviteJourDeLaSemaine() {
		
		log.traceEntry("initActiviteJourDeLaSemaine()");
		
		JLabel lblActivit = new JLabel(TRADUCTION.getString("activite.periode")+" : ");
		lblActivit.setBounds(30, 11, 537, 14);
		contentPanel.add(lblActivit);
		
		chckbxLundi = new JCheckBox(TRADUCTION.getString("sem.lundi"));
		chckbxLundi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxLundi.setBounds(80, 42, 97, 23);
		contentPanel.add(chckbxLundi);
		
		chckbxMardi = new JCheckBox(TRADUCTION.getString("sem.mardi"));
		chckbxMardi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxMardi.setBounds(201, 42, 97, 23);
		contentPanel.add(chckbxMardi);
		
		chckbxMercredi = new JCheckBox(TRADUCTION.getString("sem.mercredi"));
		chckbxMercredi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxMercredi.setBounds(300, 42, 97, 23);
		contentPanel.add(chckbxMercredi);
		
		chckbxJeudi = new JCheckBox(TRADUCTION.getString("sem.jeudi"));
		chckbxJeudi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxJeudi.setBounds(414, 42, 97, 23);
		contentPanel.add(chckbxJeudi);
		
		chckbxVendredi = new JCheckBox(TRADUCTION.getString("sem.vendredi"));
		chckbxVendredi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxVendredi.setBounds(80, 68, 97, 23);
		contentPanel.add(chckbxVendredi);
		
		chckbxSamedi = new JCheckBox(TRADUCTION.getString("sem.samedi"));
		chckbxSamedi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxSamedi.setBounds(201, 68, 97, 23);
		contentPanel.add(chckbxSamedi);
		
		chckbxDimanche = new JCheckBox(TRADUCTION.getString("sem.dimanche"));
		chckbxDimanche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEnableButtonEnregistrer(true);
			}
		});
		chckbxDimanche.setBounds(300, 68, 97, 23);
		contentPanel.add(chckbxDimanche);
		
		
		log.traceEntry("initActiviteJourDeLaSemaine()");
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
				int row = ((JTable)e.getComponent()).getSelectedRow();
				
				String name=e.getComponent().getName();
				if (row !=-1){
					
					 setEnablePopupMenu(name,true);
				}
				
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * @param name 
	 * @param b
	 */
	protected static void setEnablePopupMenu(String name, boolean b) {
		log.traceEntry("setEnablePopupMenu(String name="+name+", boolean b="+b+")");		
		if (name=="tablePresence"){
			mntmSupprimerPresence.setEnabled(b);
		}else{
			mntmSupprimerVacation.setEnabled(b);
		}
		log.traceExit("setEnablePopupMenu()");
		
	}

	/**
	 * 
	 */
	public void showDialog() {
		log.traceEntry("showDialog()");
		//Début du dialogue
		this.setVisible(true);
		log.traceExit("showDialog()");
	}


	/**
	 * @return the lkDateStart
	 */
	public LinkTxtBtnCalendar getLkDateStart() {
		return lkDateStart;
	}

	/**
	 * @param lkDateStart the lkDateStart to set
	 */
	public void setLkDateStart(LinkTxtBtnCalendar lkDateStart) {
		this.lkDateStart = lkDateStart;
	}

	/**
	 * @return the lkDateStop
	 */
	public LinkTxtBtnCalendar getLkDateStop() {
		return lkDateStop;
	}

	/**
	 * @param lkDateStop the lkDateStop to set
	 */
	public void setLkDateStop(LinkTxtBtnCalendar lkDateStop) {
		this.lkDateStop = lkDateStop;
	}
}
