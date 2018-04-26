package ir.pardas.dhis2.connector;

/*
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hisp.dhis.dxf2.events.trackedentity.TrackedEntityInstance;
*/

public class TrackedEntityInstances extends DHIS2Connector {

    public String post(String payload) /*throws JsonProcessingException*/ {
        return super.post("trackedEntityInstances", payload);
    }
   /* public String post(TrackedEntityInstance instance) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(instance);
        System.out.println(payload);
        return post(payload);
    }*/

}
