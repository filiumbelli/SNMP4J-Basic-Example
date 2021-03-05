package example;

public enum DahuaCameraConstants {
    //DAHUA-SNMP-MIB

    DETECTION_INFO_OID("1.3.6.1.4.1.1004849.2.12.1.0"),//return Start-Stop variable
    DETECTION_TIME_OID("1.3.6.1.4.1.1004849.2.12.2.0"),// return device detection time || format:2000/1/25 Tue 2:49:43
    IP_ADDRESS("192.168.1.110"),
    PORT("162"),
    COMMUNITY("public");
    public final String variable;

    DahuaCameraConstants(String variable) {
        this.variable = variable;
    }

}
