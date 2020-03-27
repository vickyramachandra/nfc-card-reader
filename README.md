# nfc-card-reader [![](https://jitpack.io/v/vickyramachandra/nfc-card-reader.svg)](https://jitpack.io/#vickyramachandra/nfc-card-reader) [![License](http://img.shields.io/:License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

An Android Library to read info from NFC enabled cards

Inspired from [EMV-NFC-Paycard-Enrollment](https://github.com/devnied/EMV-NFC-Paycard-Enrollment) and [Credit-Card-NFC-Reader](https://github.com/pro100svitlo/Credit-Card-NFC-Reader)

### Getting started
Add this to your project's `build.gradle`
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

And include the dependency in your module's `build.gradle`
```groovy
dependencies {
  implementation 'com.github.vickyramachandra:nfc-card-reader:1.0.0'
}
```

### Usage
Make sure to handle if the device has NFC capabilities and it is enabled. In the Activity

```java
public class MainActivity extends AppCompatActivity {

  private NFCCardManager nfcCardManager;
  private NFCCardReader nfcCardReader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    nfcCardManager = new NFCCardManager(this);
    nfcCardReader = new NFCCardReader();
  }
  
  @Override
  protected void onResume() {
    super.onResume();
    nfcCardManager.enableDispatch();
  }

  @Override
  protected void onPause() {
    super.onPause();
    nfcCardManager.disableDispatch();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    // When the card is brought closer to the device, a new intent with TAG info is dispatched
    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    nfcCardReader.setTag(tag); // set the tag
    NFCCardResponse cardResponse = nfcCardReader.readCard(); // read the card data
    if (cardResponse != null && cardResponse.getEmvCard() != null) {
      // use card data such as cardNumber, expire date etc
    }
  }
```

### License
MIT License

Copyright (c) 2020 Vignesh Ramachandra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
