/*
 * Copyright 2024 HM Revenue & Customs
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

import play.api.libs.json.Json
import play.api.mvc.Results.{Created, TooManyRequests}
import play.api.mvc._
import uk.gov.hmrc.bars.thirdparties.stub.models.modulr.{ModulrData, ModulrRequest, ModulrResponse, ModulrResult}

import javax.inject.Inject

class ModulrStubController @Inject() (stubbedThirdPartyData: Seq[ModulrData], cc: ControllerComponents) {

  def callModulrAPIStub: Action[AnyContent] = cc.actionBuilder { (request: Request[AnyContent]) =>
    val body: ModulrRequest = Json.fromJson(request.body.asJson.get)(ModulrRequest.format).get
    calculateDesiredResponse(body)
  }

  def calculateDesiredResponse(body: ModulrRequest): Result = {
    val filteredStubbedData: Option[ModulrData] =
      stubbedThirdPartyData.find(entry =>
        entry.paymentAccountId == body.paymentAccountId &&
          entry.sortCode == body.sortCode &&
          entry.accountNumber == body.accountNumber &&
          entry.secondaryAccountId == body.secondaryAccountId &&
          entry.accountType == body.accountType &&
          entry.name.contains(body.name.trim)
      )

    filteredStubbedData match {
      case None =>
        Created(
          Json.toJson(
            ModulrResponse(
              id = "",
              result = ModulrResult(
                code = "NOT_MATCHED",
                name = None
              )
            )
          )
        ).as("application/json;charset=utf-8")
      case Some(stubbedData) =>
        if (stubbedData.statusCode == 429) {
          TooManyRequests("Too many requests")
        } else {
          Created(
            Json.toJson(
              ModulrResponse(
                id = "",
                result = ModulrResult(
                  code = stubbedData.resultCode,
                  name = Some(stubbedData.name)
                )
              )
            )
          ).as("application/json;charset=utf-8")
        }
    }
  }
}
