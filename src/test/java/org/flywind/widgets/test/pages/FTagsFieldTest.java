package org.flywind.widgets.test.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

@Import(stylesheet="context:assets/styles/demo.css")
public class FTagsFieldTest {

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String tags;
}
