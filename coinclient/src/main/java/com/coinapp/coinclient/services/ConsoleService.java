package com.coinapp.coinclient.services;



import com.coinapp.coinclient.model.Coin;
import com.coinapp.coinclient.model.CoinList;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.println(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void mainMenuList() {
        System.out.println("==========================");
        System.out.println("   Watchlist API Client   ");
        System.out.println("==========================");
        System.out.println("1. Search for currency by ID.");
        System.out.println("2. List saved coins.");
        System.out.println("3. Create new watchlist.");
        System.out.println("0. Exit");

    }

    public void printCoinData(Coin coin, String id) {
        if (coin != null) {
            String msg = "Recent Coin Data for: " + id;
            System.out.println("=============================");
            System.out.println(msg);
            System.out.println("=============================");
            System.out.println(coin.toString());
        }
    }

    public void printDBEntries(Coin[] coins) {
        if (coins != null) {
            for (Coin coin : coins) {
                System.out.println(coin.getName());
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public String promptForString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }


    public void printError() {
        System.out.println("An error occurred. Please try again.");
    }

}
