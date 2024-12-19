package org.expensetracker.controller;

import java.io.PrintWriter;
import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/health-checker")
public class HealthChecker extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");

        try(PrintWriter pw = response.getWriter()) {
            pw.println("I'm alive!");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
