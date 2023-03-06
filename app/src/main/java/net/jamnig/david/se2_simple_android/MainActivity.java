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
            sendInput(getTextFieldNumber());
        });
        findViewById(R.id.button3).setOnClickListener(view -> {
            calculateInput(getTextFieldNumber());
        });
    }

    private String getTextFieldNumber() {
        return ((TextView) findViewById(R.id.editTextNumber)).getText().toString();
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
                ((TextView) findViewById(R.id.textView5)).setText(outputServer);

                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void calculateInput(String number) {
        // Check, ob kein leerer Input existiert
        if (!number.equals("")) {
            int[] arr = new int[number.length()];

            for (int i = 0; i < number.length(); i++) {
                arr[i] = Character.getNumericValue(number.charAt(i));
            }

            String result1 = getFactors(arr).toString();

            ((TextView) findViewById(R.id.textView5)).setText("Gemeinsame Teiler: " + result1);
        } else {
            ((TextView) findViewById(R.id.textView5)).setText("Bitte gib eine gültige Zahl an!");
        }

    }

    private StringBuilder getFactors(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > 1 && arr[j] > 1) {
                    if (arr[i] % arr[j] == 0) {
                        System.out.println(arr[i] + ", " + arr[j] + " haben den gemeinsamen Teiler " + arr[j]);
                        builder.append("[" + i + ", " + j + "] ");
                    }
                }
            }
        }
//        builder.append("\nRückgängig: ");
        System.out.println("Rückgängig:");
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > 1 && arr[j] > 1) {
                    if (arr[i] != arr[j]) {
                        if (arr[i] % arr[j] == 0) {
                            System.out.println(arr[i] + ", " + arr[j] + " haben den gemeinsamen Teiler " + arr[j]);
                            builder.append("[" + i + ", " + j + "] ");
                        }
                    }
                }
            }
        }

        return builder;
    }
}