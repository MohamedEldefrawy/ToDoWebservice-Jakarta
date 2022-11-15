package com.todo.controller;

import com.todo.entity.Priority;
import com.todo.entity.dto.response.FaildResponse;
import com.todo.entity.dto.response.FaildToDeleteResponse;
import com.todo.entity.dto.response.FaildToUpdateResponse;
import com.todo.entity.dto.response.NotFoundResponse;
import com.todo.service.PriorityService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/priorities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PriorityController {

    private final PriorityService priorityService = new PriorityService();

    @GET
    public Response selectAll() {
        List<Priority> priorityList = this.priorityService.get();
        if (priorityList.size() == 0) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("No Priorities found");
            return Response.status(404).entity(notFoundResponse).build();
        }
        return Response.ok(priorityList).build();
    }

    @GET
    @Path("{id}")
    public Response select(@PathParam("id") int categoryId) {
        Priority selectedPriority = this.priorityService.getById(categoryId);

        if (selectedPriority.getId() == null) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("Couldn't find priority with id: " + categoryId);
            return Response.status(404).entity(notFoundResponse).build();
        }
        return Response.ok(selectedPriority).build();
    }

    @POST
    public Response create(Priority priority) {
        boolean result = this.priorityService.create(priority);
        if (!result) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("Couldn't create priority");
            return Response.status(400).entity(notFoundResponse).build();
        }
        return Response.status(201).entity(priority).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        boolean result = this.priorityService.delete(id);
        if (!result) {
            FaildResponse faildResponse = new FaildToDeleteResponse();
            faildResponse.setMessage("Couldn't delete priority with id: " + id);
            return Response.status(400).entity(faildResponse).build();
        }
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") int priorityId, Priority updatedPriority) {
        boolean result = this.priorityService.update(priorityId, updatedPriority);
        if (!result) {
            FaildResponse faildResponse = new FaildToUpdateResponse();
            faildResponse.setMessage("Couldn't update selected priority with id: " + priorityId);
            return Response.status(400).entity(faildResponse).build();
        }
        return Response.noContent().build();
    }

    @Path("/name")
    @GET
    public Response findByName(@QueryParam("name") String name) {
        Priority selectedPriority = this.priorityService.findPriorityByName(name);
        if (selectedPriority == null) {
            FaildResponse faildResponse = new NotFoundResponse();
            faildResponse.setMessage("Couldn't find priority with name: " + name);
            return Response.status(404).entity(faildResponse).build();
        }
        return Response.ok(selectedPriority).build();
    }
}
