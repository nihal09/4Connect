package com.example.connect.repo;

import com.example.connect.gameManager.Game;

public interface GameRepo {

	  void saveGame(Game game);
	  
	  Game lockGet(String gameId) throws RuntimeException;

	  Game getGame(String id) throws RuntimeException;

	  void unlockGame(Game game);
	  
	 
}
