package network.sov.install.util;

import android.net.Uri;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Token {

    public static String encode(JSONObject json) {
        /*byte[] zip = JsonZip.compress(json);
        if (zip == null)
            return null;

        String zipString;
        try {
            zipString = new String(zip, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }*/

        byte[] data = json.toString().getBytes(StandardCharsets.UTF_8);
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static JSONObject decode(String token) {
        byte[] data = Base64.decode(token, Base64.DEFAULT);
        String text = new String(data, StandardCharsets.UTF_8);

        JSONObject ret;
        try {
            ret = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return ret;
        // return JsonZip.decompress(zipString);
    }

}
