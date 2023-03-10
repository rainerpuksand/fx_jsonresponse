package com.example.fx_jsonresponse;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HelloController {

    @FXML
    private Label welcomeText;
    @FXML
    private Label blockedText;


    @FXML
    public Label welcomeText2;
    @FXML
    public Label blockedText2;


    @FXML
    protected void checkIfBlocked() {
        blockedText.setText("...Not Blocked");
    }
    @FXML
    protected void checkIfBlocked2() {
        blockedText2.setText("...Not Blocked");
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        HttpURLConnection conn = null;
        int responsecode = 0;
        BufferedReader br;
        String line;
        StringBuilder strBuilder = new StringBuilder();

        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            conn = (HttpURLConnection) url.openConnection();

            // Request and setup the connection properties
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            //conn.connect();//safer to use this too

            //Getting the response code
            responsecode = conn.getResponseCode();


            //==================
            System.out.println("HttpResponseCode: " + responsecode);


            if (responsecode >= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            //whatever is received start collecting it into the stringBuilder
            while ((line = br.readLine()) != null) {
                strBuilder.append(line);
            }
            br.close();
            System.out.println("Inside builder: " + strBuilder);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        //Using the JSON simple library parse the string into a json object
        JSONParser parse = new JSONParser();
        JSONArray arr = null;
        try {
            arr = (JSONArray) parse.parse(strBuilder.toString());//arr is filled here
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arr.size(); i++) {

            JSONObject new_obj = (JSONObject) arr.get(i);
            System.out.println( i + "==========\n" +new_obj);

            if(i==9){
                String strName = (String) new_obj.get("name");
                String strEmail = (String) new_obj.get("email");
                welcomeText.setText(strName + " / " + strEmail);//Vimp line to learn the threading part
            }
        }

    }




    @FXML //this is the click Action for the bottom getJson Button
    public void doWithThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // welcomeText2.setText("Welcome to JavaFX Application!");//observe the error first

                HttpURLConnection conn = null;
                int responsecode = 0;
                BufferedReader br;
                String line;
                StringBuilder strBuilder = new StringBuilder();

                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/users");
                    conn = (HttpURLConnection) url.openConnection();

                    // Request and setup the connection properties
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(6000);
                    conn.setReadTimeout(6000);
                    //conn.connect();//safer to use this too

                    //Getting the response code
                    responsecode = conn.getResponseCode();


                    //==================
                    System.out.println("HttpResponseCode: " + responsecode);


                    if (responsecode >= 300) {
                        br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }
                    else {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    }

                    //whatever is received start collecting it into the stringBuilder
                    while ((line = br.readLine()) != null) {
                        strBuilder.append(line);
                    }
                    br.close();
                    System.out.println("Inside builder: " + strBuilder);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONArray arr = null;
                try {
                    arr = (JSONArray) parse.parse(strBuilder.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < arr.size(); i++) {
                    JSONObject new_obj = (JSONObject) arr.get(i);
                    System.out.println( i + "==========\n" +new_obj);

                    if(i==9){
                        String strName = (String) new_obj.get("name");
                        String strEmail = (String) new_obj.get("email");
                        //welcomeText2.setText(strName + " / " + strEmail);//observe the error then do the solution

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                welcomeText2.setText(strName + " / " + strEmail);//observe the error then do the solution
                            }
                        });
                    }
                }

            }//run ends
        }).start();//thread started

    }



}//xxxxxxxxxxxxxxx

/*
Platform.runLater(new Runnable() {
    @Override
    public void run() {
        welcomeText2.setText(strName + " / " + strEmail);//observe the error then do the solution
    }
});*/