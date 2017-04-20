/**
 * 
 */
package vue;


import java.awt.Color;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.DefaultCellEditor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableColumn;

import org.apache.logging.log4j.LogManager;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import model.ComplexiteModelManager;
import model.DataInfoProjet;
import model.DataRefLibelle;
import model.EstimationModelManager;
import model.ProjetModelManager;
import model.TableauModelEstimation;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.util.ResourceBundle;
import javax.swing.JTextField;


/**
 * @author test
 *
 */
public class EstimationView extends DialogBase {
	
	
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("estimationview");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ESTIMATIONVIEW = "estimationView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( EstimationView.class ); 
	private final JPanel contentPanel = new JPanel();
	ControlManager controlManager;
	EstimationModelManager managerModel;
	private JTable tableau;
	private TableauModelEstimation modelTableau;
	private JComboBox<DataInfoProjet> cmbProjet;
	private boolean ecouter;
	private JComboBox<DataRefLibelle> cmbUnite;
	private JComboBox<DataRefLibelle> cmbComplex;
	
	private JComboBox<DataRefLibelle> cmbPareto;
	private JComboBox<DataRefLibelle> cmbEisenhower;
	private JComboBox<DataRefLibelle> cmbMotivation;
	private static JMenuItem mntmArchive;
	private static JMenuItem mntmReport;
	private JTextField txtDateFinEsperee;
	private JTextField txtDateFinEstimee;
	private JTextField txtConsommation;
	private JTextField txtEstimation;
	private JTextField txtRaf;

	private DataInfoProjet currentinfoProjet;

	private JButton btnEnregistrer;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EstimationView dialog = new EstimationView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EstimationView() {
		super(null, TRADUCTION.getString("EstimationView.title.text"),true,1050, 725);
		
		log.traceEntry("EstimationView()") ;
		
		this.controlManager = Principale.ctrlManager;
		setName("EstimationView"); 
		
		try {
			managerModel = (EstimationModelManager) controlManager.getModelManager(this.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		initComponents();
		initFiltre();
		initTableau();
		initButton();
		
		setEcouter(false);

		ComplexiteModelManager manager = null;
		try {
			
			manager = (ComplexiteModelManager) controlManager.getModelManager("DialogComplexiteView");
			
			manager.loadUniteInCombo(cmbUnite);

		} catch (Exception e) {

			MainView.showMsgError(ESTIMATIONVIEW,"err10.projets","EstimationView() : Impossible charger la table tref_unite ", e);

		}
		
		try {
			manager.loadComplexiteInCombo(cmbComplex);
		} catch (Exception e) {

			MainView.showMsgError(ESTIMATIONVIEW,"err10.projets","EstimationView() : Impossible charger la table proterties tref_complexite ", e);

		}
		
		try {
			 ProjetModelManager managerProjet = (ProjetModelManager)controlManager.getModelManager("ProjectView");

			 managerProjet.loadParetoInCombo(cmbPareto) ;
			 managerProjet.loadPlaisirInCombo(cmbMotivation);
			 managerProjet.loadEisenhowerInCombo(cmbEisenhower);
			
		} catch (Exception e) {

			MainView.showMsgError(ESTIMATIONVIEW,"err10.projets","EstimationView() Impossible charger la table proterties tref_pareto , tref_motivation, tref_Eisenhower", e);

		}
		
		chargeListeProjets();
		
		cmbUnite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionChangeUnite();
			}
		});
		
