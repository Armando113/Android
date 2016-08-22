package com.blackdroidstudios.webservicetest;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Armando on 4/22/2015.
 */
public class TranslateFragment extends Fragment
{
    protected MainActivity ma;

    private static final String TAG = "TranslateTask";
    protected Spinner fromSpin;
    protected Spinner toSpin;
    protected EditText fromText;
    protected TextView toText;
    protected TextView accessTk;
    protected Button translateBtn;

    protected TextWatcher textWatcher;
    // needed to make translate requests to Microsoft
    private String accessToken;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.translatefragment, container, false);

        ma = (MainActivity)getActivity();

        fromSpin = (Spinner)view.findViewById(R.id.translate_fromSpinner);
        toSpin = (Spinner)view.findViewById(R.id.translate_toSpinner);
        fromText = (EditText)view.findViewById(R.id.translate_fromET);
        toText = (TextView)view.findViewById(R.id.translate_toTV);
        accessTk = (TextView)view.findViewById(R.id.translate_access);
        translateBtn = (Button)view.findViewById(R.id.translate_btn);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateText(fromText.getText().toString(), fromSpin.getSelectedItem().toString(), toSpin.getSelectedItem().toString());
            }
        });

        new GetAccessTokenTask().execute();

        return view;
    }

    private void translateText(String textToTranslate, String from, String to)
    {
        new TranslationTask().execute(textToTranslate, from, to);
    }

    private class TranslationTask extends AsyncTask<String, Void, String>
    {
        protected void onPostExecute(String translation)
        {
            toText.setText(translation);
        }

        @Override
        protected String doInBackground(String... params)
        {
            HttpURLConnection con2 = null;

            String result = ma.getResources().getString(R.string.translate_error);
            String original = params[0];
            String from = params[1];
            String to = params[2];

            try
            {
                BufferedReader reader;

                String uri = "http://api.microsofttranslator.com" + "/v2/Http.svc/Translate?text=" + URLEncoder.encode(original) + "&from=" + from + "&to=" + to;
                URL url_translate = new URL(uri);
                String authToken = "Bearer" + " " + accessToken;

                con2 = (HttpURLConnection) url_translate.openConnection();
                con2.setRequestProperty("Authorization", authToken);
                con2.setDoInput(true);
                con2.setReadTimeout(10000);
                con2.setConnectTimeout(15000);
                reader = new BufferedReader(new InputStreamReader(con2.getInputStream(), "UTF-8"));
                String translated_xml = reader.readLine();
                reader.close();

                //parse xml
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(translated_xml)));
                NodeList nodeList = doc.getElementsByTagName("string");
                NodeList nodeEl = nodeList.item(0).getChildNodes();
                Node node;
                String translated = null;
                if(nodeEl != null && nodeEl.getLength() > 0)
                {
                    node = nodeEl.item(0);
                    translated = node.getNodeValue();
                }

                if(translated != null)
                {
                    result = translated;
                }

            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (ParserConfigurationException e)
            {
                e.printStackTrace();
            } catch (SAXException e)
            {
                e.printStackTrace();
            }finally
            {
                if(con2 != null)
                {
                    con2.disconnect();
                }
            }
            return result;
        }
    }

    private class GetAccessTokenTask extends AsyncTask<Void, Void, String>
    {

        protected void onPostExecute(String access_token)
        {
            accessToken = access_token;
            accessTk.setText(access_token);
        }

        @Override
        protected String doInBackground(Void... params)
        {
            String result = null;
            HttpURLConnection con = null;

            String clientID = "WebTalkerID_BDS";
            String clientSecret = "NgY7GO42GcSpZLU4qC1vvztNMU7l72QBLxRdrIH7KHo=";
            String strTranslatorAccessURI = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
            String strRequestDetails = "grant_type=" + "client_credentials&client_id=" + URLEncoder.encode(clientID) + "&client_secret=" +  URLEncoder.encode(clientSecret) + "&scope=http://api.microsofttranslator.com";
            try
            {
                URL url = new URL(strTranslatorAccessURI);
                con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(10000);
                con.setConnectTimeout(15000);
                con.setRequestMethod("POST");

                con.setDoInput(true);
                con.setDoOutput(true);
                con.setChunkedStreamingMode(0);

                //start query
                con.connect();
                OutputStream out = new BufferedOutputStream(con.getOutputStream());
                out.write(strRequestDetails.getBytes());
                out.flush();

                //Read results from the query
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String payload = reader.readLine();
                reader.close();
                out.close();

                //parse to get Access token
                JSONObject jsonObj = new JSONObject(payload);
                result = jsonObj.getString("access_token");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally
            {
                if (con != null) {
                    con.disconnect();
                }
            }
            return result;
        }
    }


}
