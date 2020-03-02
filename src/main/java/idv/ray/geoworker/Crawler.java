package idv.ray.geoworker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import idv.ray.geodata.Task;
import idv.ray.geostandard.SOS;
import idv.ray.geostandard.WMS;

public class Crawler {
	private static Pattern patternDomainName;
	private Matcher matcher;
	private static final String USER_AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
	private static final String DOMAIN_NAME_PATTERN = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	}

	// not-allowed url format set
	private Set<String> notOkUrlFormatSet;
	private int maxPageNum;

	public Crawler(Set<String> notOkUrlFormatSet, int maxPageNum) {
		this.notOkUrlFormatSet = notOkUrlFormatSet;
		this.maxPageNum = maxPageNum;
		System.out.println("max crawled page is: " + maxPageNum);
		System.out.println("not ok formats are: " + notOkUrlFormatSet);
	}

	// check url file format (eg. not pdf. or others...)
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

	// check url pattern
	private String getDomainName(String url) {

		String domainName = "";
		matcher = patternDomainName.matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim().split("&")[0].split("%")[0];
		}
		return domainName;

	}

	private Set<String> crawlByKeyword(String keyword) {

		Set<String> result = new HashSet<String>();
		for (int i = 1; i <= maxPageNum; i++) {
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
						String urlString = getDomainName(temp);

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
		System.out.println("finish crawling this keyword!!");
		return result;
	}

	private Set<String> crawlByUrl(String request) {

		Set<String> result = new HashSet<String>();

		System.out.println("Sending request..." + request);

		try {

			// need http protocol, set this as a Google bot agent
			Document doc = Jsoup.connect(request).userAgent(USER_AGENT).timeout(10000).get();

			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {

				// get attribute href and convert to specific format
				String urlString = getDomainName(link.attr("href"));
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
		System.out.println("finish crawling this url!!");
		return result;
	}

	// if task is not resource, call crawl function
	public Set<String> crawl(Task task) {
		if (task.getLevel() == 0) {
			System.out.println("crawling by keyword...");
			return crawlByKeyword(task.getLink());
		} else {
			System.out.println("crawling by url...");
			return crawlByUrl(task.getLink());
		}
	}

	public Set<String> getNotOkUrlFormatSet() {
		return notOkUrlFormatSet;
	}

	public int getMaxPageNum() {
		return maxPageNum;
	}

	public static void main(String[] args) throws IOException {
		Set<String> notOkFormat=new HashSet<String>();
		notOkFormat.add("application/pdf");
		
		Crawler c=new Crawler(notOkFormat,3);
		
		Set<String> crawlResult=c.crawlByKeyword("wms ");
		SOS sos=new SOS();
		WMS wms=new WMS();
//		for(String url:crawlResult) {
//			System.out.println("url: "+url);
//			if(wms.isGeoResource(url)) {
//				System.out.println("geo resource: "+url);
//			}
//		}
	}

}
