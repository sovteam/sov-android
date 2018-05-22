package network.sov.install.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.instantapps.InstantApps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import network.sov.BuildConfig;
import network.sov.install.R;
import network.sov.install.util.Token;

public class InstallActivity extends AppCompatActivity {

    private View sovBox;
    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        ((TextView)findViewById(R.id.versionText)).setText(String.format(Locale.US, "Version: %d", BuildConfig.VERSION_CODE));

        sovBox = findViewById(R.id.sovBox);
        ((TextView) sovBox.findViewById(R.id.appName)).setText("SOV APP");
        ((TextView) sovBox.findViewById(R.id.appShortDesc)).setText("Sovereign platform for P2P applications");
        ((ImageView) sovBox.findViewById(R.id.appIcon)).setImageResource(R.mipmap.crown);

        /*JSONObject json = new JSONObject();
        try {
            json.put("id", "br.com.grubster.android");
            json.put("name", "Buddy Box");
            json.put("shortDesc", "Share your songs with your friends");
            json.put("icon", "https://lh3.googleusercontent.com/gzHEU72P4PIbYlshAnvQ8F3TMcR-OfpzeDCUQzwQ95zlipic7PTpsSTextjSt6XYuA=s180-rw");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(">>> encode 64: " + Token.encode(json));*/

        Uri d = getIntent().getData();
        if (d != null) {
            List<String> segments = d.getPathSegments();
            String token = segments.get(segments.size() -1);
            json = Token.decode(token);
            if (json != null)
                updateAppBox(json);
        }

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateSovInstallAction();
        // updateAppInstallAction();
    }

    private void updateSovInstallAction() {
        Button installButton = sovBox.findViewById(R.id.installButton);
        TextView installedLabel = sovBox.findViewById(R.id.installedLabel);
        if (InstantApps.isInstantApp(this)) {
            installButton.setVisibility(View.VISIBLE);
            installedLabel.setVisibility(View.GONE);
        } else {
            installButton.setVisibility(View.GONE);
            installedLabel.setVisibility(View.VISIBLE);
        }
    }

    private void updateAppBox(JSONObject json) {
        final View appBox = findViewById(R.id.appBox);
        try {
            ((TextView) appBox.findViewById(R.id.appName)).setText(json.getString("name"));
            ((TextView) appBox.findViewById(R.id.appShortDesc)).setText(json.getString("shortDesc"));
            final String appId = json.getString("id");
            appBox.findViewById(R.id.installButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId));
                    startActivity(intent);
                }
            });
            final String iconUrl = json.getString("icon");
            updateAppIcon(appBox, iconUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateAppIcon(final View appBox, final String iconUrl) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        // get bitmap from another thread
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new Runnable() { @Override public void run() {
            final Bitmap bitmap = getBitmapFromURL(iconUrl);
            // set bitmap from main thread
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    ((ImageView) appBox.findViewById(R.id.appIcon)).setImageBitmap(bitmap);
                }
            };
            mainHandler.post(myRunnable);
        }});
    }

    public static Bitmap getBitmapFromURL(String src) {
        Bitmap ret;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            ret = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    private void init() {
        View installButton = sovBox.findViewById(R.id.installButton);
        if (InstantApps.isInstantApp(this))
            initInstantApp(installButton);
        else
            installButton.setVisibility(View.GONE);
    }

    private void initInstantApp(View installButton) {
        installButton.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sov.network/link/dummy"));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.putExtra("AppInstalled", "SOV by Instant App");
            InstantApps.showInstallPrompt(InstallActivity.this, intent,0, "Xispas");
        }});
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
