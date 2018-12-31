package ir.tanyar.app;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class Font extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Yekan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
