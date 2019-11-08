package it.maior.acs.alg.output.parser
import java.io.{File, PrintWriter}

import scala.collection.mutable.ListBuffer
import scala.io.Source

object ParseAndFormatApplication {

  val inputFolderPath: String =
    "C:\\Users\\mato\\Desktop\\Scenarios"
  val outputPath : String = "Output.txt"
  val extension: String = "SoluzioniTrovate"

  def main(args: Array[String]): Unit = {

    var solutionsDetails: ListBuffer[SolutionDetails] = new ListBuffer()
    val parseManager: ParseAndFormatterManager = new ParseAndFormatterManager

    val filesWithSpecificExtension: Array[File] =
      getRecursiveListOfFilesWithExtensions(new File(inputFolderPath))
        .filter(_.toString().endsWith(extension))

    for (inputFile <- filesWithSpecificExtension) {
      solutionsDetails.appendAll(parseManager.parseAndFormat(inputFile))
    }
    printAll(solutionsDetails)
  }


  def printAll(solutionsDetails: ListBuffer[SolutionDetails]) ={

    val pw = new PrintWriter(new File(outputPath))

    pw.write("fileAbsolutePath,solutionId,objectiveFunction,shiftCount,tripperCount,executionTimeInSeconds")
    pw.write("\n")
    for (solutionDetails <- solutionsDetails) {
      pw.write(solutionDetails.fileAbsolutePath +",")
      pw.write(solutionDetails.solutionId +",")
      pw.write(solutionDetails.objectiveFunction +",")
      pw.write(solutionDetails.shiftCount +",")
      pw.write(solutionDetails.tripperCount +",")
      pw.write(solutionDetails.executionTimeInSeconds.toString)
      pw.write("\n")
    }
    pw.close
  }

  def getRecursiveListOfFilesWithExtensions(dir: File): Array[File] = {
    val these = dir.listFiles
    these ++ these
      .filter(_.isDirectory)
      .flatMap(getRecursiveListOfFilesWithExtensions)
  }
}