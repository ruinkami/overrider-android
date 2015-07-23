package com.ruinkami.overrider.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.ruinkami.overrider.entity.TideObject;
import com.ruinkami.overrider.util.URLBase64;

/**
 * Created by ruinkami on 2015/7/15.
 */
public class BaseActivity extends FragmentActivity {

    public int getIntParam(String name, int defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Integer.parseInt(val);
            }
        } catch (Exception e) {
        }

        return i.getIntExtra(name, defaultValue);
    }

    public int getIntParam(String name) {
        return getIntParam(name, 0);
    }

    //
    // get params util method
    //

    public String getStringParam(String name) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                if (val != null)
                    return val;
            }
        } catch (Exception e) {
        }

        return i.getStringExtra(name);
    }

    public double getDoubleParam(String name, double defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Double.parseDouble(val);
            }
        } catch (Exception e) {
        }

        return i.getDoubleExtra(name, defaultValue);
    }

    public double getDoubleParam(String name) {
        return getDoubleParam(name, 0);
    }

    public TideObject getObjectParam(String name) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                if (val != null) {
                    byte[] bytes = URLBase64.decode(val);
                    return new TideObject(bytes, 0, bytes.length);
                }
            }
        } catch (Exception e) {
        }

        return i.getParcelableExtra(name);
    }

    public boolean getBooleanParam(String name, boolean defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                if (!TextUtils.isEmpty(val))
                    return Boolean.parseBoolean(val);
            }
        } catch (Exception e) {
        }
        return i.getBooleanExtra(name, defaultValue);
    }

    public boolean getBooleanParam(String name) {
        return getBooleanParam(name, false);
    }

    public long getLongParam(String name) {
        return getLongParam(name, 0L);
    }

    public long getLongParam(String name, long defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Long.parseLong(val);
            }
        } catch (Exception e) {
        }

        return i.getLongExtra(name, defaultValue);
    }

    public byte getByteParam(String name) {
        return getByteParam(name, (byte) 0);
    }

    public byte getByteParam(String name, byte defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Byte.parseByte(val);
            }
        } catch (Exception e) {
        }

        return i.getByteExtra(name, defaultValue);
    }

    public float getFloatParam(String name) {
        return getFloatParam(name, 0f);
    }

    public float getFloatParam(String name, float defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Float.parseFloat(val);
            }
        } catch (Exception e) {
        }

        return i.getFloatExtra(name, defaultValue);
    }

    public short getShortParam(String name) {
        return getShortParam(name, (short) 0);
    }

    public short getShortParam(String name, short defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Short.parseShort(val);
            }
        } catch (Exception e) {
        }

        return i.getShortExtra(name, defaultValue);
    }

    public char getCharParam(String name) {
        return getCharParam(name, (char) 0);
    }

    public char getCharParam(String name, char defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return val.charAt(0);
            }
        } catch (Exception e) {
        }

        return i.getCharExtra(name, defaultValue);
    }
}
