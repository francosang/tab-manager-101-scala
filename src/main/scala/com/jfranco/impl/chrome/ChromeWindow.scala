package com.jfranco.impl.chrome

import com.jfranco.browser.Window

import scala.scalajs.js

@js.native
trait ChromeWindow extends js.Object {

  val id: js.UndefOr[Window.Id]             = js.native
  val focused: Boolean                      = js.native
  val top: js.UndefOr[Int]                  = js.native
  val left: js.UndefOr[Int]                 = js.native
  val width: js.UndefOr[Int]                = js.native
  val height: js.UndefOr[Int]               = js.native
  val tabs: js.UndefOr[js.Array[ChromeTab]] = js.native
  val incognito: Boolean                    = js.native
  val `type`: js.UndefOr[Window.Type]       = js.native
  val state: js.UndefOr[Window.State]       = js.native
  val alwaysOnTop: Boolean                  = js.native

}
