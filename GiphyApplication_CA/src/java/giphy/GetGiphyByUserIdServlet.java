/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giphy;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
@WebServlet(name="GetGiphyByUserIdServlet", urlPatterns = {"/getGiphyByUserId/*"})
public class GetGiphyByUserIdServlet extends HttpServlet {

    private static String query = "SELECT * FROM giphy_data.favorite_giphy where userid = ?";
    
    @Resource(lookup = "jdbc/giphy_data")
    private DataSource ds;
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        JsonArrayBuilder gifphyBuilder = Json.createArrayBuilder();
        
        String username = request.getPathInfo().substring(1);
        
        try (Connection conn = ds.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);	
            ResultSet rs = ps.executeQuery();
            
            // show error page if not records from query
            if (!rs.isBeforeFirst()) 
            {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;
            }

            // construct json object
            while(rs.next())
            {
                JsonObjectBuilder obj = Json.createObjectBuilder();
                obj.add("userid", rs.getString("userid"))
                        .add("url", rs.getInt("url"))
                        .add("title", (rs.getString("title") == null)? "": rs.getString("title"));
                gifphyBuilder.add(obj.build());
            }
            rs.close();
            conn.close();
        } 
        catch (SQLException ex) 
        {
            log(ex.getMessage());
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
       
        // set status code
        response.setStatus(HttpServletResponse.SC_OK);
        
        // set media type
        response.setContentType(MediaType.APPLICATION_JSON);
        
        try (PrintWriter out = response.getWriter()) 
        {
           JsonArray datas = gifphyBuilder.build();
           out.println(datas.toString());          
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
            throws ServletException, IOException 
    {
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
            throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }// </editor-fold>

}