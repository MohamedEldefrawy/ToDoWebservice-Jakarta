package com.todo.data;

import com.todo.config.DataBaseConfiguration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DbContext {
    private final DataBaseConfiguration dataBaseConfiguration;

    public DbContext(DataBaseConfiguration dataBaseConfiguration) {
        this.dataBaseConfiguration = dataBaseConfiguration;
    }


    public Connection openDbConnection() {
        Connection con;
        try {
            Class.forName(dataBaseConfiguration.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            con = DriverManager.getConnection(dataBaseConfiguration.CONNECTION_STRING, dataBaseConfiguration.USER_NAME, dataBaseConfiguration.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
