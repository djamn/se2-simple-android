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

        // Clicklistener für Send & Calculate Button
        findViewById(R.id.buttonSend).setOnClickListener(view -> sendInput(getTextFieldNumber()));
        findViewById(R.id.buttonCalculate).setOnClickListener(view -> calculateInput(getTextFieldNumber()));
    }

    private String getTextFieldNumber() {
        return ((TextView) findViewById(R.id.textFieldInput)).getText().toString();
    }

    private void sendInput(String inputUser) {
        // Neuer Thread für Socket
        new Thread(() -> {
            String outputServer;
            try {
                Socket clientSocket = new Socket("se2-isys.aau.at", 53212); // Erstellt neuen Socket

                // BufferedReader für Input & Output (vom und zum Server)
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Übermittelt den Methodenkopf String zum Server (\n für Ende der Übermittlung)
                outToServer.writeBytes(inputUser + '\n');

                // Liest Output des Servers
                outputServer = inFromServer.readLine();
                System.out.println("Debug - Output from server: " + outputServer);
                ((TextView) findViewById(R.id.textViewButtonOutput)).setText(outputServer); // Übergibt den Output an ein TextView

                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void calculateInput(String inputValue) {
        // Check, ob kein leerer Input existiert
        if (!inputValue.equals("")) {
            int[] arr = new int[inputValue.length()];

            // String -> Einzelnen Int Values
            for (int i = 0; i < inputValue.length(); i++) {
                arr[i] = Character.getNumericValue(inputValue.charAt(i));
            }

            // Holt sich die Indizes der gemeinsamen Teiler
            String factorIndizes = getFactors(arr).toString();

            ((TextView) findViewById(R.id.textViewButtonOutput)).setText(String.format(getString(R.string.output_factor_indizes_title), factorIndizes)); // Ersetzt Output-Textview
        } else {
            ((TextView) findViewById(R.id.textViewButtonOutput)).setText(R.string.valid_number_error);
        }
    }

    private StringBuilder getFactors(int[] arr) {
        StringBuilder builder = new StringBuilder();
        // Liest 1x von vorne bis hinten (2 Pointer, einer i und einer i+1,
        // welcher jedes i durchiteriert und nach arr[i] % arr[j] Ausschau hält
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > 1 && arr[j] > 1) {
                    if (arr[i] % arr[j] == 0) {
//                        System.out.println(arr[i] + ", " + arr[j] + " haben den gemeinsamen Teiler " + arr[j]);
                        builder.append("[").append(i).append(", ").append(j).append("] ");
                    }
                }
            }
        }
        // Gleicheres wie oben nur von hinten nach vorne
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > 1 && arr[j] > 1) {
                    if (arr[i] != arr[j]) {
                        if (arr[i] % arr[j] == 0) {
//                            System.out.println(arr[i] + ", " + arr[j] + " haben den gemeinsamen Teiler " + arr[j]);
                            builder.append("[").append(i).append(", ").append(j).append("] ");
                        }
                    }
                }
            }
        }

        return builder;
    }
}