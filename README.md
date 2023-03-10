# SE II Einzelbeispiel
Einzelbeispiel für SE II - Simple Android App mit Netzwerkschnittstelle für Software Engineering II

Implementiert einen TCP Client und gibt weiters die Indizes von eingegebenen Zahlenpaaren aus, welche gemeinsame Teiler haben

## Bugfixes & Troubleshooting
- Sockets müssen in einem **Thread** sein. Entweder mittels neuer Klasse und new Thread erstellen und in der anderen Klasse mittels Runable Interface implementieren oder einfach als Lambda-Ausdruck alles in eine Methode implementieren
  - Ein Thread erlaubt es, Anweisungen in mehrere Teile aufzuteilen, welche dann beispielsweise parallel ablaufen können. So muss das Programm zB nicht komplett stoppen, bis der Socket erstellt wurde sondern kann dies parallel durchführen
- **Error:** SecurityException: Permission denied (missing INTERNET permission?)
    - ``<uses-permission android:name="android.permission.INTERNET" />``
    - ``<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />``
in **manifests** unter ``</application>``
- **Error:** Unable to resolve host "``<URL here>``" No address associated with host name -> Checken, ob im WLan verbunden und Permissions (siehe oben) gesetzt sind
- **Error:** java.net.SocketException: socket failed: EPERM (Operation not permitted) -> Neuinstallation der App im Emulator fixt den Fehler