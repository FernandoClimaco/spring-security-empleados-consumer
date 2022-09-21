package com.registro.core.facade.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.registro.core.facade.EmpleadosFacade;
import com.registro.core.mapper.EmpleadosMapper;
import com.registro.entity.Empleados;
import com.registro.response.Response;


@Component
public class EmpleadosFacadeImpl implements EmpleadosFacade {
	
	@Autowired
	private EmpleadosMapper empleadosMapper;
	

	
	@Override
	public Response guardarEmpleados(Empleados empleados) {
        Response response = new Response();
		
		try {
			
			response = empleadosMapper.guardarEmpleados(empleados);
			
			if(response!=null) {
				response.setIdTransaccion(UUID.randomUUID().toString());
				response.setMensaje("Datos de empleados guardados");
				System.out.println(response);
			}else {
				response.setIdTransaccion(UUID.randomUUID().toString());
				response.setMensaje("Datos no guardados de empleados");
				System.out.println(response); 
			} 
		}catch(HttpStatusCodeException e) {
			
			if(e.getRawStatusCode()==400) {
				response.setIdTransaccion(UUID.randomUUID().toString());
				response.setMensaje("bad request");
				System.out.println(response);
			}
			if(e.getRawStatusCode()==404) {
				response.setIdTransaccion(UUID.randomUUID().toString());
				response.setMensaje("not found");
				System.out.println(response);
			}
			if(e.getRawStatusCode()==500) {
				response.setIdTransaccion(UUID.randomUUID().toString());
				response.setMensaje("Error interno");
				System.out.println(response);
			} 
		}catch(Exception e) {
			System.out.println(e.getMessage().toString());
		} 
 		return response;
	}

	
	

}
