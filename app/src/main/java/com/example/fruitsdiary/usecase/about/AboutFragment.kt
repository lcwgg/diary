package com.example.fruitsdiary.usecase.about

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView

import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.FragmentAboutBinding
import com.example.fruitsdiary.usecase.home.HomeAbstractFragment

class AboutFragment : HomeAbstractFragment() {

    private lateinit var mWebView: WebView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAboutBinding>(inflater, R.layout.fragment_about, container, false)
        val rootView = binding.root
        mWebView = binding.aboutWebview
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebView.webChromeClient = WebChromeClient()
        mWebView.settings.javaScriptEnabled = false
        mWebView.loadUrl(getString(R.string.about_url))
    }

    override fun setFabAction(fab: FloatingActionButton) {
        fab.setImageResource(android.R.drawable.ic_dialog_email)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.about_email))
            if (intent.resolveActivity(activity!!.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    override fun getFragmentName(): String {
        return FRAGMENT_NAME
    }

    companion object {
        private const val FRAGMENT_NAME = "About"
    }


}
