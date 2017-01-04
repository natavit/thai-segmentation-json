import java.io.BufferedReader;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) {

		String thisLine = null;
		boolean firstLine = true;
		String[] columnTitle = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("r2r59-9.tsv"));
			while ((thisLine = br.readLine()) != null) {
				if (firstLine) {
					columnTitle = thisLine.split("	");
					firstLine = false;
				} else {
					String[] columns = thisLine.split("	");
					
					JSONObject object = new JSONObject();
					// researcher
					JSONArray list = new JSONArray();
					for (int i = 3; i <= 8; i++) {
						JSONObject obj = new JSONObject();
						obj.put(columnTitle[i], columns[i]);
						obj.put(columnTitle[i + 7], columns[i]);
						list.add(obj);
					}
					object.put("researcher", list);

					String inputLine = null;
					try {
						BufferedReader in = new BufferedReader(new FileReader("input"));
						while ((inputLine = in.readLine()) != null) {
							String[] inps = inputLine.split("-");
							// o = object
							if (inps[0].equals("o")) {
								// if specify range
								if (inps.length == 4) {

									JSONObject obj = new JSONObject();
									for (int i = Integer.parseInt(inps[2].trim()); i <= Integer
											.parseInt(inps[3].trim()); i++) {
										obj.put(columnTitle[i], columns[i]);
									}
									object.put(inps[1].trim().toLowerCase(), obj);
								} else if (inps.length == 3) {
									String[] inpList = inps[2].split(",");

									JSONObject obj = new JSONObject();
									for (int i = 0; i < inpList.length; i++) {

										obj.put(columnTitle[Integer.parseInt(inpList[i])],
												columns[Integer.parseInt(inpList[i])]);
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
									}
								} else if (inps.length == 2) {
									String[] inpList = inps[1].split(",");

									for (int i = 0; i < inpList.length; i++) {
										object.put(columnTitle[Integer.parseInt(inpList[i].trim())],
												columns[Integer.parseInt(inpList[i].trim())]);
									}
								}
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.print(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
