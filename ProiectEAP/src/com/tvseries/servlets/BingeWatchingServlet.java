package com.tvseries.servlets;

import com.tvseries.containers.FriendsInfoContainer;
import com.tvseries.dao.SeasonInfoDAO;
import com.tvseries.dao.UserInfoDAO;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BingeWatchingServlet extends HttpServlet
{
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        String servlet_path = req.getServletPath();

        try
        {
            if (servlet_path.equals("/restricted/binge_watching"))
            {
                processBingeWatching(req, res);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        String servlet_path = req.getServletPath();

        try
        {
            if (servlet_path.equals("/restricted/binge_watching"))
            {
                processFriendsList(req, res);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void processBingeWatching(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String username = (String)req.getSession().getAttribute("username");
        String season_id_str = req.getParameter("season_id");

        if(!GenericValidator.isInt(season_id_str) || !SeasonInfoDAO.existsSeason(Integer.parseInt(season_id_str)) || username == null)
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Integer season_id = Integer.parseInt(season_id_str);

        String[] friends = req.getParameterValues("friend");

        if(friends != null)
        {
            List<String> friends_list = new ArrayList<>(Arrays.asList(friends));

            boolean added = UserInfoDAO.addToBingeWatching(username, friends_list, season_id);
        }

        res.sendRedirect(req.getContextPath() + "/restricted/list?status=completed");
    }

    public void processFriendsList(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String username = (String)req.getSession().getAttribute("username");

        List<FriendsInfoContainer> friends_list = UserInfoDAO.getFriendsList(username);

        req.setAttribute("friends_list", friends_list);
        req.getRequestDispatcher("/restricted/binge_watching.jsp").forward(req, res);
    }
}
