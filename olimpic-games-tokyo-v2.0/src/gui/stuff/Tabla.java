package gui.stuff;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Tabla extends DefaultTableCellRenderer{
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(value instanceof JButton) {
			JButton boton1 = (JButton) value;
			return boton1;			
		}
		
		
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	
	
}
