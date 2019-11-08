package it.maior.acs.alg.output.parser
import java.time.{Duration, LocalDateTime}
import java.io.File

import scala.io.Source
import java.time.format.DateTimeFormatter

import scala.collection.mutable.ListBuffer

class ParseAndFormatterManager {

  val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

  def parseAndFormat(inputFile: File):ListBuffer[SolutionDetails] = {

    var solutionsDetails: ListBuffer[SolutionDetails] = new ListBuffer()

    var startDateTime: LocalDateTime = null

    for (line <- Source.fromFile(inputFile).getLines) {

      if (line.startsWith("Data")) {
        startDateTime = LocalDateTime.parse(line.split('\t')(1), formatter)
      }

      if (line.startsWith("Soluzione")) {

        var splittedLine = line.split(" ").filter(_ != "")

        val solutionId = splittedLine(2)
        val objectiveFunction = splittedLine(4)
        val shiftCount = splittedLine(8)
        val tripperCount = splittedLine(11)
        val endDateTime: LocalDateTime =
          LocalDateTime.parse(
            splittedLine(splittedLine.length - 2) + " " + splittedLine(
              splittedLine.length - 1
            ),
            formatter
          )

        val executionTimeInMinutes =
          Duration.between(startDateTime, endDateTime).toMinutes

        solutionsDetails += new SolutionDetails(
          inputFile.toString,
          solutionId.toLong,
          objectiveFunction.toDouble,
          shiftCount.toLong,
          tripperCount.toLong,
          executionTimeInMinutes
        )

      }
    }
    return solutionsDetails
  }
}
