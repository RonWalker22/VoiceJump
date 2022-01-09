package org.acejump.test.util

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.psi.PsiFile
import com.intellij.testFramework.FileEditorManagerTestCase
import com.intellij.util.ui.UIUtil
import org.acejump.action.AceAction
import org.acejump.session.SessionManager

abstract class BaseTest: FileEditorManagerTestCase() {
  companion object {
    inline fun averageTimeWithWarmup(warmupRuns: Int, timedRuns: Int, action: () -> Long): Long {
      repeat(warmupRuns) { action() }
      var time = 0L
      repeat(timedRuns) { time += action() }
      return time / timedRuns
    }
  }

  protected val session get() = SessionManager[myFixture.editor]!!

  override fun tearDown() {
    resetEditor()
    super.tearDown()
  }

  fun takeAction(action: String) = myFixture.performEditorAction(action)
  fun takeAction(action: AnAction) = myFixture.testAction(action)

  fun makeEditor(contents: String): PsiFile =
    myFixture.configureByText(PlainTextFileType.INSTANCE, contents)

  fun resetEditor() {
    myFixture.editor?.let {
      takeAction(IdeActions.ACTION_EDITOR_ESCAPE)
      UIUtil.dispatchAllInvocationEvents()
      assertEmpty(it.markupModel.allHighlighters)
    }
    myManager.closeAllFiles()
  }

  fun typeAndWaitForResults(string: String) {
    myFixture.type(string)
    UIUtil.dispatchAllInvocationEvents()
  }

  fun String.executeQuery(query: String) = myFixture.run {
    makeEditor(this@executeQuery)
    testAction(AceAction.ActivateOrCycleMode())
    typeAndWaitForResults(query)
  }

  fun String.search(query: String): Set<Int> {
    this@search.executeQuery(query)
    this@search.replace(Regex("<[^>]*>"), "").assertCorrectNumberOfTags(query)
    return myFixture.editor.markupModel.allHighlighters.map { it.startOffset }.toSet()
  }

  private fun String.assertCorrectNumberOfTags(query: String) {
    var numberOfTags = split(query.fold("") { prefix, char ->
      if ((prefix + char) in this) prefix + char else return
    }).size - 1

    if (numberOfTags == 1) numberOfTags--
    assertEquals(numberOfTags, myFixture.editor.markupModel.allHighlighters.size)
  }

}
