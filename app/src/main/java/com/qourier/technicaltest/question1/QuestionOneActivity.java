package com.qourier.technicaltest.question1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qourier.technicaltest.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Hoa Nguyen on Jul 20 2019.
 */
public class QuestionOneActivity extends AppCompatActivity {

    private Spinner mSpinnerTestCase;
    private Spinner mSpinnerArrivals;
    private Spinner mSpinnerDepartures;

    private AsyncLoadFile mAsyncLoadFile;

    public static void startActivity(Activity from) {
        Intent intent = new Intent(from, QuestionOneActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.question_1_layout);

        mSpinnerTestCase = findViewById(R.id.spinnerTestCase);
        mSpinnerArrivals = findViewById(R.id.spinnerArrivals);
        mSpinnerDepartures = findViewById(R.id.spinnerDepartures);

        setupTestCase();

        mSpinnerTestCase.setSelection(0);
    }

    private void setupTestCase() {
        ArrayList<String> arrTestCase = new ArrayList<>();

        arrTestCase.add("Test1");
        arrTestCase.add("Test2");
        arrTestCase.add("Test3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrTestCase);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTestCase.setAdapter(adapter);
        mSpinnerTestCase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idJson = 0;
                switch (position) {
                    case 0:
                        idJson = R.raw.test1;
                        break;
                    case 1:
                        idJson = R.raw.test2;
                        break;
                    case 2:
                        idJson = R.raw.test3;
                        break;
                }


                mAsyncLoadFile = new AsyncLoadFile(QuestionOneActivity.this.getApplicationContext(), idJson);
                mAsyncLoadFile.setLoadJsonFileCompleted(new LoadJsonFileCompleted() {
                    @Override
                    public void loadCompleted(TimeResult result) {
                        if (result == null) {
                            Toast.makeText(QuestionOneActivity.this, "Oops! Load Failed!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        setupArrivals(result);

                    }
                });

                mAsyncLoadFile.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupArrivals(TimeResult result) {
        final TimeData timeData = result.firstData();
        final ArrayList<String> arrivalsTime = timeData.getArrivalsTime();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrivalsTime);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerArrivals.setAdapter(adapter);
        mSpinnerArrivals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String arrival = arrivalsTime.get(position);
                setupDeparture(arrival,
                        timeData.getDepartureListByArrival(arrival)
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupDeparture(final String arrivals, final ArrayList<String> departures) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, departures);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDepartures.setAdapter(adapter);
        mSpinnerDepartures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (departures.size() > 0) {

                    Toast.makeText(QuestionOneActivity.this,
                            String.format(Locale.getDefault(),
                                    "Arrival : %s, Departure : %s", arrivals, departures.get(position)),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAsyncLoadFile != null) {
            mAsyncLoadFile.cancel(true);
        }
    }

}
