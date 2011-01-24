package pl.softwaremill.traffic

import org.joda.time._

import Span._
import Acceleration._

object Model

case class Speed(metersPerSecond: Span) {
  def this(meters: Span, perPeriod: Period) = {
    this(meters / perPeriod.toDurationFrom(new DateTime).getStandardSeconds)
  }

  def *(period: Period): Span = {
    metersPerSecond * (period.toDurationFrom(new DateTime).getMillis.toDouble / 1000)
  }
}

trait VehicleSpecification {
  val width: Span
  val length: Span
  val acceleration: Acceleration
}

object TypicalCar extends VehicleSpecification {
  val width = 1710 millimeters
  val length = 4490 millimeters
  val acceleration = 2.6.meters.perSecondSquared
}

case class Vehicle(vs: VehicleSpecification, x: Span, y: Span, direction: Direction, currentSpeed: Speed) {
  def move(period: Period) = {
    val distance = currentSpeed * period
    val xDistance = distance * direction.cos
    val yDistance = distance * direction.sin
    Vehicle(vs, x + xDistance, y + yDistance, direction, currentSpeed)
  }
}