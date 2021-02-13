package net.ideabreed.ibparking.presenter; /*
 * Created by Rajan Karki on 2/13/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.model.Station;

public class HomePresenter implements HomeService.Presenter {

    private HomeService.View view;

    public HomePresenter(HomeService.View view) {
        this.view = view;
    }

    @Override
    public void doCheckIn(Station station) {

    }

    @Override
    public void doCheckOut() {

    }
}