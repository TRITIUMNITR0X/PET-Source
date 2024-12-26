package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet.PETScreen


@Composable
@Preview
private fun InvestigationScreenPreview() {
    InvestigationScreen {}
}

@Composable
fun InvestigationScreen(
    content: @Composable () -> Unit
) {

    PETScreen(
        content = content
    )

}