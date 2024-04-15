package com.jfranco.browser

final case class TabQuery(
    active: Option[Boolean],
    pinned: Option[Boolean],
    highlighted: Option[Boolean],
    currentWindow: Option[Boolean],
    lastFocusedWindow: Option[Boolean],
    status: Option[Tab.Status],
    title: Option[String],
    url: Option[String],
    windowId: Option[Window.Id],
    windowType: Option[Window.Type],
    index: Option[Int]
)

object TabQuery {
  def apply(): TabQuery =
    TabQuery(None, None, None, None, None, None, None, None, None, None, None)
}
