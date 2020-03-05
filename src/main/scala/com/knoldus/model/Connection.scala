package com.knoldus.model

import com.typesafe.config.ConfigFactory
import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

/**
 * this object can be used to get the instance of the Twitter through factory method
 */
class Connection {

  /**
   * method getTwitterInstance can be called to get the instance of Twitter
   *
   * @return the instance of type Twitter
   */
  def getTwitterInstance() : Twitter = {
    val config = ConfigFactory.load()

      println("heree")
    try{
      println("heree")
      val twitterInstance = new TwitterFactory().getInstance()
      twitterInstance.setOAuthConsumer(config.getString("consumer.key"),config.getString("consumer.secret"))
      twitterInstance.setOAuthAccessToken(new AccessToken(config.getString("token.key"),config.getString("token.secret")))
      println("heree")
      println(twitterInstance)
      twitterInstance
    }catch {
      case exception: Exception => println("heree")
                                 throw new Exception("failed to get twitter instance")
    }
  }


}