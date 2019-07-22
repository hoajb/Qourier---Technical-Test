package com.qourier.technicaltest.question1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Hoa Nguyen on Jul 20 2019.
 */
public class AsyncLoadFile extends AsyncTask<Void, Void, TimeResult> {
    private final Context mContext;
    private final int jsonId;
    private LoadJsonFileCompleted mLoadJsonFileCompleted;

    public AsyncLoadFile(Context context, int jsonId) {
        this.mContext = context.getApplicationContext();
        this.jsonId = jsonId;
    }

    public void setLoadJsonFileCompleted(LoadJsonFileCompleted loadJsonFileCompleted) {
        mLoadJsonFileCompleted = loadJsonFileCompleted;
    }

    @Override
    protected TimeResult doInBackground(Void... voids) {
        if (!isCancelled()) {
            String json = loadJSONFromAsset();
            try {
                return new Gson().fromJson(json, TimeResult.class);
            } catch (JsonSyntaxException e) {
                Log.e("JsonSyntaxException - " + jsonId, e.toString());
            }

            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(TimeResult result) {
        super.onPostExecute(result);

        if (mLoadJsonFileCompleted != null) {
            mLoadJsonFileCompleted.loadCompleted(result);
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getResources().openRawResource(jsonId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
