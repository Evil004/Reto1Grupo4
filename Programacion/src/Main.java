package Programacion.src;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Óscar Fernandez, Jonathan Taban, Jose Vicente Ebri, Pere Prior
 */
public class Main {
	public static Scanner inputValue;
	public static File carpeta = new File("./Programacion/CSVs");
	public static final String empleadosCSV = "Empleado.csv";
	public static final String departamentosCSV = "Departamento.csv";

	public static ArrayList<Empleado> empleados = new ArrayList<>();
	public static ArrayList<Departamento> departamentos = new ArrayList<>();

	public static String camposCSVEmpleados;
	public static int eleccion;
	public static String lineaInf = generarLinea("¯");
	public static String lineaSup = generarLinea("_");


	public static void main(String[] args) {
		leerCarpeta();
		menu();

	}


	//------------- Funciones Generales -------------

	/**
	 * autor/es: Óscar Fernandez
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
			}

		}
		return numero;

	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static String generarLinea(String caracter){
		String linea = "";
		for (int i = 0; i < 166; i++) {
			linea += caracter;
		}
		return linea;
	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	//Leemos un string introducido por el usuario
	public static String leerCadena(String mensaje) throws InputMismatchException {
		inputValue = new Scanner(System.in);
		System.out.println(mensaje);
		return inputValue.nextLine();

	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static int obtenerIndice(ArrayList<ArrayList<String>> datosCSV, String campoABuscar) {
		return datosCSV.get(0).indexOf(campoABuscar);
	}

	/**
	 * autor/es: Jonathan Taban
	 */
	public static void limpiarPantalla() {

		for (int x = 0; x < 50; x++) {
			System.out.println(" ");
		}
	}

	/**
	 * autor/es: Óscar Fernandez,  Jonathan Taban
	 */
	public static void esperarEnter() {
		leerCadena("\nApreta Entrer para continuar:");
	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	//Una funcion que busca un archivo con un nombre dado
	public static File buscarArchivo(String nombreArchivo){
		for (File archivo : carpeta.listFiles()) {
			if (archivo.getName() ==nombreArchivo){
				return archivo;
			}
		}
		return new File(carpeta.getPath()+"/"+nombreArchivo);
	}

	//------------- Abrir Archivos -------------

	//Leemos un string introducido por el usuario

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void leerCarpeta() {

		for (File archivo: carpeta.listFiles()) {

			switch (archivo.getName()) {


				case empleadosCSV:

					cargarEmpleados(archivo);

					break;

				// Si hay un archivo de departamentos
				case departamentosCSV:

					cargarDepartamentos(archivo);

			}
		}


	}

	/**
	 * autor/es: Óscar Fernandez
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
	 * autor/es: Óscar Fernandez
	 */
	//Cargamos los empleados a memoria
	private static void cargarEmpleados(File archivo) {

		ArrayList<ArrayList<String>> datosEmpleados = leerCsv(archivo.getPath());


		for (int i = 0; i < datosEmpleados.get(0).size() - 1; i++) {
			String dato = datosEmpleados.get(0).get(i);

			camposCSVEmpleados += dato + ";";

		}
		camposCSVEmpleados += datosEmpleados.get(0).get(datosEmpleados.get(0).size() - 1);


		for (int i = 1; i < datosEmpleados.size(); i++) {

			ArrayList<String> datosEmpleado = datosEmpleados.get(i);

			int id = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "id")));
			String dni = datosEmpleado.get(obtenerIndice(datosEmpleados, "NIF"));
			String nombre = datosEmpleado.get(obtenerIndice(datosEmpleados, "Nombre"));
			String apellido1 = datosEmpleado.get(obtenerIndice(datosEmpleados, "Apellido1"));
			String apellido2 = datosEmpleado.get(obtenerIndice(datosEmpleados, "Apellido2"));
			String cuenta = datosEmpleado.get(obtenerIndice(datosEmpleados, "Cuenta"));

