package com.todo.service;

import com.todo.data.DbContext;
import com.todo.entity.Priority;
import com.todo.repositroy.PriorityRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriorityService implements PriorityRepository {
    @Override
    public boolean create(Priority entity) {
        return false;
    }

    @Override
    public boolean update(Priority entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public List<Priority> get() {
        String query = "select * from priorities";
        List<Priority> priorities = new ArrayList<>();
        try (Connection connection = DbContext.openDbConnection()) {
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
        try (Connection connection = DbContext.openDbConnection()) {
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
