# Attempt to use Gatling to test Vert.x websocket

* Execute MainVerticle in vertx-app module

* Execute GatlingRunner in gatling-scenario

* The Vert.x app should write "Received: myBody" showing that Gatling was able to send the websocket input text frame and Gatling logs:
```
...
17:30:22.771 [GatlingSystem-akka.actor.default-dispatcher-7] INFO io.gatling.http.action.ws.WsSendTextFrame - Sending text frame {"type":"send", "address":"myAddress", "body":"myBody", "replyAddress":"8d7f27a2-b87d-493f-8ad8-4950463eabc7"} with websocket 'gatling.http.webSocket': Scenario 'WebsocketSimulation', UserId #1
17:30:22.775 [GatlingSystem-akka.actor.default-dispatcher-7] DEBUG io.gatling.http.action.ws.fsm.WsActor - Send message Login WS {"type":"send", "address":"myAddress", "body":"myBody", "replyAddress":"8d7f27a2-b87d-493f-8ad8-4950463eabc7"}
17:30:22.779 [GatlingSystem-akka.actor.default-dispatcher-7] DEBUG io.gatling.http.action.ws.fsm.WsActor - Trigger check after send message
17:30:22.779 [gatling-http-1-2] DEBUG io.gatling.http.client.impl.WebSocketHandler - ctx.write msg=TextWebSocketFrame(data: UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 110, cap: 330))
17:30:22.779 [gatling-http-1-2] DEBUG io.netty.handler.codec.http.websocketx.WebSocket08FrameEncoder - Encoding WebSocket Frame opCode=1 length=110
17:30:23.253 [gatling-http-1-2] DEBUG io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder - Decoding WebSocket Frame opCode=2
17:30:23.253 [gatling-http-1-2] DEBUG io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder - Decoding WebSocket Frame length=83
17:30:23.254 [gatling-http-1-2] DEBUG io.gatling.http.client.impl.WebSocketHandler - Read msg=BinaryWebSocketFrame(data: PooledUnsafeDirectByteBuf(ridx: 0, widx: 83, cap: 83))
17:30:23.259 [GatlingSystem-akka.actor.default-dispatcher-7] DEBUG io.gatling.http.action.ws.fsm.WsActor - Received unmatched binary frame [123, 34, 116, 121, 112, 101, 34, 58, 34, 114, 101, 99, 34, 44, 34, 97, 100, 100, 114, 101, 115, 115, 34, 58, 34, 56, 100, 55, 102, 50, 55, 97, 50, 45, 98, 56, 55, 100, 45, 52, 57, 51, 102, 45, 56, 97, 100, 56, 45, 52, 57, 53, 48, 52, 54, 51, 101, 97, 98, 99, 55, 34, 44, 34, 98, 111, 100, 121, 34, 58, 34, 109, 121, 82, 101, 115, 112, 111, 110, 115, 101, 34, 125]
...
```
The last line shows that Vert.x sent back a websocket binary frame to Gatling but the later expected a text frame.

* Gatling post: https://groups.google.com/forum/#!topic/gatling/S5nv0SSy9T4
* Vert.x post 1: https://groups.google.com/forum/#!topic/vertx/7bsvrtPLVJc
* Vert.x post 2: https://groups.google.com/forum/#!topic/vertx/lwVWU4GXpfk