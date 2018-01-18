package it.unibo.scafi.simulation.gui.test.help

import it.unibo.scafi.simulation.gui.model.common.world.MetricDefinition.CartesianMetric
import it.unibo.scafi.simulation.gui.model.common.world.ObservableWorld
import it.unibo.scafi.simulation.gui.model.common.world.ObservableWorld.AllChangesObserver
import it.unibo.scafi.simulation.gui.model.core._
import it.unibo.scafi.simulation.gui.model.space.Point2D
import it.unibo.scafi.simulation.gui.pattern.observer.{Event, SimpleSource}

class BasicTestableObservableWorld extends ObservableWorld with SimpleSource {
  override type O = BasicTestableWorldObserver[NODE]
  override type B = Boundary[NODE]

  override type M = CartesianMetric[NODE#P]

  override val metric: M = new CartesianMetric[NODE#P]

  override val boundary: Option[B] = None
}
//NB! test are concetered on the word! there aren't checks on device!
class BasicTestableNode(override val id:Int,
                        override val position: Point2D,
                        private var device : Map[String,BasicTestableDevice]
                       ) extends Node{
  override type ID = Int

  override type P = Point2D

  override type SHAPE = Shape

  override type DEVICE = BasicTestableDevice

  override def shape: Option[SHAPE] = None

  override def devices: Set[DEVICE] = device.values.toSet

  override def getDevice(name: String): Option[BasicTestableDevice] = device get name
}

class BasicTestableDevice(override val name : String) extends Device {
  private var _enable = false
  override type NAME = String

  override type NODE <: Node
  override def node = None
  override def state: Boolean = _enable
}

class BasicTestableWorldObserver[N <: Node] extends AllChangesObserver[N] {
  private var _events = List[Event]()

  override def !!(event: Event): Unit = {
    super.!!(event)
    _events = event :: _events
  }

  def eventCount() = _events.size

  def clearQueue() = _events = List[Event]()

}