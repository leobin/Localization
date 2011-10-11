/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DataManagementPanel.java
 *
 * Created on Feb 20, 2011, 7:37:25 PM
 */
package com.localization.application.panel.forframe;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import localization.data.entity.contentobject.DataTypeDataObject;
import locationaware.client.ClientMapDataSender;
import locationaware.clientserver.MapData;
import locationaware.eventlistener.ChangeMacEventListener;
import locationaware.myevent.ChangeMacEvent;

import com.localization.application.LocalizationMain;
import com.localization.application.panel.LocationManagementPanel;
import com.localization.datatype.panel.DataTypeAbstractPanel;
import com.localization.other.ApplicationConfiguration;
import com.localization.server.CommonConfig;

/**
 *
 * @author leobin
 */
public class DataManagementPanel extends javax.swing.JPanel {

    private JDialog parentDialog;
    private DataTypeDataObject datatype;
    private String datatypeClassPath;

    /** Creates new form DataManagementPanel */
    public DataManagementPanel(JDialog parentDialog) {
        this.parentDialog = parentDialog;
        initComponents();
        initOtherComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelDataType = new javax.swing.JLabel();
        panelScrollContainer = new javax.swing.JScrollPane();
        panelContainer = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelLocationName = new javax.swing.JLabel();
        labelUserName = new javax.swing.JLabel();
        uploadDataJButton = new javax.swing.JButton();
        uploadStatusjLabel = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Data Management Panel");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Data Type:");

        panelContainer.setLayout(new javax.swing.BoxLayout(panelContainer, javax.swing.BoxLayout.PAGE_AXIS));
        panelScrollContainer.setViewportView(panelContainer);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Location:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("User:");

        uploadDataJButton.setFont(new java.awt.Font("Tahoma", 1, 11));
        uploadDataJButton.setText("Upload Data");
        uploadDataJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadDataJButtonActionPerformed(evt);
            }
        });

        uploadStatusjLabel.setText("Upload success");
        uploadStatusjLabel.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(140, 140, 140))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(labelLocationName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(uploadDataJButton)
                .addContainerGap(74, Short.MAX_VALUE))
            .addComponent(panelScrollContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(uploadStatusjLabel))
                    .addComponent(labelDataType, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelLocationName, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uploadDataJButton)))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(uploadStatusjLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(labelDataType, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(panelScrollContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void uploadDataJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadDataJButtonActionPerformed
        String fileMapDataPath = ApplicationConfiguration.load().dataFolder + "/" + LocationManagementPanel.selectedLocation.getLocationId() + "_" + LocalizationMain.loginUser.getUserId() + "_" + datatype.getDataTypeId() + CommonConfig.extensionMapDataFile;
        File fileMapData = new File(fileMapDataPath);

        if (fileMapData.exists()) {
            MapData mapData = MapData.readMapData(fileMapDataPath);
            ClientMapDataSender sender = new ClientMapDataSender();

            sender.setPort(ApplicationConfiguration.load().submitDataPort);
            sender.setServerHost(ApplicationConfiguration.load().serverHost);

            sender.addChangeMacEventListener(new ChangeMacEventListener() {

                public void handleEvent(ChangeMacEvent cme) {
                    //default icon, custom title
                    ClientMapDataSender sender = (ClientMapDataSender) cme.getSource();

                    int n = JOptionPane.showConfirmDialog(
                        parentDialog,
                        "You built map with new device. Would you like to delete all map data collected by old device?",
                        "Change MAC Address",
                        JOptionPane.YES_NO_OPTION);

                    if (n == JOptionPane.YES_OPTION) {
                        sender.setReplaceRootMACIfDif(true);
                    } else if (n == JOptionPane.NO_OPTION){
                        sender.setReplaceRootMACIfDif(false);
                    }
                }
            });

            if (sender.sendMapData(mapData)) {
                uploadStatusjLabel.setVisible(true);
                uploadStatusjLabel.setText("Upload success");
            } else {
                uploadStatusjLabel.setVisible(true);
                uploadStatusjLabel.setText("Upload fail");
            }
        } else {
            uploadStatusjLabel.setVisible(true);
            uploadStatusjLabel.setText("Upload fail due to data folder doesn't exist");
        }
    }//GEN-LAST:event_uploadDataJButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel labelDataType;
    private javax.swing.JLabel labelLocationName;
    private javax.swing.JLabel labelUserName;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JScrollPane panelScrollContainer;
    private javax.swing.JButton uploadDataJButton;
    private javax.swing.JLabel uploadStatusjLabel;
    // End of variables declaration//GEN-END:variables

    private void initOtherComponents() {
        if (LocationManagementPanel.treeRootLocation == null) {
            parentDialog.dispose();
        } else {
            try {
                datatype = LocationManagementPanel.treeRootLocation.getAlgorithm().getDataType();
                System.out.println(datatype.getDataTypeClassName());
                labelDataType.setText(datatype.getDataTypeName());
                labelLocationName.setText(LocationManagementPanel.selectedLocation.getLocationName());
                labelUserName.setText(LocationManagementPanel.selectedLocation.getUser().getUserName());
                datatypeClassPath = ApplicationConfiguration.load().datatypepanelPackage + "."+ datatype.getDataTypeName() + "Panel";
                Class dataTypePanelClass = Class.forName(datatypeClassPath);
                DataTypeAbstractPanel datatypePanel = (DataTypeAbstractPanel) dataTypePanelClass.getConstructor(null).newInstance(null);
                datatypePanel.initPanel(this.parentDialog);
                datatypePanel.setVisible(true);
                panelContainer.add(datatypePanel);
                this.updateView();
            } catch (Exception ex) {
                System.out.println("Problem for loading class.");
                parentDialog.dispose();
            }

        }
    }

    private void updateView(){
        panelContainer.updateUI();
        panelContainer.invalidate();
        panelScrollContainer.updateUI();
        panelScrollContainer.invalidate();
        this.updateUI();
        this.invalidate();
    }
}
