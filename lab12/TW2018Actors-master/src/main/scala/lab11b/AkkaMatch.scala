package lab11b

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.concurrent.Await
import scala.concurrent.duration._

object Messages {

  case class Init(parent: ActorRef)
  case class Shot(team: Boolean)
  case class Pass(team: Boolean, player:Int)
  case object Start
  case object Kick
  case object Goal
  case object Stop
  case object Finished

}

class Match extends Actor {

  val team1Name = "BlueTeam"
  val team2Name = "RedTeam"
  val teams = new Array[ActorRef](2)
  var team1Score = 0
  var team2Score = 0
  var working = 2

  def receive = {

    case Messages.Init =>
      teams(0) = context.actorOf(Props[Team], team1Name)
      teams(1) = context.actorOf(Props[Team], team2Name)

      teams(0) ! Messages.Init(self)
      teams(1) ! Messages.Init(self)

      val number = scala.util.Random.nextInt(2)
      println(s"[${self.path.name}] Starting match")
      teams(number) ! Messages.Start

    case Messages.Shot(team) =>
      // sends to the other team
      teams.foreach { t => if (t != sender) t ! Messages.Shot(team) }

    case Messages.Pass(team, player) =>
      // sends to the other team
      teams.foreach { t => if (t != sender) t ! Messages.Pass(team, player) }

    case Messages.Goal =>
      val team = sender.path.name
      if (team == team1Name) {
        team1Score += 1
        println(s"$team1Name $team1Score:$team2Score $team2Name")
        teams(0) ! Messages.Start
      }
      else {
        team2Score += 1
        println(s"$team1Name $team1Score:$team2Score $team2Name")
        teams(1) ! Messages.Start
      }

    case Messages.Stop =>
      teams.foreach { t => t ! Messages.Stop }

    case Messages.Finished =>
      working -= 1
      if (working == 0) {
        println(s"Match ended! $team1Name $team1Score:$team2Score $team2Name")
        context.system.terminate
      }
  }
}

class Team extends Actor {

  val players = new Array[ActorRef](11)
  var matchRef: ActorRef = _
  var working = 11

  def receive = {

    case Messages.Init(parent) =>
      matchRef = parent
      for (index <- players.indices) {
        players(index) = context.actorOf(Props[Player], s"${self.path.name}_Player${index + 1}")
      }

    case Messages.Start =>
      println(s"${self.path.name} Kickoff")
      val number = scala.util.Random.nextInt(11)
      players(number) ! Messages.Kick

    case Messages.Shot(team) =>
      // my player's shot, pass to other team
      if (!team) {
        print(s"${sender.path.name} Taking the shot... ")
        matchRef ! Messages.Shot(true)
      }
      // handle the shot
      else {
        var number = scala.util.Random.nextInt(100)
        if (number < 40) {
          println("GOAL!")
          matchRef ! Messages.Goal
        }
        else {
          println("NO GOAL!")
          number = scala.util.Random.nextInt(11)
          players(number) ! Messages.Kick
        }
      }

    case Messages.Pass(team, player) =>
      // kept possession
      if(team){
        players(player) ! Messages.Kick
      }
      // lost possession
      else{
        matchRef ! Messages.Pass(true, player)
      }

    case Messages.Stop =>
      players.foreach { p => p ! Messages.Stop }

    case Messages.Finished =>
      working -= 1
      if (working == 0) {
        matchRef ! Messages.Finished
        context.system.terminate
      }
  }
}

class Player extends Actor {

  def receive = {

    case Messages.Kick =>
      val s = sender
      println(s"${self.path.name} Has the ball")
      val number = scala.util.Random.nextInt(3)
      import scala.concurrent.ExecutionContext.Implicits.global
      context.system.scheduler.scheduleOnce(500 millis) {
        number match {
          // shot
          case 0 =>
            s ! Messages.Shot(false)
          // successful pass
          case 1 =>
            val player = scala.util.Random.nextInt(11)
            s ! Messages.Pass(true, player)
          // unsuccessful pass
          case 2 =>
            val player = scala.util.Random.nextInt(11)
            s ! Messages.Pass(false, player)
        }
      }

    case Messages.Stop =>
      sender ! Messages.Finished
      context.system.terminate

  }
}

object Main extends App {
  val system = ActorSystem("FootballMatch")
  val mainActor = system.actorOf(Props[Match], "Blue_vs_Red")

  // start a match
  mainActor ! Messages.Init

  import system.dispatcher

  system.scheduler.scheduleOnce(10 seconds) {
    // end a match
    mainActor ! Messages.Stop
  }

  Await.result(system.whenTerminated, Duration.Inf)
}