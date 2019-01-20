package io.github.artenes.familybudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BankTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val accountId = intent?.getStringExtra("accountid") ?: ""
        val transactionId = intent?.getStringExtra("transactionid") ?: ""

        val fragment = BankTransactionFragment()
        fragment.accountId = accountId
        fragment.transactionId = transactionId
        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }

}