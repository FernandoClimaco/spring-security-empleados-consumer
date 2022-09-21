package com.registro.core.service.impl;

import java.io.UnsupportedEncodingException; 
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.registro.core.service.EmpleadosServices;
import com.registro.entity.Empleados;
import com.registro.response.Response;
import org.apache.commons.codec.binary.Base64;


@Service
public class EmpleadosServicesImpl implements EmpleadosServices {

	 
	 @Autowired
	RestTemplate restTemplate;
	 
	
	
	@Override
	public Response guardarEmpleados(Empleados empleados) { 
		Response resp = new Response();
		 
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			headers.set("Content-Type", "application/json");
			headers.set("User-Agent", "PostmanRuntime/7.28.0");
			headers.set("Accept", "*/*");
			headers.set("Accept-Encoding", "gzip, deflate, br");
			headers.set("Connection", "keep-alive");
			
			
			String user = "admin";
		    String pass = "password";	
		    
		    headers = basicAuth(user,pass);
			
		    System.out.println(user+ pass);
		    
		    
			// creo objeto entity con body y headers :
			 HttpEntity<Empleados> hea = new HttpEntity<>(empleados ,headers); 
			// consumo:
			ResponseEntity<Response> response = restTemplate.exchange("http://localhost:8080/api/v1/empleados/guardarEmpleados", HttpMethod.POST, hea, Response.class);

			if (response != null) {
				if (response.getBody() != null) {
					System.out.println("consumo exitoso" + response);
					resp = response.getBody();

				} else {
					System.out.println("cuerpo vacio");
				}
			} else {
				System.out.println("No se realizo el consumo");
			}
			
			 
			
		}catch(HttpStatusCodeException e) {
			
			if(e.getRawStatusCode()==400) {
				resp.setIdTransaccion(UUID.randomUUID().toString());
				resp.setMensaje("bad request");
				System.out.println(resp);
			}
			if(e.getRawStatusCode()==404) {
				resp.setIdTransaccion(UUID.randomUUID().toString());
				resp.setMensaje("not found");
				System.out.println(resp);
			}
			if(e.getRawStatusCode()==500) {
				resp.setIdTransaccion(UUID.randomUUID().toString());
				resp.setMensaje("Error interno");
				System.out.println(resp);
			} 
		}catch(Exception e) {
			System.out.println(e.getMessage().toString());
		} 
 		return resp;
	}

	
	
	
	private HttpHeaders basicAuth(String user, String pass) throws UnsupportedEncodingException {
	    String plainCreds = user + ":" + pass;
	    byte[] plainCredsBytes = plainCreds.getBytes("UTF-8");
	    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
	    String base64Creds = new String(base64CredsBytes,"UTF-8");

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Basic " + base64Creds);
	    return headers;
	
	}
	 

}
