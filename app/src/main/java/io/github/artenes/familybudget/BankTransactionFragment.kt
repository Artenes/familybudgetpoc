package io.github.artenes.familybudget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.artenes.familybudget.bank_account.toCents
import io.github.artenes.familybudget.data.BankTransaction

import kotlinx.android.synthetic.main.transaction_view.view.*

class BankTransactionFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.transaction_view, container, false)

        view.save.setOnClickListener(this)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onClick(v: View?) {
        val value: String = view?.value?.text.toString()
        val description: String = view?.description?.text.toString()

        if (value.isEmpty() || description.isEmpty()) {
            return
        }

        val transaction = BankTransaction(value = value.toCents(), description =  description)
        Repository.INSTANCE.addTransactionToBradesco(transaction)
        activity?.finish()
    }

}