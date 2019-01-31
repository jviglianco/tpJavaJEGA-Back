package com.tpJEG.rest;

import com.tpJEG.model.Pedido;
import com.tpJEG.repository.PedidoRepository;
import io.swagger.annotations.*;

import javax.annotation.security.PermitAll;
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

@Path("/pedidos")
@Api("Product")
public class PedidosEndPoint {

    @Inject
    private PedidoRepository pedidoRepository;

    @RolesAllowed("ADMIN")
    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve un unico Pedido", response = Pedido.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido Encontrado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Pedido no encontrado.")
    })
    public Response getPedido(@PathParam("id") Long idPedido) {
        Pedido product = pedidoRepository.find(idPedido);

        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(product).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/usuario/{idUsuario : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve un unico Pedido por Usuario", response = Pedido.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido Encontrado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Pedido no encontrado.")
    })
    public Response getPedidoByUsuario(@PathParam("idUsuario") Long idUsuario) {
        Pedido pedido = pedidoRepository.findPedidoByUsuario(idUsuario);

        if (pedido == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(pedido).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve todos los pedidos", response = Pedido.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedidos encontrados"),
            @ApiResponse(code = 204, message = "No se encontro el pedido"),
    })
    public Response getPedidos() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        if (pedidos.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(pedidos).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Devuelve el total de la cantidad de pedidos", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Total de pedidos encontrados"),
            @ApiResponse(code = 204, message = "No existen pedidos"),
    })
    public Response countPedidos() {
        Long nbOfPedidos = pedidoRepository.countAll();

        if (nbOfPedidos == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfPedidos).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/count/usuario/{idUsuario : \\d+}")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Devuelve el total de la cantidad de pedidos por usuario", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Total de pedidos por usuario encontrados"),
            @ApiResponse(code = 204, message = "No existen pedidos para el usuario"),
    })
    public Response countPedidosByUsuario(@PathParam("idUsuario") Long idUsuario) {
        Long nbOfPedidos = pedidoRepository.countPedidosByUsuario(idUsuario);

        if (nbOfPedidos == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfPedidos).build();
    }
    @RolesAllowed("ADMIN")
    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Crea un pedido en formato JSON")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Se creo el pedido."),
            @ApiResponse(code = 415, message = "No es formato JSON.")
    })
    public Response createPedido(@ApiParam(value = "El pedido fue creado", required = true) Pedido pedido, @Context UriInfo uriInfo) {
        try {
            pedido =  pedidoRepository.create(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Aca rompe, falta validar que no vuelva nulo cuando existe un pedido activo
        URI createdURI = uriInfo.getBaseUriBuilder().path(pedido.getIdPedido().toString()).build();
        return Response.created(createdURI).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Elimina un pedido a partir del id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido eliminado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 500, message = "Pedido no encontrado")
    })
    public Response deletePedido(@PathParam("id") Long idPedido) {
        try {
            pedidoRepository.delete(idPedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @ApiOperation("Actualiza un pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido actualizado"),
            @ApiResponse(code = 500, message = "Pedido no encontrado")
    })
    public Response updatePedido(@ApiParam(value = "El pedido fue creado", required = true) Pedido pedido) {
        try {
            pedidoRepository.update(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}
