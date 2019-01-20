package io.github.artenes.familybudget.bankaccount

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.artenes.familybudget.data.BankAccount
import io.github.artenes.familybudget.data.BankTransaction
import java.util.*

class BankAccountAdapter(val listener: TransactionClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), TransactionClickListener {

    companion object {

        const val VIEW_TYPE_BALANCE = 1
        const val VIEW_TYPE_DATE = 2
        const val VIEW_TYPE_TRANSACTION = 3

    }

    val dataItems: MutableList<BankAccountDataItem> = mutableListOf()

    fun setData(bankAccount: BankAccount) {

        dataItems.clear()

        dataItems.add(BalanceDataItem(bankAccount.balance))

        var lastDate = Calendar.getInstance()
        lastDate.clear()
        lastDate.set(Calendar.HOUR, 0)
        lastDate.set(Calendar.MINUTE, 0)
        lastDate.set(Calendar.SECOND, 0)

        bankAccount.transactions.sortByDescending {
            it.timestamp
        }

        for (transaction in bankAccount.transactions) {

            val dateAsCalendar = Calendar.getInstance()
            dateAsCalendar.timeInMillis = transaction.timestamp
            dateAsCalendar.set(Calendar.HOUR, 0)
            dateAsCalendar.set(Calendar.HOUR_OF_DAY, 0)
            dateAsCalendar.set(Calendar.MINUTE, 0)
            dateAsCalendar.set(Calendar.SECOND, 0)
            dateAsCalendar.set(Calendar.MILLISECOND, 0)

            val isInTheSameDate = dateAsCalendar.equals(lastDate)

            if (!isInTheSameDate) {
                dataItems.add(DateDataItem(transaction.timestamp))
                lastDate = dateAsCalendar
            }

            dataItems.add(TransactionDataItem(transaction))

        }

        notifyDataSetChanged()

    }

    override fun getItemViewType(position: Int): Int = when (dataItems[position]) {
        is BalanceDataItem -> VIEW_TYPE_BALANCE
        is DateDataItem -> VIEW_TYPE_DATE
        else -> VIEW_TYPE_TRANSACTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        VIEW_TYPE_BALANCE -> BalanceView.create(LayoutInflater.from(parent.context), parent)
        VIEW_TYPE_DATE -> DateView.create(LayoutInflater.from(parent.context), parent)
        else -> TransactionView.create(LayoutInflater.from(parent.context), parent, this)
    }

    override fun getItemCount(): Int = dataItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataItems[position]
        when (holder) {
            is BalanceView -> holder.bind(item as BalanceDataItem)
            is DateView -> holder.bind(item as DateDataItem)
            else -> (holder as TransactionView).bind(item as TransactionDataItem)
        }
    }

    override fun onTransactionClicked(position: Int, transaction: BankTransaction) {
        listener.onTransactionClicked(position, (dataItems[position] as TransactionDataItem).transaction)
    }

}