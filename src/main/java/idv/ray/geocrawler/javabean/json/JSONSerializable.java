package idv.ray.geocrawler.javabean.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JSONSerializable<T > {
	// serialize object to json string
	public String serialize(T obj) throws JsonProcessingException;

}
