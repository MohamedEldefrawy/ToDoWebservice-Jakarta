package com.todo.controller;

import com.todo.entity.ToDo;
import com.todo.entity.dto.FaildResponse;
import com.todo.entity.dto.NotFoundResponse;
import com.todo.service.ToDoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ToDoController {
    private final ToDoService toDoService = new ToDoService();

    @GET
    public Response selectAll() {
        List<ToDo> toDoList = this.toDoService.get();
        if (toDoList.size() == 0) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("No Items found");
            return Response.status(404).entity(notFoundResponse).build();
        }
        return Response.ok(toDoList).build();
    }

    @GET
    @Path("{id}")
    public Response select(@PathParam("id") int id) {
        ToDo selectedItem = this.toDoService.getById(id);

        if (selectedItem.getId() == null) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("Couldn't find todo item with id: " + id);
            return Response.status(404).entity(notFoundResponse).build();
        }

        return Response.ok(selectedItem).build();
    }

}