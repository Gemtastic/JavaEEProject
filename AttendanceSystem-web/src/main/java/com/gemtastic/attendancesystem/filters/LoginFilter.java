/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.filters;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gemtastic
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = (HttpSession) req.getSession();
        String url = req.getRequestURI();
        String loginURL = req.getContextPath() + "/login.xhtml";
        
        Boolean logIn = (Boolean) session.getAttribute("isLoggedIn");
        boolean loggedIn = logIn != null && logIn != false;
        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER) ||
                req.getRequestURI().startsWith(req.getContextPath() + "/resources/");
        boolean loginRequest = req.getRequestURI().equals(loginURL);
                
        if(loggedIn|| loginRequest || resourceRequest){
            if(!resourceRequest){
                resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                resp.setDateHeader("Expires", 0); // Proxies.
            }
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(loginURL);
        }
    }

    @Override
    public void destroy() {
    }
    
}
