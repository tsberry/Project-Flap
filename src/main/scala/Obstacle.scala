import com.github.dunnololda.scage.support.Vec
import collection.mutable.ArrayBuffer
import com.github.dunnololda.scage.ScageScreenApp
import com.github.dunnololda.scage.ScageLib._
import World._
/*
 Filename: Obstacle.scala

  Author(s):

  1) Neal Sharma - sharman - sharman@sandiego.edu
  
  2) Thomas Berry - tberry - tberry@sandiego.edu
 
  Date: 5/6/2014

  I pledge to demonstrate personal and academic integrity in all matters with 
  
  my fellow students, the faculty, and the administration here at USD. I 
  
  promise to be honest and accountable for my actions; and to uphold the 
  
  statutes of scholastic honesty to better myself and those around me.
  
  */
//The Obstacle class defines the attribute of the obstacles. These attributes 
//are the dimensions of the obstacle, the color of the obstacle, and the 
//velocity of the obstacle.
class Obstacle(init_coord:Vec, height:Float)
{
  var coord = init_coord
  val width = 50
  var removed = false
  
  action {
    coord += Vec(-3, 0) //constant obstacle velocity
    if(removed) deleteSelf()
  }
  
  render {
  //all obstacles are blue
    if(!removed) drawFilledRect(coord, width, height, BLUE)
    }
}