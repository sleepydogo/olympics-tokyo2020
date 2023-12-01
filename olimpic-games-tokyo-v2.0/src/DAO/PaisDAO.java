package DAO;

import java.util.LinkedList;

import data.Pais;

public interface PaisDAO {	
	public LinkedList<Pais> getAllPais();	
	public int cargarPais(Pais p);
	public String getnombrePais(String id);
	public boolean eliminarPais(int idPais);
	public int actualizarPais(int idPais, String nuevoNombre);
}
