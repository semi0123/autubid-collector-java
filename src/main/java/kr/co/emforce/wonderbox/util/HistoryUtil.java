package kr.co.emforce.wonderbox.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class HistoryUtil {

	public static void writekwdRankHistories(String dir_path, String kwd_nm, String target, String checked_at,
			String emergency_status, int search_ad_id, ArrayList<Map<String, Object>> rnk_list) throws IOException {

		String file_path = dir_path + "/" + target + "/" + checked_at.substring(0, 10);
		File Dir = new File(file_path);
		FileUtils.forceMkdir(Dir);
		String file_name = String.valueOf(search_ad_id) + ".txt";
		File f = new File(file_path + "/" + file_name);

		if (!f.exists()) {
			try {
				f.createNewFile();

				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
				pw.print(kwd_nm + "\n");
				pw.print("checked_at\t");
				pw.print("rank\t");
				pw.print("site\t");
				pw.print("target\t");
				pw.print("title\t");
				pw.print("ad_dsc\n");

				for (Map<String, Object> json : rnk_list) {
					pw.print(checked_at + "\t");
					pw.print(json.get("rank") + "\t");
					pw.print(json.get("site") + "\t");
					pw.print(target + "\t");
					pw.print(json.get("title") + "\t");
					pw.print(json.get("ad_dsc") + "\n");
				}

				pw.flush();

				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			try {
				PrintWriter pww = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
				for (Map<String, Object> json : rnk_list) {
					pww.print(checked_at + "\t");
					pww.print(json.get("rank") + "\t");
					pww.print(json.get("site") + "\t");
					pww.print(target + "\t");
					pww.print(json.get("title") + "\t");
					pww.print(json.get("ad_dsc") + "\n");
				}
				pww.flush();

				pww.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
