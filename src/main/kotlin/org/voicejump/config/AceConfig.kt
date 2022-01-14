package org.voicejump.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.application
import org.voicejump.input.KeyLayoutCache

/**
 * Ensures consistency between [AceSettings] and [AceSettingsPanel].
 * Persists the state of the VoiceJump IDE settings across IDE restarts.
 * [https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html]
 */
@State(name = "AceConfig", storages = [(Storage("\$APP_CONFIG\$/VoiceJump.xml"))])
class AceConfig: PersistentStateComponent<AceSettings> {
  private var aceSettings = AceSettings()

  companion object {
    val settings get() = application.getService(AceConfig::class.java).aceSettings

    // @formatter:off
    val layout get()              = settings.layout
    val cycleModes get()          = settings.let { arrayOf(it.cycleMode1, it.cycleMode2, it.cycleMode3, it.cycleMode4) }
    val minQueryLength get()      = settings.minQueryLength
    val jumpModeColor get()       = settings.jumpModeColor
    val jumpEndModeColor get()    = settings.jumpEndModeColor
    val targetModeColor get()     = settings.targetModeColor
    val definitionModeColor get() = settings.definitionModeColor
    val textHighlightColor get()  = settings.textHighlightColor
    val tagForegroundColor get()  = settings.tagForegroundColor
    val tagBackgroundColor get()  = settings.tagBackgroundColor
    val searchWholeFile get()     = settings.searchWholeFile
    val mapToASCII get()        = settings.mapToASCII
    val showSearchNotification get()          = settings.showSearchNotification
    // @formatter:on
  }

  override fun getState() = aceSettings

  override fun loadState(state: AceSettings) {
    aceSettings = state
    KeyLayoutCache.reset(state)
  }
}
