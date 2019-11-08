package it.maior.acs.alg.output.parser

case class SolutionDetails(fileAbsolutePath: String,
                           solutionId: Long,
                           objectiveFunction: Double,
                           shiftCount: Long,
                           tripperCount: Long,
                           executionTimeInSeconds: Long)

