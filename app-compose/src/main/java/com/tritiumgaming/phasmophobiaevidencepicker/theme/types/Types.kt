package com.tritiumgaming.phasmophobiaevidencepicker.theme.types

import androidx.annotation.StringRes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.CuyabraTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.DefaultTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.DigitalDreamTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.EastSeaDokdoTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.GeoTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.JetbrainsMonoTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.LazyDogTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.LongCangTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.NeuchaTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.NewTegominTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.NorseTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.RobotoMonoTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.Type.Companion.TypewriterTextStyle
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_android
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_brick
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_clean
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_jetbrainsmono
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_journal
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_longcang
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_neucha
import com.tritiumgaming.phasmophobiaevidencepicker.theme.title_newtegmon

val DefaultFont = Typography(
    displayLarge = DefaultTextStyle.copy(
        fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp
    ),
    displayMedium = DefaultTextStyle.copy(
        fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = 0.sp
    ),
    displaySmall = DefaultTextStyle.copy(
        fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp
    ),
    headlineLarge = DefaultTextStyle.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    headlineMedium = DefaultTextStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    headlineSmall = DefaultTextStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    titleLarge = DefaultTextStyle.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        lineBreak = LineBreak.Heading
    ),
    titleMedium = DefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Medium,
        lineBreak = LineBreak.Heading
    ),
    titleSmall = DefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium,
        lineBreak = LineBreak.Heading
    ),
    labelLarge = DefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = DefaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = DefaultTextStyle.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = DefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        lineBreak = LineBreak.Paragraph
    ),
    bodyMedium = DefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        lineBreak = LineBreak.Paragraph
    ),
    bodySmall = DefaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        lineBreak = LineBreak.Paragraph
    )
)

private val BaseFont = ExtendedTypography(
    extrasFamily = ExtrasFamily(
        title = title_classic
    ),

    primary = CustomFontFamily(
        regular = NorseTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NorseTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = CustomFontFamily(
        regular = EastSeaDokdoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        bold = EastSeaDokdoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = CustomFontFamily(
        regular = DigitalDreamTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = DigitalDreamTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        boldNarrow = DigitalDreamTextStyle.copy(
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
        )
    ),

    quaternary = CustomFontFamily(
        regular = TypewriterTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = TypewriterTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    )
)

val Classic = BaseFont.copy(
    extrasFamily = BaseFont.extrasFamily.copy(),

    primary = BaseFont.primary.copy(),
    secondary = BaseFont.secondary.copy(),
    tertiary = BaseFont.tertiary.copy(),
    quaternary = BaseFont.quaternary.copy()
)

val AndroidTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_android
    ),

    primary = BaseFont.primary.copy(
        regular = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        boldNarrow = RobotoMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    )
)

val JournalTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_journal
    ),

    primary = BaseFont.primary.copy(
        regular = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
        boldNarrow = LazyDogTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    )
)

val BrickTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_brick
    ),

    primary = BaseFont.primary.copy(
        regular = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = GeoTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    )
)

val CleanTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_clean
    ),

    primary = BaseFont.primary.copy(
        regular = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = CuyabraTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    )
)

val LongCangTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_longcang
    ),

    primary = BaseFont.primary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    quaternary = BaseFont.quaternary.copy(
        regular = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = LongCangTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
    )

)

val NewTegominTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_newtegmon
    ),

    primary = BaseFont.primary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    quaternary = BaseFont.quaternary.copy(
        regular = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NewTegominTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    )

)

val NeuchaTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_neucha
    ),

    primary = BaseFont.primary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        boldNarrow = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        )
    ),

    quaternary = BaseFont.quaternary.copy(
        regular = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = NeuchaTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        ),
    )
)

val JetBrainsMonoTypography = BaseFont.copy(

    extrasFamily = BaseFont.extrasFamily.copy(
        title = title_jetbrainsmono
    ),

    primary = BaseFont.primary.copy(
        regular = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    secondary = BaseFont.secondary.copy(
        regular = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal
        ),
        bold = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )
    ),

    tertiary = BaseFont.tertiary.copy(
        regular = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Italic
        ),
        bold = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Italic
        ),
        boldNarrow = JetbrainsMonoTextStyle.copy(
            fontStyle = FontStyle.Italic
        )
    )
)


@Immutable
data class ExtrasFamily(
    @StringRes val title: Int = title_classic
)

@Immutable
data class ExtendedTypography(
    val extrasFamily: ExtrasFamily = ExtrasFamily(
        title = title_classic
    ),

    val primary: CustomFontFamily = CustomFontFamily(),
    val secondary: CustomFontFamily = CustomFontFamily(),
    val tertiary: CustomFontFamily = CustomFontFamily(),
    val quaternary: CustomFontFamily = CustomFontFamily()
)

@Immutable
data class CustomFontFamily(
    val regular: TextStyle = TextStyle(),
    val bold: TextStyle = TextStyle(),
    val narrow: TextStyle = TextStyle(),
    val boldNarrow: TextStyle = TextStyle()
)

val LocalTypography = staticCompositionLocalOf { ExtendedTypography() }

val LocalTypographys = listOf<TypographyData>(
    TypographyData("c29cJglM92MLWN1RKRyK8qyAD", Classic),
    TypographyData("8Jk15N2GB6PBopXvmEluU2eoS", AndroidTypography),
    TypographyData("7q1Nza1o0Nvt16YyNXNkJ590F", JournalTypography),
    TypographyData("3a1vXEZveFEWrf5RdVxTJI6pF", BrickTypography),
    TypographyData("93Ph8a2SLU3YEupV54TKMKJAO", CleanTypography),
    TypographyData("8UEl0G5HXx119AXh69OeIUPCB", LongCangTypography),
    TypographyData("8rX9hVOyV8eIZmz3ZQaHgrnan", NewTegominTypography),
    TypographyData("DPre8Bscm8Tf3pwyQw7HxBznt", NeuchaTypography),
    TypographyData("3vAD75LdzvZN3zBjab5z19zpc", JetBrainsMonoTypography),
)

data class TypographyData(
    val uuid: String = "0",
    val typography: ExtendedTypography = ExtendedTypography()
)
