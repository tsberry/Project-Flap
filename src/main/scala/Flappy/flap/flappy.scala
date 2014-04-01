package Flappy.flap

import net.scage.support.Vec
import net.scage.support.physics.{Physical, ScagePhysics}
import net.scage.support.physics.objects.{DynaBox, StaticPolygon, DynaBall}
import net.scage.ScageScreenApp
import net.scage.ScageLib._

object flappy extends ScageScreenApp("Project Flap", 600, 800){
  backgroundColor = BLACK
val physics = ScagePhysics(gravity = Vec(0, -1000))
physics.addPhysical(flapper)
action {physics.step()}
}

import flappy._

object flapper extends DynaBall(Vec(400, 300), radius = 20, 1, true){
  val flap_image = image(".png", 40, 40,0,0,290,442)
  init {
    coord = Vec(150,400)
    velocity = Vec(0, 0)
  }
  key(KEY_SPACE, onKeyDown = 
      velocity += (Vec(0, 2000)), onKeyUp = velocity = (Vec(0, 0)))
     
  render {
    drawDisplayList(flap_image, coord)
  }
}