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
import javax.swing.SwingConstants;

import utiles.Utiles;

public class MainGUI extends JFrame implements ActionListener {

	private int FRAME_WIDTH = 700;
	private int FRAME_HEIGTH = 400;
	
	private JPanel background = new JPanel();
	private JPanel topSection = new JPanel();
	private JPanel midSection = new JPanel();
	private JPanel bottomSection = new JPanel();
	
	//private Icon icon = new ImageIcon("..\\Tokyo2020\\resources\\main\\olimpico.png");
	private JLabel tituloLabel = new JLabel("Bienvenido al Gestor de Olimpiadas");
	private JLabel firmLabel = new JLabel("@ by Lisandro Vicens, Tomas E. Schattmann");
	private JLabel deportistasLabel = new JLabel("Deportistas", SwingConstants.CENTER);
	private JLabel paisesLabel = new JLabel("Paises", SwingConstants.CENTER);
	private JLabel discLabel = new JLabel("Disciplinas", SwingConstants.CENTER);
	
	private JButton botonDepor = new JButton();
	private JButton botonDisc = new JButton();
	private JButton botonPais = new JButton();

	public MainGUI() {
		setTitle("Gestor de Olimpiadas");
		setSize(FRAME_WIDTH, FRAME_HEIGTH);
		setResizable(false);
		setLocation(Utiles.setCenterLocationFrame(getSize()));
		Image icono = new ImageIcon(getClass().getResource("/resources/gral/iconoApp.png")).getImage();
		ImageIcon icono2 = new ImageIcon(icono.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING));
		setIconImage(icono2.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		configLayout();
		configComponents();
		addComponents();

		setVisible(true);
	}

	private void configLayout() {
		setLayout(null);
		background.setLayout(null);
		topSection.setLayout(null);
		midSection.setLayout(null);
		bottomSection.setLayout(null);
	}

	private void configComponents() {
		// Background
		background.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGTH);
		
		// Section configuration:
		int TOP_SECTION_HEIGHT = 80;
		int MIDDLE_SECTION_HEIGHT = 240;
		int BOTTOM_SECTION_HEIGHT = 80;
		// --> Top Section
		topSection.setSize(FRAME_WIDTH, TOP_SECTION_HEIGHT);
		topSection.setLocation(0, 0);
		topSection.setBackground(new Color(185, 22, 70));
		// --> Middle Section
		midSection.setSize(FRAME_WIDTH, MIDDLE_SECTION_HEIGHT);
		midSection.setLocation(0, 80);
		midSection.setBackground(new Color(251, 243, 228));
		// --> Bottom Section
		bottomSection.setSize(FRAME_WIDTH, BOTTOM_SECTION_HEIGHT);
		bottomSection.setLocation(0, 320);
		bottomSection.setBackground(new Color(16, 86, 82));
		
		// Configuracion del tamaño de botones
		int BOTON_WIDTH = 180;
		int BOTON_HEIGTH = 180;
		int Y_BOTON_LOCATION = Utiles.centerFor(BOTON_HEIGTH, MIDDLE_SECTION_HEIGHT);
		
		// --> Boton deportistas
 		botonDepor.setText("Deportista");
		botonDepor.setSize(BOTON_WIDTH,BOTON_HEIGTH);
		botonDepor.setLocation(40,Y_BOTON_LOCATION);
		botonDepor.addActionListener(this);
		
		Image img = new ImageIcon(
				getClass().getResource("/resources/main/deportistasLogo2.png"))
						.getImage();
		ImageIcon img2 = new ImageIcon(img.getScaledInstance(BOTON_WIDTH, BOTON_HEIGTH, Image.SCALE_SMOOTH));
		botonDepor.setIcon(img2);
		// --> Boton disciplina
		botonDisc.setText("Disciplina");
		botonDisc.setSize(BOTON_WIDTH,BOTON_HEIGTH);
		botonDisc.setLocation(260,Y_BOTON_LOCATION);
		botonDisc.addActionListener(this);
		img = new ImageIcon(
				getClass().getResource("/resources/main/disciplinasLogo.png"))
						.getImage();
		img2 = new ImageIcon(img.getScaledInstance(BOTON_WIDTH, BOTON_HEIGTH, Image.SCALE_SMOOTH));
		botonDisc.setIcon(img2);
		// --> Boton pais
		botonPais.setText("Pais");
		botonPais.setSize(BOTON_WIDTH,BOTON_HEIGTH);
		botonPais.setLocation(480,Y_BOTON_LOCATION);
		botonPais.addActionListener(this);
		img = new ImageIcon(
				getClass().getResource("/resources/main/paisesLogo.png"))
						.getImage();
		img2 = new ImageIcon(img.getScaledInstance(BOTON_WIDTH, BOTON_HEIGTH, Image.SCALE_SMOOTH));
		botonPais.setIcon(img2);
		
		//Labels
		// --> Titulo label
		tituloLabel.setSize(500, 80);
		tituloLabel.setLocation(20,0);
		tituloLabel.setFont(new Font("Arial", Font.ITALIC, 28));
		// --> Subtitulo label
		firmLabel.setSize(250, 30);
		firmLabel.setLocation(400,5);
		firmLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		firmLabel.setForeground(new Color(255,255,255)); 
		// --> Señaladores
		int ESPACIO_LABEL_BOTON = 0;
		deportistasLabel.setSize(BOTON_WIDTH, 30);
		deportistasLabel.setLocation(40, Y_BOTON_LOCATION + BOTON_WIDTH + ESPACIO_LABEL_BOTON);

		discLabel.setSize(BOTON_WIDTH, 30);
		discLabel.setLocation(260, Y_BOTON_LOCATION + BOTON_WIDTH + ESPACIO_LABEL_BOTON);
		
		paisesLabel.setSize(BOTON_WIDTH, 30);
		paisesLabel.setLocation(480, Y_BOTON_LOCATION + BOTON_WIDTH + ESPACIO_LABEL_BOTON);
		
		
		
	}

	private void addComponents() {
		topSection.add(tituloLabel);
		
		midSection.add(botonDepor);
		midSection.add(botonDisc);
		midSection.add(botonPais);
		midSection.add(paisesLabel);
		midSection.add(deportistasLabel);
		midSection.add(discLabel);
		
		bottomSection.add(firmLabel);
		
		background.add(topSection);
		background.add(midSection);
		background.add(bottomSection);
		
		add(background);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(botonDepor)) {
			setVisible(false);
			DeportistaGUI depor = new DeportistaGUI();
		} else if (e.getSource().equals(botonDisc)) {
			JOptionPane.showMessageDialog(this, "Disculpe, disciplinas se encuentra fuera de servicio.");
		} else if (e.getSource().equals(botonPais)) {
			setVisible(false);
			PaisGUI interfazP = new PaisGUI();
		}
	}

}
