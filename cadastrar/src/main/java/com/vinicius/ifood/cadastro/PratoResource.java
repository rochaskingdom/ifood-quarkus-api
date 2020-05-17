package com.vinicius.ifood.cadastro;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {


    @GET
    public List<Prato> listar() {
        return Prato.listAll();
    }

    @POST
    @Transactional
    public Response adicionar(Prato dto) {
        dto.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Prato dto) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty()) {
            throw new NotFoundException();
        }
        Prato prato = pratoOp.get();
        prato.persist();
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void remover(@PathParam("id") Long id) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        pratoOp.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });
    }
}
