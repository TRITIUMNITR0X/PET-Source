package com.tritiumgaming.phasmophobiaevidencepicker.activities.mainmenus

import androidx.fragment.app.activityViewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.activities.pet.PETFragment
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.MainMenuViewModel

abstract class MainMenuFragment : PETFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()
    protected val newsLetterViewModel: NewsletterViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    protected fun initAdView(adView: AdView) {
        mainMenuViewModel.let { mainMenuViewModel ->
            try {
                MobileAds.initialize(requireActivity()) { }
                if (!mainMenuViewModel.hasAdRequest()) {
                    mainMenuViewModel.adRequest = AdRequest.Builder().build() }
                mainMenuViewModel.adRequest?.let { adRequest -> adView.loadAd(adRequest) }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

}