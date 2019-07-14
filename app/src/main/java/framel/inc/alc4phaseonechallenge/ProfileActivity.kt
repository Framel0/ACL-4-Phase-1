package framel.inc.alc4phaseonechallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        text_view_name.text = getString(R.string.name_text_view)
        text_view_track.text = getString(R.string.track_text_view)
        text_view_country.text = getString(R.string.country_text_view)
        text_view_email.text = getString(R.string.email_text_view)
        text_view_phone_number.text = getString(R.string.phone_number_text_view)
        text_view_slack_username.text = getString(R.string.username_text_view)
    }

}
