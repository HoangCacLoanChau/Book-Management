/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.controller;

import chauhcl.registration.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hoang
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})

public class LoginServlet extends HttpServlet {
   
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException
            {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();      
         Map<String,String> roadmap = (Map<String,String>) request.getServletContext().getAttribute("ROAD");

        String button = request.getParameter("btAction");
        String url = roadmap.get("invalidHTML");
         String username = request.getParameter("txtUsername");
         String password = request.getParameter("txtPassword");
    
    try{
      
       
        // call DAO -> new DAO object, then call method from object
            RegistrationDAO dao = new RegistrationDAO();
            String fullname = dao.getLastname(username);
            boolean result = dao.checkLogin(username, password);
            
            if(result){      
                 url= roadmap.get("searchJSP");
             
             HttpSession session = request.getSession();
             session.setAttribute("USERNAME", username);
             session.setAttribute("FULLNAME", fullname);
                 
            }
        //end if user has already clicked login      
        }     
        catch(NamingException ex){
           log("LoginServlet NamingException" + ex.getMessage());
                }
        catch(SQLException ex ){
            log("LoginServlet SQLException " +ex.getMessage());
        } catch (ClassNotFoundException ex) {
           log("LoginServlet ClassNotFoundException " +ex.getMessage());

          }
        finally{
         response.sendRedirect(url);
       
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
