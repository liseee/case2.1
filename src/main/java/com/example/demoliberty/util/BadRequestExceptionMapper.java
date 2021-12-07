package com.example.demoliberty.util;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    // Als er ergens in mijn JAX-RS-app een BadRequestException optreedt
    // vang ik hem hier af:
    @Override
    public Response toResponse(BadRequestException ex) {
        // dit is als het ware de catch(BadRequestException ex){
        return Response.status(BAD_REQUEST)
                .entity(ex.getMessage())
                .build();
        //}
    }
}
