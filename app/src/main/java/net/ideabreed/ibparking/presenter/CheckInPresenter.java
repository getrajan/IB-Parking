package net.ideabreed.ibparking.presenter; /*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.model.Ticket;

public class CheckInPresenter implements CheckInService.Presenter {
    CheckInService.View view;

    public CheckInPresenter(CheckInService.View view) {
        this.view = view;
    }

    @Override
    public void onCheckIn(Ticket ticket) {

    }
}