package org.flywind.widgets.test.pages;

import org.flywind.widgets.test.base.AppBase;

public class FArtDialogTest extends AppBase {

	public void afterRender(){
		javaScriptSupport.require("init-fartdialogtest");
	}
}
