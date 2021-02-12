package net.ideabreed.ibparking.presenter; /*
 * Created by Rajan Karki on 2/7/21
 * Copyright @2021
 */

public class LoginActivityPresenter implements LoginService.Presenter {

    LoginService.View view;

    public LoginActivityPresenter(LoginService.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String email, String password) {

//        test: test@gmail.com test123
        if(email.equals("test@gmail.com") && password.equals("test123")){
//            view.onSuccess("Login Success");
            view.onNavigate();
        }else{
            view.onError("Wrong email or password. Please try again");
        }
//        boolean insertSuccess = dbHelper.addUser(new User(email,password,true));
//        if(insertSuccess){
//            view.onSuccess("Login Success");
//        }else {
//            view.onSuccess("login failed");
//        }
    }
}