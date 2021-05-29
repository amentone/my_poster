package com.anmentone.my_poster;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import com.mypos.smartsdk.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.content.Context;
import android.app.Activity;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;


/**
 * MyPosterPlugin
 */
public class MyPosterPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private Activity activity;
    private Result result;
    private int resultStatus = -1;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "my_poster");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        activity = binding.getActivity();

        binding.addActivityResultListener((int requestCode, int resultCode, Intent data) -> {
            System.out.println("requestCode " + requestCode);
            System.out.println("resultCode " + resultCode);
            IntentLogger.logFullContent(data);
            Bundle bundle = data.getExtras();

            for(String extraKey : bundle.keySet()) {
                if(extraKey.equals("status")) {
                    resultStatus = Integer.parseInt(bundle.get(extraKey).toString());
                }
            }

            result.success(resultStatus);
    	    return true; 
	});

    }

    @Override
    public void onDetachedFromActivity() {
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        System.out.println(this.result);

        this.result = result;

        System.out.println(this.result);

        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("makePayment")) {

            double amount = call.argument("amount");
            int printCode = call.argument("printCode");
            System.out.println(amount);
            MyPOSPayment payment = MyPOSPayment.builder()
                    .productAmount(amount)
                    .currency(Currency.EUR)
                    .printCustomerReceipt(2)
                    .printMerchantReceipt(printCode)
                    .build();
            MyPOSAPI.openPaymentActivity(activity, payment, 1);
        } else if (call.method.equals("refund")) {

            double amount = call.argument("amount");
            int printCode = call.argument("printCode");
            System.out.println(amount);

            MyPOSRefund refund = MyPOSRefund.builder()
                    .refundAmount(amount)
                    .currency(Currency.EUR)
                    .printCustomerReceipt(2)
                    .printMerchantReceipt(printCode)
                    .build();

            MyPOSAPI.openRefundActivity(activity, refund, 2);
            result.success(resultStatus);

        } else {
            result.notImplemented();
        }
    }
    
    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
