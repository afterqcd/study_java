package com.afterqcd.study.servlet.war.form;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by afterqcd on 16/6/5.
 */
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("firstName", req.getParameter("first_name"));
        result.put("lastName", req.getParameter("last_name"));

        PrintWriter writer = resp.getWriter();
        writer.println(gson.toJson(result));
        writer.close();
    }
}
