package com.tvseries.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageFetchServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        String data_root = getServletContext().getInitParameter("data_root");
        String requested_image = req.getPathInfo();
        String servlet_path = req.getServletPath();
        String base_path = data_root + servlet_path + "\\";
        base_path = base_path.replace("/", "\\");

        String image_path = base_path + requested_image;
        if(requested_image == null)
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        File image = new File(image_path);

        if(!image.exists())
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        String content_type = getServletContext().getMimeType(image.getName());

        if(content_type == null || !content_type.startsWith("image"))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        res.reset();
        res.setContentType(content_type);
        res.setHeader("Content-Length", String.valueOf(image.length()));

        Files.copy(image.toPath(), res.getOutputStream());
    }
}
