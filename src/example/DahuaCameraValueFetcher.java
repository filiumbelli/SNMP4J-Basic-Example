package example;

import example.enums.DahuaCameraConstants;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import java.util.ArrayList;
import java.util.Vector;

public class DahuaCameraValueFetcher {
    static String checkStatus = "";
    static String checkTime = "";

    public static ArrayList<? extends OID> getOidAndStatus(Vector<? extends VariableBinding> oidValues, PDU pdu) {
        ArrayList<OID> statusValues = new ArrayList<OID>();
        for (VariableBinding oidValue : oidValues) {
            statusValues.add(oidValue.getOid());
            System.out.println("------------------------------------");
            System.out.println("OID: " + oidValue.getOid());
            System.out.println("Variable: " + oidValue.getVariable());
            System.out.println("------------------------------------");
        }
        return statusValues;
    }

    public static boolean checkDetectionOid(OID oid) {
        String oidValue = oid.toString();
        return oidValue.equals(DahuaCameraConstants.DETECTION_INFO_OID.variable);
    }

    public static boolean checkDetectionTime(OID oid) {
        String oidValue = oid.toString();
        return oidValue.equals(DahuaCameraConstants.DETECTION_TIME_OID.variable);
    }


    public static String checkDetection(VariableBinding variable) {
        OID oid = variable.getOid();
        Variable status = variable.getVariable();
        if (checkDetectionOid(oid) && !checkStatus.equals(status.toString())) {
            synchronized (status) {
                checkStatus = status.toString();
                return checkStatus;
            }
        } else if (checkDetectionTime(oid) && !checkTime.equals(status.toString())) {
            checkTime = status.toString();
            return checkTime;
        }
        return "";
    }

//    public static String getOidValue(Vector<? extends VariableBinding> oidValues){
//        String statusValues = ""
//    }

}
