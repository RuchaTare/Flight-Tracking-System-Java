# F21AS-Stage2
This repository is for Advanced Software Engineering

## Purpose
* This report is intended to understand and document flight tracking system application created to track flight from different airlines.
* In the main branch, which is stage 1 of this project:
    * User will be able to add a new flight plan from source to destination that can have upto 5 stops in its plan.
    * The application will calculate the position of flight at given distance and time. Along with that, application also calculates the total distance covered  by the flight, total fuel consumed, total duration of travel from source to destination, and estimated CO2 emissions and will display it on the interface
    * At the end, when the user clicks exit, a text report will be generated which shows cumulative data for all airlines flew on that day with the statistics (total fuel consumed, total duration of travel from source to destination, and estimated CO2 emissions)
    * We followed Waterfall iterative modelling for project Development
* In the stage 2 branch, which is extension to the earlier project:
    * In this iteration, Along with the Stage 1 functionality, Flight will inform about their location on fixed intervals and the status of flight, fuel consumption, Co2 emissions, duration and distance travelled will also be updated at fixed intervals to know current situation and stats of the flight and will be displayed on UI
    * A logger file captures all the events changes into a text file.
    * We have followed Agile Methodologies in this Stage
 
## Pre-requisites
This application was developed on:
* Java 17.0.2 2022-01-18 LTS
* Java(TM) SE Runtime Environment (build 17.0.2+8-LTS-86)
## Code Execution

## Dependencies

## File Details
* Data Files:
    * listofairlines.txt - Includes list of airline code and airline Names 
    * listofairports.txt - Includes airports codes, airport names and their GPS coordinates
    * listofflights.txt -  Includes flight code, Flight operator names, source , destination , start date , start time and flight plan. This file will be written from the GUI as user adds a new flight.
    * listofplanes.txt - Includes model , manufacturer, speed of the flight and fuel consumption of the flight per 100 Kms

* Reports created:
    * Reportfile - Includes cumulative data of all the airline operators flew that day in the below format - 
        * Airline: Japan Air System
        * Code: JD
        * Number of Flights: 1
        * Total Distance: 13887.0
        * Total Fuel Consumption: 106044.0
        * Total Emissions: 267230.0 
    * Logger File - Includes Logs of status changes in the flights. Sample data as below 
        * ON-FLIGHT: 
            Flight-Number: AI5677, Current-Position: GPS [lat = 21.77, lon = 39.10], Distance-Travelled: 13.333333333333334, Flight-Duration: 0 hours : 1 minutes, Control-Tower: EDI, Status: In flight
        * NEW-FLIGHT-ADDED-TO-SCHEDULE: 
            BA8288; British Airways; B747-B; EDI; LHR; 2022-04-14; 12:00:00; EDI; LHR

## Who do I talk to ?
* This repository is maintained by
    * Rucha 
    * Khumiso
    * Kago
    * Goabaone  