package io.github.artenes.familybudget

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.github.artenes.familybudget.bankaccount.toCents
import io.github.artenes.familybudget.bankaccount.toMoney
import io.github.artenes.familybudget.data.BankTransaction
import kotlinx.android.synthetic.main.transaction_view.view.*

class BankTransactionFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    lateinit var accountId: String

    lateinit var transactionId: String

    var bankTransaction: BankTransaction? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.transaction_view, container, false)

        view.save.setOnClickListener(this)
        view.description.setOnEditorActionListener(this)

        AppExecutors.INSTANCE.diskIO().run {
            bankTransaction = Repository.INSTANCE.getTransaction(accountId, transactionId)
            if (bankTransaction != null) {
                AppExecutors.INSTANCE.mainThread().run {
                    view.value.setText(bankTransaction?.value?.toMoney().toString())
                    view.description.setText(bankTransaction?.description)
                }
            }
        }

        return view
    }

    private fun save() {
        val value: String = view?.value?.text.toString()
        val description: String = view?.description?.text.toString()

        if (value.isEmpty() || description.isEmpty()) {
            return
        }

        var valueInCents = value.toCents()

        if (view?.debit?.isChecked == true) {
            valueInCents = -valueInCents
        }

        AppExecutors.INSTANCE.diskIO().execute {
            val transaction = if (bankTransaction == null) {
                BankTransaction(value = valueInCents, description = description)
            } else {
                (bankTransaction as BankTransaction).also {
                    it.value = valueInCents
                    it.description = description
                }
            }

            Repository.INSTANCE.saveTransaction(accountId, transaction)
            AppExecutors.INSTANCE.mainThread().execute {
                activity?.finish()
            }
        }
    }

    override fun onClick(v: View?) {
        save()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        save()
        return true
    }

}