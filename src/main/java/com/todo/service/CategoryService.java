package com.todo.service;

import com.todo.data.DbContext;
import com.todo.entity.Category;
import com.todo.repositroy.CategoryRepository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryRepository {
    private final DbContext dbContext;

    public CategoryService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public boolean create(Category entity) {
        String query = "insert into categories(name) values (?)";
        int rowsAffected = 0;
        try (Connection connection = dbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean update(int id, Category entity) {
        String query = "update categories set name = ?  " + " where id = ?";
        Category selectedCategory = getById(id);
        if (selectedCategory == null) return false;

        selectedCategory.setName(entity.getName());

        int rowsAffected = 0;
        try (Connection connection = dbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedCategory.getName());
            preparedStatement.setInt(2, selectedCategory.getId());
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from categories where id = ?";
        int rowsAffected = 0;
        try (Connection connection = dbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return rowsAffected > 0;
    }

    @Override
    public List<Category> get() {
        String query = "select * from categories";
        List<Category> categories = new ArrayList<>();
        try (Connection connection = dbContext.openDbConnection()) {
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
            return new ArrayList<>();
        }

        return categories;
    }

    @Override
    public Category getById(Integer id) {
        String query = "select * from categories where id = ";
        Category selectedCategory = new Category();
        try (Connection connection = dbContext.openDbConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + id);
            while (resultSet.next()) {
                selectedCategory.setId(resultSet.getInt(1));
                selectedCategory.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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
