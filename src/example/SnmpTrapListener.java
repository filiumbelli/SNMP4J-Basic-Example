package example;

import org.snmp4j.*;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.Date;

/**
 * @author Joel Patrick Llosa
 */
public class SnmpTrapListener {


    public static void main(String[] args) throws Exception {
        Address address = GenericAddress.parse("udp:localhost/162");
        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping((UdpAddress) address);
        transport.listen();

        CommandResponder trapPrinter = new CommandResponder() {
            public synchronized void processPdu(CommandResponderEvent e) {
                PDU command = e.getPDU();
                if (command != null) {
                    System.out.println("snmp trap received: " + command.toString());
                }
            }
        };

        Snmp snmp = new Snmp(transport);
        snmp.addCommandResponder(trapPrinter);
        while (true) {
            System.out.println("listening... Ctrl-C to stop");
            Thread.sleep(3000);
        }
    }

}


