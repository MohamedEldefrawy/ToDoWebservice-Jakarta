package com.todo.controller;

import com.todo.entity.Category;
import com.todo.entity.dto.NotFoundResponse;
import com.todo.service.CategoryService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categories")
public class CategoryController {
    CategoryService categoryService = new CategoryService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selectAll() {
        List<Category> categoryList = this.categoryService.get();
        if (categoryList.size() == 0) {
            NotFoundResponse notFoundResponse = new NotFoundResponse("No Categories found");
            return Response.status(404).entity(notFoundResponse).build();
        }
        return Response.ok(categoryList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response select(@PathParam("id") int categoryId) {
        Category selectedCategory = this.categoryService.getById(categoryId);

        if (selectedCategory.getId() == null) {
            NotFoundResponse notFoundResponse = new NotFoundResponse("Couldn't find category with id: " + categoryId);
            return Response.status(404).entity(notFoundResponse).build();
        }

        return Response.ok(selectedCategory).build();
    }
}
