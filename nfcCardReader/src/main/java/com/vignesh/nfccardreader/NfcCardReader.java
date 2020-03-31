package com.vignesh.nfccardreader;

import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vignesh.nfccardreader.model.EmvCard;
import com.vignesh.nfccardreader.parser.EmvParser;
import com.vignesh.nfccardreader.utils.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class NfcCardReader {

    private final static String NFC_A_TAG = "TAG: Tech [android.nfc.tech.IsoDep, android.nfc.tech.NfcA]";
    private final static String NFC_B_TAG = "TAG: Tech [android.nfc.tech.IsoDep, android.nfc.tech.NfcB]";

    private boolean isException;


    @Nullable
    public NfcCardResponse readCard(Tag tag) {
        if (tag != null) {
            NfcCardResponse nfcCardResponse = null;
            if (tag.toString().equals(NFC_A_TAG) || tag.toString().equals(NFC_B_TAG)) {
                try {
                    nfcCardResponse = getCardInfo(tag);
                } catch (Exception e) {
                    Log.e(NfcCardReader.class.getName(), e.getMessage(), e);
                }

                if (!isException) {
                    if (nfcCardResponse != null && nfcCardResponse.getEmvCard() != null) {
                        EmvCard emvCard = nfcCardResponse.getEmvCard();
                        if (StringUtils.isNotBlank(emvCard.getCardNumber())) {
                            return NfcCardResponse.createResponse(emvCard);
                        } else if (emvCard.isNfcLocked()) {
                            return NfcCardResponse.createError(NfcCardError.CARD_LOCKED_WITH_NFC);
                        }
                    } else {
                        return NfcCardResponse.createError(NfcCardError.UNKNOWN_EMV_CARD);
                    }
                } else {
                    return NfcCardResponse.createError(NfcCardError.DONOT_MOVE_CARD_SO_FAST);
                }
            } else {
                return NfcCardResponse.createError(NfcCardError.UNKNOWN_EMV_CARD);
            }
        }
        return null;
    }

    private NfcCardResponse getCardInfo(Tag tag){
        IsoDep isoDep = IsoDep.get(tag);
        Provider provider = new Provider();
        if (isoDep == null) {
            return NfcCardResponse.createError(NfcCardError.DONOT_MOVE_CARD_SO_FAST);
        }

        isException = false;

        try {
            // Open connection
            isoDep.connect();

            provider.setTagCom(isoDep);

            EmvParser parser = new EmvParser(provider, true);
            EmvCard card = parser.readEmvCard();
            if (card != null) {
                return NfcCardResponse.createResponse(card);
            }
        } catch (IOException e) {
            isException = true;
            Log.e(NfcCardReader.class.getName(), e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(isoDep);
        }
        return NfcCardResponse.createError(NfcCardError.UNKNOWN_EMV_CARD);
    }
}