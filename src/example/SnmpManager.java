package example;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.*;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import static example.OidManager.getOidValue;


public class SnmpManager implements CommandResponder {

//    private static final SnmpManager snmpManager = new SnmpManager();
//
//    private SnmpManager() {
//    }
//
//    public static SnmpManager getInstance() {
//        return snmpManager;
//    }

    // Config params
    private static final String CONFIG_IP = "message.operator.params.snmp.ip";
    private static final String CONFIG_PORT = "message.operator.params.snmp.port";
    private final String[] configParamKeys = {
            CONFIG_IP, CONFIG_PORT};

    private static Integer status = 0;
    // TODO change method


    public static int getStatus(String oidValue, String community, String port, String ipAddress, int version) {
        String valuesOfOid = "" + getOidValue(oidValue, community, port, ipAddress, version)[0];
        String[] value = valuesOfOid.split("=");
        status = Integer.parseInt(value[1].trim());
        System.out.println(status);
        return status;
    }


    public void connectSNMP(String ipAddress) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipAddress);
        sb.append("/162"); // for listening trap use //161
        try {
            listen(new UdpAddress(sb.toString()));
        } catch (Exception e) {
            System.out.println("Can not connect the " + sb.toString());
            e.printStackTrace();
        }
    }

    public void connectSNMP(String ipAddress, String port) {
        try {
            /*
             * if (udpipSocketIO == null) { udpipSocketIO = new
             * UDPIPSocketIO("192.168.1.41", 162, new SNMPMEssageOperations(),
             * true, false); udpipSocketIO.addDataListener(this);
             * udpipSocketIO.connect(); }
             */
            StringBuilder sb = new StringBuilder();
            sb.append(ipAddress);
            sb.append("/");
            sb.append(port);

            listen(new UdpAddress(sb.toString()));
            // setOidValue();"

//            listen(new UdpAddress("192.168.1.110/161"));
            // startConnectionLossThread();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void listen(TransportIpAddress address)
            throws IOException {
        AbstractTransportMapping transport;
        if (address instanceof TcpAddress) {
            transport = new DefaultTcpTransportMapping((TcpAddress) address);
        } else {
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        }

        ThreadPool threadPool = ThreadPool.create("DispatcherPool", 10);
        MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(
                threadPool, new MessageDispatcherImpl());

        // add message processing models
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());

        // add all security protocols
        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        // Create Target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));

        Snmp snmp = new Snmp(mtDispatcher, transport);
        snmp.addCommandResponder(this);

        transport.listen();
        System.out.println("Listening on " + address);
        // .1.3.6.1.2.1.1.3
        try {
            this.wait();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    // PDU has getVariableBindings method Vector<? extends VariableBinding>
    // VariableBinding has getOid getVariable methods.. OID || Variable
    public void processPdu(CommandResponderEvent cmdRespEvent) {
        // TODO Auto-generated method stub
//        System.out.println("Received PDU...");
        PDU pdu = cmdRespEvent.getPDU();
        if (pdu != null) {

//             info message
//            System.out.println("Trap Type = " + pdu.getType());
//            System.out.println("Variable Bindings = "
//                    + pdu.getVariableBindings());
//
////                    + pdu.getVariableBindings()
//            );

            Vector<? extends VariableBinding> variables = pdu.getVariableBindings();
            String value = "";
            for (VariableBinding variable : variables) {
                value = DahuaCameraValueFetcher.checkDetection(variable);
                if (!value.isEmpty() && value.equalsIgnoreCase("Start")) {
                    long startDetectionTime = System.currentTimeMillis();
//                    startDetectionThread(time);
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                    Date resultdate = new Date(startDetectionTime);
                    System.out.println("Started detection");
                    System.out.println(sdf.format(resultdate));
                } else if (!value.isEmpty() && value.equalsIgnoreCase("Stop")) {
                    long stopDetectionTime = System.currentTimeMillis();
                    System.out.println("Stopped detection");
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                    Date resultdate = new Date(stopDetectionTime);
                    System.out.println(sdf.format(resultdate));
//                    stopDetectionThread(time);

                }

            }
        }

    }


    // Name/OID: .1.3.6.1.4.1.25.1.4.3.10.3.0; Value (Integer): 1


}
