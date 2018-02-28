package be.superjoran.mint.services

import java.util.function.Predicate

class CsvOptions(
        val cvsSplitBy: String,
        val identifyStatementPredicate: Predicate<String>,
        val datePattern: String,
        val rowNumberAmount: Int,
        val rowNumberDate: Int,
        val rowNumberFromAccount: Int,
        val rowNumberToAccount: Int,
        val rowNumberDescription: Int
)