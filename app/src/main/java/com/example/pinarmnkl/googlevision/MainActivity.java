package com.example.pinarmnkl.googlevision;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.lang.reflect.GenericArrayType;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnProgreses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.imageView);
        btnProgreses=(Button)findViewById(R.id.btnProgreses);

        final Bitmap myBitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.tom);

        imageView.setImageBitmap(myBitmap);

        final Paint  rectPaint = new Paint();
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.WHITE);
        rectPaint.setStyle(Paint.Style.STROKE);

        final Bitmap tempBitmap=Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(),Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(tempBitmap);

        canvas.drawBitmap(myBitmap,0,0,null);

      btnProgreses.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
          {
              FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())

              .setTrackingEnabled(false)
                      .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                      .setMode(FaceDetector.FAST_MODE)
                      .build();

              if(!faceDetector.isOperational())
              {

                  Toast.makeText(MainActivity.this, "FaceDedector could not be set up your device", Toast.LENGTH_SHORT).show();
                  return;
                  
              }

              Frame frame= new Frame.Builder().setBitmap(myBitmap).build();

              SparseArray<Face> sparceArray=faceDetector.detect(frame);

              for(int i=0; i<sparceArray.size(); i++)
               {

                  Face face = sparceArray.valueAt(i);
                  float x1=face.getPosition().x;
                  float y1=face.getPosition().y;
                  float x2=x1+face.getWidth();
                  float y2=y1+face.getHeight();

                  RectF rectF= new RectF(x1,y1,x2,y2);
                  canvas.drawRoundRect(rectF,2,2,rectPaint);

              }

              imageView.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));


          }
      });


    }
}
