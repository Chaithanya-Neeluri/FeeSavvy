package com.example.g;

public interface paymentresultlistener {
    void onPaymentSuccess(String s);

    void onPaymentError(int i, String s);
}
