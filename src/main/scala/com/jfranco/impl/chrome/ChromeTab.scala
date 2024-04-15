package com.jfranco.impl.chrome

import com.jfranco.browser.{Tab, Window}

import scala.scalajs.js

@js.native
trait ChromeTab extends js.Object {

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

object ChromeTab {
  def apply(tab: ChromeTab): Tab =
    Tab(
      id = tab.id.toOption,
      index = tab.index,
      windowId = tab.windowId,
      openerTabId = tab.openerTabId.toOption,
      highlighted = tab.highlighted,
      active = tab.active,
      pinned = tab.pinned,
      url = tab.url.toOption,
      title = tab.title.toOption,
      favIconUrl = tab.favIconUrl.toOption,
      status = tab.status.toOption,
      incognito = tab.incognito,
      width = tab.width.toOption,
      height = tab.height.toOption
    )
}
