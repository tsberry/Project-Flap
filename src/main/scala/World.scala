import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
/*
 Filename: World.scala

  Author(s):

  1) Neal Sharma - sharman - sharman@sandiego.edu
  
  2) Thomas Berry - tberry - tberry@sandiego.edu
 
  Date: 4/7/2014

  I pledge to demonstrate personal and academic integrity in all matters with my fellow

  students, the faculty, and the administration here at USD. I promise to be honest

  and accountable for my actions; and to uphold the statutes of scholastic honesty to

  better myself and those around me.
  
  */
  
  //The World class is responsible for displaying the background, displaying all text,
  //handling collisions, and rendering the flapper. Also, the World class calls many of 
  //the methods score(), and continue() that are define in the ObstacleCreator object.
object World extends ScageScreenApp("Project Flap", 800, 600){
    backgroundColor = BLACK
  val classroom = image("class.jpg", 800, 600, 0, 0, 2700, 1800)
  render{
    drawDisplayList(classroom, windowCenter) //displays the background
  }
  var flapper = new flapper()
  var ObstacleCreator = new ObstacleCreator()
  var flapX = flapper.coord.x
  var collide = false //boolean flag used for handling collisions

//The following checks to see if the flapper is within the accepted bounds to continue. If not, the boolean
//flag is raised, which will end the game.
  action {
    if(ObstacleCreator.botObstacles.length > 1) 
      {if((flapper.coord.x + (flapper.width/2) >= (ObstacleCreator.botObstacles.dropRight(1).last.coord.x) && flapper.coord.x + (flapper.width/2) <= (ObstacleCreator.botObstacles.dropRight(1).last.coord.x + 50) 
          || (flapper.coord.x - (flapper.width/2) >= (ObstacleCreator.botObstacles.dropRight(1).last.coord.x) && flapper.coord.x - (flapper.width/2) <= (ObstacleCreator.botObstacles.dropRight(1).last.coord.x + 50)))
        && !(flapper.coord.y - (flapper.height/2) > ObstacleCreator.botObstacles.dropRight(1).last.coord.y && 
            flapper.coord.y  + (flapper.height/2) < (ObstacleCreator.botObstacles.dropRight(1).last.coord.y + 300))) collide = true
  }}
  
  //The following updates the score, creates new objects, and checks if the 
  //collision boolean is true.
  action {
    if(!collide)
      {
      ObstacleCreator.score()
      ObstacleCreator.continue()
      }
    if(collide)
    {
      pause()
      //deleteSelf()//game over
    }
    }

  //Displays current score and will display the game-over message once the flapper has collided with
  //an obstacle
  interface{
    if(!collide) print((ObstacleCreator.point).toInt, 7*(windowWidth/8), 7*(windowHeight/8), BLACK)
    else print("Game over! Your score was " + ObstacleCreator.point + " points", windowWidth/2- 200, windowHeight/2, BLACK)
  }
  
  keyIgnorePause(KEY_A, onKeyDown =
  {
    flapper.reset()
    ObstacleCreator.reset()
    collide = false
    pauseOff()
  })
}

import World._

//The flapper object uses gravity that is implemented separately from Scage physics for 
//purposes of clarity. The object handles the appearance of the flapper and the behavior of
//the flapper (falling and flapping). The user can flap by using the space bar.

class flapper {
  val flap_image = image("Sat.png", 70, 70,0,0,290,442)
  val width = 70
  val height = 70
  var coord = Vec(400,300)
  var velocity = Vec(0,0)
  //While the space bar is pressed, a constant acceleration much greater than
  //gravity is applied to the flapper. However, once the user releases the
  //space bar, the velocity is reset to zero. This unrealistic portion of
  //the flapper behavior is meant to make the game more playable.
  key(KEY_SPACE, onKeyDown = 
      velocity += (Vec(0, 25)), onKeyUp = velocity = (Vec(0, 0)))
 
  render {
    if(!collide) drawDisplayList(flap_image, coord) //keeps flapper and its image in sync
  }
  
action {
    velocity += Vec(0, -.5) //gravity is a constant acceleration downward
    //Position changes by velocity each time the action callback is called.
    //This is consistent with physics
    coord += velocity 
  }

def reset()
{
  coord = Vec(400,300)
  velocity = Vec(0,0)
  }
}

//The ObstacleCreator object handles the creation of obstacles (obviously) and scoring as well.
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
  //If there are no obstacles, a new one is created. This occurs only at the beginning
  //of the game.
    if(botObstacles.length == 0) generateObstacles((math.random*300).toFloat)
        else if(botObstacles.last.coord.x < 500) 
          {
          generateObstacles((math.random*300).toFloat)//obstacle space has a random position
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
    val secondY = (windowHeight - height - 300) //constant spacing between top and bottom
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

//The Obstacle class defines the attribute of the obstacles. These attributes are the dimensions
//of the obstacle, the color of the obstacle, and the velocity of the obstacle. Obstacle must be
//a class because there are many instances of Obstacle used within the program.
class Obstacle(init_coord:Vec, height:Float)
{
  var coord = init_coord
  val width = 50
  var removed = false
  
  action {
    coord += Vec(-3, 0) //constant obstacle velocity
    if(removed) deleteSelf()
  }
  
  render {
  //all obstacles are blue
    if(!removed) drawFilledRect(coord, width, height, BLUE)
    }
}