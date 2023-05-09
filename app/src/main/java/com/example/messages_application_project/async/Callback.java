package com.example.messages_application_project.async;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
