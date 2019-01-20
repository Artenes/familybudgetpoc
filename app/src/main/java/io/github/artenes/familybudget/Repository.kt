package io.github.artenes.familybudget

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import io.github.artenes.familybudget.data.BankAccount
import io.github.artenes.familybudget.data.BankTransaction

class Repository {

    private val firebase = FirebaseFirestore.getInstance()

    private val cachedAccounts: MutableMap<String, BankAccount> = mutableMapOf()

    companion object {

        val INSTANCE: Repository by lazy {
            Repository()
        }

    }

    fun getAccount(id: String): BankAccount {
        if (!cachedAccounts.containsKey(id)) {

            val accountTask = firebase.collection("accounts").document(id).get()
            val transactionsTask = firebase.collection("accounts").document(id).collection("transactions").get()

            val accountSnap = Tasks.await(accountTask)
            val transactionsSnap = Tasks.await(transactionsTask)

            val account: BankAccount = accountSnap.toObject(BankAccount::class.java) as BankAccount
            val transactions: MutableList<BankTransaction> = transactionsSnap.toObjects(BankTransaction::class.java)

            account.transactions = transactions

            cachedAccounts[id] = account

            return account

        } else {

            return cachedAccounts[id] as BankAccount

        }
    }

    fun getTransaction(accountId: String, transactionId: String): BankTransaction? {

        val account = getAccount(accountId)

        return try {
            account.transactions.first {
                it.id == transactionId
            }
        } catch (exception: NoSuchElementException) {
            null
        }

    }

    fun saveTransaction(accountId: String, transaction: BankTransaction) {
        val map = mutableMapOf<String, Any>()
        map.put("id", transaction.id)
        map.put("description", transaction.description)
        map.put("value", transaction.value)
        map.put("timestamp", transaction.timestamp)

        val task = firebase.collection("accounts").document(accountId).collection("transactions").document(transaction.id).set(map)
        Tasks.await(task)
        cachedAccounts[accountId]?.saveTransaction(transaction)
    }

}