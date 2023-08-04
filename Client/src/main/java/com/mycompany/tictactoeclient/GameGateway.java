package com.mycompany.tictactoeclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameGateway implements game.GameConstants{
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private Label playerStatus;
    private Label turnStatus;
    private int playerNo;
    
    // Establish the connection to the server.
    public GameGateway(Label playerStatus, Label turnStatus) {
        this.playerStatus = playerStatus;
        this.turnStatus = turnStatus;
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            System.out.print("Exception in gateway constructor: " + ex.toString() + "\n");
        }
    }
    
    
    // user joins game
    public synchronized void getUser() {
        outputToServer.println(JOIN_GAME);
        outputToServer.flush();
        int userStatus = -1;
        try {
            userStatus = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            System.out.print("Error in getUser: " + ex.toString() + "\n");
        }
        switch(userStatus) {
              case PLAYER_ONE: {
                  playerNo = 1;
                  Platform.runLater(() -> playerStatus.setText("You are player X"));
                  break;
              }
              case PLAYER_TWO: {
                  playerNo = 2;
                  Platform.runLater(() -> playerStatus.setText("You are player O"));
                  break;
              }
              case GAME_FULL: {
                  playerNo = 0;
                  Platform.runLater(() -> playerStatus.setText("The game is currently full and unavailable"));
                  break;
              }
        }
        // return playerNo;
    }

    // Send a new comment to the server.
    public synchronized void getTurn() {
        outputToServer.println(WHOSE_TURN);
        outputToServer.flush();
        int userTurn = -1;
        try {
            userTurn = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            System.out.print("Error in getTurn: " + ex.toString() + "\n");
        }
        switch(userTurn) {
              case PLAYER_ONE: {
                  if(playerNo == 1){
                      Platform.runLater(() -> turnStatus.setText("Your turn"));
                  }
                  else{
                      Platform.runLater(() -> turnStatus.setText("Other player's turn"));
                  }
                  break;
              }
              case PLAYER_TWO: {
                  if(playerNo == 2){
                      Platform.runLater(() -> turnStatus.setText("Your turn"));
                  }
                  else{
                      Platform.runLater(() -> turnStatus.setText("Other player's turn"));
                  }
                  break;
              }
              case GAME_OVER: {
                  Platform.runLater(() -> turnStatus.setText("Game Over"));
                  break;
              }
        }
    }

    public synchronized String getGameBoard() {
        outputToServer.println(GAME_BOARD);
        outputToServer.flush();
        String board = "";
        try {
            board = inputFromServer.readLine();
        } catch (IOException ex) {
            System.out.print("Error in getGameBoard: " + ex.toString() + "\n");
        }
        return board;
    }

    public synchronized String sendMove(int row, int col) {
        
        if(playerNo == 1 || playerNo == 2){
            int playerCode = playerNo == 1? PLAYER_ONE:PLAYER_TWO;
            outputToServer.println(MAKE_MOVE);
            outputToServer.println(playerCode);
            outputToServer.println(row);
            outputToServer.println(col);
            outputToServer.flush();
        }
        else{
            Platform.runLater(() -> turnStatus.setText("You can't play because the game is full"));
        }
        int moveResult = -1;
        try {
            moveResult = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            System.out.print("Error in sendMove: " + ex.toString() + "\n");
        }
        switch(moveResult) {
              case ACCEPTED: {
                  if(playerNo == 1){
                      Platform.runLater(() -> turnStatus.setText("Your turn"));
                      return "X";
                  } else if(playerNo == 2){
                      Platform.runLater(() -> turnStatus.setText("Your turn"));
                      return "O";
                  }
                  break;
              }
              case REJECTED: {
                  break;
              }
        }
        return "";
    }

}
