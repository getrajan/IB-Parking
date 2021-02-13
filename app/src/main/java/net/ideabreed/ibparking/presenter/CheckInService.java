package net.ideabreed.ibparking.presenter;/*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.model.Ticket;

public interface CheckInService {

    public interface View{
        void onError(String message);
        void onSuccess(String message);
        void onNavigate(Ticket ticket);
    }

    public interface Presenter{
        void onCheckIn(Ticket ticket);
    }
}
