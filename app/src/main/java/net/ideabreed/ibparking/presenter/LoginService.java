package net.ideabreed.ibparking.presenter;/*
 * Created by Rajan Karki on 2/7/21
 * Copyright @2021
 */

import android.content.Context;

public interface LoginService {
    public interface View {
        void onError(String message);

        void onSuccess(String message);

        void onNavigate();
    }

    public interface Presenter {
        void doLogin(String email, String password);
    }
}
