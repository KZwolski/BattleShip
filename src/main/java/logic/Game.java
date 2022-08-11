package logic;

import board.Board;
import dataManager.DataManager;
import player.*;
import ship.ShipType;
import utilities.Display;
import utilities.Input;

/**
 * Class responsible for handling all states of the game
 */
public class Game {
    /**
     * new instance of Display Class to handle displaying info to the user
     *
     * @see Display
     */
    Display printer = new Display();
    /**
     * new instance of Input class for handling user's input
     *
     * @see Input
     */
    Input input = new Input();

    /**
     * Method responsible for initializing game
     */
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

    /**
     * Method used to determine winner
     * @param player1 Instance of first player
     * @param player2 Instance of second player
     * @return instance of player that won the game
     */
    public Player determineWinner(Player player1, Player player2) {
        if (player1.isStillAlive()) {
            return player1;
        } else{
            return player2;
        }
    }

    /**
     * Method used for saving score
     *
     * @param player Instance of player that won
     */
    public void saveScore(Player player){
        DataManager dataManager = new DataManager();
        dataManager.writeToFile(String.valueOf(player.getScore()),String.valueOf(player.getPlayerName()));
    }

    /**
     * Method used for displaying the winner message
     *
     * @param player Instance of player that won
     */
    public void displayWinner(Player player){
        printer.consolePrint("Congrats "+player.getPlayerName() + " won the game witch "+player.getScore()+" points!");
    }

    /**
     * Method responsible for choosing game type
     */
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

    /**
     * Method responsible to handle player vs player game
     */
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

    /**
     * Method handling playerShooting phase
     * @param enemyBoard board with enemy's ships
     * @param userShips board with user's ships
     * @param userGuess board with user's guesses
     * @param player Instance of player that is shooting
     * @param enemyPLayer Instance of enemy player
     */
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

    /**
     * Method responsible for choosing ship placement
     * @param board board on which ships will be placed
     * @param userPlayer Instance of player that is placing ships
     */
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

    /**
     * Method responsible for placing ships randomly
     * @param board board on which ships will be placed
     * @param userPLayer Instance of player that is placing ships
     */
    private void placeShipsRandomly(Board board, Player userPLayer) {
        for(int i=0; i<ShipType.values().length;i++){
            board.randomPlacement(board,ShipType.values()[i],userPLayer);
        }
    }
    /**
     * Method responsible for placing ships manually
     * @param board board on which ships will be placed
     * @param userPLayer Instance of player that is placing ships
     */
    private void placeShipsManually(Board board, Player userPLayer) {
        for(int i=0; i<ShipType.values().length;i++){
            board.manualPlacement(board,ShipType.values()[i],userPLayer);
        }
    }

    /**
     * Method responsible for choosing game difficulty vs AI
     */
    public void playerVsAi() {
        printer.computerDifficultyMenu();
        String input = Input.getUserInput();
        switch (input) {
            case "1", "2", "3" -> playerVsComputer(Integer.parseInt(input));
            case "4" -> exitGame();
            default -> playerVsAi();
        }

    }

    /**
     * Method responsible for handling player vs computer game
     *
     * @param level number representing level of the computer player
     */

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

    /**
     * Method responsible for handling computer shooting phase
     *
     * @param enemyBoard board with enemy ships
     * @param userGuess board with computer's guesses
     * @param player Instance of computer player that is shooting
     * @param enemyPLayer Instance of enemy userPlayer
     */
    public void computerShooting(Board enemyBoard, Board userGuess, Player player, Player enemyPLayer){
        printer.consolePrint(player.getPlayerName()+"'s " + "Shooting phase now");
        player.handleShot(enemyBoard,userGuess,enemyPLayer);
    }

    /**
     * Method used for displaying high scores
     */
    public static void displayHighScores() {
        DataManager readData = new DataManager();
        readData.bestScoreRead();
    }

    /**
     * Method used for handling game exit 
     */

    public void exitGame() {
        printer.consolePrint(("Ok, Bye!"));
        System.exit(0);
    }
}