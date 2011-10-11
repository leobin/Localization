/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author leobin
 */
public class ApplicationConfiguration {

    private static final String TAG_SCANNING_TIME = "scanning-time";
    private static final String TAG_NUMBER_OF_SCANNING_POINT = "number-of-scanning-point";
    private static final String TAG_SCANNING_FREQUENCY = "scanning-frequency";
    private static final String TAG_DATA_FOLDER = "data-folder";
    private static final String TAG_IS_AUTO_UPLOAD = "is-auto-upload";
    private static final String TAG_ALGORITHM_PACKAGE = "algorithm-package";
    private static final String TAG_DATA_TYPE_PACKAGE = "datatype-package";
    private static final String TAG_DATA_TYPE_PANEL_PACKAGE = "datatypepanel-package";
    private static final String TAG_SERVER_HOST = "server-host";
    private static final String TAG_LOCALIZE_PORT = "localize-port";
    private static final String TAG_SUBMIT_DATA_PORT = "submit-data-port";
    private static final String TAG_CALL_SERVER_OBJECT_PORT = "call-server-object-port";
    private static final String TAG_CALL_SERVER_FUNCTION_PORT = "call-server-function-port";
    private static final String TAG_PREFERENCES = "preferences";
    private static final String TAG_JAR_LIB_FOR_LOAD_CLASS = "jar-for-class";
    public int scanningTime;
    public int numberOfScanningPoint;
    public double scanningFrequency;
    public String dataFolder;
    public boolean isAutoUpload;
    public String algorithmPackage = "locationaware.wifi.algorithm";
    public String datatypePackage = "locationaware.wifi.mapdata";
    public String datatypepanelPackage = "com.localization.datatype.panel";
    public String fileJarForLoadClass = "WifiLocationAware_16.jar";
    public String serverHost = "ubigroup.dyndns.org";
    public int localizePort = 10001;
    public int submitDataPort = 10002;
    public int callServerObjectFunctionPort = 10002;
    public int callServerFunctionPort = 10002;
    private static ApplicationConfiguration singleton;
    private File configurationFile = new File("preferences.xml");
    private Document dom;

    private ApplicationConfiguration() {
        try {
            //ErrorLog.writeToLog(file.getAbsolutePath().toString());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = (Document) db.parse(configurationFile);

            Node node = doc.getElementsByTagName(TAG_SCANNING_TIME).item(0).getChildNodes().item(0);
            this.scanningTime = Integer.parseInt(node.getNodeValue());

            node = doc.getElementsByTagName(TAG_NUMBER_OF_SCANNING_POINT).item(0).getChildNodes().item(0);
            this.numberOfScanningPoint = Integer.parseInt(node.getNodeValue());

            node = doc.getElementsByTagName(TAG_SCANNING_FREQUENCY).item(0).getChildNodes().item(0);
            this.scanningFrequency = Double.parseDouble(node.getNodeValue());

            node = doc.getElementsByTagName(TAG_DATA_FOLDER).item(0).getChildNodes().item(0);
            this.dataFolder = node.getNodeValue().trim();

            node = doc.getElementsByTagName(TAG_IS_AUTO_UPLOAD).item(0).getChildNodes().item(0);
            this.isAutoUpload = node.getNodeValue().equals("true");

            node = doc.getElementsByTagName(TAG_ALGORITHM_PACKAGE).item(0).getChildNodes().item(0);
            this.algorithmPackage = node.getNodeValue().trim();

            node = doc.getElementsByTagName(TAG_DATA_TYPE_PACKAGE).item(0).getChildNodes().item(0);
            this.datatypePackage = node.getNodeValue().trim();

            node = doc.getElementsByTagName(TAG_DATA_TYPE_PANEL_PACKAGE).item(0).getChildNodes().item(0);
            this.datatypepanelPackage = node.getNodeValue().trim();

            node = doc.getElementsByTagName(TAG_JAR_LIB_FOR_LOAD_CLASS).item(0).getChildNodes().item(0);
            this.fileJarForLoadClass = node.getNodeValue().trim();

            node = doc.getElementsByTagName(TAG_SERVER_HOST).item(0).getChildNodes().item(0);
            this.serverHost = node.getNodeValue().trim();

            node = doc.getElementsByTagName(TAG_LOCALIZE_PORT).item(0).getChildNodes().item(0);
            this.localizePort = Integer.parseInt(node.getNodeValue());

            node = doc.getElementsByTagName(TAG_SUBMIT_DATA_PORT).item(0).getChildNodes().item(0);
            this.submitDataPort = Integer.parseInt(node.getNodeValue());

            node = doc.getElementsByTagName(TAG_CALL_SERVER_OBJECT_PORT).item(0).getChildNodes().item(0);
            this.callServerObjectFunctionPort = Integer.parseInt(node.getNodeValue());

            node = doc.getElementsByTagName(TAG_CALL_SERVER_FUNCTION_PORT).item(0).getChildNodes().item(0);
            this.callServerFunctionPort = Integer.parseInt(node.getNodeValue());

        } catch (Exception e) {
            scanningTime = 100;
            numberOfScanningPoint = 15;
            scanningFrequency = 0.5;
            dataFolder = FileHandle.DATA_FOLDER;
            isAutoUpload = false;
            System.out.println("Problem when load data config");
        }
    }

