package com.mad.contacts;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;


/**
* a. Assignment : #07.
* b. File Name : Util (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class Util {

    public static HttpUrl getUrl(String url){
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        return builder.build();
    }

    public static HttpUrl getUrl(String url, Map<String,String> queryParameters){
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    public static FormBody createFormBody(Map<String,String> bodyParameters){
        FormBody.Builder builder = new FormBody.Builder();
        if(!bodyParameters.isEmpty()){
            for (Map.Entry<String, String> entry : bodyParameters.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }
//    public  static final String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
//    public  static final String nameRegex = "^[a-zA-Z][a-zA-Z0-9., ]+$";
//
//    public boolean isInputSanitized(){
//        if(name.getText()==null || name.getText().toString().trim().isEmpty()){
//            showAlertDialogForError(getString(R.string.name_empty_error));
//            return false;
//        } else if(email.getText()==null || email.getText().toString().trim().isEmpty()){
//            showAlertDialogForError(getString(R.string.email_empty_error));
//            return false;
//        }else if(phone.getText()==null || phone.getText().toString().trim().isEmpty()){
//            showAlertDialogForError(getString(R.string.phone_empty_error));
//            return false;
//        }else if(!isValidName(name.getText().toString().trim())){
//            showAlertDialogForError(getString(R.string.name_valid_error));
//            return false;
//        }else if(!isValidEmailAddress(email.getText().toString().trim())){
//            showAlertDialogForError(getString(R.string.email_valid_error));
//            return false;
//        }
//        return true;
//    }
}
