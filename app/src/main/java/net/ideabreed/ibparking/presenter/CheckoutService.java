package net.ideabreed.ibparking.presenter;/*
 * Created by Rajan Karki on 2/12/21
 * Copyright @2021
 */

public interface CheckoutService {

    public interface View{
        void onError(String message);
        void onSuccess(String message);
        void onNavigate();
    }

    public interface Presenter{
        void onCheckout();
    }
}
