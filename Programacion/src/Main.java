package Programacion.src;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static Scanner inputValue;
	public static File carpeta = new File("./Programacion/CSVs");
	public static ArrayList<Empleado> empleados = new ArrayList<>();
	public static ArrayList<Departamento> departamentos = new ArrayList<>();
	public static int eleccion;



	public static void main(String[] args) {
		//Menu();
		LeerCarpeta();
		//buscarEmpleadoCategoria("A3");
		empleadosPorDepart();
	}




	/**
	 * @author Óscar Fernandez
	 */
	// Leer un string del usuario
	public static int leerEntero(String mensaje) throws InputMismatchException {
		int numero;
		while (true) {
			try {
				inputValue = new Scanner(System.in);
				System.out.println(mensaje);
				numero = inputValue.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("No es un numero valido");
				continue;
			}

		}
		return numero;

	}


	/**
	 * @author Óscar Fernandez
	 */
	//Leemos un string introducido por el usuario
	public static String leerCadena(String mensaje) throws InputMismatchException {
		while (true) {
			try {
				inputValue = new Scanner(System.in);
				System.out.println(mensaje);
				return inputValue.nextLine();
			} catch (InputMismatchException e) {
				continue;
			}

		}

	}

	//Leemos un string introducido por el usuario

	/**
	 * @author Óscar Fernandez
	 */

	public static void LeerCarpeta() {

		for (File archivo: carpeta.listFiles()) {

			switch (archivo.getName()) {

				
				case "Empleado.csv":

					cargarEmpleados(archivo);

					break;

				// Si hay un archivo de departamentos
				case "Departamento.csv":

					cargarDepartamentos(archivo);

			}
		}


	}
	/**
	 * @author Óscar Fernandez
	 */
	//Cargamos los empleados a memoria
	private static void cargarDepartamentos(File archivo) {
		ArrayList<ArrayList<String>> datosDepartamentos = leerCsv(archivo.getPath());

		for (int i = 1; i < datosDepartamentos.size(); i++) {
			ArrayList<String> datosDepartamento = datosDepartamentos.get(i);

			int id = Integer.parseInt(datosDepartamento.get(obtenerIndice(datosDepartamentos, "id")));
			String nombre = datosDepartamento.get(obtenerIndice(datosDepartamentos, "Nombre"));

			departamentos.add(new Departamento(id, nombre));
		}
	}

	/**
	 * @author Óscar Fernandez
	 */
	//Cargamos los empleados a memoria
	private static void cargarEmpleados(File archivo) {
		ArrayList<ArrayList<String>> datosEmpleados = leerCsv(archivo.getPath());


		for (int i = 1; i < datosEmpleados.size(); i++) {

			ArrayList<String> datosEmpleado = datosEmpleados.get(i);

			int id = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "id")));
			String dni = datosEmpleado.get(obtenerIndice(datosEmpleados, "NIF"));
			String nombre = datosEmpleado.get(obtenerIndice(datosEmpleados, "Nombre"));
			String apellido1 = datosEmpleado.get(obtenerIndice(datosEmpleados, "Apellido1"));
			String apellido2 = datosEmpleado.get(obtenerIndice(datosEmpleados, "Apellido2"));
			String cuenta = datosEmpleado.get(obtenerIndice(datosEmpleados, "Cuenta"));
			String antiguedad = datosEmpleado.get(obtenerIndice(datosEmpleados, "Antiguedad"));
			String grupoProfesional = datosEmpleado.get(obtenerIndice(datosEmpleados, "Grupo_profesional"));
			int grupoCotizacion = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "Grupo_Cotizacion")));
			String email = datosEmpleado.get(obtenerIndice(datosEmpleados, "Email"));
			int departamento = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "Departamento")));


			Empleado empleado = new Empleado(id, dni, nombre, apellido1, apellido2, cuenta, antiguedad, grupoProfesional, grupoCotizacion, email, departamento);
			empleados.add(empleado);
		}
	}

	/**
	 * @author Óscar Fernandez
	 */
	public static int obtenerIndice(ArrayList<ArrayList<String>> datosCSV, String campoABuscar) {
		return datosCSV.get(0).indexOf(campoABuscar);
	}



	/**
	 * @author Óscar Fernandez
	 * @author Jose Vicente Ebri Gómez
	 */
	public static ArrayList<ArrayList<String>> leerCsv(String path) {
		ArrayList<ArrayList<String>> arrayEmpleado = new ArrayList<>();

		try {
			File ficheroEntrada = new File(path);
			BufferedReader flujoEntrada = new BufferedReader(new FileReader(ficheroEntrada));

			String linea = flujoEntrada.readLine();
			String[] datos;

			while (linea != null) {

				datos = linea.split(";");


				ArrayList<String> datosFinales = new ArrayList<>();
				for (String dato: datos) {
					datosFinales.add(dato);
				}

				arrayEmpleado.add(datosFinales);
				linea = flujoEntrada.readLine();

			}

			flujoEntrada.close();
			return arrayEmpleado;
		} catch (IOException e) {
			System.out.println("Ha ocurrido un problema al leer");
			return null;
		}


	}

	/**
	 * @author Óscar Fernandez
	 */
	public static void imprimirDatosEmpleado(Empleado empleado){

		System.out.println("\n");
		System.out.println("Id: " + empleado.id);
		System.out.println("DNI: " + empleado.dni);
		System.out.println("Nombre: " + empleado.nombre);
		System.out.println("Apellidos: " + empleado.apellido1 + " " + empleado.apellido2);
		System.out.println("Cuenta: " + empleado.cuenta);
		System.out.println("Antiguedad: " + empleado.antiguedad);
		System.out.println("Categoria Grupo Profesional: " + empleado.catGrupProfesional);
		System.out.println("Grupo cotizacion: " + empleado.grupCotizacion);

		for (int i = 0; i < departamentos.size(); i++) {

			if(departamentos.get(i).id == empleado.departamento){

				System.out.println("Departamento: " + departamentos.get(i).nombre);

			}
		}
		System.out.println("Email: " + empleado.email);

	}

	/**
	 * @author Óscar Fernandez
	 */
	public static void empleadosPorDepart(){
		// Creamos un array que inicializamos en 0 donde guardaremos la cantidad de empleados por departamento
		Integer[] numEmpleados = new Integer[departamentos.size()];
		for (int i = 0; i < numEmpleados.length; i++) {
			numEmpleados[i] = 0;
		}

		//Contamos la cantidad de empleados por departamento
		for (Empleado empleado : empleados) {
			numEmpleados[empleado.departamento-1] +=1;
		}

		//Imprimimos la cantidad de empleado por departamento
		for (int i = 0; i < numEmpleados.length; i++) {
			Departamento departamento = departamentos.get(i);

			System.out.println("El departamento " + departamento.nombre + " tiene " + numEmpleados[i] + " empleados.");
		}
	}

	/**
	 * @author Óscar Fernandez
	 */
	public static void buscarEmpleadoDNI(String dni){
		for (Empleado empleado : empleados) {
			if (empleado.dni.equals( dni)){
				imprimirDatosEmpleado(empleado);
			}
		}
	}

	/**
	 * @author Jose Vicente
	 */
	public static void buscarEmpleadoID(int id){
		for (Empleado empleado : empleados) {
			if (empleado.id == id){
				imprimirDatosEmpleado(empleado);
			}
		}
	}

	/**
	 * @author Jose Vicente Ebri
	 */
	public static void buscarEmpleadoCategoria(String categoria){
		for (Empleado empleado : empleados) {
			if (empleado.catGrupProfesional.equals(categoria)){
				imprimirDatosEmpleado(empleado);
			}
		}
	}


	/**
	 * @author Pere Prior
	 * @author Jose Vicente Ebri
	 */
	public static void Menu() {

		do {
			eleccion = leerEntero("<1.- Consultar> <2.- Incorporar> <3.- Modificar/Eliminar> <0.- Salir>");

			switch (eleccion) {
				case 1:
					menuConsultar();
					break;
				case 2:
					menuIncorporar();
					break;
				case 3:
					menuModificar();
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}

		} while (eleccion != 0);
		System.out.println("Cerrando el programa...");

	}

	/**
	 * @author Jose Vicente Ebri
	 * @author Pere Prior
	 */
	public static void menuConsultar(){
		
		do {
			eleccion = leerEntero("<1.- Datos Empleado> <2.- Horas Extra> <3.- Coste Salarial> <4.- Volver al Inicio> <4.- Salir>");

			switch (eleccion) {
				case 1:
					
					break;
				case 2:

					break;
				case 3:

					break;
				case 4:
					Menu();
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}

		} while (eleccion != 0);

	}


	/**
	 * @author Pere Prior
	 */
	public static void menuIncorporar(){

		do {
			eleccion = leerEntero("<1.- Empleados> <2.- Departamentos> <3.- Datos empresa> <4.- Volver al Inicio> <4.- Salir>");

			switch (eleccion) {
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:
					Menu();
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}

		} while (eleccion != 0);

	}

	/**
	 * @author Pere Prior
	 */
	public static void menuModificar(){

		do {
			eleccion = leerEntero("<1.- Empleados> <2.- Eliminar Empleados> <3.- Eliminar Departamentos> <4.- Volver al Inicio> <4.- Salir>");

			switch (eleccion) {
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:
					Menu();
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}

		} while (eleccion != 0);

	}

}
