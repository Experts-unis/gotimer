/**
 * 
 */
package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import model.DataInfoFonction;
import model.DataInfoProjet;
import model.ListModelFonction;
import model.ListModelProjet;
import model.ProjetModelManager;


import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

/**
 * @author test
 *
 */
public abstract class ProjectListView extends DialogBase  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanelPrincipal;
	private ControlManager controlManager;
	
	protected JList<DataInfoProjet> listProjet;
	protected ListModelProjet listModelProjet;
	protected  DataInfoProjet infoProjetSelection;
	private JScrollPane scrollPaneProjet;
	
	
	protected JList<DataInfoFonction> listFonctions;
	ListModelFonction listModelFonction;
	protected DataInfoFonction infoFonctionSelection;
	private JScrollPane scrollPaneFonc;
	
	
	@SuppressWarnings("unused")
	private boolean modeAffichageProjet;
	@SuppressWarnings("unused")
	private boolean modeAffichageFonction;
	private boolean ecouter;
	protected ProjetModelManager managerModel;
	protected JMenuItem mntmProjet;
	protected JMenuItem mntmFonction;

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param w
	 * @param h
	 */
	public ProjectListView(JDialog parent,String title) {
		super(parent, title, true,1050, 657);
		
		this.controlManager=Principale.ctrlManager;
		this.setName("ArchiveView");
		
		initComponents(title);
		initListeProjet();
		initListeFonction();
		
		
		setEcouter(false);		
		
		try {
			managerModel = (ProjetModelManager)controlManager.getModelManager(this.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		loadProjet();
		
	
		setEcouter(true);		
		listProjet.setSelectedIndex(0);
		
	}

	protected abstract void loadProjet();

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
	 * 
	 */
	private void initComponents(String title) {
		
		log.traceEntry("initComponents()");

		//setBounds(0, 0, 1050, 657);
		setResizable(false);
		getContentPane().setLayout(null);
		contentPanelPrincipal = new JPanel();
		contentPanelPrincipal.setBounds(0, 0, 1044, 655);
		contentPanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanelPrincipal);
		contentPanelPrincipal.setLayout(null);
		
		
		JLabel lblTitre = new JLabel(title);
		lblTitre.setForeground(Color.BLUE);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTitre.setBackground(Color.WHITE);
		lblTitre.setBounds(10, 11, 1014, 23);
		contentPanelPrincipal.add(lblTitre);
		
		log.traceExit("initComponents()");
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
	
	private void initListeProjet() {
		log.traceEntry("initListeProjet()");
		
		// Création du model
		try {
			listModelProjet = new ListModelProjet();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Création de la liste		
		listProjet = new JList<DataInfoProjet>(listModelProjet);
		listProjet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		listProjet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste des projets ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 128)));
		listProjet.setToolTipText("Liste des projets en cours");
		listProjet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listProjet.setLayoutOrientation(JList.VERTICAL);


		
		//list.addListSelectionListener(this);
		listProjet.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				actionSelectionProjet(e);
			}
		});



		initPopUpListeProjet();

		
		
		scrollPaneProjet = new JScrollPane();
		scrollPaneProjet.setBounds(10, 60, 502, 521);		

		scrollPaneProjet.setViewportView(listProjet);

		contentPanelPrincipal.add(scrollPaneProjet);
		
		log.traceExit("initListeProjet()");
	}

	/**
	 * 
	 */
	private void initPopUpListeProjet() {
		JPopupMenu popupMenuProjet = new JPopupMenu();

		addPopup(listProjet, popupMenuProjet);



		mntmProjet = new JMenuItem(getTitreMenu());
		mntmProjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionMntmProjet();
			}


		});

		mntmProjet.setEnabled(false);
		popupMenuProjet.add(mntmProjet);

		log.traceExit("initPopUpListeProjet()");
		
	}
	/**
	 * @return
	 */
	protected abstract String getTitreMenu() ;
	
	protected void actionMntmProjet() {
		
		try {
			
			actionProjet();
			
			listModelProjet.remove(infoProjetSelection.getIndex()); 
			
			if (listModelProjet.size() != 0)
				listProjet.setSelectedIndex(0);
			else {
				mntmProjet.setEnabled(false);
				listModelFonction.removeAllElements();
			}
		} catch (ExceptionTraitementSQL e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @throws ExceptionTraitementSQL
	 */
	protected abstract void actionProjet() throws ExceptionTraitementSQL ;

		/**
	 * @param e
	 */
	protected void actionSelectionProjet(ListSelectionEvent e) {
		log.traceEntry("actionSelectionProjet(ListSelectionEvent e)");

		if (! isEcouter()) return;




		if (e.getValueIsAdjusting() == false) {

			if (listProjet.getSelectedIndex() == -1) {
				//No selection, disable fire button.
				
				mntmProjet.setEnabled(false);
				infoProjetSelection=null;

			} else {
				//Selection, enable the fire button.
				//            	setEnabledPopUpProjet(true);
				infoProjetSelection = listModelProjet.getElementAt(listProjet.getSelectedIndex());
				infoProjetSelection.setIndex(listProjet.getSelectedIndex());

				mntmProjet.setEnabled(true);
				setTitle("Projet : " + infoProjetSelection.getLibelle());
				setEcouter(false);

				loadFonction();
				setEcouter(true);
				if (listModelFonction.size() != 0){
					listFonctions.setSelectedIndex(0);
					
				}else{
					
					
				}

			}
		}
		log.traceExit("actionSelectionProjet()");

	}

	/**
	 * 
	 */
	protected abstract void loadFonction() ;
//	{
//    	try {
//    		
//			managerModel.loadFonctionsArchiveInView(listModelFonction,infoProjetSelection);
//		} catch (ExceptionTraitementSQL e1) {
//			
//			MainView.showMsgError("","err04.projets","Impossible de charger les fonctions de ce projet ", e1);
//		}
//		
//	}



	
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
		listFonctions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste des tâches du projet", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		listFonctions.setLayoutOrientation(JList.VERTICAL);
		listFonctions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFonctions.setToolTipText("Liste des tâches du projet sélectionné");
	

		
		listFonctions.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				actionSelectionFonction(e);
			}

		});
			
		initPopUpListeFonction();
		

		scrollPaneFonc = new JScrollPane();
		scrollPaneFonc.setBounds(522, 60, 502, 521);
		scrollPaneFonc.setViewportView(listFonctions);

		contentPanelPrincipal.add(scrollPaneFonc);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnRetour.setBounds(935, 592, 89, 23);
		contentPanelPrincipal.add(btnRetour);
	

		
		log.traceExit("initListeFonction()");
	}

	/**
	 * @param e
	 */
	protected void actionSelectionFonction(ListSelectionEvent e) {
		log.traceEntry("actionSelectionFonction(ListSelectionEvent e)");
		if (! isEcouter()) return;
		if (listModelFonction.size()==0) return;
		if (listFonctions.getSelectedIndex()==-1) return;

		
		
		mntmFonction.setEnabled(true);
		infoFonctionSelection = listModelFonction.getElementAt(listFonctions.getSelectedIndex());
		infoFonctionSelection.setIndex(listFonctions.getSelectedIndex());
		log.traceExit("actionSelectionFonction()");

	}


	/**
	 * 
	 */
	private void initPopUpListeFonction() {
		log.traceEntry("initPopUpListeFonction()");
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(listFonctions, popupMenu);

		
		
		
		mntmFonction = new JMenuItem(getTitreMenu());
		mntmFonction.setEnabled(false);
		mntmFonction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionMntmFonction();
			}
		});
		mntmFonction.setEnabled(false);
		popupMenu.add(mntmFonction);
		
		

		log.traceExit("initPopUpListeFonction()");
		
	}

	protected void actionMntmFonction() {

		try {
			actionFonction();
			
			
			listModelFonction.remove(infoFonctionSelection.getIndex()); 
			
			if (listModelFonction.size() != 0)
				listFonctions.setSelectedIndex(0);
			else {
				mntmFonction.setEnabled(false);
				listModelProjet.remove(infoProjetSelection.getIndex()); 
				
			}
		} catch (ExceptionTraitementSQL e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	protected abstract void actionFonction() throws ExceptionTraitementSQL ;


}
