import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
/*
 Filename: World.scala

  Author(s):

  1) Neal Sharma - sharman - sharman@sandiego.edu
  
  2) Thomas Berry - tberry - tberry@sandiego.edu
 
  Date: 5/6/2014

  I pledge to demonstrate personal and academic integrity in all matters with 
  
  my fellow students, the faculty, and the administration here at USD. I 
  
  promise to be honest and accountable for my actions; and to uphold the 
  
  statutes of scholastic honesty to better myself and those around me.
  
  */
  
  //The World class is responsible for displaying the background, displaying 
  //all text, handling collisions, and rendering the flapper. Also, the World
  //class calls many of the methods score(), and continue() that are define in
  //the ObstacleCreator object.
object World extends ScageScreenApp("Project Flap", 800, 600){
    backgroundColor = BLACK
  val classroom = image("class.jpg", 800, 600, 0, 0, 2700, 1800)
  render{
    drawDisplayList(classroom, windowCenter) //displays the background
  }
  var Flapper = new Flapper()
  var ObstacleCreator = new ObstacleCreator()
  var flapX = Flapper.coord.x
  var collide = false //boolean flag used for handling collisions

//The following checks to see if the flapper is within the accepted bounds to 
//continue. If not, the boolean flag is raised, which will end the game.
  action {
    if(ObstacleCreator.botObstacles.length > 1)//given more than one obstacle 
      {
      if((Flapper.coord.x + (Flapper.width/2) >= 
      (ObstacleCreator.botObstacles.dropRight(1).last.coord.x) && 
      Flapper.coord.x + (Flapper.width/2) <= 
      (ObstacleCreator.botObstacles.dropRight(1).last.coord.x + 50) 
          || (Flapper.coord.x - (Flapper.width/2) >= 
          (ObstacleCreator.botObstacles.dropRight(1).last.coord.x) && 
          Flapper.coord.x - (Flapper.width/2) <= 
          (ObstacleCreator.botObstacles.dropRight(1).last.coord.x + 50)))
        && !(Flapper.coord.y - (Flapper.height/2) > 
        ObstacleCreator.botObstacles.dropRight(1).last.coord.y && 
            Flapper.coord.y  + (Flapper.height/2) < 
            (ObstacleCreator.botObstacles.dropRight(1).last.coord.y + 300))) 
            collide = true
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
      pause()//game over
    }
    }

  //Displays current score and will display the game-over message once the 
  //flapper has collided with an obstacle.
  interface{
    if(!collide) 
    print((ObstacleCreator.point).toInt, 7*(windowWidth/8), 7*(windowHeight/8),
     BLACK)
    else print("Game over! Your score was " + ObstacleCreator.point + 
    " points", windowWidth/2- 200, windowHeight/2, BLACK)
  }
  
  keyIgnorePause(KEY_A, onKeyDown =
  {
    Flapper.reset()
    ObstacleCreator.reset()
    collide = false
    pauseOff()
  })
}





