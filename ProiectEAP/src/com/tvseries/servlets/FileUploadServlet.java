package com.tvseries.servlets;

import com.tvseries.dao.EpisodeInfoDAO;
import com.tvseries.dao.SeasonInfoDAO;
import com.tvseries.dao.SeriesInfoDAO;
import com.tvseries.dao.UserInfoDAO;
import com.tvseries.utils.RandomString;
import javafx.util.Pair;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.validator.GenericValidator;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class FileUploadServlet extends HttpServlet
{

    private int max_file_size;
    private int max_mem_size;
    private ServletContext context;
    private String temp_path;

    private String profile_pictures_path;
    private String profile_pictures_relative;

    private String posters_path;
    private String posters_relative;

    public void init() throws ServletException
    {
        this.max_file_size = 50 * 1024 * 1024;
        this.max_mem_size = 50 * 1024 * 1024;

        this.context = getServletContext();
        this. temp_path = context.getInitParameter("temp_path");

        this.profile_pictures_path = context.getInitParameter("profile_pictures_path");
        this.profile_pictures_relative = context.getInitParameter("profile_pictures_relative");

        this.posters_path = context.getInitParameter("posters_path");
        this.posters_relative = context.getInitParameter("posters_relative");
    }

    public String generateFileName(String orig_name)
    {
        String file_name = "";
        int index = orig_name.lastIndexOf(".");
        String file_extension = index >= 0 ? orig_name.substring(index) : "";
        String hashname = DigestUtils.sha256Hex((orig_name + RandomString.generateString(10)).getBytes());
        file_name = hashname + file_extension;

        return file_name;
    }

    public boolean processProfilePictures(HttpServletRequest req, HttpServletResponse res, List<FileItem> file_items) throws Exception
    {
        File file = null;

        for(FileItem fi : file_items)
        {
            if(!fi.isFormField()) //if fi is an uploaded file
            {
                String file_name = fi.getName();
                String hash_name = generateFileName(file_name);

                HttpSession session = req.getSession();
                String username = (String)session.getAttribute("username");

                if(username != null)
                {
                    //delete previous profile picture
                    String old_relative_path = (String)UserInfoDAO.getUserAttribute(username, "picture_path");

                    if(old_relative_path != null)
                    {
                        old_relative_path = old_relative_path.substring(old_relative_path.lastIndexOf("\\"));
                        File temp_file = new File(this.profile_pictures_path + old_relative_path);

                        if(temp_file.exists())
                        {
                            boolean deleted = temp_file.delete();
                        }
                    }

                    //update database
                    boolean updated =  UserInfoDAO.setUserAttribute(username, "picture_path", profile_pictures_relative + hash_name);

                    if(!updated)
                    {
                        return false;
                    }

                    req.getSession().setAttribute("picture_path", profile_pictures_relative + hash_name);
                }


                file = new File(profile_pictures_path + hash_name);

                fi.write(file);
                res.sendRedirect(req.getContextPath() + "/restricted/profile.jsp");
                return file.exists();
            }
        }

        return false;
    }

    public void processSeries(HttpServletRequest req, HttpServletResponse res, List<FileItem> file_items) throws Exception
    {
        File file;

        List<Pair<String, Object>> attributes = new ArrayList<>();

        for(FileItem fi : file_items)
        {
            if (!fi.isFormField()) //if fi is an uploaded file
            {
                String file_name = fi.getName();
                String hash_name = generateFileName(file_name);

                attributes.add(new Pair<>("series_poster",  posters_relative + hash_name));

                file = new File(posters_path + hash_name);

                fi.write(file);

                if(!file.exists())
                {
                    System.out.println("File could not be uploaded!");
                    return;
                }
            }
            else //fi is a field
            {
                String field = fi.getFieldName();
                String field_value = fi.getString();

                if(!GenericValidator.isBlankOrNull(field))
                {
                    Object value = null;

                    switch (field)
                    {
                        case "series_id":
                            if(GenericValidator.isInt(field_value))
                            {
                                value = Integer.parseInt(field_value);
                            }
                            break;

                        case "series_title":
                        case "rating":
                            if(!GenericValidator.isBlankOrNull(field_value))
                            {
                                value = field_value;
                            }
                            break;
                    }
                    attributes.add(new Pair<>(field, value));
                }
            }
        }

        boolean added = SeriesInfoDAO.addSeries(attributes);
    }

    public void processSeason(HttpServletRequest req, HttpServletResponse res, List<FileItem> file_items) throws Exception
    {
        File file;

        List<Pair<String, Object>> attributes = new ArrayList<>();

        for(FileItem fi : file_items)
        {
            if (!fi.isFormField()) //if fi is an uploaded file
            {
                String file_name = fi.getName();
                String hash_name = generateFileName(file_name);

                attributes.add(new Pair<>("season_poster",  posters_relative + hash_name));

                file = new File(posters_path + hash_name);

                fi.write(file);

                if(!file.exists())
                {
                    System.out.println("File could not be uploaded!");
                    return;
                }
            }
            else //fi is a field
            {
                String field = fi.getFieldName();
                String field_value = fi.getString();

                if(!GenericValidator.isBlankOrNull(field))
                {
                    Object value = null;

                    switch (field)
                    {
                        case "season_no":
                        case "prequel_id":
                        case "sequel_id":
                        case "series_id":
                            if(GenericValidator.isInt(field_value))
                            {
                                value = Integer.parseInt(field_value);
                            }
                            break;

                        case "season_title":
                            if(!GenericValidator.isBlankOrNull(field_value))
                            {
                                value = field_value;
                            }
                            break;

                        case "release_date":
                        case "end_date":
                            if(GenericValidator.isDate(field_value, "yyyy-MM-dd", false))
                            {
                                value = LocalDate.parse(field_value, DateTimeFormatter.ISO_LOCAL_DATE);
                            }
                            break;
                    }
                    attributes.add(new Pair<>(field, value));
                }
            }
        }

        boolean added = SeasonInfoDAO.addSeason(attributes);
    }

    public void processEpisode(HttpServletRequest req, HttpServletResponse res, List<FileItem> file_items) throws Exception
    {
        List<Pair<String, Object>> attributes = new ArrayList<>();

        Enumeration<String> parameters =  req.getParameterNames();

        while (parameters.hasMoreElements())
        {
           String field = parameters.nextElement();
           String field_value = req.getParameter(field);

            if(!GenericValidator.isBlankOrNull(field))
            {
                Object value = null;

                switch (field)
                {
                    case "episode_id":
                    case "episode_no":
                    case "duration":
                    case "season_id":
                        if(GenericValidator.isInt(field_value))
                        {
                            value = Integer.parseInt(field_value);
                        }
                        break;

                    case "episode_title":
                        if(!GenericValidator.isBlankOrNull(field))
                        {
                            value = field_value;
                        }
                        break;
                }
                attributes.add(new Pair<>(field, value));
            }
        }

        boolean added = EpisodeInfoDAO.addEpisode(attributes);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        String content_type = req.getContentType();
        String servlet_path = req.getServletPath();

        System.out.println(servlet_path);
        if(content_type.contains("multipart/form-data"))
        {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(this.max_mem_size);
            factory.setRepository(new File(this.temp_path));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(this.max_file_size);

            try
            {
                List<FileItem> file_items = upload.parseRequest(req);

                if (servlet_path.equals("/upload_profile_picture"))
                {
                    boolean uploaded = processProfilePictures(req, res, file_items);

                    if(uploaded)
                    {
                        System.out.println("Uploaded");
                    }
                    else
                    {
                        System.out.println("Not uploaded");
                    }
                }
                else if(servlet_path.equals("/administrative/upload_series"))
                {
                    processSeries(req, res, file_items);
                }
                else if(servlet_path.equals("/administrative/upload_season"))
                {
                    processSeason(req, res, file_items);
                }
                else if(servlet_path.equals("/administrative/upload_episode"))
                {
                    processEpisode(req, res, file_items);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
