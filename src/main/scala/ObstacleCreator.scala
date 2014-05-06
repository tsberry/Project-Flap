import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
import World._
/*
 Filename: ObstacleCreator.scala

  Author(s):

  1) Neal Sharma - sharman - sharman@sandiego.edu
  
  2) Thomas Berry - tberry - tberry@sandiego.edu
 
  Date: 5/6/2014

  I pledge to demonstrate personal and academic integrity in all matters with 
  
  my fellow students, the faculty, and the administration here at USD. I 
  
  promise to be honest and accountable for my actions; and to uphold the 
  
  statutes of scholastic honesty to better myself and those around me.
  
  */
//The ObstacleCreator object handles the creation of obstacles (obviously) and 
//scoring as well.
class ObstacleCreator
{
  var botObstacles = ArrayBuffer[Obstacle]() //handles bottom obstacles
  var topObstacles = ArrayBuffer[Obstacle]() //handles top obstacles
  var point = 0 //score counter
  var addedPoint = false //boolean flag used in scoring
  
  //adds a top obstacle to the ArrayBuffer and adds physics to it.
  def addtopObstacle(obstacle:Obstacle)
  {
    topObstacles += obstacle
    //once the obstacle is off the screen it is removed
    if(topObstacles.length > 5) removetopObstacle()
  }
  
  //adds a bottom obstacle to the ArrayBuffer and adds physics to it.
  def addbotObstacle(obstacle:Obstacle)
  {
    botObstacles += obstacle
    //once the obstacle is off the screen it is removed.
    if(botObstacles.length > 5) removebotObstacle()
  }
  
  //removes a top obstacle from the beginning of the ArrayBuffer
  def removetopObstacle()
  {
    val obst = topObstacles.remove(0)
    obst.removed = true
  }
  
  //removes a bottom obstacle from the beginning of the ArrayBuffer
  def removebotObstacle()
  {
    val obst = botObstacles.remove(0)
    obst.removed = true
  }
  
  //generates a new obstacle once the most recent obstacle has moved 300
  //pixels to the left
  def continue()
  {
  //If there are no obstacles, a new one is created. This occurs only at the 
  //beginning of the game.
    if(botObstacles.length == 0) generateObstacles((math.random*300).toFloat)
        else if(botObstacles.last.coord.x < 500) 
          {
          generateObstacles((math.random*300).toFloat)//obstacle space has a 
                                                      //random position
          addedPoint = false
          }
  }
  
  //The score is updated by one point when the flapper passes through an
  //obstacle
  def score()
  {
    if(botObstacles.length > 1)
    {
    if(flapX >= botObstacles.dropRight(1).last.coord.x + 50 && !addedPoint)
    {
      point += 1
      addedPoint = true
    }
  }}
  
  //creates a new pair of obstacles based on the position passed as a parameter
  def generateObstacles(height:Float)
  {
    val secondY = (windowHeight - height - 300) //constant spacing between top 
                                                                  //and bottom
    addtopObstacle(new Obstacle(Vec(windowWidth, windowHeight), height))
    addbotObstacle(new Obstacle(Vec(windowWidth, secondY), windowHeight))
  }
  
  def reset()
  {
    for(i <- 0 until botObstacles.length)
      {
        removebotObstacle()
        removetopObstacle()
      }
    point = 0
    addedPoint = false
  }
  }