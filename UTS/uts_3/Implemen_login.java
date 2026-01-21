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
public class Implemen_login implements Interface_login {

    private final User user = new User("Kopi", "12345");

    @Override
    public boolean login(String username, String password) {

        return user.getUsername().equals(username)
                && user.getPassword().equals(password);
    }
}
