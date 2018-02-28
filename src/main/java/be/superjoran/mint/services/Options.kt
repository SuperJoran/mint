package be.superjoran.mint.services

import java.util.function.Predicate

class Options(
        private val cvsSplitBy: String?,
        private val identifyStatementPredicate: Predicate<String>?,
        private val datePattern: String?,

        private val rowNumberAmount: Int?,
        private val rowNumberDate: Int?,
        private val rowNumberFromAccount: Int?,
        private val rowNumberToAccount: Int?,
        private val rowNumberDescription: Int?
)
