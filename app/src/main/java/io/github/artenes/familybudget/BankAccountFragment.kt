package io.github.artenes.familybudget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.artenes.familybudget.bank_account.BankAccountAdapter
import io.github.artenes.familybudget.data.BankAccount
import io.github.artenes.familybudget.data.BankTransaction
import kotlinx.android.synthetic.main.recycler_view_layout.view.*

class BankAccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_layout, container, false)

        val adapter: BankAccountAdapter = BankAccountAdapter()
        val account = BankAccount(balance = 5000)

        account.transactions.add(BankTransaction(value = 4000))
        account.transactions.add(BankTransaction(value = 6000))
        account.transactions.add(BankTransaction(value = 7000))

        adapter.setData(account)

        view.list.layoutManager = LinearLayoutManager(container?.context)
        view.list.adapter =  adapter

        return view
    }

}