/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts_3;

/**
 *
 * @author x260
 */
public abstract class Product {

    protected String name;
    protected int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // Getter
    public String getName() {
        return name;
    }
    

    public int getPrice() {
        return price;
    }

    // Method umum
    public int getFinalPrice() {
        return price;
    }
}
