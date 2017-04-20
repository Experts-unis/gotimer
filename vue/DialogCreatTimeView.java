/**
 * 
 */
package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;

import javax.swing.JLabel;


import controleur.ControlManager;
import controleur.ExceptionTraitementSQL;
import controleur.Principale;
import model.DataInfoFonction;
import model.DataInfoProjet;
import model.DataInfoTimer;
import model.TableauModelTimerProjet;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * @author test
 *
 */
public class DialogCreatTimeView extends DialogBase {
	
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("dcreattimeview");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CREATETIMERTVIEW = "DialogCreatTimeView";
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DialogCreatTimeView.class ); 
	private final JPanel contentPanel = new JPanel();
	private JTextField txtStartDate;
	private JTextField txtDateStop;
	
	private ControlManager controlManager;
	private TableauModelTimerProjet modelTable;
	private int idProjet;
	private JComboBox<DataInfoProjet> cmbProjets;
	private JComboBox<DataInfoFonction> cmbFonctions;
	private LinkCombo<DataInfoProjet, DataInfoFonction> lkProjetFonction;
	private LinkTxtBtnCalendar lkDateTimer;
	private JComboBox<String> cmbTimeStart;
	private LinkTxtBtnCalendar lkDateStart;
	private LinkTxtBtnCalendar lkDateStop;
	private JComboBox<String> cmbTimeStop;
	private DataInfoTimer modifElement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogCreatTimeView dialog = new DialogCreatTimeView(null,null,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogCreatTimeView(JDialog parent,ControlManager controlManager,TableauModelTimerProjet modelTable) {
		super(parent, TRADUCTION.getString("DialogCreatTimeView.title"), true,260,380);
		log.traceEntry("DialogTimeView(JDialog parent,ControlManager controlManager,TableModelDetail modelTable) ") ;
		
		this.controlManager=controlManager;
		this.modelTable=modelTable;
		this.modifElement = null;
		
		initComponents(controlManager);
		
		log.traceExit("DialogTimeView() ");
	}

	/**
	 * @param controlManager
	 */
	public void initComponents(ControlManager controlManager) {
		
		log.traceEntry("initComponents(ControlManager controlManager)") ;
		
		//setBounds(0, 0, 260, 380);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

			
		initComponentProjetFonction(controlManager);
	
		initComponentStart();
		initComponentsStop();
		initcomponentButton();
		log.traceExit("initComponents(ControlManager controlManager)");
	}

	/**
	 * 
	 */
	public void initcomponentButton() {
		log.traceEntry("initcomponentButton()") ;
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton btnEnreg = new JButton(TRADUCTION.getString("DialogCreatTimeView.btnEnreg.text")); //$NON-NLS-1$
			btnEnreg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionBtnEnreg();
				}
			});
			btnEnreg.setActionCommand("ENREGISTREMENT");
			buttonPane.add(btnEnreg);
			getRootPane().setDefaultButton(btnEnreg);
		}
		{
			JButton btnRetour = new JButton(TRADUCTION.getString("DialogCreatTimeView.btnRetour.text")); //$NON-NLS-1$
			btnRetour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionQuitter();
				}
			});
			btnRetour.setActionCommand("RETOUR");
			buttonPane.add(btnRetour);
		}
		log.traceExit("initcomponentButton()");
	}

	/**
	 * 
	 */
	public void initComponentsStop() {
		log.traceEntry("initComponentsStop()") ;
		JLabel lblStop = new JLabel(TRADUCTION.getString("DialogCreatTimeView.lblStop.text")); //$NON-NLS-1$
		lblStop.setBounds(25, 208, 46, 14);
		contentPanel.add(lblStop);
		
		txtDateStop = new JTextField();
		txtDateStop.setEditable(false);
		txtDateStop.setBounds(25, 233, 174, 20);
		contentPanel.add(txtDateStop);
		txtDateStop.setColumns(10);

		JButton btnCalStop = new JButton(TRADUCTION.getString("DialogCreatTimeView.btnCalStop.text")); //$NON-NLS-1$
		btnCalStop.setIcon(new ImageIcon("src/images/cal216.png"));
		btnCalStop.setBounds(202, 232, 22, 20);
		contentPanel.add(btnCalStop);

		lkDateStop = new LinkTxtBtnCalendar(this, txtDateStop, btnCalStop,Principale.formatDeLaDate);

		cmbTimeStop = new JComboBox<String>();
		cmbTimeStop.setEditable(true);
		cmbTimeStop.setBounds(25, 264, 137, 20);
		contentPanel.add(cmbTimeStop);
		for (int i=0;i<=23;i++){
			cmbTimeStop.addItem(String.format("%02d:00",i));
			cmbTimeStop.addItem(String.format("%02d:30",i));
		}
		log.traceExit("initComponentsStop()");
	}

	/**
	 * 
	 */
	public void initComponentStart() {
		
		log.traceEntry("initComponentStart()") ;
		JLabel lblStart = new JLabel(TRADUCTION.getString("DialogCreatTimeView.lblStart.text")); //$NON-NLS-1$
		lblStart.setBounds(25, 117, 199, 14);
		contentPanel.add(lblStart);
		
		txtStartDate = new JTextField();
		txtStartDate.setEditable(false);
		txtStartDate.setBounds(25, 142, 174, 20);
		contentPanel.add(txtStartDate);
		txtStartDate.setColumns(10);
		
		JButton btnCalStart = new JButton(TRADUCTION.getString("DialogCreatTimeView.btnCalStart.text")); //$NON-NLS-1$
		btnCalStart.setIcon(new ImageIcon("src/images/cal216.png"));
		btnCalStart.setBounds(202, 142, 22, 20);
		contentPanel.add(btnCalStart);
		
		lkDateStart = new LinkTxtBtnCalendar(this, txtStartDate, btnCalStart,Principale.formatDeLaDate);
		
		cmbTimeStart = new JComboBox<String>();
		cmbTimeStart.setEditable(true);
		cmbTimeStart.setBounds(25, 177, 137, 20);
		contentPanel.add(cmbTimeStart);
		
		for (int i=0;i<=23;i++){
			cmbTimeStart.addItem(String.format("%02d:00",i));
			cmbTimeStart.addItem(String.format("%02d:30",i));
		}
		log.traceExit("initComponentStart()");
	}

	/**
	 * @param controlManager
	 */
	public void initComponentProjetFonction(ControlManager controlManager) {
		log.traceEntry("initComponentProjetFonction(ControlManager controlManager)") ;
		JLabel lblProjet = new JLabel(TRADUCTION.getString("DialogCreatTimeView.lblProjet.text")); //$NON-NLS-1$
		lblProjet.setBounds(25, 11, 95, 14);
		contentPanel.add(lblProjet);
		

		cmbProjets = new JComboBox<DataInfoProjet>();
		cmbProjets.setBounds(25, 30, 199, 20);
		contentPanel.add(cmbProjets);

		
		JLabel lblFonction = new JLabel(TRADUCTION.getString("DialogCreatTimeView.lblFonction.text")); //$NON-NLS-1$
		lblFonction.setBounds(25, 61, 81, 14);
		contentPanel.add(lblFonction);
		
		cmbFonctions = new JComboBox<DataInfoFonction>();
		cmbFonctions.setBounds(25, 86, 199, 20);
		contentPanel.add(cmbFonctions);
		
		try {
			lkProjetFonction = new LinkCombo<DataInfoProjet,DataInfoFonction>(cmbProjets,cmbFonctions,controlManager);
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(CREATETIMERTVIEW,"err01.projets","Impossible de charger le projet et ses fonctions", e);
		}
		
//		controlManager.getModelManager(this.getName()).loadProjetInCombo(cmbProjets );
//		DataInfoProjet infoProjet = cmbProjets.getItemAt(0);
//		controlManager.getModelManager(this.getName()).loadFonctionInCombo(infoProjet.getId(),cmbFonctions );
		log.traceExit("initComponentProjetFonction()");
	}

	/* (non-Javadoc)
	 * @see vue.DialogBase#showDialog()
	 */
	public void showDialog(DataInfoTimer element) {
				
		try {
			lkProjetFonction.load();
		} catch (ExceptionTraitementSQL e) {
			MainView.showMsgError(CREATETIMERTVIEW,"err01.projets","Impossible de charger le projet et ses fonctions", e);
		}
		initFormulaire(element);
		super.showDialog();
	}


	


	/**
	 * @param element
	 */
	private void initFormulaire(DataInfoTimer element) {

		SimpleDateFormat date = new SimpleDateFormat("dd / MM / yyyy");
		
		txtStartDate.setText(date.format(new Date()));
		
		cmbTimeStart.setSelectedIndex(0);
		txtDateStop.setText(txtStartDate.getText());
		cmbTimeStop.setSelectedIndex(0);
		
		if (element != null)
		{
			this.modifElement = element;
			
			lkProjetFonction.initSelect(element.getIdproj(),element.getIdfonc());
			
			SimpleDateFormat heure = new SimpleDateFormat("HH:mm");
			
			
			//txtStartDate.setText(date.format(element.getStart()));
			lkDateStart.setDt(element.getStart());
			cmbTimeStart.setSelectedItem(heure.format(element.getStart()));

			//txtDateStop.setText(date.format(element.getStop()));
			lkDateStop.setDt(element.getStop());
			cmbTimeStop.setSelectedItem(heure.format(element.getStop()));
	

		} else {
			this.modifElement = null;
		}
		
	}

	/**
	 * 
	 */
	protected void actionBtnEnreg() {
		log.traceEntry("actionBtnEnreg()") ;
		
		if (validationField()){
			
			DataInfoTimer currentInfoTime;
			
			if (this.modifElement == null) // Nouvelle ligne
				currentInfoTime = new DataInfoTimer ();
			else // Modification d'un ligne
				currentInfoTime = this.modifElement;
			
			
			DataInfoFonction elementFonc = cmbFonctions.getItemAt(cmbFonctions.getSelectedIndex());
			currentInfoTime.setIdfonc(elementFonc.getId());
			currentInfoTime.setLibFonction(elementFonc.getLibelle());
			currentInfoTime.setIdproj(elementFonc.getIdProj());
			DataInfoProjet elementProjet = cmbProjets.getItemAt(cmbProjets.getSelectedIndex());
			currentInfoTime.setLibProjet(elementProjet.getLibelle());
			

			currentInfoTime.setDt(new Date());
			int indexSelected ;
			String heureDebut;
			String heureFin;
			String txt;
			
			
			
			// récuperer le temps saisie ou choisie start
			
			txt = (String) cmbTimeStart.getSelectedItem();
			if (txt!="") // Il y a une saisie du temps 
			{
				heureDebut = txt;
				
			}else { // Il y a un choix par combobox
			
				indexSelected = cmbTimeStart.getSelectedIndex();
				
				log.trace("actionBtnEnreg timeStart : index ="+indexSelected);
				heureDebut=cmbTimeStart.getItemAt(indexSelected);
				
				log.trace("actionBtnEnreg timeStart : heureDebut ="+heureDebut);
			}
			Date start = formatDate(
					lkDateStart.getDt(),
					heureDebut
					);	
			currentInfoTime.setStart(start);

			// récuperer le temps saisie ou choisie stop
			
			txt = (String) cmbTimeStop.getSelectedItem();
			if (txt!="") // Il y a une saisie du temps 
			{
				heureFin = txt;
				
			}else { // Il y a un choix
			
				indexSelected = cmbTimeStop.getSelectedIndex();
				log.trace("actionBtnEnreg timeStop : index ="+indexSelected);
				
				heureFin=cmbTimeStop.getItemAt(indexSelected);
				log.trace("actionBtnEnreg timeStop : heureFin ="+heureFin);
			}
			Date stop = formatDate(
					lkDateStop.getDt(),
					heureFin
					);
			currentInfoTime.setStop(stop);


			controlManager.calculEcart(currentInfoTime);

			if (this.modifElement == null)
				// Creation
				try {
					modelTable.addRow(currentInfoTime);
				} catch (Exception e) {
					MainView.showMsgError(CREATETIMERTVIEW,"err02.projets","Impossible d'ajouter un timer", e);
				}
			else {
				// Modification
				try {
					modelTable.updateRow(currentInfoTime);
				} catch (Exception e) {
					MainView.showMsgError(CREATETIMERTVIEW,"err03.projets","Impossible de modifier un timer", e);
				}
				actionQuitter();
			}
		}
		log.traceExit("actionBtnEnreg()");
	
	}

	/**
	 * @return
	 */
	public Date formatDate(Date dt,String strHeure) {
		log.traceEntry("formatDate(Date dt="+dt.toString()+",String strHeure="+strHeure+")") ;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateStr=sdf.format(dt) +" "+ strHeure + ":00";
		log.trace("formatDate : dateStr="+dateStr);

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d;
		try {
			d = sdf2.parse(dateStr);
		} catch (ParseException e) {
			// TODO trace correctement 
			e.printStackTrace();
			return null;
		}
		
		
		log.traceExit("formatDate() ret="+d.toString()+"  "+d.getTime());
		return d;
	}

	/**
	 * @return
	 */
	private boolean validationField() {
		
		log.traceEntry(" validationField()") ;
		
		// Une heure de début doit être sélectionnée ou saisie
		
		int indexSelected = cmbTimeStart.getSelectedIndex();
		log.trace("validationField timeStart : index ="+indexSelected);
		String txt = (String) cmbTimeStart.getSelectedItem();
		log.trace("validationField saisie start : txt ="+txt);

		if (indexSelected == -1 && txt=="") return InvalideField(cmbTimeStart);
		if (txt!="" && !validationTimeSaisie(txt)) return InvalideField(cmbTimeStart);
		
		// Une heure de fin doit être sélectoinnée
		indexSelected = cmbTimeStop.getSelectedIndex();
		log.trace("validationField timeStop : index ="+indexSelected);
		txt = (String) cmbTimeStop.getSelectedItem();
		log.trace("validationField saisie stop : txt ="+txt);

		if (indexSelected == -1 && txt=="") return InvalideField(cmbTimeStop);
		if (txt !="" && !validationTimeSaisie(txt)) return InvalideField(cmbTimeStop);
		
		log.traceExit(" validationField() OK");
		return true;
	}

	/**
	 * @param cmbTimeStart2
	 * @return
	 */
	private boolean InvalideField(JComboBox<String> cmbTime) {
		// TODO Mette un cadre rouge sur la combo et un message d'erreur 
		return false;
	}

	/**
	 * @param txt
	 * @return
	 */
	private boolean validationTimeSaisie(String txt) {

		log.traceEntry("validationTimeSaisie(String txt="+txt+")");
		// Validation d'une saisie d'heure
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		try {
			Date n=sdf.parse(txt);
			log.trace("validationTimeSaisie: txt="+txt+ "OK "+n);
		} catch (ParseException e) {
			
			log.traceExit("validationTimeSaisie(String txt="+txt+") KO");
			return false;
			// TODO revoir les traces
			//e.printStackTrace();
		}
		
		log.traceExit("validationTimeSaisie(String txt="+txt+") OK");
		
		return true;
	}

	/**
	 * @return the lkDateTimer
	 */
	public LinkTxtBtnCalendar getLkDateTimer() {
		return lkDateTimer;
	}

	/**
	 * @param lkDateTimer the lkDateTimer to set
	 */
	public void setLkDateTimer(LinkTxtBtnCalendar lkDateTimer) {
		this.lkDateTimer = lkDateTimer;
	}

	/**
	 * @return the idProjet
	 */
	public int getIdProjet() {
		return idProjet;
	}

	/**
	 * @param idProjet the idProjet to set
	 */
	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	
}
