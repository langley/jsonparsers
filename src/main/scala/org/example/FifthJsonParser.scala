package org.example

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.util.parsing.combinator._
import java.io.FileReader
import com.google.gson._

object FifthJsonParser extends FifthJsonParser with App {

  if (args.size > 0 && args(0) != null) { 
	val reader = new FileReader(args(0))
    parseAll(value, reader) match { 
	  case fail: Failure => println("Found a failure: " + fail.msg)
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


class FifthJsonParser extends JavaTokenParsers {   

  case class ParsedElement(name: String, element: JsonElement)
  
  def value : Parser[JsonObject] = (
		  ("{" ~> addressBook <~ "}" | failure("ugh")) 
  )
  
  def addressBook: Parser[JsonObject] = {
	"\"address book\"" ~> ":" ~> "{"~> name ~ "," ~ address ~ "," ~ phoneNumbers <~ "}" ^^ { 
	  case (name ~ "," ~ address ~ "," ~ phoneNumbers) => { 
	    List(name,address,phoneNumbers).foldLeft(new JsonObject){(r,e) => r.add(e.name,e.element); r }
	  }
	} 
  }
  def name: Parser[ParsedElement] = {
    "\"name\"" ~ ":" ~ stringValue ^^ 	
      { case name ~ ":" ~ value => ParsedElement(stripQuotes(name),value) }  
  }
  def address: Parser[ParsedElement] = {
      "\"address\"" ~> ":" ~> "{" ~> street ~ "," ~ city ~ "," ~ zip <~ "}" ^^ {
        case (a~","~b~","~c) =>  ParsedElement("address", 
          List(a,b,c).foldLeft(new JsonObject){(r,e) => r.add(e.name, e.element); r})
      }
  }
  def street: Parser[ParsedElement] = {
    "\"street\"" ~ ":" ~ stringValue ^^ 	
      { case name~":"~value => ParsedElement(stripQuotes(name),value) }
  }
  def city: Parser[ParsedElement] = {
    "\"city\"" ~ ":" ~ stringValue ^^ 	
      { case name~":"~value => ParsedElement(stripQuotes(name),value) }
  }
  def zip: Parser[ParsedElement] = {
    "\"zip\"" ~ ":" ~ numberValue ^^ 	
      { case name~":"~value => ParsedElement(stripQuotes(name),value) }
  }
  def phoneNumbers: Parser[ParsedElement] = {
	  "\"phone numbers\"" ~ ":" ~> (stringValue | stringArray) ^^ {
	  	case value => ParsedElement("phone numbers",value)}
  }

  def stringArray: Parser[JsonArray] = {
    "["~> repsep(stringValue, ",") <~ "]" ^^ 
       { case value => value.foldLeft(new JsonArray){(r,e) => r.add(e); r} }
  }
  
  def numberArray: Parser[JsonArray] = {
    "["~> repsep(numberValue, ",") <~ "]" ^^ 
       { case value => value.foldLeft(new JsonArray){(r,e) => r.add(e); r} }
  }
    
  def stringValue: Parser[JsonPrimitive]  = stringLiteral ^^ { case str => new JsonPrimitive(stripQuotes(str)) } 

  def numberValue: Parser[JsonPrimitive] = (wholeNumber | floatingPointNumber) ^^ { case numStr => new JsonPrimitive(new java.lang.Double(numStr)) }
  
  // Strip first and last quotes from input string
  def stripQuotes(in: String): String = in.subSequence(1, in.length()-1).toString
  
}
