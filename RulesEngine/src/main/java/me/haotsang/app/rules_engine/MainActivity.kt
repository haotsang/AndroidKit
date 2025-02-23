package me.haotsang.app.rules_engine

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.haotsang.app.rules_engine.core.RulesManager
import me.haotsang.app.rules_engine.core.data.Instruct

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<View>(R.id.btn).setOnClickListener {
            val instruct = Instruct(
                id = 0,
                title = "",
                condition_value = "",
                action_value = "",
                auto_execute = true
            )

            RulesManager.addRules(this, instruct, auto = true)
        }

    }
}