import java.io.IOException;
import java.net.MalformedURLException;

public class WebSearchEngine {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("検索したい正規表現と検索対象のURLを入力してください");
			System.exit(0);
		}
		try {
			Searcher s = new Searcher(args[0], args[1]);
			s.startSearch();
			System.out.print(s.createOutputStr());
		} catch (MalformedURLException e) {
			System.out.println("不正なURLです。");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}
}