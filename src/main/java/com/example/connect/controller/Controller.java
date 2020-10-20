package com.example.connect.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import com.example.connect.general.CoinColor;
import com.example.connect.general.Status;
import com.example.connect.gameManager.Game;
import com.example.connect.gameManager.GameManager;

@RestController
@RequestMapping("/connect4")
public class Controller {

	@Autowired
	  private GameManager gameManager;

	  @RequestMapping("/start/player1")
	  public Response startNewGame() {
	    Game game = gameManager.createNewGame();
	    Response response = new Response(game);
	    response.setMessage("Game Created successfully,YELLOW player Joined");
	    return response;
	  }


	  @RequestMapping("/{gameId}/info")
	  public Game getGame(@PathVariable @NotNull String gameId) throws Exception {
	    Game game = gameManager.getGame(gameId);
	    return game;
	  }

	  @RequestMapping(path = "/{gameId}/join/player2",method=RequestMethod.PUT)
	  public Response joinGame(@PathVariable @NotNull String gameId) throws RuntimeException {
	    Game game = gameManager.joinGame(gameId);
	    Response response = new Response(game);
	    response.setMessage(game.getUser2().getUserId() + " joined the game " + gameId + " with coin color "+ game.getUser2().getCoinColor());
	    return response;
	  }

	  @RequestMapping(path="/{gameId}/play/{userId}/{column}",method=RequestMethod.PUT)
	  public Response playGame(@PathVariable @NotNull String gameId,
	      @PathVariable @NotNull String userId, @PathVariable  int column) throws Exception {
	    if(column<0 || column>6){
	      throw new IllegalArgumentException("Column value should be between 0 and 6");
	    }
	    Game game = gameManager.play(gameId, userId, column);
	    Response response = new Response(game);
	    CoinColor color = game.getLastPlayedCoin();
	    response.setMessage("Valid Move! "+ color + " dropped coin on column " + column + " for the game " + gameId);
	    if (Status.COMPLETED.equals(game.getStatus())) {
	      response.setMessage(response.getMessage() + " User " + color + " won the game!!");
	    }
	    return response;
	  }
	  
	  @RequestMapping("/{gameId}/{userId}/column")
	  public String playerColumns(@PathVariable @NotNull String gameId,@PathVariable @NotNull String userId) throws Exception {
		  String column=gameManager.playerColumns(gameId, userId);
		  return column;
	  }
}
