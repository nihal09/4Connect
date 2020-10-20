package com.example.connect.gameManager;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.example.connect.general.CoinColor;
import com.example.connect.general.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Game implements Serializable{
	
	  private static final long serialVersionUID = 4595426754L;

	  public static final String OBJ_KEY = "GAME";

	  private String id;

	  private User user1;

	  private User user2;

	  private CoinColor[][] board = new CoinColor[6][7];

	  private int lastPlayedX = -1;

	  private int lastPlayedY = -1;

	  private Status status = Status.READY;

	  @JsonIgnore
	  private transient Lock lock;

	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public User getUser1() {
	    return user1;
	  }

	  public void setUser1(User user1) {
	    this.user1 = user1;
	  }

	  public User getUser2() {
		    return user2;
		  }

	  public void setUser2(User user2) {
		    this.user2 = user2;
		  }

	  public int getLastPlayedX() {
	    return lastPlayedX;
	  }

	  public void setLastPlayedX(int lastPlayedX) {
	    this.lastPlayedX = lastPlayedX;
	  }

	  public int getLastPlayedY() {
	    return lastPlayedY;
	  }

	  public void setLastPlayedY(int lastPlayedY) {
	    this.lastPlayedY = lastPlayedY;
	  }

	  public Lock getLock() {
	    return lock;
	  }

	  public void setLock(Lock lock) {
	    this.lock = lock;
	  }

	  @Override
	  public int hashCode() {
	    return new HashCodeBuilder().append(id).toHashCode();
	  }

	  @Override
	  public boolean equals(Object obj) {
	    return (obj instanceof Game) && this.id.equals(((Game) obj).id);
	  }

	  public CoinColor[][] getBoard() {
	    return board;
	  }

	  public void setBoard(CoinColor[][] board) {
	    this.board = board;
	  }

	  public Status getStatus() {
	    return status;
	  }

	  public void setStatus(Status status) {
	    this.status = status;
	  }

	  @JsonIgnore
	  public CoinColor getLastPlayedCoin() {
	    if (lastPlayedX == -1 && lastPlayedY == -1) {
	      return user2.getCoinColor();
	    }
	    return board[lastPlayedX][lastPlayedY];
	  }

	  public boolean calculateWinner() {
	    return validateRow() || validateColumn() || validateForwardDiagonal()
	        || validateReverseDiagonal();
	  }

	  private boolean validateRow() {
	    int start = (lastPlayedY - 3) > 0 ? lastPlayedY - 3 : 0;
	    int end = (lastPlayedY + 3) < 6 ? lastPlayedY + 3 : 6;
	    int count = 0;
	    for (int i = start; i <= end; i++) {
	      if (getLastPlayedCoin().equals(board[lastPlayedX][i])) {
	        count++;
	        if (count == 4) {
	          return true;
	        }
	      } else {
	        count = 0;
	      }
	    }
	    return false;
	  }

	  private boolean validateColumn() {
	    int start = (lastPlayedX - 3) > 0 ? lastPlayedX - 3 : 0;
	    int end = (lastPlayedX + 3) < 6 ? lastPlayedX + 3 : 5;
	    int count = 0;
	    for (int i = start; i <= end; i++) {
	      if (getLastPlayedCoin().equals(board[i][lastPlayedY])) {
	        count++;
	        if (count == 4) {
	          return true;
	        }
	      } else {
	        count = 0;
	      }
	    }
	    return false;
	  }

	  private boolean validateForwardDiagonal() {
	    int startX = lastPlayedX;
	    int startY = lastPlayedY;
	    for (int i = 0; i < 3; i++) {
	      if (startX == 0 || startY == 0) {
	        break;
	      }
	      startX--;
	      startY--;
	    }

	    int endX = lastPlayedX;
	    int endY = lastPlayedY;
	    for (int i = 0; i < 3; i++) {
	      if (endX == 5 || endY == 6) {
	        break;
	      }
	      endX++;
	      endY++;
	    }

	    int count = 0;
	    for (int i = startX, j = startY; i <= endX || j <= endY; i++, j++) {
	      if (getLastPlayedCoin().equals(board[i][j])) {
	        count++;
	        if (count == 4) {
	          return true;
	        }
	      } else {
	        count = 0;
	      }
	    }
	    return false;
	  }

	  private boolean validateReverseDiagonal() {
	    int startX = lastPlayedX;
	    int startY = lastPlayedY;
	    for (int i = 0; i < 3; i++) {
	      if (startX == 0 || startY == 6) {
	        break;
	      }
	      startX--;
	      startY++;
	    }

	    int endX = lastPlayedX;
	    int endY = lastPlayedY;
	    for (int i = 0; i < 3; i++) {
	      if (endX == 5 || endY == 0) {
	        break;
	      }
	      endX++;
	      endY--;
	    }

	    int count = 0;
	    for (int i = startX, j = startY; i <= endX || j >= endY; i++, j--) {
	      if (getLastPlayedCoin().equals(board[i][j])) {
	        count++;
	        if (count == 4) {
	          return true;
	        }
	      } else {
	        count = 0;
	      }
	    }
	    return false;
	  }

	  public void putDisc(CoinColor coinColor, int column) throws Exception {
	      if (board[0][column] != null) {
	        throw new Exception("No space for given column");
	      }
	      for (int i = 5; i >= 0; i--) {
	        if (board[i][column] == null) {
	          board[i][column] = coinColor;
	          this.lastPlayedX=i;
	          this.lastPlayedY=column;
	          break;
	        }
	    }
	  }

	
}
