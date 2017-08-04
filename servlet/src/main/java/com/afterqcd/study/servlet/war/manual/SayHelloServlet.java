package com.afterqcd.study.servlet.war.manual;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by afterqcd on 16/6/5.
 */
public class SayHelloServlet extends HttpServlet {
    private String message;
    private String who;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.message = config.getInitParameter("message");
        this.who = config.getInitParameter("who");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();
        writer.println("<h1>" + this.message + " " + this.who + "</h1>");
        writer.close();
    }
}
