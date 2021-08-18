
package se.bryderi.service;

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
import se.bryderi.events.GreetingResponse.Greeting;


@ApplicationScoped
@GraphQLApi
public class GreetingService {

    @Inject
    EventBus bus;

    @Query
    public Uni<GreetingResponse> getGreeting(String name) {
        GreetingRequest query = new GreetingRequest();
       
        query.setName(name);
        Uni<GreetingResponse> result = bus.<GreetingResponse>request("greeting.query", query).onItem()
                .transform(Message::body);
        return result;

    }

    @ConsumeEvent("greeting.query")
    public GreetingResponse onGreetingQuery(GreetingRequest query) {
        
        GreetingResponse response = new GreetingResponse();
        Greeting greeting = new Greeting();
        greeting.setName("Hello " + query.getName());
        response.getGreetings().add( greeting );

        return response;
    }
}
