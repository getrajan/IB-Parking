package net.ideabreed.ibparking.presenter; /*
 * Created by Rajan Karki on 2/7/21
 * Copyright @2021
 */

import net.ideabreed.ibparking.database.DatabaseHelper;
import net.ideabreed.ibparking.model.User;

public class LoginActivityPresenter implements LoginService.Presenter {

    LoginService.View view;
    DatabaseHelper databaseHelper;

    public LoginActivityPresenter(LoginService.View view,DatabaseHelper databaseHelper) {
        this.view = view;
        this.databaseHelper = databaseHelper;
    }


    @Override
    public void doLogin(User user) {

//        test: test@gmail.com test123
        if(user.getUsername().equals("test") && user.getPassword().equals("test123")){
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