package com.todo.service;

import com.todo.data.DbContext;
import com.todo.entity.Priority;
import com.todo.repositroy.PriorityRepository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriorityService implements PriorityRepository {
    private final DbContext dbContext;

    public PriorityService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public boolean create(Priority entity) {
        String query = "insert into priorities(name) values (?)";
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
    public boolean update(int id, Priority entity) {
        String query = "update priorities set name = ?  " + " where id = ?";
        Priority selectedPriority = getById(id);
        if (selectedPriority == null) return false;

        selectedPriority.setName(entity.getName());

        int rowsAffected = 0;
        try (Connection connection = dbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedPriority.getName());
            preparedStatement.setInt(2, selectedPriority.getId());
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from priorities where id = ?";
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
    public List<Priority> get() {
        String query = "select * from priorities";
        List<Priority> priorities = new ArrayList<>();
        try (Connection connection = dbContext.openDbConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Priority priority = new Priority();
                priority.setId(resultSet.getInt(1));
                priority.setName(resultSet.getString(2));
                priorities.add(priority);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return priorities;
    }

    @Override
    public Priority getById(Integer id) {
        String query = "select * from priorities where id = ";
        Priority selectedPriority = new Priority();
        try (Connection connection = dbContext.openDbConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + id);
            while (resultSet.next()) {
                selectedPriority.setId(resultSet.getInt(1));
                selectedPriority.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return selectedPriority;
    }

    @Override
    public Priority findPriorityByName(String name) {
        List<Priority> priorities = get();
        Optional<Priority> priorityOptional = priorities.stream().filter(priority -> priority.getName().equals(name)).findFirst();
        return priorityOptional.orElse(null);
    }
}
