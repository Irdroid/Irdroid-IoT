/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.microcontrollerbg.irdroid.cloud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Handles collection of user information to create a new MQTT Client
 *
 */
public class NewConnection extends Activity {

  /** {@link Bundle} which holds data from activities launched from this activity **/
  private Bundle result = null;

  /** 
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_connection);
    ActionBar actionBar = getActionBar();

    actionBar.setDisplayHomeAsUpEnabled(true);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    adapter.addAll(readHosts());
 //   AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.serverURI);
 //   textView.setAdapter(adapter);

    //load auto compete options

  }

  /** 
   * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_new_connection, menu);
    OnMenuItemClickListener listener = new Listener(this);
    menu.findItem(R.id.connectAction).setOnMenuItemClickListener(listener);
 //   menu.findItem(R.id.advanced).setOnMenuItemClickListener(listener);

    return true;
  }

  /** 
   * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
        // app icon in action bar clicked; goto parent activity.
        this.finish();
        return true;
    default:
        return super.onOptionsItemSelected(item);
    }
   
  }

  /**
   * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent intent) {

    if (resultCode == RESULT_CANCELED) {
      return;
    }

    result = intent.getExtras();

  }

  /**
   * Handles action bar actions
   *
   */
  private class Listener implements OnMenuItemClickListener {

    //used for starting activities 
    private NewConnection newConnection = null;

    public Listener(NewConnection newConnection)
    {
      this.newConnection = newConnection;
    }

