# Tapestry 5.4.* integration jQuery component - 1.0

## Demo and documentation
http://www.flywind.org/en/tapestryzzl

## Tapestry widgets component cool style,you can see tapestry cms
CMS: http://cms.flywind.org/en/login
User name: ===`admin`
Password: ===`123456`


Tapestry5 programming technology exchange:QQ group 41138107

Exclusive jQuery components : 

- **FAjaxUpload** 
	- based on [https://github.com/FineUploader/fine-uploader](https://github.com/FineUploader/fine-uploader)	
	- [View demo](http://www.flywind.org/en/blogdetial/222)
- **FArtDialog,FArtDialogClose,FArtDialogLink**
	- Based on [http://demo.jb51.net/js/2011/artDialog/_doc/labs.html](http://demo.jb51.net/js/2011/artDialog/_doc/labs.html)
	- [View demo](http://www.flywind.org/en/blogdetial/224)
- **FCharacter**
	- Based on [http://www.flywind.org/en/start](http://www.flywind.org/en/start)
	- [View deom](http://www.flywind.org/en/blogdetial/221)
- **FDatagrid,FSubgrid,FDatagridBtn,FTabs**
	- based on [http://www.jeasyui.com/documentation/index.php](http://www.jeasyui.com/documentation/index.php)
	- [View deon](http://www.flywind.org/en/blogdetial/227)
- **FPaginate** 
	- based on [https://github.com/lyonlai/bootstrap-paginator](https://github.com/lyonlai/bootstrap-paginator)
	- [View deom](http://www.flywind.org/en/blogdetial/226)
- **FBootstrapTable,FBootstrapTableBtn** 
	- based on [http://bootstrap-table.wenzhixin.net.cn/](http://bootstrap-table.wenzhixin.net.cn/)
    - [View demo](http://www.flywind.org/en/blogdetial/238)

		
Exclusive jQuery Mixins :

- **Editor** 
	- based on [http://xheditor.com/](http://xheditor.com/)
	- [View demo](http://www.flywind.org/en/blogdetial/231)
- **FConfirm** 
	- Based on [http://www.flywind.org/en/start](http://www.flywind.org/en/start)
	- [View deom](http://www.flywind.org/en/blogdetial/228)
- **FDatetimepicker**
	- Based on [http://xdsoft.net/jqplugins/datetimepicker/](http://xdsoft.net/jqplugins/datetimepicker/)
	- [View demo](http://www.flywind.org/en/blogdetial/229)
- **FNumberSpinner**
	- Based on [http://www.jeasyui.com/documentation/index.php](http://www.jeasyui.com/documentation/index.php)
	- [View demo](http://www.flywind.org/en/blogdetial/243)
- **FSummernote**
	- Based on [http://summernote.org/](http://summernote.org/)
	- [View demo](http://www.flywind.org/en/blogdetial/243)
- **Ftagsinput**
	- Based on [http://bootstrap-tagsinput.github.io/bootstrap-tagsinput/examples/](http://bootstrap-tagsinput.github.io/bootstrap-tagsinput/examples/)
	- [View demo](http://www.flywind.org/en/blogdetial/241)
- **FChosen**
	- Based on [https://harvesthq.github.io/chosen/options.html](https://harvesthq.github.io/chosen/options.html)
	- [View demo](http://www.flywind.org/en/blogdetial/240)



## How to use it

Use maven install tapestry-widgets-1.0.jar, click here to download [https://github.com/flywind/tapestry-widgets/tree/master/componentJars](https://github.com/flywind/tapestry-widgets/tree/master/componentJars)

	mvn install:install-file -DgroupId=org.flywind -DartifactId=tapestry-widgets -Dpackaging=jar -Dversion=1.0 -DgeneratePon=true -Dfile=d:/tapestry-widgets-1.0.jar

Just  add the following dependency in your `pom.xml`.
	
For Tapestry 5.4 users:

	<dependencies>
		...
		<dependency>
		  <groupId>org.flywind</groupId>
		  <artifactId>tapestry-widgets</artifactId>
		  <version>1.0</version>
		</dependency>
		...
	</dependencies>

Then use components like you would normally do. For FDatagrid use "widgets" namespace:
 
	<t:widgets.FDatagrid />


# Changelog related to Tapestry 5.4.x branch
- 1.0 : Update to Tapestry 5.4.x & Upgrade libs
- 1.0.1
	- Import free icons styles into widgets component module.For example: font awesome, ion icons, themify icons.
	- Add Bootstrap table and WYSIWYG Summernote Editor componenta.
	- Add Ftagsinput and FChosen components.
	- Since 1.0.1 WidgetModule not add javascript infrastructure provider.Please add jquery in your Project AppModule, like this: configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");



## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information. 

## List Of Contributors
* Contributors: [https://github.com/flywind](https://github.com/flywind)
