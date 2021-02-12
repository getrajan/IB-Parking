package net.ideabreed.ibparking.presenter; /*
 * Created by Rajan Karki on 2/8/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.model.Ticket;

public class StationCheckInPresenter implements StationCheckInService.Presenter {
    StationCheckInService.View view;

    public StationCheckInPresenter(StationCheckInService.View view) {
        this.view = view;
    }

    @Override
    public void onNext(Ticket ticket) {

    }
}