package net.ideabreed.ibparking.presenter;/*
 * Created by Rajan Karki on 2/13/21
 * Copyright @2021
 */


import net.ideabreed.ibparking.model.Station;

public interface HomeService {

    public interface View{
        void onError(String message);
        void onSuccess(String message);
        void onNavigate(Station station);
    }

    public interface Presenter{
        void doCheckIn(Station station);
        void doCheckOut();
    }
}
