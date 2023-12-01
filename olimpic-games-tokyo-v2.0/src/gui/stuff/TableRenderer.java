package gui.stuff;

import java.awt.Color;


import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableRenderer extends JPanel implements TableCellRenderer{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(column < 3) {
		setBackground(Color.WHITE);
		JLabel label = new JLabel(value.toString());
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		panel.setBackground(Color.WHITE);
		panel.add(label);
		this.add(panel);
		}else {
			if(column < 4) {
				JButton botonEditar = new JButton("Editar");
				botonEditar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("EDITAR");
					}
				});
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
			panel.setBackground(Color.WHITE);
			panel.add(botonEditar);
			this.add(panel);
			}else {
				if(column < 5) {
					JButton botonEliminar = new JButton("Eliminar");
					botonEliminar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("ELIMINAR");
						}	
					});
					JPanel panelElim = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
					panelElim.add(botonEliminar);
					this.add(panelElim);
				}
			}
		}
		return this;
	}
}



