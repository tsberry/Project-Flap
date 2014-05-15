import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
import java.io._
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
  var menu = new menu()
  var collide = false //boolean flag used for handling collisions
  var player = 1
  val sat_image = image("Sat.png", 70, 70,0,0,290,442)
  val thomas_image = image("Thomas.jpg", 70, 70,0,0,960,1280)
  val pruski_image = image("pruski.jpg", 70, 70,0,0,180,180)
  val neal_image = image("Neal.jpg", 70, 70,0,0,700,717)
  val scoreArray = Array(0,0,0,0,0,0,0,0,0,0)
  pause()
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
      //Flapper.toggle()
      //ObstacleCreator.toggle()
        getScores()
        writeScore()
    }
    }

  //Displays current score and will display the game-over message once the 
  //flapper has collided with an obstacle.
  interface{
    if(!collide && !(menu.menuIsOn || menu.scoresIsOn || menu.choosePlayer)) 
    print((ObstacleCreator.point).toInt, 7*(windowWidth/8), 7*(windowHeight/8),
     BLACK)
    if(collide)
      {
      print("Game over! Your score was " + ObstacleCreator.point + 
              " points", windowWidth/2-150, windowHeight/2+110, BLACK)
      print("Press r to restart, or press m to return to the menu.",
      windowWidth/2-230, windowHeight/2+70, BLACK)
      //print(scoreArray(0), windowWidth/2 - 200, windowHeight/2 - 20, BLACK)
      }
  }
  
  keyIgnorePause(KEY_R, onKeyDown =
  {
    if(collide && !(menu.menuIsOn || menu.scoresIsOn))
    {
    Flapper.reset()
    ObstacleCreator.reset()
    collide = false
    pauseOff()
    }
  })
  
  def getScores()
  {
    val reader = new BufferedReader(new FileReader("scores.txt"))
    var line = reader.readLine()
    //var i = 0
    for(i <- 0 until 10)
    {
      scoreArray(i) = line.toInt
     // i += 1
      line = reader.readLine()
    }
    bubbleSort(scoreArray)
    reader.close()
  }
  def writeScore()
  {
    val writer = new PrintWriter(new FileWriter("scores.txt"))
    val score = ObstacleCreator.point
    if(score > scoreArray(0)) scoreArray(0) = score
    bubbleSort(scoreArray)
    for(j <- 0 until 10) 
      {
      writer.println(scoreArray(j))
      }
    writer.close()
  }
  
  def bubbleSort(a : Array[Int])
  {
// Outer loop executes n-1 times (passes)
    for ( pass <- 1 until a.length)
    { 
      var swapped = false //checks to see if array is in order
// Go through the array and check each pair
// of consecutive elements; if they violate
// the ascending order condition, swap them
      for (i <- 0 until a.length - pass)//innermost loop executes on average n/2 times
      {
        if (a(i) > a(i + 1))
        { 
          swapped = true //array is not already in order
          //swap elements
          val temp = a(i)
          a(i) = a(i + 1)
          a(i + 1) = temp 
        }
      }
 // If array is already in order, we are done
      if (!swapped) return
    }
  }
}





