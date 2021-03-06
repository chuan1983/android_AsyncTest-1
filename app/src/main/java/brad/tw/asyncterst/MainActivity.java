package brad.tw.asyncterst;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyTask mt1;
    private TextView mesg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mesg = (TextView)findViewById(R.id.mesg);
    }
    public void test1(View v){
        mt1 = new MyTask();
        mt1.execute("Brad","Kevin","Tony","Peter","Eric");
    }
    public void test2(View v){
        if (mt1 != null && !mt1.isCancelled()){
            mt1.cancel(true);
        }
    }

    private class MyTask extends AsyncTask<String,Object,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("brad", "onPreExecute");
        }
        @Override
        protected String doInBackground(String... params) {
            Log.d("brad", "doInBackground");

            int i = 0; boolean isCancel = false;
            for (String name : params){
                if (isCancelled()){
                    isCancel = true;
                    break;
                }

                Log.d("brad", "Hello, " + name);
                i++;
                publishProgress(i, name);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

            return isCancel?"Cancel!":"Game Over";
        }
        @Override
        protected void onPostExecute(String end) {
            super.onPostExecute(end);
            Log.d("brad", "onPostExecute:" + end);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            Log.d("brad", "onProgressUpdate");
            mesg.setText((Integer)values[0] + ":" + (String)values[1]);
        }

        @Override
        protected void onCancelled(String end) {
            super.onCancelled(end);
            Log.d("brad", "onCancelled:" +end);
        }
    }

}
