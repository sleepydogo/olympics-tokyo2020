package conexiones.servidor;

import java.sql.*;

public class Conexion {

	private static final String URL = "jdbc:mysql://localhost:3306/tokyo2021";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";

	static {

		try {
			Class.forName(CONTROLADOR);

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar controlador");
			e.printStackTrace();
		}

	}
	
	public Connection conectar() {
		Connection conexion = null;
		
		try {
			conexion = DriverManager.getConnection(URL,USER,PASSWORD);
			System.out.println("Conexion establecida.");
		}catch(SQLException e) {
			System.out.println("Error en la conexion");
			e.printStackTrace();
		}
		return conexion;
	}
}
