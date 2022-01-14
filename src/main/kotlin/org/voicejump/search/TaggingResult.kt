package org.voicejump.search

import com.intellij.openapi.editor.Editor
import org.voicejump.view.TagMarker

sealed class TaggingResult {
  class Jump(val query: String, val mark: String, val tag: Tag): TaggingResult()
  class Mark(val markers: MutableMap<Editor, Collection<TagMarker>>): TaggingResult()
}
