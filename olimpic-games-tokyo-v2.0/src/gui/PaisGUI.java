package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import gui.stuff.ModeloTablaPais;
import gui.stuff.Render;
import utiles.Utiles;
import DAO.AtletaEnDisciplinaDAOImp;
import DAO.DeportistaDAOImp;
import DAO.PaisDAO;
import DAO.PaisDAOImp;
import data.AtletaEnDisciplina;
import data.Pais;

/*
 * Clase que se encarga de la interfaz grafica que contempla la gestion de los paises 
 * cargados en la base de datos.
 * 
 * @author Tomas E. Schattmann
 */

public class PaisGUI extends JFrame implements ActionListener, MouseListener {

	private int FRAME_HEIGHT = 400;
	private int FRAME_WIDTH = 700;

	private JPanel background = new JPanel();
	private JPanel topSection = new JPanel();
	private JPanel middleSection = new JPanel();
	private JPanel bottomSection = new JPanel();

	private JLabel buscarLabel = new JLabel("Ingrese pais a buscar");
	private JButton buscarButton = new JButton("Buscar");
	private JTextField buscarTextField = new JTextField();

	private JButton nuevoButton = new JButton("+ Nuevo");
	private JButton volverButton = new JButton("Volver");
	private JButton editarButton = new JButton("Editar");
	private JButton eliminarButton = new JButton("Eliminar");

	private JScrollPane sp = new JScrollPane();
	private String column[] = { "Numero", "Nombre", "Editar", "Eliminar" };
	private JTable tablaPaises = new JTable();
	DefaultTableModel modeloTabla=(new DefaultTableModel(){@Override public boolean isCellEditable(int datos,int columnas){return false;}});

	public PaisGUI() {
		setTitle("GestorJuegosOlimpicos - Pais");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocation(Utiles.setCenterLocationFrame(this.getSize()));

		Image icono = new ImageIcon(getClass().getResource("/resources/gral/iconoApp.png")).getImage();
		ImageIcon icono2 = new ImageIcon(icono.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING));

		setIconImage(icono2.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setLayoutManager();
		configComponents();
		addComponents();

		setVisible(true);
		this.repaint();
	}

	private void setLayoutManager() {
		setLayout(null);
		background.setLayout(null);
		topSection.setLayout(null);
		middleSection.setLayout(null);
		bottomSection.setLayout(null);
	}

