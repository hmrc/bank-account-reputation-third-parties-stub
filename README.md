# bank-account-reputation-third-parties-stub

This stub is designed to mock the implementation of any third-party downstream 
services that the `bank-account-reputation` service interacts with.

## Overview

The stub provides pre-configured responses for the following:

- **Modulr API** – A confirmation of PAYEE API used to validate bank account details.


### Unit testing
To run the unit tests for the application, use the following command:

```sbt test ```


### SBT Updates Plugin
This project uses the sbt-updates plugin to help manage dependency updates.
For more information on how to use the plugin, please refer to the documentation:

https://github.com/hmrc/platui/blob/main/docs/sbt-updates_plugin-usage.md#sbt-updates-plugin

To check all dependencies (libraries and plugins) the easiest way is to use below command:

```sbt ";dependencyUpdates; reload plugins; dependencyUpdates"```

Keep in mind that the output will be split into two parts where the first one will have libraries and second plugins.

## Modulr API Stub

The Modulr API stub is configured to return different responses depending on the sort code and account number provided.

The input and expected output data can be found at `/conf/data/modulr-data.csv`.

