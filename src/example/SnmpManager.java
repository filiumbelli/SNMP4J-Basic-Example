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
import java.util.Set;
import java.util.Vector;


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


    public int getStatus(String oidAddress, int oidValue, String community, String port, String address, int version) {
        setOidValue(oidAddress, oidValue, community, port, address, version);
        String valuesOfOid = "" + getOidValue(community, address, port, oidAddress)[0];
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

            System.out.println("Trap Type = " + pdu.getType());
            System.out.println("Variable Bindings = "
                    + pdu.getVariableBindings()
                    + pdu.getVariableBindings()
            );

            Vector<? extends VariableBinding> variables = pdu.getVariableBindings();
            String value = "";
            for (VariableBinding variable : variables) {
                value = SnmpValueFetcher.loopChecker(variable);
                if (!value.isEmpty()) {
                    System.out.println(value);
                }
            }


        }

    }


    // Name/OID: .1.3.6.1.4.1.25.1.4.3.10.3.0; Value (Integer): 1
    private void setOidValue(String oidValue, int value, String community, String port, String ipAddress, int version) {
        TransportMapping transport = null;
        try {
            transport = new DefaultUdpTransportMapping();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            transport.listen();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CommunityTarget comtarget = new CommunityTarget();
        comtarget.setCommunity(new OctetString(community));
        comtarget.setVersion(version);
        comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
        comtarget.setRetries(2);
        comtarget.setTimeout(1000);

        PDU pdu = new PDU();
        OID oid = new OID(oidValue);
//		Setter variable
        Variable var = new Integer32(value);
        VariableBinding varBind = new VariableBinding(oid, var);
        pdu.add(varBind);
        pdu.setType(PDU.SET);
        pdu.setRequestID(new Integer32(1));
        Snmp snmp = new Snmp(transport);

        try {
            ResponseEvent response = snmp.set(pdu, comtarget);
            PDU responsePdu = response.getResponse();
            System.out.println(responsePdu);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Object[] getOidValue(String community, String ipAddress, String port, String sysContactOid) {
        TransportMapping transport = null;
        try {
            transport = new DefaultUdpTransportMapping();
            transport.listen();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Create Target Address object
        CommunityTarget comtarget = new CommunityTarget();
        comtarget.setCommunity(new OctetString(community));
        // comtarget.setVersion(snmpVersion);
        comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
        comtarget.setRetries(2);
        comtarget.setTimeout(1000);

        // Create the PDU object
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(sysContactOid)));
        pdu.setType(PDU.GET);
        pdu.setRequestID(new Integer32(1));

        // Create Snmp object for sending data to Agent
        Snmp snmp = new Snmp(transport);

        // System.out.println("Sending Request to Agent...");
        ResponseEvent response;
        PDU responsePDU = null;
        try {
            response = snmp.get(pdu, comtarget);
            if (response != null) {
                System.out.println("Got Response from Agent");
                responsePDU = response.getResponse();

                if (responsePDU != null) {
                    int errorStatus = responsePDU.getErrorStatus();
                    int errorIndex = responsePDU.getErrorIndex();
                    String errorStatusText = responsePDU.getErrorStatusText();

                    if (errorStatus == PDU.noError) {
                        System.out.println("Snmp Get Response = "
                                + responsePDU.getVariableBindings());
                    } else {
                        System.out.println("Error: Request Failed");
                        System.out.println("Error Status = " + errorStatus);
                        System.out.println("Error Index = " + errorIndex);
                        System.out.println("Error Status Text = "
                                + errorStatusText);
                    }
                } else {
                    System.out.println("Error: Response PDU is null");
                }
            } else {
                System.out.println("Error: Agent Timeout... ");
            }
            snmp.close();

            return responsePDU.getVariableBindings().toArray();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return responsePDU.getVariableBindings().toArray();

        // Process Agent Response

    }

}
