/*
package ir.pardas.dhis2.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hisp.dhis.dxf2.events.enrollment.Enrollment;
import org.hisp.dhis.dxf2.events.trackedentity.TrackedEntityInstance;

public class Enrollments extends DHIS2Connector {

    public String post(Enrollment instance) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(instance);
        System.out.println(payload);
        return super.post("enrollments", payload);
    }

}
*/
