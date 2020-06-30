package idv.ray.geocrawler.master.config;

import org.springframework.core.convert.converter.Converter;

import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.geodata.Task.Status;

public class StringToTaskStatusConverter implements Converter<String, Task.Status> {

	@Override
	public Status convert(String source) {

		return Task.Status.valueOf(source.toUpperCase());
	}

}
