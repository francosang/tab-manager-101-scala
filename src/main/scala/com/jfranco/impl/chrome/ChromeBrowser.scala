package com.jfranco.impl.chrome

import cats.effect.IO

import scala.concurrent.Promise
import scala.scalajs.js
import scala.util.{Failure, Success, Try}

object ChromeBrowser {

  def query(queryInfo: ChromeTabQuery): IO[Array[ChromeTab]] = {
    val promise = Promise[Array[ChromeTab]]()
    com.jfranco.impl.chrome.bindings.Tabs
      .query(
        queryInfo,
        js.Any.fromFunction1 { (tabs: js.Array[ChromeTab]) =>
          promise.complete(lastErrorOrValue(tabs.toArray))
        }
      )

    IO.fromFuture(IO(promise.future))
  }

  private def lastErrorOrValue[T](value: => T): Try[T] =
    ChromeRuntime.lastError match
      case Some(value) => Failure(new Exception(value.getMessage))
      case None        => Success(value)

}

object ChromeRuntime {
  def lastError: Option[Error] =
    bindings.Runtime.lastError.toOption.flatMap(Option.apply)
}
