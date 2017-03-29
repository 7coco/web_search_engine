import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebCrawler2 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("URLを指定してください");
			System.exit(1);
		}
		try {
			LinkSearcher2 ls = new LinkSearcher2(args[0]);
			List<String> links = ls.search();
			System.out.print(ls.createOutputStr(links));
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}
}
