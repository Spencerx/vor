package com.futurice.vor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.reactivecascade.AsyncBuilder;
import com.futurice.vor.activity.SettingsActivity;
import com.futurice.vor.services.LocationService;
import com.futurice.vor.utils.SharedPreferencesManager;
import com.futurice.vor.utils.BeaconLocationManager;
import static com.futurice.vor.Constants.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Main application
 */
public class VorApplication extends Application {
    public static String TAG = VorApplication.class.getCanonicalName();

    private static Context sContext;
    private static Socket sSocket;
    private static BeaconLocationManager beaconLocationManager;

    /**
     * Reference to the service.
     */
    @Nullable

    public static Context getStaticContext() {
        return VorApplication.sContext;
    }

    public static String getApplicationName() {
        return sContext.getString(sContext.getApplicationInfo().labelRes);
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        // LeakCanary.install(this);
        sContext = getApplicationContext();

        new AsyncBuilder(this)
                .setStrictMode(false) //TODO Fix strict mode on startup for performance and testing
                .build();

        try {
            sSocket = IO.socket(SERVER_URL);
            IO.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        sSocket
                .on(Socket.EVENT_CONNECT, args -> Log.d(TAG, "EVENT_CONNECT"))
                .on(Socket.EVENT_CONNECT_ERROR, args -> {
                    Log.d(TAG, "EVENT_CONNECT_ERROR");
                    for (Object arg : args) {
                        Log.d(TAG, arg.toString());
                    }
                })
                .on(LOCATION_KEY, args -> Log.d(TAG, "LOCATION RECEIVED"))
                .on(MESSAGE_KEY, args -> {
                    try {
                        JSONObject jsonObject = (JSONObject)args[0];
                        switch (jsonObject.getString(TYPE_KEY)) {
                            case LOCATION_KEY:
                                beaconLocationManager.onLocation(jsonObject);
                                break;
                            case KEEP_ALIVE_KEY:
                                // TODO Deal with the keep alive message
                                break;
                            default:
                                SharedPreferencesManager.saveToSharedPreferences(jsonObject, this);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                })
                .on(Socket.EVENT_DISCONNECT, args -> Log.d(TAG, "EVENT_DISCONNECT"));
        sSocket.connect();

        beaconLocationManager = new BeaconLocationManager(sContext);

        // Start the service for updating location if connected to the correct network.
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean serviceEnabled = preferences.getBoolean(SettingsActivity.BACKGROUND_SERVICE_KEY, true);

        if (wifiInfo.getSSID().equals(NETWORK_SSID) && serviceEnabled) {
            startService(new Intent(this, LocationService.class));
        }
    }

    public static Socket getSocket() {
        return sSocket;
    }

    public static BeaconLocationManager getBeaconLocationManager() {
        return beaconLocationManager;
    }
}
