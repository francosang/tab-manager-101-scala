package com.jfranco

import cats.effect.*
import tyrian.Html.*
import tyrian.*
import tyrian.cmds.*
import io.circe.generic.auto.*
import org.http4s.circe.CirceEntityCodec.*
import org.http4s.dom.FetchClientBuilder

import scala.scalajs.js.annotation.*

@JSExportTopLevel("Main")
object Main extends TyrianIOApp[Msg, Model]:

  def router: Location => Msg = Routing.none(Msg.NoOp)

  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (Model.init, Cmd.None)

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.UpdateRepo(r) =>
      (
        model,
        Cmd.SideEffect {
          IO.println(s"Repo updated to $r")
        } |+| Logger.info(r)
      )

    case Msg.FetchStars =>
      (
        model.copy(stargazersResult = "fetching..."),
        Http4sHelper.fetchStars(model.repo)
      )

    case Msg.Stars(res) =>
      (model.copy(stargazersResult = res), Cmd.None)

    case Msg.NoOp =>
      (model, Cmd.None)

  def view(model: Model): Html[Msg] =
    div(
      div(
        h1(cls := "text-3xl font-bold underline")("Http4s Stars"),
        p(cls := "underline")("How many stars?"),
        br,
        input(
          placeholder := "http4s/http4s",
          onInput(s => Msg.UpdateRepo(s))
        ),
        button(onClick(Msg.FetchStars))("Fetch")
      ),
      div(p(model.stargazersResult))
    )

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None

final case class Model(repo: String, stargazersResult: String)
object Model:
  val init: Model =
    Model("http4s/http4s", "")

enum Msg:
  case FetchStars
  case UpdateRepo(repo: String)
  case Stars(result: String)
  case NoOp

object Http4sHelper:

  final private case class Repo(stargazers_count: Int)

  def fetchStars(repoName: String): Cmd[IO, Msg] =

    val client = FetchClientBuilder[IO].create

    val fetchRepo: IO[String] =
      client
        .expect[Repo](s"https://api.github.com/repos/$repoName")
        .attempt
        .map {
          case Right(Repo(stars)) => s"$stars ★"
          case Left(_)            => s"Not found :("
        }

    Cmd.Run(IO.println(s"Fetching stars...") *> fetchRepo)(s => Msg.Stars(s))
