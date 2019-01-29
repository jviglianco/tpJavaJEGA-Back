package com.tpJEG.rest;

import com.tpJEG.model.Categoria;
import com.tpJEG.repository.CategoriaRepository;
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

@Path("/categorias")
@Api("Product")
public class CategoriasEndPoint {
    @Inject
    private CategoriaRepository categoriaRepository;

    @RolesAllowed("ADMIN")
    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve una unica categoria", response = Categoria.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria Encontrada"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Categoria no encontrada.")
    })
    public Response getCategoria(@PathParam("id") Long idCategoria) {
        Categoria categoria = categoriaRepository.find(idCategoria);

        if (categoria == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(categoria).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve todas las categorias", response = Categoria.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categorias encontradas"),
            @ApiResponse(code = 204, message = "No se encontro la categoria"),
    })
    public Response getCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(categorias).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Devuelve el total de la cantidad de categorias", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Total de categorias encontradas"),
            @ApiResponse(code = 204, message = "No existen categorias"),
    })
    public Response countCategorias() {
        Long nbOfCategorias = categoriaRepository.countAll();

        if (nbOfCategorias == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfCategorias).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Crea una categoria en formato JSON")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Se creo la categoria."),
            @ApiResponse(code = 415, message = "No es formato JSON.")
    })
    public Response createCategoria(@ApiParam(value = "La categoria fue creada", required = true) Categoria categoria, @Context UriInfo uriInfo) {
        try {
            categoria =  categoriaRepository.create(categoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        URI createdURI = uriInfo.getBaseUriBuilder().path(categoria.getIdCategoria().toString()).build();
        return Response.created(createdURI).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Elimina una categoria a partir del id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Categoria eliminada"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 500, message = "Categoria no encontrada")
    })
    public Response deleteCategoria(@PathParam("id") Long idCategoria) {
        try {
            categoriaRepository.delete(idCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @ApiOperation("Actualiza una categoria")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Categoria actualizada"),
            @ApiResponse(code = 500, message = "Categoria no encontrada")
    })
    public Response updateCategoria(@ApiParam(value = "La categoria fue creada", required = true) Categoria categoria) {
        try {
            categoriaRepository.update(categoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}
