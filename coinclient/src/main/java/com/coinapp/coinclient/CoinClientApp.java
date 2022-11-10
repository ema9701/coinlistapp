package com.coinapp.coinclient;

import com.coinapp.coinclient.model.Coin;
import com.coinapp.coinclient.model.CoinDTO;
import com.coinapp.coinclient.model.Watchlist;
import com.coinapp.coinclient.services.ConsoleService;
import com.coinapp.coinclient.services.CoinService;
import com.coinapp.coinclient.services.WatchlistService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
            console.printMainMenu();
            menuSelection = console.promptForMenuSelection("Please select a service: ");
            if (menuSelection == 1) {
                handleCoinSearchAndSave();
            } else if (menuSelection == 2) {
                console.printDBEntries(coinService.listSavedCoins());
            } else if (menuSelection == 3) {
                watchlistMenu();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid selection.");
            }
            console.pause();
        }
    }

    private void watchlistMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            console.printWatchlistMenu();
            menuSelection = console.promptForMenuSelection("Select a list action: ");
            if (menuSelection == 1) {
                handleNewListCreation();
            } else if (menuSelection == 2) {
                handleListViewAndEditing();
            } else if (menuSelection == 3) {
                handleListDeletion();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid selection");
            }
            console.pause();
        }
    }

    private void handleCoinSearchAndSave() {
        try {
            String idSearch = console.promptForString("Search for a coin by id: ");
            Coin response = coinService.searchCoinOnGecko(idSearch);
            console.printCoinData(response, response.getApiId());
            String addToDB = console.promptForString("Add listing to database? (Y/N)");
            if (addToDB.equalsIgnoreCase("y")) {
                console.printSavedLists(listService.getAllLists());
                int listId = console.promptForMenuSelection("Select a list id to save the coin: ");
                handleNewListEntry(response, listId);
            } else if (addToDB.equalsIgnoreCase("n")) {
                return;
            }
        } catch (Exception e) {
            console.printError();
        }
    }

    private void handleNewListEntry(Coin coinToSave, int listId) {
        double price = coinToSave.getMarketData().getCurrentPrice().get("usd");
        CoinDTO newEntry = new CoinDTO(coinToSave.getSymbol(), coinToSave.getName(), price);
        Coin saved = coinService.getCoinDetails(coinService.saveCoin(newEntry).getCoinId());
        Watchlist w = listService.saveCoinToList(listId, saved.getCoinId());
        console.printCoinEntriesOnList(w);
    }

    private void handleListViewAndEditing() {
        Watchlist w = null;
        console.printSavedLists(listService.getAllLists());
        int selection = console.promptForMenuSelection("Select a list by id to review: ");
        try {
            w = listService.getByListId(selection);
        } catch (NumberFormatException e) {
            console.printError();
        }
        console.printCoinEntriesOnList(w);
        if (console.promptForString("Edit list entries Y/N? ").equalsIgnoreCase("y")) {
            int coinId = console.promptForMenuSelection("Coin id to remove: ");
            handleListEntryRemoval(w.getListId(), coinId);
        } else {
            return;
        }
    }

    private void handleNewListCreation() {
        String title = console.promptForString("Enter a name for the list: ");
        Watchlist newList = listService.createNewList(title);
        if (newList != null) {
            console.printCoinEntriesOnList(newList);
        }
    }

    private void handleListEntryRemoval(int listId, int coinId) {
        if (listService.deleteCoinOnList(listId, coinId)) {
            System.out.println("Coin successfully removed");
        } else {
            console.printError();
        }
    }

    private void handleListDeletion() {
        console.printSavedLists(listService.getAllLists());
        int listId = console.promptForMenuSelection("Select a list by id to delete: ");
        if (listService.deleteList(listId)) {
            System.out.println("Delete successful");
        } else {
            console.printError();
        }
    }
}




