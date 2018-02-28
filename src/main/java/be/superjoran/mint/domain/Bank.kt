package be.superjoran.mint.domain

import java.io.Serializable
import java.util.*

/**
 * Created by Jorandeboever
 * Date: 30-Apr-17.
 */
enum class Bank : Display, Serializable {
    KEYTRADE, BELFIUS, ING;

    override val id: String
        get() = this.name
    override val displayValue: String
        get() = this.name


    companion object {
        fun getBanks(): List<Bank> {
            return Arrays.asList(Bank.BELFIUS, Bank.KEYTRADE, Bank.ING)
        }
    }

}