    /**
     * singleton implementation for this class
     * @return
     */
    public static ApplicationConfiguration load() {
        if (singleton == null) {
            singleton = new ApplicationConfiguration();


        }
        return singleton;


    }

    /**
     * save new configuration to file
     */
    public void saveConfiguration() {
        createDocument();
        createDomTree();
        printToFile();


    }

    /**
     * create document for new configuration
     */
    private void createDocument() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();


        } catch (ParserConfigurationException ex) {
            System.out.println("Error when create document builder");


        }
    }

    /**
     * create dom tree for configuration
     */
    private void createDomTree() {
        Element mapEle = dom.createElement(TAG_PREFERENCES);

        //create nod value
        Element scanningTimeEle = dom.createElement(TAG_SCANNING_TIME);
        scanningTimeEle.setTextContent(scanningTime + "");

        Element scanningFrequencyEle = dom.createElement(TAG_SCANNING_FREQUENCY);
        scanningFrequencyEle.setTextContent(scanningFrequency + "");

        Element numberOfScanningPointEle = dom.createElement(TAG_NUMBER_OF_SCANNING_POINT);
        numberOfScanningPointEle.setTextContent(numberOfScanningPoint + "");

        Element dataFolderEle = dom.createElement(TAG_DATA_FOLDER);
        dataFolderEle.setTextContent(dataFolder);

        Element autoUploadEle = dom.createElement(TAG_IS_AUTO_UPLOAD);
        autoUploadEle.setTextContent(isAutoUpload ? "true" : "false");

        Element algorithmPackagerEle = dom.createElement(TAG_ALGORITHM_PACKAGE);
        algorithmPackagerEle.setTextContent(algorithmPackage);

        Element datatypePackageEle = dom.createElement(TAG_DATA_TYPE_PACKAGE);
        datatypePackageEle.setTextContent(datatypePackage);

        Element datatypepanelPackageEle = dom.createElement(TAG_DATA_TYPE_PANEL_PACKAGE);
        datatypepanelPackageEle.setTextContent(datatypepanelPackage);

        Element serverHostEle = dom.createElement(TAG_SERVER_HOST);
        serverHostEle.setTextContent(serverHost);

        Element localizePortEle = dom.createElement(TAG_LOCALIZE_PORT);
        localizePortEle.setTextContent(localizePort + "");

        Element submitDataPortEle = dom.createElement(TAG_SUBMIT_DATA_PORT);
        submitDataPortEle.setTextContent(submitDataPort + "");

        Element callServerObjectFunctionPortEle = dom.createElement(TAG_CALL_SERVER_OBJECT_PORT);
        callServerObjectFunctionPortEle.setTextContent(callServerObjectFunctionPort + "");

        Element callServerFunctionPortEle = dom.createElement(TAG_CALL_SERVER_FUNCTION_PORT);
        callServerFunctionPortEle.setTextContent(callServerFunctionPort + "");


        //add to preference node
        mapEle.appendChild(scanningTimeEle);
        mapEle.appendChild(scanningFrequencyEle);
        mapEle.appendChild(numberOfScanningPointEle);
        mapEle.appendChild(dataFolderEle);
        mapEle.appendChild(autoUploadEle);
        mapEle.appendChild(algorithmPackagerEle);
        mapEle.appendChild(datatypePackageEle);
        mapEle.appendChild(datatypepanelPackageEle);
        mapEle.appendChild(serverHostEle);
        mapEle.appendChild(localizePortEle);
        mapEle.appendChild(submitDataPortEle);
        mapEle.appendChild(callServerObjectFunctionPortEle);
        mapEle.appendChild(callServerFunctionPortEle);

        dom.appendChild(mapEle);


    }

    /**
     * write new configuration to file, finish saving
     */
    private void printToFile() {
        try {
            //print
            OutputFormat format = new OutputFormat(dom);
            format.setIndenting(true);
            //to generate output to console use this serializer
            //XMLSerializer serializer = new XMLSerializer(System.out, format);
            //to generate a file output use fileoutputstream instead of system.out
            XMLSerializer serializer = new XMLSerializer(
                    new FileOutputStream(configurationFile), format);

            serializer.serialize(dom);

        } catch (IOException ie) {
        }
    }
}
