package com.afterqcd.study.servlet.war.cookie;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by afterqcd on 16/6/6.
 */
public class ShowCookieServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        writer.println(gson.toJson(req.getCookies()));
        writer.close();
    }
}
