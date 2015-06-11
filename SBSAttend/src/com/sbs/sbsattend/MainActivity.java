package com.sbs.sbsattend;

import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText userName, password;
	private CheckBox rem_pw, auto_login;
	private String userNameValue, passwordValue;
	private SharedPreferences sp;
	private Person p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		// 获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		userName = (EditText) findViewById(R.id.et_zh);
		password = (EditText) findViewById(R.id.et_mima);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);

		// 判断记住密码多选框的状态
		if (sp.getBoolean("ISCHECK", false)) {
			// 设置默认是记录密码状态
			rem_pw.setChecked(true);
			userName.setText(sp.getString("USER_NAME", ""));
			password.setText(sp.getString("PASSWORD", ""));
			// 判断自动登陆多选框状态
			if (sp.getBoolean("AUTO_ISCHECK", false)) {
				// 设置默认是自动登录状态
				auto_login.setChecked(true);
				step();
			}
		} 
  
        //监听记住密码多选框按钮事件  
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (rem_pw.isChecked()) {  
                      
                    System.out.println("记住密码已选中");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("记住密码没有选中");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });  
          
        //监听自动登录多选框事件  
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (auto_login.isChecked()) {  
                    System.out.println("自动登录已选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();  
  
                } else {  
                    System.out.println("自动登录没有选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();  
                }  
            }  
        });  
	}
	public void step()
	{
		Intent intent;
		//身份判断，界面跳转
		try {
			p = Logic.login(userName.getText().toString(), password.getText().toString());
			switch(p.getPermission())
			{
			case 0:
				 Toast.makeText(MainActivity.this,"身份：员工", Toast.LENGTH_LONG).show();  
				 intent = new Intent(MainActivity.this, StaffActivity.class);
				 intent.putExtra("name", p.getName());
				 MainActivity.this.startActivity(intent);
				 break;
			case 1:
				 Toast.makeText(MainActivity.this,"身份：领导", Toast.LENGTH_LONG).show();
				 intent = new Intent(MainActivity.this, AdminActivity.class);
				 intent.putExtra("quotan", p.getQuotan());
				 MainActivity.this.startActivity(intent);
				 break;
			default:
				 Toast.makeText(MainActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();  
				 break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sys_login(View v)
	{
		userNameValue = userName.getText().toString();  
        passwordValue = password.getText().toString();  
          
        
        if(rem_pw.isChecked())  
        {  
         //记住用户名、密码、  
          Editor editor = sp.edit();  
          editor.putString("USER_NAME", userNameValue);  
          editor.putString("PASSWORD",passwordValue);  
          editor.commit();  
        }  
        //跳转界面  
        step();
        //finish();  

	}
	
	public void sys_quit(View v)
	{
		finish();
	}
}
