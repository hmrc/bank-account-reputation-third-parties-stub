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

import play.api.libs.json._
import play.api.mvc.Results.{Ok, TooManyRequests}
import play.api.mvc._
import uk.gov.hmrc.bars.thirdparties.stub.models.surepay.{ConfirmationOfPayeeRequest, ConfirmationOfPayeeResponse, RefreshTokenResponse, SurepayData}

import javax.inject.Inject

class SurePayStubController @Inject() (stubbedSurepayData: Map[String, SurepayData], cc: ControllerComponents) {

  val rateLimitingAccounts: Array[String] = Array("99999800000000", "99999700000000")

  def callSurePayAPIStub: Action[AnyContent] = cc.actionBuilder { (request: Request[AnyContent]) =>
    val body: ConfirmationOfPayeeRequest = Json.fromJson(request.body.asJson.get)(ConfirmationOfPayeeRequest.reads).get
    calculateDesiredResponse(body.Identification)
  }

  def callSurePayOAuthStub(): Action[AnyContent] = cc.actionBuilder {
    Ok(Json.toJson(RefreshTokenResponse()))
      .as("application/json;charset=utf-8")
  }

  def calculateDesiredResponse(identification: String): Result = {
    val filteredStubbedData: Option[SurepayData] = stubbedSurepayData.get(identification)
    filteredStubbedData match {
      case None =>
        Ok(
          Json.toJson(ConfirmationOfPayeeResponse())
        ).as("application/json;charset=utf-8")
      case _ =>
        if (filteredStubbedData.get.statusCode == 429) {
          TooManyRequests("Too many requests")
        } else {
          Ok(
            Json.toJson(
              ConfirmationOfPayeeResponse(
                Matched = filteredStubbedData.get.matched,
                ReasonCode = filteredStubbedData.get.reasonCode,
                Name = filteredStubbedData.get.accountName
              )
            )
          ).as("application/json;charset=utf-8")
        }
    }
  }
}