			int nss;
			try {
				nss = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "NSS")));

			}catch (NumberFormatException e){
				 nss = 0;
			}
			String antiguedad = datosEmpleado.get(obtenerIndice(datosEmpleados, "Antiguedad"));
			String grupoProfesional = datosEmpleado.get(obtenerIndice(datosEmpleados, "Grupo_profesional"));
			int grupoCotizacion = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "Grupo_Cotizacion")));
			String email = datosEmpleado.get(obtenerIndice(datosEmpleados, "Email"));
			int departamento = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "Departamento")));


			Empleado empleado = new Empleado(id, dni, nombre, apellido1, apellido2, cuenta,nss, antiguedad, grupoProfesional, grupoCotizacion, email, departamento);
			empleados.add(empleado);
		}
	}


	/**
	 * autor/es: Óscar Fernandez, Jose Vicente Ebri Gómez
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

	//------------- Consultas -------------

	/**
	 * autor/es: Jose Vicente
	 */
	public static void buscarEmpleadoCategoria() {
		String categoria = leerCadena("Introduce la categoria de la que obtener los empleados: ");
		for (Empleado empleado: empleados) {
			if (empleado.catGrupProfesional.equals(categoria)) {
				imprimirDatosEmpleado(empleado);
			}
		}
	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void imprimirDatosEmpleado(Empleado empleado) {

		System.out.println("\n");
		System.out.println("Id: " + empleado.id);
		System.out.println("DNI: " + empleado.dni);
		System.out.println("Nombre: " + empleado.nombre);
		System.out.println("Apellidos: " + empleado.apellido1 + " " + empleado.apellido2);
		System.out.println("Cuenta: " + empleado.cuenta);
		System.out.println("Antiguedad: " + empleado.antiguedad);
		System.out.println("Categoria Grupo Profesional: " + empleado.catGrupProfesional);
		System.out.println("Grupo cotizacion: " + empleado.grupCotizacion);

		for (Departamento departamento: departamentos) {

			if (departamento.id == empleado.departamento) {

				System.out.println("Departamento: " + departamento.nombre);

			}
		}
		System.out.println("Email: " + empleado.email);

	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void cantEmpleadosPorDepart() {

		if (departamentos.size() > 0){
			for (Departamento departamento: departamentos) {
				System.out.println("Hay " + contarEmpleadoEnDep(departamento.id) + " empleados en el departamento " + departamento.nombre);
			}

		}else {
			System.out.println("No se han encontrado departamentos");
		}

	}

	/**
	 * autor/es: Óscar Fernandez, Jonathan Taban
	 */
	public static void buscarEmpleadoDNI() {

		while (true) {
			String dni = leerCadena("Introduce el DNI del empleado a buscar: ");
			for (Empleado empleado: empleados) {
				if (empleado.dni.equals(dni)) {
					imprimirDatosEmpleado(empleado);
					return;
				}
			}
			System.out.println("No existe un empleado con ese DNI");
		}
	}

	/**
	 * autor/es: Jose Vicente
	 */
	public static Empleado buscarEmpleadoID(int id) {
		for (Empleado empleado: empleados) {
			if (empleado.id == id) {
				return empleado;
			}
		}
		return null;
	}

	/**
	 * autor/es: Jose Vicente Ebri,  Jonathan Taban
	 */


	public static void buscarEmpleadoPorDepartamento() {
		while (true){

			int idDepartamento = leerEntero("Introduce el ID del departamento:");
			for (Empleado empleado: empleados) {
				if (empleado.departamento == idDepartamento) {
					System.out.printf("ID: %-12s Empleado: %-12s Departamento: %-12s \n", empleado.id,empleado.nombre, departamentos.get(idDepartamento).nombre);
					return;
				}
			}
			System.out.println("No se ha encontrado un departamento con ese id");
		}

	}

	//------------- Incorporaciones -------------

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void incorporarTrabajador() {
		Empleado empleado = crearEmpleado();
		empleados.add(empleado);
	}

	private static Empleado crearEmpleado() {
		int id = empleados.get(empleados.size() - 1).id + 1;
		String dni = leerCadena("Introduce el DNI del empleado: ");
		String nombre = leerCadena("Introduce el nombre del empleado: ");
		String apellido1 = leerCadena("Introduce el primer apellido del empleado: ");
		String apellido2 = leerCadena("Introduce el segundo apellido del empleado: ");
		String cuenta = leerCadena("Introduce la cuenta del empleado: ");
		int nss = leerEntero("Introduce el numero de la seguridad social del empleado: ");
		int antiguedad = leerEntero("Introduce la antiguedad del empleado: ");
		String catGrupProfesional = leerCadena("Introduce la categoria del grupo profesional del empleado: ");
		String grupCotizacion = leerCadena("Introduce el grupo de cotizacion del empleado: ");
		int departamento = leerEntero("Introduce el departamento del empleado: ");
		String email = leerCadena("Introduce el email del empleado: ");


		Empleado empleado = new Empleado(id, dni, nombre, apellido1, apellido2, cuenta, nss, catGrupProfesional, grupCotizacion, departamento, email, antiguedad);
		return empleado;
	}

	/**
	 * autor/es: Jose Vicente Ebri
	 */
	public static void incorporarDepartamento() {
		int id = departamentos.get(departamentos.size() - 1).id + 1;
		String nombre = leerCadena("Introduce el nombre del departamento ");


		Departamento departamento = new Departamento(id, nombre);
		departamentos.add(departamento);
	}

	//---------------- Modificar ----------------


	/**
	 * autor/es: Jose Vicente Ebri
	 */
	public static void modificarDatosPersonales() {
		int id = leerEntero("Introduce el ID del empleado a modificar:");

		System.out.println("\nIntroduce los datos modificados: \n");
		Empleado empleadoAModificar = crearEmpleado();

		empleadoAModificar.id = id;
		empleados.set(empleados.indexOf(buscarEmpleadoID(id)), empleadoAModificar);
		for (Empleado empleado: empleados) {
			imprimirDatosEmpleado(empleado);
		}


	}

	//------------- Eliminar -----------------


	/**
	 * autor/es: Oscar, Jose Vicente
	 */
	public static void eliminarDatosDepartamento() {
		int id = leerEntero("Introduce el ID del Departamento a eliminar: ");

		Departamento departamento = null;

		for (int i = 0; i < departamentos.size(); i++) {
			departamento = departamentos.get(i);
			if (departamento.id == id) {
				break;
			}
		}
		if (departamento == null) {
			System.out.println("No se ha encontrado el departamento con el id " + id);
			return;
		}

		int numEmpleados = contarEmpleadoEnDep(id);

		if (numEmpleados <= 0) {
			return;
		}

		System.out.println("Vas a eliminar el departamento " + departamento.nombre + " que tiene " + numEmpleados + " empleados");

		boolean flag = true;
		while (flag) {
			String decision = leerCadena("Quieres continuar? (si/no) Si continuas se eliminaran todos los empleados en cascada");

			switch (decision) {
				case "si":
					flag = false;

					while (contarEmpleadoEnDep(id) > 0) {
						for (int j = 0; j < empleados.size(); j++) {
							Empleado empleado = empleados.get(j);

							if (empleado.departamento == id) {
								empleados.remove(empleados.indexOf(empleado));
							}

						}
					}

					departamentos.remove(departamento);

					break;
				case "no":
					flag = false;

					break;
				default:
					System.out.println("Introduce una respuesta valida");
			}

		}
	}

	private static int contarEmpleadoEnDep(int idDep) {
		int numEmpleados = 0;
		for (Empleado empleado: empleados) {

			if (empleado.departamento == idDep) {
				numEmpleados++;
			}
		}
		return numEmpleados;
	}

	/**
	 * autor/es: Pere Prior
	 */
	public static void eliminarDatosEmpleado() {
		int id = leerEntero("Introduce el ID del empleado");

		for (Empleado empleado: empleados) {

			if (id == empleado.id) {
				empleados.remove(empleados.indexOf(buscarEmpleadoID(id)));
				System.out.println("Se ha borrado el empleado indicado");
			}

		}
	}


	//------------- Guardar en CSVs -------------

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void escribirCSVs() {
		if (empleados != null) {
			System.out.println("Escribiendo CSV de Empleado.csv...");
			escribirEmpleadosCSV();
			System.out.println("Done");

		}
		if (departamentos != null) {
			System.out.println("Escribiendo CSV de Departamento.csv...");

			escribirDepartamentoCSV();
			System.out.println("Done");

		}
	}


	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void escribirDepartamentoCSV() {


		File ficheroSalida = buscarArchivo(departamentosCSV);

		try {
			BufferedWriter flujoSalida = new BufferedWriter(new FileWriter(ficheroSalida));

			flujoSalida.write("id;Nombre");
			flujoSalida.newLine();

			for (Departamento departamento: departamentos) {
				flujoSalida.write(departamento.id + ";" + departamento.nombre);
				flujoSalida.newLine();
			}
			flujoSalida.close();


		} catch (IOException e) {
			System.out.println("Error al escribir");
		}
	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void escribirEmpleadosCSV() {

		File ficheroSalida = buscarArchivo(empleadosCSV);

		try {
			BufferedWriter flujoSalida = new BufferedWriter(new FileWriter(ficheroSalida));

			flujoSalida.write("NIF;Nombre;Apellido1;Apellido2;Cuenta;Antiguedad;NSS;Grupo_profesional;Grupo_Cotizacion;Email;Departamento;id");
			flujoSalida.newLine();

			for (Empleado empleado: empleados) {
				flujoSalida.write(empleado.dni + ";" + empleado.nombre + ";" + empleado.apellido1 + ";" + empleado.apellido2 + ";" + empleado.cuenta + ";" + empleado.antiguedad + ";" + empleado.nss + ";" + empleado.catGrupProfesional + ";" + empleado.grupCotizacion + ";" + empleado.email + ";" + empleado.departamento + ";" + empleado.id);
				flujoSalida.newLine();
			}
			flujoSalida.close();


		} catch (IOException e) {
			System.out.println("Error al escribir");
		}
	}


	//------------- Menus -------------



	/**
	 * autor/es: Jonathan
	 */
	public static void menu() {

		do {
			limpiarPantalla();

			System.out.println();
			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Consultar " ,  "2.Incorporar", "3.Modificar/Eliminar", "4. Exportar a CSV", "0.Salir"));

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
				case 4:
					escribirCSVs();

					break;
				case 0:
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);
		System.out.println("Cerrando el programa...");

	}

	/**
	 * autor/es: Jonathan
	 */
	public static void menuConsultar() {

		do {
			limpiarPantalla();

			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Empleados y departamentos","2.Horas Extra","3.Coste Salarial","4.Volver al Inicio","0.Salir"));

			switch (eleccion) {
				case 1:
					MenuRelacionadoaEmpleado();
					break;
				case 2:
					MenuHorasExtra();
					break;
				case 3:
					MenuCosteSalarial();
					break;
				case 4:
					menu();
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion invalida");

			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	/**
	 * autor/es: Jonathan
	 */
	public static void MenuRelacionadoaEmpleado() {

		do {
			limpiarPantalla();

			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Todos los Empleado", "2.Empleado por DNI", "3.Empleados en un departamento", "4.Numero de empleados por Dep.", "5.empleados por categorias", "6.Volver al Inicio", "0.Salir","","",""));

			switch (eleccion) {
				case 1:
					for (Empleado empleado: empleados) {
						imprimirDatosEmpleado(empleado);
					}
					esperarEnter();
					break;
				case 2:
					buscarEmpleadoDNI();
					esperarEnter();

					break;
				case 3:
					buscarEmpleadoPorDepartamento();
					esperarEnter();

					break;
				case 4:
					cantEmpleadosPorDepart();
					esperarEnter();
					break;
				case 5:
					buscarEmpleadoCategoria();
					esperarEnter();
					break;
				case 6:
					menu();
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion Invalida");
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	/**
	 * autor/es: Jonathan
	 */
	public static void MenuHorasExtra() {

		do {
			limpiarPantalla();

			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Horas Extra por ID", "2.Horas Extra por DNI", "3.Volver al Inicio", "0.Salir", "", ""));

			switch (eleccion) {
				case 1:
					//Horas Extra por ID
					break;
				case 2:
					//Horas Extra por DNI
					break;
				case 3:
					menu();
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion invalida");
					esperarEnter();
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	/**
	 * autor/es: Jonathan
	 */
	public static void MenuCosteSalarial() {

		do {
			limpiarPantalla();
			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Coste Salarial por Grupo Cotizacion", "2.Coste Salarial desde Nombre", "3.Volver al Inicio", "0.Salir", "", ""));

			switch (eleccion) {
				case 1:
					//Coste Salarial por Grupo Cotizacion
					break;
				case 2:
					//Coste Salarial desde Nombre
					break;
				case 3:
					menu();
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion invalida");
					esperarEnter();
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	/**
	 * autor/es: Jonathan
	 */
	public static void menuIncorporar() {

		do {
			limpiarPantalla();
			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Nuevos Empleados", "2.Nuevos Departamentos", "3.Volver al Inicio", "0.Salir", ""));

			switch (eleccion) {
				case 1:
					incorporarTrabajador();
					esperarEnter();
					break;
				case 2:
					incorporarDepartamento();
					esperarEnter();

					break;
				case 3:
					menu();
					break;
				default:
					System.out.println("Opcion inválida");
					esperarEnter();
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);

	}

	/**
	 * autor/es: Jonathan
	 */
	public static void menuModificar() {

		do {
			limpiarPantalla();
			eleccion = leerEntero(String.format( lineaSup+"\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n"+ lineaInf,"1.Modificar Datos Empleado", "2.Eliminar Datos Departamentos", "3.Eliminar Datos Personales", "4.Volver al Inicio", "0.Salir"));

			switch (eleccion) {
				case 1:
					//Modificar Datos Personales Empleado
					modificarDatosPersonales();
					esperarEnter();

					break;
				case 2:
					eliminarDatosDepartamento();
					esperarEnter();

					break;
				case 3:
					eliminarDatosEmpleado();
					esperarEnter();

					break;
				case 4:
					menu();
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion inválida");
					esperarEnter();
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);

	}
}
