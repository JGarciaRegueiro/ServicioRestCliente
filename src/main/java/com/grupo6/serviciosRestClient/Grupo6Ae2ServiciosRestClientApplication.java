 package com.grupo6.serviciosRestClient;

import java.util.ArrayList;


import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.grupo6.serviciosRestClient.modelo.Libro;
import com.grupo6.serviciosRestClient.servicio.ServicioProxyLibro;


/**
 * Aplicación que interactúa con un servicio web RESTful de libros.
 */

@SpringBootApplication
public class Grupo6Ae2ServiciosRestClientApplication implements CommandLineRunner{
	
	@Autowired
	private ServicioProxyLibro spl;
	
	@Autowired
	private ApplicationContext context;
	
	/**
     * Configuración del bean RestTemplate para realizar solicitudes HTTP.
     *
     * @param builder: Constructor de RestTemplate.
     * @return Instancia de RestTemplate.
     */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
     * Método principal que carga el contexto de Spring y ejecuta la aplicación.
     */
	
	public static void main(String[] args) {
		System.out.println("Cargando contexto de spring...");
		SpringApplication.run(Grupo6Ae2ServiciosRestClientApplication.class, args);
	}
	
	/**
     * Implementación del método CommandLineRunner para manejar la lógica principal de la aplicación.
     *
     * @throws Exception: Excepciones generales.
     */
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
        	// Menú principal
            System.out.println("-----------------------------------------------------------");
            System.out.println("Menú:");
            System.out.println("1. Dar de alta un libro");
            System.out.println("2. Dar de baja un libro");
            System.out.println("3. Modificar un libro");
            System.out.println("4. Consultar libro por id");
            System.out.println("5. Consultar listado libros");
            System.out.println("6. Salir");
            System.out.println("-----------------------------------------------------------");
            System.out.print("Elige una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next();
            }
            
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            String idStr = "Introduzca el ID: " ;
            Libro auxLibro = null;
            
            switch (opcion) {
                case 1:
                	auxLibro = crearLibroScanner(scanner, 0);
                	System.out.println(spl.altaLibro(auxLibro));
                    break;
                case 2:
                	System.out.println(spl.borrarLibro(getIDValido(scanner, idStr)));
                    break;
                case 3:
                	int id = getIDValido(scanner, idStr);
                	auxLibro = spl.consultarLibro(id);
                	if(auxLibro != null) {
                		scanner.nextLine();
                		auxLibro = crearLibroScanner(scanner, auxLibro.getId());
                    	System.out.println(spl.modificarLibro(auxLibro));
                	} else System.out.println("Libro no encontrado el libro con ID " + id);                	
                    break;
                case 4:
                	System.out.println(spl.consultarLibro(getIDValido(scanner, idStr)));
                    break;
                case 5:
                	ArrayList<Libro> list = new ArrayList<>(spl.consultarListado());
                	for (Libro l : list) {
						System.out.println(l.toString());
					}
                	//System.out.println(spl.consultarListado());
                    break;
                case 6:
                    pararAplicacion();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del 1 al 6.");
                    System.out.println("-----------------------------------------------------------");
                    break;
            }
        } while (opcion != 6);

        scanner.close();
	}
	
	/**
     * Detiene la aplicación de Spring.
     */
	
	public void pararAplicacion() {
		SpringApplication.exit(context, ()->0);
	}

	
	/**
     * Obtiene un ID válido desde la entrada del usuario.
     *
     * @param scanner: Instancia de Scanner para entrada de usuario.
     * @param titulo: Título para la solicitud de entrada.
     * @return ID válido.
     */
	
	private int getIDValido(Scanner scanner, String titulo) {
		
		try {
			System.out.print(titulo);
			int id = scanner.nextInt();			
			if(id < 0) {
				System.out.print("Se debe introducir un numero valido");
				scanner.next();
				return getIDValido(scanner, ": ");
			}else return id; 							
		} catch (InputMismatchException ime) {
			System.out.print("Se debe introducir un numero entero");
			scanner.next();
			return getIDValido(scanner, ": ");
		} catch (Exception e) {		
			return 0;
		}
	}
	
	/**
     * Obtiene una nota válida desde la entrada del usuario.
     *
     * @param scanner: Instancia de Scanner para entrada de usuario.
     * @param titulo: Título para la solicitud de entrada.
     * @return Nota válida.
     */
	
	private float getNotaValido(Scanner scanner, String titulo) {
		try {
			System.out.print(titulo);
			float nota = scanner.nextFloat();			
			if(nota < 0 || nota > 10) {
				System.out.print("Se debe introducir un numero entre [0] y [10]");
				scanner.next();
				return getNotaValido(scanner, ": ");
			}else return nota; 							
		} catch (InputMismatchException ime) {
			System.out.print("Se debe introducir un numero valido");
			scanner.next();
			return getNotaValido(scanner, ": ");
		} catch (Exception e) {		
			return 0;
		}
	}
	
	/**
     * Crea un objeto Libro utilizando la entrada del usuario.
     *
     * @param scanner: Instancia de Scanner para entrada de usuario.
     * @param id: ID del libro.
     * @return Objeto Libro creado.
     */
	
	private Libro crearLibroScanner(Scanner scanner, int id) {
		String titulo, editorial;
		float nota;
		
		System.out.print("| Introduce el titulo: " );
		titulo = scanner.nextLine();
		System.out.print("| Introduce la editorial: " );
		editorial = scanner.nextLine();
		System.out.print("| Introduce la nota: " );
		nota = getNotaValido(scanner, "");	
		
		return new Libro(id, titulo, editorial, nota);
	}
}
