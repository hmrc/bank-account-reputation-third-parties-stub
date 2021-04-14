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

import play.api.libs.json.{Json, OFormat, Reads}

case class MatchRequest(
                         bankSortCode: String,
                         bankAccountNumber: String,
                         businessTypeIndicator: Option[String] = None,
                         businessName: Option[String] = None,
                         postcode: Option[String] = None,
                         companyRegistrationNumber: Option[String] = None,
                         line1: Option[String] = None,
                         line2: Option[String] = None,
                         line3: Option[String] = None,
                         line4: Option[String] = None,
                         line5: Option[String] = None,
                         line6: Option[String] = None,
                         additionalTradingStyle: Option[String] = None,
                         businessTelephoneNumber: Option[String] = None,
                         vatNumber: Option[String] = None,
                         bankAccountIban: Option[String] = None
                       )

object MatchRequest {
  implicit val writes: OFormat[MatchRequest] = Json.format[MatchRequest]

  implicit val reads: Reads[MatchRequest] = Json.reads[MatchRequest]
}
