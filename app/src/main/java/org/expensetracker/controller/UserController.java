package org.expensetracker.controller;

import java.io.PrintWriter;
import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.expensetracker.db.MySQLConnection;
import org.expensetracker.model.User;
import org.expensetracker.repository.UserRepository;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static MySQLConnection msc = new MySQLConnection("localhost:3307", "root", System.getenv("DB_SQL_PASSWORD"), "expense_tracker");
    private static Gson jsonConverter = new GsonBuilder().create();
    private static UserRepository userRepository = new UserRepository(msc);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");

        try (PrintWriter pw = response.getWriter()) {
            pw.println(jsonConverter.toJson(userRepository.findAll(new User())));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
