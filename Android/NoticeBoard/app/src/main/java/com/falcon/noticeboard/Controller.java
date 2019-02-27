package com.falcon.noticeboard;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller {

    public static final String EXTRA_NOTICE_ID = "EXTRA_NOTICE_ID";
    public static final String EXTRA_EDITABLE_ID = "EXTRA_EDITABLE_ID";
    private static String serverReply = null;

    public static boolean isAdmin(String username, String password) {
        final String url = "http://192.168.43.203:3000/admin?username=" + username + "&password=" + password;
        execute(url);
        boolean isAdmin = serverReply.toLowerCase().equals("true");
        serverReply = null;
        return isAdmin;
    }

    public static boolean isStudent(String username, String password) {
        final String url = "http://192.168.43.203:3000/student?username=" + username + "&password=" + password;
        execute(url);
        boolean isStudent = serverReply.toLowerCase().equals("true");
        serverReply = null;
        return isStudent;
    }

    public static int register(String username, String password, boolean isAdmin) {
        final String url = "http://192.168.43.203:3000/register?username=" + username + "&password="
                + password + (isAdmin ? "&admin=true" : "");
        execute(url);
        int reply = Integer.parseInt(serverReply);
        serverReply = null;
        return reply;
    }

    public static String[] getNoticeTitles(Context context) {
        String[] titles;
        String rawString = getNoticeTitle(-1);
        try {
            JSONArray jsonArray = new JSONArray(rawString);
            titles = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                titles[i] = jsonArray.getString(i);
            }
            return titles;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "An error has occured", Toast.LENGTH_SHORT).show();
            return new String[]{};
        }
    }

    public static String getNoticeTitle(int id) {
        final String url = "http://192.168.43.203:3000/notice?id=" + id;
        execute(url);
        String title = serverReply;
        serverReply = null;
        return title;
    }

    public static String getNoticeDetail(int id) {
        final String url = "http://192.168.43.203:3000/notice?id=" + id + "&detail=true";
        execute(url);
        String detail = serverReply;
        serverReply = null;
        return detail;
    }

    public static int addNotice(String title, String detail) {
        final String url = "http://192.168.43.203:3000/add?title=" + title + "&detail=" + detail;
        execute(url);
        int reply = Integer.parseInt(serverReply);
        serverReply = null;
        return reply;
    }

    public static boolean editNotice(int id, String title, String detail) {
        final String url = "http://192.168.43.203:3000/edit?id="+ id + "&title=" + title + "&detail=" + detail;
        execute(url);
        boolean reply = serverReply.toLowerCase().equals("true");
        serverReply = null;
        return reply;
    }

    public static void deleteNotice(int id) {
        final String url = "http://192.168.43.203:3000/delete?id=" + id;
        execute(url);
        serverReply = null;
    }

    private static void execute(String url) {
        new FetchInfoTask(url).execute();
        while (serverReply == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] getBytesResponse(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = connection.getInputStream();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            connection.disconnect();
            return null;
        }
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        connection.disconnect();
        return out.toByteArray();
    }

    private static String processRequest(String urlStr) {
        byte[] urlBytes;
        try {
            urlBytes = getBytesResponse(urlStr);
            if (urlBytes == null)
                return null;
        } catch (IOException e) {
            return null;
        }
        return new String(urlBytes);
    }

    private static class FetchInfoTask extends AsyncTask<Void, Void, Void> {

        String url;

        public FetchInfoTask(String url) {
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String reply = processRequest(url);
            serverReply  = (reply == null) ? "" : reply;
            return null;
        }
    }
}