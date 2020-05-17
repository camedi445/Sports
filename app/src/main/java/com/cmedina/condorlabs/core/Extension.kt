package com.cmedina.condorlabs.core

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun View?.visible() {
    if (this != null)
        this.visibility = View.VISIBLE
}

fun View?.gone() {
    if (this != null)
        this.visibility = View.GONE
}

fun ImageView.loadUrl(url: String?) {
    if (url != null) {
        Picasso.get().load(url).into(this)
    }
}

fun View.setUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.visible()
        var finalURL = url
        if (!finalURL.startsWith("http://") && !finalURL.startsWith("https://")) {
            finalURL = "http://$url"
        }
        this.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(finalURL))
            context.startActivity(browserIntent)
        }
    } else {
        this.gone()
    }
}
