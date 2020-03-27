package idv.ray.geocrawler.worker.core;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlFilter {

	@Autowired
	private Set<String> unacceptableGeoKeywordSet;
	@Autowired
	private Set<String> unacceptableUrlFormatSet;

	@Bean
	public UrlFilter urlFilter() {
		return new UrlFilter();
	}

	public UrlFilter() {
		System.out.println("in the urlfilter constructor...");
	}

	// check url file format (eg. not pdf. or others...)
	public boolean isCorrectFormat(String urlString) throws MalformedURLException, IOException {

		if (!urlString.isEmpty()) {
			if (!urlString.contains("pdf")) {
				for (String notGeoKeywordString : unacceptableGeoKeywordSet) {
					if (urlString.contains(notGeoKeywordString)) {
						return false;
					}
				}

				HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
				urlConnection.setConnectTimeout(10000); // 5 sec
				urlConnection.setReadTimeout(10000); // 10 sec
				String contentType = urlConnection.getContentType();
				System.out.println("content type: " + contentType);
				if (contentType != null) {
					if (!unacceptableUrlFormatSet.contains(contentType)) {
						return true;
					}
				}
			}
		}
		return false;

	}
}
