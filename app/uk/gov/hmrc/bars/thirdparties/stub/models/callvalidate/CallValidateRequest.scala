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

package uk.gov.hmrc.bars.thirdparties.stub.models.callvalidate

import scala.xml.Elem

case class CallValidateRequest(company: String = "bm8tZGVmYXVsdC1jcmVkZW50aWFscwo", username: String = "no-default-credentials", password: String = "no-default-credentials", riskProfile: String = "V57-API-TESTOFA") {

  def build(financialId: FinancialId): Elem = {
    <callvalidate>
      <authentication>
        <company>{company}</company>
        <username>{username}</username>
        <password>{password}</password>
      </authentication>
      <sessions>
        <session>
          <data>
            {personalInfo(financialId)}
            <Bankinformation>
              <Bankaccountnumber>{financialId.accountNumber}</Bankaccountnumber>
              <Banksortcode>{financialId.sortCode}</Banksortcode>
            </Bankinformation>
            <ChecksRequired>
              <BankStandard>yes</BankStandard>
              <BankEnhanced>yes</BankEnhanced>
              <CardStandard>no</CardStandard>
              <CardLive>no</CardLive>
              <CardEnhanced>no</CardEnhanced>
              <IDEnhanced>yes</IDEnhanced>
              <NCOAAlert>no</NCOAAlert>
              <CallValidate3D>no</CallValidate3D>
              <TheAffordabilityReport>no</TheAffordabilityReport>
              <DeliveryFraud>no</DeliveryFraud>
              <CreditScore>no</CreditScore>
              <Zodiac>no</Zodiac>
              <RealTimeFraudAlerts>no</RealTimeFraudAlerts>
              <DeviceRisk>no</DeviceRisk>
              <MobileRisk>no</MobileRisk>
            </ChecksRequired>
          </data>
        </session>
      </sessions>
      <application>{financialId.riskProfile.getOrElse(riskProfile)}</application>
    </callvalidate>
  }

  private def personalInfo(financialId: FinancialId): Elem = {
    <Personalinformation>
      <IndividualDetails>
        {dob(financialId)}
        <Title>{safe(financialId.title.getOrElse("undefined"))}</Title>
        <Firstname>{safe(financialId.firstName)}</Firstname>
        <Surname>{safe(financialId.lastName)}</Surname>
      </IndividualDetails>
      <AddressDetails>
        {truncatedAddress(financialId)}{town(financialId)}
        <Postcode>{financialId.postcode}</Postcode>
      </AddressDetails>
    </Personalinformation>
  }

  private def dob(financialId: FinancialId): Elem = {
    if (financialId.dob.isDefined) {
      <Dateofbirth>{financialId.dob.get}</Dateofbirth>
    } else {
      null
    }
  }

  private def truncatedAddress(financialId: FinancialId): Elem = {
    if (financialId.address.isDefined) {
      // using Buildingname because it handles partial address matching, unlike the other fields
      <Buildingname>{safe(financialId.address.get)}</Buildingname>
    } else {
      null
    }
  }

  private def town(financialId: FinancialId): Elem = {
    if (financialId.town.isDefined) {
      <Town>{safe(financialId.town.get)}</Town>
    } else {
      null
    }
  }

  private def safe(s: String): String = s.replace("<", "").replace(">", "")
}
