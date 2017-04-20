/**
 * 
 */
package vue;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;


import org.apache.logging.log4j.LogManager;

/**
 * @author test
 *
 */
public class DialogBase extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DialogBase.class );  


	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public DialogBase(JDialog owner, String title, boolean modal,int w,int h) {
		super(owner, title, modal);

		log.traceEntry("DialogBase(JDialog owner, String title="+title+", boolean modal="+modal+",int w,int h)") ;

		// Centre la Dialogue
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, w, h);
		int x = (dimScreen.width / 2) - w/2 ;
		int y = (dimScreen.height / 2) - h/2 -100; 
		Point centreEcran = new Point(x,y);
		this.setLocation(centreEcran);

		//
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				log.trace("windowClosed ",e);
				actionQuitter();
			}
		});
		
		log.traceExit("DialogBase()") ;
		
	}

	/**
	 * 
	 */
	protected void actionQuitter() {
	
		log.traceEntry("actionQuitter()");
		setVisible(false);
		
	}
	
	public void showDialog(){

		log.traceEntry("showDialog()");
		//Début du dialogue
		this.setVisible(true);
		
		log.traceExit("showDialog()") ;
		

	}
	

}
