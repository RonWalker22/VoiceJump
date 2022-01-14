package org.voicejump.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.util.IncorrectOperationException
import org.voicejump.input.JumpMode
import org.voicejump.input.JumpMode.*
import org.voicejump.session.Session
import org.voicejump.session.SessionManager

/**
 * Base class for keyboard-activated actions that create or update an VoiceJump [Session].
 */
sealed class AceAction: DumbAwareAction() {
  final override fun update(action: AnActionEvent) {
    action.presentation.isEnabled = action.getData(EDITOR) != null
  }

  final override fun actionPerformed(e: AnActionEvent) {
    val editor = e.getData(EDITOR) ?: return
    val project = e.project

    if (project != null) {
      try {
        val openEditors = FileEditorManagerEx.getInstanceEx(project).splitters.selectedEditors
          .mapNotNull { (it as? TextEditor)?.editor }
          .sortedBy { if (it === editor) 0 else 1 }
        invoke(SessionManager.start(editor, openEditors))
      } catch (e: IncorrectOperationException) {
        invoke(SessionManager.start(editor))
      }
    } else {
      invoke(SessionManager.start(editor))
    }
  }
  
  abstract operator fun invoke(session: Session)

  /**
   * Generic action type that toggles a specific [JumpMode].
   */
  abstract class BaseToggleJumpModeAction(private val mode: JumpMode): AceAction() {
    final override fun invoke(session: Session) = session.toggleJumpMode(mode)
  }

  /**
   * Initiates an VoiceJump session in the first [JumpMode], or cycles to the next [JumpMode] as defined in configuration.
   */
  class ActivateOrCycleMode: AceAction() {
    override fun invoke(session: Session) = session.cycleNextJumpMode()
  }

  /**
   * Initiates an VoiceJump session in the last [JumpMode], or cycles to the previous [JumpMode] as defined in configuration.
   */
  class ActivateOrReverseCycleMode: AceAction() {
    override fun invoke(session: Session) = session.cyclePreviousJumpMode()
  }

  // @formatter:off

  class ToggleJumpMode        : BaseToggleJumpModeAction(JUMP)
  class ToggleJumpEndMode     : BaseToggleJumpModeAction(JUMP_END)
  class ToggleJumpStartMode   : BaseToggleJumpModeAction(JUMP_START)
  class ToggleChuckMode       : BaseToggleJumpModeAction(CHUCK)
  class ToggleTargetMode      : BaseToggleJumpModeAction(TARGET)
  class ToggleDeclarationMode : BaseToggleJumpModeAction(DECLARATION)

  // @formatter:on
}
