/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.controller;

import chauhcl.product.ProductDAO;
import chauhcl.product.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "errorHTML";

        try  {
            HttpSession session = request.getSession(false);
            if(session !=null){
                ProductDAO dao = (ProductDAO)session.getAttribute("LIST_ITEM_IN_CART");
                Map<ProductDTO, Integer> orderList = dao.getOrderList();
                
                for (ProductDTO dto : orderList.keySet()) {
                    dao.checkOut(dto.getNameproduct(), orderList.get(dto), dto.getPrice(), dto.getPrice() * orderList.get(dto));
                    boolean result = dao.updateQuantity(dto.getNameproduct(), dto.getQuantity()-orderList.get(dto));
                    System.out.println(result);
                }
                session.removeAttribute("LIST_ITEM_IN_CART");
              url = "shoppingJSP";
            }
            
        } catch (SQLException ex) {
           log("CheckOutServlet SQLException" + ex.getMessage());
        } catch (NamingException ex) {
           log("CheckOutServlet NamingException" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
           log("CheckOutServlet ClassNotFoundException" + ex.getMessage());
        }finally{
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
