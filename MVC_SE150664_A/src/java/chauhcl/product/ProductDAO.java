/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.product;

import chauhcl.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author hoang
 */
public class ProductDAO implements Serializable {

    private List<ProductDTO> listProduct;

    public List<ProductDTO> getListProduct() {
        return listProduct;
    }

    

    public void loadProductFromDB() throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            String sql = "Select  nameProduct, price, quantity "
                    + "From Product ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("nameProduct");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                ProductDTO dto = new ProductDTO(productName, price, quantity);

                //add to list
                if (this.listProduct == null) {
                    this.listProduct = new ArrayList<>();
                }
                this.listProduct.add(dto);
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
    }

    private Map<ProductDTO, Integer> orderList;

    public Map<ProductDTO, Integer> getOrderList() {
        return orderList;
    }

    public void addItem(String nameProduct) throws SQLException, ClassNotFoundException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            String sql = "Select idProduct,nameProduct, price, quantity "
                    + "From Product "
                    + "Where nameProduct=?";

            stm = con.prepareStatement(sql);
            stm.setString(1, nameProduct);

            rs = stm.executeQuery();
            if (rs.next()) {
                String productID = rs.getString("idProduct");
                String productName = rs.getString("nameProduct");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                ProductDTO dto = new ProductDTO(productID, productName, price, quantity);
                if (this.orderList == null) {
                    this.orderList = new HashMap<>();
                }
                //check if items existed;
                int flag = 0;
                if (!this.orderList.isEmpty()) {
                    for (ProductDTO item : orderList.keySet()) {
                        if (dto.getIdproduct().equals(item.getIdproduct())) {
                            this.orderList.put(item, orderList.get(item) + 1);
                            flag = 1;
                        }
                    }
                }
                if (flag != 1) {
                    this.orderList.put(dto, 1);
                }

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
    }
    
     public void removeItems(String productName) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rst = null;
        try {
            con = DBHelpers.makeConnection();

            String sql = "Select idProduct,nameProduct, price, quantity "
                    + "From Product "
                    + "Where nameProduct = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, productName);

            rst = stm.executeQuery();
            if (rst.next()) {
                String productID = rst.getString("idProduct");
                int quantity = rst.getInt("quantity");
                int price = rst.getInt("price");
                if (this.orderList == null) {
                    return;
                }
                ProductDTO dto = new ProductDTO();
                for (ProductDTO item : orderList.keySet()) {
                    if (item.getNameproduct().equals(productName)) {
                        dto = item;
                    }
                }
                this.orderList.remove(dto);
                if (this.orderList.isEmpty()) {
                    this.orderList = null;
                }

            }
        } finally {
            if (rst != null) {
                rst.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
      public boolean checkOut(String nameProduct, int quantity,int price,int total) throws SQLException, ClassNotFoundException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.makeConnection();
            
            String sql = "INSERT INTO OrderDetail(nameProduct, quantity, price, total) "
                    + "Values(?, ?, ?, ?)";
            stm = con.prepareStatement(sql);
            stm.setString(1,nameProduct);
            stm.setInt(2, quantity);
            stm.setInt(3, price);
            stm.setInt(4, total);
            
            int rowEffects = stm.executeUpdate();
            if(rowEffects>0)
                return true;
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
      
      public boolean updateQuantity(String nameProduct, int quantity) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.makeConnection();

            String sql = "Update Product "
                    + "Set quantity = ? "
                    + "Where nameProduct = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, quantity);
            stm.setString(2, nameProduct);

            int rowEffects = stm.executeUpdate();
            if (rowEffects > 0) {
                return true;
            }

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
}