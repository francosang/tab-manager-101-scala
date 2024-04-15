package com.jfranco.impl.chrome

import com.jfranco.browser.{Tab, TabQuery, Window}

import scala.scalajs.js

@js.native
trait ChromeTabQuery extends js.Object {

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

object ChromeTabQuery {

  def apply(tabQuery: TabQuery): ChromeTabQuery =
    import scala.scalajs.js.JSConverters.*

    ChromeTabQuery(
      active = tabQuery.active.orUndefined,
      pinned = tabQuery.pinned.orUndefined,
      highlighted = tabQuery.highlighted.orUndefined,
      currentWindow = tabQuery.currentWindow.orUndefined,
      lastFocusedWindow = tabQuery.lastFocusedWindow.orUndefined,
      status = tabQuery.status.orUndefined,
      title = tabQuery.title.orUndefined,
      url = tabQuery.url.orUndefined,
      windowId = tabQuery.windowId.orUndefined,
      windowType = tabQuery.windowType.orUndefined,
      index = tabQuery.index.orUndefined
    )

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
  ): ChromeTabQuery =
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
      .asInstanceOf[ChromeTabQuery]

}