    /**
     * @see android.view.MenuItem.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem)
     */
    public boolean isBinary(int number) { int copyOfInput = number; while (copyOfInput != 0) { if (copyOfInput % 10 > 1) { return false; } copyOfInput = copyOfInput / 10; } return true; }

  
    public boolean onMenuItemClick(MenuItem item) {
      {
        // this will only connect need to package up and sent back

        int id = item.getItemId();

        Intent dataBundle = new Intent();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        switch (id) {
          case R.id.connectAction :
            //extract client information
        	  String location = ((EditText) findViewById(R.id.location))
              .getText().toString();
            String server = "hwgroup-bg.com";//((AutoCompleteTextView) findViewById(R.id.serverURI))
                //.getText().toString();
            String port = "1883";//((EditText) findViewById(R.id.port))
                //.getText().toString();
            String clientId = "wirf"+((EditText) findViewById(R.id.clientId))
                .getText().toString();
           
           int SwitchType =(int) spinner.getSelectedItemPosition();
           
        
           String SwitchType1 = null;
            
            String channelId = ((EditText) findViewById(R.id.channel))
                    .getText().toString();
            
           String ID = ((EditText) findViewById(R.id.clientId))
                    .getText().toString();



            if (location.equals(ActivityConstants.empty) || channelId.equals(ActivityConstants.empty) || clientId.equals(ActivityConstants.empty) || ID.equals(ActivityConstants.empty))
            {
              String notificationText = newConnection.getString(R.string.missingOptions);
              Notify.toast(newConnection, notificationText, Toast.LENGTH_LONG);
              return false;
            }
            if(SwitchType == 0){
            	  SwitchType1 = "0";
            
         	   if (Integer.parseInt(channelId) < 1 || Integer.parseInt(channelId) > 4){
         		   String notificationText = newConnection.getString(R.string.wrongchannel_type0);
                    Notify.toast(newConnection, notificationText, Toast.LENGTH_LONG);
                    return false;
         	   }
         	
         }

         if(SwitchType == 1){
        	 SwitchType1 = "1";
          
         	if(isBinary(Integer.parseInt(channelId))){
         	System.out.println(channelId.length() );
         	
         		if (channelId.length() !=5){
         			String notificationText = newConnection.getString(R.string.wrongchannel_type1);
         	         Notify.toast(newConnection, notificationText, Toast.LENGTH_LONG);
         	         return false;
         			
         		}
         		
         		
         	}
         	
         }
         	
         	if(SwitchType == 2){
         	 SwitchType1 = "2";
              
          	   if (Integer.parseInt(channelId) < 1000 || Integer.parseInt(channelId) > 9999){
          		   String notificationText = newConnection.getString(R.string.wrongchannel_type2);
                     Notify.toast(newConnection, notificationText, Toast.LENGTH_LONG);
                     return false;
          	   }
          	
          }
         	
         

           
            if (Integer.parseInt(ID) < 1000 || Integer.parseInt(ID) > 9999){
           	 String notificationText = newConnection.getString(R.string.wrongclientid);
                Notify.toast(newConnection, notificationText, Toast.LENGTH_LONG);
                return false;
           	
           }

          
            boolean cleanSession = false;//((CheckBox) findViewById(R.id.cleanSessionCheckBox)).isChecked();
            //persist server
            persistServerURI(server);

            //put data into a bundle to be passed back to ClientConnections
            dataBundle.putExtra(ActivityConstants.location, location);
            dataBundle.putExtra(ActivityConstants.server, server);
            dataBundle.putExtra(ActivityConstants.port, port);
            dataBundle.putExtra(ActivityConstants.clientId, clientId);
            dataBundle.putExtra(ActivityConstants.channelId, channelId);
            dataBundle.putExtra(ActivityConstants.action, ActivityConstants.connect);
            dataBundle.putExtra(ActivityConstants.cleanSession, cleanSession);
            dataBundle.putExtra(ActivityConstants.type, SwitchType1);

            if (result == null) {
              // create a new bundle and put default advanced options into a bundle
              result = new Bundle();

              result.putString(ActivityConstants.message,
                  ActivityConstants.empty);
              result.putString(ActivityConstants.topic, ActivityConstants.empty);
              result.putInt(ActivityConstants.qos, ActivityConstants.defaultQos);
              result.putBoolean(ActivityConstants.retained,
                  ActivityConstants.defaultRetained);

              result.putString(ActivityConstants.username,
                  ActivityConstants.empty);
              result.putString(ActivityConstants.password,
                  ActivityConstants.empty);

              result.putInt(ActivityConstants.timeout,
                  ActivityConstants.defaultTimeOut);
              result.putInt(ActivityConstants.keepalive,
                  ActivityConstants.defaultKeepAlive);
              result.putBoolean(ActivityConstants.ssl,
                  ActivityConstants.defaultSsl);
            
              

            }
            
            //add result bundle to the data being returned to ClientConnections
            dataBundle.putExtras(result);

            setResult(RESULT_OK, dataBundle);
            newConnection.finish();
            break;
  //        case R.id.advanced :
            //start the advanced options activity
      //      dataBundle.setClassName(newConnection,
      //          "org.eclipse.paho.android.service.sample.Advanced");
     //       newConnection.startActivityForResult(dataBundle,
      //          ActivityConstants.advancedConnect);

         //   break;
        }
        return false;

      }

    }

    /**
     * Add a server URI to the persisted file
     * 
     * @param serverURI the uri to store
     */
    private void persistServerURI(String serverURI) {
      File fileDir = newConnection.getFilesDir();
      File presited = new File(fileDir, "hosts.txt");
      BufferedWriter bfw = null;
      try {
        bfw = new BufferedWriter(new FileWriter(presited));
        bfw.write(serverURI);
        bfw.newLine();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      finally {
        try {
          if (bfw != null) {
            bfw.close();
          }
        }
        catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

  }

  /**
   * Read persisted hosts
   * @return The hosts contained in the persisted file
   */
  private String[] readHosts() {
    File fileDir = getFilesDir();
    File persisted = new File(fileDir, "hosts.txt");
    if (!persisted.exists()) {
      return new String[0];
    }
    ArrayList<String> hosts = new ArrayList<String>();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(persisted));
      String line = null;
      line = br.readLine();
      while (line != null) {
        hosts.add(line);
        line = br.readLine();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (br != null) {
          br.close();
        }
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return hosts.toArray(new String[hosts.size()]);

  }
}
