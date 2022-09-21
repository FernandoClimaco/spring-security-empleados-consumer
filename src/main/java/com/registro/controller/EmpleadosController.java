package com.registro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.registro.core.facade.EmpleadosFacade;
import com.registro.entity.Empleados;
import com.registro.response.Response; 

@RestController
@RequestMapping("/empleados")
public class EmpleadosController {
	
	@Autowired
	private EmpleadosFacade empleadosFacade;
	
	

  	@PostMapping("/guardarEmpleados")
	public ResponseEntity<Response> guardarEmpleados(@RequestBody Empleados empleados){
		ResponseEntity<Response> response = null;
		
		try {
			response = new ResponseEntity<>( empleadosFacade.guardarEmpleados(empleados), HttpStatus.OK);
		}
		catch(Exception e) {
			e.getMessage().toString();
		} 
		return response; 
	}
	
	
	
 	

}
