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
	static private final Pattern UNREFERENCEABLE_LINK_REGEXP = Pattern.compile("^https?://|[<>{}|\\[\\]]");

	public LinkSearcher2(String uri) throws MalformedURLException, URISyntaxException {
		this.referencedLinks = new HashSet<URL>();
		this.uri = new URI(uri);
	}

	/**
	 * 指定したURLから参照することが可能なURLの一覧を再帰的に求めます。
	 * 
	 * @param links
	 *            検索したファイルの中から見つかったリンクのリスト。 main
	 *            から一番最初に呼び出すときは空文字をリストの中に入れてください。
	 * @return ファイルの中から見つかったリンクのリスト
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<String> searchAllReferenceableLinks(List<String> links) throws IOException, URISyntaxException {
		if (links.isEmpty()) {
			System.out.println("終わりでは！リンクがない！");
			return links;
		}
		int duplicateOrUnreferenceableCount = 0;
		System.out.println("今回のループはこんな感じ。" + links);
		List<String> result = new ArrayList<String>();
		for (String link : links) {
			if (UNREFERENCEABLE_LINK_REGEXP.matcher(link).find()) {
				duplicateOrUnreferenceableCount++;
				System.out.println("これダメだー。" + link);
				continue;
			} else if (referencedLinks.contains(this.uri.resolve(link).toURL())) {
				duplicateOrUnreferenceableCount++;
				System.out.println("かぶった！" + duplicateOrUnreferenceableCount);
				continue; // 重複を弾く。
			} else if (duplicateOrUnreferenceableCount == links.size()) {
				System.out.println("終わりでは！全部ダメだった！");
				return links;
			} else {
				URL url = this.uri.resolve(link).toURL();
				if (!url.toURI().resolve(link).toString().startsWith(url.toString())) {
					continue;
				}
				try {
					result = searchIn(url);
				} catch (IOException e) {
					continue;
				}
			}
		}
		result.addAll(searchAllReferenceableLinks(result));
		return result;
	}

	private List<String> searchIn(URL url) throws MalformedURLException, IOException {
		List<String> result = new ArrayList<String>();
		referencedLinks.add(url);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			System.out.println(url);
			String str;
			while ((str = br.readLine()) != null) {
				System.out.println("ファイル内検索中");
				Matcher m = LINK_REGEXP.matcher(str);
				if (m.find()) {
					String l = (m.group(1) != null) ? m.group(1) : m.group(2);
					result.add(l);
				}
			}
			return result;
		}
	}

	public List<URL> makeLinksToURL(List<String> links) throws MalformedURLException {
		List<URL> urls = new ArrayList<URL>();
		for (String link : links) {
			urls.add(this.uri.resolve(link).toURL());
		}
		return urls;
	}

	/**
	 * 引数の検索結果リストから最終的に出力するべき文字列を作ってそれを返します。
	 * 
	 * @param 検索結果のリスト
	 * @return 出力するべき文字列
	 */
	public String createOutputStr(List<URL> searchedUrls) {
		StringBuilder outputStr = new StringBuilder();
		for (URL url : searchedUrls) {
			outputStr.append(url).append("\n");
		}
		System.out.println("終わり！");
		return outputStr.toString();
	}
}
