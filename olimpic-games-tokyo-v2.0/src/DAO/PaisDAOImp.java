package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import data.Disciplina;
import data.Pais;

import conexiones.Conexion;

public class PaisDAOImp implements PaisDAO {


	@Override
	public LinkedList<Pais> getAllPais() {
		LinkedList<Pais> retorno = new LinkedList<Pais>();
		Statement stm = null;
		ResultSet rs = null;
		Connection cn = null;
		try {
			cn = Conexion.getConexion();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM pais");

			while (rs.next()) {
				Pais tmp = new Pais(rs.getInt(1), rs.getString(2));
				if (tmp != null)
					retorno.addLast(tmp);
			}
			System.out.println("Tabla de paises cargada correctamente");
		} catch (SQLException e) {
			System.out.println("Error al cargar la tabla de paises.");
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
				System.out.println("Error al cerrar las conexiones.");
				e.printStackTrace();
			}
		}
		return retorno;
	}

	
	public int cargarPais(Pais p) {
		if (estaAlmacenado(p.getNombre())) return 1;
		Connection cn = Conexion.getConexion();
		PreparedStatement stm = null;
		try {
			stm = cn.prepareStatement("insert into pais values(?,?)", Statement.RETURN_GENERATED_KEYS);

			stm.setInt(1, p.getID());
			stm.setString(2, p.getNombre());
			int filasAfectadas = stm.executeUpdate();
			
			if (filasAfectadas == 0) {
				throw new SQLException("Fallo");
			}
			try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					p.setID(generatedKeys.getInt(1));
				}
			}
			return 0;
		} catch (SQLException e) {
			System.out.println("Error al cargar el pais.");
			e.printStackTrace();
			return 2;
		}
	}

	@Override
	public boolean eliminarPais(int idPais) {
		Connection cn = Conexion.getConexion();
		PreparedStatement stm = null;
		try {
			stm = cn.prepareStatement(
					"DELETE FROM pais WHERE id = ?");
			stm.setInt(1, idPais);
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Error al eliminar el pais.");
			return false;
		}
	}
 
	@Override
	public String getnombrePais(String id) {
		LinkedList<Pais> paisesEnDB = this.getAllPais();
		ListIterator<Pais> iterador = paisesEnDB.listIterator();
		while (iterador.hasNext()) {
	        Pais pais = iterador.next();
	        if (String.valueOf(pais.getID()).equals(id)) {
	            return pais.getNombre();
	        }								
		}
		return null;
	}


	@Override
	public int actualizarPais(int idPais, String nombre) {
		if (estaAlmacenado(nombre)) return 1;
		else {
			Connection cn = Conexion.getConexion();
			PreparedStatement stm = null;
			try {
				stm = cn.prepareStatement(
						"UPDATE pais SET nombre = ? WHERE id = ?");
				stm.setString(1, nombre);
				stm.setInt(2, idPais);
				stm.executeUpdate();	
				return 0;
			} catch (SQLException e) {
				System.out.println("Error al actualizar el pais");
				e.printStackTrace();
				return 2;
			}	
		}
		
	}
	
	public boolean estaAlmacenado(String buscado) {
		LinkedList<Pais> tmp = this.getAllPais();
		Iterator<Pais> iterador = tmp.iterator();
		boolean retorno = false;
		while (!retorno && iterador.hasNext()) {
			if (iterador.next().getNombre().equals(buscado)) retorno = true;
		}
		return retorno;
	}
}



















