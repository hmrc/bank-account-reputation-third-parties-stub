/*
 * Copyright 2021 HM Revenue & Customs
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

import akka.util.Timeout
import org.assertj.core.api.Assertions.assertThat
import org.scalatest.funsuite.AnyFunSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsJson
import play.api.{Configuration, Environment}
import uk.gov.hmrc.bars.thirdparties.stub.AppConfig
import uk.gov.hmrc.bars.thirdparties.stub.models.surepay.{ConfirmationOfPayeeRequest, ConfirmationOfPayeeResponse, RefreshTokenResponse}
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents

import scala.concurrent.Future

class SurepayStubControllerTest extends AnyFunSuite {
  private final val DEFAULT_TEST_HEADER: (String, String) = "X-LOCALHOST-Origin" -> "test"
  private val env = Environment.simple()
  private val appConfig = new AppConfig(env, Configuration.load(env))
  private val surePayStubController = new SurePayStubController(appConfig.loadStubbedSurepayData, stubMessagesControllerComponents())

  test("Fully populated invalid Surepay response") {

    val expectedResult = ConfirmationOfPayeeResponse(
      Matched = false,
      ReasonCode = Some("MBAM"),
      Name = Some("Jim Jones")
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/surepay/v1/gateway")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(Json.toJson(ConfirmationOfPayeeRequest(Identification = "12345600000000", Name = "J Jones")))

    val result: Future[Result] = surePayStubController.callSurePayAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ConfirmationOfPayeeResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.OK)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Invalid Surepay response with only reason code and matched flag") {

    val expectedResult = ConfirmationOfPayeeResponse(
      Matched = false,
      ReasonCode = Some("ANNM")
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/surepay/v1/gateway")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(Json.toJson(ConfirmationOfPayeeRequest(Identification = "65432100000000", Name = "J Jones")))

    val result: Future[Result] = surePayStubController.callSurePayAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ConfirmationOfPayeeResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.OK)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Valid Surepay response") {

    val expectedResult = ConfirmationOfPayeeResponse()

    val fakeRequest = FakeRequest(method = "POST", path = s"/surepay/v1/gateway")
      .withHeaders(DEFAULT_TEST_HEADER)
      .withJsonBody(Json.toJson(ConfirmationOfPayeeRequest(Identification = "20710232211648", Name = "J Jones")))

    val result: Future[Result] = surePayStubController.callSurePayAPIStub.apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[ConfirmationOfPayeeResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.OK)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Valid Surepay OAuth response") {

    val fakeRequest = FakeRequest(method = "POST", path = s"/surepay/oauth/client_credential/accesstoken")
      .withHeaders(DEFAULT_TEST_HEADER)

    val result: Future[Result] = surePayStubController.callSurePayOAuthStub().apply(fakeRequest)
    val response = contentAsJson(result)(Timeout.zero).as[RefreshTokenResponse]

    assertThat(result.value.get.get.header.status).isEqualTo(Status.OK)
    assertThat(response.access_token).isNotBlank
    assertThat(response.expires_in).isEqualTo("3600")
    assertThat(response.token_type).isEqualTo("BearerToken")
  }

}
