package net.ideabreed.ibparking.presenter;/*
 * Created by Rajan Karki on 2/9/21
 * Copyright @2021
 */

public interface CheckInService {

    public interface View{
        void onError(String message);
        void onMessage (String message);
    }

    public interface Presenter{
        void doCheckIn();
    }
}
