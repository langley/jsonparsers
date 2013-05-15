package org.example
// From http://www.artima.com/pins1ed/combinator-parsing.html
import scala.util.parsing.combinator._
import java.io.FileReader
import com.google.gson._

object FourthJsonParser extends JSON4 with App {

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

class JSON4 extends JavaTokenParsers {   
  
//  def addressBook: Parser[JsonObject] = (
//	name ~ "," ~ address ~ "," ~ phoneNumbers ^^ { 
//	  case (name ~ "," ~ address ~ "," ~ phoneNumbers) => { 
//	    List(name,address,phoneNumbers).foldLeft(new JsonObject){(r,e) => r.; r}
//	  }
//	}
//  )
  
  def name: Parser[JsonObject] = ( 
	  member ^^ { 
	    case member => val r = new JsonObject; r.add(member._1, member._2); r}
  )
  
  def address: Parser[JsonObject] = (
      member ~ "," ~ member ~ "," ~ member ^^ {
        case (a~","~b~","~c) =>  List(a,b,c).foldLeft(new JsonObject){(r,e) => r.add(e._1, e._2); r }
      }
  )
  
  
  def phoneNumbers: Parser[JsonArray] = (
	  "["~> repsep(floatingPointNumber,",") <~"]" ^^ {
	  	case value => value.foldLeft(new JsonArray){(r,e) => r.add(new JsonPrimitive(new java.lang.Double(e))); r}}
  )

    
  
  def obj: Parser[JsonObject] = 
    "{"~> repsep(member, ",") <~"}" ^^ 
        { case member => member.foldLeft(new JsonObject){(r,e) => r.add(e._1, e._2); r} }

  def arr: Parser[JsonArray] =
    "["~> repsep(value, ",") <~"]" ^^ 
       { case value => value.foldLeft(new JsonArray){(r,e) => r.add(e); r} } 
  
  def member: Parser[(String, JsonElement)] = 
    stringLiteral~":"~value ^^ 	// 
      { case name~":"~value => (stripFirstAndLast(name),value) }
  
  def value: Parser[JsonElement] = (
      obj
    | arr 
    | stringLiteral ^^ { case str => new JsonPrimitive(stripFirstAndLast(str)) } 
    | floatingPointNumber ^^ { case fpStr => new JsonPrimitive(new java.lang.Double(fpStr)) } 
    | "null"  ^^ {case _ => new JsonNull} 
    | "true"  ^^ {case _ => new JsonPrimitive(new java.lang.Boolean(true))} 
    | "false" ^^ {case _ => new JsonPrimitive(new java.lang.Boolean(false))}
  ) // using '(' lets us use \n and avoid ';' for one statement
  
  // Strip first and last quotes from input string
  def stripFirstAndLast(in: String): String = in.subSequence(1, in.length()-1).toString
}
