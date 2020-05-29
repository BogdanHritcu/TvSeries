package com.tvseries.servlets;

import com.tvseries.dao.UserInfoDAO;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoggingServlet extends HttpServlet
{
    public boolean validateCredentials(String user, String pass) throws Exception
    {
        String hashpass = DigestUtils.sha256Hex(pass);
        String db_hashpass = (String) UserInfoDAO.getUserAttribute(user, "password");

        return hashpass.equals(db_hashpass);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        HttpSession session = req.getSession();

        String username = (String)session.getAttribute("username");

        try
        {
            if(username != null) //logout
            {
                session.removeAttribute("username");
                session.removeAttribute("display_name");
                session.removeAttribute("picture_path");
                res.sendRedirect(req.getContextPath() + "/index.jsp");
            }
            else //login
            {
                String user = req.getParameter("username");
                String pass = req.getParameter("password");

                if (validateCredentials(user, pass))
                {
                    session.setAttribute("username", user);
                    session.setAttribute("display_name", UserInfoDAO.getUserAttribute(user, "display_name"));
                    session.setAttribute("picture_path", UserInfoDAO.getUserAttribute(user, "picture_path"));
                    res.sendRedirect(req.getContextPath() + "/index.jsp");
                }
                else //invalid credentials
                {
                    req.setAttribute("login_message", "Invalid username or password!");
                    req.getRequestDispatcher("/login.jsp").forward(req, res);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
