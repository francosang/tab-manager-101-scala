package com.jfranco.impl.chrome.bindings

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSGlobal

@JSGlobal("chrome.runtime")
@js.native
object Runtime extends js.Object {

  def lastError: UndefOr[Error] = js.native

}
