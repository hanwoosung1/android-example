package com.example.usersactivtiy

import android.view.View

interface Interaction: View.OnClickListener {
    fun onBannerItemClicked(bannerItem: BannerItem)
}