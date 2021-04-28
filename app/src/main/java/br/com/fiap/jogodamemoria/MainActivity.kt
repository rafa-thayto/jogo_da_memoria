package br.com.fiap.jogodamemoria

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Action")
        builder.setMessage("Do you really want to quit the match? ")
        builder.setPositiveButton(
            "QUIT"
        ) { dialog, which -> super.onBackPressed() }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> null }
        builder.show()
    }
}