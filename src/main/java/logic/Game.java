package logic;

import board.Board;
import dataManager.DataManager;
import player.*;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

public class Game {
    Display printer = new Display();
    Input input = new Input();

    public void initGame() {
        printer.welcomeMenu();
        String input = Input.getUserInput();
        switch (input) {
            case "1" -> startNewGame();
            case "2" -> displayHighScores();
            case "3" -> exitGame();
            default -> initGame();
        }

    }
    public Player determineWinner(Player player1, Player player2) {
        if (player1.isStillAlive()) {
            return player1;
        } else{
            return player2;
        }
    }
    public void saveScore(Player player){
        DataManager dataManager = new DataManager();
        dataManager.writeToFile(String.valueOf(player.getScore()),String.valueOf(player.getPlayerName()));
    }

    public void displayWinner(Player player){
        printer.consolePrint("Congrats "+player.getPlayerName() + " won the game witch "+player.getScore()+" points!");
    }
    public void startNewGame() {
        printer.gameTypeMenu();
        String input = Input.getUserInput();
        switch (input) {
            case "1" -> playerVsPlayer();
            case "2" -> playerVsAi();
            case "3" -> exitGame();
            default -> startNewGame();
        }
    }

    public void playerVsPlayer() {
        Board player1Board = new Board();
        Board player2Board = new Board();
        String player1Name = input.askForName();
        UserPlayer user1 = new UserPlayer(player1Name, player1Board);
        String player2Name = input.askForName();
        UserPlayer user2 = new UserPlayer(player2Name, player2Board);
        choseShipPlacement(player1Board,user1);
        choseShipPlacement(player2Board,user2);
        Board player1Guess = new Board();
        Board player2Guess = new Board();
        while(user1.isStillAlive() && user2.isStillAlive()){
            playerShooting(player2Board,player1Board, player1Guess,user1,user2);
            playerShooting(player1Board,player2Board, player2Guess,user2,user1);

        }
        Player winner = determineWinner(user1,user2);
        displayWinner(winner);
        saveScore(winner);
    }
    public void playerShooting(Board enemyBoard,Board userShips, Board userGuess, UserPlayer player, Player enemyPLayer){
        printer.consolePrint(player.getPlayerName()+"'s " + "Shooting phase now");
        printer.consolePrint("Your ships");
        printer.printBoard(userShips.getOcean());
        printer.consolePrint("Your guesses");
        printer.printBoard(userGuess.getOcean());
        player.handleShot(enemyBoard,userGuess,enemyPLayer);
        printer.consolePrint("Your guesses");
        printer.printBoard(userGuess.getOcean());
        printer.consolePrint("Your score");
        printer.consolePrint(String.valueOf(player.getScore()));

    }
    public void choseShipPlacement(Board board,Player userPlayer){
        printer.shipPlacementMenu();
        String input = Input.getUserInput();
        switch (input) {
            case "1" -> placeShipsManually(board,userPlayer);
            case "2" -> placeShipsRandomly(board,userPlayer);
            case "3" -> exitGame();
            default -> choseShipPlacement(board,userPlayer);
        }
    }

    private void placeShipsRandomly(Board board, Player userPLayer) {
        for(int i=0; i<ShipType.values().length;i++){
            board.randomPlacement(board,ShipType.values()[i],userPLayer);
        }
    }

    private void placeShipsManually(Board board, Player userPLayer) {
        for(int i=0; i<ShipType.values().length;i++){
            board.manualPlacement(board,ShipType.values()[i],userPLayer);
        }
    }

    public void playerVsAi() {
        printer.computerDifficultyMenu();
        String input = Input.getUserInput();
        switch (input) {
            case "1", "2", "3" -> playerVsComputer(Integer.parseInt(input));
            case "4" -> exitGame();
            default -> playerVsAi();
        }

    }

    private void playerVsComputer(int level){
        Board player1Board = new Board();
        Board player2Board = new Board();
        String player1Name = input.askForName();
        UserPlayer user1 = new UserPlayer(player1Name, player1Board);
        Player user2;
        switch (level) {
            case 2 -> user2 = new MediumComputer(player2Board);
            case 3 -> user2 = new HardComputer(player2Board);
            default -> user2 = new EasyComputer(player2Board);
        }
        choseShipPlacement(player1Board,user1);
        placeShipsRandomly(player2Board, user2);
        Board player1Guess = new Board();
        Board player2Guess = new Board();
        while(user1.isStillAlive() && user2.isStillAlive()){
            playerShooting(player2Board,player1Board, player1Guess,user1,user2);
            computerShooting(player1Board, player2Guess, user2, user1);

        }
        Player winner = determineWinner(user1,user2);
        displayWinner(winner);
        saveScore(user1);

    }

    public void computerShooting(Board enemyBoard, Board userGuess, Player player, Player enemyPLayer){
        printer.consolePrint(player.getPlayerName()+"'s " + "Shooting phase now");
        player.handleShot(enemyBoard,userGuess,enemyPLayer);
    }

    public static void displayHighScores() {
        DataManager readData = new DataManager();
        readData.bestScoreRead();
    }

    public void exitGame() {
        printer.consolePrint(("Ok, Bye!"));
        System.exit(0);
    }
}