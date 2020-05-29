package com.tvseries.servlets;

import com.tvseries.containers.SearchListContainer;
import com.tvseries.containers.SeasonsListContainer;
import com.tvseries.dao.SearchListDAO;
import com.tvseries.dao.SeasonInfoDAO;
import com.tvseries.dao.UserInfoDAO;
import com.tvseries.utils.PasswordChecker;
import com.tvseries.utils.Triplet;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ListManagerServlet extends HttpServlet
{
    private String processStatus(String status)
    {
        if(status == null)
        {
            return "Not watching";
        }

        status = status.toLowerCase();

        switch (status)
        {
            case "completed":
                return "Completed";

            case "plan":

            case "plan to watch":
                return "Plan to watch";

            case "not watching":
                return "Not watching";

            case "dropped":
                return "Dropped";

            default:
                return "Watching";
        }
    }

    public void processSeasonList(HttpServletRequest req, HttpServletResponse res)
    {
        HttpSession session = req.getSession();

        String status = req.getParameter("status");
        status = processStatus(status);

        try
        {
            List<SeasonsListContainer> seasons_list = UserInfoDAO.getUserList((String)session.getAttribute("username"), status);

            req.setAttribute("seasons_list", seasons_list);
            req.getRequestDispatcher("/restricted/list.jsp").forward(req, res);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void processSearchList(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String search_key = req.getParameter("search_key");
        Integer series_limit = 10;
        Integer seasons_limit = 5;

        req.setAttribute("search_list", SearchListDAO.getSearchList(search_key, series_limit, seasons_limit));
        req.getRequestDispatcher("/lists/search_list.jsp").forward(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        String servlet_path = req.getServletPath();

        try
        {
            if (servlet_path.equals("/restricted/list"))
            {
                processSeasonList(req, res);
            }
            else if (servlet_path.equals("/search_list"))
            {
                processSearchList(req, res);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        HttpSession session = req.getSession();

        String username = (String)session.getAttribute("username");
        String season_id_str = req.getParameter("season_id");

        //if someone tries to update the list without being logged in
        if(username == null)
        {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        //if the season_id is invalid
        if(season_id_str == null || !PasswordChecker.onlyDigits(season_id_str))
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //get the inputs
        Integer season_id = Integer.parseInt(season_id_str);
        String current_watch_status = processStatus(req.getParameter("current_watch_status"));
        String watch_status = processStatus(req.getParameter("watch_status"));
        String[] watched_episodes_str = req.getParameterValues("episode");

        //build a list of episode_ids that the user has watched
        List<Integer> watched_episodes = new ArrayList<>();
        if(watched_episodes_str != null)
        {
            for (String s : watched_episodes_str)
            {
                if (GenericValidator.isInt(s))
                {
                    watched_episodes.add(Integer.parseInt(s));
                }
            }
        }

        try
        {
            if (watch_status.equals("Not watching"))
            {
                boolean removed = UserInfoDAO.removeSeason(season_id, username);
                //TODO: afiseaza ceva cand stergi sezonul din lista
            }
            else
            {
                String score_str = req.getParameter("score");
                Integer score = score_str == null ? null : Integer.parseInt(score_str);

                LocalDate my_start = null;
                LocalDate my_end = null;

                //TODO: logica pentru setat my_start, my_end, logica pentru setat Completed daca am vazut toate episoadele

                if(!current_watch_status.equals("Watching"))
                {
                    if(!watched_episodes.isEmpty() && !current_watch_status.equals("Dropped") && !current_watch_status.equals("Completed"))
                    {
                        watch_status = "Watching";
                    }

                    if(watch_status.equals("Watching"))
                    {
                        my_start = LocalDate.now(ZoneId.of("Europe/Paris"));
                    }
                }

                boolean complete_episodes = false;
                if(!current_watch_status.equals("Completed"))
                {
                    if(watch_status.equals("Completed"))
                    {
                        my_end = LocalDate.now(ZoneId.of("Europe/Paris"));
                        complete_episodes = true;
                    }
                }

                boolean updated_season = UserInfoDAO.updateSeasonsList(season_id, username, my_start, my_end, score, watch_status);
                if(complete_episodes)
                {
                    boolean update_episodes = UserInfoDAO.setCompleted(username, season_id);
                }
                else
                {
                    boolean updated_episodes = UserInfoDAO.updateEpisodesList(watched_episodes, username, season_id);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        res.sendRedirect(req.getContextPath() + "/season_info?season_id=" + season_id_str);
    }
}
