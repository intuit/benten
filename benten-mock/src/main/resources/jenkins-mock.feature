Feature: jenkins mock

  Background:

  Scenario: pathMatches('/job/{name}/api/json')
    * print requestHeaders
    * def response = read('job-by-name.json')

  Scenario: pathMatches('/api/json')
    * def response = read('all-jobs.json')

  Scenario: pathMatches('/job/{name}/build/api/json') && methodIs('post')
    * def responseStatus = 200
    * def response = read('job-by-name.json')
    * def responseHeaders = {}
    * set responseHeaders.Location = 'https://localhost:8001/queue/item/445/'

  Scenario: pathMatches('/job/{name}/{buildnumber}/api/json') && methodIs('get')
    * def responseStatus = 200
    * def response = read('job-by-build.json')


  Scenario:
    * print request



