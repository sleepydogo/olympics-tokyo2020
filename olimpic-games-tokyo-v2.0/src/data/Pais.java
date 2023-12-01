package data;

/*
 * Clase que se encarga de la representacion de un pais
 * 
 * @author Tomas E. Schattmann
 */

public class Pais {
	private String nombre;
	private int id;
	
	/* 
	 * Este constructor se usa para crear una instancia de pais en 
	 * local, por eso el id es 0. Ya que eso lo determina la base de datos
	 * y es necesario subirlo y consultarlo
	 */
	public Pais() {
		
	}
	
	public Pais(String n){
		this.nombre = n;
		this.id = 0;
	}
	
	public Pais(int i, String n){
		this.nombre = n;
		this.id = i;
	}
	
	public void setNombre(String n) {
		this.nombre = n;
		
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}

	public static int cargarPais(String nombre) {
		return 0;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
}
