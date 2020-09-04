package com.jsp.servlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Postgres JDBC Adaptor class.
 */
public class JDBCAdaptor {
    private static JDBCAdaptor jdbcAdaptor = null;
    private final String connectionUrl = System.getenv("DATABASE_URL");

    /**
     * Get the instance of the JDBC adaptor.
     * @return the jdbcAdaptor object.
     */
    public static JDBCAdaptor getInstance() {
        if(jdbcAdaptor == null) {
            jdbcAdaptor = new JDBCAdaptor();
        }

        return jdbcAdaptor;
    }

    /**
     * Check the JDBC Connection.
     */
    public void checkConnection() {
        // Get the connection
        try (Connection connection = DriverManager
                .getConnection(connectionUrl)
        ) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s",
                    e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the JDBC Connection.
     */
    public void createTable(String tableCommand, String attributes, boolean dropTable) {
        // Get the connection
        try (Connection connection = DriverManager
                .getConnection(connectionUrl);
             Statement statement = connection.createStatement()
        ) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

            if (dropTable) {
                String tableName = tableCommand.split(" ")
                        [tableCommand.split(" ").length-1];
                statement.executeUpdate("DROP TABLE " + tableName);
                System.out.println("DROP: " + tableName);
            }

            statement.executeUpdate(tableCommand + attributes);
            System.out.println("Table Created!!");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s",
                    e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the JDBC Connection.
     */
    public void updateTable(String query) {
        // Get the connection
        try (Connection connection = DriverManager
                .getConnection(connectionUrl);
             Statement statement = connection.createStatement()
        ) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

            statement.executeUpdate(query);
            System.out.println("Table Updated!!");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s",
                    e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute the sql query commands in the postgres.
     *
     * @param query is the SQL Select statement.
     * @throws SQLException Sql failure.
     */
    public List<List<String>> executeQuery(String query) throws SQLException {
        List<List<String>> resultList = new ArrayList<>();

        try (Connection connection = DriverManager
                .getConnection(connectionUrl);
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            List<String> header = new ArrayList<>();

            // Header
            for (int i = 1; i <= columnsNumber; i++) {
                header.add(rsmd.getColumnName(i));
            }

            // Add the header
            resultList.add(header);

            // Parsing the returned result
            while (resultSet.next()) {
                List<String> rowList = new ArrayList<>();

                for (int i = 1; i <= columnsNumber; i++) {
                    if (resultSet.getString(i) != null) {
                        rowList.add(resultSet.getString(i));
                    } else {
                        rowList.add("null");
                    }
                }
                System.out.println(rowList);
                resultList.add(rowList);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s",
                    e.getSQLState(), e.getMessage());
        }

        return resultList;
    }
}
