package com.coinapp.coinclient;

import com.coinapp.coinclient.model.Coin;
import com.coinapp.coinclient.model.CoinDTO;
import com.coinapp.coinclient.model.Watchlist;
import com.coinapp.coinclient.services.ConsoleService;
import com.coinapp.coinclient.services.CoinService;
import com.coinapp.coinclient.services.WatchlistService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
public class CoinClientApp {

    ConsoleService console = new ConsoleService();
    CoinService coinService = new CoinService();
    WatchlistService listService = new WatchlistService();


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
                searchForCoin();
            } else if (menuSelection == 2) {
                handleCoinEntries();
            } else if (menuSelection == 3) {
                printAllLists();
            } else if (menuSelection == 4) {
                createList();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid selection.");
            }
            console.pause();
        }
    }

    private void searchForCoin() {
        String idSearch = console.promptForString("Search for a coin by id: ");
        try {
            Coin response = coinService.searchCoinOnGecko(idSearch);
            console.printCoinData(response, response.getApiId());
            String addToDB = console.promptForString("Add listing to database? (Y/N)");
                if (addToDB.equalsIgnoreCase("y")) {
                    double price = response.getMarketData().getCurrentPrice().get("usd");
                    CoinDTO entry = new CoinDTO(response.getSymbol(), response.getName(), price);
                    Coin savedCoin = coinService.getCoinDetails(coinService.saveCoin(entry).getCoinId());
                    if (savedCoin != null) {
                        System.out.println(savedCoin.getCoinId() +
                                    "\n" + savedCoin.getSymbol() +
                                    "\n" + savedCoin.getName() +
                                    "\n" + savedCoin.getCurrentPrice());
                        }
                } else if (addToDB.equalsIgnoreCase("n")) {
                    return;
                }
        } catch (HttpClientErrorException e) {
            e.getMessage();
        }
    }

    private void handleCoinEntries() {
        Coin[] coins = coinService.listSavedCoins();
        console.printDBEntries(coins);
    }

    private void printAllLists() {
        Watchlist[] lists = listService.getAllLists();
        console.printSavedLists(lists);
    }

    private void createList() {
        String title = console.promptForString("Enter a name for the list: ");
        Watchlist newList = listService.createNewList(title);
        if (newList != null) {
            console.printCoinEntriesOnList(newList);
        }
    }


}


