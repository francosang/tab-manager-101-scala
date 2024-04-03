package com.jfranco

import cats.effect.*
import tyrian.*
import tyrian.Html.*

import scala.scalajs.js
import scala.scalajs.js.annotation.*

@JSExportTopLevel("Main")
object Main extends TyrianIOApp[Action, Model]:

  def router: Location => Action = Routing.none(Action.NoOp)

  def init(flags: Map[String, String]): (Model, Cmd[IO, Action]) =
    (Model.Empty, Cmd.None)

  def update(model: Model): Action => (Model, Cmd[IO, Action]) =
    case Action.NoOp        => (model, Cmd.None)
    case Action.DoSomething => (doSomething(), Cmd.None)

  def doSomething(): Model =
    Model.Value("Tete")

  def view(model: Model): Html[Action] =
    div(
      div(
        h1(cls := "text-3xl font-bold underline")(
          s"Http4s Stars"
        ),
        button(onClick(Action.DoSomething))("Do something"),
        model match
          case Model.Empty        => p("")
          case Model.Value(value) => p(cls := "text-1xl")(value)
      )
    )

  def subscriptions(model: Model): Sub[IO, Action] =
    Sub.None

sealed abstract class Action
object Action:
  case object NoOp        extends Action
  case object DoSomething extends Action

sealed abstract class Model
object Model:
  case object Empty                     extends Model
  final case class Value(value: String) extends Model
