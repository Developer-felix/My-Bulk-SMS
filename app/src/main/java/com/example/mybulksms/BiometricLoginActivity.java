package com.example.mybulksms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.Executor;

public class BiometricLoginActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_login);

        Button biometricButton = findViewById(R.id.biometricButton);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                // Biometric authentication succeeded, open the GroupListActivity.
                Intent intent = new Intent(BiometricLoginActivity.this, GroupListActivity.class);
                startActivity(intent);
                finish(); // Finish the BiometricLoginActivity to prevent going back to it.
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle authentication errors, e.g., if the user cancels or fails authentication.
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Biometric authentication failed, handle it as needed.
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Place your finger on the sensor")
                .setNegativeButtonText("Use PIN/Password")
                .build();

        biometricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}
