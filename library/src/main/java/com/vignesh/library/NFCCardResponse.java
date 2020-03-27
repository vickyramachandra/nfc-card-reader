package com.vignesh.library;

import com.vignesh.library.model.EmvCard;

public class NFCCardResponse {

  private EmvCard emvCard;
  private NFCCardError error;

  private NFCCardResponse(EmvCard emvCard, NFCCardError error) {
    this.emvCard = emvCard;
    this.error = error;
  }

  public static NFCCardResponse createResponse(EmvCard emvCard) {
    return new NFCCardResponse(emvCard, null);
  }

  public static NFCCardResponse createError(NFCCardError error) {
    return new NFCCardResponse(null, error);
  }

  public EmvCard getEmvCard() {
    return emvCard;
  }

  public NFCCardError getError() {
    return error;
  }
}
