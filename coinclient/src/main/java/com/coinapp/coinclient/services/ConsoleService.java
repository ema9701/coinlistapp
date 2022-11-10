package com.coinapp.coinclient.services;


import com.coinapp.coinclient.model.Coin;
import com.coinapp.coinclient.model.Watchlist;

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

    public void printMainMenu() {
        System.out.println("=============================");
        System.out.println("     Watchlist API Client    ");
        System.out.println("=============================");
        System.out.println("1. Search for currency by ID.");
        System.out.println("2. View Coins in Database.");
        System.out.println("3. Watchlist Menu");
        System.out.println("0. Exit");

    }

    public void printWatchlistMenu() {
        System.out.println("=============================");
        System.out.println("      Watchlist Actions      ");
        System.out.println("=============================");
        System.out.println("1. Create new list.");
        System.out.println("2. View/Edit Saved lists.");
        System.out.println("3. Delete list. ");
        System.out.println("0. Return to main menu.");
    }

    public void printCoinData(Coin coin, String id) {
        if (coin != null) {
            String msg = "Recent Coin Data for: " + id;
            System.out.println("==============================");
            System.out.println(msg);
            System.out.println("==============================");
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

    public void printSavedLists(Watchlist[] lists) {
        if (lists != null) {
            System.out.println("=============================");
            for (Watchlist w : lists) {
                System.out.println(w.toString());
            }
            System.out.println("============================");
        }
    }

    public void printCoinEntriesOnList(Watchlist list) {
        if (list != null) {
            System.out.println("============================");
            System.out.println(list.getListName());
            if (list.getCoinsToWatch().isEmpty()) {
                System.out.println("No coins saved to this list.");
            } else {
                for (Coin coin : list.getCoinsToWatch()) {
                    System.out.println("Id: " + coin.getCoinId() +
                            "\nSymbol: " + coin.getSymbol() +
                            "\nName: " + coin.getName() +
                            "\nCurrentPrice: " + coin.getCurrentPrice());
                }
            }
            System.out.println("============================");
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
