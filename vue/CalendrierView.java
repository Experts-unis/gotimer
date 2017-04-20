/**
 * 
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.awt.FlowLayout;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Principale;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Dimension;

/**
 * @author test
 *
 */
public class CalendrierView extends JDialog {

	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static final ResourceBundle TRADUCTION = Principale.config.getTraduction("calendrierview");
	private final JPanel contentPanel ;
	private Calendar calendar;
	private int anneeCourante;
	private int moisCourant;
	private int jourCourant;
	private JComboBox<String> choixDuMois;
	String[] lesMois;
	private JComboBox<Integer> choixDeAnnee;
	private JButton[][] labs;
	private JButton b0;
	
	/** The number of day squares to leave blank at the start of this month */
	protected int leadGap;

	  /** Today's year */
	protected  final int thisYear;
	protected final int thisMonth ;
	private final int toDay;
	private boolean sendData;
	private JLabel lbtMois;
	private JLabel lbtAnnee;
	  
	public final static int dom[] = { 31, 28, 31, 30, /* jan feb mar apr */
			  31, 30, 31, 31, /* may jun jul aug */
			  30, 31, 30, 31 /* sep oct nov dec */
			  };
	private JButton btnMoisPrec;
	private JButton btnAnnePrec;
	private JButton bntMoisSuiv;
	private JButton btnAnneeSuiv;
	private JSeparator sep1;
	private JSeparator sep2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CalendrierView dialog = new CalendrierView(null,100,100);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CalendrierView(JFrame parent,int x,int y) {
		super(parent,"",true);
		
		setUndecorated(true);
		setResizable(false);
		setBounds(x, y, 370, 250);
		
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
						
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(7, 7));
		
		calendar = new GregorianCalendar();
		thisYear = calendar.get(Calendar.YEAR);
		thisMonth = calendar.get(Calendar.MONTH);
		toDay = calendar.get(Calendar.DAY_OF_MONTH);
		leadGap =-1;
		
	    setDateCourante(toDay,thisMonth ,thisYear);
	
//	    lesMois = new String[] { "January", "February", "March", "April", "May", "June",
//			      "July", "August", "September", "October", "November", "December" };
		
