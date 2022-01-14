package org.voicejump.session

interface VoiceJumpListener {
  fun finished(mark: String?, query: String?)
}
