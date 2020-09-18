package animus.sherhc.webSocket

import io.ktor.http.cio.websocket.*

abstract class WebSocketListener<Session> {
	open fun onOpen(session: Session) {}
	open fun onClose(session: Session) {}
	open fun onError(session: Session, err: Exception) {}
	open fun onMessage(session: Session, frame: Frame) {}
}