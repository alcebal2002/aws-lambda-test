package com.example.aws.lambda;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.lambda.runtime.tests.EventLoader;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;

public class LambdaTest {

    @Test
    public void testLoadEventBridgeEvent() throws IOException {
        // Given
        ScheduledEvent event = EventLoader.loadScheduledEvent("event.json");
        MyLambdaHandler handler = new MyLambdaHandler();
        // When
        String response = handler.handleRequest((Map) event.getDetail(), null);
        // Then
        assertThat(response, equalTo("it works!"));
    }

    // Same Test than testLoadEventBridgeEvent but using Parametrized event

    @ParameterizedTest
    @Event(value = "event.json", type = ScheduledEvent.class)
    public void testLoadEventBridgeEvent(ScheduledEvent event) {
        assertThat(event, is(not(nullValue())));

        // Given
        MyLambdaHandler handler = new MyLambdaHandler();
        // When
        String response = handler.handleRequest((Map) event.getDetail(), null);
        // Then
        assertThat(response, equalTo("it works!"));
    }

    @Test
    public void testLoadEventMissingArgEvent() throws IOException {
        // Given
        ScheduledEvent event = EventLoader.loadScheduledEvent("event_missing_arg.json");
        MyLambdaHandler handler = new MyLambdaHandler();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // When - Then
            handler.handleRequest((Map) event.getDetail(), null);
        });

        assertThat(exception, is(not(nullValue())));
    }
}
