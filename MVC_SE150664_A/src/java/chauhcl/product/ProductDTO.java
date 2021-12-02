/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.product;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author hoang
 */
public class ProductDTO implements Serializable{
    private String idproduct;
    private String nameproduct;
    private int price;
    private int quantity;
    private int total;

    public ProductDTO( String idproduct, String nameproduct, int price, int quantity) {
       this.idproduct =idproduct;
        this.nameproduct = nameproduct;
        this.price = price;
        this.quantity = quantity;
        this.total= total;
    }

    public ProductDTO(String nameproduct, int price, int quantity) {
        this.nameproduct = nameproduct;
        this.price = price;
        this.quantity = quantity;
    }
    

    public ProductDTO() {
    }

    public String getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    


    public ProductDTO(String idproduct, String nameproduct, int price, int quantity, int total) {
        this.idproduct = idproduct;
        this.nameproduct = nameproduct;
        this.price = price;
        this.quantity = quantity;
        
    }
    
    
    
  
    
    
    
}
