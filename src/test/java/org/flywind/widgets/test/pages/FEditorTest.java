package org.flywind.widgets.test.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.flywind.widgets.WidgetSymbolConstants;


public class FEditorTest {
	@Property
	private String content;
	/*@Inject
	private Block contentBlock;

	@Property
	private String content;
	
	@Persist(PersistenceConstants.FLASH)
    @Property
    private UploadedFile uploadedFile;
	
	@OnEvent(component = "content", value = WidgetSymbolConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile; 
    }*/

	/*@OnEvent(component = "form", value = EventConstants.SUCCESS)
	void formSuccess() {
		//ajaxBehavior.showDialog("dialog", "富文本内容", contentBlock, null);
		System.out.println(contentBlock);
	}*/

}
