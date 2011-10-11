/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;


import com.localization.application.LocalizationClient;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import localization.data.entity.contentobject.LocationDataObject;

/**
 *
 * @author leobin
 */
public class TreeViewEvent implements TreeSelectionListener {

    JTree tree;
    boolean checkClhange = true;
    private LocalizationClient parentPanel = null;

    public TreeViewEvent(LocalizationClient aThis, JTree tree) {
        this.parentPanel = aThis;
        this.tree = tree;
    }

    public void valueChanged(TreeSelectionEvent e) {
        //Returns the last path element of the selection.
        //This method is useful only when the selection model allows a single selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        LocationDataObject location = (LocationDataObject) node.getUserObject();
        if (parentPanel != null){
            this.parentPanel.updateLocationByTree(location);
        } else {

        }
    }
}
