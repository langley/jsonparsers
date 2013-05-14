package org.example
// From http://www.artima.com/pins1ed/combinator-parsing.html
import scala.util.parsing.combinator._
import java.io.FileReader
import com.google.gson._

object ThirdJsonParser extends JSON3 with App {

  if (args.size > 0 && args(0) != null) { 
	val reader = new FileReader(args(0))
    println(parseAll(value, reader))
  } else { 
	println("Usage: <file to parse>")
  }
  
  def consoleMain(args: Array[String]) {
     val reader = new FileReader(args(0))
     println(parseAll(value, reader))
     
  }  
}

class JSON3 extends JavaTokenParsers {   
  def obj: Parser[JsonObject] = 
    "{"~> repsep(member, ",") <~"}" ^^ 
        { case member => // Map() ++ member; 
            val ret = new JsonObject(); 
            member.foreach(e => ret.add(e._1, e._2))
            ret 
        }

  def arr: Parser[JsonArray] =
    "["~> repsep(value, ",") <~"]" ^^ 
       { case value => val ret = new JsonArray()
         value.foreach(e => ret.add(e))
         ret
       } 
  
  def member: Parser[(String, JsonElement)] = 
    stringLiteral~":"~value ^^ 	// 
      { case name~":"~value => (stripFirstAndLast(name),value) }
  
  def value: Parser[JsonElement] = (
      obj
    | arr 
    | stringLiteral ^^ { case str => new JsonPrimitive(stripFirstAndLast(str)) } 
    | floatingPointNumber ^^ { case fpStr => new JsonPrimitive(new java.lang.Double(fpStr)) } 
    | "null"  ^^ {case _ => null} 
    | "true"  ^^ {case _ => new JsonPrimitive(new java.lang.Boolean(true))} 
    | "false" ^^ {case _ => new JsonPrimitive(new java.lang.Boolean(false))}
  ) // using '(' lets us use \n and avoid ';' for one statement
  
  def stripFirstAndLast(in: String): String = in.subSequence(1, in.length()-1).toString
}
