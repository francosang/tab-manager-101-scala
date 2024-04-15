package com.jfranco.impl.chrome.bindings

import com.jfranco.impl.chrome.{ChromeTab, ChromeTabQuery}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@JSGlobal("chrome.tabs")
@js.native
object Tabs extends js.Object {

  def query(
             queryInfo: ChromeTabQuery,
             callback: js.Function1[js.Array[ChromeTab], _]
  ): Unit = js.native

}
