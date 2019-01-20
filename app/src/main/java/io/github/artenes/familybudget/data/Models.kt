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
            if (transaction.lastValue > 0) {
                balance -= transaction.lastValue
            } else {
                balance += -transaction.lastValue
            }
            balance += transaction.value
        } else {
            balance += transaction.value
            transactions.add(transaction)
        }
    }

}

class BankTransaction() {

    var id: String = UUID.randomUUID().toString()
    var timestamp: Long = Calendar.getInstance().timeInMillis
    var description: String = ""
    var store: Store = Store()
    var value: Int = 0
        set(value) {
            lastValue = field
            field = value
        }
    var lastValue: Int = 0
        private set

    constructor(value: Int, description: String) : this() {
        this.value = value
        this.description = description
    }

}

data class Store(var name: String = "Loja", var address: String = "Endereco")