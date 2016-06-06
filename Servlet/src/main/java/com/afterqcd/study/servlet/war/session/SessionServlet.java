package com.afterqcd.study.servlet.war.session;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by afterqcd on 16/6/6.
 */
public class SessionServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameterMap().containsKey("invalidate")) {
            session.invalidate();

            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("Invalidate session " + session.getId());
            writer.close();
        } else {
            AtomicInteger visits = (AtomicInteger) session.getAttribute("visits");
            if (visits == null) {
                visits = new AtomicInteger(0);
                session.setAttribute("visits", visits);
            }
            int visitCount = visits.addAndGet(1);

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", session.getId());
            result.put("visits", visitCount);

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.println(gson.toJson(result));
            writer.close();
        }
    }
}
