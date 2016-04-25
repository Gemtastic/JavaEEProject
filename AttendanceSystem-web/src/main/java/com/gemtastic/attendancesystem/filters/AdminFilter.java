/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.filters;

import com.gemtastic.attendancesystem.sessionbeans.SessionBean;
import java.io.IOException;
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

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = (HttpSession) req.getSession();
        String url = req.getRequestURI();
        String indexURL = req.getContextPath() + "/index.xhtml";
        
        SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
        
        Boolean logIn = (Boolean) session.getAttribute("isLoggedIn");
        String userType  = sessionBean.getUsertype();
        boolean loggedIn = logIn != null && logIn != false;
        boolean admin = userType.equals("admin");
        boolean adminRequest = req.getRequestURI().startsWith(req.getContextPath() + "/admin/");
                
        if(adminRequest){
            if(loggedIn && admin){
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(indexURL);
            }
        } else {
            resp.sendRedirect(indexURL);
        }
    }

    @Override
    public void destroy() {
    }
    
}
