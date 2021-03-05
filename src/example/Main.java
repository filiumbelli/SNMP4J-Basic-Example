package example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        SnmpManager snmp = new SnmpManager();
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
        snmp.connectSNMP(DahuaCameraConstants.IP_ADDRESS.variable,DahuaCameraConstants.PORT.variable);

        Thread.sleep(2000);


    }
}
