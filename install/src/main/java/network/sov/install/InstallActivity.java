package network.sov.install;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.instantapps.InstantApps;

import java.util.Locale;

import network.sov.BuildConfig;

public class InstallActivity extends AppCompatActivity {

    private View sovBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        ((TextView)findViewById(R.id.versionText)).setText(String.format(Locale.US, "Version: %d", BuildConfig.VERSION_CODE));

        sovBox = findViewById(R.id.sovBox);
        ((TextView) sovBox.findViewById(R.id.appName)).setText("SOV APP");
        ((TextView) sovBox.findViewById(R.id.appShortDesc)).setText("Sovereign platform for P2P applications");
        ((ImageView) sovBox.findViewById(R.id.appIcon)).setImageResource(R.mipmap.crown);

        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        System.out.println(">>>>> NEW INTENT");
        init();
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
}
