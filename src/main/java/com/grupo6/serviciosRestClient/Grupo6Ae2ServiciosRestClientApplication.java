 package com.grupo6.serviciosRestClient;

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

@SpringBootApplication
public class Grupo6Ae2ServiciosRestClientApplication implements CommandLineRunner{
	
	@Autowired
	private ServicioProxyLibro spl;
	
	@Autowired
	private ApplicationContext context;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		System.out.println("Cargando contexto de spring...");
		SpringApplication.run(Grupo6Ae2ServiciosRestClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
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

            switch (opcion) {
                case 1:
                	Libro libro = new Libro (0,"titulo1","editorial",0.0);
                	System.out.println(spl.altaLibro(libro));
                    break;
                case 2:
                	System.out.println(spl.borrarLibro(3));
                    break;
                case 3:
                	Libro libro2 = new Libro (0,"titulo1","editorial",0.0);
                	System.out.println(spl.modificarLibro(libro2));
                    break;
                case 4:
                	System.out.println(spl.consultarLibro(1));
                    break;
                case 5:
                	System.out.println(spl.consultarListado());
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
	
	public void pararAplicacion() {
		SpringApplication.exit(context, ()->0);
	}

}
