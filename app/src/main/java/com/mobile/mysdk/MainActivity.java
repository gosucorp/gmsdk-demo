package com.mobile.mysdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.gamo.sdk.GamoSDK;
import com.gamo.sdk.DialogLoginID.OnLoginListener;
import com.gamo.sdk.DialogLoginID.OnLogoutListener;
import com.gamo.sdk.utils.MySDKConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;


public class MainActivity extends Activity {
    private GamoSDK mGamo = null;
    private ScrollView layoutMain = null;
    private RelativeLayout layoutIAP = null;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ClientID = "m980.dQF1Lkx17YEclineQ0";
        // init GAMO SDK
<<<<<<< HEAD
        mGamo = new GamoSDK(this, "A");
        mGamo.setLanguage("zh-rTW");
=======
        mGamo = new GamoSDK(this, ClientID);
>>>>>>> 331510a19dbf25661c8a4d590a33b9400ac2895b

        // init for activity
        final TextView tv_UID = (TextView) this.findViewById(R.id.txt_uID);
        tv_UID.setText("UserName: ");
        layoutMain = (ScrollView) findViewById(R.id.scrollView);
        layoutMain.setVisibility(View.GONE);

        layoutIAP = (RelativeLayout) findViewById(R.id.layout_iap);
        layoutIAP.setVisibility(View.GONE);

        // for login SDK
        final Button btnVaoGame = (Button) findViewById(R.id.btnVaoGame);
        btnVaoGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGamo.showLoginView(new OnLoginListener() {

                    @Override
                    public void onLoginSuccessful(String UserId, String UserName, String accesstoken) {
                        btnVaoGame.setVisibility(View.GONE);
                        layoutMain.setVisibility(View.VISIBLE);
                        tv_UID.setText("UseName: " + UserName);
                    }

                    public void onBindIDSuccessful(String username){

                    }

                    @Override
                    public void onLoginFailed() {
                    }
                });
            }
        });

        // for payment IAP
        findViewById(R.id.btn_payment_iap).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                layoutIAP.setVisibility(View.VISIBLE);
            }
        });

        // for share image
        findViewById(R.id.btn_facebook_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mGamo.shareLinkToFacebook(Uri.parse("https://388.gsscdn.com/ShareFB.jpg"), new FacebookCallback<Sharer.Result>(){
                                @Override
                                public void onSuccess(Sharer.Result o) {
                                    //Log.d("ShareFB", "Success");
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel() {
                                    //Log.d("ShareFB", "Cancel");
                                    Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(FacebookException error) {
                                    //Log.d("ShareFB", "Fail");
                                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // for share link
        findViewById(R.id.btn_share_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mGamo.shareLinkToFacebook(Uri.parse("https://play.google.com/store/apps/details?id=com.jwsvn.gg"), new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result o) {
                            //Log.d("ShareFB", "Success");
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            //Log.d("ShareFB", "Cancel");
                            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            //Log.d("ShareFB", "Fail");
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //for tracking app open
        findViewById(R.id.btn_trackopen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverId = "1";
                String roleId = "9848485";
                String roleName = "BigSDK";
                mGamo.gamoTrackingAppOpen(serverId, roleId, roleName);
            }
        });

        // for logout
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGamo.logoutAccount(new OnLogoutListener() {
                    public void onLogoutSuccessful() {
                        btnVaoGame.setVisibility(View.VISIBLE);
                        layoutMain.setVisibility(View.GONE);
                    }

                    public void onLogoutFailed() {
                    }
                });
            }
        });

        //////////// FOR IAP /////////////
        findViewById(R.id.btn_cancel_iap).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                layoutIAP.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_pk1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layoutIAP.setVisibility(View.GONE);
                callIAP(1);
            }
        });

        findViewById(R.id.btn_pk2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layoutIAP.setVisibility(View.GONE);
                callIAP(2);
            }
        });

        findViewById(R.id.btn_pk3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layoutIAP.setVisibility(View.GONE);
                callIAP(3);
            }
        });

        //
        this.printKeyHash(this);
    }
    ///////////// CAL IAP ////////////////
    /**
     *
     * @param type
     */
    private void callIAP(int type) {
        try {
            String orderid = String.valueOf(Calendar.getInstance().getTimeInMillis()) + "_10095276_2_13_11";
            String orderInfo = "";
            String amount = "0";//VNĐ
            String sku = "";
            switch (type) {
                case 1:
                    sku = MySDKConstant.iap_product_ids.get(0);// com.com.phlmobile.thanlong.sdkdemo.p4
                    amount = "0.99";
                    orderInfo = "Get 99 KNB";
                    break;
                case 2:
                    sku = MySDKConstant.iap_product_ids.get(1);// com.com.phlmobile.thanlong.sdkdemo.p5
                    amount = "1.99";
                    orderInfo = "Get 199 KNB";
                    break;
                case 3:
                    sku = MySDKConstant.iap_product_ids.get(2);// com.com.phlmobile.thanlong.sdkdemo.p6
                    amount = "2.99";
                    orderInfo = "Get 299 KNB";
                    break;
            }
            //Log.e("SKU==", sku);

            orderInfo = URLEncoder.encode(orderInfo, "UTF-8");
            String extraInfo = "YourContent";
            String character = "YourCharacterID";
            Context context = MainActivity.this;
            mGamo.showIAP(context, sku, orderid, orderInfo, amount, MySDKConstant.username,"s1", character, extraInfo,
                    new GamoSDK.OnPaymentIAPListener(){
                        public void onPaymentSuccessful(String msg) {
                            mGamo.showConfirmDialog("Payment Success!", msg);
                        }

                        public void onPaymentFailed(String msg, int errCode, String iapToken) {
                            mGamo.showConfirmDialog("Payment Fail!", msg + " (error_code: " + errCode + ")");
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if(mGamo != null)
                mGamo.gamoSDKOnPause();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if(mGamo != null)
                mGamo.gamoSDKOnResume();
        } catch (Exception e) {
        }
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {// very important
        try {
            if (mGamo != null)
                mGamo.gamoSDKOnDestroy();
        } catch (Exception e) {}
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGamo.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("KeyHash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