	    lesMois = new String[] { 
	    		
	    		TRADUCTION.getString("ArchiveView.mois01.text"),
	    		TRADUCTION.getString("ArchiveView.mois02.text"),
	    		TRADUCTION.getString("ArchiveView.mois03.text"),
	    		TRADUCTION.getString("ArchiveView.mois04.text"),
	    		TRADUCTION.getString("ArchiveView.mois05.text"),
	    		TRADUCTION.getString("ArchiveView.mois06.text"),
	    		TRADUCTION.getString("ArchiveView.mois07.text"),
	    		TRADUCTION.getString("ArchiveView.mois08.text"),
	    		TRADUCTION.getString("ArchiveView.mois09.text"),
	    		TRADUCTION.getString("ArchiveView.mois10.text"),
	    		TRADUCTION.getString("ArchiveView.mois11.text"),
	    		TRADUCTION.getString("ArchiveView.mois12.text")
		};

		
	    initCompoment(contentPanel);
		recompute();
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton(TRADUCTION.getString("ArchiveView.btncancel.text"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						actionQuitter();
					}
				});
				cancelButton.setActionCommand("CANCEL");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void initCompoment(JPanel contentPanel) {
		


		/*
		 * Utiliser
		choixDuMois = new JComboBox<String>();

		for (int i = 0; i < lesMois.length; i++)
			choixDuMois.addItem(lesMois[i]);

		choixDuMois.setSelectedItem(lesMois[moisCourant]);
		choixDuMois.getAccessibleContext().setAccessibleName("Les mois");
		choixDuMois.getAccessibleContext().setAccessibleDescription(
				"Choisir le mois de l'année");
		choixDuMois.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actionChoixMois();

			}
		});
*/

	/*	choixDeAnnee = new JComboBox<Integer>();
		for (int i = anneeCourante - 5; i < anneeCourante + 5; i++)
			choixDeAnnee.addItem(i);

		choixDeAnnee.setEditable(true);
		choixDeAnnee.setSelectedItem(Integer.toString(anneeCourante));

		choixDeAnnee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actionChoixAnnee();

			}
		});

		JPanel tp = new JPanel();
		tp.add(choixDuMois);
		tp.add(choixDeAnnee);
		getContentPane().add(BorderLayout.NORTH, tp);
*/
		JPanel tp = new JPanel();
		{
			btnAnnePrec = new JButton("<<");
			btnAnnePrec.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionAnneeMoins();
				}
			});
			tp.add(btnAnnePrec);
		}
		{
			btnMoisPrec = new JButton("<");
			btnMoisPrec.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionMoisMoins();
				}
			});
			tp.add(btnMoisPrec);
		}
		{
			sep1 = new JSeparator();
			sep1.setOrientation(SwingConstants.VERTICAL);
			sep1.setPreferredSize(new Dimension(25, 2));
			tp.add(sep1);
		}
		lbtMois = new JLabel(lesMois[moisCourant]);
		tp.add(lbtMois);
		lbtAnnee= new JLabel(Integer.toString(anneeCourante));
		tp.add(lbtAnnee);
		
		getContentPane().add(BorderLayout.NORTH, tp);
		{
			sep2 = new JSeparator();
			sep2.setPreferredSize(new Dimension(25, 2));
			sep2.setOrientation(SwingConstants.VERTICAL);
			tp.add(sep2);
		}
		{
			bntMoisSuiv = new JButton(">");
			bntMoisSuiv.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionMoisPlus();
				}
			});
			tp.add(bntMoisSuiv);
		}
		{
			btnAnneeSuiv = new JButton(">>");
			btnAnneeSuiv.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionAnneePlus();
				}
			});
			tp.add(btnAnneeSuiv);
		}

		
		//JPanel bp = new JPanel();
	//	bp.setLayout(new GridLayout(7, 7));
		labs = new JButton[6][7]; // first row is days

		contentPanel.add(b0 = new JButton(TRADUCTION.getString("ArchiveView.sem01.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);
		contentPanel.add(b0=new JButton(TRADUCTION.getString("ArchiveView.sem02.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);
		contentPanel.add(b0=new JButton(TRADUCTION.getString("ArchiveView.sem03.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);
		contentPanel.add(b0=new JButton(TRADUCTION.getString("ArchiveView.sem04.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);
		contentPanel.add(b0=new JButton(TRADUCTION.getString("ArchiveView.sem05.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);
		contentPanel.add(b0=new JButton(TRADUCTION.getString("ArchiveView.sem06.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);
		contentPanel.add(b0 = new JButton(TRADUCTION.getString("ArchiveView.sem07.text")));
		b0.setBackground(Color.GRAY);
		b0.setForeground(Color.BLACK);


		ActionListener dateSetter = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String num = e.getActionCommand();
				if (!num.equals("")) {
					// set the current day highlighted
					setDayActive(Integer.parseInt(num));
					// When this becomes a Bean, you can
					// fire some kind of DateChanged event here.
					// Also, build a similar daySetter for day-of-week btns.
				}
			}
		};

		// Construct all the buttons, and add them.
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++) {
				contentPanel.add(labs[i][j] = new JButton(""));
				labs[i][j].addActionListener(dateSetter);
				labs[i][j].setBackground(Color.white);
				labs[i][j].setForeground(Color.black);
			}

		//add(BorderLayout.SOUTH, contentPanel);


	    
	}
	/**
	 * 
	 */
	protected void actionAnneePlus() {
		++anneeCourante;
		lbtAnnee.setText(Integer.toString(anneeCourante));
		recompute();
	}

	/**
	 * 
	 */
	protected void actionAnneeMoins() {
		--anneeCourante;
		lbtAnnee.setText(Integer.toString(anneeCourante));
		recompute();
	}

	/**
	 * 
	 */
	protected void actionMoisMoins() {
		--moisCourant;
		if (moisCourant<0) {
			moisCourant=11;
			actionAnneeMoins();
		}else{
			recompute();
		}
		lbtMois.setText(lesMois[moisCourant]);
		
		
	}
	/**
	 * 
	 */
	protected void actionMoisPlus() {
		++moisCourant;
		if (moisCourant>11) {
			moisCourant=0;
			actionAnneePlus();
		}else{
			recompute();
		}
		lbtMois.setText(lesMois[moisCourant]);
		
	}
	protected void actionChoixMois() {

		int i = choixDuMois.getSelectedIndex();
		if (i >= 0) {
			moisCourant = i;
			// // System.out.println("Month=" + mm);
			recompute();
		}

	}

	protected void actionChoixAnnee() {

		int i = choixDeAnnee.getSelectedIndex();
		if (i >= 0) {
			anneeCourante = Integer.parseInt(choixDeAnnee.getSelectedItem().toString());
			// // System.out.println("Year=" + yy);
			recompute();
		}

	}
	

	 
	  
	
	/** Set just the day, on the current month */
	public void setDayActive(int newDay) {

		//TODO Stop le Dialog et retourne la date choisie
		jourCourant = newDay;
		this.sendData = true;
		this.setVisible(false);

	}

	  
	private void setDateCourante(int pJour, int pMois, int pAnnee) {
		// Initialise la date

		this.anneeCourante = pAnnee;
		this.moisCourant = pMois;
		this.jourCourant = pJour;

	}
	
	/** Compute which days to put where, in the Cal panel */
	protected void recompute() {
		
		// // System.out.println("Cal::recompute: " + yy + ":" + mm + ":" + dd);
		// Cas impossible grace aux combobox Sauf si la class est créée avec de mauvais paramètre
		if (moisCourant < 0 || moisCourant > 11)
			throw new IllegalArgumentException("Month " + moisCourant
					+ " bad, must be 0-11");
		
		if (leadGap != -1){
			// Efface la case rose de "aujourd'hui"  
			int pos = (leadGap + toDay - 1);
			Component square = labs[pos / 7][pos % 7];
			square.setBackground(Color.white);
		}
		
		// Création du calendrier de la date courante choisie
		calendar = new GregorianCalendar(anneeCourante, moisCourant, jourCourant);

		// Calcul combien il y a de case à remettre à blanc avant le premier jour du mois 
		leadGap = GetLeadGap();
		// Blank out the labels before 1st day of month
		for (int i = 0; i < leadGap; i++) {
			labs[0][i].setText("");
		}

		// Combien de jour y a t il dans le mois
		// Un jour de plus le mois de février pour les années bissextile
		int daysInMonth = dom[moisCourant];
		if (isLeap(calendar.get(Calendar.YEAR)) &&  moisCourant== 2) 
			++daysInMonth;
		
		// Met à jour chaque case du calendrier
		int pos;
		for (int i = 1; i <= daysInMonth; i++) {
			pos = (leadGap + i -1);
			JButton b = labs[pos / 7][pos % 7];
			b.setText(Integer.toString(i));
		}

		// Efface le reste du calendrier
		for (int i = leadGap  + daysInMonth; i < 6 * 7; i++) {
			labs[(i) / 7][(i) % 7].setText("");
		}

		// Color en rose le jour "d'aujourd'hui" si le calendrier est celui de cette année et de ce mois
		if (thisYear == anneeCourante && thisMonth == moisCourant ){
			pos = (leadGap + toDay - 1);
			Component square = labs[pos / 7][pos % 7];
			square.setBackground(Color.pink);
			//square.repaint();
		}
			

		// Say we need to be drawn on the screen
		repaint();
	}

	  /**
	 * @return
	 */
	private int GetLeadGap() {
		
		GregorianCalendar g=new GregorianCalendar(anneeCourante, moisCourant, 1);
		
		int numFirstDayOfMonth = g.get(Calendar.DAY_OF_WEEK) -1;
		
		// Dimanche = 1
		
		int [] transition = {6,0,1,2,3,4,5};
		
		
		
		return transition[numFirstDayOfMonth];
	}

	/**
	   * isLeap() returns true if the given year is a Leap Year.
	   *
	   * "a year is a leap year if it is divisible by 4 but not by 100, except
	   * that years divisible by 400 *are* leap years." -- Kernighan &#038; Ritchie,
	   * _The C Programming Language_, p 37.
	   */
	  public boolean isLeap(int year) {
	    if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
	      return true;
	    return false;
	  }
	  
		/**
		 * 
		 */
		public Date showDialog() {

			this.sendData = false;
			
			//Début du dialogue
			this.setVisible(true);
			
			GregorianCalendar g=new GregorianCalendar(anneeCourante, moisCourant, jourCourant);
			Date theDate = g.getTime();
			
			//Le dialogue prend fin
			//Si on a cliqué sur OK, on envoie, sinon on envoie une chaîne vide !
			return (this.sendData)? theDate : null;
			
			
		}
		private void actionQuitter() {
			
			setVisible(false);
			
		}
}
