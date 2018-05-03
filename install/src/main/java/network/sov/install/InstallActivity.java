package network.sov.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.instantapps.InstantApps;

public class InstallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        System.out.println(">>>>> CREATE");
        ((TextView)findViewById(R.id.hello_label)).setText("Hello " + getIntent().getDataString() + System.currentTimeMillis());

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
            System.out.println(">>>>> BUTTON PRESSED");
            Intent intent = new Intent(getApplicationContext(), InstallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            InstantApps.showInstallPrompt(InstallActivity.this, intent, 0, null);
        }});
    }
}
