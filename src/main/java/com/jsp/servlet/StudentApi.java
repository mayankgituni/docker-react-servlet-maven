package com.jsp.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/students")
public class StudentApi extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        JDBCAdaptor jdbcAdaptor = JDBCAdaptor.getInstance();
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

        ServletOutputStream out = response.getOutputStream();

        try {
            List<List<String>> data = jdbcAdaptor.executeQuery("SELECT * FROM Student");
            List<Student> students = new ArrayList<>();

            for(int i = 1; i < data.size(); i++) {
                List<String> s = data.get(i);
                students.add(new Student(s.get(0), s.get(1), s.get(2)));
            }

            JsonConverter coverter = new JsonConverter();

            String output = coverter.convertToJson(students);
            out.print(output);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
