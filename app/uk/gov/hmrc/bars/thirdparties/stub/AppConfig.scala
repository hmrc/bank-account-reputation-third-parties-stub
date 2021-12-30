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

package uk.gov.hmrc.bars.thirdparties.stub

import com.github.tototoshi.csv.CSVReader
import com.google.inject.{AbstractModule, Provides}
import play.api.{Configuration, Environment}
import uk.gov.hmrc.bars.thirdparties.stub.models.AccountDetails
import uk.gov.hmrc.bars.thirdparties.stub.models.callvalidate.CallValidateData
import uk.gov.hmrc.bars.thirdparties.stub.models.surepay.SurepayData

import java.io.File
import javax.inject.Singleton

class AppConfig(environment: Environment, configuration: Configuration) extends AbstractModule {

  lazy val surepayDataFile: String = configuration.get[String]("stubbed.data.surepay")
  lazy val callValidateDataFile: String = configuration.get[String]("stubbed.data.callvalidate")

  private var stubbedSurepayData: Map[String, SurepayData] = Map.empty
  private var stubbedCallValidateData: Map[AccountDetails, CallValidateData] = Map.empty

  private def loadDataFile(stubbedDataFile: String): File = {
    environment.getExistingFile(stubbedDataFile).getOrElse {
      throw new Exception("Unable to find " + stubbedDataFile)
    }
  }

  @Provides
  @Singleton
  def loadStubbedCallValidateData: Map[AccountDetails, CallValidateData] = {
    if (stubbedCallValidateData.isEmpty) {
      val mockedDataStream = CSVReader.open(loadDataFile(callValidateDataFile)).toStreamWithHeaders
      val it = mockedDataStream.iterator
      while (it.hasNext) {
        val data = it.next()
        val stubbedData: CallValidateData = CallValidateData(
          statusCode = data("status-code").toInt,
          title = data("title"),
          firstname = data("firstname"),
          surname = data("surname"),
          flatNumber = if (data("flat-number").trim.isEmpty) None else Some(data("flat-number")),
          streetNumber = if (data("street-number").trim.isEmpty) None else Some(data("street-number")),
          street = data("street"),
          town = data("town"),
          postcode = data("postcode"),
          accountExists = data("account-exists")
        )
        stubbedCallValidateData += (AccountDetails(data("sort-code"), data("account-number")) -> stubbedData)
      }
    }
    stubbedCallValidateData
  }

  @Provides
  @Singleton
  def loadStubbedSurepayData: Map[String, SurepayData] = {
    if (stubbedSurepayData.isEmpty) {
      val mockedDataStream = CSVReader.open(loadDataFile(surepayDataFile)).toStreamWithHeaders
      val it = mockedDataStream.iterator
      while (it.hasNext) {
        val data = it.next()
        val stubbedData: SurepayData = SurepayData(
          statusCode = data("status-code").toInt,
          matched = data("matched").toBoolean,
          reasonCode = if (data("reason-code").trim.isEmpty) None else Some(data("reason-code")),
          accountName = if (data("account-name").trim.isEmpty) None else Some(data("account-name"))
        )
        stubbedSurepayData += (data("identification") -> stubbedData)
      }
    }
    stubbedSurepayData
  }

}
