package com.knoldus.controller

import java.util.Date
import com.knoldus.model.Connection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class TwitterPost(id:Long,createdAt:Date,favouriteCount:Int,reTweetCount:Int)

class TwitterQueryOperation(connection: Connection) {



  def getTweet(hashTag: String): Future[List[TwitterPost]] = Future{
    connection.getTwitterInstance(hashTag)

  }.fallbackTo{Future{List.empty[TwitterPost]}}

  def getLength(tweets: Future[List[TwitterPost]]): Future[Int] = tweets.map(list=>list.length)

  def getAverageTweet(tweets: Future[List[TwitterPost]]): Future[Double] = Future{
    val listOfDate = tweets.map(list=>list.map(date=>date.createdAt.getDate))
    val sortedList =  listOfDate.map(_.sortWith((first,second)=>first<second))
    val twitterSize:Future[Double]=tweets.map(size=>size.length)
    sortedList.map(list=>list.reverse.head - list.head).map(dateDifference=>twitterSize.map(list=>list/dateDifference)).flatten

  }.flatten.fallbackTo{Future{-100}}

  def getAverageLike(tweets: Future[List[TwitterPost]]):Future[Double]=Future{
    val result=tweets.map(listUserPost=>listUserPost.map(userPost=>userPost.favouriteCount).sum)
    val twitterSize:Future[Double]=tweets.map(size=>size.length)
    val averageLike = result.map(average=>twitterSize.map(list=>average/list)).flatten
    averageLike
  }.flatten.fallbackTo{Future{-100}}

  def getReTweet(tweets:Future[List[TwitterPost]]):Future[Double]=Future{
    val result=tweets.map(listUserPost=>listUserPost.map(userPost=>userPost.reTweetCount).sum)
    val twitterSize:Future[Double]=tweets.map(size=>size.length)
     twitterSize.map(println)
      result.map(println)
    result.map(average=>twitterSize.map(list=>average/list)).flatten

  }.flatten.fallbackTo{Future{-100}}

}
