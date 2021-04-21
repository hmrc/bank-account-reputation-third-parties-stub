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

import controllers.Assets.Ok
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.bars.thirdparties.stub.models.creditsafe.MatchResponse

import javax.inject.Inject

class CreditSafeMatchStubController @Inject()(cc: ControllerComponents) {

  //TODO implement more logic if we keep creditsafe, this will do for now.

  def creditSafeMatchStub: Action[AnyContent] = cc.actionBuilder {
    (request: Request[AnyContent]) =>
      Ok(Json.toJson(MatchResponse()))
        .as("text/plain")
  }
}
