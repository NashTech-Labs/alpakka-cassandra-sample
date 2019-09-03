package com.common.http

import com.common.json.JsonHelper
import org.json4s.JValue


case class ResponseCart(
                             code: Int,
                             data: Option[JValue] = None,
                             message: Option[String] = None
                           )

object ResponseCart extends JsonHelper {

  def OK(data: AnyRef): String = write(ResponseCart(code = 200, data = Some(parse(write(data)))))

  def ERROR(msg: String): String = write(ResponseCart(code = 400, message = Some(msg)))

}
