package com.vignesh.library;

import com.vignesh.library.model.EmvCard;

public class CardNfcResponse {

  private EmvCard emvCard;
  private CardNfcError error;

  private CardNfcResponse(EmvCard emvCard, CardNfcError error) {
    this.emvCard = emvCard;
    this.error = error;
  }

  public static CardNfcResponse createResponse(EmvCard emvCard) {
    return new CardNfcResponse(emvCard, null);
  }

  public static CardNfcResponse createError(CardNfcError error) {
    return new CardNfcResponse(null, error);
  }

  public EmvCard getEmvCard() {
    return emvCard;
  }

  public CardNfcError getError() {
    return error;
  }
}
