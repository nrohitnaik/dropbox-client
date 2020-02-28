package com.personal.dropbox.client.util;

import org.springframework.stereotype.Service;

@Service
public class SizeConverter {

  public String getString(long size) {
    long n = 1000;
    String s = "";
    double kb = size / n;
    double mb = kb / n;
    double gb = mb / n;
    double tb = gb / n;
    if (size < n) {
      s = size + " bytes";
    } else if (size >= n && size < (n * n)) {
      s = String.format("%.2f", kb) + " KB";
    } else if (size >= (n * n) && size < (n * n * n)) {
      s = String.format("%.2f", mb) + " MB";
    } else if (size >= (n * n * n) && size < (n * n * n * n)) {
      s = String.format("%.2f", gb) + " GB";
    } else if (size >= (n * n * n * n)) {
      s = String.format("%.2f", tb) + " TB";
    }
    return s;
  }
}
