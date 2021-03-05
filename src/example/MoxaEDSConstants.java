package example;

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
    IP_ADDRESS("192.168.1.110"),
    PORT("162"),
    COMMUNITY("public");
    //    private static String[] ports = {port1, port2, port3, port4, port5, port6, port7, port8};
    final String variable;

    MoxaEDSConstants(String variable) {
        this.variable = variable;
    }
}
