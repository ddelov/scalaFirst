package com.estafet.eet.data.source

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by Delcho Delov on 19.10.18.
  *
  */
class CSVReader(/*, */) {

  def <<(bufferedSource: Source)={
    require(bufferedSource!=null)
    val rows =  new ArrayBuffer[KafRec]()
    //first line is header
    for (line <- bufferedSource.getLines.drop(1).filter(_.length > 10)) {
      val colValues = line.split("\",\"").map(_.replace("\"", ""))
      val head = colValues.head.toInt
      val value:String = colValues.tail.mkString("|")// colValues.collect(new PartialFunction[0] {})apply(1)
      rows += new KafRec(head, value)
    }
    bufferedSource.close
    rows
  }
}
case class KafRec(k:Int, v:String){

}
