package excepciones;

public class PaisNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public PaisNotFoundException(String mensaje) {
		super("PaisNotFoundException: " + mensaje);
	}
	
}
