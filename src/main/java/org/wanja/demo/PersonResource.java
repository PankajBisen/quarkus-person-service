package org.wanja.demo;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/person/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    public List<Person> getAll() throws Exception {
        return Person.findAll().list();
    }

    @POST
    @Transactional
    public Response create(Person p) {
        if (p == null || p.id != null)
            throw new WebApplicationException("id != null");
        p.persist();
        return Response.ok(p).status(200).build();
    }


    @Transactional
    @Path("{id}")
    @PUT
    public Person update(@PathParam("id") Long id, Person p) {
        Person entity = Person.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        if(p.getSalutation() != null ){
            entity.setSalutation(p.getSalutation());
            }
        if(p.getFirstName() != null ){
            entity.setFirstName(p.getFirstName());
        }
        if(p.getLastName() != null) {
            entity.setLastName(p.getLastName()) ;
        }
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Person entity = Person.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }
}
