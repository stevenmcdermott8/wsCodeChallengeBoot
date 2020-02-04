# wsCodeChallenge - Spring Boot Implementation

Challenge Details:

BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

`[94133,94133] [94200,94299] [94600,94699]`

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

NOTES
- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices

EXAMPLES:
If the input = `[94133,94133] [94200,94299] [94600,94699]`
Then the output should be = `[94133,94133] [94200,94299] [94600,94699]`

If the input = `[94133,94133] [94200,94299] [94226,94399]`
Then the output should be = `[94133,94133] [94200,94399]`

Evaluation Guidelines:
Your work will be evaluated against the following criteria:
- Successful implementation
- Efficiency of the implementation
- Design choices and overall code organization
- Code quality and best practices


# Implementation Information
Implemented spring boot application

Pre requisit: Maven 3.x should be installed, Java JDK 8+ should be installed.
1. Once repository is cloned, navigate to root directory containing pom.xml file in your terminal.
2. Run command `mvn clean install`
3. Run command `java -jar target/ws-code-challenge-boot-0.0.1-SNAPSHOT.jar`
4. Once running, see WsChallenge.postman_collection.json for examples to retrieve zip code ranges via postman.

There are three endpoints exposed to retrieve zip code ranges:

1. GET with query parameter 
    - `http://localhost:8080/wsChallenge/zipRange?zipCodeRanges=10000,20000|50000,60000|11111,11112`
2. GET with path parameter
    - `http://localhost:8080/wsChallenge/zipRange/10000,20000|50000,60000|11111,11112`
3. POST with JSON body
    - `http://localhost:8080/wsChallenge/zipRange`
    - Example request body:
    
    ```json
    {
    "zipCodeRanges": [
        {
            "zipRange": [
                "29999",
                "40000"
            ]
        },
        {
            "zipRange": [
                "10000",
                "50000"
            ]
        },
        {
            "zipRange": [
                "20000",
                "30000"
            ]
        }
      ]
   }

Example Response for all endpoints:
```json
[
    [
        "10000",
        "20000"
    ],
    [
        "50000",
        "60000"
    ]
]
