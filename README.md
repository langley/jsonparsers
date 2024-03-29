What's Here
===========
This project shows how to build a "strict json parser" that insures
the input is of the required type before transforming it to gson
objects. It started from code illustrated here: 
     http://www.artima.com/pins1ed/combinator-parsing.html

Each of the parsers included extends the basic example, see the table 
of Scala Files below which explains each step of the progression. 

The project is a simple SBT project created using the following steps:
  1. g8 typesafehub/scala-sbt
  2. adding the project/plugins.sbt containing the eclipse plugin. 

Note the eclipse plugin allows you to use the "eclipse" command in sbt
to create the eclipse project support. 

Scala Files
-----------

|         Name          | Illustrates                                 |
|:---------------------:|:--------------------------------------------|
|FirstJsonParser.scala  |This is the first example from the url above |
|SecondJsonParser.scala |This is the second example from the url above|
|ThirdJsonParser.scala  |Adds support for transforming the input json into json objects defined by the google gson library.                                      |                                     
|FourthJsonParser.scala |Moves the parser from generically parsing json into one that is specifically looking for an address book and finding it, transforms it into gson objects.   |             
|FifthJsonParser.scala  |Shows what you need to do to report failures and react to them. |                           

Data Files
----------
|        Name     | Used for                                            |
|:---------------:|:----------------------------------------------------|
|addressBook.json | the canonical json from the url example             |
|a.json           | basically the same as addrBook.json                 |
|aFail.json       | an address book that is valid json but not a valid address  book. |
|b.json           | a VERY simple json object                           |
|bFail.json       | a VERY simple BROKEN json object, not valid json    |

