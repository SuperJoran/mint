package be.superjoran.mint.domain.searchresults

import be.superjoran.mint.domain.BankAccount
import java.io.File

import java.io.Serializable

class CsvFile(var fileUrl: File, var bankAccount: BankAccount?) : Serializable {
    constructor(fileUrl: File) : this(fileUrl, null)
}
