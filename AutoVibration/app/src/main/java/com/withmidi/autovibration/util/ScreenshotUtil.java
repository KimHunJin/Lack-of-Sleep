package com.withmidi.autovibration.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.widget.ScrollView;
import android.widget.Toast;

import com.withmidi.autovibration.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotUtil {

    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() ;
    public static String pathfile = FILE_SAVEPATH + "/ScreenshotUtil.png";
    public static int h = 0;

    public static void getBitmapByView(Context mContext, final ScrollView scrollView) {
        h = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(android.R.color.white);
        }

        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);

        Bitmap head = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share_term_table_header);
        Bitmap foot = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share_term_table_footer);
        Bitmap v = toConformBitmap(head, bitmap, foot);

        File savedir = new File(FILE_SAVEPATH);
        if (!savedir.exists()) {
            savedir.mkdirs();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(pathfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext,"저장실패",Toast.LENGTH_SHORT).show();
        }
        try {
            if (null != out) {
                v.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
            Toast.makeText(mContext,"저장완료",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext,"저장실패",Toast.LENGTH_SHORT).show();
        }

    }

    public static Bitmap toConformBitmap(Bitmap head, Bitmap kebiao, Bitmap san) {
        if (head == null) {
            return null;
        }
        int headWidth = head.getWidth();
        int kebianwidth = kebiao.getWidth();
        int fotwid = san.getWidth();

        int headHeight = head.getHeight();
        int kebiaoheight = kebiao.getHeight();
        int footerheight = san.getHeight();

        Bitmap newbmp = Bitmap.createBitmap(kebianwidth, headHeight + kebiaoheight + footerheight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        cv.drawBitmap(head, 0, 0, null);


        if (headWidth < kebianwidth) {
            Bitmap ne = Bitmap.createBitmap(kebianwidth - headWidth, headHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, headWidth, 0, null);
        }
        cv.drawBitmap(kebiao, 0, headHeight, null);
        cv.drawBitmap(san, 0, headHeight + kebiaoheight, null);

        if (fotwid < kebianwidth) {
            Bitmap ne = Bitmap.createBitmap(kebianwidth - fotwid, footerheight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, fotwid, headHeight + kebiaoheight, null);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();

        head.recycle();
        kebiao.recycle();
        san.recycle();
        return newbmp;
    }


}
