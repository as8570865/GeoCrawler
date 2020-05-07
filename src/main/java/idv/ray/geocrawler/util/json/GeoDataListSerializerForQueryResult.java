package idv.ray.geocrawler.util.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public class GeoDataListSerializerForQueryResult extends JsonSerializer<List<? extends GeoData>>{

	public static String QUERY_GEODATA_BY_ID_URL;

	@Override
	public void serialize(List<? extends GeoData> value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		
	}

}