		setEcouter(true);
		
		
		log.traceExit("SuiviProjetView()") ;
		
	}

	/**
	 * @throws HeadlessException
	 */
	private void chargeListeProjets() throws HeadlessException {
		setEcouter(false);
		try {
			managerModel.loadProjetInCombo(this.cmbProjet,true );

		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(ESTIMATIONVIEW,"err01.projets","Impossible de charger les projets ", e);
		}
		setEcouter(true);
	}
	/**
	 * 
	 */
	public void initTableau() {
		
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 1014, 557);
		contentPanel.add(scrollPane);
		
		try {
			modelTableau = new TableauModelEstimation(managerModel);
		} catch (ExceptionTraitementSQL e1) {
			MainView.showMsgError(ESTIMATIONVIEW,"err02.projets","Impossible de charger les données d'estimation", e1);
		}
		tableau = new JTable(modelTableau);
		//modelTableau.setLabelModif(lblEnregistrement);
		modelTableau.setViewParent(this);

		// Agrandir et colorer l'entête du tableau
		tableau.getTableHeader().setBackground(Color.LIGHT_GRAY);
		tableau.getTableHeader().setForeground(Color.BLACK);
		
		Dimension dim=tableau.getTableHeader().getPreferredSize();
		dim.setSize(dim.width,70);
		tableau.getTableHeader().setPreferredSize(dim);

		// Agrandir toutes les ligne du tableau
		tableau.setRowHeight(20);

		//"Fonction", "Pareto","Eisenhower", "Motivation","Estimation initiale","Complexité", "Estimation","Consommé","Reste à faire"

		final int FONCTION=0;
		final int PARETO=1;
		final int EISENHOWER=2;
		final int MOTIVATION=3;
		
		
		final int COMPLEX=4;
		
		final int CONSOMME=5;
		final int ESTIMATION=6;
		final int RESTEAFAIRE=7;
		final int TOTAL=8;
		
		
		
		
		//TODO Ajouter les combox pour pareto, eisen, motivation
		
		
		// Change l'éditeur de la colonne PARETO
		// Créer un JComboBox pour PARETO
		cmbPareto = new JComboBox<DataRefLibelle>();
		
		TableColumn nameColumn = tableau.getColumn(tableau.getColumnName(PARETO));		
		nameColumn.setCellEditor(new DefaultCellEditor(cmbPareto));
		
		
		// Change l'éditeur de la colonne EISENHOWER
		// Créer un JComboBox pour EISENHOWER
		cmbEisenhower = new JComboBox<DataRefLibelle>();
		
		nameColumn = tableau.getColumn(tableau.getColumnName(EISENHOWER));		
		nameColumn.setCellEditor(new DefaultCellEditor(cmbEisenhower));
		
		
		// Change l'éditeur de la colonne MOTIVATION
		// Créer un JComboBox pour MOTIVATION
		cmbMotivation = new JComboBox<DataRefLibelle>();
		
		nameColumn = tableau.getColumn(tableau.getColumnName(MOTIVATION));		
		nameColumn.setCellEditor(new DefaultCellEditor(cmbMotivation));


		// Change l'éditeur de la colonne Complexité
		
		// Créer un JComboBox pour la complexite
		cmbComplex = new JComboBox<DataRefLibelle>();

		nameColumn = tableau.getColumn(tableau.getColumnName(COMPLEX));		
		nameColumn.setCellEditor(new DefaultCellEditor(cmbComplex));
		
		// Change le rendu de la colonne Complexité
