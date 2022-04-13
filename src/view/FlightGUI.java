package view;

import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.ControllerFlightGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Array;
import java.util.ArrayList;
import java.lang.*;

public class FlightGUI extends JFrame implements ActionListener {
    //classes used
    private ListOfFlights listOfFlights;
    private ListOfAirlines listOfAirlines;
    private ListOfAirports listOfAirports;
    private ListOfAeroplanes listOfAeroplanes;
    private Flight flight;
    private ControlTower controlTower;

    //Components
    JLabel lblFlights, lblFlightPlanTitle, lblDistance, lblTime, lblKilometere, lblFuelConsumption, lblLitre;
    JLabel lblCO2, lblKilogram, lblAddFlight, lblFlightPlan, lblAirline, lblNumber, lblPlane, lblDeparture, lblDestination, lblDate, lblTimeAddFlight;
    JTextField txtDistance, txtTime, txtFuelConsumption, txtEmissions, txtNumber, txtDate, txtTimeAddFlight;
    JList listFlightPlanList;

    //PANELS
    JPanel flightPanel;

    JTable flightTable;
    JComboBox<String> airlineComboBox = new JComboBox<String>();
    JComboBox<String> airportComboBox1 = new JComboBox<String>();
    JComboBox<String> airportComboBox2 = new JComboBox<String>();
    JComboBox<String> airportComboBox3 = new JComboBox<String>();
    JComboBox<String> airportComboBox4 = new JComboBox<String>();
    JComboBox<String> airportComboBox5 = new JComboBox<String>();
    JComboBox<String> planeComboBox = new JComboBox<String>();
    JComboBox<String> departureComboBox = new JComboBox<String>();
    JComboBox<String> destinationComboBox = new JComboBox<String>();
    JButton btnAdd, btnCancel, btnExit;

    public FlightGUI(ListOfFlights listOfFlights, ListOfAirlines listOfAirlines, ListOfAirports listOfAirports, ListOfAeroplanes listOfAeroplanes)
    {
        this.listOfFlights = listOfFlights;
        this.listOfAirlines = listOfAirlines;
        this.listOfAirports = listOfAirports;
        this.listOfAeroplanes = listOfAeroplanes;


        setTitle("Flight Tracking System"); //set up window title
        setDefaultCloseOperation(EXIT_ON_CLOSE); //exit out of application
        pack(); //pack and set visible
        setLocation(0, 0);
        setSize(1400, 850);
        getContentPane().setLayout(null);
        ImageIcon image = new ImageIcon("src/flightTrack.png"); // create image icon
        setIconImage(image.getImage());

        setUpFlightPanel();
        setUpFlightPlanPanel();
        setUpDetailsPanel();
        setUpAddFlightTitlePanel();
        setUpAddFlightPanelLabels();
        setUpAddFlightPanel();
        setUpAddFlightPlanTitlePanel();
        setUpAddFlightPlanPanel();
        setUpButtonsPanel();

        setVisible(true); //make the frame visible


//       getContentPane().setBackground(new Color(123, 50, 250)); getContentPane().setFont(new Font("Lucida Grande", Font.PLAIN, 14));




    }

//    THE FLIGHT LIST DISPLAY

