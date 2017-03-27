import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileSearch2 {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("検索したい文字列と検索対象テキストファイル名を空白区切で入力してください.");
			System.out.println("例）hoge test.txt test2.txt");
			System.exit(1);
		}

		List<String> fileNames = createFileNamesList(args);
		try {
			Map<String, Map<Integer, List<Integer>>> result = search(args[0], fileNames);
			if (result.isEmpty()) {
				System.out.println("該当する行はありませんでした。");
			} else {
				System.out.println("入力された文字列が現れた行番号は以下の通りです。");
				String answer = createOutputStr(result);
				System.out.print(answer);
			}
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}

	/**
	 * コマンドライン引数を引数で渡すと第二引数以降のリストを返します。
	 * 
	 * @param commandLineArgs
	 * @return fileNames
	 */
	private static List<String> createFileNamesList(String[] commandLineArgs) {
		List<String> fileNames = new ArrayList<String>();
		for (int i = 0; i < commandLineArgs.length; i++) {
			if (i == 0) {
				continue;
			}
			fileNames.add(commandLineArgs[i]);
		}
		return fileNames;
	}

	/**
	 * 指定されたファイルから指定された文字列の位置を検索します。
	 * 
	 * @param query
	 *            検索文字列
	 * @param fileName
	 *            検索対象のファイル名
	 * @return 検索結果。キー：行数、値：文字の位置リスト
	 * @throws IOException
	 */
	private static Map<String, Map<Integer, List<Integer>>> search(String query, List<String> fileNames)
			throws IOException {
		Map<String, Map<Integer, List<Integer>>> result = new LinkedHashMap<String, Map<Integer, List<Integer>>>();
		for (String fileName : fileNames) {
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				Map<Integer, List<Integer>> lineAndCharNumbers = new LinkedHashMap<Integer, List<Integer>>();
				String str;
				int lineNumber = 1;
				while ((str = br.readLine()) != null) {
					if (str.indexOf(query) == -1) {
						lineNumber++;
						continue;
					}
					List<Integer> charNumbers = new ArrayList<Integer>();
					int charNumber = 0;
					while ((charNumber = str.indexOf(query, charNumber)) != -1) {
						charNumber++;
						charNumbers.add(charNumber);
					}
					lineAndCharNumbers.put(lineNumber, charNumbers);
					lineNumber++;
				}
				if (!lineAndCharNumbers.isEmpty()) { // そのファイルの検索結果があれば
					result.put(fileName, lineAndCharNumbers);
				}
			}
		}
		return result;
	}

	/**
	 * 引数のマップから最終的に出力する文字列を作ってそれを返します。
	 * 
	 * @param resultMap
	 *            検索結果のマップ
	 * @return 出力するべき文字列
	 */
	private static String createOutputStr(Map<String, Map<Integer, List<Integer>>> resultMap) {
		StringBuilder answer = new StringBuilder();
		for (Map.Entry<String, Map<Integer, List<Integer>>> entry : resultMap.entrySet()) {
			String fileName = entry.getKey();
			answer.append(fileName).append('の');
			Map<Integer, List<Integer>> lineAndCharNumbers = entry.getValue();
			for (Map.Entry<Integer, List<Integer>> etr : lineAndCharNumbers.entrySet()) {
				int line = etr.getKey();
				List<Integer> chars = etr.getValue();
				answer.append(line).append("行目 ");
				for (Integer c : chars) {
					answer.append(c).append("文字目 ");
				}
				answer.append("\n");
			}
		}
		return answer.toString();
	}
}
