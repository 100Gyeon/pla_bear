package com.pla_bear.board.base;

@SuppressWarnings("unused")
public interface Uploadable {
    void localSave();
    void uploadOnServer(String remotePath, String filename);
}
