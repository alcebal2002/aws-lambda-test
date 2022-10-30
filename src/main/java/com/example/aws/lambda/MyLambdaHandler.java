package com.example.aws.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MyLambdaHandler implements RequestHandler<Map<String, String>, String> {

    public String handleRequest(Map<String, String> event, Context context) {
        if (null == event.get("message"))
            throw new IllegalArgumentException("message missing");

        return event.get("message");
    }
}
