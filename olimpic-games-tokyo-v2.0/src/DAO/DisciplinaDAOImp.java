package DAO;

import java.sql.Connection; 
import java.util.LinkedList;
import java.util.ListIterator;

import conexiones.Servidor;
import data.Disciplina;

public class DisciplinaDAOImp implements DisciplinaDAO {
	
	LinkedList<Disciplina> disciplinas = Servidor.crearListaDisciplinas();
	LinkedList<String> nombresDisciplinas;
	Connection cn = null;
	
	public DisciplinaDAOImp(){
		nombresDisciplinas = getNombres();
	}
	
	
	public String obtenerID(Object object) {
		int i = 0;
		String ID = "";
		while(disciplinas != null) {
			if(object.equals(disciplinas.get(i).getNombre())) {
				ID = String.valueOf(i);
			}else {
				i++;
			}
		}
		return ID;
	}
	
	
	public String obtenerNombre(Object object) {
		int i = 0;
		String nombre = "";
		while(disciplinas != null) {
			if(object == disciplinas.get(i).getID()) {
				nombre = String.valueOf(i);
			}else {
				i++;
			}
		}
		return nombre;
	}

	
	@Override
	public LinkedList<Disciplina> getListaDisciplinas() {
		return disciplinas;
	}



	@Override
	public LinkedList<String> getNombres() {
		LinkedList<String> nombres = new LinkedList<String>();
		ListIterator<Disciplina> iterador = this.getListaDisciplinas().listIterator();
		while(iterador.hasNext()) {
			nombres.add(iterador.next().getNombre());
		}
		return nombres;
	}
	

}

