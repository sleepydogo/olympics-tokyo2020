package gui.stuff;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Render extends DefaultTableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) {
	
		if (value instanceof JButton) {
			JButton btn = (JButton) value;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("ELIMINAR");
					
				}
			});
			return btn;
		}
		
		return super.getTableCellRendererComponent(table, value, isSelected, 
				hasFocus, row, column);
	
	}

}
