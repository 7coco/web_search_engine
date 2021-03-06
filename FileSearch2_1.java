import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileSearch2_1 {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("検索したい文字列と検索対象テキストファイル名を空白区切で入力してください.");
			System.out.println("例）hoge test.txt");
			System.exit(1);
		}
		String[] fileNames = Arrays.copyOfRange(args, 1, args.length);
		for (String fileName : fileNames) {
			try {
				FileSearcher fs = new FileSearcher(fileName);
				Map<Integer, List<Integer>> result = fs.search(args[0]);
				System.out.println("ファイル名：" + fileName);
				if (result.isEmpty()) {
					System.out.println("該当する行はありませんでした。");
				} else {
					System.out.println("入力された文字列が現れた行番号は以下の通りです。");
					String answer = fs.createOutputStr(result);
					System.out.print(answer);
				}
			} catch (IOException e) {
				System.out.println("入出力エラーです。");
				e.printStackTrace();
			}
		}
	}

}
