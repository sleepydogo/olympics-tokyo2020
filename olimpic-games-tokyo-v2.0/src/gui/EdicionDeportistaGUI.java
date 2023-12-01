package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import DAO.DeportistaDAOImp;
import DAO.DisciplinaDAOImp;
import DAO.PaisDAOImp;

import data.Deportista;
import data.Pais;
import excepciones.PaisNotFoundException;
import utiles.Utiles;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EdicionDeportistaGUI extends JFrame implements ActionListener {
	private Deportista d = null;
	private Deportista d1 = null;
	private String discOriginal;
	private static int contador = 0;
	LinkedList<Deportista> deportistasIngresados = new LinkedList<Deportista>();
	DisciplinaDAOImp disciplina = new DisciplinaDAOImp();
	PaisDAOImp pais = new PaisDAOImp();
	// AtletaEnDisciplinaDAOImp atl = new AtletaEnDisciplinaDAOImp();
	LinkedList<Pais> paisesEnDB = pais.getAllPais();
	LinkedList<String> nombresDisc = disciplina.getNombres();
	// LinkedList<String> nombresPais = pais.getAllPais(); Se crea una lista con los
	// paises ingresados en la DB

	private JPanel bandaRoja = new JPanel();
	private String[] arrayDisciplinas = nombresDisc.toArray(new String[0]);

	String imagen = ".\\resources\\ingresoDeportista\\person.png";
	ImageIcon icon = new ImageIcon(imagen);

	private JComboBox<Pais> listaPais = new JComboBox<Pais>();
	private JComboBox<String> listaDisc = new JComboBox<>(arrayDisciplinas);
	private JPanel contentPane = new JPanel();
	private JTextField nombre = new JTextField();
	private JTextField apellido = new JTextField();
	private JTextField email = new JTextField();
	private JTextField tel = new JTextField();
	private JLabel labelNombre = new JLabel("Nombre");
	private JLabel labelApellido = new JLabel("Apellido");
	private JLabel labelMail = new JLabel("E-mail");
	private JLabel labelTel = new JLabel("Telefono");
	private JLabel labelDisc = new JLabel("Disciplina");
	private JLabel labelPais = new JLabel("Pais");
	private JButton guardar = new JButton("Actualizar");

	private JLabel labelError = new JLabel();
	private JLabel labelBien = new JLabel();
	private JButton volver = new JButton("Volver");

	public EdicionDeportistaGUI(Deportista d, String disciplinaOriginal) {
		contador++;
		this.d = d;
		this.d1 = d;
		this.discOriginal = disciplinaOriginal;
		System.out.println(discOriginal);
		d.setDisciplina(imagen);
		for (Pais p : paisesEnDB) {
			listaPais.addItem(p);
		}
		setTitle("Gestor de Juegos Olimpicos - Editar Deportista");
		setSize(800, 597);
		setLocation(Utiles.setCenterLocationFrame(getSize()));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel imgLabel = new JLabel();

		Image imagen = new ImageIcon(getClass().getResource("/resources/ingresoDeportista/person.png")).getImage();
		ImageIcon imagen2 = new ImageIcon(imagen.getScaledInstance(250, 350, Image.SCALE_SMOOTH));
		imgLabel.setIcon(imagen2);
		imgLabel.setBounds(500, -300, 1920, 1080);
		contentPane.add(imgLabel);

		Image icono = new ImageIcon(getClass().getResource("/resources/gral/iconoApp.png")).getImage();
		ImageIcon icono2 = new ImageIcon(icono.getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING));
		setIconImage(icono2.getImage());

		labelNombre.setFont(new Font("Arial", Font.PLAIN, 16));
		labelNombre.setBounds(105, 93, 99, 43);
		contentPane.add(labelNombre);

		nombre.setFont(new Font("Arial", Font.PLAIN, 20));
		nombre.setBounds(213, 100, 220, 30);
		contentPane.add(nombre);
		nombre.setColumns(10);
		nombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char ch = e.getKeyChar();
				if (Character.isLetter(ch)) {
					guardar.setBorderPainted(true);
					guardar.setFocusPainted(true);
					guardar.setEnabled(true);
					labelError.setText("");
				} else {
					labelBien.setText("");
					guardar.setBorderPainted(false);
					guardar.setFocusPainted(false);
					guardar.setEnabled(false);
					labelError.setText("Los campos Nombre y Apellido solo pueden contener letras");
				}
			}
		});

		labelApellido.setFont(new Font("Arial", Font.PLAIN, 16));
		labelApellido.setBounds(105, 153, 99, 43);
		contentPane.add(labelApellido);

		apellido.setFont(new Font("Arial", Font.PLAIN, 20));
		apellido.setBounds(213, 160, 220, 30);
		contentPane.add(apellido);
		apellido.setColumns(10);
		apellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char ch = e.getKeyChar();
				if (Character.isLetter(ch)) {
					guardar.setBorderPainted(true);
					guardar.setFocusPainted(true);
					guardar.setEnabled(true);
					labelError.setText("");
				} else {
					labelBien.setText("");
					guardar.setBorderPainted(false);
					guardar.setFocusPainted(false);
					guardar.setEnabled(false);
					labelError.setText("Los campos Nombre y Apellido solo pueden contener letras");
				}
			}
		});

		labelMail.setFont(new Font("Arial", Font.PLAIN, 16));
		labelMail.setBounds(105, 213, 99, 43);
		contentPane.add(labelMail);

		email.setFont(new Font("Arial", Font.PLAIN, 20));
		email.setBounds(213, 220, 220, 30);
		contentPane.add(email);
		email.setColumns(10);
		email.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (email.getText().contains("@")) {
					guardar.setBorderPainted(true);
					guardar.setFocusPainted(true);
					guardar.setEnabled(true);
					labelError.setText("");
				} else {
					labelBien.setText("");
					guardar.setBorderPainted(false);
					guardar.setFocusPainted(false);
					guardar.setEnabled(false);
					labelError.setText("El campo email debe contener '@'");
				}
			}
		});
		email.setEnabled(false);

		labelTel.setFont(new Font("Arial", Font.PLAIN, 16));
		labelTel.setBounds(105, 273, 99, 43);
		contentPane.add(labelTel);

		labelPais.setFont(new Font("Arial", Font.PLAIN, 16));
		labelPais.setBounds(105, 333, 99, 43);
		contentPane.add(labelPais);

		labelBien.setFont(new Font("Calibri", Font.BOLD, 20));
		labelBien.setForeground(new Color(92, 184, 92));
		labelBien.setBounds(30, 12, 600, 30);
		contentPane.add(labelBien);

		listaPais.setFont(new Font("Arial", Font.PLAIN, 16));
		listaPais.setBounds(213, 340, 220, 30);
		contentPane.add(listaPais);

		labelDisc.setFont(new Font("Arial", Font.PLAIN, 16));
		labelDisc.setBounds(105, 393, 99, 43);
		contentPane.add(labelDisc);

		listaDisc.setFont(new Font("Arial", Font.PLAIN, 16));
		listaDisc.setBounds(213, 400, 220, 30);
		contentPane.add(listaDisc);

		guardar.addActionListener(this);
		guardar.setBounds(260, 460, 100, 30);
		contentPane.add(guardar);

		volver.addActionListener(this);
		volver.setBounds(680, 10, 90, 30);
		contentPane.add(volver);

		labelError.setFont(new Font("Calibri", Font.BOLD, 20));
		labelError.setForeground(Color.BLACK);
		labelError.setBounds(30, 12, 600, 30);
		contentPane.add(labelError);

		bandaRoja.setLayout(null);
		bandaRoja.setBounds(0, 0, 900, 50);
		bandaRoja.setBackground(new Color(185, 22, 70));
		contentPane.add(bandaRoja, BorderLayout.LINE_START);

		tel.setFont(new Font("Arial", Font.PLAIN, 20));
		tel.setBounds(213, 280, 220, 30);
		contentPane.add(tel);
		tel.setColumns(10);
		tel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					guardar.setBorderPainted(true);
					guardar.setFocusPainted(true);
					guardar.setEnabled(true);
					labelError.setText("");
				} catch (NumberFormatException e1) {
					labelBien.setText("");
					labelError.setText("El campo 'Telefono' solo acepta numeros");
					guardar.setBorderPainted(false);
					guardar.setFocusPainted(false);
					guardar.setEnabled(false);
				}
			}
		});
		setVisible(true);

		contentPane.setBackground((new Color(251, 243, 228)));
		if (contador == 1) {
			JOptionPane.showMessageDialog(this,
					"En la ventana de 'Editar' se podran modificar los datos personales del deportista. \nPara modificar el email deberemos eliminar el deportista erroneamente cargado");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(volver)) {
			DeportistaGUI idep = new DeportistaGUI();
			idep.setVisible(true);
			this.setVisible(false);
		} else if (e.getSource().equals(guardar)) {
			try {
				paisCheck();
				if (vacio()) {
					labelError.setText("Debe rellenar todos los campos");
				} else {
					nombre.setText(nombre.getText().trim());
					apellido.setText(apellido.getText().trim());
					email.setText(email.getText().trim());
					tel.setText(tel.getText().trim());
					labelBien.setText("Agregado correctamente");
					DeportistaDAOImp depo = new DeportistaDAOImp();
					depo.actualizarDeportistas(editarDepo(d), d1, discOriginal);
					DeportistaGUI interfaz = new DeportistaGUI();
					interfaz.setVisible(true);
					this.setVisible(false);
				}
			} catch (PaisNotFoundException e1) {
				System.out.println(e1.getMessage());
				JOptionPane.showMessageDialog(this, "Error, no hay paises ingresados con el cual asociar al deportista.");
				this.setVisible(false);
				MainGUI nueva = new MainGUI();
			}
		}
	}

	public void paisCheck() throws PaisNotFoundException {
		PaisDAOImp tmp = new PaisDAOImp();
		if (tmp.getAllPais().isEmpty()) {
			throw new PaisNotFoundException(
					"Error, no se puede ingresar el deportista debido a" + "que actualmente no hay paises cargados.");

		}
	}

	public Deportista editarDepo(Deportista d) {
		d.setNombre(nombre.getText());
		d.setApellido(apellido.getText());
		d.setEmail(email.getText());
		d.setTelefono(tel.getText());
		d.setIDPais(String.valueOf(((Pais) listaPais.getSelectedItem()).getID()));
		d.setDisciplina(listaDisc.getSelectedItem().toString());
		// AtletaEnDisciplina atld = new AtletaEnDisciplina();
		return d;
	}

	public LinkedList<Deportista> ingresados() {
		return deportistasIngresados;
	}

	public boolean vacio() {
		if (nombre.getText().equals("") || apellido.getText().equals("") || email.getText().equals("")
				|| tel.getText().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public void setNombre(String nombre) {
		this.nombre.setText(nombre);
	}

	public void setApellido(String apellido) {
		this.apellido.setText(apellido);
	}

	public void setEmail(String email) {
		this.email.setText(email);
	}

	public void setTel(String tel) {
		this.tel.setText(tel);
	}

	public void setLista(int i, int j) {
		this.listaDisc.setSelectedIndex(i);
		this.listaPais.setSelectedIndex(j);
	}

}