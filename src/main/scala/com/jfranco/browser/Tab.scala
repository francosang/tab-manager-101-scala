package com.jfranco.browser

final case class Tab(
    id: Option[Tab.Id],
    index: Int,
    windowId: Window.Id,
    openerTabId: Option[Tab.Id],
    highlighted: Boolean,
    active: Boolean,
    pinned: Boolean,
    url: Option[String],
    title: Option[String],
    favIconUrl: Option[String],
    status: Option[Tab.Status],
    incognito: Boolean,
    width: Option[Int],
    height: Option[Int]
)

object Tab {

  type Status = String
  type Id     = Int

  object StatusValues {

    val LOADING: Status  = "loading"
    val COMPLETE: Status = "complete"

  }

  val dummyTabsList: List[Tab] = List[Tab](
    Tab(
      id = Some(1),
      index = 0,
      windowId = 1,
      openerTabId = Some(2),
      highlighted = false,
      active = false,
      pinned = false,
      url = Some("https://www.google.com"),
      title = Some("Google"),
      favIconUrl = Some("https://www.google.com/favicon.ico"),
      status = Some(Tab.StatusValues.COMPLETE),
      incognito = false,
      width = Some(800),
      height = Some(600)
    ),
    Tab(
      id = Some(2),
      index = 1,
      windowId = 1,
      openerTabId = Some(1),
      highlighted = false,
      active = false,
      pinned = false,
      url = Some("https://www.youtube.com"),
      title = Some("YouTube"),
      favIconUrl = Some("https://www.youtube.com/favicon.ico"),
      status = Some(Tab.StatusValues.COMPLETE),
      incognito = false,
      width = Some(800),
      height = Some(600)
    ),
    Tab(
      id = Some(3),
      index = 2,
      windowId = 1,
      openerTabId = Some(1),
      highlighted = false,
      active = false,
      pinned = false,
      url = Some("https://www.github.com"),
      title = Some("GitHub"),
      favIconUrl = Some("https://www.github.com/favicon.ico"),
      status = Some(Tab.StatusValues.COMPLETE),
      incognito = false,
      width = Some(800),
      height = Some(600)
    ),
    Tab(
      id = Some(4),
      index = 3,
      windowId = 1,
      openerTabId = Some(1),
      highlighted = false,
      active = false,
      pinned = false,
      url = Some("https://www.twitter.com"),
      title = Some("Twitter"),
      favIconUrl = Some("https://www.twitter.com/favicon.ico"),
      status = Some(Tab.StatusValues.COMPLETE),
      incognito = false,
      width = Some(800),
      height = Some(600)
    ),
    Tab(
      id = Some(5),
      index = 4,
      windowId = 1,
      openerTabId = Some(1),
      highlighted = false,
      active = false,
      pinned = false,
      url = Some("https://www.facebook.com"),
      title = Some("Facebook"),
      favIconUrl = Some("https://www.facebook.com/favicon.ico"),
      status = Some(Tab.StatusValues.COMPLETE),
      incognito = false,
      width = Some(800),
      height = Some(600)
    ),
    Tab(
      id = Some(6),
      index = 5,
      windowId = 1,
      openerTabId = Some(1),
      highlighted = false,
      active = false,
      pinned = false,
      url = Some("https://www.instagram.com"),
      title = Some("Instagram"),
      favIconUrl = Some("https://www.instagram.com/favicon.ico"),
      status = Some(Tab.StatusValues.COMPLETE),
      incognito = false,
      width = Some(800),
      height = Some(600)
    )
  ).flatMap(tab => List.fill(5)(tab))

}
