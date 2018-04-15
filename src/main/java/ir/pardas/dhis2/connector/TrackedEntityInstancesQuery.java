package ir.pardas.dhis2.connector;

public class TrackedEntityInstancesQuery extends DHIS2Connector {

    public String get(String queryString) {
        return super.get("trackedEntityInstances.json", queryString);
    }

    public void addCititeria(String attribute,String value){

    }


}
