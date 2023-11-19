package com.grupo6.serviciosRestClient.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.grupo6.serviciosRestClient.modelo.Libro;


/**
 * Servicio que actúa como un proxy para interactuar con un servicio web RESTful de libros.
 */

@Service
public class ServicioProxyLibro {
	
	// Definimos la URL donde se encuentra nuestro servicio REST.
	
	public static final String URL = "http://localhost:8080/libros";
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
     * Consulta un libro por su ID.
     *
     * @param id: Identificador del libro.
     * @return El libro correspondiente al ID proporcionado, o null si no se puede encontrar.
     */
	
	public Libro consultarLibro(int id) {
		try {
			ResponseEntity<Libro> response = restTemplate.getForEntity(URL + "/" + id, Libro.class);
			return response.getBody();
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	/**
     * Consulta el listado completo de libros.
     *
     * @return La lista de libros, o null si no se puede realizar la consulta.
     */
	
	public List<Libro> consultarListado(){
		try {
			ResponseEntity<List<Libro>> response = restTemplate.exchange(URL, HttpMethod.GET,null,new ParameterizedTypeReference<List<Libro>>() {});
		return response.getBody();
		} catch (Exception e) {
			System.out.println("Error al realizar la consulta de libros.");
			return null;
		}
	}
	
	/**
     * Da de alta un nuevo libro.
     *
     * @param libro: El libro a dar de alta.
     * @return El libro recién creado, o null si hay algún problema.
     */
	
	public Libro altaLibro(Libro libro) {
		try {
			Libro response = restTemplate.postForObject(URL, libro, Libro.class);
			return response;
		} catch (Exception e) {
			System.out.println("Error al dar de alta el libro.");
			return null;
		}
	}
	
	/**
     * Borra un libro por su ID.
     *
     * @param id:Identificador del libro a borrar.
     * @return Un mensaje indicando el resultado de la operación, o null si hay algún problema.
     */
	
	public String borrarLibro(int id) {
		try {
			restTemplate.delete(URL + "/" + id);
			return "Libro borrado exitosamente.";
		} catch (Exception e) {
			System.out.println("Error al borrar el libro.");
			return null;
		}
	}
	
	 /**
     * Modifica un libro existente.
     *
     * @param libro: El libro modificado.
     * @return Un mensaje indicando el resultado de la operación, o null si hay algún problema.
     */
	
	public String modificarLibro(Libro libro) {
		try {
			restTemplate.put(URL + "/" + libro.getId(), libro);
			return "Libro modificado exitosamente.";
		} catch (Exception e) {
			System.out.println("Error al modificar el libro.");
			return null;
		}
	}
	
}
