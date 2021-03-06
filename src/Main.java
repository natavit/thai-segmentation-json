import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {
	public static String[] columnTitle = null;
	public static String[] columns = null;
	public static List<Integer> intList = null;
	static LongLexTo tokenizer;

	public static void put(JSONObject object, int i) throws ParseException, IOException {
		// int
		if (intList.contains(i)) {
			if (columns[i].length() == 0) {
				//nothing = 0 
				object.put(columnTitle[i], 0);
			} else {
				object.put(columnTitle[i], Integer.parseInt(columns[i].replaceAll(",", "")));
			}
		} else if (i == 77) {
			// date
			object.put(columnTitle[i], getEpochTime(columns[i]));
		} else if (i == 2) {
			// exception: Type no need to segmentation
			object.put(columnTitle[i], wordSegment(columns[i]));
		} else {
			// string
			object.put(columnTitle[i], wordSegment(columns[i]));
		}
	}

	public static long getEpochTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date date = format.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long millis = cal.getTimeInMillis();
		return millis;
	}

	public static String wordSegment(String s) throws IOException {

		int begin, end;
		String result = "";
		tokenizer.wordInstance(s);
		begin = tokenizer.first();
		while (tokenizer.hasNext()) {
			end = tokenizer.next();
			result += s.substring(begin, end) + " ";
			begin = end;
		}
		return result.trim();
	}

	public static void main(String[] args) throws IOException {

		Integer[] intDataType = { 25, 41, 42, 48, 52 };
		intList = Arrays.asList(intDataType);
		tokenizer = new LongLexTo(new File("lexitron.txt"));
		
		PrintWriter writer = new PrintWriter("json/5.json", "UTF-8");

		String thisLine = null;
		boolean firstLine = true;
		int counter = 1;

		try {
			BufferedReader br = new BufferedReader(new FileReader("tsv/5.tsv"));
			while ((thisLine = br.readLine()) != null) {
				if (firstLine) {
					columnTitle = thisLine.split("	");
					firstLine = false;
				} else {
					columns = thisLine.split("	");
					//columns[0] = "59-" + columns[0];

					JSONObject object = new JSONObject();
					/*// researcher
					JSONArray list = new JSONArray();
					for (int i = 3; i <= 8; i++) {
						JSONObject obj = new JSONObject();
						put(obj, i);
						put(obj, i + 7);
						list.add(obj);
					}
					object.put("researcher", list);
*/
					String inputLine = null;
					try {
						BufferedReader in = new BufferedReader(new FileReader("test"));
						while ((inputLine = in.readLine()) != null) {
							String[] inps = inputLine.split("-");
							// o = object
							if (inps[0].equals("o")) {
								// if specify range
								if (inps.length == 4) {

									JSONObject obj = new JSONObject();
									for (int i = Integer.parseInt(inps[2].trim()); i <= Integer
											.parseInt(inps[3].trim()); i++) {
										put(obj, i);
									}
									object.put(inps[1].trim().toLowerCase(), obj);
								} else if (inps.length == 3) {
									String[] inpList = inps[2].split(",");

									JSONObject obj = new JSONObject();
									for (int i = 0; i < inpList.length; i++) {

										put(obj, Integer.parseInt(inpList[i]));
									}
									object.put(inps[1].trim().toLowerCase(), obj);
								}
								// s = single
							} else if (inps[0].equals("s")) {
								// if specify range
								if (inps.length == 3) {
									for (int i = Integer.parseInt(inps[1].trim()); i <= Integer
											.parseInt(inps[2].trim()); i++) {
										object.put(columnTitle[i], columns[i]);
										put(object, i);
									}
								} else if (inps.length == 2) {
									String[] inpList = inps[1].split(",");
									for (int i = 0; i < inpList.length; i++) {
										put(object, Integer.parseInt(inpList[i].trim()));
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					JSONObject innerObj = new JSONObject();
					innerObj.put("_id", new Integer(counter++));
					JSONObject countObj = new JSONObject();
					countObj.put("index", innerObj);
					writer.println(countObj);
					writer.println(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		writer.close();
		System.out.println("done "+counter);


	}

}
