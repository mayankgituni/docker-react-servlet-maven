package com.jsp.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/sample")
public class sample extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("username", request.getParameter("username"));
        request.setAttribute("password", request.getParameter("password"));
        request.setAttribute("url", System.getenv("DATABASE_URL"));

        System.out.println("DB_URL: " + System.getenv("DATABASE_URL"));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("welcome.jsp");

        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        System.out.println("DB_URL: " + System.getenv("DATABASE_URL"));

        JDBCAdaptor jdbcAdaptor = JDBCAdaptor.getInstance();

        jdbcAdaptor.checkConnection();

        String tableCommand = "CREATE TABLE IF NOT EXISTS Student ";
        String tableAttributes = "("
                + " ID SERIAL,"
                + " NAME varchar(100) NOT NULL,"
                + " SUBJECT varchar(100) NOT NULL,"
                + " PRIMARY KEY (ID)"
                + ")";

        jdbcAdaptor.createTable(tableCommand, tableAttributes, false);

        String insertVal1 = "INSERT INTO Student (NAME, SUBJECT)" +
                "VALUES('Mayank Tomar', 'Software Design and Architecture')";

        jdbcAdaptor.updateTable(insertVal1);
        PrintWriter res = response.getWriter();

        try {
            List<List<String>> data = jdbcAdaptor.executeQuery("SELECT * FROM Student");

            res.println("<h1>Student Table</h1>");
            for(List<String> row: data) {
                String rowData = "";
                for (String cell: row) {
                    rowData += cell + " | ";
                }
                res.println("<p>" + rowData + "</p>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res.println("<p> SQL Error. </p>");
        }
    }
}
