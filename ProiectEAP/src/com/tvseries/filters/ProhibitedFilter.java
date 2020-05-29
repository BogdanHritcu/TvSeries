package com.tvseries.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProhibitedFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException
    {
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0); // Proxies.

        res.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}