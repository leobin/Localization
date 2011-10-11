/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BrowseAlgorithmPanel.java
 *
 * Created on Feb 10, 2011, 10:19:34 PM
 */
package com.localization.application.panel;

import com.localization.application.panel.item.AlgorithmPanel;
import com.localization.manager.AlgorithmDataManagement;
import java.util.ArrayList;
import localization.data.entity.contentobject.AlgorithmDataObject;

/**
 *
 * @author leobint
 */
public class BrowseAlgorithmPanel extends LocalizationPanel {
    public static boolean isEdited;

    /** Creates new form BrowseAlgorithmPanel */
    public BrowseAlgorithmPanel() {
        initComponents();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        panelListAlgorithm = new javax.swing.JPanel();

        jLabel1.setText("Browse Algorithm");

        panelListAlgorithm.setLayout(new javax.swing.BoxLayout(panelListAlgorithm, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(panelListAlgorithm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(191, 191, 191))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelListAlgorithm;
    // End of variables declaration//GEN-END:variables

    @Override
    public void reload() {
        ArrayList<AlgorithmDataObject> allAlgorithm = AlgorithmDataManagement.getAllAlgorithm();
        System.out.println(panelListAlgorithm.getComponents().length);
        System.out.println(allAlgorithm.size());
        if (panelListAlgorithm.getComponents().length < allAlgorithm.size()) {
            for (int j = panelListAlgorithm.getComponents().length; j < allAlgorithm.size(); j++) {
                AlgorithmDataObject algorithm = allAlgorithm.get(j);
                AlgorithmPanel algorithmPanel = new AlgorithmPanel(algorithm);
                algorithmPanel.setVisible(true);
                panelListAlgorithm.add(algorithmPanel);
            }
        } else {
            if (panelListAlgorithm.getComponents().length > allAlgorithm.size()) {
                for (int j = 0; j < panelListAlgorithm.getComponents().length; j++) {
                    AlgorithmPanel algorithmPanel = (AlgorithmPanel) panelListAlgorithm.getComponent(j);
                    if (algorithmPanel.isRemove) {
                        panelListAlgorithm.remove(j);
                    }
                }
            }
        }

        if (isEdited) {
            for (int j = 0; j < panelListAlgorithm.getComponents().length; j++) {
                AlgorithmPanel AlgorithmPanel = (AlgorithmPanel) panelListAlgorithm.getComponent(j);
                AlgorithmPanel.reload();
            }
            isEdited = false;
        }
        updateView();
    }

    /**
     * init other components
     */
    private void initOtherComponents() {
        try {
            ArrayList<AlgorithmDataObject> allAlgorithm = AlgorithmDataManagement.getAllAlgorithm();

            for (int i = 0; i < allAlgorithm.size(); i++) {
                AlgorithmDataObject algorithm = allAlgorithm.get(i);
                AlgorithmPanel algorithmPanel = new AlgorithmPanel(algorithm);
                algorithmPanel.setVisible(true);
                panelListAlgorithm.add(algorithmPanel);
            }
            updateView();
        } catch (Exception e) {
            System.out.println("Problem in loading init browse algorithm form");
        }
    }

    private void updateView() {
        panelListAlgorithm.invalidate();
        panelListAlgorithm.updateUI();
    }
}
