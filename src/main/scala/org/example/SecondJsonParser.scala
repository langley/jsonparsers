package org.example
// From http://www.artima.com/pins1ed/combinator-parsing.html
import scala.util.parsing.combinator._
import java.io.FileReader

object SecondJsonParser extends SecondJsonParser with App {
  if (args.size > 0 && args(0) != null) { 
	val reader = new FileReader(args(0))
    println(parseAll(value, reader))
  } else { 
	println("Usage: <file to parse>")
  }
}

class SecondJsonParser extends JavaTokenParsers {   
  def obj: Parser[Map[String, Any]] = 
    "{"~> repsep(member, ",") <~"}" ^^ (Map() ++ _)

  def arr: Parser[List[Any]] =
    "["~> repsep(value, ",") <~"]" 
  
  def member: Parser[(String, Any)] = 
    stringLiteral~":"~value ^^ 
      { case name~":"~value => (name, value) }
  
  def value: Parser[Any] = (
      obj
    | arr 
    | stringLiteral
    | floatingPointNumber ^^ (_.toDouble) 
    | "null"  ^^ (x => null) 
    | "true"  ^^ (x => true) 
    | "false" ^^ (x => false)
  ) // using '(' lets us use \n and avoid ';' for one statement
}
