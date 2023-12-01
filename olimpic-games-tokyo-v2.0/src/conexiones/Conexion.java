package conexiones;

import java.sql.*;

public class Conexion {

	private static final String URL = "jdbc:mysql://localhost:3306/tokyo2021_e3";
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static Connection conexion = null;
	private static boolean loggedIn = false;
	private static String USERNAME = null;
	private static String PASSWORD = null;
	
	private Conexion() {
		try {
			Class.forName(CONTROLADOR);
			conexion = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			System.out.println("Conexion establecida.");
			loggedIn = true;
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar controlador jdbc.");
		} catch (SQLException e) {
			System.out.println("El inicio de sesion ha fallado, intentelo nuevamente.");
		}

	}
	
	static boolean logIn(String user, String psw) {
		if (!loggedIn) {
			USERNAME = user;
			if (psw.isBlank()) {
				PASSWORD = null;
			}
			else {
				PASSWORD = psw;
			}
			new Conexion();
		}
		return loggedIn;
	}
 	
	public static Connection getConexion() {
		return conexion;
	}
	
}
