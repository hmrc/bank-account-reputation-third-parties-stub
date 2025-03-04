# bank-account-reputation-third-parties-stub

This stub is designed to mock the implementation of any third-party downstream 
services that the `bank-account-reputation` service interacts with.

## Overview

The stub provides pre-configured responses for the following:

- **Modulr API** – A confirmation of PAYEE API used to validate bank account details.

## Modulr API Stub

The Modulr API stub is configured to return different responses depending on the sort code and account number provided.

The input and expected output data can be found at `/conf/data/modulr-data.csv`.

