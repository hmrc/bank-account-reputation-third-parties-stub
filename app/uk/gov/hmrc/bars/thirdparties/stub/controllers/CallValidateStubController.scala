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

import play.api.mvc.Results.{InternalServerError, Ok, TooManyRequests}
import play.api.mvc.{AnyContent, ControllerComponents, _}
import uk.gov.hmrc.bars.thirdparties.stub.models.AccountDetails
import uk.gov.hmrc.bars.thirdparties.stub.models.callvalidate.{CallValidateData, CallValidateResponse}

import javax.inject.Inject

object CallValidateStubController {
  val defaultResponseData: CallValidateData = CallValidateData(
    statusCode = 200,
    title = "Mr",
    firstname = "Jim",
    surname = "Jones",
    streetNumber = Some("1"),
    street = "Test Street",
    town = "Test Town",
    postcode = "TE55 5TT",
    accountExists = "no"
  )
}

class CallValidateStubController @Inject()(stubbedCallValidateData: Map[AccountDetails, CallValidateData], cc: ControllerComponents) {

  def callValidateAPIStub: Action[AnyContent] = cc.actionBuilder {
    (request: Request[AnyContent]) =>
      val requestBody = request.body.asXml.get

      val account = AccountDetails(requestBody.\\("Banksortcode").text, requestBody.\\("Bankaccountnumber").text)
      val filteredStubbedData: Option[CallValidateData] = stubbedCallValidateData.get(account)
      filteredStubbedData match {
        case None =>
          Ok(CallValidateResponse(CallValidateStubController.defaultResponseData).build())
            .as("application/xml")
        case _ =>
          if (filteredStubbedData.get.statusCode == 429) {
            TooManyRequests("Too many requests")
          } else if (filteredStubbedData.get.statusCode == 500) {
            InternalServerError("Something went wrong")
          }
          else {
            Ok(CallValidateResponse(filteredStubbedData.get).build())
              .as("application/xml")
          }
      }
  }
}
