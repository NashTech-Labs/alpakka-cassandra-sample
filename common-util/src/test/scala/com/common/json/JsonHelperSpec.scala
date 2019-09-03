package com.common.json

import org.scalatest.AsyncWordSpec


case class Test(key: String)

class JsonHelperSpec extends AsyncWordSpec with JsonHelper{


  "A kafka Producer" should {

    "send message to the topic successfully" in {

      val res = parse("""{"key":"value"}""").extract[Test]
      assert(res === Test("value"))
    }
  }

}
