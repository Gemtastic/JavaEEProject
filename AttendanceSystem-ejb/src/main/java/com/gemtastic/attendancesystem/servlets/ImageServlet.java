/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gemtastic.attendancesystem.servlets;

import com.gemtastic.attendancesystem.services.CRUDservices.interfaces.LocalStudentEJBService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gemtastic
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
    
    @EJB
    LocalStudentEJBService sEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.valueOf(req.getPathInfo().substring(1));
        byte[] img = sEJB.readOne(id).getImage();
        
        if(img != null && img.length > 0) {
            resp.getOutputStream().write(img);
            resp.getOutputStream().close();
        } else {
            ServletContext cntxt = getServletContext();
            
            String filePath = "/resources/img/no_image.jpg";
            String fullPath = cntxt.getRealPath(filePath);
            File image = new File(fullPath);
            
            String mime = cntxt.getMimeType(fullPath);
            
            resp.setContentType(mime);
            resp.setContentLength((int) image.length());
            
            BufferedImage in = ImageIO.read(image);
            try (OutputStream out = resp.getOutputStream()) {
                ImageIO.write(in, "jpg", out);
            } catch(Exception e) {
                System.out.println("Caught an exception!");
                resp.setStatus(500);
            }
        }
    }
}
