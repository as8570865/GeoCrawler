package idv.ray.geocrawler.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.ray.geocrawler.model.GeoData;
import idv.ray.geocrawler.model.Task;
import idv.ray.geocrawler.service.TaskService;

@Controller
public class TaskController {
	
	private TaskService taskService;
	
	private Map<String,String> dataTypeMap;

	public TaskController(TaskService taskService,Map<String,String> dataTypeMap) {
		this.taskService = taskService;
		this.dataTypeMap=dataTypeMap;
	}

	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String req, @PathVariable("geoType") String geoType)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, JSONException {

		List<GeoData> resultList = new ArrayList<GeoData>();

		JSONObject reqJson = new JSONObject(req);
		Task task = new Task(reqJson.getJSONObject("task"));
		if (reqJson.has("result")) {
			String resultType = reqJson.getJSONObject("result").getString("type");
			JSONArray resultValueJsonArr = reqJson.getJSONObject("result").getJSONArray("value");
			for (int i = 0; i < resultValueJsonArr.length(); i++) {

				// create geodata obj by class name
				Class<?> clazz = Class.forName(dataTypeMap.get(resultType));
				Constructor<?> constructor = clazz.getConstructor(JSONObject.class);
				GeoData geoData = (GeoData) constructor.newInstance(resultValueJsonArr.getJSONObject(i));
				// add to result list
				resultList.add(geoData);

				// print geoData
				System.out.println(geoData.toString());
			}
			taskService.post(geoType, task, resultList);
		}

		Task t=taskService.getNext(geoType, task);
		//System.out.println(t.toString());
		return t.toJsonString();
	}

}
