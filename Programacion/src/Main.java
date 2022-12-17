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
	public static boolean guardado = true;

	public static File carpeta = new File("./Programacion/CSVs");
	public static final String empleadosCSV = "Empleado.csv";
	public static final String grupoCotizacionCSV = "Grupo_Cotizacion.csv";
	public static final String horasExtraCSV = "Horas_Extra.csv";
	public static final String departamentosCSV = "Departamento.csv";

	public static ArrayList<Empleado> empleados = new ArrayList<>();
	public static ArrayList<Departamento> departamentos = new ArrayList<>();
	public static ArrayList<GrupoCotizacion> gruposCotizacion = new ArrayList<>();
	public static ArrayList<HorasExtra> horasExtra = new ArrayList<>();

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
	public static String generarLinea(String caracter) {
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
	public static File buscarArchivo(String nombreArchivo) {
		for (File archivo: carpeta.listFiles()) {
			if (archivo.getName() == nombreArchivo) {
				return archivo;
			}
		}
		return new File(carpeta.getPath() + "/" + nombreArchivo);
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

				case departamentosCSV:
					cargarDepartamentos(archivo);
					break;

				case grupoCotizacionCSV:
					cargarGrupoCotizacion(archivo);
					break;

				case horasExtraCSV:
					cargarHorasExtra(archivo);
					break;

			}
		}


	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	private static <Srting> void cargarGrupoCotizacion(File archivo) {
		ArrayList<ArrayList<String>> datosGruposCot = leerCsv(archivo.getPath());

		for (int i = 1; i < datosGruposCot.size(); i++) {
			ArrayList<String> datosGrupoCot = datosGruposCot.get(i);

			int id = Integer.parseInt(datosGrupoCot.get(obtenerIndice(datosGruposCot, "Grupo_Cotizacion")));
			float sueldoBase = Float.parseFloat(datosGrupoCot.get(obtenerIndice(datosGruposCot, "Sueldo_Base")).replace(",", "."));

			gruposCotizacion.add(new GrupoCotizacion(id, sueldoBase));
		}
	}

	/**
	 * @author Jose Vicente
	 */
	private static void cargarHorasExtra(File archivo) {
		ArrayList<ArrayList<String>> datosHorasExtras = leerCsv(archivo.getPath());

		for (int i = 1; i < datosHorasExtras.size(); i++) {
			ArrayList<String> datosHorasExtra = datosHorasExtras.get(i);
			String nif = datosHorasExtra.get(obtenerIndice(datosHorasExtras, "NIF"));
			String fecha = datosHorasExtra.get(obtenerIndice(datosHorasExtras, "Fecha"));
			int hora_i = Integer.parseInt(datosHorasExtra.get(obtenerIndice(datosHorasExtras, "hora_inicio")));
			int hora_f = Integer.parseInt(datosHorasExtra.get(obtenerIndice(datosHorasExtras, "hora_fin")));
			int sitio = Integer.parseInt(datosHorasExtra.get(obtenerIndice(datosHorasExtras, "Sitio")));
			horasExtra.add(new HorasExtra(nif, fecha, hora_i, hora_f, sitio));
		}
	}

	/**
	 * autor/es: Óscar Fernandez
	 */
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

			} catch (NumberFormatException e) {
				nss = 0;
			}
			String antiguedad = datosEmpleado.get(obtenerIndice(datosEmpleados, "Antiguedad"));
			String grupoProfesional = datosEmpleado.get(obtenerIndice(datosEmpleados, "Grupo_profesional"));
			int grupoCotizacion = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "Grupo_Cotizacion")));
			String email = datosEmpleado.get(obtenerIndice(datosEmpleados, "Email"));
			int departamento = Integer.parseInt(datosEmpleado.get(obtenerIndice(datosEmpleados, "Departamento")));


			Empleado empleado = new Empleado(id, dni, nombre, apellido1, apellido2, cuenta, nss, antiguedad, grupoProfesional, grupoCotizacion, email, departamento);
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
	 * autor/es: Jose Vicente Ebri
	 */
	public static void consultarHorasExtraDNI() {
		while (true) {
			String dni = leerCadena("Introduce el DNI del empleado: ");
			for (HorasExtra hora: horasExtra) {

				if (hora.nif.equals(dni)) {
					for (Empleado empleado: empleados) {
						if (empleado.dni.equals(dni)) {
							int horas = (hora.hora_f - hora.hora_i);
							System.out.println(empleado.nombre + " ha realizado " + horas + " horas extra");
							return;
						}
					}

				}
			}
			System.out.println("Ese empleado no tiene horas extra.");

		}

	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void consultarHorasExtraID() {
		String dniEmpleado = null;
		Empleado empleado = null;

		while (true) {
			int idEmpleado = leerEntero("Introduce el ID del empleado: ");

			for (Empleado empleadoTemp: empleados) {
				if (empleadoTemp.id == idEmpleado) {
					empleado = empleadoTemp;
					dniEmpleado = empleadoTemp.dni;
				}
			}
			if (dniEmpleado != null) {
				break;
			} else {
				System.out.println("No se ha encontrado un empleado con ese ID.");

			}
		}


		for (HorasExtra hora: horasExtra) {
			if (hora.nif.equals(dniEmpleado)) {
				int horas = (hora.hora_f - hora.hora_i);
				System.out.println(empleado.nombre + " ha realizado " + horas + " horas extra");
				return;
			}

		}
		System.out.println("Ese empleado no tiene horas extra.");
	}

	/**
	 * autor/es: Óscar Fernandez
	 */
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
	 * autor/es: Óscar Fernandez
	 */
	public static void consultarCosteSalarialGrupoCot() {
		int categoria = leerEntero("Introduce el numero de categoria a buscar: ");

		for (GrupoCotizacion grupoCotizacion: gruposCotizacion) {
			int empleadosPorGrupo = 0;
			for (Empleado empleado: empleados) {
				if (empleado.grupCotizacion == grupoCotizacion.id) {
					empleadosPorGrupo++;
				}
			}

			if (grupoCotizacion.id == categoria) {
				System.out.println("El grupo de cotizacion " + grupoCotizacion.id + " tiene un coste salarial de " + grupoCotizacion.sueldoBase * empleadosPorGrupo + "€");

			}
		}


	}

	/**
	 * autor/es: Óscar Fernandez
	 */
	public static void consultarCosteSalarialDepartamento() {
		int idDepartamento = leerEntero("Introduce el ID del departamento");

		for (Departamento departamento: departamentos) {
			int costeDepartamento = 0;
			if (departamento.id == idDepartamento) {

				for (Empleado empleado: empleados) {
					if (empleado.departamento == idDepartamento) {
						for (GrupoCotizacion grupoCotizacion: gruposCotizacion) {
							if (empleado.grupCotizacion == grupoCotizacion.id) {
								costeDepartamento += grupoCotizacion.sueldoBase;
							}
						}
					}
				}
				System.out.println("El departamento " + departamento.nombre + " tiene un coste salarial de: " + costeDepartamento + "€");

			}
		}
	}

	/**
	 * autor/es: Jose Vicente
	 */
	public static void buscarEmpleadoCategoria() {
		System.out.println("A1 - A2 - A3 - B1 - B2 -B3");
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

		//TODO: Imprime varios departamentos en algunos empleados
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

		if (departamentos.size() > 0) {
			for (Departamento departamento: departamentos) {
				System.out.println("Hay " + contarEmpleadoEnDep(departamento.id) + " empleados en el departamento " + departamento.nombre);
			}

		} else {
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
	public static void buscarEmpleadoID() {
		while (true) {
			int idEmpleado = leerEntero("Introduce el ID del empleado a buscar");
			for (Empleado empleado: empleados) {
				if (empleado.id == idEmpleado) {
					imprimirDatosEmpleado(empleado);
					return;
				}
			}
			System.out.println("No se ha encontrado un empleado con ese ID.");
		}

	}

	public static Empleado buscarEmpleadoID(int idEmpleado) {
		for (Empleado empleado: empleados) {
			if (empleado.id == idEmpleado) {
				return empleado;
			}
		}
		System.out.println("No se ha encontrado un empleado con ese ID.");
		return null;
	}

	/**
	 * autor/es: Jose Vicente Ebri,  Jonathan Taban
	 */
	public static void buscarEmpleadoPorDepartamento() {
		while (true) {

			boolean flag = false;
			int idDepartamento = leerEntero("Introduce el ID del departamento:");
			for (Empleado empleado: empleados) {
				if (empleado.departamento == idDepartamento) {
					for (Departamento departamento: departamentos) {
						if (departamento.id == idDepartamento) {
							flag = true;
							System.out.printf("ID: %-12s Empleado: %-12s Departamento: %-12s \n", empleado.id, empleado.nombre, departamentos.get(departamentos.indexOf(departamento)).nombre);
						}
					}

				}
			}
			if (flag) {
				return;
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
		System.out.println("Se ha creado un nuevo empleado");
		guardado = false;
	}

	//TODO: Puedes añadir departamentos, grupos y categorias que no existen
	private static Empleado crearEmpleado() {
		int id = empleados.get(empleados.size() - 1).id + 1;
		String dni = leerCadena("Introduce el DNI del empleado: ");
		String nombre = leerCadena("Introduce el nombre del empleado: ");
		String apellido1 = leerCadena("Introduce el primer apellido del empleado: ");
		String apellido2 = leerCadena("Introduce el segundo apellido del empleado: ");
		String cuenta = leerCadena("Introduce la cuenta del empleado: ");
		int nss = leerEntero("Introduce el numero de la seguridad social del empleado: ");
		String antiguedad = leerCadena("Introduce la antiguedad del empleado: ");
		String catGrupProfesional = leerCadena("Introduce la categoria del grupo profesional del empleado: ");
		int grupCotizacion = leerEntero("Introduce el grupo de cotizacion del empleado: ");
		int departamento = leerEntero("Introduce el departamento del empleado: ");
		String email = leerCadena("Introduce el email del empleado: ");


		Empleado empleado = new Empleado(id, dni, nombre, apellido1, apellido2, cuenta, nss, antiguedad, catGrupProfesional, grupCotizacion, email, departamento);
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
		System.out.println("Se ha creado un nuevo departamento");
		guardado = false;
	}

	//---------------- Modificar ----------------


	/**
	 * autor/es: Jose Vicente Ebri, Pere Prior
	 */
	public static void modificarDatosPersonales() {

		while (true) {

			int id = leerEntero("Introduce el ID del empleado a modificar:");

			for (Empleado empleado: empleados) {
				if (id == empleado.id) {

					imprimirDatosEmpleado(buscarEmpleadoID(id));
					System.out.println("...................");

					System.out.println("\nIntroduce los datos modificados: \n");
					Empleado empleadoAModificar = crearEmpleado();

					empleadoAModificar.id = id;
					empleados.set(empleados.indexOf(buscarEmpleadoID(id)), empleadoAModificar);

					imprimirDatosEmpleado(empleado);
					guardado = false;

					return;

				} else {

					System.out.println("Opcion no valida.");

				}
			}
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

		while (true) {
			String decision = leerCadena("Se va a eleminar el departamento " + departamento.nombre + ", estas seguro? (si, no)");
			switch (decision) {
				case "si":
					if (numEmpleados <= 0) {
						departamentos.remove(departamento);
						guardado = false;

						return;
					}

					System.out.println("Vas a eliminar el departamento " + departamento.nombre + " que tiene " + numEmpleados + " empleados");

					while (true) {
						decision = leerCadena("Quieres continuar? (si/no) Si continuas se eliminaran todos los empleados en cascada");

						switch (decision) {
							case "si":

								while (contarEmpleadoEnDep(id) > 0) {
									for (int j = 0; j < empleados.size(); j++) {
										Empleado empleado = empleados.get(j);

										if (empleado.departamento == id) {
											empleados.remove(empleados.indexOf(empleado));
										}

									}
								}

								departamentos.remove(departamento);
								guardado = false;
								return;
							case "no":
								System.out.println("Eliminacion cancelada");
								return;
							default:
								System.out.println("Introduce una respuesta valida");
						}

					}

				case "no":
					System.out.println("Eliminacion cancelada");
					return;
				default:
					System.out.println("Opcion no valida, introduzca una opcion valida.");
					esperarEnter();
					limpiarPantalla();
			}
		}


	}


	/**
	 * autor/es: Pere Prior
	 */
	public static void eliminarDatosEmpleado() {

		while (true) {

			int id = leerEntero("Introduce el ID del empleado");


			for (int i = 0; i < empleados.size(); i++) {

				Empleado empleado = empleados.get(i);

				if (empleado.id == id) {


					System.out.println("Va a eliminar el empleado " + empleado.nombre);

					while (true) {

						String decision = leerCadena("Quieres continuar? (si/no)");

						switch (decision) {

							case "si":
								empleados.remove(empleado);
								System.out.println("Se ha eliminado el empleado");
								guardado = false;

								return;

							case "no":
								System.out.println("Eliminacion cancelada");
								return;

							default:
								System.out.println("Introduce una respuesta valida");
						}
					}
				}


			}
			System.out.println("No se ha encontrado ningun empleado con el id " + id);
			return;
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
	//TODO: Si no se han guardado los datos 	que se avise al usuario antes de salir
	public static void menu() {

		do {
			limpiarPantalla();

			System.out.println();
			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Consultar ", "2.Incorporar", "3.Modificar/Eliminar", "4. Exportar a CSV", "0.Salir"));

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
					esperarEnter();
					guardado = true;
					break;
				case 0:


					boolean cerrar = comprobarSiCerrar();
					if (!cerrar) {
						eleccion = -1;
					} else {
						eleccion = 0;
						continue;
					}
					break;

				default:
					System.out.println("Opcion inválida");
					continue;
			}
			limpiarPantalla();

		} while (eleccion != 0);
		System.out.println("Cerrando el programa...");

	}

	private static boolean comprobarSiCerrar() {
		boolean cerrar = false;
		if (!guardado) {

			boolean flag = true;
			while (flag) {
				String eleccion = leerCadena("Vas a cerrar el programa, pero tienes datos sin guardar, estas seguro? (si/no)");
				switch (eleccion) {

					case "si":
						flag = false;
						cerrar = true;
						break;
					case "no":
						flag = false;
						break;
					default:
						System.out.println("No has introducido una opcion valida.");
				}
			}

		}
		return cerrar;
	}

	/**
	 * autor/es: Jonathan
	 */
	public static void menuConsultar() {

		do {
			limpiarPantalla();

			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Empleados y departamentos", "2.Horas Extra", "3.Coste Salarial", "4.Ir al menu anterior", "0.Salir"));

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
					return;

				case 0:
					boolean cerrar = comprobarSiCerrar();
					if (!cerrar) {
						eleccion = -1;
					} else {
						eleccion = 0;
						continue;
					}
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

			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Todos los Empleado", "2.Empleado por DNI", "3.Empleados en un departamento", "4.Numero de empleados por Dep.", "5.empleados por categorias", "6.Empleado por ID", "7.Ir al menu anterior", "0.Salir", "", "", ""));

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
					buscarEmpleadoID();
					esperarEnter();
					break;
				case 7:
					return;

				case 0:
					boolean cerrar = comprobarSiCerrar();
					if (!cerrar) {
						eleccion = -1;
					} else {
						eleccion = 0;
						continue;
					}
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

			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Horas Extra por ID", "2.Horas Extra por DNI", "3.Ir al menu anterior", "0.Salir", ""));

			switch (eleccion) {
				case 1:
					consultarHorasExtraID();
					esperarEnter();
					break;
				case 2:
					consultarHorasExtraDNI();
					esperarEnter();
					break;
				case 3:
					return;

				case 0:
					boolean cerrar = comprobarSiCerrar();
					if (!cerrar) {
						eleccion = -1;
					} else {
						eleccion = 0;
						continue;
					}
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
			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Coste por Grupo Cotizacion", "2.Coste Salarial Departamento", "3.Ir al menu anterior", "0.Salir", "", ""));

			switch (eleccion) {
				case 1:
					consultarCosteSalarialGrupoCot();
					esperarEnter();
					break;
				case 2:
					consultarCosteSalarialDepartamento();
					esperarEnter();

					break;
				case 3:
					return;

				case 0:
					boolean cerrar = comprobarSiCerrar();
					if (!cerrar){
						eleccion = -1;
					}else{
						eleccion = 0;
						continue;
					}
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
			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Nuevos Empleados", "2.Nuevos Departamentos", "3.Ir al menu anterior", "0.Salir", ""));

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
					return;
				case 0:
					boolean cerrar = comprobarSiCerrar();
					if (!cerrar){
						eleccion = -1;
					}else{
						eleccion = 0;
						continue;
					}
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
			eleccion = leerEntero(String.format(lineaSup + "\n| %-30s | %-30s | %-30s | %-30s | %-30s |\n" + lineaInf, "1.Modificar Datos Empleado", "2.Eliminar Datos Departamentos", "3.Eliminar Datos Empleado", "4.Ir al menu anterior", "0.Salir"));

			switch (eleccion) {
				case 1:
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
					return;
				case 0:
					boolean cerrar = comprobarSiCerrar();
					if (!cerrar){
						eleccion = -1;
					}else{
						eleccion = 0;
						continue;
					}
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
