package io.github.artenes.familybudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BankTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        supportFragmentManager.beginTransaction().replace(R.id.container, BankTransactionFragment()).commit()
    }

}