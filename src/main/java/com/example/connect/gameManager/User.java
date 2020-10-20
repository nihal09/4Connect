package com.example.connect.gameManager;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.connect.general.CoinColor;


public class User implements Serializable {

	  private static final long serialVersionUID = 345654677L;

	  public static final String OBJ_KEY = "PLAYER";
	  
	  private String userId;
	  
	  private ArrayList<int[]> column;
	  
	  private CoinColor coinColor;

	  public String getUserId() {
	    return userId;
	  }

	  public void setUserId(String userId) {
	    this.userId = userId;
	  }

	  public CoinColor getCoinColor() {
	    return coinColor;
	  }
	  

	public ArrayList<int[]> getColumn() {
		return column;
	}

	public void setColumn( ArrayList<int[]> column) {
		this.column = column;
	}

	public void setCoinColor(CoinColor coinColor) {
	    this.coinColor = coinColor;
	  }

	}


