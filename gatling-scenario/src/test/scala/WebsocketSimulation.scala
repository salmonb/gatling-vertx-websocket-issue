
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class WebsocketSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost")
    .wsBaseUrl("ws://localhost")
    .inferHtmlResources()
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")

  val scn = scenario("WebsocketSimulation")
    .exec(ws("Connect WS").connect("/eventbus/websocket")
      .onConnected(
        exec(ws("Send WS Text Frame Message")
          .sendText("""{"type":"send", "address":"myAddress", "body":"myBody", "replyAddress":"8d7f27a2-b87d-493f-8ad8-4950463eabc7"}""")
          .await(5 seconds)(
            ws.checkTextMessage("Receive WS Text Frame Reply") // Not Vert.x compliant as Vert.x sends a binary frame, but no other choice offered by Gatling when using sendText()
            //ws.checkBinaryMessage("Receive WS Binary Frame Repy") // could be Vert.x compliant but doesn't compile with Gatling
          )
        )
          .pause(1)
          .exec(ws("Close WS").close)
      )
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}