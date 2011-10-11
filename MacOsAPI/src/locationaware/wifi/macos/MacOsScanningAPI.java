package locationaware.wifi.macos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import locationaware.wifi.osindependentapi.AccessPointStub;
import locationaware.wifi.osindependentapi.OSIndependentAPI;
import locationaware.wifi.osindependentapi.ScanningPointStub;

public class MacOsScanningAPI extends OSIndependentAPI{
    public ScanningPointStub scan() {
        String str;
        ScanningPointStub vecapDetectedAP = new ScanningPointStub();
        String strCommand = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -s";

        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(strCommand);
        } catch (IOException ex) {
        }

        boolean bNewAP = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        try {
            String strSSID = new String();
            String strNetworkType = new String();
            String strAuthentication = new String();
            String strEncryption = new String();
            String strMACAddress = new String();
            double dPercentageSignal = 0;
            String strRadioType = new String();
            int iChannel = 0;
            Vector<Double> vecdBasicRates = new Vector<Double>();
            Vector<Double> vecdOtherRates = new Vector<Double>();
            reader.readLine();

            while ((str = reader.readLine()) != null) {
                //get wifi name
                str = str.trim();
                int endOfWifiName = str.indexOf(':') - 3;
                strSSID = str.substring(0,endOfWifiName);
                str = str.substring(endOfWifiName).trim();
                str = str.trim();
                String[] split = str.split(" ");
//                strSSID = split[0];
                strMACAddress = split[0];
                dPercentageSignal = Double.parseDouble(split[1]);

                String tempChannel = split[3];
                if (tempChannel.contains(",")){
                    tempChannel = tempChannel.substring(0,tempChannel.indexOf(','));
                }
                iChannel = Integer.parseInt(tempChannel);
                strAuthentication = split[split.length - 1];

                bNewAP = true;
                if (bNewAP) {
                    AccessPointStub apis = new AccessPointStub();
                    apis.accessPointName = strSSID;
                    apis.Authentication = strAuthentication;
                    apis.Encryption = strEncryption;
                    apis.iChannel = iChannel;
                    apis.MACAddress = OSIndependentAPI.convertMACToLong(strMACAddress);
                    apis.networkType = strNetworkType;
                    apis.RadioType = strRadioType;
                    apis.signalStrength = dPercentageSignal;
                    apis.normalizedSignalStrength = dPercentageSignal;
                    apis.vecdBasicRates = vecdBasicRates;
                    apis.vecdOtherRates = vecdOtherRates;
                    vecapDetectedAP.apStubsList.add(apis);
                    bNewAP = false;
                }
            }

        } catch (IOException ex) {
        }
        if (vecapDetectedAP.apStubsList.size() > 0) {
            return vecapDetectedAP;
        } else {
            return null;
        }
    }

    public long getScannerMAC() {
        long macAddLong = 0;
        String macAddString;
        String str;
        String strCommand = "ifconfig";

        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(strCommand);
        } catch (IOException ex) {
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));

        try {
            while ((str = reader.readLine()) != null) {
                if (str.startsWith("en1")) {
                    while ((str = reader.readLine()) != null) {
                        if (str.indexOf("ether") != -1) {
                            macAddString = str.substring(str.indexOf(" ") + 1);
                            macAddLong = OSIndependentAPI.convertMACToLong(macAddString);
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return macAddLong;
    }
}
