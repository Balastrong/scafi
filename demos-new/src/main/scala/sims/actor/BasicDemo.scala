/*
 * Copyright (C) 2016-2017, Roberto Casadei, Mirko Viroli, and contributors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package sims.actor

import it.unibo.scafi.incarnations.BasicSimulationIncarnation.AggregateProgram
import it.unibo.scafi.simulation.gui.incarnation.scafi.bridge.SimulationInfo
import it.unibo.scafi.simulation.gui.incarnation.scafi.bridge.reflection.Demo
import it.unibo.scafi.simulation.gui.incarnation.scafi.configuration.ScafiProgramBuilder
import it.unibo.scafi.simulation.gui.incarnation.scafi.world.ScafiWorldInitializer.Grid
import it.unibo.scafi.simulation.gui.view.scalaFX.drawer.GradientFXOutput
import it.unibo.scafi.simulation.gui.view.scalaFX.drawer.StandardFXOutput
import it.unibo.scafi.simulation.gui.incarnation.scafi.bridge.actor.ActorPlatformInitializer.RadiusSimulation
//import it.unibo.scafi.simulation.gui.incarnation.scafi.bridge.ScafiSimulationInitializer.RadiusSimulation
import sims.SensorDefinitions

object BasicDemo extends App {
  ScafiProgramBuilder (
    Grid(4,100,100),
    SimulationInfo(program = classOf[BasicProgram]),
    RadiusSimulation(radius = 4),
    neighbourRender = false,
    outputPolicy = GradientFXOutput
  ).launch()
}
@Demo
class BasicProgram extends AggregateProgram with SensorDefinitions {
  //override def main(): Boolean = rep(false)(x => sense[Boolean]("sens1") | foldhoodPlus(false)(_|_)(nbr(x)))
  //override def main(): Int = foldhoodPlus(0)(_ + _)(50)
  //override def main() = rep(0)(_ + 1) // the aggregate program to run
  override def main() = branch(sense1) {1} {150}
}

