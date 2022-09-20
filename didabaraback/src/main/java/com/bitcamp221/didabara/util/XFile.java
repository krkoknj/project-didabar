package com.bitcamp221.didabara.util;

import java.io.File;

public class XFile extends File {

  XFile(String filePath) {
    super(filePath);
  }

  /**
   * ==============================================================
   * This method is a cover for File.renameTo(). The motivation for this
   * cover method is that with this new version of Java (1.5.0), rename (and
   * other file methods) sometimes don't work on the first try. This seems to
   * be because file objects that have been closed are hung onto, pending
   * garbage collection.
   *
   * @param pNewFile is a File customer containing the new name for the file.
   *                 ==============================================================
   * @return true if and only if the renaming succeeded; false otherwise
   */
  public boolean renameTo(File pNewFile) {
    /*
     * =============================================== HACK - I should just
     * be able to call renameTo() here and return its result. In fact, I
     * used to do just that and this method always worked fine. Now with
     * this new version of Java (1.5.0), rename (and other file methods)
     * sometimes don't work on the first try. This is because file objects
     * that have been closed are hung onto, pending garbage collection. By
     * suggesting garbage collection, the next time, the renameTo() usually
     * (but not always) works.
     * -----------------------------------------------
     */
    //재시도 루프의 적당한 횟수를 20이라고 생각한 듯 하다.
    for (int i = 0; i < 20; i++) {

      if (super.renameTo(pNewFile)) {
        return true;
      }

      //가비지 콜렉션 - 아마도 윈도우가 점유한 메모리를 해제하기 위함인 듯.
      System.gc();

      try {
        Thread.sleep(50); //처리할 시간적 여유를 주기 위함인듯.
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }
    }
    return false;
  }
}