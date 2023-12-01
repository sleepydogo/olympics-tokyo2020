package conexiones;

import java.sql.*;
import java.sql.Connection;
import java.util.LinkedList;

import data.Disciplina;

public class Servidor {

	public static boolean login(String username, String psw) {
		return Conexion.logIn(username, psw);
	}

	public LinkedList<Disciplina> obtenerListaDisciplinas() {
		LinkedList<Disciplina> retorno = new LinkedList<Disciplina>();
		Statement stm = null;
		ResultSet rs = null;
		Connection cn = null;
		try {
			cn = Conexion.getConexion();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM disciplina");

			while (rs.next()) {
				String stringTmp = String.valueOf(rs.getInt(1));
				Disciplina tmp = new Disciplina(stringTmp, rs.getString(2));
				if (tmp != null)
					retorno.addLast(tmp);
			}
			System.out.println("Tabla de disciplinas cargada correctamente");
		} catch (SQLException e) {
			System.out.println("Error al cargar la tabla de disciplinas SQLException:");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la conexion temporal.");
				e.printStackTrace();
			}
		}
		return retorno;
	}

	public static LinkedList<Disciplina> crearListaDisciplinas() {
		LinkedList<Disciplina> disciplinas = new LinkedList<Disciplina>();
		Connection conn = Conexion.getConexion();
		Statement stm = null;
		try {
			stm = ((java.sql.Connection) conn).createStatement();
			String sql = "SELECT * FROM disciplina";
			ResultSet rst = ((java.sql.Statement) stm).executeQuery(sql);
			while (rst.next()) {
				Disciplina disciplina = new Disciplina(rst.getString("id"), rst.getString("nombre"));
				disciplinas.add(disciplina);
			}
			rst.close();
		} catch (Exception e) {
			System.out.println("Error al crear lista de disciplinas.");
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la conexion temporal.");
				e.printStackTrace();
			}
		}
		return disciplinas;
	}

}
