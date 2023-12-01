package data;

public class Deportista {
	private String id;
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String idPais;
	private Pais pais;
	private String disciplina;
	
	
	public Deportista() {
		
	}
	
	public Deportista(String id, String apellido, String nombre, String email, String telefono, String idP) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.idPais = idP;
	}
	
	public String getDisciplina(){
		return disciplina;
	}
	
	public void setDisciplina(String d) {
		this.disciplina = d;
	}
	public Pais getPais() {
		return this.pais;
	}
	
	public void setPais(Pais p) {
		this.pais = p;
	}
	
	public String getIDPais() {
		return idPais;
	}
	
	public void setIDPais(String i) {
		this.idPais = i;
	}
	
	public void setPais(String id) {
		this.idPais = id;
	}
	
	
	public String getID() {
		return id;
	}
		public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setID(String iden) {
		this.id = iden;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Override
	public String toString() {
		if(this.getID() == null) {
			return (" - " + "    " + this.getNombre() + "    " + this.getApellido() + "    " + this.getEmail() + "    " + this.getTelefono());
		}else {
			return (this.getID() + "   " + this.getNombre() + "       " + this.getApellido() + "      " + this.getEmail() + "      " + this.getTelefono());
		}
	}
	
	
		
}
