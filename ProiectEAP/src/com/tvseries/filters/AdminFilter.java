package com.tvseries.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpSession session = req.getSession();
        //String loginURI = req.getContextPath() + "/administrative/log";

        String username = (String)session.getAttribute("username");
        boolean loggedIn = username != null && username.equals("admin");
        //boolean logRequest = req.getRequestURI().equals(loginURI);

        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0); // Proxies.

        if(loggedIn)
        {
            chain.doFilter(req, res);
        }
        else
        {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            //res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
