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

import akka.util.Timeout
import org.assertj.core.api.Assertions.assertThat
import org.scalatest.funsuite.AnyFunSuite
import play.api.http.{MimeTypes, Status}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsString
import play.api.{Configuration, Environment}
import sttp.model.HeaderNames
import uk.gov.hmrc.bars.thirdparties.stub.AppConfig
import uk.gov.hmrc.bars.thirdparties.stub.models.callvalidate.{CallValidateData, CallValidateRequest, CallValidateResponse, FinancialId}
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents

import scala.concurrent.Future

class CallValidateStubControllerTest extends AnyFunSuite {
  private final val DEFAULT_TEST_HEADER: (String, String) = "X-LOCALHOST-Origin" -> "test"
  private val env = Environment.simple()
  private val appConfig = new AppConfig(env, Configuration.load(env))
  private val callValidateStubController = new CallValidateStubController(appConfig.loadStubbedCallValidateData, stubMessagesControllerComponents())

  test("Fully populated valid CallValidate response") {
    val individual = FinancialId(
      sortCode = "204578",
      accountNumber = "89251211",
      title = Some("MRS"),
      firstName = "ANNIE",
      lastName = "MCLAREN",
      postcode = "X9 9AB"
    )
    val expectedResult = CallValidateResponse(
      CallValidateData(
        statusCode = 200,
        title = individual.title.get,
        firstname = individual.firstName,
        surname = individual.lastName,
        streetNumber = Some("1"),
        street = "FIA LANE",
        town = "TEST TOWN",
        postcode = "X9 9AB",
        accountExists = "yes")
    ).build()

    val fakeRequest = FakeRequest(method = "POST", path = s"/callvalidateapi/incomingserver.php")
      .withHeaders(DEFAULT_TEST_HEADER, HeaderNames.ContentType -> MimeTypes.XML)
      .withXmlBody(CallValidateRequest().build(individual))

    val result: Future[Result] = callValidateStubController.callValidateAPIStub.apply(fakeRequest)
    val response = scala.xml.XML.loadString(contentAsString(result)(Timeout.zero))

    assertThat(result.value.get.get.header.status).isEqualTo(Status.OK)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Fallback response provided if data not found") {
    val individual = FinancialId(
      sortCode = "554688",
      accountNumber = "66448571",
      title = Some("MRS"),
      firstName = "ANNIE",
      lastName = "MCLAREN",
      postcode = "X9 9AB"
    )
    val expectedResult = CallValidateResponse(CallValidateStubController.defaultResponseData).build()

    val fakeRequest = FakeRequest(method = "POST", path = s"/callvalidateapi/incomingserver.php")
      .withHeaders(DEFAULT_TEST_HEADER, HeaderNames.ContentType -> MimeTypes.XML)
      .withXmlBody(CallValidateRequest().build(individual))

    val result: Future[Result] = callValidateStubController.callValidateAPIStub.apply(fakeRequest)
    val response = scala.xml.XML.loadString(contentAsString(result)(Timeout.zero))

    assertThat(result.value.get.get.header.status).isEqualTo(Status.OK)
    assertThat(response).isEqualTo(expectedResult)
  }

  test("Too many requests response") {
    val individual = FinancialId(
      sortCode = "204578",
      accountNumber = "89291211",
      title = Some("MR"),
      firstName = "Not",
      lastName = "Applicable",
      postcode = "NT1 1LD"
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/callvalidateapi/incomingserver.php")
      .withHeaders(DEFAULT_TEST_HEADER, HeaderNames.ContentType -> MimeTypes.XML)
      .withXmlBody(CallValidateRequest().build(individual))

    val result: Future[Result] = callValidateStubController.callValidateAPIStub.apply(fakeRequest)
    val response = contentAsString(result)(Timeout.zero)

    assertThat(result.value.get.get.header.status).isEqualTo(Status.TOO_MANY_REQUESTS)
    assertThat(response).isEqualTo("Too many requests")
  }

  test("Internal server error response") {
    val individual = FinancialId(
      sortCode = "204578",
      accountNumber = "89293211",
      title = Some("MR"),
      firstName = "Not",
      lastName = "Applicable",
      postcode = "NT1 1LD"
    )

    val fakeRequest = FakeRequest(method = "POST", path = s"/callvalidateapi/incomingserver.php")
      .withHeaders(DEFAULT_TEST_HEADER, HeaderNames.ContentType -> MimeTypes.XML)
      .withXmlBody(CallValidateRequest().build(individual))

    val result: Future[Result] = callValidateStubController.callValidateAPIStub.apply(fakeRequest)
    val response = contentAsString(result)(Timeout.zero)

    assertThat(result.value.get.get.header.status).isEqualTo(Status.INTERNAL_SERVER_ERROR)
    assertThat(response).isEqualTo("Something went wrong")
  }
}
