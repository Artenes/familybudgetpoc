package io.github.artenes.familybudget

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.artenes.familybudget.bankaccount.BankAccountAdapter
import kotlinx.android.synthetic.main.recycler_view_layout.view.*

class BankAccountFragment : Fragment(), View.OnClickListener {

    private val adapter = BankAccountAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_layout, container, false)
        view.list.layoutManager = LinearLayoutManager(container?.context)
        view.list.adapter =  adapter
        view.fab.setOnClickListener(this)

        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.setData(Repository.INSTANCE.getBradesco())
    }

    override fun onClick(v: View?) {
        startActivity(Intent(context, BankTransactionActivity::class.java))
    }

}