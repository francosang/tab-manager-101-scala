package com.jfranco.browser

final case class Window(
    id: Option[Window.Id],
    focused: Boolean,
    top: Option[Int],
    left: Option[Int],
    width: Option[Int],
    height: Option[Int],
    tabs: Option[List[Tab]],
    incognito: Boolean,
    `type`: Option[Window.Type],
    state: Option[Window.State],
    alwaysOnTop: Boolean
)

object Window {

  type Id    = Int
  type Type  = String
  type State = String

}
