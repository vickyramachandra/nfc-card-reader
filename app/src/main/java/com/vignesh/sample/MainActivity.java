package com.vignesh.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

import com.vignesh.nfccardreader.NfcCardManager;
import com.vignesh.nfccardreader.NfcCardReader;
import com.vignesh.nfccardreader.NfcCardResponse;

public class MainActivity extends AppCompatActivity {

  private TextView textView;
  private NfcCardManager nfcCardManager;
  private NfcCardReader nfcCardReader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.text);
    nfcCardManager = new NfcCardManager(this);
    nfcCardReader = new NfcCardReader();
  }

  @Override
  protected void onPause() {
    super.onPause();
    textView.setText(null);
    nfcCardManager.disableDispatch();
  }

  @Override
  protected void onResume() {
    super.onResume();
    nfcCardManager.enableDispatch();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    if (tag != null) {
      NfcCardResponse cardResponse = nfcCardReader.readCard(tag);
      if (cardResponse != null && cardResponse.getEmvCard() != null) {
        textView.setText(cardResponse.getEmvCard().getCardNumber() + " " +
            cardResponse.getEmvCard().getExpireDate());
      }
    }
  }
}
