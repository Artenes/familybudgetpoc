package io.github.artenes.familybudget.bank_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.artenes.familybudget.R
import io.github.artenes.familybudget.data.BankTransaction
import kotlinx.android.synthetic.main.balance_view.view.*
import kotlinx.android.synthetic.main.date_view.view.*
import kotlinx.android.synthetic.main.transaction_view.view.*
import java.text.DateFormat
import java.text.NumberFormat

fun Int.toMoney(): String {
    val money = this / 100
    val format = NumberFormat.getCurrencyInstance()
    return format.format(money)
}

fun Long.toDate(): String {
    val format = DateFormat.getDateInstance()
    return format.format(this)
}

interface BankAccountDataItem

data class BalanceDataItem(val balance: Int) : BankAccountDataItem
data class DateDataItem(val timestamp: Long) : BankAccountDataItem
data class TransactionDataItem(val transaction: BankTransaction) : BankAccountDataItem

class BalanceView(view: View) : RecyclerView.ViewHolder(view) {

    companion object {

        fun create(inflater: LayoutInflater, parent: ViewGroup): BalanceView {
            val view = inflater.inflate(R.layout.balance_view, parent, false)
            return BalanceView(view)
        }

    }

    fun bind(dataItem: BalanceDataItem) {
        itemView.balance.text = dataItem.balance.toMoney()
    }

}

class DateView(view: View) : RecyclerView.ViewHolder(view) {

    companion object {

        fun create(inflater: LayoutInflater, parent: ViewGroup): DateView {
            val view = inflater.inflate(R.layout.date_view, parent, false)
            return DateView(view)
        }

    }

    fun bind(dataItem: DateDataItem) {
        itemView.date.text = dataItem.timestamp.toDate()
    }

}

class TransactionView(view: View) : RecyclerView.ViewHolder(view) {

    companion object {

        fun create(inflater: LayoutInflater, parent: ViewGroup): TransactionView {
            val view = inflater.inflate(R.layout.transaction_view, parent, false)
            return TransactionView(view)
        }

    }

    fun bind(dataItem: TransactionDataItem) {
        itemView.value.text = dataItem.transaction.value.toMoney()
        itemView.description.text = dataItem.transaction.description
        itemView.transactionDate.text = dataItem.transaction.timestamp.toDate()
    }

}