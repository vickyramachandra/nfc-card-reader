package com.vignesh.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

import com.vignesh.nfccardreader.NFCCardManager;
import com.vignesh.nfccardreader.NFCCardReader;
import com.vignesh.nfccardreader.NFCCardResponse;

public class MainActivity extends AppCompatActivity {

  private TextView textView;
  private NFCCardManager nfcCardManager;
  private NFCCardReader nfcCardReader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.text);
    nfcCardManager = new NFCCardManager(this);
    nfcCardReader = new NFCCardReader();
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
      NFCCardResponse cardResponse = nfcCardReader.readCard(tag);
      if (cardResponse != null && cardResponse.getEmvCard() != null) {
        textView.setText(cardResponse.getEmvCard().getCardNumber() + " " +
            cardResponse.getEmvCard().getExpireDate());
      }
    }
  }
}
