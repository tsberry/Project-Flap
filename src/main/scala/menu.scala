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
      getScores()
      for(i <- 0 until 10) print(scoreArray(i), windowWidth/2, windowHeight/2 -20 - 20*i, BLACK)
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
  else if(collide)
  {
    collide = false
    Flapper.reset()
    ObstacleCreator.reset()
    pause()
    menuIsOn = true
  }
})
}