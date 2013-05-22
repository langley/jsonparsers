package org.example
// From http://www.artima.com/pins1ed/combinator-parsing.html
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.util.parsing.combinator._
import java.io.FileReader
import com.google.gson._

object XpParser extends XpParser with App {

  if (args.size > 0 && args(0) != null) { 
	val reader = new FileReader(args(0))
	parseAll(value,reader) match { 
	  case f: Failure => println("Found a failure: " + f.msg)
	  case ok => println("Was okay: " + ok)
	}
  } else { 
	println("Usage: <file to parse>")
  }
  
  def consoleMain(args: Array[String]) {
     val reader = new FileReader(args(0))
     println(parseAll(value, reader))
     
  }  
}


class XpParser extends JavaTokenParsers {   

  def value: Parser[Any] =  (myParser | failure("didn't find what I wanted"))
  
  def myParser: Parser[Any] = "{" ~> "\"firstField\"" ~> ":" ~> "\"abcd\"" <~ "}"
  
}
