package com.example.connect;

import java.io.IOException;

import org.msgpack.MessagePack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.connect.gameManager.Game;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

@Bean
  public JedisConnectionFactory connectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, Game> redisTemplate() {
    RedisTemplate<String, Game> template = new RedisTemplate<String, Game>();
    template.setConnectionFactory(connectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    
    //http://msgpack.org/ this serialize to minimum size data.
    template.setValueSerializer(new RedisSerializer<Game>() {
      @Override
      public byte[] serialize(Game game) throws SerializationException {
        MessagePack msgpack = new MessagePack();
        try {
          return msgpack.write(game);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public Game deserialize(byte[] bytes) throws SerializationException {
        MessagePack msgpack = new MessagePack();
        try {
          return msgpack.read(bytes, Game.class);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
    return template;
  }
}

