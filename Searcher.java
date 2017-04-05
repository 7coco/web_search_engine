import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {
	private Pattern regexp;
	private URL url;
	private Set<URL> results;
	static private final Pattern LINK_REGEXP = Pattern
			.compile("<a href=\"([^\"]*html?)\"|<frame[^<]src=\"([^\"]*\\.html?)");
	static private final Pattern TARGET_URL_REGEXP = Pattern.compile("https?");
	static private final Pattern EXCEPT_TAGS = Pattern.compile("<[^>]*>");
	private Set<URL> referenceadUrls;

	public Searcher(String regexp, String url) throws MalformedURLException {
		this.regexp = Pattern.compile(regexp);
		this.url = new URL(url);
		this.results = new HashSet<URL>();
		this.referenceadUrls = new HashSet<URL>();
	}

	public void startSearch() throws IOException {
		search(url);
	}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				try {
					searchUrls(currentLine);
					if (isMatched(currentLine)) {
						results.add(url);
					}
				} catch (URISyntaxException e) {
					continue;
				}
			}
		}
	}

	private void searchUrls(String currentLine) throws IOException, URISyntaxException {
		Set<URL> searchedUrls = new HashSet<URL>();
		Matcher m = LINK_REGEXP.matcher(currentLine);
		if (m.find()) {
			String link = (m.group(1) != null) ? m.group(1) : m.group(2);
			URL searchedUrl = url.toURI().resolve(link).toURL();
			if (searchedUrl.toString().startsWith(url.toString()) && TARGET_URL_REGEXP.matcher(url.toString()).find()) {
				searchedUrls.add(searchedUrl);
			}
		}
		searchedUrls = deleteDuplicates(searchedUrls);
		referenceadUrls.addAll(searchedUrls);
		forSearch(searchedUrls);
	}

	private void forSearch(Set<URL> urls) {
		for (URL url : urls) {
			try {
				search(url);
			} catch (IOException e) {
				continue;
			}
		}
	}

	private boolean isMatched(String currentLine) throws IOException {
		String str = EXCEPT_TAGS.matcher(currentLine).replaceAll("");
		Matcher m = regexp.matcher(str);
		if (m.find()) {
			System.out.println(str);
			return true;
		} else {
			return false;
		}
	}

	private Set<URL> deleteDuplicates(Set<URL> urls) {
		urls.removeAll(referenceadUrls);
		return urls;
	}

	public String createOutputStr() {
		StringBuilder outputStr = new StringBuilder();
		for (URL url : results) {
			outputStr.append(url).append("\n");
		}
		return outputStr.toString();
	}
}
