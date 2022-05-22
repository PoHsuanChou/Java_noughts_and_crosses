package edu.uob;

import java.util.Scanner;

import static edu.uob.OXOPlayer.*;
import static java.lang.Character.isDigit;

class OXOController {
  OXOModel gameModel;

  public OXOController(OXOModel model) {
    gameModel = model;
  }

  public void handleIncomingCommand(String command) throws OXOMoveException {
      int begin = gameModel.getCurrentPlayerNumber();
      if(command.equals("y")){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter number: ");
        String newPlayer = scanner.nextLine();
        if(!Character.isLetter(newPlayer.charAt(0)) && !isDigit(newPlayer.charAt(0))){
          System.out.println("Invalid Identifier Character");
        }

        gameModel.addPlayer(new OXOPlayer(newPlayer.charAt(0)));
        return;
      }


      OXOPlayer currentPlayer = gameModel.getPlayerByNumber(gameModel.getCurrentPlayerNumber());
      try{
        if(!Character.isLetter(command.charAt(0)) ){
          System.out.println("Invalid Identifier Character");
          throw new OXOMoveException.InvalidIdentifierCharacterException(OXOMoveException.RowOrColumn.ROW,command.charAt(0));
        }
        if(!isDigit(command.charAt(1))){
            System.out.println("Invalid Identifier Character");
          throw new OXOMoveException.InvalidIdentifierCharacterException(OXOMoveException.RowOrColumn.COLUMN,command.charAt(1));
        }
        int row = (int)Character.toLowerCase(command.charAt(0)) - 'a';
        int col = (int)command.charAt(1) - '0'-1;
//        int row = Integer.valueOf(command.charAt(0));
//        int col = Integer.valueOf(command.charAt(1));
        int[] arr = {row,col};
        if(gameModel.getWinner() != null){
          System.out.println("Game over!");
        }

        else if(row>=gameModel.getNumberOfRows() || row < 0){
          System.out.println("Row or Col out of range!");
          throw new OXOMoveException.OutsideCellRangeException(OXOMoveException.RowOrColumn.ROW,row);
        }
        else if(col>= gameModel.getNumberOfColumns() || col< 0){
          System.out.println("Row or Col out of range!");
          throw new OXOMoveException.OutsideCellRangeException(OXOMoveException.RowOrColumn.COLUMN,col);
        }

        else if(gameModel.getCellOwner(row, col) != null){
          System.out.println("Someone already here!");
          throw new OXOMoveException.CellAlreadyTakenException(row,col);
        }
        else if(arr.length != 2){
          System.out.println("Input type error");
          throw new OXOMoveException.OutsideCellRangeException.InvalidIdentifierLengthException(arr.length);
        }
        else{

          if(begin == gameModel.getNumberOfPlayers() - 1){
        OXOPlayer current_player = gameModel.getPlayerByNumber(begin);
        gameModel.setCellOwner(row, col,current_player);
        gameModel.setCurrentPlayerNumber(0);
          }
          else{
        OXOPlayer current_player = gameModel.getPlayerByNumber(begin);
        gameModel.setCellOwner(row, col,current_player);
        gameModel.setCurrentPlayerNumber(begin+1);
          }

        if(checkHorizontally() || checkVertically() || checkDiagonally() ){
          gameModel.setWinner(currentPlayer);
        }
        if(checkHorizontally_Threshold() || checkVertically_Threshold() || checkDiagonally_Threshold()){
          gameModel.setWinner(currentPlayer);
        }

        }

      }catch (NumberFormatException e) {
        System.out.println("Index must be Integer");
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    if(IsFullFill()){
      gameModel.setGameDrawn();
    }



  }


  public boolean checkHorizontally() {
    OXOPlayer player = null;
    for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
      for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
        // check first column
        if (j == 0) {
          if (gameModel.getCellOwner(i, j) == null) // null break
          {
            break;
          } else {
            player = gameModel.getCellOwner(i, j);
          }
        }
        // if first column has data, check all of them are the same
        if (player != null) {
          if (player != gameModel.getCellOwner(i, j)) // different break
          {
            break;
          } else if (j == gameModel.getNumberOfColumns() - 1) // all same player -> true
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean checkVertically() {
    OXOPlayer player = null;
    for (int i = 0; i < gameModel.getNumberOfColumns(); i++) {
      for (int j = 0; j < gameModel.getNumberOfRows(); j++) {
        // check first row
        if (j == 0) {
          if (gameModel.getCellOwner(j, i) == null) // null break
          {
            break;
          } else {
            player = gameModel.getCellOwner(j, i);
          }
        }
        // if first row has data, check all of them are the same
        if (player != null) {
          if (player != gameModel.getCellOwner(j, i)) // different break
          {
            break;
          } else if (j == gameModel.getNumberOfRows() - 1) // all same player -> true
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean checkDiagonally() {
    // row = column
    if (gameModel.getNumberOfRows() != gameModel.getNumberOfColumns()) {
      return false;
    }
    // check upper left
    boolean flag1 = false;
    if (gameModel.getCellOwner(0, 0) != null) {
      flag1 = true;
      OXOPlayer player1 = gameModel.getCellOwner(0, 0);
      for (int i = 1; i < gameModel.getNumberOfRows(); i++) {
        if (player1 != gameModel.getCellOwner(i, i)) {
          flag1 = false;
          break;
        }
      }
    }
    // check top right
    boolean flag2 = false;
    if (gameModel.getCellOwner(0, gameModel.getNumberOfColumns() - 1) != null) {
      flag2 = true;
      OXOPlayer player2 = gameModel.getCellOwner(0, gameModel.getNumberOfColumns() - 1);
      for (int i = 1; i < gameModel.getNumberOfRows(); i++) {
        if (player2 != gameModel.getCellOwner(i, gameModel.getNumberOfRows() - 1 - i)) {
          flag2 = false;
          break;
        }
      }
    }
    return flag1 || flag2;
  }
  public boolean checkHorizontally_Threshold(){
    for(int i = 0; i < gameModel.getNumberOfRows(); i ++){
      OXOPlayer player = null;
      int count = 0;
      for(int j = 0; j < gameModel.getNumberOfColumns();j ++){
        if(player == null && gameModel.getCellOwner(i,j) != null){
          player = gameModel.getCellOwner(i,j);
          count ++;
        }
        else if(player != null&& gameModel.getCellOwner(i,j) == player){
          player = gameModel.getCellOwner(i,j);
          count++;
        }
        else if(player != null && gameModel.getCellOwner(i,j) != player && gameModel.getCellOwner(i,j) != null){
          player = gameModel.getCellOwner(i,j);
          count = 1;
        }
        else if(player != null && gameModel.getCellOwner(i,j) == null){
          player = null;
          count = 0;
        }
        if(count == gameModel.getWinThreshold()){
          return true;
        }
      }
    }
    return false;
  }

  public boolean checkVertically_Threshold() {
    for(int i = 0; i < gameModel.getNumberOfColumns(); i ++){
      OXOPlayer player = null;
      int count = 0;
      for(int j = 0; j < gameModel.getNumberOfRows();j++){
        if(player == null && gameModel.getCellOwner(j,i) != null){
          player = gameModel.getCellOwner(j,i);
          count ++;
        }
        else if(player != null && player == gameModel.getCellOwner(j,i)){
          player = gameModel.getCellOwner(j,i);
          count ++;
        }
        else if(player!= null && player != gameModel.getCellOwner(j,i) && gameModel.getCellOwner(j, i) != null){
          player = gameModel.getCellOwner(j,i);
          count = 1;
        }
        else if(player != null && gameModel.getCellOwner(j,i) == null){
          player = null;
          count = 0;
        }
        if(count == gameModel.getWinThreshold()){
          return true;
        }

      }
    }
    return false;
  }

  public boolean checkDiagonally_Threshold() {
    for (int i = 0; i < gameModel.getNumberOfRows() - 1; i++) {
      for(int j = 0 ; j < gameModel.getNumberOfColumns() - 1 ; j++){
        OXOPlayer player = null;
        int count = 0;
        int row = i;
        int col = j;
        while(row < gameModel.getNumberOfRows() && col < gameModel.getNumberOfColumns()){
          // init data
          //System.out.println(row + ", " + col);
          if (player == null && gameModel.getCellOwner(row, col) != null) {
            player = gameModel.getCellOwner(row, col);
            count += 1;
          } // same player
          else if (player != null && player == gameModel.getCellOwner(row, col)) {
            count += 1;
          } // change player
          else if (player != null && gameModel.getCellOwner(row, col) != null && player != gameModel.getCellOwner(row, col)) {
            player = gameModel.getCellOwner(row, col);
            count = 1;
          } // next is null
          else if (player != null && gameModel.getCellOwner(row, col) == null) {
            player = null;
            count = 0;
          }
          row++; col++;
          if (count == gameModel.getWinThreshold()) {
            return true;
          }
        }
      }
    }
    for (int i = 0 ; i < gameModel.getNumberOfRows() - 1; i++) {
      for(int j = gameModel.getNumberOfColumns() - 1 ; j > 0 ; j--){
        OXOPlayer player = null;
        int count = 0;
        int row = i;
        int col = j;
        while(row < gameModel.getNumberOfRows() && col > -1){
          // init data
          //System.out.println(row + ", " + col);
          if (player == null && gameModel.getCellOwner(row, col) != null) {
            player = gameModel.getCellOwner(row, col);
            count += 1;
          } // same player
          else if (player != null && player == gameModel.getCellOwner(row, col)) {
            count += 1;
          } // change player
          else if (player != null && gameModel.getCellOwner(row, col) != null && player != gameModel.getCellOwner(row, col)) {
            player = gameModel.getCellOwner(row, col);
            count = 1;
          } // next is null
          else if (player != null && gameModel.getCellOwner(row, col) == null) {
            player = null;
            count = 0;
          }
          row++; col--;
          if (count == gameModel.getWinThreshold()) {
            return true;
          }
        }
      }
    }
    return false;
  }







  public void addRow() {
    gameModel.addRow();
  }
  public void removeRow() {
    gameModel.removeRows();
  }
  public void addColumn() {
    gameModel.addCol();
  }
  public void removeColumn() {
    gameModel.removeCols();
  }
  public void increaseWinThreshold() {
    int Threshold = gameModel.getWinThreshold();
    gameModel.setWinThreshold(Threshold + 1);
    System.out.println("WinThreshold : " + gameModel.getWinThreshold());

  }
  public void decreaseWinThreshold() {
    int Threshold = gameModel.getWinThreshold();
    gameModel.setWinThreshold(Threshold - 1);
    System.out.println("WinThreshold : " + gameModel.getWinThreshold());
  }

  public boolean IsFullFill() {
    for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
      for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
        if (gameModel.getCellOwner(i, j) == null) {
          return false;
        }
      }
    }
    return true;
  }



}
