/*
  Class to display the Home view
*/
package com.customify.client.views;

import com.customify.client.views.Business.BusinessRegisterView;
import com.customify.client.views.Business.BusinessView;

import java.io.IOException;
import java.net.Socket;
import  java.util.*;

public class Home {

    private Socket socket;

    public Home(Socket socket){
        this.socket = socket;
    }
    public Socket getSocket()
    {
        return socket;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    public void view() throws Exception {

        int choice;
        Scanner scan = new Scanner(System.in);
        LoginView loginView =new LoginView(this.socket);
        SignupView signupView =new SignupView(this.socket);
        ProductView productView = new ProductView(this.socket);
        BusinessView businessView=new BusinessView(this.socket);

        System.out.println("---------------------------------------------");
        System.out.println("--------------CUSTOMIFY HOME-----------------");
        System.out.println("\n           1. Sign Up");
        System.out.println("           2. Login In");
        System.out.println("           3. Register product");
        System.out.println("           4. See all Products");
        System.out.println("           5. Business ");

        choice = scan.nextInt();

        switch(choice)
        {
            case 1:
                signupView.view();
                break;
            case 2:
                loginView.view();
                break;
            case 3:
                productView.createProduct();
                break;
            case 4:
                productView.getAll();
                break;
            case 5:
                businessView.view();
                break;
            default:
                System.out.println("Invalid choice");
        }

    }
}
