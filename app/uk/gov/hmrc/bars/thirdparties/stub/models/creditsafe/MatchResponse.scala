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

package uk.gov.hmrc.bars.thirdparties.stub.models.creditsafe

import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.bars.thirdparties.stub.models.creditsafe.MatchResponse.Exact

import java.util.UUID


case class MatchResponse(
                        requestId: String = UUID.randomUUID().toString,
                        result: String = Exact,
                        isActive: Boolean = true,
                        confidence: Confidence = Confidence(None, None)
                      )

object MatchResponse {
  final val Exact: String = "exact"
  final val Partial: String = "partial"
  final val NoMatch: String = "none"

  implicit lazy val matchResultFormat: OFormat[MatchResponse] = Json.format[MatchResponse]
  implicit lazy val confidenceFormat: OFormat[Confidence] = Json.format[Confidence]
}
