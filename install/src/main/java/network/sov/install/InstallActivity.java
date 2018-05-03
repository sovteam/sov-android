package network.sov.install;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.instantapps.InstantApps;

public class InstallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        System.out.println(">>>>> CREATE");

        String val = getIntent().getStringExtra("AppInstalled");
        if (val != null) {
            System.out.println(">>>>>> extra val: " + val);
        } else {
            System.out.println(">>>>>> extra val is null");
        }
        ((TextView)findViewById(R.id.hello_label)).setText("Hello " + getIntent().getDataString() + System.currentTimeMillis());

        findViewById(R.id.dummy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("network.sov.app", DummyActivity.class.getName());
                intent.putExtra("AppInstalled", "SOV");
                startActivity(intent);
            }
        });

        init();
    }

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

            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("network.sov.app", DummyActivity.class.getName());
            intent.putExtra("AppInstalled", "SOV");

            /*Bundle bundle = new Bundle();
            bundle.putString("AppInstalled", "SOV");*/

            InstantApps.showInstallPrompt(InstallActivity.this, intent, 7, "InstallActivity");
        }});
    }
}
