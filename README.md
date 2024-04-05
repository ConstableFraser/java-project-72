# PAGE SEO-ANALYZER

### [tests, linter status and maintainability]
[![Actions Status](https://github.com/ConstableFraser/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/ConstableFraser/java-project-72/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/711064afba93eb59701d/maintainability)](https://codeclimate.com/github/ConstableFraser/java-project-72/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/711064afba93eb59701d/test_coverage)](https://codeclimate.com/github/ConstableFraser/java-project-72/test_coverage)

## [demo version]:
https://pageanalyzerjavalin.onrender.com

## [useful features]
1. checking website for availability
2. scanning the specified page of website for seo-suitability (tags: h1, title and description)
3. report generation

## [how it works]
1. launch the web service Page Analyzer
2. type the name of the website in according with requirements and push the Enter
3. information page about website will open
4. push the "Проверить" (launch checking)
5. see result of checking at the table below
6. enjoy)

## [page analyzer example]
![mainPage.png](app/src/main/resources/static/screenshots/mainPage.png)
![listUrls.png](app/src/main/resources/static/screenshots/listUrls.png)
![ramOkna.png](app/src/main/resources/static/screenshots/ramOkna.png)

## [technical stack]
**backend**: java, javalin, PostgresQL

**frontend**: jte template, bootstrap and css

**tooling**: lombok, unirest, jsoup, jacoco, checkstyle, mockwebserver, assertj 

**deploy**: PaaS on render.com
