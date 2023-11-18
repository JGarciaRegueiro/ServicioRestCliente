package com.grupo6.serviciosRestClient.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grupo6.serviciosRestClient.modelo.Libro;

@Service
public class ServicioProxyLibro {
	
	public static final String URL = "http://localhost:8080/libros";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Libro consultarLibro(int id) {
		try {
			ResponseEntity<Libro> response = restTemplate.getForEntity(URL + "/" + id, Libro.class);
			return response.getBody();
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	public List<Libro> consultarListado(){
		try {
			ResponseEntity<List<Libro>> response = restTemplate.exchange(URL, HttpMethod.GET,null,new ParameterizedTypeReference<List<Libro>>() {});
		return response.getBody();
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	public Libro altaLibro(Libro libro) {
		try {
			Libro response = restTemplate.postForObject(URL, libro, Libro.class);
			return response;
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	public String borrarLibro(int id) {
		try {
			restTemplate.delete(URL + "/" + id);
			return "libro borrado";
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	public String modificarLibro(Libro libro) {
		try {
			restTemplate.put(URL + "/" + libro.getId(), libro);
			return "libro modificado";
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
}
