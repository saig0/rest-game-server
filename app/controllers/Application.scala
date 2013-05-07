package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import models.Game
import play.api.libs.json.JsValue
import views.html.defaultpages.badRequest
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError
import models.Game._
import play.api.libs.json.JsSuccess
import play.api.libs.json.Reads

object Application extends Controller with JsonRequest {

	def index = Action {
		Ok(views.html.index("Your new application is ready."))
	}

	def createGame = Action(parse.json) { implicit request =>
		jsonRequest[Game] { newGame =>
			val address = getAddressOfRequest(request)
			val game = Game.create(address, newGame.maxPlayers, newGame.currentPlayers)
			Ok(Json.toJson(game))
		}
	}

	/**
	 * Die IP-Addresse des Senders ist im Header des Request unter dem Attribut X-FORWARDED-FOR abgelegt.
	 * Da diese Methode beim lokalen Test nicht funktioniert, wird ein LOCALHOST zurückgegeben.
	 */
	private def getAddressOfRequest(request: Request[Any]): String =
		request.headers.get("X-FORWARDED-FOR") getOrElse ("localhost")

	def getGames = Action { implicit request =>
		Ok(Json.toJson(Game.all))
	}

	def updateGame(id: Long) = Action(parse.json) { implicit request =>
		jsonRequest[Game] { updatedGame =>
			Game.update(id, updatedGame)
			Ok
		}
	}

	def deleteGame(id: Long) = Action { implicit request =>
		Game.delete(id)
		Ok
	}
}