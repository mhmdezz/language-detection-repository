

Language Detector Application
------------------------------

A prototype application for identifying languages after learning these languages dynamically through sample files. 
The algorism is based on the research paper: 
	N-Gram-Based Text Categorization
	William B. Cavnar and John M. Trenkle
	Environmental Research Institute of Michigan
	P.O. Box 134001
	Ann Arbor MI 48113-4001


-Running the application by:
	
	maven command:
		$ mvn spring-boot:run

	-Access the application through the local link:
		http://localhost:3001/languagedetector/index.html
		
-Language samples folder:
		src/main/resources/language_samples/

-Configurations can be edited through: 
		config.properties

-To add new language:
		
		-Add new text files with naming convention: LANGUAGE.[n].txt
		-Fill in the text files with texts from the new language
		-Start the application
		-The new language will be updated in the Available Languages list in the home page
		 
		 
-The project technologies:
  
    -Java 8
    -Spring IoC, Spring boot
    -AngularJS
    -JUnit, Mockito
