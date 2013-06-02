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

class TestFourthJsonParser extends FlatSpec {
  val fjp = new FourthJsonParser
  
  "FourthJsonParser" should "parse a.json w/out failure" in {
	val reader = Source.fromURL(getClass.getResource("/a.json")).reader
	val result = fjp.parseAll(fjp.addressBook, reader)
	result should be ('successful)
  }
  
  "FourthJsonParser" should "fail a parse of aFail.json because its missing the name field" in {
	val reader = Source.fromURL(getClass.getResource("/aFail.json")).reader
	val result = fjp.parseAll(fjp.addressBook, reader)
	result should not be ('successful)
  }

  // b.json isn't an address book so it should fail
  "FourthJsonParser" should "fail a parse of b.json because it's not an addressBook" in {
	val reader = Source.fromURL(getClass.getResource("/b.json")).reader
	val result = fjp.parseAll(fjp.addressBook, reader)
	result should not be ('successful)
  }  
  
  // bFail.json isn't an address book so it should fail
  "FourthJsonParser" should "fail a parse of bFail.json because it's not even json" in {
	val reader = Source.fromURL(getClass.getResource("/bFail.json")).reader
	val result = fjp.parseAll(fjp.addressBook, reader)
	result should not be ('successful)
  }  
  
}

