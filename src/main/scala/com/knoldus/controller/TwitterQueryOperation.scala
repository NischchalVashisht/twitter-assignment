package com.knoldus.controller

import com.knoldus.model.Connection
import twitter4j.{Query, Status}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class TwitterQueryOperation {

  
  
  def getTweet(hashTag: String): Future[List[Status]] = Future{
    val con = new Connection
    val resultInstance = con.getTwitterInstance()
    val query = new Query(hashTag)
    val result=resultInstance.search(query)
    result.getTweets.asScala.toList
  }.fallbackTo{Future{List.empty[Status]}}

  def getLength(tweets: Future[List[Status]]): Future[Int] = tweets.map(list=>list.length)

  def getAverageTweet(tweets: Future[List[Status]]): Future[Int] = Future{
    tweets.map(list=>list.length)
  }.flatten.fallbackTo{Future{-100}}

  def getAverageLike(tweets: Future[List[Status]]):Future[Double]=Future{
    val result=tweets.map(x=>x.map(y=>y.getFavoriteCount))
    tweets.map(x=>x.map(y=>y.getFavoriteCount).sum)
     println(result)
       result.map(x=>x.sum).map(ff=>ff/15.0)

  }.flatten.fallbackTo{Future{-100}}

  def getReTweet(tweets:Future[List[Status]]):Future[Double]=Future{
    val result=tweets.map(x=>x.map(y=>y.getRetweetCount))
    result.map(x=>x.sum).map(ff=>ff/15.0)

  }.flatten.fallbackTo{Future{-100}}

}



object Driver extends  App{
  val temp=new TwitterQueryOperation
  val instanceOfTweeter=temp.getTweet("#VIRAT")


  val result=for{
    numberOfTweet<-temp.getLength(instanceOfTweeter)
    averageLike<-temp.getAverageLike(instanceOfTweeter)
    averageRetweet<-temp.getReTweet(instanceOfTweeter)
  }yield (numberOfTweet,averageLike,averageRetweet)

  Thread.sleep(20000)
  result.map(println)

}
