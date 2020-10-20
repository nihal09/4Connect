# REST API Connect 4 backend Application

This is a Backend API which plays Connect 4 with 2 users,where player chooses a number between 0 and 6 to drop coin and a winner is calculated.
1. API creates a game.
2. Tracks the game progress.
3. Display moves made by individual player.
4. Supports parallel game.
5. Supports Multiple games.
6. Calculates winner of the game.
7. Handles exceptions which will display appropriate message. For Example If more than 2 player try to join in a Game if will display "Game already has 2 players,there is no room for new player"

It uses a Redis server to store information.

It requires a Redis server to be running locally.

Redis server can be setup in MacOS/Linux using below doc:
https://redis.io/topics/quickstart

If you are using windows deploy it in docker 
`docker pull redis:latest`

Online Link : http://35.193.36.182:8080/connect4/start/player1

## Build

    gradle build

## Run

	gradle run
	
	or
	
	java -jar build/libs/4connect-0.0.1.jar
	
Or you can directly run :
	
	gradle boot run

### REST Services

#### Create Game
method: GET

http://localhost:8080/connect4/start/player1
	
#### Join Game(Player 2)
method: PUT

http://localhost:8080/connect4/{gameId}/join/player2


#### Play Game
method: PUT

http://localhost:8080/connect4/{GameId}/play/{PlayerId}/{ColumnNumber}


#### View/Restore Game information
method : GET

http://localhost:8080/connect4/{GameId}/info


#### Moves Made by a Player
method: GET

http://localhost:8080/connect4/{GameId}/{PlayerId}/column

It returns the position of coins in the board


