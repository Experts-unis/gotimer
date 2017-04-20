/**
 * 
 */
package vue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * @author test
 *
 */
public class TextFieldDate extends JTextField {

	String ctrl[]={"0","1","2","3","4","5","6","7","8","9","/"};
	/**
	 * 
	 */
	public TextFieldDate() {


//		addKeyListener(new KeyAdapter() {
//			public void keyReleased(KeyEvent e) {							
//				DefaultListModel model = new DefaultListModel();//creation dun nouveau model pour une JList
//				String enteredText = getText().toUpperCase();	//On recupère le texte entree dans le JtextField
//				e.getKeyChar();
//				for (int i = 0; i< data.size();i++) {
//					//Comparaison des elements contenu dans l ArrayList et du texte entree 
//					if (data.get(i).toString().indexOf(enteredText) != -1) {
//						model.addElement(data.get(i).toString());//ajout de lelement dans le nouveau model
//					}
//				}					
//				liste.setModel(model);//On definit ce nouveau model pour la JList
//			}
//		});
	}

	/**
	 * @param text
	 */
	public TextFieldDate(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param columns
	 */
	public TextFieldDate(int columns) {
		super(columns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param columns
	 */
	public TextFieldDate(String text, int columns) {
		super(text, columns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param doc
	 * @param text
	 * @param columns
	 */
	public TextFieldDate(Document doc, String text, int columns) {
		super(doc, text, columns);
		// TODO Auto-generated constructor stub
	}

}
