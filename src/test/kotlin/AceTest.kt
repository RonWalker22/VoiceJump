import com.intellij.openapi.actionSystem.IdeActions.ACTION_EDITOR_ENTER
import com.intellij.openapi.actionSystem.IdeActions.ACTION_EDITOR_START_NEW_LINE
import com.intellij.openapi.editor.actions.EnterAction
import org.voicejump.action.AceAction
import org.voicejump.config.AceConfig
import org.voicejump.test.util.BaseTest

/**
 * Functional test cases and end-to-end performance tests.
 *
 * TODO: Add more structure to test cases, use test resources to define files.
 */

class AceTest : BaseTest() {
  fun `test that scanner finds all occurrences of single character`() =
    assertEquals("test test test".search("t"), setOf(0, 3, 5, 8, 10, 13))

  fun `test empty results for an absent query`() =
    assertEmpty("test test test".search("best"))

  fun `test sticky results on a query with extra characters`() =
    assertEquals("test test test".search("testz"), setOf(0, 5, 10))

  fun `test a query inside text with some variations`() =
    assertEquals("abcd dabc cdab".search("cd"), setOf(2, 10))

  fun `test a query containing a space character`() =
    assertEquals("abcd dabc cd cdab".search("cd "), setOf(2, 10))

  fun `test a query containing a { character`() =
    assertEquals("abcd{dabc cd{ cdab".search("cd{"), setOf(2, 10))

  fun `test that jumping to first occurrence succeeds`() {
    "<caret>testing 1234 testing 123".search("1")

    takeAction(ACTION_EDITOR_ENTER)

    myFixture.checkResult("testing <caret>1234 testing 123")
  }

  fun `test that jumping to second occurrence succeeds`() {
    "<caret>testing 1234 testing".search("ti")

    takeAction(ACTION_EDITOR_ENTER)

    myFixture.checkResult("tes<caret>ting 1234 testing")
  }

  fun `test that jumping to previous occurrence succeeds`() {
    "te<caret>sting 1234".search("t")

    takeAction(ACTION_EDITOR_START_NEW_LINE)

    myFixture.checkResult("<caret>testing 1234")
  }

  fun `test tag selection`() {
    "<caret>testing 1234 testing".search("g")

    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("testin<caret>g 1234 testing")
  }


  /**
   * Automatically select a target when there is only one potential target remaining.
   */
  fun `test short-circuiting tags`() {
    "<caret>testing 1234".search("g")

    myFixture.checkResult("testin<caret>g 1234")
  }

  fun `test shift selection`() {
    "<caret>testing 1234 gg 45".search("4")

    typeAndWaitForResults(session.tags[0].key.uppercase())

    myFixture.checkResult("<selection>testing 123<caret></selection>4 gg 45")
  }

  fun `test target mode`() {
    "<caret>test target action target".search("target")

    takeAction(AceAction.ToggleTargetMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test <selection>target<caret></selection> action target")
  }

  fun `test jump start mode against word`() {
    "<caret>test target action target".search("target")

    takeAction(AceAction.ToggleJumpStartMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test <caret>target action target")
  }

  fun `test jump start mode against letter`() {
    "<caret>test target action action".search("i")

    takeAction(AceAction.ToggleJumpStartMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test target <caret>action action")
  }

  fun `test jump end mode against word`() {
    "<caret>test target action target".search("target")

    takeAction(AceAction.ToggleJumpEndMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test target<caret> action target")
  }

   fun `ignore test chuck mode against letter`() {
    "test <caret>target action action".search("g")

    takeAction(AceAction.ToggleChuckMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test <caret>action action")
  }

  fun `ignore test chuck mode against word`() {
    "test <caret>target action target".search("target")

    takeAction(AceAction.ToggleChuckMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test <caret>action target")
  }

  fun `test jump end mode against letter`() {
    ("<caret>test target action target").search("g")

    takeAction(AceAction.ToggleJumpEndMode())
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test target<caret> action target")
  }

  fun `test cache invalidation`() {
    "first line first".search("first")
    typeAndWaitForResults(session.tags[0].key)

    repeat(3) { takeAction(EnterAction()) }

    takeAction(AceAction.ToggleTargetMode())
    typeAndWaitForResults("first")
    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("\n\n\n<selection>first<caret></selection> line first")
  }

  fun `test chinese selection`() {
    AceConfig.settings.mapToASCII = true

    "test 拼音 selection 拼音".search("py")

    takeAction(AceAction.ToggleTargetMode())

    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("test <selection>拼音<caret></selection> selection 拼音")
  }

  fun `test japanese selection`() {
    AceConfig.settings.mapToASCII = true

    "あみだにょらい あみだにょらい".search("am")

    takeAction(AceAction.ToggleTargetMode())

    typeAndWaitForResults(session.tags[0].key)

    myFixture.checkResult("<selection>あみだにょらい<caret></selection> あみだにょらい")
  }

  // https://github.com/acejump/AceJump/issues/355
  fun `ignore test a word that is difficult to tag`() {
    makeEditor("aaCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")

    takeAction(AceAction.ActivateOrCycleMode())

    typeAndWaitForResults("c")

    assertEquals(2, session.tags.size)
  }
}
