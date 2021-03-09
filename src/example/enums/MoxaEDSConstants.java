package example.enums;

public enum MoxaEDSConstants {
    // MOXA EDS-G512E-T
    PORT_1(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.1"),
    PORT_2(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.2"),
    PORT_3(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.3"),
    PORT_4(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.4"),
    PORT_5(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.5"),
    PORT_6(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.6"),
    PORT_7(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.7"),
    PORT_8(".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.8"),
    COMPUTER_IP_ADDRESS("192.168.127.250"),
    DEVICE_IP_ADDRESS("192.168.127.253"),
    SNMP_SET_GET_PORT("161"),
    SNMP_TRAP_PORT("162"),
    // Set port 161
    // Trap port 162
    COMMUNITY("public");

    public final String variable;

    MoxaEDSConstants(String variable) {
        this.variable = variable;
    }

    //    PORT_1.variable,
    public final static String[] PORTS = {PORT_2.variable, PORT_3.variable, PORT_4.variable, PORT_5.variable, PORT_6.variable, PORT_7.variable, PORT_8.variable};

}
