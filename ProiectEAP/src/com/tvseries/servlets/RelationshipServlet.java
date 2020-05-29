package com.tvseries.servlets;

import com.tvseries.containers.BingeWatchingContainer;
import com.tvseries.containers.FriendsInfoContainer;
import com.tvseries.dao.UserInfoDAO;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RelationshipServlet extends HttpServlet
{
    String getErrorMessage(String error_code)
    {
        switch (error_code)
        {
            case "0":
                return "A friend request was sent.";

            case "1":
                return "User does not exist!";

            case "2":
                return "User is already in your friend/pending list.";

            case "3":
                return "You cannot befriend yourself :(";

            default:
                return "Invalid username";

        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String servlet_path = req.getServletPath();
        String username = (String)req.getSession().getAttribute("username");
        String friend_username = req.getParameter("friend_username");
        String error_code = "0";
        try
        {
            if (servlet_path.equals("/restricted/add_friend"))
            {
                boolean add = true;

                if(!UserInfoDAO.existsUser(friend_username))
                {
                    add = false;
                    error_code = "1";
                }

                if(add && UserInfoDAO.alreadyFriends(username, friend_username))
                {
                    add = false;
                    error_code = "2";
                }

                if(add && username.equals(friend_username))
                {
                    add = false;
                    error_code = "3";
                }

                if(add)
                {
                    boolean added = UserInfoDAO.addFriend(username, friend_username);
                }
            }
            else if (servlet_path.equals("/restricted/accept_friend"))
            {
                boolean accepted = UserInfoDAO.acceptFriend(username, friend_username);
            }
            else if (servlet_path.equals("/restricted/remove_friend"))
            {
                boolean removed = UserInfoDAO.removeFriend(username, friend_username);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        res.sendRedirect(req.getContextPath() + "/restricted/show_friends?error_code=" + error_code);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        String username = (String)req.getSession().getAttribute("username");
        String error_code = req.getParameter("error_code");

        if(!GenericValidator.isBlankOrNull(error_code))
        {
            req.setAttribute("error_message", getErrorMessage(error_code));
        }

        try
        {
            List<FriendsInfoContainer> friends_list = UserInfoDAO.getFriendsList(username);
            List<FriendsInfoContainer> pending_list = UserInfoDAO.getPendingList(username);
            List<FriendsInfoContainer> requests_list = UserInfoDAO.getRequestsList(username);

            List<BingeWatchingContainer> binge_watching_list = UserInfoDAO.getBingeWatchingList(username);

            req.setAttribute("friends_list", friends_list);
            req.setAttribute("pending_list", pending_list);
            req.setAttribute("requests_list", requests_list);

            req.setAttribute("binge_watching_list", binge_watching_list);

            req.getRequestDispatcher("/restricted/friends.jsp").forward(req, res);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
