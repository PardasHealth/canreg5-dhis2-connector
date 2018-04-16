package ir.pardas.dhis2.connector;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hisp.dhis.dxf2.events.trackedentity.TrackedEntityInstance;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrackerPayloadTest {
    @Test
    public void testJson() {
        Dictionary dictionary = new Dictionary();
        try {
            dictionary.load();
            Path path = Paths.get(this.getClass().getResource("/breast11.csv").toURI());

            CSVParser parser = CSVFile.read(path, Dictionary.CSV_HEADER);

            int i = 0;
            for (CSVRecord record : parser) {
                i++;
                //Skip 1st line
                if(i==1)
                    continue;
                TrackedEntityInstance trackedEntityInstance = new TrackedEntityInstance();
//TODO   کد منحصر بفرد فرد
                trackedEntityInstance.setTrackedEntityInstance("0"+record.get(Dictionary.DIC_NID));//Dictionary.DIC_TrackedEntityInstance
//TODO  sDzK609rUbJ بیمارستان امام
                trackedEntityInstance.setOrgUnit("sDzK609rUbJ");//Dictionary.DIC_OrgUnit
//TODO  nEenWmSyUEp Persone
                trackedEntityInstance.setTrackedEntityType("nEenWmSyUEp");//Dictionary.DIC_TrackedEntityType

//Tracker Attributes
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_FIRSTN, record.get(Dictionary.DIC_FIRSTN)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_FATHNAME, record.get(Dictionary.DIC_FATHNAME)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_FAMN, record.get(Dictionary.DIC_FAMN)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_NID, record.get(Dictionary.DIC_NID)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_BIRTHD, record.get(Dictionary.DIC_BIRTHD)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_TELEPHONE, record.get(Dictionary.DIC_TELEPHONE)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_MOBILEN, record.get(Dictionary.DIC_MOBILEN)));
                trackedEntityInstance.getAttributes().add(
                        dictionary.createAttribute(Dictionary.DIC_MOBILEN, record.get(Dictionary.DIC_MOBILEN)));
                // 'household location' & 'Nationality' not find
                /*List<Enrollment> enrollments = new ArrayList<>();
                Enrollment enrollment = new Enrollment();
                //.....
                List<Event>events = new ArrayList<>();
                Event event = new Event();
                //.....
                events.add(event);
                enrollment.setEvents(events);
                enrollments.add(enrollment);
                trackedEntityInstance.setEnrollments(enrollments);*/
//
                try {
                    TrackedEntityInstances connector = new TrackedEntityInstances();
                    String respose = connector.post(trackedEntityInstance);
                    System.out.println(respose);

/*
                    TrackedEntityInstancesQuery queryConnector = new TrackedEntityInstancesQuery();
                    respose = queryConnector.get("ouMode=ALL&filter=" + dictionary.getDictionaryElement(Dictionary.DIC_FIRSTNAME).getUid() + ":EQ:Mahdi");
                    System.out.println(respose);
*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
