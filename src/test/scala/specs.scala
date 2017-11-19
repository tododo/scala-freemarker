package ru.circumflex
package freemarker

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import java.io.File

import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FlatSpec, Matchers}

import collection.mutable.HashMap

class CircumflexFtlSpec extends FlatSpec with Matchers {

  def beFine(root: Any) = new Matcher[String] {

    override def apply(name : String): MatchResult = {
      val text = ftl2string("/" + name + ".ftl", root).trim
      val out = FileUtils.readFileToString(
        new File(this.getClass.getResource("/" + name + ".out").toURI), "UTF-8").trim
      val diffIndex = StringUtils.indexOfDifference(text, out)
      val diff = StringUtils.difference(text, out)
      MatchResult(diffIndex == -1,
          "\"" + name + "\" is fine",
          "\"" + name + "\" fails at " + diffIndex + ": " + StringUtils.abbreviate(diff, 32))
    }
  }

  "Circumflex FTL" should
    "handle simple objects" in {
      object simpleObject {
        val name = "Joe"
        val subobj = new Object {
          val name = "Smith"
        }
        override def toString = name + " " + subobj.name
      }
      
      val obj = simpleObject
      "Simple objects" should beFine (Map("obj"->obj))
    }
    it should "handle lists" in {
      val list = List("one", "two", "three")
      val range = 0 to 9
      "Lists" should beFine(Map("list"->list, "range" -> range))
    }
    it should "handle maps" in {
      val map = new HashMap[String, Any]
      map += ("one" -> "Hello to")
      map += ("two" -> HashMap("one" -> "Joe", "two" -> "Smith"))
      //val map = Map("one" -> "Hello to", "two" -> Map("one" -> "Joe", "two" -> "Smith"))
      val context = Map("map" -> map)
      println(context)
      "Maps" should beFine(context)
    }
    it should "provide limited support of Scala XML" in {
      val root = <root>
        <child>
          <one id="0">1</one>
            <two id="1"/>
          <three id="2">Three</three>
        </child>
          <child/>
      </root>
      "XML" should beFine(Map("root"->root))
    }


}
