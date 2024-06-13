package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels

import android.content.Context
import android.content.res.Resources
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DifficultyCarouselModel(
    context: Context,
    val evidenceViewModel: EvidenceViewModel
) {

    enum class Difficulty {
        AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY
    }

    private var titles = arrayOfNulls<String>(0)
        set(value) {
            field = value
            updateCurrentDifficultyName()
        }
    private var times = LongArray(0)

    private val difficultyCount: Int
        get() = titles.size

    private val _difficultyIndex: MutableStateFlow<Int> =
        MutableStateFlow(Difficulty.AMATEUR.ordinal)
    var difficultyIndex = _difficultyIndex.asStateFlow()
    private fun updateDifficultyIndex(index: Int) {
        _difficultyIndex.value = index
        evidenceViewModel.timerModel?.setTimeRemaining(currentDifficultyTime)
        evidenceViewModel.timerModel?.resetTimer()

        updateCurrentDifficultyName()
    }

    val currentDifficultyTime: Long
        get() {
            return times[difficultyIndex.value]
        }

    private val _currentDifficultyName: MutableStateFlow<String> = MutableStateFlow("?")
    val currentDifficultyName = _currentDifficultyName.asStateFlow()
    private fun updateCurrentDifficultyName() {
        _currentDifficultyName.value = titles.getOrNull(difficultyIndex.value) ?: "???"
    }

    val responseTypeKnown: Boolean
        get() = difficultyIndex.value < 2

    init {
        try {
            val difficultyNames = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_names_array)
            titles = difficultyNames
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        try {
            val difficultyTimes = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_times_array)
            setTimes(difficultyTimes)
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        updateCurrentDifficultyName()
    }

    private fun setTimes(times: Array<String>) {
        val temp = LongArray(times.size)
        for (i in times.indices) temp[i] = times[i].toLong()

        setTimes(temp)
    }

    private fun setTimes(difficultyTimes: LongArray) {
        this.times = difficultyTimes
    }


    fun incrementIndex() {
        var i = difficultyIndex.value + 1
        if (i >= difficultyCount) {
            i = 0
        }
        updateDifficultyIndex(i)

        if (evidenceViewModel.hasSanityModel()) {
            evidenceViewModel.sanityModel?.warnTriggered = false
        }
    }

    fun decrementIndex() {
        var i = difficultyIndex.value - 1
        if (i < 0) {
            i = difficultyCount - 1
        }
        updateDifficultyIndex(i)

        if (evidenceViewModel.hasSanityModel()) {
            evidenceViewModel.sanityModel?.warnTriggered = false
        }
    }

    fun resetSanityData() {
        evidenceViewModel.sanityModel?.reset()
    }

    fun isDifficulty(difficultyIndex: Int): Boolean {
        return this.difficultyIndex.value == difficultyIndex
    }


    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    val currentDifficultyRate: Float
        get() {
            val diffIndex =
                evidenceViewModel.difficultyCarouselData?.difficultyIndex?.value ?: return 1f
            if (diffIndex >= 0 && diffIndex < SanityModel.DIFFICULTY_MODIFIER.size) {
                return SanityModel.DIFFICULTY_MODIFIER[diffIndex]
            }
            return 1f
        }

}
