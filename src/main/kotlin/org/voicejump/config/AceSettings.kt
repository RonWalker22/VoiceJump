package org.voicejump.config

import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.annotations.OptionTag
import org.voicejump.input.JumpMode
import org.voicejump.input.KeyLayout
import org.voicejump.input.KeyLayout.QWERTY
import java.awt.Color

data class AceSettings(
  var layout: KeyLayout = QWERTY,
  var allowedChars: String = layout.allChars,
  var cycleMode1: JumpMode = JumpMode.JUMP,
  var cycleMode2: JumpMode = JumpMode.DECLARATION,
  var cycleMode3: JumpMode = JumpMode.TARGET,
  var cycleMode4: JumpMode = JumpMode.JUMP_END,
  var minQueryLength: Int = 1,

  @OptionTag("jumpModeRGB", converter = ColorConverter::class)
  var jumpModeColor: Color = Color(0xFFFFFF),

  @OptionTag("jumpEndModeRGB", converter = ColorConverter::class)
  var jumpEndModeColor: Color = Color(0x33E78A),

  @OptionTag("targetModeRGB", converter = ColorConverter::class)
  var targetModeColor: Color = Color(0xFFB700),

  @OptionTag("definitionModeRGB", converter = ColorConverter::class)
  var definitionModeColor: Color = Color(0x6FC5FF),

  @OptionTag("textHighlightRGB", converter = ColorConverter::class)
  var textHighlightColor: Color = Color(0x394B58),

  @OptionTag("tagForegroundRGB", converter = ColorConverter::class)
  var tagForegroundColor: Color = Color(0xFFFFFF),

  @OptionTag("tagBackgroundRGB", converter = ColorConverter::class)
  var tagBackgroundColor: Color = Color(0x008299),

  var searchWholeFile: Boolean = true,

  var mapToASCII : Boolean = false,

  var showSearchNotification : Boolean = false
)

internal class ColorConverter: Converter<Color>() {
  override fun toString(value: Color) = value.rgb.toString()
  override fun fromString(value: String) = value.toIntOrNull()?.let(::Color)
}
