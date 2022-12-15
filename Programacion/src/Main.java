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

	public static String camposCSVEmpleados;
	public static int eleccion;


	public static void main(String[] args) {
		leerCarpeta();
		menu();

	}


	//------------- Funciones Basicas -------------

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
				System.out.println("Error al leer la cadena");
			}

		}

	}

	/**
	 * @author Óscar Fernandez
	 */
	public static int obtenerIndice(ArrayList<ArrayList<String>> datosCSV, String campoABuscar) {
		return datosCSV.get(0).indexOf(campoABuscar);
	}

	/**
	 * @author Jonathan Taban
	 */
	public static void limpiarPantalla() {
		leerCadena("\nApreta Entrer para continuar:");

		for (int x = 0; x < 50; x++) {
			System.out.println(" ");
		}
	}

	//------------- Abrir Archivos -------------

	//Leemos un string introducido por el usuario

	/**
	 * @author Óscar Fernandez
	 */
	public static void leerCarpeta() {

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

	//------------- Consultas -------------

	/**
	 * @author Jose Vicente
	 */
	public static void buscarEmpleadoCategoria(String categoria) {
		for (Empleado empleado: empleados) {
			if (empleado.catGrupProfesional.equals(categoria)) {
				imprimirDatosEmpleado(empleado);
			}
		}
	}

	/**
	 * @author Óscar Fernandez
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
	 * @author Óscar Fernandez
	 */
	public static void cantEmpleadosPorDepart() {

		for (Departamento departamento : departamentos ) {
			System.out.println("Hay " + contarEmpleadoEnDep(departamento.id) + " empleados en el departamento " + departamento.nombre);
		}

	}

	/**
	 * @author Óscar Fernandez
	 */
	public static void buscarEmpleadoDNI() {
		String dni = leerCadena("Introduce el DNI del empleado a buscar: ");
		for (Empleado empleado: empleados) {
			if (empleado.dni.equals(dni)) {
				imprimirDatosEmpleado(empleado);
			}
		}
	}

	/**
	 * @author Jose Vicente
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
	 * @author Jose Vicente Ebri
	 */

	public static void buscarEmpleadoPorDepartamento() {
		int id = leerEntero("Introduce el ID del departamento");
		for (Empleado empleado: empleados) {
			if (empleado.departamento == id) {
				String name = empleado.nombre;
				//name = length(name);
				//System.out.println("ID: " + empleado.id + "\t" + "Empleado: " + empleado.nombre + String.format(name-"\t%-10s", "") + "Departamento: " + departamentos.get(id).nombre);
			}
		}
	}

	//------------- Incorporaciones -------------

	/**
	 * @author Óscar Fernandez
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
		int antiguedad = leerEntero("Introduce la antiguedad del empleado: ");
		String catGrupProfesional = leerCadena("Introduce la categoria del grupo profesional del empleado: ");
		String grupCotizacion = leerCadena("Introduce el grupo de cotizacion del empleado: ");
		int departamento = leerEntero("Introduce el departamento del empleado: ");
		String email = leerCadena("Introduce el email del empleado: ");


		Empleado empleado = new Empleado(id, dni, nombre, apellido1, apellido2, cuenta, catGrupProfesional, grupCotizacion, departamento, email, antiguedad);
		return empleado;
	}

	/**
	 * @author Jose Vicente Ebri
	 */
	public static void incorporarDepartamento() {
		int id = departamentos.get(departamentos.size() - 1).id + 1;
		String nombre = leerCadena("Introduce el nombre del departamento ");


		Departamento departamento = new Departamento(id, nombre);
		departamentos.add(departamento);
	}

	//---------------- Modificar ----------------


	/**
	 * @author Jose Vicente Ebri
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
	 * @author Oscar, Jose Vicente
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
		if(departamento == null){
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

					while(contarEmpleadoEnDep(id)>0){
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
	 * @author Pere Prior
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
	 * @author Óscar Fernandez
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
	 * @author Óscar Fernandez
	 */
	public static void escribirDepartamentoCSV() {

		//TODO: Quitar el archivo hardcodeado.
		File ficheroSalida = new File("./Programacion/CSVs/Departamento.csv");

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
	 * @author Óscar Fernandez
	 */
	public static void escribirEmpleadosCSV() {
		//TODO: Quitar el archivo hardcodeado.
		File ficheroSalida = new File("./Programacion/CSVs/Empleado.csv");

		try {
			BufferedWriter flujoSalida = new BufferedWriter(new FileWriter(ficheroSalida));

			flujoSalida.write("NIF;Nombre;Apellido1;Apellido2;Cuenta;Antiguedad;NSS;Grupo_profesional;Grupo_Cotizacion;Email;Departamento;id");
			flujoSalida.newLine();

			for (Empleado empleado: empleados) {
				flujoSalida.write(empleado.dni + ";" + empleado.nombre + ";" + empleado.apellido1 + ";" + empleado.apellido2 + ";" + empleado.cuenta + ";" + empleado.antiguedad + ";" + "" + ";" + empleado.catGrupProfesional + ";" + empleado.grupCotizacion + ";" + empleado.email + ";" + empleado.departamento + ";" + empleado.id);
				flujoSalida.newLine();
			}
			flujoSalida.close();


		} catch (IOException e) {
			System.out.println("Error al escribir");
		}
	}


	//------------- Menus -------------

	/**
	 * @author Jonathan
	 */
	public static void menu() {

		do {
			eleccion = leerEntero("1.Consultar | 2.Incorporar | 3.Modificar/Eliminar | 4. Exportar a CSV | 0.Salir");

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
				default:
					System.out.println("Opcion inválida");
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);
		System.out.println("Cerrando el programa...");

	}

	public static void menuConsultar() {

		do {
			eleccion = leerEntero("1.Datos relacionados a Empleado | 2.Horas Extra | 3.Coste Salarial | 4.Volver al Inicio | 0.Salir");

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
				default:
					System.out.println("Se ha cerrado el programa");
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	public static void MenuRelacionadoaEmpleado() {

		do {
			eleccion = leerEntero("1.Mostrar todos los Empleado | 2.Empleado por DNI | 3.Empleado desde Dep. | 4.Numero de empleados por Dep. | 5.Datos empleado desde categoria | 6.Volver al Inicio | 0.Salir");

			switch (eleccion) {
				case 1:
					for (Empleado empleado: empleados) {
						imprimirDatosEmpleado(empleado);
					}
					break;
				case 2:
					buscarEmpleadoDNI();
					break;
				case 3:
					buscarEmpleadoPorDepartamento();
					break;
				case 4:
					cantEmpleadosPorDepart();
					break;
				case 5:
					buscarEmpleadoCategoria(null);
					break;
				case 6:
					menu();
					break;
				default:
					System.out.println("Se ha cerrado el programa");
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	public static void MenuHorasExtra() {

		do {
			eleccion = leerEntero("1.Horas Extra por ID | 2.Horas Extra por DNI | 3.Volver al Inicio | 0.Salir");

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
				default:
					System.out.println("Se ha cerrado el programa");
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	public static void MenuCosteSalarial() {

		do {
			eleccion = leerEntero("1.Coste Salarial por Grupo Cotizacion | 2.Coste Salarial desde Nombre | 3.Volver al Inicio | 0.Salir");

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
				default:
					System.out.println("Se ha cerrado el programa");
			}
			limpiarPantalla();

		} while (eleccion != 0);
	}

	public static void menuIncorporar() {

		do {
			eleccion = leerEntero("1.Nuevos Empleados | 2.Nuevos Departamentos | 3.Volver al Inicio | 0.Salir");

			switch (eleccion) {
				case 1:
					incorporarTrabajador();
					break;
				case 2:
					incorporarDepartamento();
					break;
				case 3:
					menu();
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);

	}

	public static void menuModificar() {

		do {
			eleccion = leerEntero("1.Modificar Datos Personales Empleado | 2.Eliminar Datos Departamentos | 3.Eliminar Datos Personales | 4.Volver al Inicio | 0.Salir");

			switch (eleccion) {
				case 1:
					//Modificar Datos Personales Empleado
					modificarDatosPersonales();
					break;
				case 2:
					eliminarDatosDepartamento();
					break;
				case 3:
					eliminarDatosEmpleado();
					break;
				case 4:
					menu();
					break;
				default:
					System.out.println("Opcion inválida");
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);

	}
}
