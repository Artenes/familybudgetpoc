package io.github.artenes.familybudget.data

import java.util.*

data class BankAccount(var balance: Int = 0, val transactions: MutableList<BankTransaction> = mutableListOf()) {

    fun addTransaction(transaction: BankTransaction) {
        balance += transaction.value
        transactions.add(transaction)
    }

}

data class BankTransaction(
    var timestamp: Long = Calendar.getInstance().timeInMillis,
    var value: Int = 0,
    var description: String = "Nova despesa",
    var store: Store = Store()
)

data class Store(var name: String = "Loja", var address: String = "Endereco")