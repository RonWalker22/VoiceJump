package org.voicejump.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import org.voicejump.session.Session
import org.voicejump.session.SessionManager

/**
 * Base class for keyboard-activated overrides of existing editor actions, that have a different meaning during an VoiceJump [Session].
 */
sealed class AceEditorAction(private val originalHandler: EditorActionHandler): EditorActionHandler() {
  final override fun isEnabledForCaret(editor: Editor, caret: Caret, dataContext: DataContext?): Boolean =
    SessionManager[editor] != null || originalHandler.isEnabled(editor, caret, dataContext)

  final override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext?) {
    val session = SessionManager[editor]

    if (session != null) run(session)
    else if (originalHandler.isEnabled(editor, caret, dataContext))
      originalHandler.execute(editor, caret, dataContext)
  }

  protected abstract fun run(session: Session)

  // Actions

  class Reset(originalHandler: EditorActionHandler): AceEditorAction(originalHandler) {
    override fun run(session: Session) = session.end()
  }

  class ClearSearch(originalHandler: EditorActionHandler): AceEditorAction(originalHandler) {
    override fun run(session: Session) = session.restart()
  }

  class SelectBackward(originalHandler: EditorActionHandler): AceEditorAction(originalHandler) {
    override fun run(session: Session) = session.visitPreviousTag()
  }

  class SelectForward(originalHandler: EditorActionHandler): AceEditorAction(originalHandler) {
    override fun run(session: Session) = session.visitNextTag()
  }

  class ScrollToNextScreenful(originalHandler: EditorActionHandler): AceEditorAction(originalHandler) {
    override fun run(session: Session) { session.scrollToNextScreenful() }
  }

  class ScrollToPreviousScreenful(originalHandler: EditorActionHandler): AceEditorAction(originalHandler) {
    override fun run(session: Session) { session.scrollToPreviousScreenful() }
  }
}
