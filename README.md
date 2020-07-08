# Created for Applitools hackathon
https://go.applitools.com/hackathon-apply

# How to run
### Prerequisites
* JDK >= 11 installed
* Local browsers installed
    * Chrome for modern tests
    * Chrome, Edge & Safari for traditional tests
* Set your APPLITOOLS_API_KEY environment variable
    * Mac:
        ```
        export APPLITOOLS_API_KEY='YOUR_API_KEY'
        ```
    * Windows:
        ```
        set APPLITOOLS_API_KEY='YOUR_API_KEY'
        ```
  
### Run from command line
Traditional tests
```shell script
./gradlew clean test --tests TraditionalTestsV1
./gradlew clean test --tests TraditionalTestsV2
```
Modern tests
```shell script
./gradlew clean test --tests ModernTestsV1
./gradlew clean test --tests ModernTestsV2 
```