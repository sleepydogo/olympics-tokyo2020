package gui.stuff;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaPais extends DefaultTableModel {

	public ModeloTablaPais(final Object[][] datos, final String[] titulos) {
		super(datos, titulos);
	}

	public Class getColumnClass(final int column) {
		return this.getValueAt(0, column).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
