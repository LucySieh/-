package com.example.test



import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var pwd: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnreg: Button
    // private lateinit var mysql: Mysql
    private lateinit var db: SQLiteDatabase
    private lateinit var sp1: SharedPreferences
    private lateinit var sp2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)  // 用户名输入框
        pwd = findViewById(R.id.pwd)    // 密码输入框
        btnlogin = findViewById(R.id.login)  // 登录按钮
        btnreg = findViewById(R.id.reg)      // 注册按钮
        sp1 = getSharedPreferences("useinfo", MODE_PRIVATE)
        sp2 = getSharedPreferences("username", MODE_PRIVATE)

        name.setText(sp1.getString("usname", null))
        pwd.setText(sp1.getString("uspwd", null))
        // mysql = Mysql(this, "Userinfo", null, 1)  // 建数据库或者取数据库
        // db = mysql.readableDatabase

        btnlogin.setOnClickListener {
            val username = name.text.toString()
            val password = pwd.text.toString()  // 获取用户输入的用户名和密码

            // 查询用户名和密码相同的数据
            val cursor: Cursor = db.query(
                "logins",
                arrayOf("usname", "uspwd"),
                "usname=? and uspwd=?",
                arrayOf(username, password),
                null,
                null,
                null
            )

            val flag = cursor.count  // 查询出来的记录项的条数，若没有该用户则为0条
            if (flag != 0) {  // 若查询出的记录不为0，则进行跳转操作
                //val intent = Intent(this, Welcome::class.java)  // 设置页面跳转
                val editor = sp2.edit()
                cursor.moveToFirst()  // 将光标移动到position为0的位置，默认位置为-1
                val loginname = cursor.getString(0)
                editor.putString("Loginname", loginname)
                editor.apply()  // 将用户名存到SharedPreferences中
                startActivity(intent)
            } else {
                Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_LONG).show()  // 提示用户信息错误或没有账号
            }
        }

        btnreg.setOnClickListener {
            //val intent = Intent(this, Register::class.java)  // 跳转到注册页面
            startActivity(intent)
            Toast.makeText(this, "前往注册！", Toast.LENGTH_SHORT).show()
        }
    }
}
