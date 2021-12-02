/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.registration;

import chauhcl.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author hoang
 */
public class RegistrationDAO implements Serializable {

    public boolean checkLogin(String username, String password)
            throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1.Connect DB
            con = DBHelpers.makeConnection();
            //2.Create SQL String
            if (con != null) {
                String sql = "Select username "
                        + "From Registration "
                        + "Where username = ? And password = ?";

                //3.Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4.Excute Query
                rs = stm.executeQuery();
                //5.Process Result
                // neu  tra 1 dong thi dung if
                // neu tra nhieu dong thi dung while
                if (rs.next()) {
                    return true;
                }
            }//end connection is opened
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    private List<RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }
    
    
    public void searchLastname(String searchValue) 
            throws NamingException, SQLException, ClassNotFoundException{
         Connection con = null;
         PreparedStatement stm = null;
         ResultSet rs = null;
        try {
            //1.Connect DB
            con = DBHelpers.makeConnection();
            //2.Create SQL String
            if (con != null) {
                String sql = "Select username, password, lastname, isAdmin "
                           + "From Registration "
                           + "Where lastname Like ?";

                //3.Create Statement Object
                stm = con.prepareStatement(sql);
               stm.setString(1, "%" + searchValue + "%"); 
                //4.Excute Query
                rs = stm.executeQuery();
                //5.Process Result
                while(rs.next()){
                String username = rs.getString("username");
                //username tu sql
                String password = rs.getString("password");
                String lastname= rs.getString("lastname");
                boolean role = rs.getBoolean("isAdmin");
                //add vao dto
                RegistrationDTO dto = 
                        new RegistrationDTO(username, password, lastname, role);
               if(this.accountList == null){
                   this.accountList = new ArrayList<>();
               }//end if account list is not exist
               
               //acount list is existed
               this.accountList.add(dto);
                }//end while
                
                // neu  tra 1 dong thi dung if
                // neu tra nhieu dong thi dung while
                
            }//end connection is opened
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
   
    public boolean deleteAccount(String username)
      throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1.Connect DB
            con = DBHelpers.makeConnection();
            //2.Create SQL String
            if (con != null) {
                String sql = "Delete From Registration "
                        + "Where username = ?";

                //3.Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4.Excute Query
                int effectRows = stm.executeUpdate();
                //5.Process Result
                // neu  tra 1 dong thi dung if
                // neu tra nhieu dong thi dung while
                if (effectRows > 0) {
                    return true;
                }
            }//end connection is opened
        } finally {
           
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
     public boolean updateAccount(String username,String password,String chAdmin )
      throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean isAdmin = false;
      if(chAdmin != null){
            isAdmin = true;
        }
        try {
            //1.Connect DB
            con = DBHelpers.makeConnection();
            //2.Create SQL String
            if (con != null) {
                String sql = "Update Registration "
                        +"set password= ?, isAdmin = ? "
                        + "Where username = ?";

                //3.Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, isAdmin);
                stm.setString(3, username);
                //4.Excute Query
                int effectRows = stm.executeUpdate();
                //5.Process Result
                // neu  tra 1 dong thi dung if
                // neu tra nhieu dong thi dung while
                if (effectRows > 0) {
                    return true;
                }
            }//end connection is opened
        } finally {
           
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
     public boolean createNewAccount(String username, String password,
     String fullname, boolean role) 
             throws SQLException,NamingException, ClassNotFoundException {
       
      Connection con = null;
        PreparedStatement stm = null;
        try {
            //1.Connect DB
            con = DBHelpers.makeConnection();
            //2.Create SQL String
            if (con != null) {
                String sql = "Insert Into Registration(username, password, lastname, isAdmin) "
                        + "Values(?, ?, ?, ?)";

                //3.Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullname);
                stm.setBoolean(4, role);
                //4.Excute Query
                int effectRows = stm.executeUpdate();
                //5.Process Result
                // neu  tra 1 dong thi dung if
                // neu tra nhieu dong thi dung while
                if (effectRows > 0) {
                    return true;
                }
            }//end connection is opened
        } finally {
           
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
         
     }
     public String getLastname(String username) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String fullName = "";
        try {
            con = DBHelpers.makeConnection();

            String sql = "Select lastname "
                    + "From Registration "
                    + "Where username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            
            rs = stm.executeQuery();
            if(rs.next()){
                fullName = rs.getString("lastname");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return fullName;
    }
}


