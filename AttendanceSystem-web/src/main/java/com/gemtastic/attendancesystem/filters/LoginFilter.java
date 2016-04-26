package com.gemtastic.attendancesystem.filters;

import com.gemtastic.attendancesystem.sessionbeans.SessionBean;
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
 * Filter for making sure access is restricted to logged in users.
 * 
 * @author Aizic Moisen
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * filtering method, triggers on all pages.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = (HttpSession) req.getSession();
        String url = req.getRequestURI();
        String loginURL = req.getContextPath() + "/login.xhtml";
        
        // Get session managed bean
        SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
        
        boolean loggedIn = sessionBean != null && sessionBean.isLoggedIn() != false;
        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER) ||
                req.getRequestURI().startsWith(req.getContextPath() + "/resources/");
        boolean loginRequest = req.getRequestURI().equals(loginURL);
                
        // If you are logged in, it's a login page request, or a resource request go on to next step, else redirect user to the login page.
        if(loggedIn|| loginRequest || resourceRequest){
            if(!resourceRequest){                                                       // If it's not a resource request set the following headers.
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
