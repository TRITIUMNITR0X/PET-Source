package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.sanitywarn

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.sanitywarn.alerts.SanityWarnActionView
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.sanitywarn.alerts.SanityWarnHuntView
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.sanitywarn.alerts.SanityWarnSetupView

class SanityWarningLayout : ConstraintLayout {

    lateinit var sanityWarnSetupView: SanityWarnSetupView
    lateinit var sanityWarnActionView: SanityWarnActionView
    lateinit var sanityWarnHuntView: SanityWarnHuntView

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    private fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_warnings, this)

        sanityWarnSetupView = findViewById(R.id.evidence_sanitymeter_phase_setup)
        sanityWarnActionView = findViewById(R.id.evidence_sanitymeter_phase_action)
        sanityWarnHuntView = findViewById(R.id.evidence_sanitymeter_huntwarning)

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(evidenceViewModel: EvidenceViewModel) {
        sanityWarnSetupView.init(evidenceViewModel)
        sanityWarnActionView.init(evidenceViewModel)
        sanityWarnHuntView.init(evidenceViewModel)
    }

}
