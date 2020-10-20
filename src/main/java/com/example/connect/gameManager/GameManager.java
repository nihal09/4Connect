package com.example.connect.gameManager;


public interface GameManager {

	  Game createNewGame();

	  Game getGame(String gameId) throws Exception;

	  Game play(String gameId, String userId, int column) throws Exception;
	  
	  Game joinGame(String gameId) throws RuntimeException;
	  
	  String playerColumns(String gameId, String userId) throws Exception;
	

}
