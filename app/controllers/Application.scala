package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import models.Game
import play.api.libs.json.JsValue
import views.html.defaultpages.badRequest

object Application extends Controller {

	def index = Action {
		Ok(views.html.index("Your new application is ready."))
	}

	def createGame = Action { implicit request =>
		request.getQueryString("address") match {
			case Some(address) => {
				val game = Game.create(address)
				Ok(Game.toJson(game))
			}
			case None => BadRequest("Missing Parameter: address")
		}
	}

	def games = Action { implicit request =>
		Ok(Json.toJson(Game.all.map(Game.asJson(_))))
	}

	def deleteGame(id: Long) = Action { implicit request =>
		Game.delete(id)
		Ok
	}
}