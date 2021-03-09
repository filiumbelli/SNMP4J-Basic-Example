package example;

import example.enums.DahuaCameraConstants;
import example.enums.MoxaEDSConstants;
import org.snmp4j.mp.SnmpConstants;

public class Main {

    public static void main(String[] args) {
//        String[] ports = MoxaEDSConstants.PORTS;
//        for (String s : ports) {
//            System.out.println(s);
//        }


        listenPortThread(MoxaEDSConstants.COMPUTER_IP_ADDRESS.variable, MoxaEDSConstants.SNMP_TRAP_PORT.variable);
        listenPortThread(DahuaCameraConstants.COMPUTER_IP_ADDRESS.variable, DahuaCameraConstants.SNMP_TRAP_PORT.variable);
        OidManager.setOidValue(
                MoxaEDSConstants.PORT_3.variable,
                1,
                MoxaEDSConstants.COMMUNITY.variable,
                MoxaEDSConstants.SNMP_SET_GET_PORT.variable,
                MoxaEDSConstants.DEVICE_IP_ADDRESS.variable,
                SnmpConstants.version1);
//        snmp.connectSNMP(DahuaCameraConstants.IP_ADDRESS.variable,DahuaCameraConstants.PORT.variable);
//        snmp.connectSNMP(MoxaEDSConstants.DEVICE_IP_ADDRESS.variable, MoxaEDSConstants.SNMP_TRAP_PORT.variable);


        OidManager.getOidValue(MoxaEDSConstants.PORT_3.variable,
                MoxaEDSConstants.COMMUNITY.variable,
                MoxaEDSConstants.SNMP_SET_GET_PORT.variable,
                MoxaEDSConstants.DEVICE_IP_ADDRESS.variable,
                SnmpConstants.version1);

//        statusCheck();

    }

    public static void statusCheck() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    for (String port : MoxaEDSConstants.PORTS) {
                        SnmpManager.getStatus(port,
                                MoxaEDSConstants.COMMUNITY.variable,
                                MoxaEDSConstants.SNMP_SET_GET_PORT.variable,
                                MoxaEDSConstants.DEVICE_IP_ADDRESS.variable,
                                SnmpConstants.version1);

                        if (port.equals("1")) {
                            System.out.println("Port is open! ");

                        } else {
                            System.out.println("Port is closed! ");
                        }
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();


    }


    public static void listenPortThread(final String ipAddress, final String port) {
        new Thread(new Runnable() {

            public void run() {
                new SnmpManager().connectSNMP(ipAddress, port);
            }
        }).start();
    }

    public static void setGetPortThread() {
        new Thread(new Runnable() {
            String[] ports = MoxaEDSConstants.PORTS;

            @Override
            public void run() {
                while (true) {
                    SnmpManager snmp = new SnmpManager();
                    for (String s : ports) {

                        snmp.getStatus(s, MoxaEDSConstants.COMMUNITY.variable, MoxaEDSConstants.SNMP_SET_GET_PORT.variable, MoxaEDSConstants.DEVICE_IP_ADDRESS.variable, SnmpConstants.version1);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (String s : ports) {
                        snmp.getStatus(s, MoxaEDSConstants.COMMUNITY.variable, MoxaEDSConstants.SNMP_SET_GET_PORT.variable, MoxaEDSConstants.DEVICE_IP_ADDRESS.variable, SnmpConstants.version1);
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
