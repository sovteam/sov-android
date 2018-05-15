package network.sov.install;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.instantapps.InstantApps;

import java.util.Locale;

// import com.google.android.gms.instantapps.PackageManagerCompat;

public class InstallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        System.out.println(">>>>> CREATE");
        ((TextView)findViewById(R.id.versionText)).setText(String.format(Locale.US, "Version: %d", BuildConfig.VERSION_CODE));

        /*PackageManagerCompat packMan = com.google.android.gms.instantapps.InstantApps.getPackageManagerCompat(getApplicationContext());
        if (packMan != null)
            printCookie(packMan);*/

        /*findViewById(R.id.dummy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("network.sov.app", DummyActivity.class.getName());
                intent.putExtra("AppInstalled", "SOV");
                startActivity(intent);
            }
        });*/

        init();
    }

    /*private void printCookie(PackageManagerCompat packMan) {
        byte[] cookie = packMan.getInstantAppCookie();

        ByteArrayInputStream byteIn = new ByteArrayInputStream(cookie);
        Map<String, String> map = null;
        try {
            ObjectInputStream in = new ObjectInputStream(byteIn);
            map = (Map<String, String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (map != null) {
            System.out.println(">>>>>> extra val: " + map.get("MSG"));
            ((TextView)findViewById(R.id.hello_label)).setText("Hello " + map.get("MSG") + " " + System.currentTimeMillis());
        } else {
            System.out.println(">>>>>> extra val is null");
            ((TextView)findViewById(R.id.hello_label)).setText("Hello #NoCookies " + System.currentTimeMillis());
        }
    }*/

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        System.out.println(">>>>> NEW INTENT");
        init();
    }

    private void init() {
        View installButton = findViewById(R.id.install_button);
        if (InstantApps.isInstantApp(this))
            initInstantApp(installButton);
        else
            installButton.setVisibility(View.GONE);
    }

    private void initInstantApp(View installButton) {
        System.out.println(">>>>> INIT");

        installButton.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
            System.out.println(">>>>> BUTTON PRESSED: " + getIntent().getDataString());
            // Intent intent = new Intent(InstallActivity.this, DummyActivity.class);

            /*PackageManagerCompat packMan = com.google.android.gms.instantapps.InstantApps.getPackageManagerCompat(getApplicationContext());
            if (packMan != null) {
                Map<String, String> data = new HashMap<>();
                data.put("MSG", "hello");

                try {
                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(byteOut);
                    out.writeObject(data);
                    out.close();
                    packMan.setInstantAppCookie(byteOut.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sov.network/link/dummy"));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.putExtra("AppInstalled", "SOV by Instant App");
            InstantApps.showInstallPrompt(InstallActivity.this, intent,0, "Xispas");
        }});
    }
}
