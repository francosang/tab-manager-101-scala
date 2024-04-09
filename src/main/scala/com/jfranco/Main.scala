package com.jfranco

import cats.effect.*
import tyrian.*
import tyrian.Html.*

import scala.concurrent.Promise
import scala.scalajs.js
import scala.scalajs.js.annotation.*
import scala.util.{Failure, Success, Try}

@JSExportTopLevel("Main")
object Main extends TyrianIOApp[Action, Model]:

  def router: Location => Action = Routing.none(Action.NoOp)

  def init(flags: Map[String, String]): (Model, Cmd[IO, Action]) =
    (
      Model.Empty,
      Cmd.Run(tabs.query(TabQuery()))(it => Action.TabsLoaded(it.toArray))
    )

  def update(model: Model): Action => (Model, Cmd[IO, Action]) =
    case Action.NoOp             => (model, Cmd.None)
    case Action.TabsLoaded(tabs) => (Model.Tabs(tabs), Cmd.None)

  def view(model: Model): Html[Action] =
    div(
      header(),
      div(
        model match
          case Model.Empty      => List(p("Loading..."))
          case Model.Tabs(tabs) => tabs.map(tabItem).toList
      )
    )

  def header(): Html[Action] =
    div(
      cls := "text-sm font-medium text-center text-gray-500 border-b border-gray-200 bg-white dark:bg-gray-800 dark:text-gray-400 dark:border-gray-700"
    )(
      ul(
        cls := "flex flex-wrap -mb-px w-full"
      )(
        headerItem("All windows", selected = true),
        headerItem("Window 1", selected = false),
        headerItem("Window 2", selected = false),
        li(
          cls := "me-2 flex items-center"
        )(
          actionHeader("+ window")
        ),
        li(
          cls := "me-2 flex items-center"
        )(
          actionHeader("+ tab")
        )
      )
    )

  def headerItem(label: String, selected: Boolean): Html[Action] =
    val classes =
      if selected then
        "inline-block py-3 pl-3 pr-1 rounded-t-lg text-blue-600 border-b-2 border-blue-600 active dark:text-blue-500 dark:border-blue-500"
      else
        "inline-block py-3 pl-3 pr-1 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"
    li(
      cls := "me-1"
    )(
      div(cls := classes)(
        a(
          cls  := "pr-2",
          href := "#"
        )(label),
        actionHeaderItem("x")
      )
    )

  def actionHeader(label: String): Html[Action] =
    button(
      cls := "h-7 px-2 font-light dark:text-white border border-gray-200 rounded-lg toggle-ring-gray-500 focus:outline-none dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
    )(label)

  def actionHeaderItem(label: String): Html[Action] =
    button(
      cls := "px-1 hover:text-white font-small rounded-full dark:hover:text-white dark:hover:bg-blue-500"
    )(label)

  def tabItem(tab: Tab): Html[Action] =
    div(
      cls := "flex items-center max-w-2xl border-b bg-white dark:bg-gray-800 dark:border-gray-700 dark:hover:text-white dark:hover:bg-gray-700"
    )(
      // Icon
      div(cls := "px-2 py-2 flex items-center")(
        img(
          src := tab.favIconUrl.getOrElse(""),
          cls := "w-4 h-4"
        )
      ),
      // Title
      div(
        cls := "py-2 truncate font-light dark:text-white text-sm w-full"
      )(tab.title.toOption.getOrElse("")),
      // Actions aligned to the right
      div(cls := "flex pr-2 space-x-1")(
        // Windows button
        actionTab("w"),
        // Duplicate button
        actionTab("d"),
        // Close button
        actionTab("x")
      )
    )

  def actionTab(label: String): Html[Action] =
    button(
      cls := "w-7 h-7 font-light dark:text-white border border-gray-200 rounded-lg toggle-ring-gray-500 focus:outline-none dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-800"
    )(label)

  def subscriptions(model: Model): Sub[IO, Action] =
    Sub.None

sealed abstract class Action
object Action:
  case object NoOp                              extends Action
  final case class TabsLoaded(tabs: Array[Tab]) extends Action

sealed abstract class Model
object Model:
  case object Empty                       extends Model
  final case class Tabs(tabs: Array[Tab]) extends Model

object tabs {

  def query(queryInfo: TabQuery): IO[Array[Tab]] = {
    val promise = Promise[Array[Tab]]()
    bindings.Tabs
      .query(
        queryInfo,
        js.Any.fromFunction1 { (tabs: js.Array[Tab]) =>
          promise.complete(lastErrorOrValue(tabs.toArray))
        }
      )

    IO.fromFuture(IO(promise.future)).timed.flatMap { case (time, value) =>
      IO.println(s"Duration: $time") *> IO.pure(value)
    }
  }

