package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

abstract class InvestigationFragment : PETFragment {
    protected var investigationViewModel: InvestigationViewModel? = null
    protected var objectivesViewModel: ObjectivesViewModel? = null
    protected var mapMenuViewModel: MapMenuViewModel? = null

    protected var adRequest: AdRequest? = null

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.init()

        try {
            (requireActivity() as InvestigationActivity).initNavigationComponents()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        initAd(view.findViewById(R.id.adView))
    }

    override fun initViewModels() {
        initGlobalPreferencesViewModel()
        initinvestigationViewModel()
        initObjectivesViewModel()
        initMapMenuViewModel()
    }

    private fun initMapMenuViewModel() {
        if (mapMenuViewModel == null) {
            mapMenuViewModel =
                ViewModelProvider(requireActivity())[MapMenuViewModel::class.java]
            mapMenuViewModel?.init(requireContext())
        }
    }

    private fun initObjectivesViewModel() {
        if (objectivesViewModel == null) {
            objectivesViewModel =
                ViewModelProvider(requireActivity())[ObjectivesViewModel::class.java]
            objectivesViewModel?.init(requireContext())
        }
    }

    private fun initinvestigationViewModel() {
        if (investigationViewModel == null) {
            investigationViewModel =
                ViewModelProvider(requireActivity()).get(InvestigationViewModel::class.java)
            investigationViewModel?.init(requireContext())
        }
    }

    protected fun initAd(mAdView: AdView?) {
        if (mAdView == null) { return }

        MobileAds.initialize(requireContext()) { }
        adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest!!)
    }

    override fun backPressedHandler() {
        try {
            if ((requireActivity() as InvestigationActivity).closeNavigationDrawer()) { return }
        } catch (e: IllegalStateException) { e.printStackTrace() }

        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
            return
        }

        Log.d("Backstack", "Popping")
        try {
            if (!findNavController(requireView()).popBackStack()) {
                Log.d("Backstack", "Could not Pop")
                requireActivity().finish()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    /*
    override fun saveStates() {
        super.saveStates()
    }
    */

    abstract fun reset()

}

