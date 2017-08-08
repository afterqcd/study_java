package com.afterqcd.study.servlet.war.cookie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by afterqcd on 16/6/6.
 */
public class AddCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cookie a = new Cookie("A", "a");
        a.setMaxAge(3600);
        Cookie b = new Cookie("B", "b");
        b.setMaxAge(3600);

        resp.addCookie(a);
        resp.addCookie(b);

        resp.sendRedirect("/showCookie");
    }
}
