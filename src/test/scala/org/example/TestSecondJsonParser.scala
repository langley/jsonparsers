package org.example

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers._

import scala.io.Source
import scala.util.parsing.combinator.Parsers

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.util.parsing.combinator._
import java.io.FileReader
import com.google.gson._

class TestSecondJsonParser extends FlatSpec {
  val fjp = new SecondJsonParser
  
  "SecondJsonParser" should "parse a.json w/out failure" in {
	val reader = Source.fromURL(getClass.getResource("/a.json")).reader
	val result = fjp.parseAll(fjp.obj, reader)
	result should be ('successful)
  }
  
  "SecondJsonParser" should "succeed because this parser just looks for json" in {
	val reader = Source.fromURL(getClass.getResource("/aFail.json")).reader
	val result = fjp.parseAll(fjp.obj, reader)
	result should be ('successful)
  }

  // b.json isn't an address book but this parser doesn't care
  "SecondJsonParser" should "fail a parse of b.json because it's not an addressBook" in {
	val reader = Source.fromURL(getClass.getResource("/b.json")).reader
	val result = fjp.parseAll(fjp.obj, reader)
	result should be ('successful)
  }  
  
  // bFail.json isn't an address book so it should fail
  "SecondJsonParser" should "fail a parse of bFail.json because it's not even json" in {
	val reader = Source.fromURL(getClass.getResource("/bFail.json")).reader
	val result = fjp.parseAll(fjp.obj, reader)
	result should not be ('successful)
  }  
  
}

