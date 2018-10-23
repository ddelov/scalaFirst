package com.estafet.eet.data.source

import java.io.FileNotFoundException

import org.scalatest.FunSuite

class CSVReaderTest extends FunSuite {
  private val reader = new CSVReader()

  test("null source") {
    intercept[IllegalArgumentException] {
      reader << (null)
    }
  }
  test("missing file") {
    lazy val fileSource = io.Source.fromFile("65.csv", "UTF-8")
    intercept[FileNotFoundException] {
       val buf = new CSVReader << fileSource
    }
  }
  test("invalid source") {
    lazy val stringSource = io.Source.fromString("65.csv")
      val buf = new CSVReader << stringSource
    assert(buf.isEmpty)
  }
  test("valid CSV file") {
    lazy val fileSource = io.Source.fromFile("csv2kafka/src/test/resources/data-1529499889365.csv", "UTF-8")
    val buf = new CSVReader << fileSource
    assert(!buf.isEmpty)
    assert(buf.length == 2472)
    val rec = buf.remove(0)
    assert(rec.isInstanceOf[KafRec])
  }

  //    private val start: Long = System.nanoTime()
//    //step 1 : read csv file
//    //  lazy val fileSource = io.Source.fromFile("/home/ddelov/IdeaProjects/scalaFirst/csv2kafka/src/main/resources/data-1529499889365.csv", "UTF-8")
//    lazy val fileSource = io.Source.fromFile("csv2kafka/src/main/resources/data-1529499889365.csv", "UTF-8")
//    val buf = new CSVReader << fileSource
//    println(s"Found ${buf.size} records")
//
//    val emptyStack = new Stack[Int]
//    intercept[NoSuchElementException] {
//      emptyStack.pop()
//    }
//    assert(emptyStack.isEmpty)
//  }

}
