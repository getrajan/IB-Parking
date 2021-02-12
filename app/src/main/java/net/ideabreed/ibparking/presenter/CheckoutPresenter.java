package net.ideabreed.ibparking.presenter; /*
 * Created by Rajan Karki on 2/12/21
 * Copyright @2021
 */

public class CheckoutPresenter implements CheckoutService.Presenter {

    private CheckoutService.View view;

    public CheckoutPresenter(CheckoutService.View view) {
        this.view = view;
    }

    @Override
    public void onCheckout() {

    }
}