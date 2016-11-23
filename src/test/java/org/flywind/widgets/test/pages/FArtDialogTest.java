package org.flywind.widgets.test.pages;

import org.apache.tapestry5.annotations.Import;
import org.flywind.widgets.test.base.AppBase;

@Import(stylesheet="context:assets/styles/demo.css")
public class FArtDialogTest extends AppBase {

	public void afterRender(){
		javaScriptSupport.require("init-fartdialogtest");
	}
}
