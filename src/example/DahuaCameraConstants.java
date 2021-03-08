package example;

public enum DahuaCameraConstants {
    //DAHUA-SNMP-MIB

    DETECTION_INFO_OID("1.3.6.1.4.1.1004849.2.12.1.0"),//return Start-Stop variable
    DETECTION_TIME_OID("1.3.6.1.4.1.1004849.2.12.2.0"),// return device detection time || format:2000/1/25 Tue 2:49:43
    COMPUTER_IP_ADDRESS("192.168.1.109"),
    DEVICE_IP_ADDRESS("192.168.1.108"),
    SNMP_SET_GET_PORT("161"),
    SNMP_TRAP_PORT("162"),
    // Set port 161
    // Trap port 162
    COMMUNITY("public");
    public final String variable;

    DahuaCameraConstants(String variable) {
        this.variable = variable;
    }

}
