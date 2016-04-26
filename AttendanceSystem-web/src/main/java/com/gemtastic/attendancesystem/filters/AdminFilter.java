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
 * A filter for ensuring access is limited to admins.
 * 
 * @author Aizic Moisen
 */

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * The filter method. It's set to trigger on /admin/ paths only.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Cast the servlet responses into HttpServlets.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // Get session
        HttpSession session = (HttpSession) req.getSession();
        // Index path url
        String indexURL = req.getContextPath() + "/index.xhtml";
        
        // Get session managed bean
        SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
        
        String userType  = sessionBean.getUsertype();
        boolean loggedIn = sessionBean.isLoggedIn();
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
