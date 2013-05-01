package models

import play.api.libs.json.JsValue
import play.api.libs.json.Json

trait JsonDto[T] {

	def asJson(value: T): Map[String, JsValue]

	def toJson(value: T): JsValue =
		Json.toJson(asJson(value))

	def asJson(value: Any): JsValue =
		value match {
			case s: String => Json.toJson(s)
			case l: Long => Json.toJson(l)
			case i: Int => Json.toJson(i)
		}
}