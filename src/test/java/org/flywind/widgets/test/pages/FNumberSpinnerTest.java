package org.flywind.widgets.test.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
@Import(stylesheet="context:assets/styles/demo.css")
public class FNumberSpinnerTest {

	@Property
	private int num = 10;
}
