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
            System.out.println("Menú:");
            System.out.println("1. Consultar libro por id");
            System.out.println("2. Opción 2");
            System.out.println("3. Opción 3");
            System.out.println("4. Opción 4");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next();
            }
            
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                	System.out.println(spl.consultarLibro(1));
                    System.out.println("Seleccionaste la Opción 1");
                    break;
                case 2:
                	System.out.println(spl.borrarLibro(5));                	
                    System.out.println("Seleccionaste la Opción 2");
                    break;
                case 3:
                    System.out.println("Seleccionaste la Opción 3");
                    break;
                case 4:
                    System.out.println("Seleccionaste la Opción 4");
                    break;
                case 5:
                    pararAplicacion();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del 1 al 5.");
                    break;
            }
        } while (opcion != 5);

        scanner.close();
		
	}
	
	public void pararAplicacion() {
		SpringApplication.exit(context, ()->0);
	}

}
