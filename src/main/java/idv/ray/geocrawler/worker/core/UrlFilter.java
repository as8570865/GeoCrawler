package idv.ray.geocrawler.worker.core;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlFilter {

	@Autowired
	@Resource(name = "unacceptableGeoKeywordSet")
	private Set<String> unacceptableGeoKeywordSet;
	@Autowired
	@Resource(name = "unacceptableUrlFormatSet")
	private Set<String> unacceptableUrlFormatSet;

	@Autowired
	@Qualifier("domainNamePatternString")
	private String domainNamePatternString;
	private Pattern patternDomainName;

	@PostConstruct
	public void init() {
		patternDomainName = Pattern.compile(domainNamePatternString);
	}

	// make the url match specific pattern
	public String getDomainName(String url) {
		String domainName = "";
		Matcher matcher = patternDomainName.matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).trim().split("&")[0].split("%")[0].split("\\?")[0];
		}
		return domainName;
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
