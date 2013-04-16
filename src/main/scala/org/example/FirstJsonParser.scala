package org.example
// From http://www.artima.com/pins1ed/combinator-parsing.html
import scala.util.parsing.combinator._
import java.io.FileReader
  
object FirstJsonParser extends JSON {
   // consoleMain lets us use sbt run for SecondJsonParser
   def consoleMain(args: Array[String]) {
     val reader = new FileReader(args(0))
     println(parseAll(value, reader))
   }
}
  
class JSON extends JavaTokenParsers {   
  
   def obj   : Parser[Any] = "{"~repsep(member, ",")~"}"
  
   def arr   : Parser[Any] = "["~repsep(value, ",")~"]"
  
   def member: Parser[Any] = stringLiteral~":"~value
   
   def value : Parser[Any] = obj | arr | 
                             stringLiteral | 
                             floatingPointNumber | 
                             "null" | "true" | "false"
}

