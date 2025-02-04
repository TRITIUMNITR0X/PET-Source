package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.network.NetworkMarketDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketBundle
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketTypographyEntity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypographyiesMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TypographyRepository(
    val dataStore: DataStore<Preferences>,
    context: Context,
    private val networkSource: NetworkMarketDataSource = NetworkMarketDataSource(),
    private val localSource: Map<String, ExtendedTypography> = LocalTypographyiesMap,
) {

    val flow: Flow<FontPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapFontPreferences(preferences)
        }

    suspend fun saveTypography(uuid: String) {
        Log.d("ColorThemeHandler", "Saving")
        dataStore.edit { preferences ->
            preferences[KEY_TYPOGRAPHY] = uuid
        }
        Log.d("ColorThemeHandler", "Finalizing Save")
    }

    val defaultTypography = MarketTypographyEntity(
        uuid = "0",
        name = "Default",
        group = "Default",
        unlocked = true
    )

    val marketTypographies: MutableMap<String, MarketTypography> = mutableMapOf()
    private val localTypographies = fetchAllLocalTypographies().toMap()

    private var _allTypographies = MutableStateFlow(mutableMapOf<String, MarketTypography>())
    var allTypographies = _allTypographies.asStateFlow()

    val marketBundles: MutableMap<String, MarketBundle> = mutableMapOf()

    suspend fun fetchAllTypographies() {
        populateTypographiesLocal()
        populateTypographiesRemote()

        unifyRemoteWithLocalTypographies()
    }

    private fun unifyRemoteWithLocalTypographies() {
        marketTypographies.toList().forEach {
            allTypographies.value[it.first] = it.second
        }

        allTypographies.value.toList()
            .sortedBy { it.second.marketTypographyEntity?.priority }
            .forEach {
                val entity = it.second.marketTypographyEntity
                val palette = it.second.typography
                Log.d(
                    "Palette",
                    "${it.first} -> ${entity?.uuid} ${entity?.name} / ${palette.extrasFamily.title}"
                )
            }
    }

    private suspend fun fetchAllRemoteTypographies(): List<MarketTypographyEntity> =
        networkSource.fetchAllTypographys()

    private suspend fun populateTypographiesRemote() {
        val remoteEntities: List<MarketTypographyEntity> = fetchAllRemoteTypographies()
        remoteEntities.forEach { remoteEntity ->
            localTypographies[remoteEntity.uuid]?.let { localTypography ->

                val typography = MarketTypography(
                    marketTypographyEntity = remoteEntity,
                    typography = localTypography
                )

                marketTypographies[remoteEntity.uuid] = typography
            }
        }
    }

    private fun populateTypographiesLocal() {
        localTypographies.toList().forEach {
            allTypographies.value[it.first] = MarketTypography(
                uuid = it.first, typography = it.second
            )
        }
    }

    private fun fetchAllLocalTypographies(): List<Pair<String, ExtendedTypography>> = localSource.toList()

    init {
        Log.d("FontTheme Repository", "Initializing")

        KEY_TYPOGRAPHY = stringPreferencesKey(context.resources.getString(R.string.preference_savedFont))

    }

    suspend fun fetchInitialPreferences() =
        mapFontPreferences(dataStore.data.first().toPreferences())

    private fun mapFontPreferences(preferences: Preferences): FontPreferences {
        return FontPreferences(
            preferences[KEY_TYPOGRAPHY] ?: "0"
        )
    }

    data class FontPreferences(
        val uuid: String
    )

    companion object PreferencesKeys {
        lateinit var KEY_TYPOGRAPHY: Preferences.Key<String>
    }
}
