package com.cmedina.condorlabs.view.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.core.BaseActivity
import com.cmedina.condorlabs.core.SPLASH_DURATION
import com.cmedina.condorlabs.view.team.TeamListActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Navigate to the main View -> TeamListActivity
        Handler().postDelayed({
            finish()
            startActivity(TeamListActivity.newIntent(this))
        }, SPLASH_DURATION)
    }

}