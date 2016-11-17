package org.flywind.widgets.test.pages;

import java.util.List;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.entities.example.Example;

public class FDatagridTest2 extends AppBase {
	
	/*@Property
	@PageActivationContext
	private Long id;*/
	
	/*@Property
	private String v;
	
	@Property
	@Persist
	private List<String> list;
	
	void onActivate(List<String> l){
		list = l;
	}*/
	
	private enum Mode {
        CREATE, REVIEW, UPDATE;
    }
	
	@Property
    private Mode editorMode;
	
	 @Property
	 private Long editorExampleId;
	 
	 @Property
	 private Example example;
	 
	 /*public void onShow(){
		 editorMode = Mode.CREATE;
	 }*/
	 
	 void setupRender(){
		 if(editorMode == Mode.REVIEW){
			 if(editorExampleId == null){
				 example = null;
			 }else{
				 if(example == null){
					 //根据exampleId查询
				 }
			 }
		 }
	 }
	
	void onActivate(EventContext ec){
		if(ec.getCount() == 0) {
            editorMode = null;
            editorExampleId = null;
        }
        else if (ec.getCount() == 1) {
            editorMode = ec.get(Mode.class, 0);
            editorExampleId = null;
        }
        else {
            editorMode = ec.get(Mode.class, 0);
            editorExampleId = ec.get(Long.class, 1);
        }
		System.out.println(ec);
	}
	
	Object[] onPassivate() {

        if (editorMode == null) {
            return null;
        }
        else if (editorMode == Mode.CREATE) {
            return new Object[] { editorMode };
        }
        else if (editorMode == Mode.REVIEW || editorMode == Mode.UPDATE) {
            return new Object[] { editorMode, editorExampleId };
        }
        else {
            throw new IllegalStateException(editorMode.toString());
        }

    }
	
	public void onCreate(){
		editorMode = Mode.CREATE;
		editorExampleId = null;
	}
	
	public void onView(Long id){
		editorMode = Mode.REVIEW;
		editorExampleId = id;
	}
	
	public void onUpdate(Long id){
		editorMode = Mode.UPDATE;
		editorExampleId = id;
	}
	
	public boolean isModeCreate() {
		return editorMode == Mode.CREATE;
	}

    public boolean isModeReview() {
        return editorMode == Mode.REVIEW;
    }

    public boolean isModeUpdate() {
        return editorMode == Mode.UPDATE;
    }

}
