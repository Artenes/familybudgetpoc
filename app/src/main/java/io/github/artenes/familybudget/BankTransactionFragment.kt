package io.github.artenes.familybudget

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.github.artenes.familybudget.bankaccount.toCents
import io.github.artenes.familybudget.bankaccount.toEditableMoneyString
import io.github.artenes.familybudget.data.BankTransaction
import kotlinx.android.synthetic.main.transaction_view.view.*
import java.util.NoSuchElementException

class BankTransactionFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    lateinit var accountId: String

    lateinit var transactionId: String

    var bankTransaction: BankTransaction = BankTransaction()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.transaction_view, container, false)

        view.save.setOnClickListener(this)
        view.description.setOnEditorActionListener(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bind(view as View)
    }

    fun bind(view: View) {
        AppExecutors.INSTANCE.diskIO().run {

            bankTransaction = try {
                Repository.INSTANCE.getTransaction(accountId, transactionId)
            } catch (exception: NoSuchElementException) {
                BankTransaction()
            }

            AppExecutors.INSTANCE.mainThread().run {

                val isNew = bankTransaction.value == 0

                if (!isNew) {
                    view.value.setText(bankTransaction.value.toEditableMoneyString())
                    view.description.setText(bankTransaction.description)
                    view.debit.isChecked = bankTransaction.value < 0
                } else {
                    view.value.setText("")
                    view.description.setText("")
                    view.debit.isChecked = true
                }

            }
        }
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
            bankTransaction.value = valueInCents
            bankTransaction.description = description
            Repository.INSTANCE.saveTransaction(accountId, bankTransaction)

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