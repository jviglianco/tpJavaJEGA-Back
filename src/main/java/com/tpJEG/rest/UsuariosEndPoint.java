package com.tpJEG.rest;

import com.tpJEG.model.Usuario;
import com.tpJEG.repository.UsuarioRepository;
import io.swagger.annotations.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/usuarios")
@Api("Product")
public class UsuariosEndPoint {
    @Inject
    private UsuarioRepository usuarioRepository;


    @GET
    @PermitAll
    @Path("/authenticate/{username}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve un unico usuario", response = Usuario.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario Encontrado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Usuario no encontrado.")
    })
    public Response authenticate(@PathParam("username") String token) {

        String usernameAndPassword = new String(Base64.getDecoder().decode(token.getBytes()));

        StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();

        Usuario usuario = usuarioRepository.findByUserAndPass(username,password);

        if (usuario == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(usuario).build();
    }

    @GET
    @RolesAllowed("ADMIN")
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve un unico usuario", response = Usuario.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario Encontrado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Usuario no encontrado.")
    })
    public Response getUsuario(@PathParam("id") Long idUsuario) {
        Usuario usuario = usuarioRepository.find(idUsuario);

        if (usuario == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(usuario).build();
    }

    @GET
    @RolesAllowed("ADMIN")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve todos los usuarios", response = Usuario.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuarios encontrados"),
            @ApiResponse(code = 204, message = "No se encontro el usuario"),
    })
    public Response getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(usuarios).build();
    }

    @GET
    @RolesAllowed("ADMIN")
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Devuelve el total de la cantidad de usuarios", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Total de usuarios encontrados"),
            @ApiResponse(code = 204, message = "No existen usuarios"),
    })
    public Response countUsuarios() {
        Long nbOfUsuarios = usuarioRepository.countAll();

        if (nbOfUsuarios == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfUsuarios).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Crea un usuario en formato JSON")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Se creo el usuario."),
            @ApiResponse(code = 415, message = "No es formato JSON.")
    })
    public Response createUsuario(@ApiParam(value = "El usuario fue creado", required = true) Usuario usuario, @Context UriInfo uriInfo) {
        try {
            usuario =  usuarioRepository.create(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        URI createdURI = uriInfo.getBaseUriBuilder().path(usuario.getIdUsuario().toString()).build();
        return Response.created(createdURI).build();
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id : \\d+}")
    @ApiOperation("Elimina un usuario a partir del id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuario eliminado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 500, message = "Usuario no encontrado")
    })
    public Response deleteUsuario(@PathParam("id") Long idUsuario) {
        try {
            usuarioRepository.delete(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @PUT
    @RolesAllowed("ADMIN")
    @ApiOperation("Actualiza un usuario")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuario actualizado"),
            @ApiResponse(code = 500, message = "Usuario no encontrado")
    })
    public Response updateUsuario(@ApiParam(value = "El usuario fue creado", required = true) Usuario usuario) {
        try {
            usuarioRepository.update(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}
