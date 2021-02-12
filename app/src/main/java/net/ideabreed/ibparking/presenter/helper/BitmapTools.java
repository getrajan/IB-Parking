package net.ideabreed.ibparking.presenter.helper; /*
 * Created by Rajan Karki on 2/9/21
 * Copyright @2021
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BitmapTools {

    public static byte[] bitmap2PrinterBytes(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //Log.v("hello", "height?:"+height);
        int startX = 0;
        int startY = 0;
        int offset = 0;
        int scansize = width;
        int writeNo = 0;
        int rgb = 0;
        int colorValue = 0;
        int[] rgbArray = new int[offset + (height - startY) * scansize
                + (width - startX)];
        bitmap.getPixels(rgbArray, offset, scansize, startX, startY,
                width, height);

        int iCount = (height % 8);
        if (iCount > 0) {
            iCount = (height / 8) + 1;
        } else {
            iCount = (height / 8);
        }

        byte[] mData = new byte[iCount * width];

        //Log.v("hello", "myiCount?:"+iCoun t);
        for (int l = 0; l <= iCount - 1; l++) {
            //Log.v("hello", "iCount?:"+l);
            //Log.d("hello", "l?:"+l);
            for (int i = 0; i < width; i++) {
                int rowBegin = l * 8;
                //Log.v("hello", "width?:"+i);
                int tmpValue = 0;
                int leftPos = 7;
                int newheight = ((l + 1) * 8 - 1);
                //Log.v("hello", "newheight?:"+newheight);
                for (int j = rowBegin; j <= newheight; j++) {
                    //Log.v("hello", "width?:"+i+"  rowBegin?:"+j);
                    if (j >= height) {
                        colorValue = 0;
                    } else {
                        rgb = rgbArray[offset + (j - startY) * scansize + (i - startX)];
                        if (rgb == -1) {
                            colorValue = 0;
                        } else {
                            colorValue = 1;
                        }
                    }
                    //Log.d("hello", "rgbArray?:"+(offset + (j - startY)
                    //		* scansize + (i - startX)));
                    //Log.d("hello", "colorValue?:"+colorValue);
                    tmpValue = (tmpValue + (colorValue << leftPos));
                    leftPos = leftPos - 1;
                }
                mData[writeNo] = (byte) tmpValue;
                writeNo++;
            }
        }

        return mData;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}