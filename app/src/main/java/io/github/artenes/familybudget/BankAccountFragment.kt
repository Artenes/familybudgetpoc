package io.github.artenes.familybudget

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.artenes.familybudget.bankaccount.BankAccountAdapter
import io.github.artenes.familybudget.bankaccount.TransactionClickListener
import io.github.artenes.familybudget.data.BankTransaction
import kotlinx.android.synthetic.main.recycler_view_layout.view.*
import kotlinx.coroutines.*

class BankAccountFragment : Fragment(), View.OnClickListener, TransactionClickListener {

    private val adapter = BankAccountAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_layout, container, false)
        view.list.layoutManager = LinearLayoutManager(container?.context)
        view.list.adapter =  adapter
        view.fab.setOnClickListener(this)

        return view
    }

    override fun onResume() {
        super.onResume()
        AppExecutors.INSTANCE.diskIO().execute {
            val bankAccount = Repository.INSTANCE.getAccount("vnuPiFGtcdQNHMTlz8a2")
            AppExecutors.INSTANCE.mainThread().execute {
                adapter.setData(bankAccount)
            }
        }
    }

    override fun onClick(v: View?) {
        startActivity(
            Intent(context, BankTransactionActivity::class.java)
                .putExtra("accountid", "vnuPiFGtcdQNHMTlz8a2"))
    }

    override fun onTransactionClicked(position: Int, transaction: BankTransaction) {
        startActivity(
            Intent(context, BankTransactionActivity::class.java)
                .putExtra("accountid", "vnuPiFGtcdQNHMTlz8a2")
                .putExtra("transactionid", transaction.id))
    }

}