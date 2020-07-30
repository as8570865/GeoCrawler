package idv.ray.geocrawler.worker.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public class Crawler {

	public static final String USER_AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";

	private int maxPageNum;

	@Autowired
	private UrlFilter urlFilter;

	public Crawler(int maxPageNum) {
		this.maxPageNum = maxPageNum;
		System.out.println("max crawled page is: " + maxPageNum);
		// System.out.println("not ok formats are: " + notOkUrlFormatSet);
	}

	private List<String> crawlByKeyword(String keyword) {

		List<String> result = new ArrayList<String>();
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
						String urlString = urlFilter.getDomainName(temp);
						if (urlString.length() < 255) {
							if (!result.contains(urlString)) {
								if (urlFilter.isCorrectFormat(urlString)) {
									System.out.println("get this url: " + urlString);
									System.out.println("/////////");
									result.add(urlString);
								}
							}
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

	private List<String> crawlByUrl(String request) {

		List<String> result = new ArrayList<String>();

		System.out.println("Sending request..." + request);

		try {

			Parser parser = Parser.htmlParser();
			parser.settings(new ParseSettings(true, true));

			// need http protocol, set this as a Google bot agent
			Document doc = Jsoup.connect(request).userAgent(USER_AGENT).timeout(10000).parser(parser).get();

			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {

				// get attribute href and convert to specific format
				String urlString = urlFilter.getDomainName(link.attr("href"));
				if (urlString.length() < 255) {
					if (!result.contains(urlString)) {
						if (urlFilter.isCorrectFormat(urlString)) {
							result.add(urlString);
							System.out.println("get this url: " + urlString);
							System.out.println("/////////");

						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finish crawling this url!!");
		return result;
	}

	public List<String> crawl(Task srcTask) {

		if (srcTask.getLevel() == 0) {
			System.out.println("crawling by keyword...");
			return crawlByKeyword(srcTask.getLink());
		} else {
			System.out.println("crawling by url...");
			return crawlByUrl(srcTask.getLink());
		}

	}

	public int getMaxPageNum() {
		return maxPageNum;
	}


}
