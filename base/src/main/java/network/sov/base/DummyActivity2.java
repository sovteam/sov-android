package network.sov.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import network.sov.R;

public class DummyActivity2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy2);

        ((TextView) findViewById(R.id.dummyTextView)).setText(getIntent().getStringExtra("AppInstalled"));
    }
}