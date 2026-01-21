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
public class CartItem {

    private Interface_kopi product;
    private int qty;

    public CartItem(Interface_kopi product) {
        this.product = product;
        this.qty = 0;
    }

    public void increment() {
        qty++;
    }

    public void decrement() {
        if (qty > 0) {
            qty--;
        }
    }

    public int getSubtotal() {
        return product.getFinalPrice() * qty;
    }

    public int getQty() {
        return qty;
    }

    public Interface_kopi getProduct() {
        return product;
    }

    public String getProductName() {
        return product.getName(); // ambil dari backend
    }
}
