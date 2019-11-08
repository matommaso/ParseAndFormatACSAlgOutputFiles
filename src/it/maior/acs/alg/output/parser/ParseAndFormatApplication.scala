package it.maior.acs.alg.output.parser
import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat
import java.time.{Duration, LocalDateTime}
import java.util.Date

import scala.io.Source

object ParseAndFormatApplication {

  val inputFolderPath: String =
    "C:\\Users\\mato\\Desktop\\Scenarios"

  val extension: String = "SoluzioniTrovate"


  import java.time.LocalDateTime
  import java.time.format.DateTimeFormatter
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

  /**
    * Get a recursive listing of all files underneath the given directory.
    * from stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
    */
  def getRecursiveListOfFilesWithExtensions(dir: File): Array[File] = {
    val these = dir.listFiles
    these ++ these
      .filter(_.isDirectory)
      .flatMap(getRecursiveListOfFilesWithExtensions)
  }

  def main(args: Array[String]): Unit = {
    val dataFormat = new SimpleDateFormat()

    val inputFile: File = new File(inputFolderPath)

    val files: Array[File] = getRecursiveListOfFilesWithExtensions(inputFile)

    val tmp = files.filter(_.toString().endsWith(extension))

    for (inputFile <- tmp) {

      var startDateTime: LocalDateTime = null

      for (line <- Source.fromFile(inputFile).getLines) {

        if (line.startsWith("Data")) {
          startDateTime = LocalDateTime.parse(line.split('\t')(1), formatter)
        }

        if(line.startsWith("Soluzione")){

        //  println(line)

          var splittedLine = line.split(" ").filter(_ !="")

          val solutionId = splittedLine(2)
          val objectiveFunction = splittedLine(4)
          val shiftCount = splittedLine(8)
          val tripperCount = splittedLine(11)

          val endTimeString = splittedLine(splittedLine.length-2) +" "+ splittedLine(splittedLine.length-1)

          val endDateTime : LocalDateTime = LocalDateTime.parse(endTimeString, formatter)




          val dur = Duration.between(startDateTime, endDateTime)
          val millis = dur.toMillis
          val minutes = millis /1000 /60

         println(inputFile.toString +","+solutionId+","+objectiveFunction+","+shiftCount+","+tripperCount+","+minutes)

        }


      }
    }

//    val pw = new PrintWriter(new File(outputPath))
//    var countIdentation = 0
//
//    for (line <- Source.fromFile(inputFolderPath).getLines) {
//
//      if (line.contains("{")) { countIdentation += 1 } else if (line.contains(
//                                                                  "}"
//                                                                )) {
//        countIdentation -= 1
//      }
//
//      var countIdentationPrintable = countIdentation
//      if (line.contains("{")) { countIdentationPrintable -= 1 }
//
//      var printableOutput: String = ""
//      for (i <- 0 to countIdentationPrintable - 1) {
//        printableOutput += "\t"
//      }
//
//      printableOutput += line
//      pw.write(printableOutput + "\n")
//      println(printableOutput)
//    }
//    pw.close
  }
}
