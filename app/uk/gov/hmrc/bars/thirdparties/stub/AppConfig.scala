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

package uk.gov.hmrc.bars.thirdparties.stub

import com.github.tototoshi.csv.CSVReader
import com.google.inject.{AbstractModule, Provides}
import play.api.{Configuration, Environment}
import uk.gov.hmrc.bars.thirdparties.stub.models.AccountDetails
import uk.gov.hmrc.bars.thirdparties.stub.models.callvalidate.CallValidateData
import uk.gov.hmrc.bars.thirdparties.stub.models.modulr.ModulrData

import java.io.File
import javax.inject.Singleton

class AppConfig(environment: Environment, configuration: Configuration) extends AbstractModule {

  lazy val callValidateDataFile: String = configuration.get[String]("stubbed.data.callvalidate")
  lazy val modulrDataFile: String = configuration.get[String]("stubbed.data.modulr")

  private var stubbedCallValidateData: Map[AccountDetails, CallValidateData] = Map.empty
  private var stubbedModulrData: Seq[ModulrData] = Seq.empty

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
  def loadStubbedModulrData: Seq[ModulrData] = {
    if (stubbedModulrData.isEmpty) {
      val mockedDataStream = CSVReader.open(loadDataFile(modulrDataFile)).toStreamWithHeaders
      val it = mockedDataStream.iterator
      while (it.hasNext) {
        val data = it.next()
        val stubbedData: ModulrData = ModulrData(
          statusCode = data("status-code").toInt,
          resultCode = data("result-code"),
          paymentAccountId = data("payment-account-id"),
          sortCode = data("sort-code"),
          accountNumber = data("account-number"),
          secondaryAccountId = if (data("secondary-account-id").trim.isEmpty) None else Some(data("secondary-account-id")),
          accountType = data("account-type"),
          name = data("name")
        )

        stubbedModulrData = stubbedModulrData :+ stubbedData
      }
    }
    stubbedModulrData
  }
}
