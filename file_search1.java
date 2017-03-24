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

		// 検索を行う。
		// 戻り値は列挙するべき数字たち
		System.out.println(/* 検索結果 */);
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
