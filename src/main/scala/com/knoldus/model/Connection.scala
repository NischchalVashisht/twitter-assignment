package com.knoldus.model

import com.knoldus.controller.TwitterPost
import com.typesafe.config.ConfigFactory
import twitter4j.auth.AccessToken
import twitter4j.{Query,Twitter,TwitterFactory}

import scala.collection.JavaConverters._

/**
 * this object can be used to get the instance of the Twitter through factory method
 */
class Connection {

  var twitterInstance:Twitter = null

  /**
   * method getTwitterInstance can be called to get the instance of Twitter
   *
   * @return the instance of type Twitter
   */

  def getTwitterInstance(hashTag:String):List[TwitterPost] = {
    val config = ConfigFactory.load()

    /**
     *
     * @param iterableStatus List of Status coming from Twitter
     * @param userPost List of UserPost case Class
     * @return List of UserPost class
     */

    @scala.annotation.tailrec
    def createCaseClass(iterableStatus:List[twitter4j.Status],userPost:List[TwitterPost]):List[TwitterPost] = {

      iterableStatus match {
        case Nil => userPost
        case head :: Nil => userPost :+ TwitterPost(head.getId,head.getCreatedAt,head.getFavoriteCount,head.getRetweetCount)
        case head :: tail => createCaseClass(tail,userPost :+ TwitterPost(head.getId(),head.getCreatedAt,head.getFavoriteCount,head.getRetweetCount))
      }
    }


    try {
      println("heree")
      if (twitterInstance != null) {
        twitterInstance = new TwitterFactory().getInstance()
        twitterInstance.setOAuthConsumer(config.getString("consumer.key"),config.getString("consumer.secret"))
        twitterInstance.setOAuthAccessToken(new AccessToken(config.getString("token.key"),config.getString("token.secret")))
      }
      else {}
      val query = new Query(hashTag)
      val result = twitterInstance.search(query)
      val iter = result.getTweets.asScala.toList
      val finalResult = createCaseClass(iter,List())

      finalResult
    } catch {
      case exception:Exception => println("heree exceptuon")
        throw new Exception("failed to get twitter instance")
    }
  }


}