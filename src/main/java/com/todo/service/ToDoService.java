package com.todo.service;

import com.todo.data.DbContext;
import com.todo.entity.Category;
import com.todo.entity.Priority;
import com.todo.entity.ToDo;
import com.todo.exception.NoDateAssignedException;
import com.todo.repositroy.ToDoRepository;
import com.todo.utils.Helpers;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class ToDoService implements ToDoRepository {

    private static final int SEARCH_BY_START_DATE = 0;
    private final CategoryService categoryService = new CategoryService();
    private final PriorityService priorityService = new PriorityService();


    @Override
    public ToDo findByTitle(String title) {
        List<ToDo> toDos = get();
        ToDo selectedTodo = null;
        if (toDos != null && toDos.size() > 0) for (ToDo item : toDos) {
            if (item.getTitle().equals(title)) selectedTodo = item;
            break;
        }
        return selectedTodo;
    }

    @Override
    public List<ToDo> findByPriority(String priority) {
        List<ToDo> toDos = get();
        List<ToDo> result = new ArrayList<>();
        Priority selectedPriority = this.priorityService.findPriorityByName(priority);
        if (selectedPriority == null) return null;

        if (toDos != null && toDos.size() > 0) for (ToDo toDo : toDos) {
            if (toDo.getPriority().equals(selectedPriority)) result.add(toDo);
        }
        return result;
    }

    @Override
    public boolean deleteByTitle(String title) {
        ToDo selectedTodo = findByTitle(title);
        if (selectedTodo == null) return false;
        return delete(selectedTodo.getId());
    }

    @Override
    public List<ToDo> findByDate(int mode, String date) {
        List<ToDo> toDos = get();
        List<ToDo> result = new ArrayList<>();
        Date selectedDate;

        try {
            selectedDate = Helpers.covertStringToDate(date);
        } catch (NoDateAssignedException e) {
            return this.get();
        }

        if (mode == SEARCH_BY_START_DATE) {
            if (toDos != null && toDos.size() > 0) for (ToDo toDo : toDos) {
                if (toDo.getStartDate() != null && toDo.getStartDate().equals(selectedDate)) result.add(toDo);
            }
        } else {
            if (toDos != null) for (ToDo toDo : toDos) {
                if (toDo.getEndDate() != null && toDo.getEndDate().equals(selectedDate)) result.add(toDo);
            }
        }

        return result;
    }

    @Override
    public List<ToDo> selectTopFiveNearestByStartDate() {
        List<ToDo> unSortedToDos = get();

        if (unSortedToDos != null && unSortedToDos.size() > 0) {
            {
                Collections.sort(unSortedToDos, new Comparator<ToDo>() {
                    @Override
                    public int compare(ToDo toDo1, ToDo toDo2) {
                        return (toDo1.getStartDate().compareTo(toDo2.getStartDate()));
                    }
                });
            }
            List<ToDo> sortedToDos = new ArrayList<>();
            for (int i = 0; i < unSortedToDos.size(); i++) {
                sortedToDos.add(i, unSortedToDos.get(i));
            }
            return sortedToDos;
        }
        return unSortedToDos;
    }

    @Override
    public boolean addItemToCategory(String title, String category) {

        ToDo toDo = findByTitle(title);
        if (toDo != null) {
            Category updatedCategory = this.categoryService.findCategoryByName(category);
            if (updatedCategory == null) return false;
            toDo.setCategory(updatedCategory);
            return update(toDo.getId(), toDo);
        }
        return false;
    }

    @Override
    public boolean addItemToFavourite(String title) {
        ToDo toDo = findByTitle(title);
        if (toDo != null) {
            toDo.setFavourite(true);
            update(toDo.getId(), toDo);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(ToDo entity) {
        String query = "insert into items(title,description,start_date,end_date,category_id,priority_id) " + "values (?,?,?,?,?,?)";
        int rowsAffected = 0;
        try (Connection connection = DbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setObject(3, entity.getStartDate());
            preparedStatement.setObject(4, entity.getEndDate());
            preparedStatement.setInt(5, entity.getCategory().getId());
            preparedStatement.setInt(6, entity.getPriority().getId());
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean update(int id, ToDo entity) {
        String query = "update items set title = ? , description = ? , start_date = ?,end_date = ?" + ",priority_id=?,category_id=?,isFavourite=? " + " where id = ?";
        ToDo selectedToDo = getById(id);
        if (selectedToDo == null) return false;

        selectedToDo.setTitle(entity.getTitle());
        selectedToDo.setDescription(entity.getDescription());
        selectedToDo.setFavourite(entity.isFavourite());
        selectedToDo.setCategory(entity.getCategory());
        selectedToDo.setPriority(entity.getPriority());
        selectedToDo.setStartDate(entity.getStartDate());
        selectedToDo.setEndDate(entity.getEndDate());

        int rowsAffected = 0;
        try (Connection connection = DbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedToDo.getTitle());
            preparedStatement.setString(2, selectedToDo.getDescription());
            preparedStatement.setObject(3, selectedToDo.getStartDate());
            preparedStatement.setObject(4, selectedToDo.getEndDate());
            preparedStatement.setInt(5, selectedToDo.getCategory().getId());
            preparedStatement.setInt(6, selectedToDo.getPriority().getId());
            preparedStatement.setBoolean(7, selectedToDo.isFavourite());
            preparedStatement.setInt(8, selectedToDo.getId());
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from items where id = ?";
        int rowsAffected = 0;
        try (Connection connection = DbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.addBatch();
            rowsAffected = preparedStatement.executeBatch().length;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected > 0;
    }

    @Override
    public List<ToDo> get() {
        String query = "select * from items";
        List<ToDo> result = new ArrayList<>();
        try (Connection connection = DbContext.openDbConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Priority selectedPriority = this.priorityService.getById(resultSet.getInt(6));
                Category selectedCategory = this.categoryService.getById(resultSet.getInt(7));
                System.out.println(resultSet.getInt(6));
                System.out.println(resultSet.getInt(7));
                ToDo toDo = new ToDo();
                toDo.setId(resultSet.getInt(1));
                toDo.setTitle(resultSet.getString(2));
                toDo.setDescription(resultSet.getString(3));
                toDo.setStartDate(resultSet.getDate(4));
                toDo.setEndDate(resultSet.getDate(5));
                toDo.setPriority(selectedPriority);
                toDo.setCategory(selectedCategory);
                toDo.setFavourite(resultSet.getBoolean(8));
                result.add(toDo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public ToDo getById(Integer id) {
        String query = "select * from items where id = ?";
        ToDo selectedToDo = new ToDo();
        try (Connection connection = DbContext.openDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Priority selectedPriority = this.priorityService.getById(resultSet.getInt(6));
                Category selectedCategory = this.categoryService.getById(resultSet.getInt(7));

                selectedToDo.setId(resultSet.getInt(1));
                selectedToDo.setTitle(resultSet.getString(2));
                selectedToDo.setDescription(resultSet.getString(3));
                selectedToDo.setStartDate(resultSet.getDate(4));
                selectedToDo.setEndDate(resultSet.getDate(5));
                selectedToDo.setPriority(selectedPriority);
                selectedToDo.setCategory(selectedCategory);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (selectedToDo.getTitle() == null)
            return null;
        return selectedToDo;
    }
}
