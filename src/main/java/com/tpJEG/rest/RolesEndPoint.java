package com.tpJEG.rest;

import com.tpJEG.model.Rol;
import com.tpJEG.repository.RolRepository;
import io.swagger.annotations.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/roles")
@Api("Product")
public class RolesEndPoint {
    @Inject
    private RolRepository rolRepository;

    @RolesAllowed("ADMIN")
    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve un unico rol", response = Rol.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Rol Encontrado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Rol no encontrado.")
    })
    public Response getRol(@PathParam("id") Long idRol) {
        Rol rol = rolRepository.find(idRol);

        if (rol == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(rol).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve todos los roles", response = Rol.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Roles encontrados"),
            @ApiResponse(code = 204, message = "No se encontro el rol"),
    })
    public Response getRoles() {
        List<Rol> roles = rolRepository.findAll();

        if (roles.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(roles).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Devuelve el total de la cantidad de roles", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Total de roles encontrados"),
            @ApiResponse(code = 204, message = "No existen roles"),
    })
    public Response countRoles() {
        Long nbOfRoles = rolRepository.countAll();

        if (nbOfRoles == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfRoles).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Crea un rol en formato JSON")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Se creo el rol."),
            @ApiResponse(code = 415, message = "No es formato JSON.")
    })
    public Response createRol(@ApiParam(value = "El rol fue creado", required = true) Rol rol, @Context UriInfo uriInfo) {
        try {
            rol =  rolRepository.create(rol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        URI createdURI = uriInfo.getBaseUriBuilder().path(rol.getIdRol().toString()).build();
        return Response.created(createdURI).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Elimina un rol a partir del id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Rol eliminado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 500, message = "Rol no encontrado")
    })
    public Response deleteRol(@PathParam("id") Long idRol) {
        try {
            rolRepository.delete(idRol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @ApiOperation("Actualiza un rol")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Rol actualizado"),
            @ApiResponse(code = 500, message = "Rol no encontrado")
    })
    public Response updateRol(@ApiParam(value = "El rol fue creado", required = true) Rol rol) {
        try {
            rolRepository.update(rol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}
