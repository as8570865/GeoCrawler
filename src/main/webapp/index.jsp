<!DOCTYPE html>
<html>

<head>
    <title>GeoCrawler</title>
</head>

<body onload="init()">
    <center>
        <h1>GeoCrawler</h1>

        <table id="objTable">
            <tr>
                <td>Select worker, task or resource:
                    <select id="obj" onchange="changeQueryCondition()">
                        <option value="worker">worker</option>
                        <option value="task">task</option>
                        <option value="resource">resource</option>
                    </select>
                </td>
            </tr>
        </table>

        <table id="queryConditionTable"></table>
        <div id="queryButton"></div>

        <table id="idleWorkerDetectorTable">
            <tr>
                <td>Select the idle duration: <select id="idleDurationSelect"></select> hours</td>
            </tr>
        </table>
        <input type="submit" value="Detect idle worker" onclick="getIdleWorkers()">

        <table id="idleWorkerTable"></table>

        <div id="resultSize"></div>
        <table id="resultTable" border="1"></table>

    </center>

    <div id="test"></div>
</body>

<script>

    function init() {
        httpRequest("http://140.115.110.81:8080/geocrawler-master/queryCondition?type=worker", selectWorker);
        generateIdleDurationSelect();
    }

    function generateIdleDurationSelect() {
        var idleDurationSelect = document.getElementById('idleDurationSelect');

        for (i = 1; i <= 24; i++) {
            idleDurationSelect.options[idleDurationSelect.options.length] = new Option(i, i);
        }
    }

    function getIdleWorkers() {
        var idleDuration = document.getElementById("idleDurationSelect").value;
        httpRequest("http://140.115.110.81:8080/geocrawler-master/monitor/worker/idle?duration=" + idleDuration, displayIdleWorker);
    }

    function displayIdleWorker(idleWorkerArr) {
        var table = document.getElementById("idleWorkerTable");
        table.innerHTML = "";
        for (i = 0; i < idleWorkerArr.length; i++) {

            var row = table.insertRow(0);
            var cell1 = row.insertCell(0);
            cell1.innerHTML = "<tr><td> worker: " + idleWorkerArr[i].name + " is idle</td></tr>";

        }
        document.getElementById("idleWorkerTable").style.color = "#ff0000";
    }

    function changeQueryCondition() {
        var obj = document.getElementById("obj").value;

        if (obj == "task") {
            httpRequest("http://140.115.110.81:8080/geocrawler-master/queryCondition?type=task", selectTask);
        } else if (obj == "resource") {
            httpRequest("http://140.115.110.81:8080/geocrawler-master/queryCondition?type=resource", selectResource);
        } else if (obj == "worker") {
            httpRequest("http://140.115.110.81:8080/geocrawler-master/queryCondition?type=worker", selectWorker);
        }

    }

    function httpRequest(url, callback) {

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                var responseJson = JSON.parse(xhr.responseText);
                callback(responseJson);
            }
        }
        xhr.open('GET', url, true);
        xhr.send(null);
    }

    function selectTask(taskQCJson) {

        document.getElementById("queryConditionTable").innerHTML
            = "<tr><td> id: <input type=\"text\" id=\"id\"></td></tr>"
            + "<tr><td> geoType: <select id=\"geoTypeSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> worker ip: <select id=\"workerIpSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> worker name: <select id=\"workerNameSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> minimum level: <select id=\"minLevelSelect\"></td></tr>"
            + "<tr><td> maximum level: <select id=\"maxLevelSelect\"></td></tr>"
            + "<tr><td> status: <select id=\"statusSelect\"><option value=''>null</option></td></tr>";

        var geoTypeSelect = document.getElementById('geoTypeSelect');
        for (i = 0; i < taskQCJson.geoTypeSet.length; i++) {
            geoTypeSelect.options[geoTypeSelect.options.length] = new Option(taskQCJson.geoTypeSet[i], taskQCJson.geoTypeSet[i]);
        }

        var workerIpSelect = document.getElementById('workerIpSelect');
        var workerNameSelect = document.getElementById('workerNameSelect');
        for (i = 0; i < taskQCJson.workerList.length; i++) {
            workerIpSelect.options[workerIpSelect.options.length] = new Option(taskQCJson.workerList[i].ipAddress, taskQCJson.workerList[i].ipAddress);
            workerNameSelect.options[workerNameSelect.options.length] = new Option(taskQCJson.workerList[i].name, taskQCJson.workerList[i].name);
        }

        var minLevelSelect = document.getElementById('minLevelSelect');
        var maxLevelSelect = document.getElementById('maxLevelSelect');
        for (i = 0; i <= taskQCJson.maxLevel; i++) {
            var maxLevel=new Option(i, i);
            var minLevel=new Option(i, i);
            minLevelSelect.options[minLevelSelect.options.length] = minLevel;
            maxLevelSelect.options[maxLevelSelect.options.length] = maxLevel;
            if(i==taskQCJson.maxLevel){
                maxLevel.setAttribute('selected',true);
            }
        }

        var statusSelect = document.getElementById('statusSelect');
        for (i = 0; i < taskQCJson.statusList.length; i++) {
            statusSelect.options[statusSelect.options.length] = new Option(taskQCJson.statusList[i], taskQCJson.statusList[i]);
        }

        document.getElementById("queryButton").innerHTML
            = "<input type=\"submit\" value=\"Query\" onclick=\"getTasks()\">";

    }

    function selectResource(taskQCJson) {

        document.getElementById("queryConditionTable").innerHTML
            = "<tr><td> id: <input type=\"text\" id=\"id\"></td></tr>"
            + "<tr><td> geoType: <select id=\"geoTypeSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> worker ip: <select id=\"workerIpSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> worker name: <select id=\"workerNameSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> minimum level: <select id=\"minLevelSelect\"></td></tr>"
            + "<tr><td> maximum level: <select id=\"maxLevelSelect\"></td></tr>";

        var geoTypeSelect = document.getElementById('geoTypeSelect');
        for (i = 0; i < taskQCJson.geoTypeSet.length; i++) {
            geoTypeSelect.options[geoTypeSelect.options.length] = new Option(taskQCJson.geoTypeSet[i], taskQCJson.geoTypeSet[i]);
        }

        var workerIpSelect = document.getElementById('workerIpSelect');
        var workerNameSelect = document.getElementById('workerNameSelect');
        for (i = 0; i < taskQCJson.workerList.length; i++) {
            workerIpSelect.options[workerIpSelect.options.length] = new Option(taskQCJson.workerList[i].ipAddress, taskQCJson.workerList[i].ipAddress);
            workerNameSelect.options[workerNameSelect.options.length] = new Option(taskQCJson.workerList[i].name, taskQCJson.workerList[i].name);
        }

        var minLevelSelect = document.getElementById('minLevelSelect');
        var maxLevelSelect = document.getElementById('maxLevelSelect');
        for (i = 0; i <= taskQCJson.maxLevel; i++) {
            var maxLevel=new Option(i, i);
            var minLevel=new Option(i, i);
            minLevelSelect.options[minLevelSelect.options.length] = minLevel;
            maxLevelSelect.options[maxLevelSelect.options.length] = maxLevel;
            if(i==taskQCJson.maxLevel){
                maxLevel.setAttribute('selected',true);
            }
        }

        document.getElementById("queryButton").innerHTML
            = "<input type=\"submit\" value=\"Query\" onclick=\"getResources()\">";

            document.getElementById("queryButton").innerHTML
            = "<input type=\"submit\" value=\"Query\" onclick=\"getResources()\">";
    }

    function selectWorker(taskQCJson) {

        document.getElementById("queryConditionTable").innerHTML
            = "<tr><td> worker ip: <select id=\"workerIpSelect\"><option value=''>null</option></td></tr>"
            + "<tr><td> worker name: <select id=\"workerNameSelect\"><option value=''>null</option></td></tr>";

        var workerIpSelect = document.getElementById('workerIpSelect');
        var workerNameSelect = document.getElementById('workerNameSelect');
        for (i = 0; i < taskQCJson.workerList.length; i++) {
            workerIpSelect.options[workerIpSelect.options.length] = new Option(taskQCJson.workerList[i].ipAddress, taskQCJson.workerList[i].ipAddress);
            workerNameSelect.options[workerNameSelect.options.length] = new Option(taskQCJson.workerList[i].name, taskQCJson.workerList[i].name);
        }

        document.getElementById("queryButton").innerHTML
            = "<input type=\"submit\" value=\"Query\" onclick=\"getWorkers()\">";
    }

    function getWorkers() {
        var workerIp = document.getElementById('workerIpSelect').value;
        var workerName = document.getElementById('workerNameSelect').value;

        var baseUrl="http://140.115.110.81:8080/geocrawler-master/monitor/worker?"

        if(workerIp!=""){
            baseUrl=baseUrl+"ipAddress="+workerIp;
        }

        if(workerName!=""){
            baseUrl=baseUrl+"&name="+workerName;
        }
 
        httpRequest(baseUrl, displayWorker);
    }

    function displayWorker(resultJson) {
        document.getElementById("resultSize").innerHTML="result size:"+resultJson.resultSize;
        var table = document.getElementById("resultTable");
        table.innerHTML = "<tr><td>name</td><td>ip address</td><td>last crawling time</td></tr>";
        for (i = 0; i < resultJson.resultSize; i++) {
            var row = table.insertRow(1);
            var name = row.insertCell(0);
            var ipAddress = row.insertCell(1);
            var lastCrawlingTime = row.insertCell(2);
            name.innerHTML = resultJson.result[i].name;
            ipAddress.innerHTML = resultJson.result[i].ipAddress;
            lastCrawlingTime.innerHTML = resultJson.result[i].lastCrawlingTime;

        }
    }

    function getTasks() {
        var id = document.getElementById('id').value;
        var geoType = document.getElementById('geoTypeSelect').value;
        var workerIp = document.getElementById('workerIpSelect').value;
        var workerName = document.getElementById('workerNameSelect').value;
        var minLevel = document.getElementById('minLevelSelect').value;
        var maxLevel = document.getElementById('maxLevelSelect').value;
        var status = document.getElementById('statusSelect').value;

        var baseUrl="http://140.115.110.81:8080/geocrawler-master/monitor/task?";

        if(id!=""){
            baseUrl=baseUrl+"id="+id;
        }
        if(geoType!=""){
            baseUrl=baseUrl+"&geoType="+geoType;
        }
        if(workerIp!=""){
            baseUrl=workerIp+"&workerIp="+workerIp;
        }
        if(workerName!=""){
            baseUrl=baseUrl+"&workerName="+workerName;
        }
        if(minLevel!=""){
            baseUrl=baseUrl+"&minLevel="+minLevel;
        }
        if(maxLevel!=""){
            baseUrl=baseUrl+"&maxLevel="+maxLevel;
        }
        if(status!=""){
            baseUrl=baseUrl+"&status="+status;
        }

        httpRequest(baseUrl, displayTask);
    }

    function displayTask(resultJson) {
        document.getElementById("resultSize").innerHTML="result size:"+resultJson.resultSize;
        var table = document.getElementById("resultTable");
        table.innerHTML = "<tr><td>id</td><td>level</td><td>link</td><td>time</td><td>geotype</td><td>source task</td><td>worker name</td><td>status</td></tr>";
        for (i = 0; i < resultJson.resultSize; i++) {
            var row = table.insertRow(1);
            var id = row.insertCell(0);
            var level= row.insertCell(1);
            var link = row.insertCell(2);
            var time = row.insertCell(3);
            var geotype = row.insertCell(4);
            var srcTask = row.insertCell(5);
            var workerName = row.insertCell(6);
            var status = row.insertCell(7);
            id.innerHTML = resultJson.result[i].id;
            level.innerHTML = resultJson.result[i].level;
            link.innerHTML = resultJson.result[i].link;
            time.innerHTML = resultJson.result[i].time;
            geotype.innerHTML = resultJson.result[i].geotype;
            srcTask.innerHTML = resultJson.result[i].srcTask;
            if(resultJson.result[i].worker!=null){
                workerName.innerHTML = resultJson.result[i].worker.name;
            }else{
                workerName.innerHTML ="null";
            }
            
            status.innerHTML = resultJson.result[i].status;
        }

    }

    function getResources() {
        var id = document.getElementById('id').value;
        var geoType = document.getElementById('geoTypeSelect').value;
        var workerIp = document.getElementById('workerIpSelect').value;
        var workerName = document.getElementById('workerNameSelect').value;
        var minLevel = document.getElementById('minLevelSelect').value;
        var maxLevel = document.getElementById('maxLevelSelect').value;

        var baseUrl="http://140.115.110.81:8080/geocrawler-master/monitor/resource?";

        if(id!=""){
            baseUrl=baseUrl+"id="+id;
        }
        if(geoType!=""){
            baseUrl=baseUrl+"&geoType="+geoType;
        }
        if(workerIp!=""){
            baseUrl=workerIp+"&workerIp="+workerIp;
        }
        if(workerName!=""){
            baseUrl=baseUrl+"&workerName="+workerName;
        }
        if(minLevel!=""){
            baseUrl=baseUrl+"&minLevel="+minLevel;
        }
        if(maxLevel!=""){
            baseUrl=baseUrl+"&maxLevel="+maxLevel;
        }

        httpRequest(baseUrl, displayResource);
    }

    function displayResource(resultJson) {
        document.getElementById("resultSize").innerHTML="result size:"+resultJson.resultSize;
        var table = document.getElementById("resultTable");
        table.innerHTML = "<tr><td>id</td><td>level</td><td>link</td><td>time</td><td>geotype</td><td>source task</td><td>worker name</td></tr>";
        for (i = 0; i < resultJson.resultSize; i++) {
            var row = table.insertRow(1);
            var id = row.insertCell(0);
            var level= row.insertCell(1);
            var link = row.insertCell(2);
            var time = row.insertCell(3);
            var geotype = row.insertCell(4);
            var srcTask = row.insertCell(5);
            var workerName = row.insertCell(6);

            id.innerHTML = resultJson.result[i].id;
            level.innerHTML = resultJson.result[i].level;
            link.innerHTML = resultJson.result[i].link;
            time.innerHTML = resultJson.result[i].time;
            geotype.innerHTML = resultJson.result[i].geotype;
            srcTask.innerHTML = resultJson.result[i].srcTask;
            workerName.innerHTML = resultJson.result[i].worker.name;

        }

    }

</script>

</html>