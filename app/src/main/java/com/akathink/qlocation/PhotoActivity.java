package com.akathink.qlocation;

import android.Manifest;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PhotoActivity extends AppCompatActivity {

    private Button mSelectedBtn;
    private TextView mLocationTV;
    private int REQUEST_GET_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        mSelectedBtn = findViewById(R.id.select_photo_btn);
        mLocationTV = findViewById(R.id.location_tv);


        mSelectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                /*AndPermission.with(PhotoActivity.this)
                        .runtime()
                        .permission(Manifest.permission.READ_MEDIA_IMAGES)//READ_MEDIA_IMAGES
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                selectImage();
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                Toast.makeText(PhotoActivity.this, "权限拒绝", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .start();*/

                selectImage();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            getInfo(uri);
        }
    }

    /**
     * @param uri 图片路径
     */
    private void getInfo(Uri uri) {
        try {

            ///mnt/content/media/external/images/media/768

            InputStream inputStream = getContentResolver().openInputStream(uri);
            ExifInterface exifInterface = new ExifInterface(inputStream);
            String guangquan = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
            String shijain = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            String baoguangshijian = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
            String jiaoju = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            String chang = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String kuan = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            String moshi = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            String zhizaoshang = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String iso = exifInterface.getAttribute(ExifInterface.TAG_ISO);
            String jiaodu = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            String baiph = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            String altitude_ref = exifInterface.getAttribute(ExifInterface
                    .TAG_GPS_ALTITUDE_REF);
            String altitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE);
            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String latitude_ref = exifInterface.getAttribute(ExifInterface
                    .TAG_GPS_LATITUDE_REF);
            String longitude_ref = exifInterface.getAttribute(ExifInterface
                    .TAG_GPS_LONGITUDE_REF);
            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String timestamp = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            String processing_method = exifInterface.getAttribute(ExifInterface
                    .TAG_GPS_PROCESSING_METHOD);

            //转换经纬度格式
            double lat = score2dimensionality(latitude);
            double lon = score2dimensionality(longitude);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("光圈 = " + guangquan + "\n")
                    .append("时间 = " + shijain + "\n")
                    .append("曝光时长 = " + baoguangshijian + "\n")
                    .append("焦距 = " + jiaoju + "\n")
                    .append("长 = " + chang + "\n")
                    .append("宽 = " + kuan + "\n")
                    .append("型号 = " + moshi + "\n")
                    .append("制造商 = " + zhizaoshang + "\n")
                    .append("ISO = " + iso + "\n")
                    .append("角度 = " + jiaodu + "\n")
                    .append("白平衡 = " + baiph + "\n")
                    .append("海拔高度 = " + altitude_ref + "\n")
                    .append("GPS参考高度 = " + altitude + "\n")
                    .append("GPS时间戳 = " + timestamp + "\n")
                    .append("GPS定位类型 = " + processing_method + "\n")
                    .append("GPS参考经度 = " + latitude_ref + "\n")
                    .append("GPS参考纬度 = " + longitude_ref + "\n")
                    .append("GPS经度 = " + lat + "\n")
                    .append("GPS经度 = " + lon + "\n");

            //将获取的到的信息设置到TextView上
            mLocationTV.setText(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将 112/1,58/1,390971/10000 格式的经纬度转换成 112.99434397362694格式
     *
     * @param string 度分秒
     * @return 度
     */
    private double score2dimensionality(String string) {
        double dimensionality = 0.0;
        if (null == string) {
            return dimensionality;
        }

        //用 ，将数值分成3份
        String[] split = string.split(",");
        for (int i = 0; i < split.length; i++) {

            String[] s = split[i].split("/");
            //用112/1得到度分秒数值
            double v = Double.parseDouble(s[0]) / Double.parseDouble(s[1]);
            //将分秒分别除以60和3600得到度，并将度分秒相加
            dimensionality = dimensionality + v / Math.pow(60, i);
        }
        return dimensionality;
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Browser Image..."), REQUEST_GET_IMAGE);
    }
}
