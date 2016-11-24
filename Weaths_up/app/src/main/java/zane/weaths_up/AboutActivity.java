package zane.weaths_up;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView description;
    private TextView github_addr;
    private TextView linkedin_addr;
    private TextView website_addr;
    private TextView gmail_addr;
    private final String github  = "https://github.com/zaneran";
    private final String linkedin = "https://www.linkedin.com/in/zhuoranhuang";
    private final String gmail = "zaneranhuang@gmail.com";
    private final String website = "http://zhang-ning.org";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FF40977A"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new NavigationListener());

        String Description = "Weath's up, a light app designed for you, " +
                "supports searching in multiple language, " +
                "enables saving your favorite cities, " +
                "optional switch for temperature unit and time format." +
                "\n" +
                "All you need is to grasp it, " +
                "and enjoy it." +
                "\n" +
                "\n" +
                "Current Version : V 2.0.0" +
                "\n" +
                "\n" +
                "Application Developer : Zhuoran Huang" +
                "\n" +
                "App Icon Designer : Ning Zhang";

        description = (TextView) findViewById(R.id.app_description);
        description.setText(Description);

        github_addr = (TextView) findViewById(R.id.github_addr);
        linkedin_addr = (TextView) findViewById(R.id.linkedin_addr);
        website_addr = (TextView) findViewById(R.id.website_addr);
        gmail_addr = (TextView) findViewById(R.id.gmail_addr);

        github_addr.setOnClickListener(new TextViewListener());
        linkedin_addr.setOnClickListener(new TextViewListener());
        website_addr.setOnClickListener(new TextViewListener());
        gmail_addr.setOnClickListener(new TextViewListener());
    }

    public class TextViewListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.github_addr:
                    Uri uri = Uri.parse(github);
                    Intent github_intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(github_intent);
                    break;

                case R.id.linkedin_addr:
                    Uri uri1 = Uri.parse(linkedin);
                    Intent linkedin_intent = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(linkedin_intent);
                    break;

                case R.id.gmail_addr:
                    Intent gmail_intent = new Intent(Intent.ACTION_SEND);
                    gmail_intent.setType("plain/text");
                    gmail_intent.putExtra(Intent.EXTRA_EMAIL, new String[] { gmail });
                    startActivity(Intent.createChooser(gmail_intent, ""));
                    break;

                case R.id.website_addr:
                    Uri uri2 = Uri.parse(website);
                    Intent website_intent = new Intent(Intent.ACTION_VIEW, uri2);
                    startActivity(website_intent);
                    break;

                default:
                    break;
            }
        }
    }



    public class NavigationListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MainActivity.INTENT_KEY, true);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY, true);
        startActivity(intent);
        finish();
    }
}
