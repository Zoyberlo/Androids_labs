package com.zoyberlo.project;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Lab4Activity extends AppCompatActivity {

    private TextView uahText;
    private TextView usdText;
    private TextView eurText;
    private Button convert;
    private Button clearBtn;
    private final String apiUrl = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4);

        uahText = findViewById(R.id.uah);
        usdText = findViewById(R.id.usd);
        eurText = findViewById(R.id.eur);
        convert = findViewById(R.id.convert);
        clearBtn = findViewById(R.id.clearBtn);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonTask jt = new JsonTask(apiUrl, getApplicationContext());
                jt.execute();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uahText.setText("");
                usdText.setText("");
                eurText.setText("");
            }
        });
    }

    private JsonObj[] getJsonObj(String json) throws JSONException {
        if ((json != null) && (!json.isEmpty())) {
            JSONArray reader = new JSONArray(json);
            JsonObj[] res = new JsonObj[reader.length()];
            for (int i = 0; i < reader.length(); i++) {
                JSONObject arrObj = reader.getJSONObject(i);
                JsonObj j = new JsonObj(
                        arrObj.getString("ccy"),
                        arrObj.getString("base_ccy"),
                        arrObj.getDouble("buy"),
                        arrObj.getDouble("sale"));
                res[i] = j;
            }
            return res;
        }
        return null;
    }

    private class JsonObj {
        private String ccy;
        private String base_ccy;
        private double buy;
        private double sale;

        private JsonObj(String ccy, String base_ccy, double buy, double sale) {
            this.ccy = ccy;
            this.base_ccy = base_ccy;
            this.buy = buy;
            this.sale = sale;
        }

        public String getCcy() {
            return ccy;
        }

        public String getBase_ccy() {
            return base_ccy;
        }

        public double getBuy() {
            return buy;
        }

        public double getSale() {
            return sale;
        }
    }

    private class JsonTask extends AsyncTask<Void, Void, Void> {
        private String url;
        private String json;

        private Context context;

        private JsonTask(String url, Context context) {
            this.url = url;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                this.json = HttpUtils.httpRequest(url);


            } catch (final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(e, "Can't retrieve JSON!");
                        Toast.makeText(context, "[HTTP REQUEST ERROR]", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Logger.d("JSON = " + json);
            String uah = String.valueOf(uahText.getText());
            String usd = String.valueOf(usdText.getText());
            String eur = String.valueOf(eurText.getText());

            if ((!uah.isEmpty()) && (StringUtils.isInteger(uah))) {
                try {
                    JsonObj[] arr = getJsonObj(json);
                    if (arr != null) {
                        for (JsonObj jsonObj : arr) {
                            if (jsonObj.getCcy().equals("USD")) {
                                double res = Double.parseDouble(uah) / jsonObj.getSale();
                                usdText.setText(Double.toString(res));
                            }
                            if (jsonObj.getCcy().equals("EUR")) {
                                double res = Double.parseDouble(uah) / jsonObj.getSale();
                                eurText.setText(Double.toString(res));
                            }
                        }
                    } else {
                        Logger.d("arr is null");
                    }
                } catch (JSONException e) {
                    Logger.e(e, "JSON MATH ERROR");
                }
            }
            if ((!usd.isEmpty()) && (StringUtils.isInteger(usd))) {
                try {
                    JsonObj[] arr = getJsonObj(json);
                    if (arr != null) {
                        for (JsonObj jsonObj : arr) {
                            if (jsonObj.getCcy().equals("USD")) {
                                double res = Double.parseDouble(usd) * jsonObj.getSale();
                                uahText.setText(Double.toString(res));
                            }
                            if (jsonObj.getCcy().equals("EUR")) {
                                double resUah = 0;
                                for (JsonObj jsonObj1 : arr) {
                                    if (jsonObj1.getCcy().equals("USD")) {
                                        resUah = Double.parseDouble(usd) * jsonObj1.getSale();
                                        break;
                                    }
                                }
                                double res = resUah / jsonObj.getSale();
                                eurText.setText(Double.toString(res));
                            }
                        }
                    } else {
                        Logger.d("arr is null");
                    }
                } catch (JSONException e) {
                    Logger.e(e, "JSON MATH ERROR");
                }
            }
            if ((!eur.isEmpty()) && (StringUtils.isInteger(eur))) {
                try {
                    JsonObj[] arr = getJsonObj(json);
                    if (arr != null) {
                        for (JsonObj jsonObj : arr) {
                            if (jsonObj.getCcy().equals("EUR")) {
                                double res = Double.parseDouble(eur) * jsonObj.getSale();
                                uahText.setText(Double.toString(res));
                            }
                            if (jsonObj.getCcy().equals("USD")) {
                                double resUah = 0;
                                for (JsonObj jsonObj1 : arr) {
                                    if (jsonObj1.getCcy().equals("EUR")) {
                                        resUah = Double.parseDouble(eur) * jsonObj1.getSale();
                                        break;
                                    }
                                }
                                double res = resUah / jsonObj.getSale();
                                usdText.setText(Double.toString(res));
                            }
                        }
                    } else {
                        Logger.d("arr is null");
                    }
                } catch (JSONException e) {
                    Logger.e(e, "JSON MATH ERROR");
                }
            }
        }
    }
}
