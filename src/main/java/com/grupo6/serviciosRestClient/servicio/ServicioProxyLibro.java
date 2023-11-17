package com.grupo6.serviciosRestClient.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grupo6.serviciosRestClient.modelo.Libro;

@Service
public class ServicioProxyLibro {
	
	public static final String URL = "http://localhost:8080/libros/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Libro consultarLibro(int id) {
		try {
			ResponseEntity<Libro> response = restTemplate.getForEntity(URL + "/consultar/" + id, Libro.class);
			return response.getBody();
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	public String borrarLibro(int id) {
		try {
			restTemplate.delete(URL + "/baja/" + id);
			return "libro borrado";
		} catch (Exception e) {
			System.out.println("error");
			return null;
		}
	}
	
	
	
}
