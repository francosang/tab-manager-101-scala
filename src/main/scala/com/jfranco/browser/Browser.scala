package com.jfranco.browser

import cats.effect.IO
import com.jfranco.impl.chrome.{ChromeBrowser, ChromeTab, ChromeTabQuery}

trait Browser {

  def query(): IO[List[Tab]]

  def query(queryInfo: TabQuery): IO[List[Tab]]
}

object Browser {

  def chromeImpl: Browser = new Browser:
    override def query(): IO[List[Tab]] = query(TabQuery())

    override def query(queryInfo: TabQuery): IO[List[Tab]] = ChromeBrowser
      .query(ChromeTabQuery(queryInfo))
      .flatMap(chromeTab => IO(chromeTab.map(ChromeTab.apply).toList))

}
