package com.pla_bear.board.base;

@SuppressWarnings({"unused", "RedundantSuppression"})
public interface Uploadable {
    void localSave();
    void uploadOnServer(String remotePath, String filename);
}
