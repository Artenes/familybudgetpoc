package io.github.artenes.familybudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BankTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val accountId = intent?.getStringExtra("accountid") ?: ""

        val fragment = BankTransactionFragment()
        fragment.accountId = accountId
        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }

}