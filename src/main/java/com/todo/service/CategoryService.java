package com.todo.service;

import com.todo.data.DbContext;
import com.todo.entity.Category;
import com.todo.repositroy.CategoryRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryService implements CategoryRepository {
    @Override
    public boolean create(Category entity) {
        return false;
    }

    @Override
    public boolean update(Category entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public List<Category> get() {
        String query = "select * from categories";
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DbContext.openDbConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }

    @Override
    public Category getById(Integer id) {
        String query = "select * from categories where id = ";
        Category selectedCategory = new Category();
        try (Connection connection = DbContext.openDbConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + id);
            while (resultSet.next()) {
                selectedCategory.setId(resultSet.getInt(1));
                selectedCategory.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return selectedCategory;
    }

    @Override
    public Category findCategoryByName(String name) {
        List<Category> categories = get();
        Optional<Category> categoryOptional = categories.stream().filter(category -> category.getName().equals(name.toUpperCase())).findFirst();
        return categoryOptional.orElse(null);
    }
}
