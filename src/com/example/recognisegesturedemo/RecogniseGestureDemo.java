package com.example.recognisegesturedemo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class RecogniseGestureDemo extends Activity {

	GestureOverlayView gestureView;  //�������Ʊ༭���
	GestureLibrary gestureLibrary; //��¼�ֻ������е����ƿ�
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //��ȡ��һ���������������ƿ�
        gestureLibrary = GestureLibraries.fromFile("/sdcard/mygestures"); 
        
        if (gestureLibrary.load())
        {
        	Toast.makeText(RecogniseGestureDemo.this, "�����ļ�װ�سɹ���", 8000).show();
        }
        else 
        {
        	Toast.makeText(RecogniseGestureDemo.this, "�����ļ�װ��ʧ�ܣ�", 8000).show();
        }
        
        gestureView = (GestureOverlayView)findViewById(R.id.gesture); //��ȡ���Ʊ༭���
        //Ϊ���Ʊ༭������¼�������
        gestureView.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			
			@Override
			public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
				// TODO Auto-generated method stub
				//ʶ���û��ոջ��Ƶ�����
				ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture); 
				ArrayList<String> result = new ArrayList<String>();
				//���������ҵ���Prediction����
				for (Prediction pred : predictions)
				{
					//ֻ�����ƶȴ���2.0�����ƲŻᱻ���
					if (pred.score > 2.0)
					{
						result.add("�����ơ�" + pred.name + "�������ƶ�Ϊ��" + pred.score);
					}
				}
				
				if (result.size() > 0)
				{
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecogniseGestureDemo.this, 
							android.R.layout.simple_dropdown_item_1line);
					//ʹ��һ����List�ĶԻ�������ʾ����ƥ�������
					new AlertDialog.Builder(RecogniseGestureDemo.this)
					.setAdapter(adapter, null)
					.setPositiveButton("����", null).show();
				}
				else
				{
					Toast.makeText(RecogniseGestureDemo.this, "�޷��ҵ���ƥ������ƣ�", 8000).show();
				}
			}
		});
    }
}
