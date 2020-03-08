package com.knoldus

import java.util.Date

import com.knoldus.controller.{TwitterQueryOperation,TwitterPost}
import com.knoldus.model.Connection
import org.mockito.MockitoSugar
import org.scalatest._

import scala.concurrent.Future

class TwitterQueryOperationSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val mockNameService:Connection = mock[Connection]
  val listOFUserPost = List(TwitterPost(1,new Date(2014,2,5),3,5),TwitterPost(1,new Date(2014,2,12),3,15)
    ,TwitterPost(1,new Date(2014,2,18),3,5),TwitterPost(1,new Date(2014,2,10),3,5),
    TwitterPost(1,new Date(2014,2,20),3,5))
  var welcomeService:TwitterQueryOperation = _

  override def beforeAll():Unit = {
    welcomeService = new TwitterQueryOperation(mockNameService)
  }



  "Method" should "getTwitterInstance" in {
    when(mockNameService.getTwitterInstance("   ")).thenReturn(listOFUserPost)

    val actualResult:Future[List[TwitterPost]] = welcomeService.getTweet(" ")

    val expectedResult = listOFUserPost
    actualResult.map(x=>assert(expectedResult.isInstanceOf[List[TwitterPost]] == x.isInstanceOf[List[TwitterPost]]))
  }


  "Method" should "getLength" in {
    val actualResult=welcomeService.getLength(Future(listOFUserPost))
    val expectedResult = 5
    actualResult.map(x=>assert(expectedResult == x))
  }

  "Method" should "getAverageLike" in {
    val actualResult=welcomeService.getAverageLike(Future(listOFUserPost))
    val expectedResult:Double = 3.0
    actualResult.map(x=>assert(expectedResult == x))
  }


  "Method" should "getReTweet" in {
    val actualResult=welcomeService.getReTweet(Future(listOFUserPost))
    val expectedResult =  7
    actualResult.map(x=>assert(expectedResult == x))

  }

  "Method" should "getAverageTweet" in {
    val actualResult:Future[Double] = welcomeService.getAverageTweet(Future{
      listOFUserPost
    })
    val expectedResult =  0.3333333333333333
    actualResult.map(x=>assert(expectedResult == x))
  }
}
