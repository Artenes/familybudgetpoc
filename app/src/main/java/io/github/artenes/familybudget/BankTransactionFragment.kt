package io.github.artenes.familybudget

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.github.artenes.familybudget.bankaccount.toCents
import io.github.artenes.familybudget.data.BankTransaction

import kotlinx.android.synthetic.main.transaction_view.view.*

class BankTransactionFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.transaction_view, container, false)

        view.save.setOnClickListener(this)
        view.description.setOnEditorActionListener(this)

        return view
    }

    private fun save() {
        val value: String = view?.value?.text.toString()
        val description: String = view?.description?.text.toString()

        if (value.isEmpty() || description.isEmpty()) {
            return
        }

        val transaction = BankTransaction(value = value.toCents(), description =  description)
        Repository.INSTANCE.addTransactionToBradesco(transaction)
        activity?.finish()
    }

    override fun onClick(v: View?) {
        save()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        save()
        return true
    }

}