//		ComboRenderer comboRenderer = new ComboRenderer();
//		tableau.getColumnModel().getColumn(2).setCellRenderer(comboRenderer);
		
		
		// Fonction
		tableau.getColumnModel().getColumn(FONCTION).setPreferredWidth(150);
		tableau.getColumnModel().getColumn(FONCTION).setResizable(false);

		// Pareto
		tableau.getColumnModel().getColumn(PARETO).setPreferredWidth(20);
		tableau.getColumnModel().getColumn(PARETO).setResizable(false);

		// Eisenhower
		tableau.getColumnModel().getColumn(EISENHOWER).setPreferredWidth(180);
		tableau.getColumnModel().getColumn(EISENHOWER).setResizable(false);
		
		
		DefaultTableCellRenderer rendererRight = new DefaultTableCellRenderer();
		rendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		DefaultTableCellRenderer rendererCenter = new DefaultTableCellRenderer();
		rendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		tableau.getColumnModel().getColumn(PARETO).setCellRenderer(rendererCenter);
		tableau.getColumnModel().getColumn(MOTIVATION).setCellRenderer(rendererCenter);
		tableau.getColumnModel().getColumn(COMPLEX).setCellRenderer(rendererCenter);
		
		//tableau.getColumnModel().getColumn(ESTIMATION_INIT).setCellRenderer(rendererRight);
		tableau.getColumnModel().getColumn(ESTIMATION).setCellRenderer(rendererRight);
		tableau.getColumnModel().getColumn(CONSOMME).setCellRenderer(rendererCenter);
		tableau.getColumnModel().getColumn(RESTEAFAIRE).setCellRenderer(rendererRight);
		tableau.getColumnModel().getColumn(TOTAL).setCellRenderer(rendererCenter);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tableau, popupMenu);
		
		mntmArchive = new JMenuItem(TRADUCTION.getString("EstimationView.mntmArchive.text")); //$NON-NLS-1$
		mntmArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionArchive();
			}
		});
		
		
		mntmReport = new JMenuItem(TRADUCTION.getString("EstimationView.mntmReport.text"));
		mntmReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		popupMenu.add(mntmReport);
		popupMenu.add(mntmArchive);
		scrollPane.setViewportView(tableau);
		
		setEnablePopupMenu(false);
	}
	/**
	 * 
	 */
	public void initFiltre() {
		
		JLabel lblProjet = new JLabel(TRADUCTION.getString("EstimationView.lblProjet.text")); //$NON-NLS-1$
		lblProjet.setBounds(10, 9, 130, 14);
		contentPanel.add(lblProjet);
		
		cmbProjet = new JComboBox<DataInfoProjet>();
		cmbProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChangeProjet();
			}
		});
		cmbProjet.setAutoscrolls(true);
		cmbProjet.setBounds(10, 34, 289, 20);
		contentPanel.add(cmbProjet);
		
		txtDateFinEsperee = new JTextField();
		txtDateFinEsperee.setText(""); 
		txtDateFinEsperee.setBounds(309, 34, 86, 20);
		contentPanel.add(txtDateFinEsperee);
		txtDateFinEsperee.setColumns(10);
		
		txtDateFinEstimee = new JTextField();
		txtDateFinEstimee.setText(TRADUCTION.getString("EstimationView.txtDateFinEstime.text")); //$NON-NLS-1$
		txtDateFinEstimee.setBounds(405, 34, 86, 20);
		contentPanel.add(txtDateFinEstimee);
		txtDateFinEstimee.setColumns(10);
		
		JLabel lblAttendu = new JLabel(TRADUCTION.getString("EstimationView.lblAttendu.text")); //$NON-NLS-1$
		lblAttendu.setBounds(309, 9, 86, 14);
		contentPanel.add(lblAttendu);
		
		JLabel lblDateEstimee = new JLabel(TRADUCTION.getString("EstimationView.lblDateEstimee.text")); //$NON-NLS-1$
		lblDateEstimee.setBounds(405, 9, 86, 14);
		contentPanel.add(lblDateEstimee);
		
		txtConsommation = new JTextField();
		txtConsommation.setText(TRADUCTION.getString("EstimationView.txtConso.text")); //$NON-NLS-1$
		txtConsommation.setBounds(602, 34, 86, 20);
		contentPanel.add(txtConsommation);
		txtConsommation.setColumns(10);
		
		JLabel lblConsomm = new JLabel(TRADUCTION.getString("EstimationView.lblConsomm.text")); //$NON-NLS-1$
		lblConsomm.setBounds(602, 9, 86, 14);
		contentPanel.add(lblConsomm);
		
		txtEstimation = new JTextField();
		txtEstimation.setText(TRADUCTION.getString("EstimationView.txtEstime.text")); //$NON-NLS-1$
		txtEstimation.setBounds(506, 34, 86, 20);
		contentPanel.add(txtEstimation);
		txtEstimation.setColumns(10);
		
		JLabel lblEstimation = new JLabel(TRADUCTION.getString("EstimationView.lblEstimation.text")); //$NON-NLS-1$
		lblEstimation.setBounds(501, 9, 86, 14);
		contentPanel.add(lblEstimation);
		
		txtRaf = new JTextField();
		txtRaf.setText(TRADUCTION.getString("EstimationView.txtRaf.text")); //$NON-NLS-1$
		txtRaf.setBounds(698, 34, 86, 20);
		contentPanel.add(txtRaf);
		txtRaf.setColumns(10);
		
		JLabel lblResteFaire = new JLabel(TRADUCTION.getString("EstimationView.lblResteFaire.text")); //$NON-NLS-1$
		lblResteFaire.setBounds(698, 9, 86, 14);
		contentPanel.add(lblResteFaire);
		
		
		
		JLabel lblUnit = new JLabel(TRADUCTION.getString("EstimationView.lblUnit.text")); //$NON-NLS-1$
		lblUnit.setBounds(933, 9, 91, 14);
		contentPanel.add(lblUnit);
		
		cmbUnite = new JComboBox<DataRefLibelle>();
		cmbUnite.setBounds(933, 34, 91, 20);
		contentPanel.add(cmbUnite);
		
	}



	/**
	 * 
	 */
	public void initButton() {
		log.traceEntry("initButton()");

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 642, 1034, 33);
		getContentPane().add(buttonPane);
		buttonPane.setLayout(null);

		JButton btnAnnuler = new JButton(TRADUCTION.getString("EstimationView.btnAnnuler.text")); //$NON-NLS-1$
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnAnnuler.setBounds(944, 5, 80, 23);
		buttonPane.add(btnAnnuler);
		
		btnEnregistrer = new JButton(TRADUCTION.getString("EstimationView.btnEnregistrer.text")); //$NON-NLS-1$
		btnEnregistrer.setEnabled(false);
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionBtnEnregistrer();
			}
		});
		btnEnregistrer.setBounds(821, 5, 113, 23);
		buttonPane.add(btnEnregistrer);
		log.traceExit("initButton()");
	}



	
	/**
	 * 
	 */
	protected void actionBtnEnregistrer() {
		try {
			modelTableau.saveData();
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(ESTIMATIONVIEW,"err03.estimations","Impossible d'enregistrer les données ", e);
		}
		setEnregistrerEnable(false); 
		setTitle(TRADUCTION.getString("EstimationView.title.text"));
		
		
	}

	/**
	 * 
	 */
	protected void actionArchive() {

		if (btnEnregistrer.isEnabled()){
			MainView.showMsgUser(ESTIMATIONVIEW, "user001.archive",  "Attention vous devez enregistrer vos estimations avant d'archiver.",  "Archiver");
			return;
		}
		
		int rowSel = tableau.getSelectedRow();
		try {
			modelTableau.archive(rowSel);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(ESTIMATIONVIEW,"err04.projets","Impossible d'archier le projets ", e);
		}
		setEnablePopupMenu(false);
		
	}

	/**
	 * 
	 */
	public void initComponents() {
		//setBounds(0, 0, 1050, 725);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 1034, 631);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		
		
		
		
	}

	/**
	 * 
	 */
	protected void actionChangeProjet() {

		if (!isEcouter()) return;
		
		int index = cmbProjet.getSelectedIndex();

		currentinfoProjet = cmbProjet.getItemAt(index);

		actionChangeUnite();
		try {
			modelTableau.reload(currentinfoProjet.getId());
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(ESTIMATIONVIEW,"err05.projets","Impossible de charger les données du projet ", e);
		}
		
		afficheInfoProjet();
		
		
	}

	/**
	 * 
	 */
	private void afficheInfoProjet() {
		txtDateFinEsperee.setText(currentinfoProjet.getDateSouhaiteeStr());
		txtDateFinEstimee.setText(currentinfoProjet.getDateFinEstimeeStr() );
		txtEstimation.setText(String.format("%2.00f", currentinfoProjet.getEstimationtotale()));
		txtConsommation.setText(String.format("%2.00f", currentinfoProjet.getChargetotale()) );
		txtRaf.setText(String.format("%2.00f", currentinfoProjet.getResteafairetotal()));
	}
	
	/**
	 * 
	 */
	protected void actionChangeUnite() {
		
		if (! isEcouter()) return;
		modelTableau.setUnite(cmbUnite.getSelectedIndex());
		currentinfoProjet.setUnite(cmbUnite.getSelectedIndex());
		afficheInfoProjet();
		
	}
	
	
	/* (non-Javadoc)
	 * @see vue.DialogBase#showDialog()
	 */
	@Override
	public void showDialog() {
		actionChangeProjet();
		super.showDialog();
	}


//	public class ComboRenderer extends JComboBox implements TableCellRenderer {
//
//
//		  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
//				
//			  addItem("Faible");
//			  addItem("Moyenne");
//			  addItem("Forte");
//			//  setOpaque(true);
//
//		    return this;
//
//		  }
//
//		}
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
				//System.out.println(" row selected " + row);
				if (row !=-1){
					 setEnablePopupMenu(true);
				}
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

/**
 * @param b
 */
protected static void setEnablePopupMenu(boolean b) {
	mntmArchive.setEnabled(b);
	mntmReport.setEnabled(b);
	
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
 * @param b
 */
public void setEnregistrerEnable(boolean b) {
	btnEnregistrer.setEnabled(b);
	
}
}
