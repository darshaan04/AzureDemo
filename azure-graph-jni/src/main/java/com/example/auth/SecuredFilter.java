package com.example.auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SecuredFilter ensures that only authenticated users can access /secure paths.
 */
@WebFilter(urlPatterns = {"/secure/*", "/secure"})
public class SecuredFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed for this filter
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // If no session exists or user attribute is missing, redirect to login
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // User is authenticated, continue to requested resource
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup needed for this filter
    }
}
