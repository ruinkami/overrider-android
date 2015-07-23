package com.ruinkami.overrider.util;

/**
 * Created by ruinkami on 2015/7/15.
 */
public class URLBase64 {
    private static final char[] base = new char[64];
    private static final int[] reverse = new int[128];

    static {
        int i = 0;

        char c;
        for(c = 65; c <= 90; ++c) {
            base[i] = c;
            reverse[c] = i++;
        }

        for(c = 97; c <= 122; ++c) {
            base[i] = c;
            reverse[c] = i++;
        }

        for(c = 48; c <= 57; ++c) {
            base[i] = c;
            reverse[c] = i++;
        }

        base[i] = 45;
        reverse[45] = i++;
        base[i] = 95;
        reverse[95] = i;
    }

    private URLBase64() {
    }

    public static String encode(byte[] buf, int start, int length) {
        char[] ar = new char[(length + 2) / 3 * 4];
        int a = 0;

        byte b2;
        byte mask;
        for(int i = 0; i < length; ar[a++] = base[b2 & mask]) {
            byte b0 = buf[start + i++];
            byte b1 = i < length?buf[start + i++]:0;
            b2 = i < length?buf[start + i++]:0;
            mask = 63;
            ar[a++] = base[b0 >> 2 & mask];
            ar[a++] = base[(b0 << 4 | (b1 & 255) >> 4) & mask];
            ar[a++] = base[(b1 << 2 | (b2 & 255) >> 6) & mask];
        }

        switch(length % 3) {
            case 1:
                --a;
            case 2:
                --a;
            default:
                return new String(ar, 0, a);
        }
    }

    public static String encode(byte[] buf) {
        return encode(buf, 0, buf.length);
    }

    public static byte[] decode(String s) {
        int dlen = (s.length() + 3) / 4 * 4;
        int delta = dlen - s.length();
        byte[] buffer = new byte[dlen * 3 / 4 - delta];
        short mask = 255;
        int index = 0;

        for(int i = 0; i < s.length(); i += 4) {
            int c0 = reverse[s.charAt(i)];
            int c1 = reverse[s.charAt(i + 1)];
            buffer[index++] = (byte)((c0 << 2 | c1 >> 4) & mask);
            if(index >= buffer.length) {
                return buffer;
            }

            int c2 = reverse[s.charAt(i + 2)];
            buffer[index++] = (byte)((c1 << 4 | c2 >> 2) & mask);
            if(index >= buffer.length) {
                return buffer;
            }

            int c3 = reverse[s.charAt(i + 3)];
            buffer[index++] = (byte)((c2 << 6 | c3) & mask);
        }

        return buffer;
    }
}

