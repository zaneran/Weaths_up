package zane.weaths_up.Layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import zane.weaths_up.R;

/**
 * Created by zaneran on 10/3/2016.
 */
public class CustomLayout extends RelativeLayout implements Target {

        public CustomLayout(Context context) {
            super(context);
        }

        public CustomLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.i("start", "Setting Background!");
            setBackground(new BitmapDrawable(getResources(), bitmap));
            Log.i("Completed","Setting Background!");
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            setBackgroundResource(R.drawable.splash_bg);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            //Set your placeholder
        }
}

