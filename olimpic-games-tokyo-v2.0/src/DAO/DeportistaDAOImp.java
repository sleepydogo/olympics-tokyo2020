package DAO;

import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import conexiones.Conexion;
import data.AtletaEnDisciplina;
import data.Deportista;

import java.sql.*;

public class DeportistaDAOImp implements DeportistaDAO {

	Connection cn = null;

	@Override
	public LinkedList<Deportista> getAllDeportistas() {
		LinkedList<Deportista> deportistasEnDB = new LinkedList<Deportista>();
		Statement stm = null;
		cn = Conexion.getConexion();
		try {
			stm = cn.createStatement();
			String sql = "SELECT * FROM deportista";
			ResultSet rst = stm.executeQuery(sql);
			while (rst.next()) {
				Deportista deportista = new Deportista(rst.getString("id"), rst.getString("apellidos"),
						rst.getString("nombres"), rst.getString("email"), rst.getString("telefono"),
						rst.getString("id_pais"));
				deportistasEnDB.add(deportista);
			}
			rst.close();
		} catch (Exception e) {
			System.out.println("Error al cargar la lista de deportistas.");
		}finally {
			try {
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la conexion temporal.");
				e.printStackTrace();
			}
		}
		return deportistasEnDB;
	}

	@Override
	public void agregarDeportistas(LinkedList<Deportista> deportistasIngresados) {
		cn = Conexion.getConexion();
		if (deportistasIngresados != null) {
			ListIterator<Deportista> iterador = deportistasIngresados.listIterator();
			Deportista dato = null;
			PreparedStatement stm = null;
			Integer i = 0;
			Integer o = 0;
			while (iterador.hasNext()) {
				LinkedList<Deportista> baseDatos = getAllDeportistas();
				dato = iterador.next();
				if (!(estaEnDB(baseDatos, dato))) {
					try {
						stm = cn.prepareStatement("insert into deportista values(?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
						stm.setString(1, dato.getID());
						stm.setString(2, dato.getApellido());
						stm.setString(3, dato.getNombre());
						stm.setString(4, dato.getEmail());
						stm.setString(5, dato.getTelefono());
						stm.setString(6, dato.getIDPais());
						int filasAfectadas = stm.executeUpdate();
						i++;
						if (filasAfectadas == 0) {
							throw new SQLException("Fallo");
						}
						try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								dato.setID(generatedKeys.getString(1));
							}
						}
					} catch (SQLException e) {
						System.out.println("Error al cargar deportista.");
						o++;
					}finally {
						try {
							if (stm != null) {
								stm.close();
							}
						} catch (SQLException e) {
							System.out.println("Error al cerrar la conexion temporal.");
							e.printStackTrace();
						}
					}
					System.out.println("Se cargaron exitosamente " + i + " de " + (i + o));
				}
			}
		}
	}

	public void actualizarDeportistas(Deportista d, Deportista original, String disciplinaOriginal) {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		AtletaEnDisciplinaDAOImp atd = new AtletaEnDisciplinaDAOImp();
		Deportista aux = original;
		Integer i = 0;
		Integer o = 0;
		if (!(existe(aux))) {
			try {
				stm = cn.prepareStatement(
						"UPDATE deportista_en_disciplina SET id_disciplina = ? WHERE id_deportista = ? AND id_disciplina = ?");
				stm.setString(1, atd.buscarID(d.getDisciplina()));
				stm.setString(2, d.getID());
				stm.setString(3, disciplinaOriginal);
				stm.executeUpdate();
				stm1 = cn.prepareStatement(
						"UPDATE deportista SET apellidos = ?, nombres = ?, email = ?, telefono = ?, id_pais = ? WHERE id = ?");
				stm1.setString(1, d.getApellido());
				stm1.setString(2, d.getNombre());
				stm1.setString(3, d.getEmail());
				stm1.setString(4, d.getTelefono());
				stm1.setString(5, d.getIDPais());
				stm1.setString(6, d.getID());
				stm1.executeUpdate();
				i++;
			} catch (SQLException e) {
				System.out.println("Error al actualizar el deportista");
				e.printStackTrace();
				o++;
			}finally {
				try {
					if (stm != null) {
						stm.close();
					}
					if (stm1 != null) {
						stm1.close();
					}
				} catch (SQLException e) {
					System.out.println("Error al cerrar la conexion temporal.");
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"El Deportista editado ya se encuentra en la base de datos. No se han realizado cambios");
		}
	}

	public boolean estaEnDB(LinkedList<Deportista> lista, Deportista dato) {
		ListIterator<Deportista> it = lista.listIterator();
		boolean esta = false;
		Deportista d = null;
		if (it != null) {
			while (it.hasNext() && esta == false) {
				d = it.next();
				if (dato.getEmail().equals(d.getEmail())) {
					esta = true;
				}
			}
		}
		return esta;

	}

	public boolean existe(Deportista d) {
		AtletaEnDisciplinaDAOImp atd = new AtletaEnDisciplinaDAOImp();
		ListIterator<AtletaEnDisciplina> iterador = atd.getAllAtletasyDisciplinas().listIterator();
		ListIterator<Deportista> iteradorDepo = this.getAllDeportistas().listIterator();
		AtletaEnDisciplina dato = null;
		Deportista datoDepo = null;
		boolean existe = false;
		while (iterador.hasNext() && existe == false) {
			dato = iterador.next();
			while (iterador.hasNext() && iteradorDepo.hasNext() && existe == false) {
				datoDepo = iteradorDepo.next();
				if (((d.getID().equals(dato.getidDepo())) && (atd.buscarID(d.getDisciplina()).equals(dato.getidDisc())))
						&& datoDepo.equals(datoDepo) && (d.getEmail().equals(datoDepo.getEmail()))) {
					existe = true;
				}
			}
		}
		return existe;
	}
}
