package io.github.artenes.familybudget

import android.content.Intent
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

class BankAccountFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_layout, container, false)

        val adapter = BankAccountAdapter()

        adapter.setData(Repository.INSTANCE.getBradesco())

        view.list.layoutManager = LinearLayoutManager(container?.context)
        view.list.adapter =  adapter
        view.fab.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        startActivity(Intent(context, BankTransactionActivity::class.java))
    }

}