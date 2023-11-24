package common.java.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JSONResult {

    public static JSONObject getJSON(String s) throws IOException {

        //String example:  "https://api.geoapify.com/v1/geocode/search?text="+ URLEncoder.encode(city, StandardCharsets.UTF_8)+"&format=json&apiKey=7bfb9cd960b3496d9ce019358cef6c08";

        URL url = new URL(s);

        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        StringBuilder str = new StringBuilder();
        while (scan.hasNext())
            str.append(scan.nextLine());
        scan.close();

        // build a JSON object
        System.out.println(str);
        return new JSONObject(str.toString());
    }
}
