package network.sov.install.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

public class JsonZip {

    public static byte[] compress(JSONObject json) {
        String jsonString = json.toString();
        byte[] zip;
        try {
            zip = Gzip.compress(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return zip;
    }

    public static JSONObject decompress(String zipString) {
        byte[] data = zipString.getBytes(Charset.forName("UTF-8"));
        String jsonString;
        try {
            jsonString = Gzip.decompress(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        JSONObject ret;
        try {
            ret = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }
}
