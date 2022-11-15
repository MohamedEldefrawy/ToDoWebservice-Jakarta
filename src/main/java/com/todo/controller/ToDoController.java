package com.todo.controller;

import com.todo.entity.ToDo;
import com.todo.entity.dto.response.FaildResponse;
import com.todo.entity.dto.response.FaildToDeleteResponse;
import com.todo.entity.dto.response.FaildToUpdateResponse;
import com.todo.entity.dto.response.NotFoundResponse;
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

    @POST
    public Response create(ToDo todo) {
        boolean result = this.toDoService.create(todo);
        if (!result) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("Couldn't create todo item");
            return Response.status(400).entity(notFoundResponse).build();
        }
        return Response.status(201).entity(todo).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") int todoId, ToDo updatedItem) {
        boolean result = this.toDoService.update(todoId, updatedItem);
        if (!result) {
            FaildResponse faildResponse = new FaildToUpdateResponse();
            faildResponse.setMessage("Couldn't update selected todo item with id: " + todoId);
            return Response.status(400).entity(faildResponse).build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        boolean result = this.toDoService.delete(id);
        if (!result) {
            FaildResponse faildResponse = new FaildToDeleteResponse();
            faildResponse.setMessage("Couldn't delete todo item with id: " + id);
            return Response.status(400).entity(faildResponse).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("{title}")
    public Response findByTitle(@PathParam("title") String title) {
        ToDo selectedItem = this.toDoService.findByTitle(title);
        if (selectedItem == null) {
            FaildResponse faildResponse = new FaildToDeleteResponse();
            faildResponse.setMessage("Couldn't find todo item with title: " + title);
            return Response.status(404).entity(faildResponse).build();
        }
        return Response.ok(selectedItem).build();
    }

    @GET
    @Path("/date")
    public Response findByDate(@QueryParam("mode") Integer mode, @QueryParam("date") String date) {
        if (mode == null)
            mode = 0;
        List<ToDo> selectedItems = this.toDoService.findByDate(mode, date);
        if (selectedItems.size() == 0) {
            FaildResponse faildResponse = new FaildToDeleteResponse();
            faildResponse.setMessage("Couldn't find todo item with date: " + date);
            return Response.status(404).entity(faildResponse).build();
        }
        return Response.ok(selectedItems).build();
    }
}