package org.acejump.input

import com.intellij.openapi.editor.colors.impl.AbstractColorsScheme
import org.acejump.config.AceConfig
import java.awt.Color

/**
 * Describes modes that determine the behavior of a "jump" to a tag.
 * Most modes have two variations:
 *   - **Default jump** happens when jumping without holding the Shift key
 *   - **Shift jump** happens when jumping while holding the Shift key
 */
enum class JumpMode {
  /**
   * Default value at the start of a session. If the session does not get
   * assigned a proper [JumpMode] by the time the user requests a jump, the
   * results of the jump are undefined.
   */
  DISABLED {
    override val canJump = true
   },
  
  /**
   * On default jump, places the caret at the first character of the search
   * query. On shift jump, does the above but also selects all text between
   * the original and new caret positions.
   */
  JUMP {
    override val canJump = true
  },
  
  /**
   * On default jump, places the caret at the end of a word. Word detection
   * uses [Character.isJavaIdentifierPart] to count some special characters,
   * such as underscores, as part of a word. If there is no word at the first
   * character of the search query, then the caret is placed after the last
   * character of the search query.
   *
   * On shift jump, does the above but also selects all text between
   * the original and new caret positions.
   */
  JUMP_END {
    override val canJump = true
  },

  /**
   * On default jump, places the caret at the start of a word. Word detection
   * uses [Character.isJavaIdentifierPart] to count some special characters,
   * such as underscores, as part of a word. If there is no word at the first
   * character of the search query, then the caret is placed after the last
   * character of the search query.
   *
   */
  JUMP_START {
    override val canJump = true
  },


  /**
   * On default jump, deletes word. Word detection
   * uses [Character.isJavaIdentifierPart] to count some special characters,
   * such as underscores, as part of a word. If there is no word at the first
   * character of the search query, then the caret is placed after the last
   * character of the search query.
   *
   * On both default jump and shift jump, the caret will never move from its original position.
   */
  CHUNK {
    override val canJump = false
  },
  
  /**
   * On default jump, places the caret at the end of a word, and also selects
   * the entire word. Word detection uses [Character.isJavaIdentifierPart] to
   * count some special characters, such as underscores, as part of a word.
   * If there is no word at the first character of the search query, then the
   * caret is placed after the last character of the search query, and all
   * text between the start and end of the search query is selected.
   *
   * On shift jump, does the above but also selects all text between the
   * start of the original caret position and the end of the new selection.
   */
  TARGET {
    override val canJump = true
  },
  
  /**
   * On default jump, performs the Go To Declaration action, available
   * via `Navigate | Declaration or Usages`.
   *
   * On shift jump, performs the Go To Type Declaration action, available
   * via `Navigate | Type Declaration`.
   *
   * Always places the caret at the first character of the search query.
   */
  DECLARATION {
    override val canJump = true
  };

  /**
   * Decides if the caret can move from its original position during a "jump".
   */
  abstract val canJump: Boolean

  val caretColor: Color get() = when (this) {
    DISABLED    -> AbstractColorsScheme.INHERITED_COLOR_MARKER
    JUMP        -> AceConfig.jumpModeColor
    JUMP_END    -> AceConfig.jumpEndModeColor
    JUMP_START  -> AceConfig.jumpModeColor
    CHUNK       -> AceConfig.jumpModeColor
    TARGET      -> AceConfig.targetModeColor
    DECLARATION -> AceConfig.definitionModeColor
  }

  override fun toString() = when (this) {
    DISABLED    -> "(Skip)"
    JUMP        -> "Jump"
    JUMP_END    -> "Jump to End"
    JUMP_START  -> "Jump to Start"
    CHUNK       -> "Delete Word"
    TARGET      -> "Target"
    DECLARATION -> "Definition"
  }
}
