package com.droidplayer.lib.player;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by mingjun on 16/9/30.
 */

public abstract class AbstractPlayer implements IMediaPlayer {

    public CopyOnWriteArraySet<IPlayerListener> listeners = new CopyOnWriteArraySet<>();

    @Override
    public void addListener(IPlayerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IPlayerListener listener) {
        listeners.remove(listener);
    }
}
