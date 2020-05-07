package idv.ray.geocrawler.util.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public abstract class GeoDataMixin {

	public static class SrcTaskSerializer extends JsonSerializer<Integer> {	
		
		public static String QUERY_GEODATA_BY_ID_URL;

		@Override
		public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(QUERY_GEODATA_BY_ID_URL + String.valueOf(value));

		}

		public SrcTaskSerializer() {
			super();
		}

	}

	@JsonSerialize(using = SrcTaskSerializer.class)
	@JsonProperty("srcTask")
	public abstract String getSrcTaskId();

	@JsonIgnore
	int srcTaskId;

}
