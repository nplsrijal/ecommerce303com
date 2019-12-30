package com.example.onlineshopping_ecommerce;

import com.example.onlineshopping_ecommerce.BLL.LoginBLL;
import com.example.onlineshopping_ecommerce.BLL.ProductBLL;
import com.example.onlineshopping_ecommerce.BLL.ProfileBLL;
import com.example.onlineshopping_ecommerce.BLL.RegisterBLL;
import com.example.onlineshopping_ecommerce.url.Url;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class APITest {

    //Login Test

    @Test
    public void testLoginUser(){
        LoginBLL loginBLL = new LoginBLL("srijal.fantastic@gmail.com","srijal");
        boolean result=loginBLL.checkLogin();
        assertEquals(true,result);
    }

    @Test
    public void testLogin(){
        LoginBLL loginBLL = new LoginBLL("srijal.fantastic@gmail.com","nepal");
        boolean result=loginBLL.checkLogin();
        assertEquals(true,result);
    }

    //Register Test (POST)

    @Test
    public void testRegister(){
        RegisterBLL loginBLL = new RegisterBLL("Shreejal Nepal","fantastic.srijal@gmail.com","nepal","9823334233","Kathmandu","2998b1e357c840028236f2a930843af3");
        boolean result=loginBLL.checkRegister();
        assertEquals(true,result);
    }



    @Test
    public void testRegisterUser(){
        RegisterBLL loginBLL = new RegisterBLL("Shreejal Nepal","f@test.com","nepal","9823334233","Kathmandu","2998b1e357c840028236f2a930843af3");
        boolean result=loginBLL.checkRegister();
        assertEquals(false,result);
    }


    //Product Search Test
    @Test
    public void testProductSearchByName(){
        ProductBLL loginBLL = new ProductBLL("Vest", Url.token,"95674760-8365-11e9-b294-b759ac43fa64");
        boolean result=loginBLL.checkSearchProductByName();
        assertEquals(false,result);
    }

    //Profile Update Test

    @Test
    public void testProfileUpdate(){
        ProfileBLL loginBLL = new ProfileBLL("96312690-8e67-11e9-99b8-11b1d4acf706", "Shreejal Nepal","srijal.fantastic@gmail.com","9832834532","Kathmandu",Url.token,"imagenew.jpg");
        boolean result=loginBLL.checkProfileUpdate();
        assertEquals(false,result);
    }

}