  def lastErrorOrValue[T](value: => T): Try[T] =
    Runtime.lastError match
      case Some(value) => Failure(new Exception(value.getMessage))
      case None        => Success(value)
}

object Runtime {
  def lastError: Option[Error] =
    // Apparently, bindings.Runtime.lastError.toOption could be Option(null)
    bindings.Runtime.lastError.toOption.flatMap(Option.apply)
}

@js.native
trait Tab extends js.Object {

  def id: js.UndefOr[Tab.Id] = js.native

  def index: Int = js.native

  def windowId: Window.Id = js.native

  def openerTabId: js.UndefOr[Tab.Id] = js.native

  def highlighted: Boolean = js.native

  def active: Boolean = js.native

  def pinned: Boolean = js.native

  def url: js.UndefOr[String] = js.native

  def title: js.UndefOr[String] = js.native

  def favIconUrl: js.UndefOr[String] = js.native

  def status: js.UndefOr[Tab.Status] = js.native

  def incognito: Boolean = js.native

  def width: js.UndefOr[Int] = js.native

  def height: js.UndefOr[Int] = js.native

}

object Tab {

  type Status = String
  type Id     = Int

  object StatusValues {

    val LOADING: Status  = "loading"
    val COMPLETE: Status = "complete"

  }

}

@js.native
trait Window extends js.Object {

  val id: js.UndefOr[Window.Id]       = js.native
  val focused: Boolean                = js.native
  val top: js.UndefOr[Int]            = js.native
  val left: js.UndefOr[Int]           = js.native
  val width: js.UndefOr[Int]          = js.native
  val height: js.UndefOr[Int]         = js.native
  val tabs: js.UndefOr[js.Array[Tab]] = js.native
  val incognito: Boolean              = js.native
  val `type`: js.UndefOr[Window.Type] = js.native
  val state: js.UndefOr[Window.State] = js.native
  val alwaysOnTop: Boolean            = js.native

}

object Window {

  type Id    = Int
  type Type  = String
  type State = String

}

@js.native
trait TabQuery extends js.Object {

  def active: js.UndefOr[Boolean] = js.native

  def pinned: js.UndefOr[Boolean] = js.native

  def highlighted: js.UndefOr[Boolean] = js.native

  def currentWindow: js.UndefOr[Boolean] = js.native

  def lastFocusedWindow: js.UndefOr[Boolean] = js.native

  def status: js.UndefOr[Tab.Status] = js.native

  def title: js.UndefOr[String] = js.native

  def url: js.UndefOr[js.Any] = js.native

  def windowId: js.UndefOr[Window.Id] = js.native

  def windowType: js.UndefOr[Window.Type] = js.native

  def index: js.UndefOr[Int] = js.native

}

object TabQuery {

  @SuppressWarnings(
    Array(
      "org.wartremover.warts.AsInstanceOf",
      ""
    )
  )
  def apply(
      active: js.UndefOr[Boolean] = js.undefined,
      pinned: js.UndefOr[Boolean] = js.undefined,
      highlighted: js.UndefOr[Boolean] = js.undefined,
      currentWindow: js.UndefOr[Boolean] = js.undefined,
      lastFocusedWindow: js.UndefOr[Boolean] = js.undefined,
      status: js.UndefOr[Tab.Status] = js.undefined,
      title: js.UndefOr[String] = js.undefined,
      url: js.UndefOr[js.Any] = js.undefined,
      windowId: js.UndefOr[Window.Id] = js.undefined,
      windowType: js.UndefOr[Window.Type] = js.undefined,
      index: js.UndefOr[Int] = js.undefined
  ): TabQuery =
    js.Dynamic
      .literal(
        active = active,
        pinned = pinned,
        highlighted = highlighted,
        currentWindow = currentWindow,
        lastFocusedWindow = lastFocusedWindow,
        status = status,
        title = title,
        url = url,
        windowId = windowId,
        windowType = windowType,
        index = index
      )
      .asInstanceOf[TabQuery]

}

package bindings {

  import scala.scalajs.js.UndefOr

  @JSGlobal("chrome.tabs")
  @js.native
  object Tabs extends js.Object {

    def query(
        queryInfo: TabQuery,
        callback: js.Function1[js.Array[Tab], _]
    ): Unit = js.native

  }

  @JSGlobal("chrome.runtime")
  @js.native
  object Runtime extends js.Object {

    def lastError: UndefOr[Error] = js.native

  }

}
