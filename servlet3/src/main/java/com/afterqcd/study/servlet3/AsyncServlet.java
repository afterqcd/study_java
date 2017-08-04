package com.afterqcd.study.servlet3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by afterqcd on 16/6/22.
 */

@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write("进入Servlet的时间 " + new Date() + "\n");
        writer.flush();

        final AsyncContext asyncContext = req.startAsync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    PrintWriter innerWriter = asyncContext.getResponse().getWriter();
                    innerWriter.write("业务处理结束的时间 " + new Date() + "\n");
                    innerWriter.flush();
                    asyncContext.complete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        writer.write("退出Servlet的时间 " + new Date() + "\n");
    }
}
