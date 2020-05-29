package com.tvseries.servlets;

import com.tvseries.containers.*;
import com.tvseries.dao.EpisodeInfoDAO;
import com.tvseries.dao.SeasonInfoDAO;
import com.tvseries.dao.SeriesInfoDAO;
import com.tvseries.dao.UserInfoDAO;
import com.tvseries.utils.Encoder;
import com.tvseries.utils.PasswordChecker;
import javafx.util.Pair;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DisplayInfoServlet extends HttpServlet
{
    public void displaySeasonPage(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("username");
        String season_id_str = req.getParameter("season_id");

        if(season_id_str == null || !PasswordChecker.onlyDigits(season_id_str))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Integer season_id = Integer.parseInt(season_id_str);

        if(!SeasonInfoDAO.existsSeason(season_id))
        {
            System.out.println("check if exists");
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        SeasonInfoContainer season_info = SeasonInfoDAO.getSeasonInfo(season_id, username);
        List<Pair<String, Integer>> time_spent = UserInfoDAO.timeSpentPerMonth(username, season_id);

        req.setAttribute("season_info", season_info);
        req.setAttribute("time_spent", time_spent);

        req.getRequestDispatcher("/season_info.jsp").forward(req, res);
    }

    public void displaySeriesPage(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String series_id_str = req.getParameter("series_id");

        if(series_id_str == null || !PasswordChecker.onlyDigits(series_id_str))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Integer series_id = Integer.parseInt(series_id_str);

        if(!SeriesInfoDAO.existsSeries(series_id))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        SeriesInfoContainer series_info = SeriesInfoDAO.getSeriesInfo(series_id);

        req.setAttribute("series_info", series_info);

        req.getRequestDispatcher("/series_info.jsp").forward(req, res);
    }

    public void displayEpisodePage(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String episode_id_str = req.getParameter("episode_id");

        if(episode_id_str == null || !PasswordChecker.onlyDigits(episode_id_str))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Integer episode_id = Integer.parseInt(episode_id_str);

        if(!EpisodeInfoDAO.existsEpisode(episode_id))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        EpisodeInfoContainer episode_info = EpisodeInfoDAO.getEpisodeInfo(episode_id);

        req.setAttribute("episode_info", episode_info);

        req.getRequestDispatcher("/episode_info.jsp").forward(req, res);
    }

    public void displayUserPage(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String username = req.getParameter("username");

        if(username == null || !PasswordChecker.onlyDigitsAndLetters(username) || !UserInfoDAO.existsUser(username))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserInfoContainer user_info = UserInfoDAO.getUserInfo(username);

        req.setAttribute("user_info", user_info);

        req.getRequestDispatcher("/user_info.jsp").forward(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        String action = req.getServletPath();

        try
        {
            if(action.equals("/season_info"))
            {
                displaySeasonPage(req, res);
            }
            else if(action.equals("/series_info"))
            {
                displaySeriesPage(req, res);
            }
            else if(action.equals("/episode_info"))
            {
                displayEpisodePage(req, res);
            }
            else if(action.equals("/user_info"))
            {
                displayUserPage(req, res);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
