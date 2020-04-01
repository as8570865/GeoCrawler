package idv.ray.geocrawler.util.jsonserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JSONDeserializable<T > {
	// deserialize json string to object
	public T deserialize(String jsonString,Class<? extends T> objClass) throws JsonParseException, JsonMappingException, IOException;
}
