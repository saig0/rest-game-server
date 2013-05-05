package models

import play.api.libs.json.Json
import play.api.libs.json.JsValue

case class Game(id: Long, address: String, maxPlayers: Int, currentPlayers: Int)

object Game {

	var games = List[Game]().toBuffer

	def all: List[Game] = games.toList

	def create(address: String, maxPlayers: Int, currentPlayers: Int): Game = {
		val id = games.size
		val newGame = Game(id, address, maxPlayers, currentPlayers)
		games += newGame
		newGame
	}

	def update(id: Long, maxPlayers: Int, currentPlayers: Int) {
		val game = games(id.toInt)
		games -= game
		create(game.address, maxPlayers, currentPlayers)
	}

	def delete(id: Long) {
		games -= games(id.toInt)
	}

	implicit val gameReads = Json.reads[Game]
	implicit val gameWrites = Json.writes[Game]
}