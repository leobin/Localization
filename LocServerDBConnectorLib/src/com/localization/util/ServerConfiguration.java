/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.util;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class ServerConfiguration {

    private static final String TAG_MYSQL_URL 			= "url";
    private static final String TAG_MYSQL_USERNAME 		= "username";
    private static final String TAG_MYSQL_PASSWORD 		= "password";
    private static final String TAG_MYSQL_DRIVER 		= "driver";
    private static final String TAG_PREFERENCES = "preferences";

	private static ServerConfiguration singleton;
    public String url;
    public String username;
    public String password;
    public String driver;
    private File configurationFile = new File("preferences.xml");
    private Document dom;

    private ServerConfiguration() {
        try {
            //ErrorLog.writeToLog(file.getAbsolutePath().toString());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = (Document) db.parse(configurationFile);

            Node node = doc.getElementsByTagName(TAG_MYSQL_URL).item(0).getChildNodes().item(0);
            this.url = node.getNodeValue().trim();
            node = doc.getElementsByTagName(TAG_MYSQL_USERNAME).item(0).getChildNodes().item(0);
            this.username = node.getNodeValue().trim();
            node = doc.getElementsByTagName(TAG_MYSQL_PASSWORD).item(0).getChildNodes().item(0);
            this.password = node.getNodeValue().trim();
            node = doc.getElementsByTagName(TAG_MYSQL_DRIVER).item(0).getChildNodes().item(0);
            this.driver = node.getNodeValue().trim();
        } catch (Exception e) {
        	this.url ="jdbc:mysql://localhost:3306/localization_database_v4";
        	this.username = "root";
        	this.password = "";
        	this.driver = "com.mysql.jdbc.Driver";
        }
        
    }

    /**
     * singleton implementation for this class
     * @return
     */
    public static ServerConfiguration load() {
        if (singleton == null) {
            singleton = new ServerConfiguration();
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

        Element mysqlURLEle = dom.createElement(TAG_MYSQL_URL);
        mysqlURLEle.setTextContent(url);
        Element mysqlUsernameEle = dom.createElement(TAG_MYSQL_USERNAME);
        mysqlUsernameEle.setTextContent(url);
        Element mysqlPasswordEle = dom.createElement(TAG_MYSQL_PASSWORD);
        mysqlPasswordEle.setTextContent(url);
        Element mysqlDriverEle = dom.createElement(TAG_MYSQL_DRIVER);
        mysqlPasswordEle.setTextContent(url);

        mapEle.appendChild(mysqlURLEle);
        mapEle.appendChild(mysqlUsernameEle);
        mapEle.appendChild(mysqlPasswordEle);
        mapEle.appendChild(mysqlDriverEle);
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
