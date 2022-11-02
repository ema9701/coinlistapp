package com.coinapp.coinclient;

import com.coinapp.coinclient.services.ConsoleService;
import com.coinapp.coinclient.services.CoinService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinClientApp {

    ConsoleService console = new ConsoleService();
    CoinService coinService = new CoinService();


    public static void main(String[] args) {
        CoinClientApp app = new CoinClientApp();
        app.run();
    }

    private void run() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            console.mainMenuList();
            menuSelection = console.promptForMenuSelection("Please select a service: "
            );
            if (menuSelection == 1) {
            } else if (menuSelection == 2) {
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid selection.");
            }
            console.pause();
        }
    }

}


