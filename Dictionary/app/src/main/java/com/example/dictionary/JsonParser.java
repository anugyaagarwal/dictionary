package com.example.dictionary;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Reconnect on 04-07-2016.
 */
public class JsonParser {


    public static WordBean getDataFromServer(String response) throws Exception {

        WordBean wordBean = new WordBean();


        JSONObject responseJSONObject = new JSONObject(response);
        if (responseJSONObject != null && responseJSONObject.length() > 0) {

            JSONObject jsonObject = responseJSONObject;
            wordBean.setCount(jsonObject.getString("count"));
            JSONArray resultJSONArray = jsonObject.getJSONArray("results");

            if (resultJSONArray.length() > 0) {

                JSONObject object = resultJSONArray.getJSONObject(0);

                //optString for optional string

                wordBean.setPart_of_speech(object.optString("part_of_speech"));
                wordBean.setWord(object.getString("headword"));

                if (object.has("pronunciations")) {
                    JSONArray pronunciationsJsonArray = object.getJSONArray("pronunciations");
                    if (pronunciationsJsonArray.length() > 0) {
                        //JSONObject obj=responseJSONObject.getJSONObject("0");
                        JSONObject obj = pronunciationsJsonArray.getJSONObject(0);
                        JSONArray audioJsonArray = obj.getJSONArray("audio");

                        if (audioJsonArray.length() > 0) {
                            //JSONObject object2=responseJSONObject.getJSONObject("0");
                            JSONObject object2 = audioJsonArray.getJSONObject(0);
                            wordBean.setAudio_url(object2.getString("url"));

                        }

                    }
                }

                JSONArray sensesJsonArray = object.getJSONArray("senses");
                if (sensesJsonArray.length() > 0) {
                    //JSONObject objectsenses = responseJSONObject.getJSONObject("0");
                    JSONObject objectsenses = sensesJsonArray.getJSONObject(0);
                    JSONArray definitionJsonArray = objectsenses.getJSONArray("definition");

                    if (definitionJsonArray.length() > 0) {
                        wordBean.setDefinition(definitionJsonArray.getString(0));


                    }
                }
            }


        }

        return wordBean;
    }

}











