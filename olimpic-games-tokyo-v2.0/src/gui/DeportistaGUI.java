package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import DAO.DeportistaDAOImp;
import DAO.DisciplinaDAOImp;
import DAO.PaisDAOImp;
import DAO.AtletaEnDisciplinaDAOImp;

import data.Disciplina;
import data.AtletaEnDisciplina;
import data.Deportista;
import gui.stuff.Render;
import gui.stuff.TableRenderer;
import utiles.Utiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class DeportistaGUI extends JFrame implements ActionListener, MouseListener {
	private static int contador = 0;
	String[] columnas = new String[] { "Deportista", "Pais", "Disciplina", "Editar atleta", "Eliminar atleta" };
	String[][] datos = new String[][] {};
	String directorioActual = System.getProperty("user.dir");
	JTable tabla = new JTable(datos, columnas) {
		public TableCellRenderer getCellRenderer(int fila, int columna) {
			return new TableRenderer();
		}
	};
	DefaultTableModel modeloTabla = (new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int datos, int columnas) {
			return false;
		}
	});

	PaisDAOImp dao = new PaisDAOImp();

	// Creo todos los paneles (master es el que incluye a todos)
	private JPanel panelBotonesSuperior = new JPanel();
	private JPanel panelSuperior = new JPanel();

	private JButton botonEditar = new JButton("Editar");
	private JButton botonEliminar = new JButton("Eliminar");
	private JButton agregar = new JButton("+ Nuevo");
	private JButton exportar = new JButton("Exportar CSV");
	private JButton volver = new JButton("Volver");

	public DeportistaGUI() {
		contador++;
		setTitle("Gestor de Juegos Olimpicos - Deportistas");
		setSize(700, 400);
		setLocation(Utiles.setCenterLocationFrame(getSize()));
		setBackground(new Color(251, 243, 228));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image icono = new ImageIcon(getClass().getResource("/resources/gral/iconoApp.png")).getImage();
		ImageIcon icono2 = new ImageIcon(icono.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING));
		setIconImage(icono2.getImage());

		modeloTabla.setColumnIdentifiers(columnas);
		tabla.setDefaultRenderer(Object.class, new Render());
		tabla.setModel(modeloTabla);
		tabla.setFillsViewportHeight(true);
		tabla.setLocation(0, 0);
		tabla.setColumnSelectionAllowed(false);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setRowHeight(30);
		tabla.setRowSorter(null);
		tabla.addMouseListener(this);

		// Se agregan los botones superiores al panel
		agregar.addActionListener(this);
		exportar.addActionListener(this);
		volver.addActionListener(this);

		panelBotonesSuperior.setBackground(new Color(185, 22, 70));
		panelBotonesSuperior.add(agregar);
		panelBotonesSuperior.add(exportar);
		panelBotonesSuperior.add(volver);

		botonEditar.addActionListener(this);
		botonEliminar.addActionListener(this);

		// Sele agrega al panel superior el panel con los botones superiores
		panelSuperior.setLayout(new BorderLayout());
		panelSuperior.add(panelBotonesSuperior, BorderLayout.NORTH);

		cargarTabla();

		// Pongo a la tabla en un panel con scroll
		JScrollPane panelTabla = new JScrollPane(tabla);

		// Agrego la tabla y los botones al frame
		getContentPane().add(panelTabla, BorderLayout.CENTER);
		getContentPane().add(panelBotonesSuperior, BorderLayout.NORTH);

		setVisible(true);
	}

	private void cargarTabla() {
		DeportistaDAOImp depor = new DeportistaDAOImp();
		AtletaEnDisciplinaDAOImp atd = new AtletaEnDisciplinaDAOImp();
		LinkedList<Deportista> ingresados = depor.getAllDeportistas();
		LinkedList<AtletaEnDisciplina> asociacion = atd.getAllAtletasyDisciplinas();
		Object[] fila = new Object[5];
		for (int i = 0; i < asociacion.size(); i++) {
			fila[0] = this.buscarNombreDepor(ingresados, asociacion.get(i).getidDepo()).getNombre() + " "
					+ this.buscarNombreDepor(ingresados, asociacion.get(i).getidDepo()).getApellido();
			fila[1] = this.buscarPaisDepor(ingresados, asociacion.get(i).getidDepo());
			fila[2] = this.buscarNombre(asociacion.get(i).getidDisc());
			fila[3] = botonEditar;
			fila[4] = botonEliminar;
			modeloTabla.addRow(fila);

		}

		if (contador == 1) {
			JOptionPane.showMessageDialog(this,
					"Carga de Deportistas: Cada deportista cuenta con un e-mail unico. En el caso que un deportista participe en varias disciplinas, se debera ingresar el email correspondiente en cada ingreso");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(volver)) {
			setVisible(false);
			MainGUI interfaz = new MainGUI();
		} else if (e.getSource().equals(agregar)) {
			setVisible(false);
			IngresoDeportistaGUI ingreso = new IngresoDeportistaGUI();
		} else if (e.getSource().equals(exportar)) {
			JFileChooser guardar = new JFileChooser();
			guardar.showSaveDialog(null);
			guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			File archivo = guardar.getSelectedFile();
			
			String directorioActual = archivo.getPath();
			
			if (!directorioActual.contains(".csv")) {
				directorioActual = directorioActual + ".csv";
			}
			
			if (exportToCSV(tabla, directorioActual))
				JOptionPane.showMessageDialog(agregar,
						"Archivo exportado correctamente como .CSV a " + directorioActual);
		}

	}

	public static boolean exportToCSV(JTable tabla, String pathToExportTo) {
		try {
			TableModel modelo = tabla.getModel();
			int columnasExportables = (((modelo.getColumnCount()) - 2));
			int filasExportables = modelo.getRowCount();
			FileWriter csv = new FileWriter(new File(pathToExportTo));
			for (int i = 0; i < columnasExportables; i++) {
				csv.write(modelo.getColumnName(i) + ",");
			}
			csv.write("\n");
			for (int i = 0; i < filasExportables; i++) {
				for (int j = 0; j < columnasExportables; j++) {
					csv.write(modelo.getValueAt(i, j).toString() + ",");
				}
				csv.write("\n");
			}
			csv.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String buscarNombre(String idDisc) {
		DisciplinaDAOImp disc = new DisciplinaDAOImp();
		LinkedList<Disciplina> listaDisc = disc.getListaDisciplinas();
		ListIterator<Disciplina> iterador = listaDisc.listIterator();
		while (iterador.hasNext()) {
			Disciplina d = iterador.next();
			if (d.getID().equals(idDisc)) {
				return d.getNombre();
			}
		}
		return null;
	}

	private String buscarPaisDepor(LinkedList<Deportista> listaDB, String id) {
		ListIterator<Deportista> it = listaDB.listIterator();
		while (it.hasNext()) {
			Deportista d = it.next();
			if (d.getID().equals(id)) {
				return (dao.getnombrePais(d.getIDPais()));
			}
		}
		return null;
	}

	private Deportista buscarNombreDepor(LinkedList<Deportista> listaDB, String id) {
		ListIterator<Deportista> it = listaDB.listIterator();
		while (it.hasNext()) {
			Deportista d = it.next();
			if (d.getID().equals(id)) {
				return (d);
			}
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int columnas = tabla.getColumnModel().getColumnIndexAtX(e.getX());
		int filas = e.getY() / tabla.getRowHeight();
		if (filas < tabla.getRowCount() && filas >= 0 && columnas < tabla.getColumnCount() && columnas >= 0) {
			Object value = tabla.getValueAt(filas, columnas);
			if (value instanceof JButton) {
				((JButton) value).doClick();
				JButton botonClick = (JButton) value;
				if (botonClick.equals(botonEditar)) {
					this.setVisible(false);
					AtletaEnDisciplinaDAOImp atd = new AtletaEnDisciplinaDAOImp();
					DeportistaDAOImp depDao = new DeportistaDAOImp();
					LinkedList<AtletaEnDisciplina> lista = atd.getAllAtletasyDisciplinas();
					int i = tabla.getSelectedRow();
					String id, nombre, apellido, telefono, email, idPais;
					id = lista.get(i).getidDepo();
					nombre = this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getNombre();
					apellido = this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo())
							.getApellido();
					telefono = this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo())
							.getTelefono();
					email = this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getEmail();
					idPais = this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getIDPais();
					Deportista d = new Deportista(id, nombre, apellido, telefono, email, idPais);
					String disciplinaOriginal = lista.get(i).getidDisc();
					EdicionDeportistaGUI editar = new EdicionDeportistaGUI(d, disciplinaOriginal);
					// Lleno campos con info ya existente
					editar.setNombre(
							this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getNombre());
					editar.setTel(
							this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getTelefono());
					editar.setApellido(
							this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getApellido());
					editar.setEmail(
							this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getEmail());
					int disc = ((Integer.parseInt(lista.get(i).getidDisc())) - 1);
					int pais = (Integer.parseInt(
							this.buscarNombreDepor(depDao.getAllDeportistas(), lista.get(i).getidDepo()).getIDPais())
							- 1);
					editar.setLista(disc, pais);
				} else if (botonClick.equals(botonEliminar)) {
					int respuesta = JOptionPane.showConfirmDialog(botonClick, "Esta seguro que desea eliminar la fila?",
							getTitle(), JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						AtletaEnDisciplinaDAOImp depo = new AtletaEnDisciplinaDAOImp();
						int i = tabla.getSelectedRow();
						LinkedList<AtletaEnDisciplina> lista = depo.getAllAtletasyDisciplinas();
						AtletaEnDisciplina d = new AtletaEnDisciplina(lista.get(i).getidDepo(),
								lista.get(i).getidDisc());
						depo.eliminarAtleta(d);
						this.setVisible(false);
						DeportistaGUI nueva = new DeportistaGUI();
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