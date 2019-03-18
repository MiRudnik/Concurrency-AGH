package webcrawler

import java.io._
import java.net.URL

import org.htmlcleaner.HtmlCleaner

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.duration._

object WebCrawler extends App {
  val url = "http://galaxy.agh.edu.pl/~balis/dydakt/tw/"


  val cleaner = new HtmlCleaner
  val props = cleaner.getProperties

  val u = crawlUntilDepth(3, url)

  Await.result(u, 2 minutes)
  readLine()

  def crawlSingleSite(site: String, depth: Int): Future[Iterable[String]] = Future {
    val url = new URL(site)

    val rootNode = cleaner.clean(url)
    val elements = rootNode.getElementsByName("a", true)

    val urls = elements.map(e => e.getAttributeByName("href"))
    urls
  }

  def crawlUntilDepth(maxDepth: Int, root: String, depth: Int = 0) : Future[Unit] = Future {
    try {
      println("[" + depth + "] " + root)

      if (depth < maxDepth) crawlSingleSite(root, depth) flatMap {urls => {
        Future.sequence(urls map {href => crawlUntilDepth(maxDepth,href ,depth+1)})
      }
      }
    }
    catch {
      case _: Throwable => depth
    }
  }
}