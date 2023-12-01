package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import DAO.PaisDAOImp;
import data.Pais;
import utiles.Utiles;
/*
 * Esta clase se encarga de la interfaz grafica correspondiente al ingreso
 * de un pais a la base de datos.
 * 
 * @author Tomas E. Schattmann
 */

public class IngresoPaisGUI extends JFrame implements ActionListener {

	private int FRAME_HEIGHT = 400;
	private int FRAME_WIDTH = 700;

	private JPanel background = new JPanel();
	private JPanel footer = new JPanel();

	private JLabel imgLabel = new JLabel();
	private JLabel nombreLabel = new JLabel("Nombre: ");
	private JTextField ingresoTF = new JTextField();
	private JButton volverButton = new JButton("Volver");
	private JButton guardarButton = new JButton("Guardar");

	private boolean ModoEditar;
	private JLabel modoEditar = new JLabel("Modo editar activado.");
	private int idPaisAEditar;

	/*
	 * Constructor de la interfaz grafica de ingreso de pais, esta interfaz posee
	 * dos modos de ejecucion, default mode y editar mode.
	 */

	public IngresoPaisGUI(boolean ModoEditar) {
		this.ModoEditar = ModoEditar;
		setTitle("GestorJuegosOlimpicos - Ingreso pais");
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

	public IngresoPaisGUI(boolean ModoEditar, int idPaisAEditar) {
		this.ModoEditar = ModoEditar;
		this.idPaisAEditar = idPaisAEditar;
		setTitle("GestorJuegosOlimpicos - Ingreso pais");
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

	/*
	 * Este metodo se encarga de configurar todos los componentes pertenecientes a
	 * el frame.
	 */
	private void configComponents() {
		// Background
		background.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		background.setBackground(new Color(223, 216, 202));

		// Label's
		// --> Nombre
		nombreLabel.setSize(130, 30);
		nombreLabel.setLocation(30, 117);
		nombreLabel.setAlignmentX(SwingConstants.LEFT);
		nombreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		// --> Modo editar
		if (ModoEditar) {
			modoEditar.setSize(130, 30);
			modoEditar.setLocation(30, 250);
			modoEditar.setFont(new Font("Arial", Font.PLAIN, 10));
		}
		// Ingreso text field
		ingresoTF.setSize(200, 35);
		ingresoTF.setLocation(30, 153);
		ingresoTF.setFont(new Font("Arial", Font.PLAIN, 14));

		// Guardar button
		guardarButton.setSize(100, 30);
		guardarButton.setLocation(30, 210);
		guardarButton.addActionListener(this);

		// Volver Button
		volverButton.setSize(100, 30);
		volverButton.setLocation(135, 210);
		volverButton.addActionListener(this);

		// --> Imagen
		int imgWidth = 300;
		int imgHeigth = 300;
		imgLabel.setSize(imgWidth, imgHeigth);
		imgLabel.setLocation(350, 30);
		imgLabel.setVerticalAlignment(JLabel.TOP);
		imgLabel.setVerticalAlignment(JLabel.TOP);
		Image iconoTitulo = new ImageIcon(getClass().getResource("/resources/ingresoPais/Paises.png")).getImage();
		ImageIcon iconoTitulo2 = new ImageIcon(iconoTitulo.getScaledInstance(imgWidth, imgHeigth, Image.SCALE_SMOOTH));
		imgLabel.setIcon(iconoTitulo2);

		// Footer
		footer.setSize(FRAME_WIDTH, 50);
		footer.setLocation(0, 350);
		footer.setBackground(new Color(16, 86, 82));
	}

	private void addComponents() {
		background.add(nombreLabel);
		background.add(volverButton);
		background.add(ingresoTF);
		background.add(imgLabel);
		background.add(guardarButton);
		if (ModoEditar) {
			background.add(modoEditar);
		}

		background.add(footer);

		add(background);

	}

	private void setLayoutManager() {
		background.setLayout(null);
		setLayout(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ModoEditar) {
			if (e.getSource() == guardarButton) {
				if (!Utiles.contieneSoloLetras(ingresoTF.getText())) {
					JOptionPane.showMessageDialog(this, "El pais ingresado debe contener solo letras.");
				} else {
					PaisDAOImp pdao = new PaisDAOImp();
					switch (pdao.actualizarPais(idPaisAEditar, ingresoTF.getText())) {
					case 0:
						JOptionPane.showMessageDialog(this, "El pais se ha actualizado correctamente.");
						break;
					case 1: 
						JOptionPane.showMessageDialog(this, "Error, el pais ya esta almacenado en la base de datos.");
						ingresoTF.setText(null);
						break;
					case 2: 
						JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el pais.");
						break;
					}	
				}

			}
			setVisible(false);
			PaisGUI tmp = new PaisGUI();
		} else {
			if (e.getSource() == guardarButton) {
				if (!Utiles.contieneSoloLetras(ingresoTF.getText())) {
					JOptionPane.showMessageDialog(this, "El pais ingresado debe contener solo letras.");
				} else {
					PaisDAOImp pdao = new PaisDAOImp();
					switch (pdao.cargarPais(new Pais(ingresoTF.getText()))) {
					case 0:
						JOptionPane.showMessageDialog(this, "El pais se ha cargado correctamente.");
						break;
					case 1:
						JOptionPane.showMessageDialog(this, "Error, el pais ya esta almacenado en la base de datos.");
						break;
					case 2:
						JOptionPane.showMessageDialog(this, "Ha ocurrido un error al cargar el pais.");
						break;
					}
					ingresoTF.setText(null);
				}
			}
		}
		if (e.getSource() == volverButton) {
			PaisGUI pais = new PaisGUI();
			setVisible(false);
		}
	}

}
