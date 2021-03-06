import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

		String[] fileNames = Arrays.copyOfRange(args, 1, args.length);
		try {
			Map<String, Map<Integer, List<Integer>>> result = searchInAllFiles(args[0], fileNames);
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
	 * 第二引数のリストの中の全てのファイルから検索を行います。
	 * 
	 * @param query
	 *            検索文字列
	 * @param fileNames
	 *            検索対象ファイル名のリスト
	 * @return 検索結果のマップ。 キー：ファイル名、値：行番号と文字の位置のマップ
	 * @throws IOException
	 */
	private static Map<String, Map<Integer, List<Integer>>> searchInAllFiles(String query, String[] fileNames)
			throws IOException {
		Map<String, Map<Integer, List<Integer>>> result = new LinkedHashMap<String, Map<Integer, List<Integer>>>();
		for (String fileName : fileNames) {
			Map<Integer, List<Integer>> resultInFile = searchInFile(query, fileName);
			result.put(fileName, resultInFile);
		}
		return result;
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
	private static Map<Integer, List<Integer>> searchInFile(String query, String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			Map<Integer, List<Integer>> result = new LinkedHashMap<Integer, List<Integer>>();
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
				result.put(lineNumber, charNumbers);
				lineNumber++;
			}
			return result;
		}
	}

	/**
	 * 検索結果のマップから最終的に出力する文字列を作ってそれを返します。
	 * 
	 * @param searchResultMap
	 *            検索結果のマップ
	 * @return 最終的に出力するべき文字列
	 */
	private static String createOutputStr(Map<String, Map<Integer, List<Integer>>> searchResultMap) {
		StringBuilder output = new StringBuilder();
		for (Map.Entry<String, Map<Integer, List<Integer>>> entry : searchResultMap.entrySet()) {
			String fileName = entry.getKey();
			Map<Integer, List<Integer>> searchedByFileMap = entry.getValue();
			output.append(fileName).append('の');
			String searchedByFileStr = createStrBy(searchedByFileMap);
			output.append(searchedByFileStr);
		}
		return output.toString();
	}

	/**
	 * 引数のマップから出力に使用する文字列を作ってそれを返します。
	 * 
	 * @param lineAndCharNumberMap
	 *            行番号と文字数のマップ
	 * @return 出力に使用する文字列
	 */
	private static String createStrBy(Map<Integer, List<Integer>> lineAndCharNumberMap) {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<Integer, List<Integer>> entry : lineAndCharNumberMap.entrySet()) {
			int line = entry.getKey();
			List<Integer> chars = entry.getValue();
			str.append(line).append("行目 ");
			for (Integer c : chars) {
				str.append(c).append("文字目 ");
			}
			str.append("\n");
		}
		return str.toString();
	}
}
