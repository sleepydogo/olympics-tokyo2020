package utiles;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

/*
 * Esta clase contiene herramientas que son usadas comunmente por distintas clases
 * del programa.
 * 
 * @author Tomas E. Schattmann
 */
public class Utiles {

	
	/*
	 * Se encarga de setear la locacion del frame para que este se encuentre
	 * en el centro de la pantalla.
	 * 
	 */
	public static Point setCenterLocationFrame(Dimension dimensionFrame) {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		
		int y = ((pantalla.height / 2) - (dimensionFrame.height / 2));
		int x = ((pantalla.width / 2) - (dimensionFrame.width / 2));
		
		Point p = new Point(x,y);
		return p;
	}
	public static int centerFor(int y, int widthContainer) {
		int retorno = ((widthContainer/2) - (y/2));
		return retorno;
	}
	
	public static boolean contieneSoloLetras(String cadena) {
	    for (int x = 0; x < cadena.length(); x++) {
	        char c = cadena.charAt(x);
	        // Si no está entre a y z, ni entre A y Z, ni es un espacio
	        if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
	            return false;
	        }
	    }
	    return true;
	}
}