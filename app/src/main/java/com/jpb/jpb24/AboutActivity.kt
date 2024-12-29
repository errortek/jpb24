package com.jpb.jpb24

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.ui.LibsActivity

class AboutActivity : LibsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        val intent = Intent()
        intent.putExtra(Libs.BUNDLE_FIELDS, Libs.toStringArray(R.string::class.java.fields))
        intent.putExtra(Libs.BUNDLE_LIBS, arrayOf("activeandroid", "caldroid"))
        setIntent(intent)
        */
        intent = LibsBuilder()
            .withEdgeToEdge(true)
            .withSearchEnabled(true)
            .intent(this)
        super.onCreate(savedInstanceState)
    }
}