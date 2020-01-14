package idv.ray.geoworker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import idv.ray.geodata.Task;
import idv.ray.geourl.GeoStandard;

public class Crawler {
	private static Pattern patternDomainName;
	private Matcher matcher;
	private static final String USER_AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
	private static final String DOMAIN_NAME_PATTERN = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	}

	// not-allowed url format set
	Set<String> notOkUrlFormatSet;
	int maxPageNum;

	// geo standard list
	private Set<GeoStandard> geoStandardSet;

	public Crawler(Set<String> notOkUrlFormatSet, int maxPageNum, Set<GeoStandard> geoStandardSet) {
		this.notOkUrlFormatSet = notOkUrlFormatSet;
		this.maxPageNum = maxPageNum;
		this.geoStandardSet = geoStandardSet;
	}

	private boolean isOkUrl(String urlString) throws MalformedURLException, IOException {

		if (!urlString.isEmpty()) {
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
			urlConnection.setConnectTimeout(10000); // 5 sec
			urlConnection.setReadTimeout(10000); // 10 sec
			String contentType = urlConnection.getContentType();
			System.out.println("content type: " + contentType);
			if (contentType != null) {
				if (!notOkUrlFormatSet.contains(contentType)) {
					return true;
				}
			}
		}
		return false;

	}

	private String getDomainName(String url) {

		String domainName = "";
		matcher = patternDomainName.matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim();
		}
		return domainName;

	}

	private Set<String> crawlByKeyword(String keyword) {

		Set<String> result = new HashSet<String>();
		for (int i = 0; i < maxPageNum; i++) {
			String request = "https://www.google.com/search?q=" + keyword + "&start=" + i;
			System.out.println("Sending request..." + request);

			try {

				// need http protocol, set this as a Google bot agent :)
				Document doc = Jsoup.connect(request).userAgent(USER_AGENT).timeout(60000).get();

				// get all links
				Elements links = doc.select("a[href]");
				for (Element link : links) {

					String temp = link.attr("href");
					if (temp.startsWith("/url?q=")) {
						// use regex to get domain name
						String urlString = getDomainName(temp).split("&")[0];

						if (isOkUrl(urlString)) {
							System.out.println("add this url: " + urlString);
							System.out.println("/////////");
							result.add(urlString);
						}

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private Set<String> crawlByUrl(String request) {

		Set<String> result = new HashSet<String>();

		System.out.println("Sending request..." + request);

		try {

			// need http protocol, set this as a Google bot agent :)
			Document doc = Jsoup.connect(request).userAgent(USER_AGENT).timeout(10000).get();

			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {

				// get attribute href and convert to specific format
				String urlString = getDomainName(link.attr("href")).split("&")[0];
				if (isOkUrl(urlString)) {
					result.add(urlString);
					System.out.println("add this url: " + urlString);
					System.out.println("/////////");
					// String contentType1 = new URL(urlString).openConnection().getContentType();
					// System.out.println("content type: " + contentType1);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Set<String> crawl(Task task) {
		if (task.getLevel() == 0) {
			System.out.println("crawling by keyword!!");
			return crawlByKeyword(task.getLink());
		} else {
			System.out.println("crawling by url!!");
			return crawlByUrl(task.getLink());
		}
	}
}
