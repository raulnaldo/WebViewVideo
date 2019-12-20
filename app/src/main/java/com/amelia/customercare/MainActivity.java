package com.amelia.customercare;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.SslErrorHandler;
import android.net.http.SslError;

class SSLTolerentWebViewClient extends WebViewClient {
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

    private WebView mWebView;

    private String mCustomTestResolutions = "https://webrtc.github.io/samples/src/content/getusermedia/resolution/";
    private String mCustomIvrpURL = "https://webrtc.demo.ivrpowers.com/webclient?theme=senior-telecom&to=forward&autocall=true";
    private String mCustomTokBoxURL = "https://raulnaldo.github.io/crm/Video.html";
    private String mCustomQvgaURL = "https://webrtc.dev.ivrpowers.com/amelia-performance/qvga/";
    private String mCustomVgaURL = "https://webrtc.dev.ivrpowers.com/amelia-performance/vga/";
    private String mCustomHDURL = "https://webrtc.dev.ivrpowers.com/amelia-performance/hd/";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ivrp:
                    mWebView.loadUrl(mCustomIvrpURL);
                    return true;
                case R.id.navigation_tokbox:
                    mWebView.loadUrl(mCustomTokBoxURL);
                    return true;
                case R.id.navigation_Qvga:
                    mWebView.loadUrl(mCustomQvgaURL);
                    return true;
                case R.id.navigation_Vga:
                    mWebView.loadUrl(mCustomVgaURL);
                    return true;
                case R.id.navigation_exit:
                    finish();
                    System.exit(0);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){
            setContentView(R.layout.activity_main);
        }



        setContentView(R.layout.activity_main);
        checkForAndAskForAudioPermisions();
        checkForAndAskForAudioModifyPermisions();
        checkForAndAskForCameraPermisions();

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createWebView();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void createWebView() {

        mWebView = (WebView) findViewById(R.id.webview);
        setUpWebViewDefaults(mWebView);
        mWebView.loadUrl(mCustomTestResolutions);
        mWebView.setWebViewClient(new SSLTolerentWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {

            public boolean onConsoleMessage(ConsoleMessage  m ) {
                Log.d("getUserMedia, WebView", m.message() + " -- From line "
                        + m.lineNumber() + " of "
                        + m.sourceId());

                return true;
            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                // getActivity().
                MainActivity.this.runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        // Below isn't necessary, however you might want to:
                        // 1) Check what the site is and perhaps have a blacklist
                        // 2) Have a pop up for the user to explicitly give permission
                        request.grant(request.getResources());

                        //if(request.getOrigin().toString().equals("https://dominio.com") ||
                        //        request.getOrigin().toString().equals("https://webrtc.github.io/")) {
                        //    request.grant(request.getResources());
                        //} else {
                        //    request.deny();
                        //}
                    }
                });
            }
        });
    }

    private void checkForAndAskForPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)) {
                //
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        } else {
            // Permission has already been granted
            createWebView();
        }
    }

    private void checkForAndAskForCameraPermisions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)) {
                //
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        } else {
            // Permission has already been granted
            createWebView();
        }
    }
    private void checkForAndAskForAudioPermisions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.RECORD_AUDIO)) {
                //
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO}, CAMERA_REQUEST);
            }
        } else {
            // Permission has already been granted
            //createWebView();
        }
    }
    private void checkForAndAskForAudioModifyPermisions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                //
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS}, CAMERA_REQUEST);
            }
        } else {
            // Permission has already been granted
            //createWebView();
        }
    }
    private void setUpWebViewDefaults(WebView webView) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);


        // Enable remote debugging via chrome://inspect
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.clearCache(true);
        webView.clearHistory();
        webView.setWebViewClient(new WebViewClient());
    }

}