    private void setUpFlightPanel(){
        JPanel flightPanel = new JPanel();
        flightPanel.setBackground(Color.cyan);
        flightPanel.setBounds(0,0, 880, 350);
        flightPanel.setLayout(null);

        lblFlights = new JLabel("FLIGHTS");
        lblFlights.setBounds(0, 0, 100, 30);
        flightPanel.add(lblFlights);

        flightTable = new JTable();
        DefaultTableModel dm = new DefaultTableModel(0,0);
        String[] columnNames = {"Flight", "Plane", "Departure", "Destination", "Date", "Time", "Latitude", "Longitude", "Status"};
        dm.setColumnIdentifiers(columnNames);

        flightTable.setModel(dm);
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(5,30,870,300);

        //Adds flight details into the flight details, from the flightlist initially created
        DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
        Object rowData[] = new Object[9];
        ArrayList<Flight> list = listOfFlights.flightIterator();
        for(Flight f: list){
            rowData[0] = f.getFlightNumber();
            rowData[1] = f.getAeroplane().getModel();
            rowData[2] = f.getDepartureAirport().getAirportCode();
            rowData[3] = f.getArrivalAirport().getAirportCode();
            rowData[4] = f.getDepartureDate();
            rowData[5] = f.getDepartureTime();
            model.addRow(rowData);
        }

        ListSelectionModel cellSelectionModel = flightTable.getSelectionModel();
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                int viewRow = flightTable.getSelectedRow();

                if (!event.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = flightTable.convertRowIndexToModel(viewRow);
                    Object modelvalue = flightTable.getModel().getValueAt(modelRow, columnIndex);
        
                    rowData[8] = 1;

                }
            }
        });

        flightPanel.add(scrollPane);

        this.add(flightPanel);
    }

    private void setUpFlightPlanPanel(){
        JPanel flightPlanPanel = new JPanel();
        flightPlanPanel.setBounds(890, 0, 240, 320);
        flightPlanPanel.setBackground(Color.red);
        flightPlanPanel.setLayout(null);
        this.add(flightPlanPanel);

        lblFlightPlanTitle = new JLabel("FLIGHT PLAN");
        lblFlightPlanTitle.setBounds(0, 0, 100, 30);
        flightPlanPanel.add(lblFlightPlanTitle);

        // Enables scrolling through the table
        JScrollPane fpPane = new JScrollPane();
        fpPane.setBounds(20, 35, 180, 270);

        flightTable.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = flightTable.getSelectionModel();
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                int viewRow = flightTable.getSelectedRow();

                if (!event.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = flightTable.convertRowIndexToModel(viewRow);
                    Object modelvalue = flightTable.getModel().getValueAt(modelRow, columnIndex);

                    String fKey = (String) modelvalue;

                    String v = listOfFlights.get(fKey).getFlightPlan().getFlightPlanList();
                    String fp[] = v.split(" ");

                    listFlightPlanList = new JList(fp);
                    listFlightPlanList.setVisibleRowCount(10);

                    fpPane.setViewportView(listFlightPlanList);
                }

            }
        });

        flightPlanPanel.add(fpPane);
    }

    private void setUpDetailsPanel(){
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBounds(1140, 0, 260, 320);
        detailsPanel.setBackground(Color.ORANGE);
        detailsPanel.setLayout(null);
        this.add(detailsPanel);

        //adding the distance display row
        lblDistance = new JLabel("Distance");
        lblDistance.setBounds(80, 20, 200, 35);
        detailsPanel.add(lblDistance);

        txtDistance = new JTextField();
        txtDistance.setBounds(25, 50, 200, 35);
        txtDistance.setEditable(false);
        ListSelectionModel cellSelectionModel = flightTable.getSelectionModel();
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                int viewRow = flightTable.getSelectedRow();

                if (!event.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = flightTable.convertRowIndexToModel(viewRow);
                    Object modelvalue = flightTable.getModel().getValueAt(modelRow, columnIndex);
                    // Object tablevalue =flightTable.getValueAt(viewRow, columnIndex);
                    String fKey = (String) modelvalue;
                    // String tKey = (String) tablevalue;

                    Flight v = listOfFlights.get(fKey);
                    FlightPlan fl = listOfFlights.get(fKey).getFlightPlan();
                    int i = v.getFlightPlan().getFlightPlanTotalDistance();

                    txtDistance.setText(Integer.toString(i));

                
                SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        
                            
                            ArrayList<Flight> list = listOfFlights.flightIterator();
                            for(Flight f: list){
                                f.start();
                                Thread.sleep(60000);
                                f.interrupt();}
                                
                            
                        return null;
                    }
                    
                };
                worker.execute();

            }}

        });
        detailsPanel.add(txtDistance);

        lblKilometere = new JLabel("km");
        lblKilometere.setBounds(225, 55, 35, 35);
        detailsPanel.add(lblKilometere);

        //adding the time display row and its label
        lblTime = new JLabel("Time");
        lblTime.setBounds(80, 90, 200, 35);
        detailsPanel.add(lblTime);

        txtTime = new JTextField();
        txtTime.setBounds(25, 120, 200, 35);
        txtTime.setEditable(false);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                int viewRow = flightTable.getSelectedRow();

                if (!event.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = flightTable.convertRowIndexToModel(viewRow);
                    Object modelvalue = flightTable.getModel().getValueAt(modelRow, columnIndex);
                    // Object tablevalue =flightTable.getValueAt(viewRow, columnIndex);
                    String fKey = (String) modelvalue;
                    // String tKey = (String) tablevalue;

                    String v = listOfFlights.get(fKey).flightTotalTime();

                    txtTime.setText(v);

                }
            }
        });

        detailsPanel.add(txtTime);

        //adding fuel consumption display row and its labels
        lblFuelConsumption = new JLabel("Fuel Consumption");
        lblFuelConsumption.setBounds(80, 160, 200, 35);
        detailsPanel.add(lblFuelConsumption);

        txtFuelConsumption = new JTextField();
        txtFuelConsumption.setBounds(25, 190, 200, 35);
        txtFuelConsumption.setEditable(false);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                int viewRow = flightTable.getSelectedRow();

                if (!event.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = flightTable.convertRowIndexToModel(viewRow);
                    Object modelvalue = flightTable.getModel().getValueAt(modelRow, columnIndex);
                    // Object tablevalue =flightTable.getValueAt(viewRow, columnIndex);
                    String fKey = (String) modelvalue;
                    // String tKey = (String) tablevalue;

                    double fc = listOfFlights.get(fKey).getTotalFuelConsumption();
                    String fcl = Double.toString(fc);

                    txtFuelConsumption.setText(fcl);

                }
            }
        });

        detailsPanel.add(txtFuelConsumption);

        lblLitre = new JLabel("litres");
        lblLitre.setBounds(225, 190, 35, 35);
        detailsPanel.add(lblLitre);

        //adding co2 emissions display row and its labels
        lblCO2 = new JLabel("CO2 Emissions");
        lblCO2.setBounds(80,230, 200, 35);
        detailsPanel.add(lblCO2);

        txtEmissions = new JTextField();
        txtEmissions.setBounds(25, 260, 200, 35);
        txtEmissions.setEditable(false);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                int viewRow = flightTable.getSelectedRow();

                if (!event.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = flightTable.convertRowIndexToModel(viewRow);
                    Object modelvalue = flightTable.getModel().getValueAt(modelRow, columnIndex);
                    // Object tablevalue =flightTable.getValueAt(viewRow, columnIndex);
                    String fKey = (String) modelvalue;
                    // String tKey = (String) tablevalue;

                    double C = listOfFlights.get(fKey).getCO2Emissions();
                    String str = String.format("%.0f", C);

                    txtEmissions.setText(str);

                }
            }
        });

        detailsPanel.add(txtEmissions);

        lblKilogram = new JLabel("kg");
        lblKilogram.setBounds(225, 260, 35, 35);
        detailsPanel.add(lblKilogram);


    }

    public void setUpAddFlightTitlePanel(){
        JPanel addFlightsTitle = new JPanel();
        addFlightsTitle.setBounds(0, 400, 1000, 30);
        addFlightsTitle.setBackground(Color.cyan);
        addFlightsTitle.setLayout(null);

        lblAddFlight = new JLabel("ADD FLIGHT");
        lblAddFlight.setBounds(0, 0, 150, 20);
        addFlightsTitle.add(lblAddFlight);

        this.add(addFlightsTitle);
    }

    private void setUpAddFlightPanelLabels(){
        JPanel addFlightLabels = new JPanel();
        addFlightLabels.setBounds(0, 430, 1000, 50);
        addFlightLabels.setBackground(Color.cyan);
        addFlightLabels.setLayout(new GridLayout(1, 7));

        lblAirline = new JLabel("Airline");
        lblNumber = new JLabel("Number");
        lblPlane= new JLabel("Plane");
        lblDeparture = new JLabel("Departure");
        lblDestination = new JLabel("Destination");
        lblDate = new JLabel("Date");
        lblTimeAddFlight = new JLabel("Time");

        addFlightLabels.add(lblAirline);
        addFlightLabels.add(lblNumber);
        addFlightLabels.add(lblPlane);
        addFlightLabels.add(lblDeparture);
        addFlightLabels.add(lblDestination);
        addFlightLabels.add(lblDate);
        addFlightLabels.add(lblTimeAddFlight);

        this.add(addFlightLabels);

    }

    private void setUpAddFlightPanel(){
        JPanel addFlightPanel = new JPanel();
        addFlightPanel.setBounds(0, 470, 1000, 50);
        addFlightPanel.setBackground(Color.cyan);
        addFlightPanel.setLayout(new GridLayout(1, 7));

        airlineComboBox = new JComboBox();
        airlineComboBox.addItem("Choose..");
        airlineComboBox.setSelectedItem("Choose..");
        var list = listOfAirlines.airlineIterator();
        for(Airline a: list){
            airlineComboBox.addItem(a.getAirlineName());
        }

        txtNumber = new JTextField();

        planeComboBox = new JComboBox();
        planeComboBox.addItem("Choose..");
        planeComboBox.setSelectedItem("Choose..");
        var listplanes = listOfAeroplanes.aeroplanesIterator();
        for(Aeroplane a: listplanes){
            planeComboBox.addItem(a.getModel());
        }

        departureComboBox = new JComboBox();
        departureComboBox.addItem("Choose..");
        departureComboBox.setSelectedItem("Choose..");

        destinationComboBox = new JComboBox();
        destinationComboBox.addItem("Choose..");
        destinationComboBox.setSelectedItem("Choose..");

        var listdepart = listOfAirports.airportsIterator();
        for(Airport a: listdepart){
            departureComboBox.addItem(a.getAirportCode());
            destinationComboBox.addItem(a.getAirportCode());
        }

        txtDate = new JTextField();
        txtTimeAddFlight = new JTextField();

        addFlightPanel.add(airlineComboBox);
        addFlightPanel.add(txtNumber);
        addFlightPanel.add(planeComboBox);
        addFlightPanel.add(departureComboBox);
        addFlightPanel.add(destinationComboBox);
        addFlightPanel.add(txtDate);
        addFlightPanel.add(txtTimeAddFlight);

        this.add(addFlightPanel);

    }


    public void setUpAddFlightPlanTitlePanel(){
        JPanel addFlightsPlanTitle = new JPanel();
        addFlightsPlanTitle.setBounds(0, 560, 1000, 30);
        addFlightsPlanTitle.setBackground(Color.cyan);
        addFlightsPlanTitle.setLayout(null);

        lblFlightPlan = new JLabel("CHOOSE FLIGHT PLAN");
        lblFlightPlan.setBounds(0, 0, 150, 20);
        addFlightsPlanTitle.add(lblFlightPlan);

        this.add(addFlightsPlanTitle);
    }

    private void setUpAddFlightPlanPanel(){
        JPanel addFlightPlanPanel = new JPanel();
        addFlightPlanPanel.setBounds(0, 590, 1000, 50);
        addFlightPlanPanel.setBackground(Color.cyan);
        addFlightPlanPanel.setLayout(new GridLayout(1,5));

        this.add(addFlightPlanPanel);

        airportComboBox1 = new JComboBox();
        airportComboBox1.addItem("Choose..");
        airportComboBox1.setSelectedItem("Choose..");

        airportComboBox2 = new JComboBox();
        airportComboBox2.addItem("Choose..");
        airportComboBox2.setSelectedItem("Choose..");

        airportComboBox3 = new JComboBox();
        airportComboBox3.addItem("Choose..");
        airportComboBox3.setSelectedItem("Choose..");

        airportComboBox4 = new JComboBox();
        airportComboBox4.addItem("Choose..");
        airportComboBox4.setSelectedItem("Choose..");

        airportComboBox5 = new JComboBox();
        airportComboBox5.addItem("Choose..");
        airportComboBox5.setSelectedItem("Choose..");

        var listair = listOfAirports.airportsIterator();
        for(Airport a: listair){
            airportComboBox1.addItem(a.getAirportCode());
            airportComboBox2.addItem(a.getAirportCode());
            airportComboBox3.addItem(a.getAirportCode());
            airportComboBox4.addItem(a.getAirportCode());
            airportComboBox5.addItem(a.getAirportCode());
        }

        addFlightPlanPanel.add(airportComboBox1);
        addFlightPlanPanel.add(airportComboBox2);
        addFlightPlanPanel.add(airportComboBox3);
        addFlightPlanPanel.add(airportComboBox4);
        addFlightPlanPanel.add(airportComboBox5);

    }

    private void setUpButtonsPanel(){

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0, 740, 1000, 50);
        buttonsPanel.setBackground(Color.cyan);
        buttonsPanel.setLayout(new GridLayout(1,3));

        btnAdd = new JButton("ADD");
        btnAdd.addActionListener(this);

        btnCancel = new JButton("CANCEL");
        btnCancel.addActionListener(this);

        btnExit = new JButton("EXIT");
        btnExit.addActionListener(this);

        this.add(buttonsPanel);
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnCancel);
        buttonsPanel.add(btnExit);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
           AddBtnActionPerformed();
           
        }
        else if (e.getSource() == btnCancel) {
            planeComboBox.setSelectedItem("Choose..");
            txtNumber.setText(null);
            airlineComboBox.setSelectedItem("Choose..");
            departureComboBox.setSelectedItem("Choose..");
            destinationComboBox.setSelectedItem("Choose..");
            txtDate.setText(null);
            txtTimeAddFlight.setText(null);
            airportComboBox1.setSelectedItem("Choose..");
            airportComboBox2.setSelectedItem("Choose..");
            airportComboBox3.setSelectedItem("Choose..");
            airportComboBox4.setSelectedItem("Choose..");
            airportComboBox5.setSelectedItem("Choose..");

            
        }

        else if (e.getSource() == btnExit) {
            ControllerFlightGUI demo = new ControllerFlightGUI();
            demo.createReport();

            System.exit(0);
        }
    }

    // private void addAction() {
    //     //Retrieve values from the text boxes
    //     String date = txtDate.getText();
    //     String time = txtTimeAddFlight.getText();
    //     String flightNo = txtNumber.getText();

    //     String airlineKey = airlineComboBox.getSelectedItem().toString();
    //     Airline airline = listOfAirlines.getListOfAirlines().get(airlineKey);

    //     String departKey = departureComboBox.getSelectedItem().toString();
    //     Airport departure = listOfAirports.getListOfAirports().get(departKey);

    //     String destKey = destinationComboBox.getSelectedItem().toString();
    //     Airport destination = listOfAirports.getListOfAirports().get(destKey);

    //     String planeKey = planeComboBox.getSelectedItem().toString();
    //     Aeroplane plane = listOfAeroplanes.getListOfAeroplanes().get(planeKey);


    //     if (!(flightNo.substring(0, 2).equals(airline.getAirlineCode()))) {
    //         JOptionPane.showMessageDialog(rootPane, "Flight Number must match the airline code");
    //         return;
    //     }
    //     //Check if the departure and destination airports are the same, don't allow users to continue if so
    //     if (departKey.equals(destKey)) {
    //         JOptionPane.showMessageDialog(rootPane, "destination and departure can not be the same");
    //         return;
    //     }
    //     //Check if data and time are empty, if so don't allow users to continue
    //     if (date.equals(null) || time.equals(null)) {
    //         JOptionPane.showMessageDialog(rootPane, "empty field is not allowed");
    //         return;
    //     }
    //     //Make sure data matches a specific format (DD:MM:YYYY)
    //     if (!date.matches("\\d{2}:\\d{2}:\\d{4}")) {
    //         JOptionPane.showMessageDialog(rootPane, "Date does not match dd:mm:yyyy format");
    //         return;
    //     }
    //     //Make sure time matches a specific format (HH:MM)
    //     if (!time.matches("\\d{2}:\\d{2}")) {
    //         JOptionPane.showMessageDialog(rootPane, "Time does not match hh:mm format");
    //         return;
    //     }

    //     var plan = new FlightPlan();

    //     String port1Key = airportComboBox1.getSelectedItem().toString();
    //     Airport t1 = listOfAirports.getListOfAirports().get(port1Key);

    //     String port2Key = airportComboBox2.getSelectedItem().toString();
    //     Airport t2 = listOfAirports.getListOfAirports().get(port2Key);

    //     String port3Key = airportComboBox3.getSelectedItem().toString();
    //     Airport t3 = listOfAirports.getListOfAirports().get(port3Key);

    //     String port4Key = airportComboBox4.getSelectedItem().toString();
    //     Airport t4 = listOfAirports.getListOfAirports().get(port4Key);

    //     String port5Key = airportComboBox5.getSelectedItem().toString();
    //     Airport t5 = listOfAirports.getListOfAirports().get(port5Key);

    //     plan.addToPlan(t1, 0);
    //     plan.addToPlan(t2, 1);
    //     plan.addToPlan(t3, 2);
    //     plan.addToPlan(t4, 3);
    //     plan.addToPlan(t5, 4);

    //     Flight additionalFlight = new Flight(flightNo, airline, plane, departKey, destKey, date, time, plan);

    //     listOfFlights.AddFlight(additionalFlight);

    //     JPanel flightPanel = new JPanel();
    //     flightPanel.setBackground(Color.cyan);
    //     flightPanel.setBounds(0,0, 880, 350);
    //     flightPanel.setLayout(null);

    //     lblFlights = new JLabel("FLIGHTS");
    //     lblFlights.setBounds(0, 0, 100, 30);
    //     flightPanel.add(lblFlights);

    //     flightTable = new JTable();
    //     DefaultTableModel dm = new DefaultTableModel(0,0);
    //     String[] columnNames = {"Flight", "Plane", "Departure", "Destination", "Date", "Time", "Latitude", "Longitude", "Status"};
    //     dm.setColumnIdentifiers(columnNames);

    //     flightTable.setModel(dm);
    //     JScrollPane scrollPane = new JScrollPane(flightTable);
    //     scrollPane.setBounds(5,30,870,300);

    //     //Adds flight details into the flight details, from the flightlist initially created
    //     DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
    //     Object rowData[] = new Object[9];
    //     ArrayList<Flight> list = listOfFlights.flightIterator();
    //     for(Flight f: list){
    //         rowData[0] = f.getFlightNumber();
    //         rowData[1] = f.getAeroplane().getModel();
    //         rowData[2] = f.getDepartureAirport();
    //         rowData[3] = f.getArrivalAirport();
    //         rowData[4] = f.getDepartureDate();
    //         rowData[5] = f.getDepartureTime();

    //         model.addRow(rowData);
    //     }

    //     flightPanel.add(scrollPane);

    //     this.add(flightPanel);


    // }

    
    private void AddBtnActionPerformed() {
        //code to load data into table
        //Store the data inot a new instance of flight
                //Retrieve values from the text boxes
                String date = txtDate.getText();
                String time = txtTimeAddFlight.getText();
                String flightNo = txtNumber.getText();
        
                String airlineKey = airlineComboBox.getSelectedItem().toString();
                // Airline airline = listOfAirlines.getListOfAirlines().get(airlineKey);
        
                String departKey = departureComboBox.getSelectedItem().toString();
                // Airport departure = listOfAirports.getListOfAirports().get(departKey);
        
                String destKey = destinationComboBox.getSelectedItem().toString();
                // Airport destination = listOfAirports.getListOfAirports().get(destKey);
        
                String planeKey = planeComboBox.getSelectedItem().toString();
                // Aeroplane plane = listOfAeroplanes.getListOfAeroplanes().get(planeKey);
        
        
                // if (!(flightNo.substring(0, 2).equals(airline.getAirlineCode()))) {
                //     JOptionPane.showMessageDialog(rootPane, "Flight Number must match the airline code");
                //     return;
                // }
                // //Check if the departure and destination airports are the same, don't allow users to continue if so
                // if (departKey.equals(destKey)) {
                //     JOptionPane.showMessageDialog(rootPane, "destination and departure can not be the same");
                //     return;
                // }
                // //Check if data and time are empty, if so don't allow users to continue
                // if (date.equals(null) || time.equals(null)) {
                //     JOptionPane.showMessageDialog(rootPane, "empty field is not allowed");
                //     return;
                // }
                // //Make sure data matches a specific format (DD:MM:YYYY)
                // if (!date.matches("\\d{2}:\\d{2}:\\d{4}")) {
                //     JOptionPane.showMessageDialog(rootPane, "Date does not match dd:mm:yyyy format");
                //     return;
                // }
                // //Make sure time matches a specific format (HH:MM)
                // if (!time.matches("\\d{2}:\\d{2}")) {
                //     JOptionPane.showMessageDialog(rootPane, "Time does not match hh:mm format");
                //     return;
                // }
        
                // var plan = new FlightPlan();
        
                String port1Key = airportComboBox1.getSelectedItem().toString();
                // Airport t1 = listOfAirports.getListOfAirports().get(port1Key);
        
                String port2Key = airportComboBox2.getSelectedItem().toString();
                // Airport t2 = listOfAirports.getListOfAirports().get(port2Key);
        
                String port3Key = airportComboBox3.getSelectedItem().toString();
                // Airport t3 = listOfAirports.getListOfAirports().get(port3Key);
        
                String port4Key = airportComboBox4.getSelectedItem().toString();
                // Airport t4 = listOfAirports.getListOfAirports().get(port4Key);
        
                String port5Key = airportComboBox5.getSelectedItem().toString();
                // Airport t5 = listOfAirports.getListOfAirports().get(port5Key);
        
                // plan.addToPlan(t1, 0);
                // plan.addToPlan(t2, 1);
                // plan.addToPlan(t3, 2);
                // plan.addToPlan(t4, 3);
                // plan.addToPlan(t5, 4);
        
                // Flight additionalFlight = new Flight(flightNo, airline, plane, departKey, destKey, date, time, plan);
                String output= "\n"+flightNo+"; "+airlineKey+"; "+planeKey+"; "+departKey+"; "+destKey+"; "+date+"; "+time+"; "+port1Key+"; "+port2Key+"; "+port3Key+"; "+port4Key+"; "+port5Key;
                //Only 4 entries in the flight plan
                String output4= "\n"+flightNo+"; "+airlineKey+"; "+planeKey+"; "+departKey+"; "+destKey+"; "+date+"; "+time+"; "+port1Key+"; "+port2Key+"; "+port3Key+"; "+port4Key;
                //Only 3 entries in th flight plan
                String output3= "\n"+flightNo+"; "+airlineKey+"; "+planeKey+"; "+departKey+"; "+destKey+"; "+date+"; "+time+"; "+port1Key+"; "+port2Key+"; "+port3Key;
                //only 2 entries in the flight plan
                String output2= "\n"+flightNo+"; "+airlineKey+"; "+planeKey+"; "+departKey+"; "+destKey+"; "+date+"; "+time+"; "+port1Key+"; "+port2Key;
        
                try
                {
                if  (airportComboBox1.getSelectedItem() =="Choose.."||airportComboBox2.getSelectedItem()=="Choose.."||flightNo==null||airlineKey==null||planeKey==null||departKey==null||destKey==null||date==null||time==null){
                    JOptionPane.showMessageDialog(null, "Please Fill  up all the fields before clicking the \"ADD\" button");
                    throw new IllegalArgumentException("Please add all the flight details required");
        
                }
                  
                else if (airportComboBox1.getSelectedItem() !="Choose.."&&airportComboBox2.getSelectedItem()!="Choose.."&&airportComboBox3.getSelectedItem()!="Choose.."&&airportComboBox4.getSelectedItem()!="Choose.."&&airportComboBox5.getSelectedItem()!="Choose.."){
                    Files.write(Paths.get("src/listofflights.txt"),
                    output.getBytes(), StandardOpenOption.APPEND);

                }
                else if (airportComboBox3.getSelectedItem()=="Choose.."){
                    Files.write(Paths.get("src/listofflights.txt"),
                    output2.getBytes(), StandardOpenOption.APPEND);
                }
                else if (airportComboBox4.getSelectedItem()=="Choose.."){
                    Files.write(Paths.get("src/listofflights.txt"),
                    output3.getBytes(), StandardOpenOption.APPEND);
                }
                else if (airportComboBox5.getSelectedItem()=="Choose.."){
                    Files.write(Paths.get("src/listofflights.txt"),
                    output4.getBytes(), StandardOpenOption.APPEND);
                }
                else if  (airportComboBox1.getSelectedItem() =="Choose.."||airportComboBox2.getSelectedItem()=="Choose.."||flightNo==null||airlineKey==null||planeKey==null||departKey==null||destKey==null||date==null||time==null){
                    Files.write(Paths.get("src/listofflights.txt"),
                    output.getBytes(), StandardOpenOption.APPEND);
                }

               
                    JOptionPane.showMessageDialog(null, " Flight successfully added! ");
                    
                    // clear fields for new entry
                    txtTimeAddFlight.setText(null);
                    airportComboBox1.setSelectedItem("Choose..");
                    airportComboBox2.setSelectedItem("Choose..");
                    airportComboBox3.setSelectedItem("Choose..");
                    airportComboBox4.setSelectedItem("Choose..");
                    airportComboBox5.setSelectedItem("Choose..");
                    airlineComboBox.setSelectedItem("Choose..");
                    txtNumber.setText("");
                    planeComboBox.setSelectedItem("Choose..");
                    departureComboBox.setSelectedItem("Choose..");
                    destinationComboBox.setSelectedItem("Choose..");
                    txtDate.setText("");

                    // this.invalidate();
                    ControllerFlightGUI demo = new ControllerFlightGUI();
                    demo.updateGUI();

                    
                    

                }

                catch(IOException e)
                {
                    e.printStackTrace();
                }



        // DefaultTableModel tblModel1 = (DefaultTableModel)flightTable.getModel();
        // // add String array data
        // String data[] = {flightNo,planeKey, departKey, destKey, date, time};
        // tblModel1.addRow(data);

        //succesfully added message
        // JOptionPane.showMessageDialog(this, "Flight added succusfully ");
        //clear fields for new entry
        // txtTime.setText("");
        // airportComboBox1.setSelectedItem("Choose..");
        // airportComboBox2.setSelectedItem("Choose..");
        // airportComboBox3.setSelectedItem("Choose..");
        // airportComboBox4.setSelectedItem("Choose..");
        // airportComboBox5.setSelectedItem("Choose");
        // airlineComboBox.setSelectedItem("Choose");
        // txtNumber.setText("");
        // planeComboBox.setSelectedItem("Choose..");
        // departureComboBox.setSelectedItem("Choose..");
        // destinationComboBox.setSelectedItem("Choose..");
        // txtDate.setText("");
               
  
}
}

