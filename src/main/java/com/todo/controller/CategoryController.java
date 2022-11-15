package com.todo.controller;

import com.todo.entity.Category;
import com.todo.entity.dto.FaildResponse;
import com.todo.entity.dto.FaildToDeleteResponse;
import com.todo.entity.dto.FaildToUpdateResponse;
import com.todo.entity.dto.NotFoundResponse;
import com.todo.service.CategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {
    CategoryService categoryService = new CategoryService();

    @GET
    public Response selectAll() {
        List<Category> categoryList = this.categoryService.get();
        if (categoryList.size() == 0) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("No Categories found");
            return Response.status(404).entity(notFoundResponse).build();
        }
        return Response.ok(categoryList).build();
    }

    @GET
    @Path("{id}")
    public Response select(@PathParam("id") int categoryId) {
        Category selectedCategory = this.categoryService.getById(categoryId);

        if (selectedCategory.getId() == null) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("Couldn't find category with id: " + categoryId);
            return Response.status(404).entity(notFoundResponse).build();
        }

        return Response.ok(selectedCategory).build();
    }

    @POST
    public Response create(Category category) {
        boolean result = this.categoryService.create(category);
        if (!result) {
            FaildResponse notFoundResponse = new NotFoundResponse();
            notFoundResponse.setMessage("Couldn't create category");
            return Response.status(400).entity(notFoundResponse).build();
        }
        return Response.status(201).entity(category).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        boolean result = this.categoryService.delete(id);
        if (!result) {
            FaildResponse faildResponse = new FaildToDeleteResponse();
            faildResponse.setMessage("Couldn't delete category with id: " + id);
            return Response.status(400).entity(faildResponse).build();
        }
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") int categoryId, Category updatedCategory) {
        boolean result = this.categoryService.update(categoryId, updatedCategory);
        if (!result) {
            FaildResponse faildResponse = new FaildToUpdateResponse();
            faildResponse.setMessage("Couldn't update selected category with id: " + categoryId);
            return Response.status(400).entity(faildResponse).build();
        }
        return Response.noContent().build();
    }

    @Path("/name")
    @GET
    public Response findByName(@QueryParam("name") String name) {
        Category selectedCategory = this.categoryService.findCategoryByName(name);
        if (selectedCategory == null) {
            FaildResponse faildResponse = new NotFoundResponse();
            faildResponse.setMessage("Couldn't find category with name: " + name);
            return Response.status(404).entity(faildResponse).build();
        }
        return Response.ok(selectedCategory).build();
    }
}
