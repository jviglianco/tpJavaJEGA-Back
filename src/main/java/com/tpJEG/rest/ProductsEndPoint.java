package com.tpJEG.rest;

import com.tpJEG.model.Product;
import com.tpJEG.repository.ProductRepository;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SwaggerDefinition(
        info = @Info(
                title = "ProductStore APIs",
                description = "Product APIs TP Java por los pibes.",
                version = "V1.0.0",
                contact = @Contact(
                        name = "JEG",
                        email = "JEG@gmail.com",
                        url = "vigliancoj@gmail.com"
                )
        ),
        host = "localhost:8080",
        basePath = "/productstore-back-1.0-SNAPSHOT/api",
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Product", description = "Tag usado para definir operaciones")
        }
)
@Path("/productos")
@Api("Product")
public class ProductsEndPoint {

    @Inject
    private ProductRepository productoRepository;

    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve un unico Producto", response = Product.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Libro Encontrado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 404, message = "Libro no encontrado.")
    })
    public Response getProducto(@PathParam("id") Long idProducto) {
        Product product = productoRepository.find(idProducto);

        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(product).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Devuelve todos los productos", response = Product.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Productos encontrados"),
            @ApiResponse(code = 204, message = "No se encontro el producto"),
    })
    public Response getProductos() {
        List<Product> productos = productoRepository.findAll();

        if (productos.size() == 0)
            return Response.noContent().build();

        return Response.ok(productos).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Devuelve el total de la cantidad de productos", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Total de productos encontrados"),
            @ApiResponse(code = 204, message = "No existen libros"),
    })
    public Response countProductos() {
        Long nbOfProducts = productoRepository.countAll();

        if (nbOfProducts == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfProducts).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Crea un producto en formato JSON")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Se creo el producto."),
            @ApiResponse(code = 415, message = "No es formato JSON.")
    })
    public Response createProducto(@ApiParam(value = "El producto fue creado", required = true) Product producto, @Context UriInfo uriInfo) {
        try {
            producto =  productoRepository.create(producto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        URI createdURI = uriInfo.getBaseUriBuilder().path(producto.getIdProducto().toString()).build();
        return Response.created(createdURI).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Elimina un producto a partir del id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Producto eliminado"),
            @ApiResponse(code = 400, message = "Ingreso invalido. Debe poner un id mayor a 0."),
            @ApiResponse(code = 500, message = "Producto no encontrado")
    })
    public Response deleteProducto(@PathParam("id") Long idProducto) {
        try {
            productoRepository.delete(idProducto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @PUT
    @ApiOperation("Actualiza un producto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Producto actualizado"),
            @ApiResponse(code = 500, message = "Producto no encontrado")
    })
    public Response updateProducto(@ApiParam(value = "El producto fue creado", required = true) Product producto) {
        try {
            productoRepository.update(producto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }


}
