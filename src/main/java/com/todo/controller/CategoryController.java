package com.todo.controller;

import com.todo.entity.Category;
import com.todo.service.CategoryService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categories")
public class CategoryController {
    CategoryService categoryService = new CategoryService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectAll() {
        List<Category> categoryList = this.categoryService.get();
        if (categoryList.size() == 0)
            return Response.status(404, "Couldn't find categories").build();
        return Response.ok(categoryList).build();
    }
}