	private void configComponents() {
		// Background
		background.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

		// Section configuration:
		int TOP_SECTION_HEIGHT = 90;
		int MIDDLE_SECTION_HEIGHT = 260;
		int BOTTOM_SECTION_HEIGHT = 50;
		// --> Top Section
		topSection.setSize(FRAME_WIDTH, TOP_SECTION_HEIGHT);
		topSection.setLocation(0, 0);
		topSection.setBackground(new Color(185, 22, 70));
		// --> Middle Section
		middleSection.setSize(FRAME_WIDTH, MIDDLE_SECTION_HEIGHT);
		middleSection.setLocation(0, 90);
		middleSection.setBackground(new Color(251, 243, 228));
		// --> Bottom Section
		bottomSection.setSize(FRAME_WIDTH, BOTTOM_SECTION_HEIGHT);
		bottomSection.setLocation(0, 350);
		bottomSection.setBackground(new Color(16, 86, 82));

		// Top-Bar configuration
		int TOP_BAR_HEIGHT = 35;

		// --> Buscar text Label
		buscarLabel.setSize(130, 30);
		buscarLabel.setLocation(50, TOP_BAR_HEIGHT);
		buscarLabel.setAlignmentX(SwingConstants.LEFT);
		// --> Buscar text Input Field
		buscarTextField.setSize(150, 30);
		buscarTextField.setLocation(180, TOP_BAR_HEIGHT);
		buscarTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char ch = e.getKeyChar();
				if (Character.isLetter(ch)) {
					filtro(buscarTextField.getText(), tablaPaises);
					repaint();
				}
				if (buscarTextField.getText() == null) {
					filtro(null, tablaPaises);
					repaint();
				}
			}
		});
		// --> Buscar button
		buscarButton.setSize(100, 30);
		buscarButton.setLocation(335, TOP_BAR_HEIGHT);
		buscarButton.addActionListener(this);
		// --> Nuevo button
		nuevoButton.setSize(100, 30);
		nuevoButton.setLocation(440, TOP_BAR_HEIGHT);
		nuevoButton.addActionListener(this);
		// --> Volver button
		volverButton.setSize(100, 30);
		volverButton.setLocation(545, TOP_BAR_HEIGHT);
		volverButton.addActionListener(this);

		// Middle section
		// --> Tabla
		modeloTabla.setColumnIdentifiers(column);
		tablaPaises.setDefaultRenderer(Object.class, new Render());
		tablaPaises.setModel(modeloTabla);
		tablaPaises.setFillsViewportHeight(true);
		tablaPaises.setLocation(0, 0);
		tablaPaises.setColumnSelectionAllowed(false);
		tablaPaises.getTableHeader().setReorderingAllowed(false);
		tablaPaises.setRowHeight(30);
		tablaPaises.setRowSorter(null);
		tablaPaises.addMouseListener(this);
		crearTabla();
		// --> Scroll Panel
		sp.setViewportView(tablaPaises);
		sp.setSize(500, 240);
		sp.setLocation(100, 10);
		// --> Botones de la tabla
		editarButton.addActionListener(this);
		eliminarButton.addActionListener(this);

	}

	private void addComponents() {
		// Top Section
		topSection.add(buscarLabel);
		topSection.add(buscarButton);
		topSection.add(buscarTextField);
		topSection.add(volverButton);
		topSection.add(nuevoButton);

		// Middle section
		middleSection.add(sp);

		// Background
		background.add(topSection);
		background.add(middleSection);
		background.add(bottomSection);

		add(background);

	}

	private void crearTabla() {
		PaisDAOImp paisDao = new PaisDAOImp();
		LinkedList<Pais> tmp = paisDao.getAllPais();
		Object[] fila = new Object[4];
		for (int i = 0; i < tmp.size(); i++) {
			Pais pais = tmp.get(i);
			fila[0] = pais.getID();
			fila[1] = pais.getNombre();
			fila[2] = editarButton;
			fila[3] = eliminarButton;
			modeloTabla.addRow(fila);
		}
	}

	private void filtro(String consulta, JTable tabla) {
		DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
		tabla.setRowSorter(tr);
		tr.setRowFilter(RowFilter.regexFilter(consulta));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == volverButton) {
			setVisible(false);
			MainGUI interfaz = new MainGUI();
		}
		if (e.getSource() == nuevoButton) {
			setVisible(false);
			IngresoPaisGUI interfaz = new IngresoPaisGUI(false);
		}
		if (e.getSource() == buscarButton) {
			filtro(buscarTextField.getText(), tablaPaises);
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int columnas = tablaPaises.getColumnModel().getColumnIndexAtX(e.getX());
		int filas = e.getY() / tablaPaises.getRowHeight();
		if (filas < tablaPaises.getRowCount() && filas >= 0 && columnas < tablaPaises.getColumnCount()
				&& columnas >= 0) {
			Object value = tablaPaises.getValueAt(filas, columnas);
			int paisSeleccionado = ((int) tablaPaises.getValueAt(filas, 0));
			if (value instanceof JButton) {
				JButton botonClick = (JButton) value;				
				if (botonClick.equals(editarButton)) {
					this.setVisible(false);
					IngresoPaisGUI tmp = new IngresoPaisGUI(true, paisSeleccionado);
				} else if (botonClick.equals(eliminarButton)) {
					int respuesta = JOptionPane.showConfirmDialog(botonClick, "Esta seguro que desea eliminar el pais?",
							getTitle(), JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						PaisDAOImp tmp = new PaisDAOImp();
						if (tmp.eliminarPais(paisSeleccionado)) {
							JOptionPane.showMessageDialog(this, "El pais se ha eliminado satisfactoriamente.");
							this.setVisible(false);
							new PaisGUI();
						}
						else {
							JOptionPane.showMessageDialog(this, "Ha habido un error al eliminar el pais.");
						}
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}