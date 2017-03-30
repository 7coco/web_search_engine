import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkSearcher2 {
	private URI uri;
	// ListではなくSetを使い、StringではなくURLを保持するようにしよう。
	private Set<URL> referencedLinks;
	static private final Pattern LINK_REGEXP = Pattern.compile("<a href=\"([^\"]*)\"|src=\"([^\"]*)");

	public LinkSearcher2(String uri) throws MalformedURLException, URISyntaxException {
		this.referencedLinks = new HashSet<URL>();
		this.uri = new URI(uri);
	}

	/*
	 * public List<String> search() throws IOException { try (BufferedReader br
	 * = new BufferedReader(new InputStreamReader(uri.openStream()))) {
	 * List<String> result = new ArrayList<String>(); String str; while ((str =
	 * br.readLine()) != null) { Matcher m = LINK_REGEXP.matcher(str); if
	 * (m.find()) { String link = (m.group(1) != null) ? m.group(1) :
	 * m.group(2); result.add(link); } } return
	 * searchAllReferenceableLinks(result); } }
	 */
	public List<String> searchAllReferenceableLinks(List<String> links) throws IOException {
		links.removeAll(refferencedLinks);
		if (links.isEmpty()) {
			return links;
		} else {
			List<String> result = new ArrayList<String>();
			for (String link : links) {
				// URI の resolve method を使ってちゃんとURL を作る。
				URL url = this.uri.resolve(link).toURL();
				refferencedLinks.add(url);
				try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
					System.out.println(url);
					String str;
					while ((str = br.readLine()) != null) {
						Matcher m = LINK_REGEXP.matcher(str);
						if (m.find()) {
							String l = (m.group(1) != null) ? m.group(1) : m.group(2);
							result.add(l);
						}
					}
				} catch (IOException e) {
					continue;
				}
			}
			result.addAll(searchAllReferenceableLinks(result));
			return result;
		}
	}

	public String createOutputStr(List<String> searchedLinks) {
		StringBuilder outputStr = new StringBuilder();
		for (String link : searchedLinks) {
			outputStr.append(link).append("\n");
		}
		return outputStr.toString();
	}
}