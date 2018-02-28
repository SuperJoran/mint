package be.superjoran.mint.domain

import java.io.Serializable
import java.util.*

/**
 * Created by Jorandeboever
 * Date: 30-Apr-17.
 */
enum class Bank : Display, Serializable {
    KEYTRADE, BELFIUS, ING;

    override fun getId(): String {
        return this.name
    }

    override fun getDisplayValue(): String {
        return this.name
    }


    companion object {
        public fun getBanks(): List<Bank> {
            return Arrays.asList(Bank.BELFIUS, Bank.KEYTRADE, Bank.ING)
        }
    }

}