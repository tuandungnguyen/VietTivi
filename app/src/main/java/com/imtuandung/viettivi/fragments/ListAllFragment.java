package com.imtuandung.viettivi.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.imtuandung.viettivi.R;
import com.imtuandung.viettivi.object.Channel;
import com.imtuandung.viettivi.object.ListChannelAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Dung Nguyen on 12/25/15.
 */
public class ListAllFragment extends Fragment {


    ListView lvAllChannel;
    ArrayList<Channel> listChannel;
    String linkJSON = "http://link.freefilmhd.com/channel.json";

    public ListAllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_listall, container, false);

        lvAllChannel = (ListView) rootView.findViewById(R.id.lvAllChannel);

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.pleasewait));
        dialog.show();

        AsyncDataClass jsonAsync = new AsyncDataClass();
        jsonAsync.execute("");
        dialog.cancel();

        return rootView;
    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost(linkJSON);

            String jsonResult = "";

            try {
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            listChannel = returnParsedJsonObject(result);
            ListChannelAdapter adapter = new ListChannelAdapter(getContext(), listChannel);
            lvAllChannel.setAdapter(adapter);
            lvAllChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getContext(), PlayerFragment.class);
                    intent.putExtra("link", listChannel.get(position).getLink());
                    intent.putExtra("title", listChannel.get(position).getTitle());
                    intent.putExtra("description", listChannel.get(position).getDescription());
                    startActivity(intent);
                }
            });


        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }

    }

    /**
     * Parsing Json string to ArrayList of Channel Items
     *
     * @param result json string
     * @return ArrayList channel
     */
    private ArrayList<Channel> returnParsedJsonObject(String result) {

        ArrayList<Channel> jsonObject = new ArrayList<Channel>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        Channel newItemObject = null;

        try {
            resultObject = new JSONObject(result);
            jsonArray = resultObject.optJSONArray("channel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                String id = jsonChildNode.getString("id");
                String title = jsonChildNode.getString("title");
                String description = jsonChildNode.getString("description");
                String link = jsonChildNode.getString("link");
                String category = jsonChildNode.getString("category");
                newItemObject = new Channel(id, title, description, link, category);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}