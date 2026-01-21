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
public class PromoCoffee extends CoffeeProduct{

    private int discount; // persen

    public PromoCoffee(String name, int price, String type, boolean sugar, int discount) {
        super(name, price, type, sugar);
        this.discount = discount;
    }

    @Override
    public int getFinalPrice() {
        int normalPrice = super.getFinalPrice();
        return normalPrice - (normalPrice * discount / 100);
    }
}
