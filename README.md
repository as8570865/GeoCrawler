# GeoCrawler

  <p>It is a http-based distributed geo-crawler. It crawls geo-information which follows OGC open standard such as SOS, WMS and WMTS. 
  
  ## Master
  <p>Master is a central server connecting to each woker. It is in charge of assigning tasks to workers and processing the returning data. Currently, OGC SOS, WFS, WMPS, WMS and WMTS open standards are handled.</p>
 
 - ### Deployment<br>
    1. Download `geocrawler-master.war` file and deploy it to Tomcat.<br>
    2. The seeds (keywords for google searching) and crawled level of the crawler can be adjusted in the `/resource/seedBeans.xml` file    

  ## Worker
  <p>A worker is responsible for determining whether the task provided by the master is a geo-resource. Then, it returns the result and get next task.
  
  - ### Installation<br>  
     1. Download `geocrawler-worker.jar`.
     2. Make sure the `masterUrl` in `/BOOT-INF/classed/Beans.xml` is correct. (open the jar file as a zip file and modify it)
  - ### Usage<br>
    Execute the worker jar and remember to register a name.
    ```
    java -jar geocrawler-worker.jar
    ```
    Moreover, by visiting `/geocrawler-master/monitor/worker` , users can check whether the worker was registered successfully.
    
 - ### Determining mechanism<br>
   OGC defines many open standards for geospatial resources. In those standards, as an OGC open geospatial resource, a capabilities file is necessary. Geocrawler determines URLs by sending query for for their capabilities file. For example,
   ```
   ?request=GetCapabilities&service=SOS
   ```    
  
  
  ## Monitor
  <p>In addition to crawling data, Geocrawler also provides RESTful-based monitoring APIs for users to observe the progress.
  
  - ### Usage
    Not only `task` and `resource` can be querried, `worker` is also avialable. For example,<br>
    ```url
    /geocrawler-master/monitor/resource
    ```
  - ### Query parameter
     `task` and `resource` include `id`, `MinLevel`, `MaxLevel`, `link`, `geoType`, `srcTask`, `workerName` and `status` (`task` only)
     `worker` includes `ipAddress` and `name`
     
     For example, query with `geoType` and `maxLevel`
     ```
     /geocrawler-master/monitor/resource?geoType=sos&maxLevel=1
     ```
     query with `workerName`
     ```
     /geocrawler-master/monitor/resource?workerName=<worker_name>
     ```
   - ### Query result
     The results will be returned and displayed in JSON formate. As shown below,
      ```java
      {
          "resultSize": 2,
              "result": [
                  {
                      "id": 100,
                      "level": 1,
                      "link": "https://sos.service.resource",
                      "geotype": "sos",
                      "srcTask": "http://master.root/geocrawler-master/monitor/task?id=50",
                      "worker": {
                          "ipAddress": "140.xxx.xxx.xx",
                          "name": "worker1"
                      }
                  },
                  {
                      "id": 101,
                      "level": 1,
                      "link": "http://sos.service.resource2",
                      "geotype": "sos",
                      "srcTask": "http://master.root>/geocrawler-master/monitor/task?id=51",
                      "worker": {
                          "ipAddress": "140.xxx.xxx.xx",
                          "name": "worker2"
                      }
                  }
              ]
      }
      ```
     
  
