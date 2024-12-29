package com.jpb.jpb24

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.LibsConfiguration
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.LibsActivity
import com.mikepenz.aboutlibraries.util.SpecialButton

class AboutActivity : LibsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        val intent = Intent()
        intent.putExtra(Libs.BUNDLE_FIELDS, Libs.toStringArray(R.string::class.java.fields))
        intent.putExtra(Libs.BUNDLE_LIBS, arrayOf("activeandroid", "caldroid"))
        setIntent(intent)
        */
        intent = LibsBuilder()
            .withAboutIconShown(true)
            .withAboutVersionShown(true)
            .withAboutDescription("View device information and specifications with ease!")
            .withActivityTitle("About")
            .withAboutSpecial1("Changes")
            .withAboutSpecial2("Devs")
            .withAboutSpecial3("Credits")
            .withAboutAppName("jpb24")
            .withListener(object : LibsConfiguration.LibsListener {
                override fun onIconClicked(v: View) {
                }

                override fun onIconLongClicked(v: View): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onLibraryAuthorClicked(
                    v: View,
                    library: Library
                ): Boolean {
                    return false
                }

                override fun onLibraryAuthorLongClicked(v: View, library: Library): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onLibraryContentClicked(
                    v: View,
                    library: Library
                ): Boolean {
                    return false
                }

                override fun onLibraryContentLongClicked(v: View, library: Library): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onLibraryBottomClicked(
                    v: View,
                    library: Library
                ): Boolean {
                    return false
                }

                override fun onLibraryBottomLongClicked(v: View, library: Library): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onExtraClicked(
                    v: View,
                    specialButton: SpecialButton
                ): Boolean {
                    val context = v.context
                    when (specialButton) {
                        SpecialButton.SPECIAL1 -> {
                            val contributors = StringBuilder()
                            contributors.append("<b>What's New</b>")
                                .append("<br>- UI redesign, using the SurgeDL (SurgeOS Design Language) libraries")
                                .append("<br>- Introduce an about page")
                                .append("<br>- Dependency updates")
                            MaterialAlertDialogBuilder(context)
                                .setIcon(R.drawable.alert_decagram)
                                .setTitle("Changelog")
                                .setMessage(
                                    HtmlCompat.fromHtml(
                                        contributors.toString(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                    )
                                )
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                        }

                        SpecialButton.SPECIAL2 -> {
                            val contributors = StringBuilder()
                            contributors.append("<b>jpb</b>")
                                .append(" [")
                                .append(getHyperLink("https://jpb896.vercel.app"))
                                .append("]")
                                .append("<br>")
                            MaterialAlertDialogBuilder(context)
                                .setIcon(R.drawable.account_multiple)
                                .setTitle("Developers")
                                .setMessage(
                                    HtmlCompat.fromHtml(
                                        contributors.toString(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                    )
                                )
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                        }

                        SpecialButton.SPECIAL3 -> {
                            val content = StringBuilder()
                            val list = listOf(
                                "https://pictogrammers.com/library/mdi/"
                            )
                            content.append("<b>").append("Acknowledgements").append("</b>").append("<br>")
                            content.append(getAcknowledgementHtmlString(context, list)).append("<br>")
                            MaterialAlertDialogBuilder(context)
                                .setIcon(R.drawable.note_text)
                                .setTitle("Credits")
                                .setMessage(
                                    HtmlCompat.fromHtml(
                                        content.toString(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                    )
                                )
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                        }
                    }
                    return true
                }
            })
            .intent(this)

    super.onCreate(savedInstanceState)
    }

    private fun getHyperLink(url: String): String {
        return String.format("<a href=\"%s\">%s</a>", url, url)
    }

    private fun getAcknowledgementHtmlString(context: Context, list: List<String>): String {
        val sb = StringBuilder()

        sb.append(context.getString(R.string.resource_declaration)).append("<br>")
        list.forEach { sb.append(getHyperLink(it)).append("<br>") }
        return sb.toString()
    }
}