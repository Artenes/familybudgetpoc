package io.github.artenes.familybudget.data

import java.util.*

class BankAccount {

    var id: String = UUID.randomUUID().toString()
    var name: String = ""
    var balance: Int = 0
    var transactions: MutableList<BankTransaction> = mutableListOf()

    fun saveTransaction(transaction: BankTransaction) {
        val position = transactions.indexOfFirst { it.id == transaction.id }
        if (position > -1) {
            val originalTransaction = transactions.get(position)
            if (originalTransaction.value > 0) {
                balance -= originalTransaction.value
            } else {
                balance += originalTransaction.value
            }
            balance += transaction.value
            transactions[position] = transaction
        } else {
            balance += transaction.value
            transactions.add(transaction)
        }
    }

}

class BankTransaction() {

    var id: String = UUID.randomUUID().toString()
    var timestamp: Long = Calendar.getInstance().timeInMillis
    var value: Int = 0
    var description: String = "Nova despesa"
    var store: Store = Store()

    constructor(value: Int, description: String) : this() {
        this.value = value
        this.description = description
    }

}

data class Store(var name: String = "Loja", var address: String = "Endereco")