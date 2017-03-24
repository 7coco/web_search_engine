import java.io.*;
import java.util.ArrayList;

class file_search1
{
	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("検索したい文字列と検索対象テキストファイル名を空白区切で入力してください.");
			System.out.println("例）hoge test.txt");
			System.exit(1);
		}

		try {
			ArrayList<Integer> numOfLines = search(args[0], args[1]);
			if(numOfLines.size() > 0){
				System.out.println("入力された文字列が現れた行番号は以下の通りです。");
				numOfLines.forEach(num -> {
					System.out.println(num);
				});
			}else{
				System.out.println("該当する行はありませんでした。");
			}
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
		}
	}

	public static ArrayList<Integer> search(String query, String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		ArrayList<Integer> results = new ArrayList<Integer>();
		String str;
		int lineNumber = 1;
		while((str = br.readLine()) != null){
			if(str == query) results.add(lineNumber);
		}
		
		return results;
  	}
}
