/**
 * 
 */
package vue;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author test
 *
 */
public class StatistiqueView extends DialogBase {
	private JTable table;
	
	public StatistiqueView() {
		super(null,"Statistique",true,780,500);
		
		
		
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 26, 746, 383);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Typologie", "Estimation", "R\u00E9el", "Coeff"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Double.class, Double.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setResizable(false);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionQuitter();
			}
		});
		btnRetour.setBounds(667, 420, 89, 23);
		getContentPane().add(btnRetour);
	}
}
