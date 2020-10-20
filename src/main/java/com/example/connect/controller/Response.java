package com.example.connect.controller;

import com.example.connect.gameManager.Game;
import com.example.connect.general.CoinColor;
import com.example.connect.general.Status;

public class Response {

	  private String gameId;
	  private CoinColor player1Color;
	  private String player1Id;
	  private CoinColor player2Color;
	  private String player2Id;
	  private Status status;
	  private String message;
	  private CoinColor[][] board;
	  
	  public Response(Game game) {
	    this.gameId = game.getId();
	    this.player1Color = game.getUser1().getCoinColor();
	    this.player2Color = game.getUser2() == null ? CoinColor.NA : game.getUser2().getCoinColor();
	    this.player1Id = game.getUser1().getUserId();
	    this.player2Id = game.getUser2() == null ? "NA":game.getUser2().getUserId();
	    this.status = game.getStatus();
	    this.board = game.getBoard();
	  }

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public CoinColor getPlayer1Color() {
		return player1Color;
	}

	public void setPlayer1Color(CoinColor player1Color) {
		this.player1Color = player1Color;
	}

	public String getPlayer1Id() {
		return player1Id;
	}

	public void setPlayer1Id(String player1Id) {
		this.player1Id = player1Id;
	}

	public CoinColor getPlayer2Color() {
		return player2Color;
	}

	public void setPlayer2Color(CoinColor player2Color) {
		this.player2Color = player2Color;
	}

	public String getPlayer2Id() {
		return player2Id;
	}

	public void setPlayer2Id(String player2Id) {
		this.player2Id = player2Id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CoinColor[][] getBoard() {
		return board;
	}

	public void setBoard(CoinColor[][] board) {
		this.board = board;
	}

}