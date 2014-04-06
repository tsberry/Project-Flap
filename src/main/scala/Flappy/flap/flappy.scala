package Flappy.flap

import net.scage.support.Vec
import collection.mutable.ArrayBuffer
import net.scage.support.physics.{Physical, ScagePhysics}
import net.scage.support.physics.objects.{DynaBox, StaticPolygon, DynaBall}
import net.scage.ScageScreenApp
import net.scage.ScageLib._

object flappy extends ScageScreenApp("Project Flap", 800, 600){
  backgroundColor = BLACK
  val physics = ScagePhysics(gravity = Vec(0, 0))
  physics.addPhysical(flapper)

//action {
  //if(flapper.velocity.x < 0) {
    //pause()
    //deleteSelf()
  //}
//}

  var collide = false
  
  keyIgnorePause(KEY_Q, onKeyDown = 
  {
   // if(onPause)
     // {
      restart()
      pauseOff()
      print("You Lose!", windowWidth/2, windowHeight/2, WHITE)
      //}
    })

  action {
    if(ObstacleCreator.botObstacles.length > 1) 
      {if(((flapper.coord.x - 15 >= (ObstacleCreator.botObstacles.last.coord.x - 300) && flapper.coord.x - 15 <= (ObstacleCreator.botObstacles.last.coord.x - 250)) || (flapper.coord.x - 15>= (ObstacleCreator.botObstacles.last.coord.x -340) && flapper.coord.x - 15 <= (ObstacleCreator.botObstacles.last.coord.x - 290)))
        && !(flapper.coord.y - 31 > ObstacleCreator.botObstacles.dropRight(1).last.coord.y && flapper.coord.y < (ObstacleCreator.botObstacles.dropRight(1).last.coord.y + 300))) collide = true
  }}
  
action {
    //ObstacleCreator.score()
    //physics.step()
    ObstacleCreator.continue()
    if(collide)
    {
      pause()
      deleteSelf()
    }
    }

  
  interface{
    if(!onPause) print((ObstacleCreator.point/150).toInt, windowWidth/2, windowHeight/2, WHITE)
}
  }

import flappy._

object flapper extends DynaBox(Vec(400, 300), 40, 40, 1, false){
  val flap_image = image("sat.png", 40, 40,0,0,290,442)
  init {
    coord = Vec(400, 300)
    val velocity = Vec(0, 0)
  }
  key(KEY_SPACE, onKeyDown = 
      velocity += (Vec(0, 25)), onKeyUp = velocity = (Vec(0, 0)))
 
  
  
  render {
    drawDisplayList(flap_image, coord)
  }
  
action {
    velocity += Vec(0, -.5)
    coord += velocity
  }
}

object ObstacleCreator
{
  val botObstacles = ArrayBuffer[Physical]()
  val topObstacles = ArrayBuffer[Physical]()
  var point = 0
  def addtopObstacle(obstacle:Physical)
  {
    physics.addPhysical(obstacle)
    topObstacles += obstacle
    if(topObstacles.length > 10) removetopObstacle()
    if(collide) topObstacles.remove(0, topObstacles.length-1)
  }
    def addbotObstacle(obstacle:Physical)
  {
    physics.addPhysical(obstacle)
    botObstacles += obstacle
    if(botObstacles.length > 10) removebotObstacle()
    if(collide) botObstacles.remove(0, botObstacles.length-1)
  }
  def removetopObstacle()
  {
    val obstacle = topObstacles.remove(0)
    physics.removePhysicals(obstacle)
  }
  
  def removebotObstacle()
  {
    val obstacle = botObstacles.remove(0)
    physics.removePhysicals(obstacle)
  }
  
  def continue()
  {
    if(botObstacles.length == 0) generateObstacles((math.random*400).toFloat)
        else if(botObstacles.last.coord.x < 500) generateObstacles((math.random*400).toFloat)
  }
  
  //def score()
  //{
   // if(botObstacles.length < 2) point = 0
   // else if(botObstacles.last.coord.x >= 650 && obstacles.last.coord.x <= 700) point += 1
 // }
  
  def generateObstacles(height:Float)
  {
    val secondY = (windowHeight - height - 300)
    val obst1 = new Obstacle(Vec(windowWidth, windowHeight), height)
    val obst2 = new Obstacle(Vec(windowWidth, secondY), windowHeight)
    addtopObstacle(obst1)
    addbotObstacle(obst2)
  }
  }

class Obstacle(init_coord:Vec, height:Float) extends DynaBox(init_coord, 50, height, 1, false)
{
  init {
    coord = init_coord
    //velocity = Vec(-100, 0)
  }
  action {
    coord += Vec(-1.5, 0)
  }
  render {
    drawRect(coord, this.box_width, this.box_height)}
  }