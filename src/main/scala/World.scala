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
      writeScore()
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
  
  def writeScore()
  {
    val scoreArray = new Array[Int](10)
    val writer = new PrintWriter(new FileWriter("scores.txt"))
    val reader = new BufferedReader(new FileReader("scores.txt"))
    var line = reader.readLine()
    val score = ObstacleCreator.point
    var i = 0
    while(line != null)
    {
      scoreArray(i) = line.toInt
      i += 1
      line = reader.readLine()
    }
    bubbleSort(scoreArray)
    if(score > scoreArray(0)) scoreArray(0) = score
    bubbleSort(scoreArray)
    for(i <- 0 until 9) writer.println(scoreArray(i))
    writer.close()
    reader.close()
  }
  
  def bubbleSort(a : Array[Int]) // Basic very bad search algorithm
   {
       var swapped = false;
       do{
         swapped = false;
         for(i <- 1 until a.length-1)
         {
            if(a(i-1) > a(i))
            {
               swapped = true;
               var temp = a(i);
               a(i) = a(i-1);
               a(i-1) = temp;
            }
         }
      } while(swapped)
   }
}





