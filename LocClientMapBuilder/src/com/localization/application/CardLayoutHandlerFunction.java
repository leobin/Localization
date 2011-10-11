/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.application;

import com.localization.application.panel.LocalizationPanel;
import com.localization.application.panel.LocationManagementPanel;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author leobin
 */
public class CardLayoutHandlerFunction {


	private static JPanel panelMain  = null;


	public static void setPanelMain(JPanel panelMain){
		CardLayoutHandlerFunction.panelMain = panelMain;
	}
	/**
	 * change a card view for a specifc cardlayout panel
	 * @param cardName
	 * @param panelMain
	 */
	public static void changeCardView(String cardName) {
		CardLayout cardLayout = (CardLayout) panelMain.getLayout();
                //auto close datatype dialog when change view
                if (cardName != LocalizationMain.PANEL_LOCATION_MANAGEMENT){
                    try {
                        LocationManagementPanel.datatypeDialog.dispose();
                    } catch (Exception ex){
                        
                    }
                }
		cardLayout.show(panelMain, cardName);
		Component[] components = panelMain.getComponents();

		//update card view reload page
		for (int i = 0; i < components.length; i++) {
			LocalizationPanel component = (LocalizationPanel) components[i];
			//reload component
			if (component.isVisible()) {
				component.reload();
			}
		}
	}
}
