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

action {
    ObstacleCreator.score()
    physics.step()
    ObstacleCreator.continue()
    
    }
  
  interface{
    if(!onPause) print((ObstacleCreator.point).toInt, windowWidth/2, windowHeight/2, WHITE)
}
  }

import flappy._

object flapper extends DynaBall(Vec(400, 300), radius = 20, 1, false){
  val flap_image = image("sat.png", 40, 40,0,0,290,442)
  init {
    coord = Vec(400, 300)
    velocity = Vec(0, 0)
  }
  key(KEY_SPACE, onKeyDown = 
      velocity += (Vec(0, 2000)), onKeyUp = velocity = (Vec(0, 0)))
     
  render {
    drawDisplayList(flap_image, coord)
  }
  
  action {
    velocity += Vec(0, -17)
  }
}

object ObstacleCreator
{
  val obstacles = ArrayBuffer[Physical]()
  var point = 0
  def addObstacle(obstacle:Physical)
  {
    physics.addPhysical(obstacle)
    obstacles += obstacle
    if(obstacles.length > 10) removeObstacle()
  }
  
  def removeObstacle()
  {
    val obstacle = obstacles.remove(0)
    physics.removePhysicals(obstacle)
  }
  
  def continue()
  {
    if(obstacles.length == 0) generateObstacles((math.random*600).toFloat)
        else if(obstacles.last.coord.x < 500) generateObstacles((math.random*400).toFloat)
  }
  
  def score()
  {
    if(obstacles.length < 2) point = 0
    else if(obstacles.last.coord.x == 700) point += 1
  }
  
  def generateObstacles(height:Float)
  {
    val secondY = (windowHeight - height - 200)
    val obst1 = new Obstacle(Vec(windowWidth, windowHeight), height)
    val obst2 = new Obstacle(Vec(windowWidth, secondY), secondY)
    addObstacle(obst1)
    addObstacle(obst2)
  }
  }

class Obstacle(init_coord:Vec, height:Float) extends DynaBox(init_coord, 50, height, 1, false)
{
  init {
    coord = init_coord
    velocity = Vec(-100, 0)
  }
  render {
    drawRect(coord, this.box_width, this.box_height)}
  }