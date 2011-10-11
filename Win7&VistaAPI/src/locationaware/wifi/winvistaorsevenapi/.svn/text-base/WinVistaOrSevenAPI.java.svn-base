package locationaware.wifi.winvistaorsevenapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import locationaware.wifi.osindependentapi.AccessPointStub;
import locationaware.wifi.osindependentapi.OSIndependentAPI;
import locationaware.wifi.osindependentapi.ScanningPointStub;

public class WinVistaOrSevenAPI extends OSIndependentAPI{
	public ScanningPointStub scan() {
        String str;
        ScanningPointStub vecapDetectedAP = new ScanningPointStub();
        String strCommand = "netsh wlan show networks mode=bssid";

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

            while ((str = reader.readLine()) != null) {
                if (str.startsWith("SSID")) {
                    strSSID = str.substring(str.indexOf(":") + 2);
                }

                if (str.indexOf("Network type") != -1) {
                    strNetworkType = str.substring(str.indexOf(":") + 2);
                }

                if (str.indexOf("Authentication") != -1) {
                    strAuthentication = str.substring(str.indexOf(":") + 2);
                }

                if (str.indexOf("Encryption") != -1) {
                    strEncryption = str.substring(str.indexOf(":") + 2);
                }

                if (str.indexOf("BSSID") != -1) {
                    strMACAddress = str.substring(str.indexOf(":") + 2);
                }

                if (str.indexOf("Signal") != -1) {
                    dPercentageSignal = Double.parseDouble(str.substring(str.indexOf(":") + 2, str.indexOf("%")));
                }

                if (str.indexOf("Radio type") != -1) {
                    strRadioType = str.substring(str.indexOf(":") + 2);
                }

                if (str.indexOf("Channel") != -1) {
                    iChannel = Integer.parseInt(str.substring(str.indexOf(":") + 2).trim());
                }

                if (str.indexOf("Basic rates") != -1 || str.indexOf("Basic Rates") != -1) {
                    String strTemp = str.substring(str.indexOf(":") + 2);
                    StringTokenizer strtok = new StringTokenizer(strTemp);

                    while (strtok.hasMoreTokens()) {
                        vecdBasicRates.add(Double.parseDouble(strtok.nextToken()));
                    }
                }

                if (str.indexOf("Other rates") != -1 || str.indexOf("Other Rates") != -1) {
                    String strTemp = str.substring(str.indexOf(":") + 2);
                    StringTokenizer strtok = new StringTokenizer(strTemp);

                    while (strtok.hasMoreTokens()) {
                        vecdOtherRates.add(Double.parseDouble(strtok.nextToken()));
                    }

                    bNewAP = true;
                }

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
		String strCommand = "ipconfig /all";

        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(strCommand);
        } catch (IOException ex) {
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));

        try {
			while ((str = reader.readLine()) != null) {
				if (str.startsWith("Wireless LAN")) {
					while ((str = reader.readLine()) != null) {
						if (str.indexOf("Physical Address") != -1) {
							macAddString = str.substring(str.indexOf(":") + 2);
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
