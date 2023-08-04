package com.mycompany.tictactoeclient;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class PrimaryController implements Initializable, game.GameConstants{
    
    @FXML
    private Label playerStatus;
    @FXML
    private Label turnStatus;
    
    private GameGateway gateway;
    
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button21;
    @FXML
    private Button button22;
    @FXML
    private Button button23;
    @FXML
    private Button button31;
    @FXML
    private Button button32;
    @FXML
    private Button button33;
    
    
    @FXML
    private void row1col1(ActionEvent event) {
        int row = 0;
        int col = 0;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button11.setText(result);
        }
    }
    
    @FXML
    private void row1col2(ActionEvent event) {
        int row = 0;
        int col = 1;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button12.setText(result);
        }
    }
    
    @FXML
    private void row1col3(ActionEvent event) {
        int row = 0;
        int col = 2;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button13.setText(result);
        }
    }
    
    @FXML
    private void row2col1(ActionEvent event) {
        int row = 1;
        int col = 0;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button21.setText(result);
        }
    }
    
    @FXML
    private void row2col2(ActionEvent event) {
        int row = 1;
        int col = 1;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button22.setText(result);
        }
    }
    
    @FXML
    private void row2col3(ActionEvent event) {
        int row = 1;
        int col = 2;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button23.setText(result);
        }
    }
    
    @FXML
    private void row3col1(ActionEvent event) {
        int row = 2;
        int col = 0;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button31.setText(result);
        }
    }
    
    @FXML
    private void row3col2(ActionEvent event) {
        int row = 2;
        int col = 1;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button32.setText(result);
        }
    }
    
    @FXML
    private void row3col3(ActionEvent event) {
        int row = 2;
        int col = 2;
        String result = gateway.sendMove(row, col);
        if(!result.equals("")){
            button33.setText(result);
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gateway = new GameGateway(playerStatus, turnStatus);

        // Start the boardStatus thread
        new Thread(new boardCheck(gateway, button11,  button12,  button13,  button21,  button22,  button23,  button31,  button32,  button33)).start();
    }    
    
}

class boardCheck implements Runnable, game.GameConstants {
    private GameGateway gateway; // Gateway to the server
    private String board; // How many comments we have read
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button21;
    private Button button22;
    private Button button23;
    private Button button31;
    private Button button32;
    private Button button33;
    /** Construct a thread */
    public boardCheck(GameGateway gateway, Button button11, Button button12, Button button13, Button button21, Button button22, Button button23, Button button31, Button button32, Button button33) {
      this.gateway = gateway;
      this.board = this.gateway.getGameBoard();
      this.button11 = button11;
      this.button12 = button12;
      this.button13 = button13;
      this.button21 = button21;
      this.button22 = button22;
      this.button23 = button23;
      this.button31 = button31;
      this.button32 = button32;
      this.button33 = button33;
    }

    /** Run a thread */
    public void run() {
      gateway.getUser();
      while(true) {
          gateway.getTurn();
          String serverBoard = gateway.getGameBoard();
          if(!serverBoard.equals(board)) {
            board = serverBoard;
            
              char[] jo = board.toCharArray();
              int[][] newBoard = new int[3][3];
              for(int i=0; i<3; i++){
                  for(int j=0; j<3; j++){
                    // newboard[i][j] = jo[i*3+j]
                          newBoard[i][j] = jo[i*3 + j] - '0';
                  }
              }
              
              //go to every button and set text
              if(newBoard[0][0] == 1) {
                Platform.runLater(()-> button11.setText("X"));
              } else if(newBoard[0][0] == 2) {
                Platform.runLater(()-> button11.setText("O"));
              }
              if(newBoard[0][1] == 1) {
                Platform.runLater(()-> button12.setText("X"));
              } else if(newBoard[0][1] == 2) {
                Platform.runLater(()-> button12.setText("O"));
              }
              if(newBoard[0][2] == 1) {
                Platform.runLater(()-> button13.setText("X"));
              } else if(newBoard[0][2] == 2) {
                Platform.runLater(()-> button13.setText("O"));
              }
              if(newBoard[1][0] == 1) {
                Platform.runLater(()-> button21.setText("X"));
              } else if(newBoard[1][0] == 2) {
                Platform.runLater(()-> button21.setText("O"));
              }
              if(newBoard[1][1] == 1) {
                Platform.runLater(()-> button22.setText("X"));
              } else if(newBoard[1][1] == 2) {
                Platform.runLater(()-> button22.setText("O"));
              }
              if(newBoard[1][2] == 1) {
                Platform.runLater(()-> button23.setText("X"));
              } else if(newBoard[1][2] == 2) {
                Platform.runLater(()-> button23.setText("O"));
              }
              if(newBoard[2][0] == 1) {
                Platform.runLater(()-> button31.setText("X"));
              } else if(newBoard[2][0] == 2) {
                Platform.runLater(()-> button31.setText("O"));
              }
              if(newBoard[2][1] == 1) {
                Platform.runLater(()-> button32.setText("X"));
              } else if(newBoard[2][1] == 2) {
                Platform.runLater(()-> button32.setText("O"));
              }
              if(newBoard[2][2] == 1) {
                Platform.runLater(()-> button33.setText("X"));
              } else if(newBoard[2][2] == 2) {
                Platform.runLater(()-> button33.setText("O"));
              }
            //   for(int i=0; i<3; i++){
            //       for(int j=0; j<3; j++){
            //           if(newBoard[i][j] == 1){
            //               Platform.runLater(()-> currButton.setText("X"));
            //           } else if(newBoard[i][j] == 2){
            //               Platform.runLater(()-> currButton.setText("O"));
            //           }
            //       }
            //   }
              
            

          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
      }
    }
  }
