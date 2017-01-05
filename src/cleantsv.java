import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/*
 * This file is to clean tsv file
 */

public class cleantsv {
	public static void main(String[] args) throws IOException {

		String inputLine = null;
		boolean firstLine = true;
		PrintWriter writer = new PrintWriter("tsv/r2r59-8-cleaned.tsv", "UTF-8");
		BufferedReader in = new BufferedReader(new FileReader("tsv/r2r59-8.tsv"));
		while ((inputLine = in.readLine()) != null) {
			if (firstLine) {
				// do nothing
				writer.println(inputLine);
				firstLine = false;
			} else {
				String[] inps = inputLine.split("	");
				String result = "";

				inps[24] = ((inps[24].equals("มี") ? "yes" : "no"));
				inps[26] = ((inps[26].equals("เกี่ยวกับโรค") ? "yes" : "no"));
				inps[30] = ((inps[30].equals("เป็น") ? "yes" : "no"));
				inps[32] = ((inps[32].equals("มี") ? "yes" : "no"));
				inps[36] = ((inps[36].equals("มี") ? "yes" : "no"));
				inps[36] = ((inps[36].equals("มี") ? "yes" : "no"));
				inps[43] = ((inps[43].equals("เคย") ? "yes" : "no"));
				inps[45] = ((inps[45].equals("เคย") ? "yes" : "no"));
				inps[49] = ((inps[49].equals("เคย") ? "yes" : "no"));
				inps[72] = ((inps[72].equals("มีผู้ส่งแทน") ? "yes" : "no"));

				//System.out.println(inps[40]);
				switch (inps[40]) {
				case "ระดับที่ 1 นำไปใช้ในหน่วยงานตนเอง":
					inps[40] = "1";
					break;
				case "ระดับที่ 2 นำไปใช้ในหน่วยงานตนเองและหน่วยงานอื่นๆ ในองค์กร":
					inps[40] = "2";
					break;
				case "ระดับที่ 3 นำไปใช้ได้หลายแห่งในจังหวัดเดียวกัน":
					inps[40] = "3";
					break;
				case "ระดับที่ 4 นำไปใช้ทั้งจังหวัด":
					inps[40] = "4";
					break;
				case "ระดับที่ 5 นำไปใช้หลายจังหวัด,ทั้งภูมิภาค ทั้งประเทศ":
					inps[40] = "5";
					break;

				}
				//System.out.println(inps[40]);

				//System.out.println(inps[24]);

				for (int i = 0; i < inps.length; i++) {
					result += inps[i].trim().replaceAll(" +", " ") + "	";
				}
				writer.println(result);

			}

		}

		writer.close();

	}

}
