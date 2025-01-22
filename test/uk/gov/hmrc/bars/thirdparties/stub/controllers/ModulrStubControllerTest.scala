/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.bars.thirdparties.stub.controllers

import org.apache.pekko.util.Timeout
import org.assertj.core.api.Assertions.assertThat
import org.scalatest.funsuite.AnyFunSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsJson
import play.api.{Configuration, Environment}
import uk.gov.hmrc.bars.thirdparties.stub.AppConfig
import uk.gov.hmrc.bars.thirdparties.stub.models.modulr.{ModulrRequest, ModulrResponse, ModulrResult}
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents

import scala.concurrent.Future

class ModulrStubControllerTest extends AnyFunSuite {
  private final val DEFAULT_TEST_HEADER: (String, String) = "X-LOCALHOST-Origin" -> "test"
  private val env = Environment.simple()
  private val appConfig = new AppConfig(env, Configuration.load(env))
  private val modulrStubController = new ModulrStubController(appConfig.loadStubbedModulrData, stubMessagesControllerComponents())

  test("Valid MATCHED Modulr personal response") {
    val expectedResult = ModulrResponse(
      id = "",
      result = ModulrResult(
        code = "MATCHED",
        name = Some("David Martin")
      )
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/api-sandbox/account-name-check")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(
        Json.toJson(
          ModulrRequest(
            paymentAccountId = "A2100CX9GS",
            sortCode = "999999",
            accountNumber = "00000001",
            secondaryAccountId = None,
            accountType = "PERSONAL",
            name = "David Martin"
          )
        )
      )

    val result: Future[Result] = modulrStubController.callModulrAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ModulrResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.CREATED)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Valid MATCHED Modulr business response") {
    val expectedResult = ModulrResponse(
      id = "",
      result = ModulrResult(
        code = "MATCHED",
        name = Some("MockService Inc.")
      )
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/api-sandbox/account-name-check")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(
        Json.toJson(
          ModulrRequest(
            paymentAccountId = "A2100CX9GS",
            sortCode = "999999",
            accountNumber = "00000005",
            secondaryAccountId = None,
            accountType = "BUSINESS",
            name = "MockService Inc."
          )
        )
      )

    val result: Future[Result] = modulrStubController.callModulrAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ModulrResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.CREATED)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Valid NOT_MATCHED Modulr personal response") {
    val expectedResult = ModulrResponse(
      id = "",
      result = ModulrResult(
        code = "NOT_MATCHED",
        name = Some("David Martin")
      )
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/api-sandbox/account-name-check")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(
        Json.toJson(
          ModulrRequest(
            paymentAccountId = "A2100CX9GS",
            sortCode = "999999",
            accountNumber = "00000000",
            secondaryAccountId = None,
            accountType = "PERSONAL",
            name = "David Martin"
          )
        )
      )

    val result: Future[Result] = modulrStubController.callModulrAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ModulrResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.CREATED)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Valid NOT_MATCHED Modulr business response") {
    val expectedResult = ModulrResponse(
      id = "",
      result = ModulrResult(
        code = "NOT_MATCHED",
        name = Some("MockService Inc.")
      )
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/api-sandbox/account-name-check")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(
        Json.toJson(
          ModulrRequest(
            paymentAccountId = "A2100CX9GS",
            sortCode = "999999",
            accountNumber = "00000034",
            secondaryAccountId = None,
            accountType = "BUSINESS",
            name = "MockService Inc."
          )
        )
      )

    val result: Future[Result] = modulrStubController.callModulrAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ModulrResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.CREATED)
    assertThat(response).isEqualTo(expectedResult)
  }
}
