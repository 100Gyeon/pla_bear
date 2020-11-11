package com.pla_bear.board.base;

public interface Uploadable {
    void localSave();
    void uploadOnServer(String remotePath, String filename);
}
