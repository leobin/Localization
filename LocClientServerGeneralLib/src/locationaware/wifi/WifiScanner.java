/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * scanPointWifi() is currently used
 * @author Xuan Nam
 */
public class WifiScanner {

    /**
     * Quét tín hiệu với khoảng th�?i gian nghỉ giữa 2 lần quét,
     * nếu quét mà không có dữ liệu thì sẽ thử lại, số lần thử là max
     * @param sleepTime
     * @param max số lần quét lại tối đa khi không phát hiện tín hiệu
     * @return ScanningPoint
     */
    public static ScanningPoint scanPointWifi(int sleepTime, int max) {
        int maxcount = max; // Số lần quét tối đa đến khi phát hiện được tín hiệu, nếu quá số lần này mà không có tín hiệu thì return null
        int count = 0;

        while (true && count <= maxcount) {
            count++;
            ScanningPoint sp = scan();
            if (sp != null) {
                return sp;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(WifiScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    /**
     * Quét tín hiệu mà không cần khoảng th�?i gian nghỉ giữa 2 lần quét,
     * có quét lại nếu không tìm thấy tín hiệu vào lần quét đầu,
     * số lần quét lại là max lần
     * @param wa
     * @param max số lần quét lại tối đa khi không phát hiện tín hiệu
     * @return ScanningPoint
     */
    public static ScanningPoint scanPointWifi(int max) {
        int maxcount = max;
        int count = 0;

        while (true && count <= maxcount) {
            count++;
            ScanningPoint sp = scan();
            if (sp != null) {
                return sp;
            }

        }
        return null;

    }

    /**
     * Quét list wifi
     * @param s
     * @param numOfScan

     * @return ArrayList<ScanningPoint>
     */
    public static ArrayList<ScanningPoint> scanListPointWifi(int numOfScan) {
        ArrayList<ScanningPoint> list = new ArrayList<ScanningPoint>();
        for (int i = 0; i < numOfScan; i++) {
            ScanningPoint p = scanPointWifi(3);
            list.add(p);
        }
        return list;
    }

    /**
     * Currently in used now
     * @param s
     * @return ScanningPoint
     */
    public static ScanningPoint scanPointWifi() {
        ScanningPoint sp = scan();
        return sp;

    }

    /**
     * Quét sóng wifi (tự động detect OS (XP, Vista, Windows 7)
     * @param s
     * @return ScanningPoint nếu scan được dữ hiệu hoặc null nếu không scan được dữ liệu nào.
     */
    static OSDetector osIndependent=new OSDetector();
    
    private static ScanningPoint scan() {
        ScanningPoint sp = osIndependent.scan();
        return sp;
    }

    public static long getScannerMac() {
    	long macAdd = osIndependent.getScannerMAC();
    	return macAdd;
    }

}
