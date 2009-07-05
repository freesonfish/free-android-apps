package com.hax;

import android.webkit.WebChromeClient;
import java.io.IOException;
import com.hax.NanoHTTPD;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Local extends Activity implements OnClickListener {

	WebView webview;
	NanoHTTPD x;
	
	/* To ensure we don't open a new window each click. */
	public class LocalWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	view.loadUrl(url);
        	return(true);
        }
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        webview = (WebView) findViewById(R.id.webview);
        webview.setVisibility(android.view.View.INVISIBLE);
        
        Button button = (Button)findViewById(R.id.btClose);
        button.setOnClickListener(this);

        boolean success = true;
        
        TextView tv = (TextView) this.findViewById(R.id.textview);
        tv.setText("Ready!");

        try {
        	x = new NanoHTTPD(8080);
        }
        catch(Exception e) {
        	tv.setText(e.toString());
        	success = false;
        }
        
        if (success) {
        	tv.setText("Running on local port 8080...");
        	try {
		        webview = (WebView) findViewById(R.id.webview);
		        webview.setWebViewClient(new LocalWebViewClient());
		        
		        webview.getSettings().setJavaScriptEnabled(true);
		        webview.loadUrl("http://localhost:8080/");
		        webview.setVisibility(android.view.View.VISIBLE);
        	}
        	catch (Exception e) {
        		success = false;
        		tv.setText(e.toString());
        	}
        }
    }

    public void onClick(View v) {
    	try {
    		x.stop();
    		webview.loadUrl("http://localhost:8080/");
    	}
    	catch (Exception e) {
    	}
    	this.finish();
    }
}
