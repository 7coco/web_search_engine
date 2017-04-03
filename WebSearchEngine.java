import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebSearchEngine {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("検索したい正規表現と検索対象のURLを入力してください");
			System.exit(0);
		}
	}
}