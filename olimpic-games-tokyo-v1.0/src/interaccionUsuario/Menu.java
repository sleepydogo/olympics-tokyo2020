package interaccionUsuario;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import conexiones.servidor.Servidor;
import data.AtletaEnDisciplina;
import data.Deportista;
import data.Volatil;
import utiles.Utiles;

public class Menu {

	public void menu(Scanner in, Servidor servidor, Volatil volatil, Utiles util) {
		util.delay(700);
		System.out.println("" + " _____      _                  ____   ___ ____   ___  \r\n"
				+ "/__   \\___ | | ___   _  ___   |___ \\ / _ \\___ \\ / _ \\ \r\n"
				+ "  / /\\/ _ \\| |/ / | | |/ _ \\    __) | | | |__) | | | |\r\n"
				+ " / / | (_) |   <| |_| | (_) |  / __/| |_| / __/| |_| |\r\n"
				+ " \\/   \\___/|_|\\_\\\\__, |\\___/  |_____|\\___/_____|\\___/ \r\n"
				+ "                 |___/                                \r\n"
				+ " __        __ _                                       \r\n"
				+ "/ _\\ ___  / _| |___      ____ _ _ __ ___              \r\n"
				+ "\\ \\ / _ \\| |_| __\\ \\ /\\ / / _` | '__/ _ \\             \r\n"
				+ "_\\ \\ (_) |  _| |_ \\ V  V / (_| | | |  __/             \r\n"
				+ "\\__/\\___/|_|  \\__| \\_/\\_/ \\__,_|_|  \\___|             \r\n"		
				
				+"												\r\n"
				+"												\r\n"
				+"   # # #         # # #         # # #   \r\n"
				+ " #       #     #       #     #       # \r\n"
				+ "#         # # #         # # #         #\r\n"
				+ "#       # #   # #     # #   # #       #\r\n"
				+ " #     # #     # #   # #     # #     # \r\n"
				+ "   # # #         # # #         # # #   \r\n"
				+ "        #       #     #       #        \r\n"
				+ "          # # #         # # #                            \r\n "
				+"                                                       ");
		System.out.println("    @ by Lisandro Vicens, Tomas E. Schattmann");
		System.out.println("    @ version 0.0.1");
		Boolean interruptor = true;
		String tmp = null;
		while (interruptor) {
			util.delay(700);
			System.out.println("-----------------------------------------------------------------");
			System.out.println("");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Escoja una opcion:");
			System.out.println("   - 1. Cargar deportistas.");
			System.out.println("   - 2. Asignar disciplinas a deportistas.");
			System.out.println("   - 3. Mostrar lista disciplinas.");
			System.out.println("   - 4. Mostrar deportistas ingresados en esta ejecucion.");
			System.out.println("   - 5. Mostrar los participantes de las disciplinas.");
			System.out.println("   - 0. Cerrar.");
			System.out.println("");
			System.out.print("  > ");
			tmp = in.nextLine();
			System.out.println();
			switch (tmp) {
			case "1": {
				try {
					servidor.ingresarDeportistas(volatil);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case "2": {
				servidor.asociarADisciplina(volatil.getListaDeportistasEjecucion());
				break;
			}
			case "3": {
				System.out.println("Disciplinas: ");
				System.out.println("IDs ID   Nombre");
				Utiles.mostrarLista(Volatil.getListaDisciplinas());
				break;
			}
			case "4": {
				System.out.println("   Deportistas: ");
				LinkedList<Deportista> tmpCase4 = volatil.getListaDeportistasEjecucion();
				if (tmpCase4 != null) {
					System.out.println("IDs ID  Nombre  Apellido  Email  Telefono");	
				}
				Utiles.mostrarLista(tmpCase4);
				break;
			}
			case "5": {
				System.out.println("Participantes y disciplinas: ");
				LinkedList<AtletaEnDisciplina> tmpCase5 = Volatil.getListaAsociacion();
				if (tmpCase5 != null) {
					System.out.println("  | ID Deportista | ID Disciplina");
				}
				Utiles.mostrarLista(tmpCase5);
				break;
			}
			case "0": {
				System.out.println("Cerrando...");
				interruptor = false;
				break;
			}
			default:
				System.out.println("Error en el digito ingresado.");
				break;
			}
		}
	}
}
