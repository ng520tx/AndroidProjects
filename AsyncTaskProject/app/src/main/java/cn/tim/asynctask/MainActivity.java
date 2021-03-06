package cn.tim.asynctask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private File descFile;

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new DownloadAsyncTask().execute("Hello world", "Good world");
    }

    public void startDownload(View view) {
        verifyStoragePermissions(this);
        ProgressBar progressBar = findViewById(R.id.download_pb);
        TextView textView = findViewById(R.id.download_tv);
        new MyDownloadAsyncTask(progressBar, textView).execute("http://img.zouchanglin.cn/adbdriver-39e8d4e0.zip");
    }


    public class MyDownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {
        private ProgressBar progressBar;
        private TextView textView;

        public MyDownloadAsyncTask(ProgressBar progressBar, TextView textView) {
            this.progressBar = progressBar;
            this.textView = textView;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String urlStr = strings[0];
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                // ?????????????????????
                int length = conn.getContentLength();
                File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                descFile = new File(file, "xxx.zip");
                int downloadSize = 0;
                if(descFile.exists()) {
                    if(descFile.delete()) Log.i(TAG, "doInBackground: descFile.delete()");
                }
                int offset;
                byte[] buffer = new byte[1024];
                FileOutputStream fileOutputStream = new FileOutputStream(descFile);
                while ((offset = inputStream.read(buffer)) != -1){
                    downloadSize += offset;
                    fileOutputStream.write(buffer, 0, offset);
                    //downloadHandler.sendEmptyMessage((downloadSize * 100 / length));
                    // ???????????????????????????
                    publishProgress((downloadSize * 100 / length));
                }
                fileOutputStream.close();
                inputStream.close();
                Log.i(TAG, "download: descFile = " + descFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        // ?????????????????????????????????
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                textView.setText("??????????????????????????? " + descFile.getAbsolutePath());
            }else{
                textView.setText("????????????");
            }
        }

        // ??????????????????
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // ??????????????????????????????
            textView.setText("?????????" + values[0] + "%");
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("???????????????");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //??????????????????
                Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            }else{
                //?????????????????????
                Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * AsyncTask API??????
     */
    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        // ??????????????????????????????????????????????????? UI
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // ????????????????????????????????????
        @Override
        protected Boolean doInBackground(String... strings) {
            for (int i = 0; i < 1000; i++) {
                Log.i(TAG, "doInBackground: " + Arrays.toString(strings));
                // ???????????????????????????
                publishProgress(i);
            }
            return true;
        }

        // ?????????????????????????????????
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        // ??????????????????
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // ??????????????????????????????
        }

        // ??????????????????
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}