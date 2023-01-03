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

package uk.gov.hmrc.bars.thirdparties.stub.models.surepay

import play.api.libs.json.{Json, OFormat, Reads}

import java.util.UUID

case class RefreshTokenResponse(
                                access_token: String = UUID.randomUUID().toString,
                                expires_in: String = "3600",
                                token_type: String = "BearerToken"
                              )

object RefreshTokenResponse {
  implicit val writes: OFormat[RefreshTokenResponse] = Json.format[RefreshTokenResponse]

  implicit val reads: Reads[RefreshTokenResponse] = Json.reads[RefreshTokenResponse]
}
