package models

import play.api.libs.json.Json

case class Game(id: Long, address: String)

object Game extends JsonDto[Game] {

	var games = List[Game]().toBuffer

	def all: List[Game] = games.toList

	def create(address: String): Game = {
		val id = games.size
		val newGame = Game(id, address)
		games += newGame
		newGame
	}

	def delete(id: Long) {
		games -= games(id.toInt)
	}

	def asJson(game: Game) = Map(
		"id" -> asJson(game.id),
		"address" -> asJson(game.address)
	)

	implicit val gameReads = Json.reads[Game]
}