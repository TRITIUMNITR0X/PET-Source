package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.timer.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.sanitywarn.SanityWarningView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanityWarnHuntView : SanityWarningView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.timerModel?.currentPhase?.collectLatest {
                setState(it == PhaseTimerModel.Phase.HUNT)
            }
        }
    }
}