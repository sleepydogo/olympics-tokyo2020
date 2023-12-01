package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import conexiones.Conexion;
import data.AtletaEnDisciplina;
import data.Deportista;
import data.Disciplina;

public class AtletaEnDisciplinaDAOImp implements AtletaEnDisciplinaDAO {

	Connection cn = null;

	@Override
	public LinkedList<AtletaEnDisciplina> getAllAtletasyDisciplinas() {
		LinkedList<AtletaEnDisciplina> atletas = new LinkedList<AtletaEnDisciplina>();
		Statement stm = null;
		try {
			cn = Conexion.getConexion();
			stm = cn.createStatement();
			String sql = "SELECT * FROM deportista_en_disciplina";
			ResultSet rst = stm.executeQuery(sql);
			while (rst.next()) {
				AtletaEnDisciplina atd = new AtletaEnDisciplina(rst.getString("id_deportista"),
						rst.getString("id_disciplina"));
				atletas.add(atd);
			}
			rst.close();
		} catch (Exception e) {
			System.out.println("Error al cargar las asociaciones < Atleta----Disciplina >");
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
		return atletas;
	}

	@Override
	// Si hay solo 1 entrada del deportista, se borra de la base de datos
	public void eliminarAtleta(AtletaEnDisciplina atd) {
		cn = Conexion.getConexion();
		PreparedStatement stm = null;
		if (apariciones(atd) > 1) {
			try {
				stm = cn.prepareStatement(
						"DELETE FROM deportista_en_disciplina WHERE id_deportista = ? AND id_disciplina = ?");
				stm.setString(1, atd.getidDepo());
				stm.setString(2, atd.getidDisc());
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
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
		} else {
			if (apariciones(atd) == 1) {
				try {
					stm = cn.prepareStatement(
							"DELETE FROM deportista_en_disciplina WHERE id_deportista = ? AND id_disciplina = ?");
					stm.setString(1, atd.getidDepo());
					stm.setString(2, atd.getidDisc());
					stm.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						stm = cn.prepareStatement("DELETE FROM deportista WHERE id = ?");
						stm.setString(1, atd.getidDepo());
						stm.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void asociarAtletas(LinkedList<Deportista> listaIngresados) {
		LinkedList<AtletaEnDisciplina> lista = this.crearListaAsociacion(listaIngresados);
		cn = Conexion.getConexion();
		if (lista != null) {
			ListIterator<AtletaEnDisciplina> iterador = lista.listIterator();
			AtletaEnDisciplina dato = null;
			PreparedStatement stm = null;
			Integer i = 0;
			Integer o = 0;
			while (iterador.hasNext()) {
				dato = iterador.next();
				if (!(yaExiste(dato))) {
					try {
						stm = cn.prepareStatement("insert into deportista_en_disciplina values(?,?)");
						stm.setString(1, dato.getidDepo());
						stm.setString(2, dato.getidDisc());
						int filasAfectadas = stm.executeUpdate();
						i++;
						if (filasAfectadas == 0) {
							throw new SQLException("Fallo");
						}
					} catch (SQLException e) {
						e.printStackTrace();
						o++;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Uno de los deportistas ingresados ya se encuentra en la base de datos");
				}
			}
			System.out.println("Se cargaron exitosamente " + i + " de " + (i + o));
		}
	}

	public int apariciones(AtletaEnDisciplina atd) {
		ListIterator<AtletaEnDisciplina> ite = this.getAllAtletasyDisciplinas().listIterator();
		int encontre = 0;
		while (ite.hasNext()) {
			AtletaEnDisciplina dato = ite.next();
			if ((atd.getidDepo().equals(dato.getidDepo()))) {
				encontre++;
			}
		}
		return encontre;
	}

	public boolean yaExiste(AtletaEnDisciplina atd) {
		ListIterator<AtletaEnDisciplina> ite = this.getAllAtletasyDisciplinas().listIterator();
		boolean existe = false;
		while (ite.hasNext()) {
			AtletaEnDisciplina dato = ite.next();
			if (atd.getidDepo().equals(dato.getidDepo()) && atd.getidDisc().equals(dato.getidDisc())) {
				existe = true;
			}
		}
		return existe;
	}

	@Override
	public String getidDisc(String nombreDisc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<AtletaEnDisciplina> crearListaAsociacion(LinkedList<Deportista> lista) {
		DeportistaDAOImp dep = new DeportistaDAOImp();
		LinkedList<Deportista> listaID = dep.getAllDeportistas();
		LinkedList<AtletaEnDisciplina> listaAsociacion = new LinkedList<AtletaEnDisciplina>();
		ListIterator<Deportista> iterador = listaID.listIterator();
		while (iterador.hasNext()) {
			Deportista dato = iterador.next();
			ListIterator<Deportista> iteradorDisc = lista.listIterator();
			while (iteradorDisc.hasNext()) {
				Deportista datoDisc = iteradorDisc.next();
				if ((dato.getEmail().equals(datoDisc.getEmail()) && (dato.getIDPais().equals(datoDisc.getIDPais())))) {
					AtletaEnDisciplina atd = new AtletaEnDisciplina(dato.getID(), buscarID(datoDisc.getDisciplina()));
					listaAsociacion.add(atd);
				}
			}
		}
		return listaAsociacion;
	}

	public String buscarID(String nombreDisc) {
		DisciplinaDAOImp disc = new DisciplinaDAOImp();
		LinkedList<Disciplina> listaDisc = disc.getListaDisciplinas();
		ListIterator<Disciplina> iterador = listaDisc.listIterator();
		boolean encontre = false;
		String id = "";
		while (iterador.hasNext() && encontre == false) {
			Disciplina d = iterador.next();
			if (d.getNombre().equals(nombreDisc)) {
				encontre = true;
				id = d.getID();
			}
		}
		return id;
	}

	public String buscarNombre(String ID) {
		DisciplinaDAOImp disc = new DisciplinaDAOImp();
		LinkedList<Disciplina> listaDisc = disc.getListaDisciplinas();
		ListIterator<Disciplina> iterador = listaDisc.listIterator();
		while (iterador.hasNext()) {
			Disciplina d = iterador.next();
			if (d.getNombre().equals(ID)) {
				return d.getNombre();
			}
		}
		return null;
	}
}
