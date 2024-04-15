package com.jfranco

import cats.effect.*
import com.jfranco.browser.{Browser, Tab}
import tyrian.*
import tyrian.Html.*

import scala.scalajs.js
import scala.scalajs.js.annotation.*

sealed abstract class Action

object Action:
  case object NoOp                             extends Action
  final case class TabsLoaded(tabs: List[Tab]) extends Action

sealed abstract class Model
object Model:
  case object Empty                      extends Model
  final case class Tabs(tabs: List[Tab]) extends Model

@JSExportTopLevel("Main")
object Main extends TyrianIOApp[Action, Model]:

  val browser: Browser = Browser.chromeImpl

  def router: Location => Action = Routing.none(Action.NoOp)

  def init(flags: Map[String, String]): (Model, Cmd[IO, Action]) =
    (
      Model.Tabs(Tab.dummyTabsList),
      // Cmd.Run(browser.query())(it => Action.TabsLoaded(it)),
      Cmd.None
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
          case Model.Tabs(tabs) => tabs.map(tabItem)
      )
    )

  def header(): Html[Action] =
    div(
      cls := "text-sm font-medium text-center text-gray-500 border-b border-gray-200 bg-white dark:bg-gray-800 dark:text-gray-400 dark:border-gray-700"
    )(
      ul(
        cls := "flex items-center -mb-px w-full"
      )(
        headerItem(windowItem("All windows", selected = true)),
        headerItem(windowItem("Window 1", selected = false)),
        headerItem(windowItem("Window 2", selected = false)),
        headerItem(iconButton(plusOneIcon))
      )
    )

  def headerItem(content: Html[Action]): Html[Action] =
    div(cls := "inline-block")(
      content
    )

  def windowItem(label: String, selected: Boolean): Html[Action] =
    val classes =
      if selected then
        "rounded-t-lg text-blue-600 border-blue-600 active dark:text-blue-500 dark:border-blue-500"
      else
        "border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"

    div(
      cls := s"flex items-center space-x-2 py-2 pl-2 pr-1 border-b-2 $classes"
    )(
      a(
        href := "#"
      )(label),
      div(
        cls := "w-4 h-4 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-500 hover:text-white"
      )(closeIcon)
    )

  def closeIcon: Html[Action] =
    import tyrian.SVG.*

    svg(
      xmlns   := "http://www.w3.org/2000/svg",
      fill    := "currentColor",
      viewBox := "0 0 16 16"
    )(
      path(
        d := "M5.28 4.22a.75.75 0 0 0-1.06 1.06L6.94 8l-2.72 2.72a.75.75 0 1 0 1.06 1.06L8 9.06l2.72 2.72a.75.75 0 1 0 1.06-1.06L9.06 8l2.72-2.72a.75.75 0 0 0-1.06-1.06L8 6.94 5.28 4.22Z"
      )
    )

  def iconButton(icon: Html[Action]): Html[Action] =
    div(
      cls := "w-7 h-7 p-1 ml-3 font-light dark:text-white border border-gray-200 rounded-lg toggle-ring-gray-500 focus:outline-none dark:text-gray-400 dark:border-gray-600 hover:bg-gray-300 dark:hover:bg-gray-500 hover:text-white"
    )(
      icon
    )

  def menuIcon: Html[Action] =
    import tyrian.SVG.*

    svg(
      xmlns   := "http://www.w3.org/2000/svg",
      fill    := "currentColor",
      viewBox := "0 0 16 16"
    )(
      path(
        d := "M8 2a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM8 6.5a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM9.5 12.5a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0Z"
      )
    )

  def plusOneIcon: Html[Action] =
    import tyrian.SVG.*
    svg(
      xmlns   := "http://www.w3.org/2000/svg",
      fill    := "currentColor",
      viewBox := "0 0 16 16"
    )(
      path(
        d := "M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z"
      )
    )

  def duplicateIcon: Html[Action] =
    import tyrian.SVG.*
    svg(
      xmlns   := "http://www.w3.org/2000/svg",
      fill    := "currentColor",
      viewBox := "0 0 16 16"
    )(
      path(
        d := "M5.5 3.5A1.5 1.5 0 0 1 7 2h2.879a1.5 1.5 0 0 1 1.06.44l2.122 2.12a1.5 1.5 0 0 1 .439 1.061V9.5A1.5 1.5 0 0 1 12 11V8.621a3 3 0 0 0-.879-2.121L9 4.379A3 3 0 0 0 6.879 3.5H5.5Z"
      ),
      path(
        d := "M4 5a1.5 1.5 0 0 0-1.5 1.5v6A1.5 1.5 0 0 0 4 14h5a1.5 1.5 0 0 0 1.5-1.5V8.621a1.5 1.5 0 0 0-.44-1.06L7.94 5.439A1.5 1.5 0 0 0 6.878 5H4Z"
      )
    )

  def actionHeader(label: String): Html[Action] =
    button(
      cls := "h-7 px-2 font-light dark:text-white border border-gray-200 rounded-lg toggle-ring-gray-500 focus:outline-none dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
    )(label)

  def actionHeaderIcon(icon: String): Html[Action] =
    div(
      cls := s"h-12 w-10 bg-white"
    )(
      span(
        cls := s"$icon hover:text-gray-600 dark:hover:border-gray-900 dark:hover:text-gray-500"
      )()
    )

  def tabItem(tab: Tab): Html[Action] =
    div(
      cls := "flex items-center border-b bg-white dark:bg-gray-800 dark:border-gray-700 dark:hover:text-white dark:hover:bg-gray-700"
    )(
      // Icon
      img(
        src := tab.favIconUrl.getOrElse(""),
        cls := "w-4 h-4 m-2"
      ),
      // Title
      div(
        cls := "py-2 truncate font-light dark:text-white text-sm w-full"
      )(tab.title.getOrElse("")),
      // Actions aligned to the right
      div(cls := "flex pr-2 space-x-1")(
        // Windows button
        iconButton(menuIcon),
        // Duplicate button
        iconButton(duplicateIcon),
        // Close button
        iconButton(closeIcon)
      )
    )

  def actionTab(label: String): Html[Action] =
    button(
      cls := "w-7 h-7 font-light dark:text-white border border-gray-200 rounded-lg toggle-ring-gray-500 focus:outline-none dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-800"
    )(label)

  def subscriptions(model: Model): Sub[IO, Action] =
    Sub.None
