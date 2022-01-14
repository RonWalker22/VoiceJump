package org.voicejump.config

import com.intellij.openapi.options.Configurable
import org.voicejump.config.AceConfig.Companion.settings
import org.voicejump.input.KeyLayoutCache

class AceConfigurable: Configurable {
  private val panel by lazy(::AceSettingsPanel)

  override fun getDisplayName() = "VoiceJump"

  override fun createComponent() = panel.rootPanel

  override fun isModified() =
    panel.allowedChars != settings.allowedChars ||
      panel.keyboardLayout != settings.layout ||
      panel.cycleMode1 != settings.cycleMode1 ||
      panel.cycleMode2 != settings.cycleMode2 ||
      panel.cycleMode3 != settings.cycleMode3 ||
      panel.cycleMode4 != settings.cycleMode4 ||
      panel.minQueryLengthInt != settings.minQueryLength ||
      panel.jumpModeColor != settings.jumpModeColor ||
      panel.jumpEndModeColor != settings.jumpEndModeColor ||
      panel.targetModeColor != settings.targetModeColor ||
      panel.definitionModeColor != settings.definitionModeColor ||
      panel.textHighlightColor != settings.textHighlightColor ||
      panel.tagForegroundColor != settings.tagForegroundColor ||
      panel.tagBackgroundColor != settings.tagBackgroundColor ||
      panel.searchWholeFile != settings.searchWholeFile ||
      panel.mapToASCII != settings.mapToASCII ||
      panel.showSearchNotification != settings.showSearchNotification

  override fun apply() {
    settings.allowedChars = panel.allowedChars
    settings.layout = panel.keyboardLayout
    settings.cycleMode1 = panel.cycleMode1
    settings.cycleMode2 = panel.cycleMode2
    settings.cycleMode3 = panel.cycleMode3
    settings.cycleMode4 = panel.cycleMode4
    settings.minQueryLength = panel.minQueryLengthInt ?: settings.minQueryLength
    panel.jumpModeColor?.let { settings.jumpModeColor = it }
    panel.jumpEndModeColor?.let { settings.jumpEndModeColor = it }
    panel.targetModeColor?.let { settings.targetModeColor = it }
    panel.definitionModeColor?.let { settings.definitionModeColor = it }
    panel.textHighlightColor?.let { settings.textHighlightColor = it }
    panel.tagForegroundColor?.let { settings.tagForegroundColor = it }
    panel.tagBackgroundColor?.let { settings.tagBackgroundColor = it }
    settings.searchWholeFile = panel.searchWholeFile
    settings.mapToASCII = panel.mapToASCII
    settings.showSearchNotification = panel.showSearchNotification
    KeyLayoutCache.reset(settings)
  }

  override fun reset() = panel.reset(settings)
}
