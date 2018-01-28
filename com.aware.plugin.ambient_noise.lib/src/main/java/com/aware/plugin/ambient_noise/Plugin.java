package com.aware.plugin.ambient_noise;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;
import com.aware.utils.Scheduler;

import org.json.JSONException;

public class Plugin extends Aware_Plugin {

    /**
     * Broadcasted with sound frequency value <br/>
     * Extra: sound_frequency, in Hz
     */
    public static final String ACTION_AWARE_PLUGIN_AMBIENT_NOISE = "ACTION_AWARE_PLUGIN_AMBIENT_NOISE";

    /**
     * Extra for ACTION_AWARE_PLUGIN_AMBIENT_NOISE
     * (double) sound frequency in Hz
     */
    public static final String EXTRA_SOUND_FREQUENCY = "sound_frequency";

    /**
     * Extra for ACTION_AWARE_PLUGIN_AMBIENT_NOISE
     * (boolean) true/false if silent
     */
    public static final String EXTRA_IS_SILENT = "is_silent";

    /**
     * Extra for ACTION_AWARE_PLUGIN_AMBIENT_NOISE
     * (double) sound noise in dB
     */
    public static final String EXTRA_SOUND_DB = "sound_db";

    /**
     * Extra for ACTION_AWARE_PLUGIN_AMBIENT_NOISE
     * (double) sound RMS
     */
    public static final String EXTRA_SOUND_RMS = "sound_rms";

    public static final String SCHEDULER_PLUGIN_AMBIENT_NOISE = "SCHEDULER_PLUGIN_AMBIENT_NOISE";

    //AWARE context producer
    public static ContextProducer context_producer;

    @Override
    public void onCreate() {
        super.onCreate();

        AUTHORITY = Provider.getAuthority(this);

        TAG = "AWARE::Ambient Noise";

        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                Intent context_ambient_noise = new Intent();
                context_ambient_noise.setAction(ACTION_AWARE_PLUGIN_AMBIENT_NOISE);
                context_ambient_noise.putExtra(EXTRA_SOUND_FREQUENCY, AudioAnalyser.sound_frequency);
                context_ambient_noise.putExtra(EXTRA_SOUND_DB, AudioAnalyser.sound_db);
                context_ambient_noise.putExtra(EXTRA_SOUND_RMS, AudioAnalyser.sound_rms);
                context_ambient_noise.putExtra(EXTRA_IS_SILENT, AudioAnalyser.is_silent);
                sendBroadcast(context_ambient_noise);
            }
        };
        context_producer = CONTEXT_PRODUCER;

        REQUIRED_PERMISSIONS.add(Manifest.permission.RECORD_AUDIO);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (PERMISSIONS_OK) {

            DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

            Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_AMBIENT_NOISE, true);

            if (Aware.getSetting(getApplicationContext(), Settings.FREQUENCY_PLUGIN_AMBIENT_NOISE).length() == 0) {
                Aware.setSetting(getApplicationContext(), Settings.FREQUENCY_PLUGIN_AMBIENT_NOISE, 5);
            }
            if (Aware.getSetting(getApplicationContext(), Settings.PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE).length() == 0) {
                Aware.setSetting(getApplicationContext(), Settings.PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE, 30);
            }
            if (Aware.getSetting(getApplicationContext(), Settings.PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD).length() == 0) {
                Aware.setSetting(getApplicationContext(), Settings.PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD, 50);
            }

            try {
                Scheduler.Schedule audioSampler = Scheduler.getSchedule(this, SCHEDULER_PLUGIN_AMBIENT_NOISE);
                if (audioSampler == null || audioSampler.getInterval() != Long.parseLong(Aware.getSetting(this, Settings.FREQUENCY_PLUGIN_AMBIENT_NOISE))) {
                    audioSampler = new Scheduler.Schedule(SCHEDULER_PLUGIN_AMBIENT_NOISE)
                            .setInterval(Long.parseLong(Aware.getSetting(this, Settings.FREQUENCY_PLUGIN_AMBIENT_NOISE)))
                            .setActionType(Scheduler.ACTION_TYPE_SERVICE)
                            .setActionClass(getPackageName() + "/" + AudioAnalyser.class.getName());
                    Scheduler.saveSchedule(this, audioSampler);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (Plugin.getSensorObserver() == null) {
                Plugin.setSensorObserver(new AWARESensorObserver() {
                    @Override
                    public void onAmbientNoiseChanged(ContentValues data) {
                        sendBroadcast(new Intent("AMBIENT_NOISE_DATA").putExtra("data", data));
                    }
                });
            }

            if (Aware.getSetting(this, Aware_Preferences.FREQUENCY_WEBSERVICE).length() >= 0 && !Aware.isSyncEnabled(this, Provider.getAuthority(this)) && Aware.isStudy(this) && getApplicationContext().getPackageName().equalsIgnoreCase("com.aware.phone") || getApplicationContext().getResources().getBoolean(R.bool.standalone)) {
                ContentResolver.setIsSyncable(Aware.getAWAREAccount(this), Provider.getAuthority(this), 1);
                ContentResolver.setSyncAutomatically(Aware.getAWAREAccount(this), Provider.getAuthority(this), true);
                ContentResolver.addPeriodicSync(
                        Aware.getAWAREAccount(this),
                        Provider.getAuthority(this),
                        Bundle.EMPTY,
                        Long.parseLong(Aware.getSetting(this, Aware_Preferences.FREQUENCY_WEBSERVICE)) * 60
                );
            }

            Aware.startAWARE(this);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Aware.isStudy(this) && (getApplicationContext().getPackageName().equalsIgnoreCase("com.aware.phone") || getApplicationContext().getResources().getBoolean(R.bool.standalone))) {
            //ContentResolver.setSyncAutomatically(Aware.getAWAREAccount(this), Provider.getAuthority(this), false);
            ContentResolver.removePeriodicSync(
                    Aware.getAWAREAccount(this),
                    Provider.getAuthority(this),
                    Bundle.EMPTY
            );
        }

        Scheduler.removeSchedule(this, SCHEDULER_PLUGIN_AMBIENT_NOISE);
        Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_AMBIENT_NOISE, false);

        Aware.stopAWARE(this);
    }

    private static AWARESensorObserver awareSensor;
    public static void setSensorObserver(AWARESensorObserver observer) {
        awareSensor = observer;
    }

    public static AWARESensorObserver getSensorObserver() {
        return awareSensor;
    }

    public interface AWARESensorObserver {
        void onAmbientNoiseChanged(ContentValues data);
    }
}
