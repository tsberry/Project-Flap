import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.support.physics.{Physical, ScagePhysics}
import com.github.dunnololda.scage.support.physics.objects.{DynaBox, StaticPolygon, DynaBall}
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._

object World extends ScageScreenApp("Project Flap", 800, 600){
  backgroundColor = BLACK
  val classroom = image("class.jpg", 800, 600, 0, 0, 2700, 1800)
  
  render{
    drawDisplayList(classroom, windowCenter)
  }
  
  val physics = ScagePhysics(gravity = Vec(0, 0))
  physics.addPhysical(flapper)

  var collide = false

  action {
    if(ObstacleCreator.botObstacles.length > 1) 
      {if(((flapper.coord.x - 15 >= (ObstacleCreator.botObstacles.last.coord.x - 300) && flapper.coord.x - 15 <= (ObstacleCreator.botObstacles.last.coord.x - 250)) 
          || (flapper.coord.x - 15>= (ObstacleCreator.botObstacles.last.coord.x -370) && flapper.coord.x - 15 <= (ObstacleCreator.botObstacles.last.coord.x - 320)))
        && !(flapper.coord.y - 31 > ObstacleCreator.botObstacles.dropRight(1).last.coord.y && 
            flapper.coord.y < (ObstacleCreator.botObstacles.dropRight(1).last.coord.y + 261))) collide = true
  }}
  
  action {
    ObstacleCreator.score()
    ObstacleCreator.continue()
    if(collide)
    {
      pause()
      deleteSelf()
    }
    }

  
  interface{
    if(!onPause) print((ObstacleCreator.point).toInt, 7*(windowWidth/8), 7*(windowHeight/8), BLACK)
    else print("Game over! Your score was " + ObstacleCreator.point + "points", windowWidth/2- 200, windowHeight/2, BLACK)
  }
 }

import World._

object flapper extends DynaBox(Vec(400, 300), 70, 70, 1, false){
  val flap_image = image("sat.png", 70, 70,0,0,290,442)
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
  var addedPoint = false
  
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
        else if(botObstacles.last.coord.x < 500) 
          {
          generateObstacles((math.random*400).toFloat)
          addedPoint = false
          }
  }
  
  def score()
  {
    if(botObstacles.length > 1)
    {
    if(flapper.coord.x >= botObstacles.dropRight(1).last.coord.x + 50 && !addedPoint)
    {
      point += 1
      addedPoint = true
    }
  }}
  
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
  }
  
  action {
    coord += Vec(-3, 0)
  }
  
  render {
    drawFilledRect(coord, this.box_width, this.box_height, BLUE)}
  }