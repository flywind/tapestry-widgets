# Tapestry 5.4.* integration jQuery component - 1.0

## Demo and documentation
http://www.flywind.org/en/tapestryzzl

Tapestry5 programming technology exchange:QQ group 41138107

Exclusive jQuery components : 

- **FAjaxUpload** 
	- based on [https://github.com/FineUploader/fine-uploader](https://github.com/FineUploader/fine-uploader)	
- **FArtDialog,FArtDialogClose,FArtDialogLink**
	- Based on [http://demo.jb51.net/js/2011/artDialog/_doc/labs.html](http://demo.jb51.net/js/2011/artDialog/_doc/labs.html)
- **FCharacter**
	- Based on [http://www.flywind.org/en/start](http://www.flywind.org/en/start)
- **FDatagrid,FSubgrid,FDatagridBtn,FTabs**
	- based on [http://www.jeasyui.com/documentation/index.php](http://www.jeasyui.com/documentation/index.php)
- **FPaginate** 
	- based on [https://github.com/lyonlai/bootstrap-paginator](https://github.com/lyonlai/bootstrap-paginator)

    
    
		
Exclusive jQuery Mixins :

- **Editor** 
	- based on [http://xheditor.com/](http://xheditor.com/)
- **FConfirm** 
	- Based on [http://www.flywind.org/en/start](http://www.flywind.org/en/start)
- **FDatetimepicker**
	- Based on [http://xdsoft.net/jqplugins/datetimepicker/](http://xdsoft.net/jqplugins/datetimepicker/)
- **FNumberSpinner**
	- Based on [http://www.jeasyui.com/documentation/index.php](http://www.jeasyui.com/documentation/index.php)



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
	- 1,Import free icons styles into widgets component module.For example: font awesome, ion icons, themify icons.



## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information. 

## List Of Contributors
* Contributors: [https://github.com/flywind](https://github.com/flywind)
