package DAO;

import java.sql.SQLException; 
import java.util.LinkedList;

import data.Deportista;

public interface DeportistaDAO {
	public LinkedList<Deportista> getAllDeportistas();
	public void agregarDeportistas(LinkedList<Deportista> deportistasIngresados) throws SQLException;

}