
package se.bryderi.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import se.bryderi.events.GreetingRequest;
import se.bryderi.events.GreetingResponse;

@ApplicationScoped
@GraphQLApi
public class GreetingService {

    @Inject
    EventBus bus;

    @Query
    public Uni<List<GreetingResponse>> getGreeting(String name) {
        GreetingRequest query = new GreetingRequest();
        query.setName(name);
        Uni<List<GreetingResponse>> result = bus.<List<GreetingResponse>>request("greeting.query", query).onItem()
                .transform(Message::body);
        return result;

    }

    @ConsumeEvent("greeting.query")
    public List<GreetingResponse> query(GreetingRequest query) {
        
        GreetingResponse response = new GreetingResponse();
        response.setTitle("Hello " + query.getName());
        List<GreetingResponse> greetings = List.of(response);

        return greetings;
    }
}
