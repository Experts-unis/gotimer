/**
 * 
 */
package vue;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;



/**
 * @author test
 *
 */
public class LinkTxtBtnCalendar {
	
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( LinkTxtBtnCalendar.class );  
	private JTextField txtDate;
	private JButton btnCalDate;
	private JDialog dial;
	private Date dt;
	private String formatDelaDate;
	/**
	 * @param txtDate
	 * @param btnCalDate
	 */
	public LinkTxtBtnCalendar(JDialog dial, JTextField txtDate, JButton btnCalDate,String formatDelaDate) {
		super();
		log.traceEntry("LinkTxtBtnCalendar(JDialog dial, JTextField txtDate, JButton btnCalDate)") ;
		
		this.txtDate = txtDate;
		this.btnCalDate = btnCalDate;
		this.dial = dial;
		this.formatDelaDate=formatDelaDate;
		
		setDt(new Date());
		
		
		this.btnCalDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 showCalendrier();
			}
		});
		log.traceExit("LinkTxtBtnCalendar() dt="+getDt().toString());
	}
	
	/**
	 * @return the dt
	 */
	public Date getDt() {
		log.traceEntry("getDt() dt="+dt.toString()) ;
		return dt;
	}

	/**
	 * @param dt the dt to set
	 */
	public void setDt(Date dt) {
		log.traceEntry("setDt(Date dt="+dt.toString()+")") ;
		this.dt = dt;
		SimpleDateFormat sdf = new SimpleDateFormat(formatDelaDate);
		txtDate.setText(sdf.format(dt));
	}

	/**
	 * 
	 */
	protected void showCalendrier() {
		log.traceEntry("showCalendrier()") ;
		//txtDate.setBounds(25, 36, 174, 20);
		try {

			Point ptxt = txtDate.getLocation();
			Point p=dial.getLocation();
			
			CalendrierView dialog = new CalendrierView(null,p.x+ptxt.x,p.y+30+ptxt.y+20);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			Date theDate = dialog.showDialog();
			
			if (theDate !=null){
				SimpleDateFormat sdf = new SimpleDateFormat(formatDelaDate);
				txtDate.setText(sdf.format(theDate));
				dt = theDate;
			}
			log.trace("showCalendrier : theDate="+theDate.toString());
			
		} catch (Exception e) {
			
			log.fatal("printStackTrace " , e);
			
		}
		log.traceExit("showCalendrier()");
	}

}
