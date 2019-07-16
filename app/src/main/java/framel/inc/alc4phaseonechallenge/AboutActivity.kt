package framel.inc.alc4phaseonechallenge

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.content_about.*


class AboutActivity : AppCompatActivity() {

    private val url: String = "https://andela.com/alc/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)

        if (Connectivity.isConnected) {

            if (!Connectivity.isConnectedFast) {
                Toast.makeText(this, "Slow internet connection", Toast.LENGTH_LONG).show()
            }

            text_connect.visibility = View.GONE

            web_view_about.webViewClient = object : WebViewClient() {

                override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                    val builder = AlertDialog.Builder(this@AboutActivity)
                    var message = "SSL Certificate error."
                    when (error.primaryError) {
                        SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                        SslError.SSL_EXPIRED -> message = "The certificate has expired."
                        SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                        SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
                    }
                    message += " Do you want to continue anyway?"


                    builder.setTitle("SSL Certificate Error")
                    builder.setMessage(message)
                    builder.setPositiveButton("continue",
                        { dialog, which ->
                            handler.proceed()
                        })
                    builder.setNegativeButton("cancel",
                        { dialog, which ->
                            handler.cancel()
                            this@AboutActivity.finish()
                        })
                    val dialog = builder.create()
                    dialog.show()
                }

                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progress_bar.visibility = View.VISIBLE
                    title = "Loading..."
                }

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                    progress_bar.visibility = View.VISIBLE
                    title = "Loading..."
                    view.loadUrl(url)
                    return true

                }

                override fun onPageFinished(view: WebView, url: String) {

                    super.onPageFinished(view, url)

                    progress_bar.visibility = View.GONE
                    title = view.title
                }


            }

            web_view_about.settings.javaScriptEnabled = true
            web_view_about.loadUrl(url)
        } else {

            web_view_about.visibility = View.GONE
            progress_bar.visibility = View.GONE
            text_connect.visibility = View.VISIBLE

            text_connect.text = getString(R.string.no_internet_text)

        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (web_view_about.canGoBack()) {
                        web_view_about.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }

        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {

        if (web_view_about.canGoBack()) {
            web_view_about.goBack()
        } else {
            finish()
        }
    }


}


