package example;

import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        String[] ports = MoxaEDSConstants.PORTS;
//        for (String s : ports) {
//            System.out.println(s);
//        }


        listenPortThread(MoxaEDSConstants.COMPUTER_IP_ADDRESS.variable, MoxaEDSConstants.SNMP_TRAP_PORT.variable);
        listenPortThread(DahuaCameraConstants.COMPUTER_IP_ADDRESS.variable, DahuaCameraConstants.SNMP_TRAP_PORT.variable);

//        snmp.connectSNMP(DahuaCameraConstants.IP_ADDRESS.variable,DahuaCameraConstants.PORT.variable);
//        snmp.connectSNMP(MoxaEDSConstants.DEVICE_IP_ADDRESS.variable, MoxaEDSConstants.SNMP_TRAP_PORT.variable);


    }

    public static void listenPortThread(final String ipAddress, final String port) {
        System.out.println(ipAddress + port);
        new Thread(new Runnable() {

            public void run() {
                new SnmpManager().connectSNMP(ipAddress, port);
            }
        }).start();

    }

    public void setGetPortThread() {
        new Thread(new Runnable() {
            String[] ports = MoxaEDSConstants.PORTS;

            @Override
            public void run() {
                while (true) {
                    SnmpManager snmp = new SnmpManager();
                    for (String s : ports) {
                        snmp.getStatus(s, 0, MoxaEDSConstants.COMMUNITY.variable, MoxaEDSConstants.SNMP_SET_GET_PORT.variable, MoxaEDSConstants.DEVICE_IP_ADDRESS.variable, SnmpConstants.version1);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (String s : ports) {
                        snmp.getStatus(s, 1, MoxaEDSConstants.COMMUNITY.variable, MoxaEDSConstants.SNMP_SET_GET_PORT.variable, MoxaEDSConstants.DEVICE_IP_ADDRESS.variable, SnmpConstants.version1);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
