package models

import play.api.libs.json.Json
import play.api.libs.json.JsValue

case class Game(id: Long, address: String, maxPlayers: Int, currentPlayers: Int)

object Game {

	import anorm._
	import anorm.SqlParser._
	import play.api.db._
	import play.api.Play.current

	val game = {
		get[Long]("id") ~
			get[String]("address") ~
			get[Int]("maxPlayers") ~
			get[Int]("currentPlayers") map {
				case id ~ address ~ maxPlayers ~ currentPlayers =>
					Game(id, address, maxPlayers, currentPlayers)
			}
	}

	def all: List[Game] = DB.withConnection { implicit c =>
		SQL("select * from game").as(game *)
	}

	def create(address: String, maxPlayers: Int, currentPlayers: Int): Game = {
		val id = DB.withConnection { implicit c =>
			SQL("insert into game (address, maxPlayers, currentPlayers) values ({address}, {maxPlayers}, {currentPlayers})").on(
				'address -> address,
				'maxPlayers -> maxPlayers,
				'currentPlayers -> currentPlayers
			).executeInsert() match {
					case Some(id) => id
					case None => 0
				}
		}
		Game(id, address, maxPlayers, currentPlayers)
	}

	def update(id: Long, game: Game) {
		DB.withConnection { implicit c =>
			SQL("update game SET maxPlayers = {maxPlayers}, currentPlayers = {currentPlayers} where id = {id}").on(
				'id -> id,
				'maxPlayers -> game.maxPlayers,
				'currentPlayers -> game.currentPlayers
			).executeUpdate()
		}
	}

	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from game where id = {id}").on(
				'id -> id
			).execute()
		}
	}

	implicit val gameReads = Json.reads[Game]
	implicit val gameWrites = Json.writes[Game]
}