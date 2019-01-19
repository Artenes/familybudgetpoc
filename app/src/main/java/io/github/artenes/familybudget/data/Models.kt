package io.github.artenes.familybudget.data

import java.util.*

data class BankAccount(var balance: Int = 0, var transactions: MutableList<BankTransaction> = mutableListOf())

data class BankTransaction(
    var timestamp: Long = Calendar.getInstance().timeInMillis,
    var value: Int = 0,
    var description: String = "Nova despesa",
    var store: Store = Store()
)

data class Store(var name: String = "Loja", var address: String = "Endereco")