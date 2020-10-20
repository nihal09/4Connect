package com.example.connect.gameManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.connect.general.CoinColor;
import com.example.connect.general.Status;
import com.example.connect.repo.GameRepo;

@Component
public class ImplementGameManager implements GameManager {

	@Autowired
	  private GameRepo gameRepo;


	  @Override
	  public Game createNewGame() {
	    User user = new User();
	    Calendar cal= Calendar.getInstance();
	    user.setUserId( (UUID.randomUUID().toString().toUpperCase() + cal.getTimeInMillis()).toString());
	    user.setCoinColor(CoinColor.YELLOW);
	    Game game = new Game();
	    game.setId(UUID.randomUUID().toString());
	    ArrayList<int[]> arr= new ArrayList<int[]>();
	    arr.add(new int[] {});
	    user.setColumn(arr);
	    game.setUser1(user);
	    gameRepo.saveGame(game);
	    return game;
	  }

	  @Override
	  public Game getGame(String gameId) throws Exception {
	    Game game = gameRepo.getGame(gameId);
	    return game;
	  }

	  @Override
	  public Game joinGame(String gameId) throws RuntimeException {
	    Game game = null;
	    try {
	      game = gameRepo.lockGet(gameId);
	      Calendar cal= Calendar.getInstance();
	      if (game.getUser2() != null) {
	        throw new RuntimeException("Game already has 2 players,there is no room for new player");
	      }

	      User user = new User();
	      user.setUserId( (UUID.randomUUID().toString().toUpperCase() + cal.getTimeInMillis()).toString());
	      ArrayList<int[]> arr= new ArrayList<int[]>();
	      arr.add(new int[] {});
	      user.setColumn(arr);
	      CoinColor coinColorForUser2 =
	          game.getUser1().getCoinColor().equals(CoinColor.RED) ? CoinColor.YELLOW : CoinColor.RED;
	      user.setCoinColor(coinColorForUser2);
	      game.setUser2(user);
	      game.setStatus(Status.STARTED);
	      gameRepo.saveGame(game);
	    } finally {
	      gameRepo.unlockGame(game);
	    }
	    return game;
	  }
	  
	  @Override
	  public Game play(String gameId, String userId, int column) throws Exception {
	    Game game = null;
	    try {
	      //lock for race condition for same game
	      game = gameRepo.lockGet(gameId);
	      if (Status.READY.equals(game.getStatus())) {
	        throw new Exception("Game has only one player, one more required");
	      }
	      
	      if (!(game.getUser1().getUserId().equals(userId)
	          || game.getUser2().getUserId().equals(userId))) {
	        throw new Exception("No game found for the given userId");
	        
	      }
	      
	    
	      if (Status.COMPLETED.equals(game.getStatus())) {
	        throw new Exception("Game is already completed");
	      }
	      User player =
	          game.getUser1().getUserId().equals(userId) ? game.getUser1() : game.getUser2();
	      if (player.getCoinColor().equals(game.getLastPlayedCoin())) {
	    	  CoinColor color = player.getCoinColor() == CoinColor.YELLOW ? CoinColor.RED : CoinColor.YELLOW;
	        throw new Exception("Game should be played by "+ color +" player");
	      }
	      ArrayList<int[]> list = player.getColumn();
	      game.putDisc(player.getCoinColor(), column);
	      list.add(new int[] {game.getLastPlayedX(),game.getLastPlayedY()});
	      player.setColumn(list);
	      if (game.calculateWinner()) {
	        game.setStatus(Status.COMPLETED);
	      }
	      gameRepo.saveGame(game);
	    } finally {
	      gameRepo.unlockGame(game);
	    }
	    return game;
	  }
	  
	  @Override
	  public String playerColumns(String gameId, String userId) throws Exception{
		  Game game = gameRepo.getGame(gameId);
		  User player =
		          game.getUser1().getUserId().equals(userId) ? game.getUser1() : game.getUser2();
		  ArrayList<int[]> column = player.getColumn();
		  column.remove(0);
		  ObjectMapper objectMapper = new ObjectMapper();
		  String json = objectMapper.writeValueAsString(column);
		  return json;

	  }
}
