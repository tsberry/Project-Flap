import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
import java.io._
import World._

class menu {
var menuIsOn = true
var scoresIsOn = false
val pic = image("menu.jpg", 800, 600, 0, 0, 800, 600)
val scores = Array[Int](10)
render
{
  if(menuIsOn || scoresIsOn) drawDisplayList(pic, windowCenter)
}

interface
{
  if(menuIsOn) print("This is the menu", windowWidth/2, windowHeight/2, BLACK)
  if(scoresIsOn) 
    {
      print("These are the scores", windowWidth/2, windowHeight/2, BLACK)
      readScores()
      for(i <- 0 until 9) print(scores(i), windowWidth/2, windowHeight/2 - 20*i, BLACK)
    }
}

keyIgnorePause(KEY_D, onKeyDown =
{
  if(menuIsOn)
  {
    scoresIsOn = true
    menuIsOn = false
  }
  else if(scoresIsOn)
  {
    menuIsOn = true
    scoresIsOn = false
  }
})

keyIgnorePause(KEY_J, onKeyDown =
{
  if(menuIsOn)
  {
    menuIsOn = false
    pauseOff()
  }
})

def readScores()
{
  val reader = new BufferedReader(new FileReader("scores.txt"))
  var line = reader.readLine()
  var i = 0
  while(i < 10)
  {
    scores(i) = line.toInt
    i += 1
    line = reader.readLine()
  }
  reader.close()
}

}