package be.superjoran.mint.domain.searchresults

import be.superjoran.mint.domain.BankAccount
import java.io.File

import java.io.Serializable
import javax.validation.constraints.NotNull

class CsvFile(var fileUrl: File, @NotNull var bankAccount: BankAccount? = null) : Serializable