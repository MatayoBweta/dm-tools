/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.ui;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Matayo Bweta Doudoux Stanyslas
 */
public class CustomMigLayout {

    public static final MigLayout ADD_REMOVE_LAYOUT = new MigLayout("inset 10,gapx 5lp, wrap 3,alignx center, hidemode 2,nocache", "[min:100:400,grow 35,fill]5lp[40lp,fill]5lp[min:100:400,grow 65,fill]", "");
    public static final MigLayout BUTTON_FLOWY_LAYOUT = new MigLayout("inset 10,flowy,aligny center, hidemode 2,nocache", "[grow,center][]", "");
    public static final MigLayout FLOWY_LAYOUT = new MigLayout("inset 10,flowy, hidemode 2,nocache", "[grow,center][]", "");
    public static final MigLayout PANEL_2COLUMN_LAYOUT = new MigLayout("inset 10,gapx 5lp, wrap 6,alignx center, hidemode 2,nocache", "[align label]4lp[][min:100:400,grow 50,fill,sg 1]5lp[align label]4lp[][min:100:400,grow 50,fill,sg 1]", "");
    public static final MigLayout PANEL_2COLUMN_LAYOUT_CENTERED = new MigLayout("inset 10,gapx 5lp, wrap 6,align center,hidemode 2,nocache", "[align label]4lp[][min:100:400,grow 50,fill,sg 1]5lp[align label]4lp[][min:100:400,grow 50,fill,sg 1]", "");

}
