package DAO;

import java.util.LinkedList;

import data.Disciplina;

public interface DisciplinaDAO {
	public LinkedList<Disciplina> getListaDisciplinas();
	public LinkedList<String> getNombres();
}
