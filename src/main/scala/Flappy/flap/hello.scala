package Flappy.flap

import net.scage.ScageScreenApp
import net.scage.ScageLib._
import net.scage.support.Vec

object hello extends ScageScreenApp("Hello World") {
  private var ang = 0f
  actionDynamicPeriod({100}) {
    ang += 5
  }

  backgroundColor = BLACK
  render {
    openglMove(windowSize/2)
    openglRotate(ang)
    print("Hello World!", Vec(-50, -5), GREEN)
  }
}