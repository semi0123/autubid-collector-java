import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import kr.co.emforce.wonderbox.module.IProcess;

public class TestFileUtils {

	@Test
	public void test() throws IOException {

		// System.out.println("RANK_HISTORY_DIR : "+IProcess.RANK_HISTORY_DIR);

		String file_path = "D:/data1/histories/rank_histories" + "/mobile/2017-08-17";

		File Dir = new File(file_path);

		FileUtils.forceMkdir(Dir);

		String file_name = "111301114.txt";

		File f = new File(file_path + "/" + file_name);

		if (!f.exists()) {
			try {
				f.createNewFile();

				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
				pw.print("something written in file");
				pw.flush();

				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			PrintWriter pww = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
			pww.write("\r\n");
			pww.write("by printwriter");
			pww.flush();

			pww.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
