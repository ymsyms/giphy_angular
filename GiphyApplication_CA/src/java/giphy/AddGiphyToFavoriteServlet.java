/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giphy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Yimon Soe
 */
@WebServlet(name = "AddGiphyToFavoriteServlet",urlPatterns = "/addGiphyToFavorite")
public class AddGiphyToFavoriteServlet extends HttpServlet{
    private static String query = "INSERT INTO giphy_data.favorite_giphy (userid, url, title) VALUES (?, ?, ?);";
    private Integer rowsAffected;
    
    @Resource(lookup = "jdbc/giphy_data")
    private DataSource ds;
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {           
        try (Connection conn = ds.getConnection())
        {
            String userid = request.getParameter("userid");
            String url = request.getParameter("url");
            String title = request.getParameter("title");
                    
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, userid);
            ps.setString(2, url);
            ps.setString(3, title);
            
            rowsAffected = ps.executeUpdate();
                       
            conn.close();
        } 
        catch (SQLException ex) 
        {
            log(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } 
       
        // set status code
        response.setStatus(HttpServletResponse.SC_OK);
        
        // set media type
        response.setContentType(MediaType.TEXT_HTML);
        
        try (PrintWriter out = response.getWriter()) 
        {           
           out.println(rowsAffected);          
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

