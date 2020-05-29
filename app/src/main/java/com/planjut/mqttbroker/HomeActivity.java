package com.planjut.mqttbroker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Spinner spnTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spnTopic = findViewById(R.id.spnTopic);

        List<String> topics = new ArrayList<>();
        topics.add(0, "Choose Topic");
        topics.add(1, "Topic 1");
        topics.add(2, "Topic 2");
        topics.add(3, "Topic 3");

        // Style the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, topics);

        // Dropdown layout style
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);

        // Attaching data to spinner
        spnTopic.setAdapter(dataAdapter);
        spnTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Topic")) {
                    // Do nothing
                } else {
                    // On selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();

                    // Show selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        connect();
    }

    public void connect() {

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://tailor.cloudmqtt.com:13898",
                        clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setCleanSession(false);
        options.setUserName("jxvhljee");
        options.setPassword("m0q_vUULIQo9".toCharArray());
        try {
            IMqttToken token = client.connect(options);
            //IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("file", "onSuccess");
                    //publish(client,"payloadd");
                    subscribe(client, "kamar-tidur/temperature");
                    subscribe(client, "kamar-tidur/humidity");
                    client.setCallback(new MqttCallback() {
                        TextView tt = (TextView) findViewById(R.id.txtV_temp);
                        TextView th = (TextView) findViewById(R.id.txtV_humi);

                        @Override
                        public void connectionLost(Throwable cause) {

                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.d("file", message.toString());

                            if (topic.equals("kamar-tidur/temperature")) {
                                tt.setText(message.toString());
                            }

                            if (topic.equals("kamar-tidur/humidity")) {
                                th.setText(message.toString());
                            }

                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("file", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

//    public void publish(MqttAndroidClient client, String payload) {
//        String topic = "foo/bar";
//        byte[] encodedPayload = new byte[0];
//        try {
//            encodedPayload = payload.getBytes("UTF-8");
//            MqttMessage message = new MqttMessage(encodedPayload);
//            client.publish(topic, message);
//        } catch (UnsupportedEncodingException | MqttException e) {
//            e.printStackTrace();
//        }
//    }

    public void subscribe(MqttAndroidClient client, String topic) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
