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
	static private final Pattern LINK_REGEXP = Pattern
			.compile("<a href=\"([^\"]*html?)\"|<frame[^<]src=\"([^\"]*\\.html?)");
	static private final Pattern TARGET_URL_REGEXP = Pattern.compile("https?");
	private Set<URL> referenceadUrls;

	public Searcher(String regexp, String url) throws MalformedURLException {
		this.regexp = Pattern.compile(regexp);
		this.url = new URL(url);
		this.referenceadUrls = new HashSet<URL>();
	}

	public Set<URL> search(URL url) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
					}
				}
			}
	private Set<URL> searchUrls(String currentLine) throws IOException, URISyntaxException {
		Set<URL> searchedUrls = new HashSet<URL>();
		Matcher m = LINK_REGEXP.matcher(currentLine);
		if (m.find()) {
			String link = (m.group(1) != null) ? m.group(1) : m.group(2);
			URL searchedUrl = this.url.toURI().resolve(link).toURL();
			if (searchedUrl.toString().startsWith(this.url.toString())
					&& TARGET_URL_REGEXP.matcher(this.url.toString()).find()) {
				searchedUrls.add(searchedUrl);
			}
		}
		searchedUrls = deleteDuplicates(searchedUrls);
		referenceadUrls.addAll(searchedUrls);
		searchedUrls = forSearch(searchedUrls);
		return searchedUrls;
	}

	private boolean isMatched(String currentLine) throws IOException {
		Matcher m = regexp.matcher(currentLine);
		if (m.find()) {
			System.out.println(currentLine);
			return true;
		} else {
			return false;
		}
	}

	private Set<URL> deleteDuplicates(Set<URL> urls) {
		urls.removeAll(referenceadUrls);
		return urls;
	}

	private Set<URL> forSearch(Set<URL> urls) {
		Set<URL> resultUrls = new HashSet<>();
		for (URL url : urls) {
			System.out.println(url);
			try {
				resultUrls.addAll(search(url));
			} catch (IOException e) {
				continue;
			}
		}
		return resultUrls;
	}

	public String createOutputStr(Set<URL> searchedUrls) {
		StringBuilder outputStr = new StringBuilder();
		for (URL url : searchedUrls) {
			outputStr.append(url).append("\n");
		}
		return outputStr.toString();
	}
}
