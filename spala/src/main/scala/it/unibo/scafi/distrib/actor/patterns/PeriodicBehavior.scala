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

package it.unibo.scafi.distrib.actor.patterns

import akka.actor.Actor
import it.unibo.scafi.distrib.actor.GoOn

import scala.concurrent.duration._

/**
 * Represents a behavior that marks some repetitive work via self messages
 * (by default [[GoOn]]). Defines a utility method for the purpose
 * ({{ScheduleNextWorkingCycle}}) and some template methods/hooks for lifecycle.
 */
trait LifecycleBehavior { thisVery: Actor =>
  def handleLifecycle(): Unit

  import context.dispatcher

  def scheduleNextWorkingCycle(delay: FiniteDuration, msg: Any = GoOn): Unit =
    context.system.scheduler.scheduleOnce(delay){ this.self ! msg }

  def lifecyclePreStart(): Unit = { }
  def lifecyclePostStop(): Unit = { }
}

/**
 * Represents a periodic or repeatedly-delayed behavior.
 *   - {{HandleLifecycle}} schedules the next job with a delay given by {{workInterval}
 *   - {{LifecyclePreStart}} handles the {{initialDelay}}
 */
trait PeriodicBehavior extends LifecycleBehavior { thisVery: Actor =>
  val initialDelay: Option[FiniteDuration] = None
  var workInterval: FiniteDuration

  def handleLifecycle(): Unit =
    scheduleNextWorkingCycle(workInterval)

  def scheduleNextWorkingCycle(): Unit =
    scheduleNextWorkingCycle(workInterval, GoOn)

  override def lifecyclePreStart(): Unit = {
    super.lifecyclePreStart()
    periodicBehaviorPreStart()
  }

  def periodicBehaviorPreStart(): Unit =
    initialDelay.foreach(scheduleNextWorkingCycle(_))
}
