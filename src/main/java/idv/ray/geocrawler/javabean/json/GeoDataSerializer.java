package idv.ray.geocrawler.javabean.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import idv.ray.geocrawler.javabean.geodata.GeoData;
import idv.ray.geocrawler.javabean.geodata.Resource;

public class GeoDataSerializer implements JSONSerializable<GeoData>, JSONDeserializable<GeoData> {
	@Override
	public String serialize(GeoData geoData) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(geoData);
	}

	@Override
	public GeoData deserialize(String jsonString, Class<? extends GeoData> GeoDataClass)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Reader reader = new StringReader(jsonString);

		return mapper.readValue(reader, GeoDataClass);
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		GeoDataSerializer s = new GeoDataSerializer();
		String json = "{\"id\":5,\"level\":1,\"link\":\"abc.com\"}";
		Resource task = (Resource) s.deserialize(json, Resource.class);
		System.out.println(task);
	}
}