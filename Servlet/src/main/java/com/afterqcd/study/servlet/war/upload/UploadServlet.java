package com.afterqcd.study.servlet.war.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by afterqcd on 16/6/6.
 */
public class UploadServlet extends HttpServlet {
    private String path;

    @Override
    public void init() throws ServletException {
        this.path = getServletContext().getInitParameter("fileUpload");
        System.out.println("Path " + this.path);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean isMultiContext = ServletFileUpload.isMultipartContent(req);
        if (!isMultiContext) {
            resp.sendError(404, "Upload form must be type of multipart/form-data");
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(new File("/tmp"));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(50 * 1024);

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    String fileName = fileItem.getName();
                    if (fileName.lastIndexOf("/") >= 0) {
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                    }
                    fileItem.write(new File("/tmp/" + fileName));
                }
            }

            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("Succeed to upload file");
            writer.close();
        } catch (Exception e) {
            throw new IOException("Failed to upload file", e);
        }
    }
}
