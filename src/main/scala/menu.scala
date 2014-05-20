import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
import java.io._
import World._

class menu {
var menuIsOn = true
var scoresIsOn = false
var choosePlayer = false
var player2Unlocked = false
var player3Unlocked = false
var player4Unlocked = false
val pic = image("menu.jpg", 800, 600, 0, 0, 800, 600)
render
{
  if(menuIsOn || scoresIsOn || choosePlayer) drawDisplayList(pic, windowCenter)
  if(choosePlayer) 
  {
    drawDisplayList(sat_image, Vec(windowWidth/2-300, windowHeight/2))
    if(player2Unlocked)
      drawDisplayList(thomas_image, Vec(windowWidth/2-100, windowHeight/2))
    else
      drawFilledRect(Vec(windowWidth/2-135, windowHeight/2+35), 70, 70, BLACK)
    if(player3Unlocked)
      drawDisplayList(neal_image, Vec(windowWidth/2+100, windowHeight/2))
    else
      drawFilledRect(Vec(windowWidth/2+65, windowHeight/2+35), 70, 70, BLACK)
    if(player4Unlocked)
    drawDisplayList(pruski_image, Vec(windowWidth/2+300, windowHeight/2))
    else
      drawFilledRect(Vec(windowWidth/2+265, windowHeight/2+35), 70, 70, BLACK)
  } 
}

interface
{
  if(menuIsOn) 
  {
    print("Welcome to Flappy Sat!", windowWidth/2-95, windowHeight/2+140, 
    BLACK)
    print("Press s to start playing, press h to look at your highscores,", 
    windowWidth/2-245, windowHeight/2, BLACK)
    print("or p to choose your player.", 
    windowWidth/2-108, windowHeight/2-70, BLACK)
  }
  if(scoresIsOn) 
    {
      print("Highscores:", windowWidth/2-50, windowHeight/2+140, BLACK)
      getScores()
      for(i <- 0 until 10) print(scoreArray(scoreArray.length-1-i), 
      windowWidth/2-10, 
      windowHeight/2 +120 - 20*i, BLACK)
      print("Press h again to return to the main menu.", windowWidth/2-165, 40, 
      BLACK)
    }
  if(choosePlayer)
  {
    print("Please choose your player.", windowWidth/2-100, windowHeight/2+140, 
    BLACK)
    print("Press p again to return to the main menu.", windowWidth/2-180, 
    40, BLACK)
    print("Sat - Press 1", windowWidth/2-350, windowHeight/2-60)
    if(player2Unlocked)
      print("Thomas - Press 2", windowWidth/2-165, windowHeight/2-60)
    else
      print("Locked. Score 20!", windowWidth/2-165, windowHeight/2-60) 
    if(player3Unlocked)
      print("Neal - Press 3", windowWidth/2+40, windowHeight/2-60)
    else
      print("Locked. Score 40!", windowWidth/2+40, windowHeight/2-60)
    if(player4Unlocked)
      print("Pruski - Press 4", windowWidth/2+235, windowHeight/2-60)
    else
      print("Locked. Score 60!", windowWidth/2+235, windowHeight/2-60)
    if(player == 1)
      print("Sat selected!", windowWidth/2-350, windowHeight/2+60)
    if(player == 2)
      print("Thomas selected!", windowWidth/2-165, windowHeight/2+60)
    if(player == 3)
      print("Neal selected!", windowWidth/2+40, windowHeight/2+60)
    if(player == 4)
      print("Pruski selected!", windowWidth/2+235, windowHeight/2+60)
  }    
}

keyIgnorePause(KEY_H, onKeyDown =
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

keyIgnorePause(KEY_S, onKeyDown =
{
  if(menuIsOn)
  {
    menuIsOn = false
    pauseOff()
  }
})

keyIgnorePause(KEY_M, onKeyDown =
{
  if(collide)
  {
    collide = false
    Flapper.reset()
    ObstacleCreator.reset()
    pause()
    menuIsOn = true
  }
})

keyIgnorePause(KEY_P, onKeyDown =
{
  getScores()
  if(scoreArray(9) >= 20)
    player2Unlocked = true
  if(scoreArray(9) >= 40)
    player3Unlocked = true
  if(scoreArray(9) >= 60)
    player4Unlocked = true
  if(choosePlayer)
  {
    choosePlayer = false
    menuIsOn = true
  }
  else if(menuIsOn)
  {
    menuIsOn = false
    choosePlayer = true
  }
})

  keyIgnorePause(KEY_1, onKeyDown =
  {
  if(choosePlayer)
    player = 1
  })
  keyIgnorePause(KEY_2, onKeyDown =
  {
  if(choosePlayer)
    if(player2Unlocked)
      player = 2
  })
  keyIgnorePause(KEY_3, onKeyDown =
  {
  if(choosePlayer)
    if(player3Unlocked)
      player = 3
  })
  keyIgnorePause(KEY_4, onKeyDown =
  {
  if(choosePlayer)
    if(player4Unlocked)
      player = 4
  })

}