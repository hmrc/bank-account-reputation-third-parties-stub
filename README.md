# bank-account-reputation-third-parties-stub

This is a stub that mocks out the implementation of any third parties that BARS contacts

The Surepay stub has been configured to set the following responses (Note that name supplied does not matter):

## Personal endpoint

| Sort code | Account number | accountNumberIsWellFormatted | sortCodeIsPresentOnEISCD | nonStandardAccountDetailsRequiredForBacs | sortCodeBankName | accountExists | nameMatches | sortCodeSupportsDirectCredit | sortCodeSupportsDirectDebit | iban |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 204578 | 86473611 | yes | yes | no | BARCLAYS BANK PLC | yes | yes | yes | yes | GB02 BARC 2045 7886 4736 11 |
| 204578 | 74597611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB72 BARC 2045 7874 5976 11 |
| 204577 | 86473611 | yes | yes | no | BARCLAYS BANK PLC | yes | yes | yes | yes | GB55 BARC 2045 7786 4736 11 |
| 204578 | 76523611 | yes | yes | no | BARCLAYS BANK PLC | no | yes | yes| yes | GB63 BARC 2045 7876 5236 11 |  
| 204577 | 76523611 | yes | yes | no | BARCLAYS BANK PLC | no | yes | yes | yes | GB19 BARC 2045 7776 5236 11 |  
| 204578 | 56945688 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB59 BARC 2045 7856 9456 88 |  
| 204578 | 96883600 | no | yes | inapplicable | BARCLAYS BANK PLC | inapplicable | inapplicable | yes | yes | GB21 BARC 2045 7896 8836 00 |
| 204578 | 66945688 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB44 BARC 2045 7866 9456 88 | 
| 204578 | 84597611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB57 BARC 2045 7884 5976 11 | 
| 204578 | 56523611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB93 BARC 2045 7856 5236 11 | 
| 204578 | 06883600 | no | yes | inapplicable | BARCLAYS BANK PLC | inapplicable | inapplicable | yes | yes | GB59 BARC 2045 7806 8836 00 |
| 301658 | 06883600 | indeterminate | yes | no | SWANSEA BUILDING SOCIETY | indeterminate | indeterminate | yes | yes | N/A |

## Business endpoint

| Sort code | Account number | accountNumberIsWellFormatted | sortCodeIsPresentOnEISCD | nonStandardAccountDetailsRequiredForBacs | sortCodeBankName | accountExists | nameMatches | sortCodeSupportsDirectCredit | sortCodeSupportsDirectDebit | iban |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 204578 | 86473611 | yes | yes | no | BARCLAYS BANK PLC | yes | yes | yes | yes | GB02 BARC 2045 7886 4736 11 |
| 204578 | 74597611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB72 BARC 2045 7874 5976 11 |
| 204577 | 86473611 | yes | yes | no | BARCLAYS BANK PLC | yes | yes | yes | yes | GB55 BARC 2045 7786 4736 11 | 
| 204578 | 44377611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB31 BARC 2045 7844 3776 11 |   
| 204578 | 44311677 | yes | yes | no | BARCLAYS BANK PLC | yes | yes | yes | yes | GB08 BARC 2045 7844 3116 77 | 
| 204578 | 56945688 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB59 BARC 2045 7856 9456 88 |  
| 204578 | 96883600 | no | yes | inapplicable | BARCLAYS BANK PLC | inapplicable | inapplicable | yes | yes | GB21 BARC 2045 7896 8836 00 |
| 204578 | 66945688 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB44 BARC 2045 7866 9456 88 | 
| 204578 | 84597611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB57 BARC 2045 7884 5976 11 | 
| 204578 | 56523611 | yes | yes | no | BARCLAYS BANK PLC | indeterminate | indeterminate | yes | yes | GB93 BARC 2045 7856 5236 11 | 
| 204578 | 06883600 | no | yes | inapplicable | BARCLAYS BANK PLC | inapplicable | inapplicable | yes | yes | GB59 BARC 2045 7806 8836 00 |
| 301658 | 06883600 | indeterminate | yes | no | SWANSEA BUILDING SOCIETY | indeterminate | indeterminate | yes | yes | N/A |

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
