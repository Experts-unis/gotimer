/**
 * 
 */
package vue;


import java.awt.Color;



import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;


import org.apache.logging.log4j.LogManager;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import model.TableauModelTimerProjet;
import model.DataInfoProjet;
import model.DataInfoTimer;
import model.DataRefLibelle;
import model.ModelManager;



import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import java.util.ResourceBundle;

/**
 * @author test
 *
 */
public class TimerProjetView extends DialogBase {
	
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("timerprojectview");
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TIMERPROJECTVIEW = "timerProjectView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TimerProjetView.class ); 
	
	private final JPanel contentPanel;
	
	ControlManager controlManager;
	private ProjectView fDialogProjet;
	
	private DialogCreatTimeView fDialogNew;
	
	
	TableauModelTimerProjet modelTableau;
	private JTable tableau;
	private static JMenuItem mntmEditer;
	private static JMenuItem mntmSupprimer;
	private JComboBox<DataInfoProjet> cmbProjet;
	private JComboBox<DataRefLibelle> cmbUnite;
	private ModelManager managerModel;
	private boolean ecouter;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TimerProjetView dialog = new TimerProjetView(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TimerProjetView(JDialog parent) {
		super(parent,"Les temps du projet",true,984, 656);
		
		log.traceEntry("TimerProjetView(JDialog parent)") ;
		contentPanel = new JPanel();
		setName("TimerProjetView");
		this.controlManager = Principale.ctrlManager;
		managerModel = this.controlManager.getModelManager();
		
		initComponents();
		
		setEcouter(false);
		
		int indexSelected=0;
		try {
			indexSelected = managerModel.loadProjetInCombo(this.cmbProjet,false );
			managerModel.loadUniteInCombo(cmbUnite);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(TIMERPROJECTVIEW,"err01.projets","Impossible de charger les projets ", e);
		}
		
		
		setEcouter(true);
		
		cmbProjet.setSelectedIndex(indexSelected);
		cmbProjet.addItem(new DataInfoProjet("Tous"));
		cmbUnite.setSelectedIndex(1);
		log.traceExit("TimerProjetView(JDialog parent)") ;
	}

	/**
	 * 
	 */
	public void initButton() {

		log.traceEntry("initButton()");
		
		
		JButton btnNouveau = new JButton(TRADUCTION.getString("TimerProjetView.btnNouveau.text")); //$NON-NLS-1$
		btnNouveau.setBounds(870, 44, 89, 23);
		contentPanel.add(btnNouveau);
		
		btnNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnNouveau();
			}
		});
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 579, 960, 33);
		getContentPane().add(buttonPane);
		buttonPane.setLayout(null);

		JButton btnProjet = new JButton(TRADUCTION.getString("TimerProjetView.btnProjet.text")); //$NON-NLS-1$
		btnProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionBtnProjets();
			}
		});
		btnProjet.setBounds(10, 5, 89, 23);
		buttonPane.add(btnProjet);

		JButton btnRetour = new JButton(TRADUCTION.getString("TimerProjetView.btnRetour.text")); //$NON-NLS-1$
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnRetour.setBounds(861, 5, 89, 23);
		btnRetour.setActionCommand(TRADUCTION.getString("TimerProjetView.btnRetour.actionCommand")); //$NON-NLS-1$
		buttonPane.add(btnRetour);
		


		log.traceExit("initButton()");
	}

	/**
	 * 
	 */
	public void initTable() {
		
		log.traceEntry("initTable()");

			try {
				modelTableau = new TableauModelTimerProjet(controlManager);
			} catch (Exception e1) {
				MainView.showMsgError(TIMERPROJECTVIEW,"err02.projets","Impossible de charger les données timers ", e1);
			}
			tableau = new JTable(modelTableau);
			tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			//tableau.setRowHeight(0, 20);
			tableau.setRowHeight(20);
			
			DefaultTableCellRenderer rendererLeft = new DefaultTableCellRenderer();
			rendererLeft.setHorizontalAlignment(SwingConstants.LEFT);
			
			tableau.getColumnModel().getColumn(1).setCellRenderer(rendererLeft);
			tableau.getColumnModel().getColumn(2).setCellRenderer(rendererLeft);
			
			tableau.getColumnModel().getColumn(1).setPreferredWidth(100);
			tableau.getColumnModel().getColumn(1).setResizable(false);

			tableau.getColumnModel().getColumn(2).setPreferredWidth(200);
			tableau.getColumnModel().getColumn(2).setResizable(false);

			
			DefaultTableCellRenderer rendererRight = new DefaultTableCellRenderer();
			rendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
			tableau.getColumnModel().getColumn(3).setCellRenderer(rendererRight);
			tableau.getColumnModel().getColumn(5).setCellRenderer(rendererRight);
			
			tableau.getColumnModel().getColumn(7).setCellRenderer(rendererRight);
			tableau.getColumnModel().getColumn(8).setCellRenderer(rendererRight);

			
			
			DefaultTableCellRenderer rendererCenter = new DefaultTableCellRenderer();
			rendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
			tableau.getColumnModel().getColumn(0).setCellRenderer(rendererCenter);
			tableau.getColumnModel().getColumn(0).setPreferredWidth(30);
			tableau.getColumnModel().getColumn(0).setResizable(false);

			
			
			
			tableau.getColumnModel().getColumn(4).setCellRenderer(rendererCenter);
			tableau.getColumnModel().getColumn(6).setCellRenderer(rendererCenter);
			
			
			
			tableau.getTableHeader().setBackground(Color.LIGHT_GRAY);
			tableau.getTableHeader().setForeground(Color.BLACK);
			Dimension dim=tableau.getTableHeader().getPreferredSize();
			dim.setSize(dim.width,70);
			tableau.getTableHeader().setPreferredSize(dim);
			
			
			
			tableau.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

				    if (e.getClickCount() == 2){  
				        actionEditer();
				    }
				      
				}
			});
			
			JScrollPane scrollPane = new JScrollPane(tableau);
			scrollPane.setBounds(10, 86, 949, 480);
			contentPanel.add(scrollPane);
			
			JPopupMenu popupMenu = new JPopupMenu();
			addPopup(tableau, popupMenu);
			
			mntmEditer = new JMenuItem(TRADUCTION.getString("TimerProjetView.mntmEditer.text")); //$NON-NLS-1$
			mntmEditer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionEditer() ;
				}
			});
			popupMenu.add(mntmEditer);
			
			mntmSupprimer = new JMenuItem(TRADUCTION.getString("TimerProjetView.mntmSupprimer.text")); //$NON-NLS-1$
			mntmSupprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actionSupprimer();
				}
			});
			//mntmSupprimer.setEnabled(false);
			popupMenu.add(mntmSupprimer);
			
			
			
			setEnablePopupMenu(false);
			//add(scrollPane);

		log.traceExit("initTable()");
	}

	/**
	 * 
	 */
	public void initFiltre() {
		
		log.traceEntry("initFiltre()");
		
		cmbProjet = new JComboBox<DataInfoProjet>();
		cmbProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionChangeProjet();
			}
		});
		
		cmbProjet.setBounds(10, 47, 297, 20);
		contentPanel.add(cmbProjet);
		
		cmbUnite = new JComboBox<DataRefLibelle>();

		cmbUnite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionChangeUnite();
			}
			
		});
		
		//cmbUnite.setModel(new DefaultComboBoxModel<String>(new String[] {"Jour(s)", "Heure(s)"}));			
		cmbUnite.setBounds(342, 47, 88, 20);
		
		contentPanel.add(cmbUnite);
		
		JLabel lblProjet = new JLabel(TRADUCTION.getString("TimerProjetView.lblProjet.text")); //$NON-NLS-1$
		lblProjet.setBounds(10, 20, 173, 14);
		contentPanel.add(lblProjet);
		
		JLabel lblUnit = new JLabel(TRADUCTION.getString("TimerProjetView.lblUnit.text")); //$NON-NLS-1$
		lblUnit.setBounds(342, 20, 88, 14);
		contentPanel.add(lblUnit);
		log.traceExit("initFiltre()");
	}
	/**
	 * 
	 */
	protected void actionChangeProjet() {
		
		log.traceEntry("actionChangeProjet()");
		
		if (!isEcouter()) return;
		
		int index = cmbProjet.getSelectedIndex();

		DataInfoProjet infoProjet = cmbProjet.getItemAt(index);

		actionChangeUnite();
		try {
			modelTableau.reload(infoProjet.getId());
		} catch (Exception e) {
			MainView.showMsgError(TIMERPROJECTVIEW,"err03.projets","Impossible de charger les données timers du projet", e);
		}
		
		//infoProjet.getChargeTotale();
		log.traceExit("actionChangeProjet()");
	}

	/**
	 * 
	 */
	protected void actionChangeUnite() {
		log.traceEntry("actionChangeUnite()");
		modelTableau.setUnite(cmbUnite.getSelectedIndex());
		log.traceExit("actionChangeUnite()");
	}

	/**
	 * @param b
	 */
	
	private static void setEnablePopupMenu(boolean b) {
		log.traceEntry("setEnablePopupMenu("+b+")");
		mntmEditer.setEnabled(b);
		mntmSupprimer.setEnabled(b);
		
		log.traceExit("setEnablePopupMenu(boolean b)");
		
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
				//System.out.println(" row selected " + row);
				if (row !=-1){
					 setEnablePopupMenu(true);
				}
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}


	
	/**
	 * 
	 */
	protected void actionSupprimer() {
		log.traceEntry("actionSupprimer()");
		int rowSel = tableau.getSelectedRow();
		
		try {
			modelTableau.removeRow(rowSel);
		} catch (Exception e) {
			MainView.showMsgError(TIMERPROJECTVIEW,"err04.projets","Impossible de supprimer ces données timers", e);
		}
		setEnablePopupMenu(false);
		log.traceExit("actionSupprimer()");
	}

	/**
	 * 
	 */
	protected void actionEditer() {
		
		log.traceEntry("actionEditer()") ;
		
		// Quelle ligne est sélectionnées ?
		int rowSel = tableau.getSelectedRow();
		
		DataInfoTimer element =(DataInfoTimer) modelTableau.getDataItem(rowSel);
		
		element.setRow(rowSel);
		
		
		if (fDialogNew == null){
			fDialogNew = new DialogCreatTimeView(this,Principale.ctrlManager,modelTableau);
		} 
		
		fDialogNew.showDialog(element);
		log.traceExit("actionEditer()") ;
		
	}

	/**
	 * @param controlManager
	 */
	public void reload() {
		
		log.traceEntry("reload()") ;
		
		actionChangeProjet();
		log.traceExit("reload()") ;
		
	}
	/**
	 * 
	 */
	public void refresh() {
		
		log.traceEntry("refresh()") ;
		modelTableau.refresh();
		log.traceExit("refresh()") ;
	}
	/**
	 * 
	 */
	public void initComponents() {
		log.traceEntry("initComponents()") ;
		
		//setBounds(100, 100, 984, 656);
		getContentPane().setLayout(null);
		
		
		contentPanel.setBounds(0, 0, 970, 571);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		initTable();
		initFiltre();
		initButton();
		log.traceExit("initComponents()") ;

	}
	/**
	 * 
	 */
	private
	void actionBtnNouveau() {
		log.traceEntry("actionBtnNouveau()") ;
		
		if (fDialogNew == null){
			fDialogNew = new DialogCreatTimeView(this,Principale.ctrlManager,modelTableau);
		} 
		
		fDialogNew.showDialog(null);
		log.traceExit("actionBtnNouveau()") ;
		
	}
	/**
	 * 
	 */
	private void actionBtnProjets() {
		log.traceEntry("actionBtnProjets()") ;
		if (fDialogProjet == null){
			fDialogProjet = new ProjectView(this);
		} 
		
		fDialogProjet.showDialog();
		
		actionChangeProjet();
		
		
		// System.out.println("actionBtnProjets: OUT )" );
		log.traceExit("actionBtnProjets()") ;
		
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
		log.traceEntry("setEcouter(boolean ecouter="+ecouter+")");
		
		this.ecouter = ecouter;
		
		log.traceExit("setEcouter(boolean ecouter)");
	}


}
