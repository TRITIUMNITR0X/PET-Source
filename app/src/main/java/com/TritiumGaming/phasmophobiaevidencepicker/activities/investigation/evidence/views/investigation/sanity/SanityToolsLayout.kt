package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.carousel.DifficultyCarouselLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.carousel.MapCarouselLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.controller.SanityTrackerLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.SanityWarningLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.timer.PhaseTimerLayout
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel

class SanityToolsLayout : ConstraintLayout {

    private var sanityWarningLayout: SanityWarningLayout
    private var phaseTimerLayout: PhaseTimerLayout
    private var sanityTrackerView: SanityTrackerLayout

    private var mapCarouselLayout: MapCarouselLayout
    private var difficultyCarouselLayout: DifficultyCarouselLayout

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.layout_sanity_tools, this)

        sanityWarningLayout = findViewById(R.id.sanityWarningLayout)
        phaseTimerLayout = findViewById(R.id.phaseTimerLayout)
        sanityTrackerView = findViewById(R.id.sanityTrackerView)

        mapCarouselLayout = findViewById(R.id.mapCarouselLayout)
        difficultyCarouselLayout = findViewById(R.id.difficultyCarouselLayout)

        setDefaults()
    }

    private fun setDefaults() { }

    fun init(investigationViewModel: InvestigationViewModel) {
        sanityWarningLayout.init(investigationViewModel)
        phaseTimerLayout.init(investigationViewModel)
        sanityTrackerView.init(investigationViewModel)

        mapCarouselLayout.init(investigationViewModel)
        difficultyCarouselLayout.init(investigationViewModel)
    }
}
