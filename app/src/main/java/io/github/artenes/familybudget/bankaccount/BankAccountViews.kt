package io.github.artenes.familybudget.bankaccount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.artenes.familybudget.R
import io.github.artenes.familybudget.data.BankTransaction
import kotlinx.android.synthetic.main.balance_view.view.*
import kotlinx.android.synthetic.main.date_view.view.*
import kotlinx.android.synthetic.main.transaction_line_view.view.*
import java.text.DateFormat
import java.text.NumberFormat

fun Int.formatAsMoney(): String {
    val format = NumberFormat.getCurrencyInstance()
    return format.format(this.toMoney())
}

fun Int.toMoney(): Double {
    return this / 100.00
}

fun Int.toEditableMoneyString(): String {
    return this.toMoney().toString().replace(".", ",").removePrefix("-")
}

fun Long.toDate(): String {
    val format = DateFormat.getDateInstance()
    return format.format(this)
}

fun String.toCents(): Int {
    val money = this.replace(",", ".").toFloat()
    return (money * 100).toInt()
}

interface BankAccountDataItem

interface TransactionClickListener {

    fun onTransactionClicked(position: Int, transaction: BankTransaction)

}

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
        itemView.balance.text = dataItem.balance.formatAsMoney()
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

class TransactionView(view: View, val listener: TransactionClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

    companion object {

        fun create(inflater: LayoutInflater, parent: ViewGroup, listener: TransactionClickListener): TransactionView {
            val view = inflater.inflate(R.layout.transaction_line_view, parent, false)
            return TransactionView(view, listener)
        }

    }

    fun bind(dataItem: TransactionDataItem) {
        itemView.value.text = dataItem.transaction.value.formatAsMoney()
        itemView.description.text = dataItem.transaction.description
        itemView.transactionDate.text = dataItem.transaction.timestamp.toDate()
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onTransactionClicked(adapterPosition, BankTransaction())
    }

}