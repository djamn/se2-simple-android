package net.jamnig.david.se2_simple_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(view -> {
            TextView textView = findViewById(R.id.editTextNumber);
            String number = textView.getText().toString();
            sendInput(number);
            System.out.println("Print");
        });
    }

    private void sendInput(String inputUser) {
        new Thread(() -> {
            String outputServer;
            try {
                Socket clientSocket = new Socket("se2-isys.aau.at", 53212);

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outToServer.writeBytes(inputUser + '\n');

                outputServer = inFromServer.readLine();
                System.out.println("Debug - Output from server: " + outputServer);
                TextView outputLocale = findViewById(R.id.textView5);
                outputLocale.setText(outputServer);

                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}//