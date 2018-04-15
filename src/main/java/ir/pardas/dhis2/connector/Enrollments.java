package ir.pardas.dhis2.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hisp.dhis.dxf2.events.enrollment.Enrollment;

public class Enrollments extends DHIS2Connector {

    public String post(Enrollment instance) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(instance);
        return super.post("enrollments", payload);
    }

    public String cancel(String enrollmentId){
        return get("enrollments/"+enrollmentId+"/cancel","");
    }

    public String complete(String enrollmentId){
        return get("enrollments/"+enrollmentId+"/complete","");
    }

    public String delete(String enrollmentId){
        return get("enrollments/"+enrollmentId+"/delete","");
    }

}
