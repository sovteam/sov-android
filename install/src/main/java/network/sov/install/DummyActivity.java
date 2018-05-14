package network.sov.install;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DummyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        ((TextView)findViewById(R.id.dummyTextView)).setText(getIntent().getStringExtra("AppInstalled"));
    }
}
