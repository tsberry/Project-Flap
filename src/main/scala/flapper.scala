import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
import World._
/*
 Filename: Flapper.scala

  Author(s):

  1) Neal Sharma - sharman - sharman@sandiego.edu
  
  2) Thomas Berry - tberry - tberry@sandiego.edu
 
  Date: 5/6/2014

  I pledge to demonstrate personal and academic integrity in all matters with 
  
  my fellow students, the faculty, and the administration here at USD. I 
  
  promise to be honest and accountable for my actions; and to uphold the 
  
  statutes of scholastic honesty to better myself and those around me.
  
  */

//The flapper object uses gravity that is implemented separately from Scage 
//physics for purposes of clarity. The object handles the appearance of the
//flapper and the behavior of the flapper (falling and flapping). The user can
//flap by using the space bar.

class Flapper {
  val width = 70
  val height = 70
  var coord = Vec(400,300)
  var velocity = Vec(0,0)
  var pause = true
  //While the space bar is pressed, a constant acceleration much greater than
  //gravity is applied to the flapper. However, once the user releases the
  //space bar, the velocity is reset to zero. This unrealistic portion of
  //the flapper behavior is meant to make the game more playable.
  key(KEY_SPACE, onKeyDown = 
      velocity += (Vec(0, 25)), onKeyUp = velocity = (Vec(0, 0)))
 
  render {
    if(player == 1) drawDisplayList(sat_image, coord) //keeps flapper and its image in sync
    if(player == 2) drawDisplayList(thomas_image, coord)
    if(player == 3) drawDisplayList(neal_image, coord)  
    if(player == 4) drawDisplayList(pruski_image, coord)
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