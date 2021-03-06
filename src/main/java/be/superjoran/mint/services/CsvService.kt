package be.superjoran.mint.services

import be.superjoran.mint.domain.BankAccount
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.Statement
import be.superjoran.mint.domain.searchresults.CsvFile
import java.io.File
import javax.validation.ConstraintViolationException

interface CsvService {
    @Throws(ConstraintViolationException::class)
    fun uploadCSVFiles(file: List<CsvFile>, person: Person): Iterable<Statement>

    fun identifyBankAccount(fileUrl: String?, person: Person): BankAccount?

    fun identifyCsvFiles(files: List<File>, person: Person): List<CsvFile>
}
