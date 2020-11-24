package com.pla_bear.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Message;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Commons {
    static public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    static public void setPermissions(Activity activity, int code, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    static public void showToast(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static public String sha256(String str) {
        String hash;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            byte[] bytes = messageDigest.digest();
            hash = bytesToHexString(bytes);
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            hash = null;
        }

        return hash;
    }

    @SuppressWarnings("StringBufferMayBeStringBuilder")
    static public String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
