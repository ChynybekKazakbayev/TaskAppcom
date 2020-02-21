package com.geektech.taskapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Toster;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private EditText editText, editCode;
    private Button btn;
    FirebaseAuth auth;
    String codeSent;
    private LinearLayout code, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        auth = FirebaseAuth.getInstance();
        editText = findViewById(R.id.editNumber);
        editCode = findViewById(R.id.editCode);
        code = findViewById(R.id.code);
        number = findViewById(R.id.number);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed" + e.getMessage());

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeSent = s;
                Log.e("TAG", "onCodeSent");
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.e("TAG", "onCodeAutoRetrievalTimeOut");
            }
        };


    }

    private void sendVerificationCode() {
        String phoneNum = editText.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNum,
                30,
                TimeUnit.SECONDS,
                this,
                callbacks
        );
    }

    private void verifySignInCode() {
        String code = editCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            editCode.setError("Code is required");
            editCode.requestFocus();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toster.show("Ошибка авторизации" + task.getException().getMessage());
                        }
                    }

                });
    }

//    public void onClick(View view) {
//        String phone = editText.getText().toString().trim();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phone, 60, TimeUnit.SECONDS, this, callbacks
//        );


    public void OnCode(View view) {
        verifySignInCode();

    }

    public void onContinue(View view) {
        sendVerificationCode();
        number.setVisibility(View.GONE);
        code.setVisibility(View.VISIBLE);
    }
}
