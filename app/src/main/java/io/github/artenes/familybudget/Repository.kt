package io.github.artenes.familybudget

import io.github.artenes.familybudget.data.BankAccount
import io.github.artenes.familybudget.data.BankTransaction

class Repository {

    companion object {

        private val BRADESCO: BankAccount = BankAccount()

        val INSTANCE: Repository by lazy {
            Repository()
        }

    }

    fun getBradesco(): BankAccount {
        return BRADESCO
    }

    fun addTransactionToBradesco(transaction: BankTransaction) {
        BRADESCO.transactions.add(transaction)
    }

}