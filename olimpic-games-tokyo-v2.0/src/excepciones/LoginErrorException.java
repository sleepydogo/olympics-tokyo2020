package excepciones;

public class LoginErrorException extends Exception{

	private static final long serialVersionUID = 1L;

	public LoginErrorException(String mensaje) {
		super("LoginErrorException: " + mensaje);
	}
	
}
