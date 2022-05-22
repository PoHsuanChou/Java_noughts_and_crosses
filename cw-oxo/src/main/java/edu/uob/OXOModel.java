package edu.uob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class OXOModel {
  private ArrayList<ArrayList<OXOPlayer>> cells;
  private ArrayList<OXOPlayer> players;
  private static int currentPlayerNumber;
  private OXOPlayer winner;
  private boolean gameDrawn;
  private static int winThreshold;

  public OXOModel(int numberOfRows, int numberOfColumns, int winThresh) {
    winThreshold = winThresh;
    cells = new ArrayList<ArrayList<OXOPlayer>>();

    for (int i = 0; i < numberOfRows; i++) {
      cells.add(new ArrayList<OXOPlayer>());
      for (int j = 0; j < numberOfRows; j++) {
        cells.get(i).add(null); // 初始填入空值
      }
    }
    players = new ArrayList<OXOPlayer>();


  }

  public int getNumberOfPlayers() {
    return players.size();
  }

  public void addPlayer(OXOPlayer player) {
        players.add(player);
  }

  public OXOPlayer getPlayerByNumber(int number) {
    return players.get(number);
  }

  public OXOPlayer getWinner() {
    return winner;
  }

  public void setWinner(OXOPlayer player) {
    winner = player;
  }

  public static int getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  public void setCurrentPlayerNumber(int playerNumber) {
    currentPlayerNumber = playerNumber;
  }

  public int getNumberOfRows() {
    return this.cells.size();
  }

  public int getNumberOfColumns() {
    return this.cells.get(0).size();
  }

  public OXOPlayer getCellOwner(int rowNumber, int colNumber) {

    return cells.get(rowNumber).get(colNumber);
  }

  public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {
    ArrayList<OXOPlayer> list = cells.get(rowNumber);
    list.set(colNumber,player);
  }

  public void setWinThreshold(int winThresh) {
    winThreshold = winThresh;
  }

  public int getWinThreshold() {
    return winThreshold;
  }

  public void setGameDrawn() {
    gameDrawn = true;
  }

  public boolean isGameDrawn() {
    return gameDrawn;
  }

  public void addRow(){
      int cols = getNumberOfColumns();
      ArrayList<OXOPlayer> list = new ArrayList<>();

      for(int i = 0; i < cols ; i ++){
        list.add(null);
      }
      cells.add(list);

  }

  public void addCol(){
    int rows = getNumberOfRows();
    for(int i = 0; i < rows; i ++){
      cells.get(i).add(null);
    }
  }

  public void removeRows(){
    if(getNumberOfRows() - 1 > 0){
      cells.remove(cells.size()-1);
    }
  }

  public void removeCols(){
    int rows = getNumberOfRows();
    if(getNumberOfColumns() -1 > 0){
      for(int i = 0; i < rows; i ++){
        cells.get(i).remove(cells.size()-1);
      }
    }

  }





}
