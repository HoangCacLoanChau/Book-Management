/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.controller;

import chauhcl.registration.RegistrationCreateError;
import chauhcl.registration.RegistrationDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author hoang
 */
@WebServlet(name = "CreateNewAccount", urlPatterns = {"/CreateNewAccount"})
public class CreateNewAccount extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        Map<String, String> roadmap = (Map<String, String>) request.getServletContext().getAttribute("ROAD");
        String url = roadmap.get("createNewAccountJSP");

        RegistrationCreateError errors = new RegistrationCreateError();
        boolean foundErr = false;
        try {
            //1.check all errors's user
            if (username.trim().length() < 6 || username.trim().length() > 20) {
                foundErr = true;
                errors.setUsernameLengthErr("Username is required form 6 to 20 character");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("password is required form 6 to 30 character");
            } else if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMatched("confirm must match password");

            }
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullnameLengthErr("Fullname is required form 2 to 50 character");
            }
            //2.process
            if (foundErr) {
                //send error to user
                request.setAttribute("CREATE_ERROR", errors);

            } else {
                // call dao
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createNewAccount(username, password, fullname, false);
                if (result) {
                    //transfer to login
                    url = "loginHTML";
                }// insert done
            }
        } catch (NamingException ex) {
            log("CreateNewAccountServlet Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("CreateNewAccountServlet SQL : " + ex.getMessage());
            if (msg.contains("duplicate")) {
                errors.setUsernameIsExisted(username + " is Existed");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (ClassNotFoundException ex) {
            log("CreateNewAccountServlet ClassNotFoundException  : " + ex.getMessage());
        } finally {
            if (foundErr) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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