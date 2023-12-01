package DAO;

import java.util.LinkedList;

import data.AtletaEnDisciplina;
import data.Deportista;

public interface AtletaEnDisciplinaDAO {
	public LinkedList<AtletaEnDisciplina> getAllAtletasyDisciplinas();
	public String getidDisc(String nombreDisc);
	public LinkedList<AtletaEnDisciplina> crearListaAsociacion(LinkedList<Deportista> lista);
	public void asociarAtletas(LinkedList<Deportista> lista);
	public void eliminarAtleta(AtletaEnDisciplina atd);
}