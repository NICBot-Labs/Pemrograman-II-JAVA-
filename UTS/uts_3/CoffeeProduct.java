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
public class CoffeeProduct extends Product implements Interface_kopi {

    protected String type;      // Hot / Ice
    protected boolean sugar;

    public CoffeeProduct(String name, int price, String type, boolean sugar) {
        super(name, price);
        this.type = type;
        this.sugar = sugar;
    }

    @Override
    public int getFinalPrice() {
        if (type.equalsIgnoreCase("Ice")) {
            return price + 2000;
        }
        return price;
    }
}
