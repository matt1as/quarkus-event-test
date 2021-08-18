package se.bryderi.events;

import java.util.ArrayList;
import java.util.List;

public class GreetingResponse {

    private List<Greeting> greetings = new ArrayList<Greeting>();


    public List<Greeting> getGreetings() {
        return this.greetings;
    }

    public void setGreetings(List<Greeting> greetings) {
        this.greetings = greetings;
    }
    
    public static class Greeting {
        private String name;
         
        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
