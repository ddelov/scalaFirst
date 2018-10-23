import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.datastax.driver.core.{Cluster, ProtocolOptions}
import com.estafet.eep.actors.ClaimRegistryActor
import com.estafet.eep.routes.Routes

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn

object WebServer extends App with Routes {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher // needed for the future flatMap/onComplete in the end

  // Creating Cassandra Cluster connector
  val cluster: Cluster = Cluster.builder()
      .addContactPoints("127.0.0.1")
      .withCompression(ProtocolOptions.Compression.SNAPPY)
      .withPort(9042)
      .build()

  val dbManagerActorRef = system.actorOf(Props(new ClaimRegistryActor(cluster)), "dbManager")
  val bindingFuture = Http().bindAndHandle(restRoutes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

  Await.result(system.whenTerminated, Duration.Inf)
}
