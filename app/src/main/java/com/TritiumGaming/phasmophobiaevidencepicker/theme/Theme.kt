package com.tritiumgaming.phasmophobiaevidencepicker.theme

import android.R.attr.statusBarColor
import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.ExtendedColorScheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography

@Preview
@Composable
fun SelectiveTheme(
    theme: ExtendedColorScheme = Non_Colorblind,
    typography: ExtendedTypography = Classic,
    //darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit = {}
) {
    // here is the important point, where you will expose custom objects
    CompositionLocalProvider(
        LocalPalette provides theme, // our custom palette
        LocalTypography provides typography // our custom font
    ) {
        MaterialTheme(
            shapes = NormalShapes,
            content = content
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = theme.statusBarColor.toArgb()
            window.navigationBarColor = theme.navigationBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                theme.extrasFamily.isLightMode
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                theme.extrasFamily.isLightMode
        }
    }

}
