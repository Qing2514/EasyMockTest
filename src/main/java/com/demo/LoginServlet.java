package com.demo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private static final int MAX_LENGTH = 10;
    private static final int MIN_LENGTH = 3;
    private static final String USERNAME_TYPE = "usernmae";
    private static final String PASSWORD_TYPE = "password";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        checkString(username, USERNAME_TYPE);
        checkString(password, PASSWORD_TYPE);

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getNamedDispatcher("dispatcher");
            dispatcher.forward(req, res);
            System.out.println("login success.");
        } else {
            throw new RuntimeException("login failed.");
        }

    }

    private void checkString(String s, String type) {
        if (s == null) {
            System.out.println(type + " is null");
            return;
        }
        if (s.length() < MIN_LENGTH || s.length() > MAX_LENGTH) {
            System.out.println(type + " length error");
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)) && !Character.isAlphabetic(s.charAt(i)) && s.charAt(i) != '_') {
                System.out.println(type + " type error");
                return;
            }
        }
    }
}