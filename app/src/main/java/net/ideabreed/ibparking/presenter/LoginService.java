package net.ideabreed.ibparking.presenter;/*
 * Created by Rajan Karki on 2/7/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.model.User;

public interface LoginService {
    public interface View {
        void onError(String message);

        void onSuccess(String message);

        void onNavigate();
    }

    public interface Presenter {
        void doLogin(User user);
    }
}
