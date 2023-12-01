package interaccionUsuario;

import java.util.InputMismatchException; 
import java.util.LinkedList;
import java.util.Scanner;
import data.AtletaEnDisciplina;
import data.Deportista;
import data.Disciplina;
import data.Volatil;
import utiles.Utiles;

public class Frontend {

	/*
	 * Metodo que crea la lista de participantes a almacenar en la DB. Trabaja con
	 * el metodo encuesta().
	 */
	public static LinkedList<Deportista> crearListaParticipantes() {
		Utiles utiles = new Utiles();
		utiles.delay(500);
		LinkedList<Deportista> lista = new LinkedList<Deportista>();
		System.out.println("Usted ha seleccionado la opcion de cargar participantes.");
		System.out.println("	-Para finalizar el ingreso escriba ZZZ como nombre de participantes :)");
		Scanner in = new Scanner(System.in);
		Deportista deportista = encuesta(in);
		while (deportista != null) {
			lista.addLast(deportista);
			deportista = encuesta(in);
		}
		/*
		 * en caso de que se haya cargado mal un deportista existe la posibilidad de
		 * eliminarlo antes de enviar la lista a la base de datos.
		 */
		Boolean interruptor = true;
		while (interruptor) {
			System.out.println("Lista a cargar: ");
			Utiles.mostrarLista(lista);
			System.out.println("Desea eliminar algun deportista? y/n");
			switch (in.next()) {
			case "y":
				System.out.print("Ingrese el indice del deportista a eliminar: ");
				int tmp = in.nextInt();
				if (lista.size() != 1) {
					lista.remove(tmp);
					System.out.println("Eliminado satisfactoriamente.");
				} else {
					lista.clear();
					System.out.println("La lista ha sido eliminada, abortando la operacion.");
					interruptor = false;
				}

				break;
			case "n":
				interruptor = false;
				break;
			default:
				System.out.println("Error.");
			}
		}
		if (!lista.isEmpty()) {
			System.out.println("Carga terminada");
			return lista;
		} else
			return null;
	}

	/*
	 * Metodo que se encarga de recibir los datos de los deportistas ingresados por
	 * teclado. 
	 * 
	 */
	private static Deportista encuesta(Scanner in) {
		Utiles utiles = new Utiles();
		utiles.delay(500);
		Deportista deportista = new Deportista();
		String tmp = new String();
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");
		System.out.println("Ingrese los datos del participante:");
		System.out.println("");
		System.out.print("	- Nombre: ");
		tmp = in.nextLine();
		if (!(tmp.compareTo("zzz") == 0)) {
			deportista.setNombre(tmp);
			System.out.print("	- Apellido: ");
			tmp = in.nextLine();
			deportista.setApellido(tmp);
			System.out.print(" 	- Email: ");
			tmp = in.nextLine();
			deportista.setEmail(tmp);
			System.out.print("	- Telefono: ");
			tmp = in.nextLine();
			deportista.setTelefono(tmp);
			// System.out.println(deportista.toString());
			return deportista;
		} else {
			return null;
		}
	}

	/*
	 * Metodo que se encarga de crear y devolver la lista con la asociacion entre el
	 * ID del Deportista y el ID de la disciplina en la cual participa. Contiene
	 * objetos de tipo AtletaEnDisciplina. Estos datos luego son cargados a la base
	 * de datos
	 * 
	 * @return Lista con objetos de tipo AtletaEnDisciplina con los ID de disciplina
	 * en los que participa cada deportista
	 */

	public static LinkedList<AtletaEnDisciplina> crearListaAsociacion(LinkedList<Deportista> listaDep) {
		Utiles utiles = new Utiles();
		utiles.delay(700);
		LinkedList<AtletaEnDisciplina> lista = new LinkedList<AtletaEnDisciplina>();
		System.out.println("");
		System.out.println("Usted ha seleccionado asignar disciplinas a deportistas.");
		System.out.println("Ingrese el ID de la/las disciplinas en las que participa el deportista:");
		LinkedList<Disciplina> disciplinas = Volatil.getListaDisciplinas();
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");
		System.out.println("	- Deportitas: ");
		System.out.println("IDs ID	Nombre     Apellido  Email     Telefono");
		Utiles.mostrarLista(listaDep);
		LinkedList<Integer> disciplinasIngresadas = new LinkedList<Integer>();
		if (disciplinas != null) {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			for (int i = 0; i < listaDep.size(); i++) {
				System.out.println("-----------------------------------------------------------------");
				System.out.println("");
				System.out.println("	- Disciplinas");
				System.out.println("IDs ID  Nombre");
				Utiles.mostrarLista(disciplinas);
				System.out.println("");
				System.out.println("Se ingresan las disciplinas para el Deportista " + listaDep.get(i).getApellido()
						+ " " + listaDep.get(i).getNombre() + " | ID: " + listaDep.get(i).getID());
				System.out.println("");
				System.out.println("Ingrese el ID de la/las disciplinas en las que participa el deportista");
				// resetea la lista temporal de disciplinas ingresadas que se encarga de que no se repitan disciplinas.
				disciplinasIngresadas.clear();
				int tmp = -1;
				do {
					try {
						tmp = in.nextInt();
					} catch (InputMismatchException ex) {
						System.out.println("Error! Intente nuevamente con un valor valido.");
					} finally {
						in.nextLine();
					}
				} while (tmp == -1);
				while (tmp != 0) {
					if ((1 <= tmp) && (tmp <= disciplinas.size()) && !disciplinasIngresadas.contains(tmp)) {
						AtletaEnDisciplina atd = new AtletaEnDisciplina(listaDep.get(i).getID(), tmp);
						lista.add(atd);
						disciplinasIngresadas.add(tmp);
					} else if (!((1 <= tmp) && (tmp <= disciplinas.size()))) {
						System.out.println("Error! Intente nuevamente con un valor valido.");
					} else if (disciplinasIngresadas.contains(tmp)) {
						System.out.println("Error! Disciplina ya ingresada.");
					}
					try {
						tmp = in.nextInt();
					} catch (InputMismatchException ex) {
						System.out.println("Error! Intente nuevamente con un valor valido.");
					} finally {
						in.nextLine();
					}
				}
			}
			System.out.println("Carga de disciplinas terminada");
			return lista;
		} else {
			System.out.println("Error. Se han ingresado 0 desportistas en ejecucion." + "Retornando...");
			return null;
		}
	}

}
