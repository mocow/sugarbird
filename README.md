The Sugarbird application is created using JAVA programming language.

The following tools are required to compile and run the application

1. JDK1.7.0_80 or higher

The application is executed by running the following class:

1. com.sugarbird.assessment.ApplicationMain


The application supports two output formats namely: Console output and File output. By default, the results are populated into a file named sugarbird_assessment.txt found in the root directory of the application. 

To output the results into a console, System Java property 'output' should be set to 'console' during runtime. Below is the sample command which shows how to change the default values used by the application.

1. java -cp "bin/" -Doutput=console -Dnectar.quantity=11 -Dflowers.count=15 com.sugarbird.assessment.ApplicationMain

The command above will run the application with 15 flowers for the bird to feed on.Each flower will have a nectar quantity of 11. The application will populate the results on the console.


Testing

The jUnits for each test have been created. They are located under test/junit directory



