package Programacion.src;
/**
 * @author Óscar Fernandez
 * Clase para almacenar los Empleados.
 */

public class Empleado {

	int id;
	String dni;
	String nombre;
	String apellido1;
	String apellido2;
	String cuenta;
	int nss;
	String antiguedad;
	String catGrupProfesional;
	int grupCotizacion;
	String email;
	int departamento;

	public Empleado ( int id, String dni, String nombre, String apellido1, String apellido2, String cuenta, int nss, String antiguedad, String catGrupProfesional, int grupCotizacion, String email, int departamento) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.cuenta = cuenta;
		this.nss = nss;
		this.antiguedad = antiguedad;
		this.catGrupProfesional = catGrupProfesional;
		this.grupCotizacion = grupCotizacion;
		this.email = email;
		this.departamento = departamento;
	}


}
