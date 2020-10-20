package com.example.connect.repo;

import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import com.example.connect.gameManager.Game;

@Component
public class RedisGameRepo implements GameRepo {
	
	@Autowired
	  private RedisTemplate<String, Game> template;
	 
	  /**
	   * Used for concurrency management by distributed locking
	   */
	  private LockRegistry redisLockRegistry;

	  @Override
	  public void saveGame(Game game) {
	    template.opsForHash().put(game.getId(), Game.OBJ_KEY, game);
	  }

	  @Override
	  public Game getGame(String id) throws RuntimeException {
	    Game game = (Game) template.opsForHash().get(id, Game.OBJ_KEY);
	    if (game == null) {
	      throw new RuntimeException("Game with id " + id + " is not found");
	    }
	    return game;
	  }

	  @Override
	  public Game lockGet(String gameId) throws RuntimeException {
	    Lock lock = redisLockRegistry.obtain(gameId);
	    lock.lock();
	    Game game = getGame(gameId);
	    game.setLock(lock);
	    return game;
	  }

	  @Override
	  public void unlockGame(Game game) {
	    if (game!=null && game.getLock() != null) {
	      game.getLock().unlock();
	    }
	  }

	  @PostConstruct
	  public void initRegistry() {
	    this.redisLockRegistry =
	        new RedisLockRegistry(template.getConnectionFactory(), Game.OBJ_KEY);
	  }

	  public void setRedisLockRegistry(LockRegistry redisLockRegistry) {
	    this.redisLockRegistry = redisLockRegistry;
	  }

}
