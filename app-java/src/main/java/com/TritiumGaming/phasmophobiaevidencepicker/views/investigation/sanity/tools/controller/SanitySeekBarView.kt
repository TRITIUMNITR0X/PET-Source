package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanitySeekBarView : AppCompatSeekBar {

    private lateinit var evidenceViewModel: EvidenceViewModel

    var onProgressChangedListener: OnSanityBarProgressChangedListener? = null
    abstract class OnSanityBarProgressChangedListener {
        abstract fun onChange()
        abstract fun onReset()
        abstract fun onInvalidate()
    }
    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun init(evidenceViewModel: EvidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel

        progress = evidenceViewModel.sanityModel?.insanityPercent?.value?.toInt() ?: 0

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    evidenceViewModel.sanityModel?.setProgressManually(progress.toLong())
                    evidenceViewModel.sanityModel?.tick()

                    onProgressChangedListener?.onChange()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        initObservables()

        invalidate()
    }


    private fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.sanityModel?.insanityPercent?.collectLatest {
                progress = 100 - (it*100).toInt()
                onProgressChangedListener?.onChange()
                invalidate()
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        //onProgressChangedListener?.onInvalidate()
    }
}
