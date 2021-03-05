package example;

import org.snmp4j.mp.SnmpConstants;

public class Main {
    private static final String port1 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.1";
    private static final String port2 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.2";
    private static final String port3 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.3";
    private static final String port4 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.4";
    private static final String port5 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.5";
    private static final String port6 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.6";
    private static final String port7 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.7";
    private static final String port8 = ".1.3.6.1.4.1.8691.7.70.1.9.1.1.3.8";
    private static String[] ports = {port2, port3, port4, port5, port6, port7, port8};
    private static final String ipAddressTrap = "192.168.1.110";
    private static final String port = "162";
    private static final String community = "public";

    public static void main(String[] args) throws InterruptedException {
        SnmpExample snmp = new SnmpExample();
//        while (true) {
//            for (String s : ports) {
//                snmp.getStatus(s, 0, community, port, ipAddressTrap, SnmpConstants.version1);
//                Thread.sleep(500);
//            }
//            for (String s : ports) {
//                snmp.getStatus(s, 1, community, port, ipAddressTrap, SnmpConstants.version1);
//                Thread.sleep(500);
//            }
//    }
        snmp.connectSNMP(ipAddressTrap,port);

        Thread.sleep(2000);


    }
}
